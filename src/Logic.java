import java.beans.EventSetDescriptor;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.internal.Throwables;

public class Logic {
    private static ArrayList<Task> taskStored = Storage.retrieveTexts();
    private static ArrayList<String> msgLogger = new ArrayList<String>();
    private static ArrayList<String> event = initList("event", taskStored);
    private static ArrayList<String> deadline = initList("deadline", taskStored);
    private static ArrayList<String> floatingTask = initList("floating", taskStored);
    private static ArrayList<Task> repeatedTask = new ArrayList <Task>();
    
    private static int taskCode = 1;

    private static ArrayList<Integer> currentList = new ArrayList <Integer>();
    private static CommandHistory history = new CommandHistory(new ArrayList<>(taskStored));
    private static String currentDate = initDate();

    public static void executeCommand (String userInput) throws Exception {
        if(userInput.trim().isEmpty()) {
            msgLogger.add("Please enter a command.");
        }
        else {
            try {
                msgLogger.add("command : " + userInput);
                Command command = CommandParser.parse(userInput);

                switch (command.getCommandType()){
                    case ADD : 
                        addTask(command);
                        break;
                    case REPEAT : 
                        addRepeatTask(command);
                        break;
                    case DELETE :
                        deleteTask(command);
                        break;
                    case VIEW :
                        viewTask (command);
                        break;
                    case UPDATE :
                        updateTask(command);
                        break;
                    case SEARCH:
                        searchTask(command);
                        break;
                    case COMPLETE :
                        completeTask(command);
                        break;
                    case UNDO :
                        undoCommand();
                        break;
                    case REDO :
                        redoCommand();
                        break;
                    case EXIT :
                        break;
                    default :
                        msgLogger.add("invalid command");
                }
            } catch (Exception e) {
                msgLogger.add(e.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
	private static ArrayList<String> initList(String type, ArrayList<Task> taskStored) {
        ArrayList<String> list = new ArrayList <String>();
        for (int i = 0; i<taskStored.size(); i++){
            if(taskStored.get(i).getType().equals(type)){
                list.addAll((Collection<? extends String>) taskStored.get(i));
            }
        }
        return list;
    }

    private static String initDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    // ================================================================
    // "Add" command methods
    // ================================================================

    private static void addTask(Command command) throws Exception{
        try{
        	
            String taskType = command.getTaskType();
            ArrayList <String> detailStored;
            ArrayList <String> detailTask;
            Task.Type type;

            if(taskType.equals("floating")) {
                detailStored.add(taskType+"#"+command.getTaskDescription());
                detailTask.add(command.getTaskDescription()+ "#" + taskCode++);
                type = Task.Type.FLOATING;
                floatingTask.add(detailStored.toString());
            } 
            else if(taskType.equals("event")) {
                detailStored.add(taskType + "#" + command.getTaskEventDate() + "#" + command.getTaskEventTime() + "#" + command.getTaskDescription());
                detailTask.add(command.getTaskEventDate() + "#" + command.getTaskEventTime() + "#" + command.getTaskDescription()+ "#" + taskCode++);
                type = Task.Type.EVENT;
                event.add(detailStored.toString());
            }
            else if(taskType.equals("deadline")) {
                detailStored.add(taskType + "#" + command.getTaskDeadline() + "#" + command.getTaskDescription());
                detailTask.add(command.getTaskDeadline() + "#" + command.getTaskDescription()+ "#" + taskCode++);
                type = Task.Type.DEADLINE;
                deadline.add(detailStored.toString());
            }
            else {
                throw new Exception("Fail to add an invalid task");
            }
            if (taskStored.contains(detailStored)) {
            	msgLogger.add("Collision Task!");
            	
            }else {
            	taskStored.add(new Task(type,detailTask));
              //  sortForAdd();
                Storage.saveToFile(taskStored);
                msgLogger.add("add " + command.getTaskDescription() + " successful!");  
                history.addChangeToHistory(new ArrayList<String>(taskStored));
            }
            

        }catch (FileNotFoundException e){
            msgLogger.add(e.toString());
        }

    }
/*
    private static void sortForAdd(){
        Collections.sort(taskStored, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
*/
    // ================================================================
    // "Repeat task" command methods
    // ================================================================

    private static void addRepeatTask(Command com) throws FileNotFoundException{  
    	ArrayList <String> detailStored; 
        detailStored.add(com.getTaskRepeatPeriod() +"#" + com.getTaskDescription()+ "#" + taskCode++);
        repeatedTask.add(new Task (Task.Type.REPEAT,detailStored));
        Storage.saveToFileRC(repeatedTask);
        msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
    }

    /* 
    public static void sortForRec(){
    	Collections.sort(inputList, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("EEE");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
     */

    public static void printArrayList(){
        for (int i=0; i<taskStored.size(); i++){
            System.out.println(taskStored.get(i));
        }
    }

    // ================================================================
    // "search" command methods
    // ================================================================

    private static void searchTask(Command command) throws FileNotFoundException{
        ArrayList<String> keyWordList = command.getSearchKeyword();
        String keyword = "";
        for(int i=0; i< keyWordList.size(); i++) {
            keyword = keyWordList.get(i).toLowerCase();
            for(int j=0; j<taskStored.size(); j++) {
                String[] arr = taskStored.get(j).getDescription().split(" ");
                for(int k=0; k<arr.length; k++) {
                    if (arr[k].toLowerCase().contains(keyword)){
                        msgLogger.add(taskStored.get(j).getDescription());
                    }
                }
            }
        }
    }

    // ================================================================
    // "Delete" command methods
    // ================================================================

    private static void deleteTask(Command command){
        String taskType = command.getTaskType();
        try{
            int indexToRemove = command.getTaskID() - 1;
            String removedItem = "";

            if(taskType.equals("deadline")) {
                removedItem = deadline.remove(indexToRemove);
            }
            else if(taskType.equals("floating")) {
                removedItem = floatingTask.remove(indexToRemove);
            }
            else if(taskType.equals("event")) {
                removedItem = event.remove(indexToRemove);
            }

            taskStored.remove(new String(removedItem));
            Storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<String>(taskStored));
            msgLogger.add("deleted " + taskType + " index " + command.getTaskID() + " successfully!");

            /*int index = currentList.get(com.getTaskID()-1);
            if (com.getTaskEventDate().equals(currentDate)){
                todayTask.remove(com.getTaskID()-1);
            }*/
        }catch(Exception e){
            if(taskType.equals("deadline") && deadline.size() == 0) {
                msgLogger.add("There is no deadline task to delete!!");
            }
            else if(taskType.equals("floating") && floatingTask.size() == 0) {
                msgLogger.add("There is no floating task to delete!!");
            }
            else if(taskType.equals("event") && event.size() == 0) {
                msgLogger.add("There is no event to delete!!");
            }
            else { 
                msgLogger.add("Cannot find item to delete!!");
            }
        }

    }

    // ================================================================
    // "Complete" command method
    // ================================================================

    private static void completeTask(Command command) {
        String taskType = command.getTaskType();
        try{
            int indexToComplete = command.getTaskID() - 1;
            String completedItem = "";

            if(taskType.equals("deadline")) {
                completedItem = deadline.remove(indexToComplete);
            }
            else if(taskType.equals("floating")) {
                completedItem = floatingTask.remove(indexToComplete);
            }
            else if(taskType.equals("event")) {
                completedItem = event.remove(indexToComplete);
            }

            taskStored.remove(new String(completedItem));
            Storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<String>(taskStored));
            msgLogger.add("completed " + taskType + " index " + command.getTaskID());

        }catch(Exception e){
            if(taskType.equals("deadline") && deadline.size() == 0) {
                msgLogger.add("There is no deadline task to complete!!");
            }
            else if(taskType.equals("floating") && floatingTask.size() == 0) {
                msgLogger.add("There is no floating task to complete!!");
            }
            else if(taskType.equals("event") && event.size() == 0) {
                msgLogger.add("There is no event to complete!!");
            }
            else { 
                msgLogger.add("Cannot find item to complete!!");
            }
        }

    }

    // ================================================================
    // "Update" command methods
    // ================================================================

    private static void updateTask(Command command) throws FileNotFoundException{
        String taskType = command.getTaskType();

        try {
            int indexToUpdate = command.getTaskID() - 1;
            String updatedItem = "";
            String existingItem = "";
            if(taskType.equals("deadline")) {
                existingItem = deadline.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                updatedItem += strArr[0]+"#";
                if(command.getTaskDeadline() != null) {
                    updatedItem += command.getTaskDeadline()+"#";
                }
                else{
                    updatedItem += strArr[1]+"#";
                }

                msgLogger.add("Deadline Desc: " + command.getTaskDescription());

                if(!command.getTaskDescription().isEmpty()) {
                    updatedItem += command.getTaskDescription();
                    msgLogger.add("Using new desc");
                }
                else{
                    updatedItem += strArr[2];
                    msgLogger.add("Using current desc");
                }
                deadline.set(indexToUpdate, updatedItem);
            }
            else if(taskType.equals("floating")) {
                existingItem = floatingTask.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                updatedItem += strArr[0]+"#";                
                if(command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                }
                else{
                    updatedItem += strArr[1];
                }
                floatingTask.set(indexToUpdate, updatedItem);
            }
            else if(taskType.equals("event")) {
                existingItem = event.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                updatedItem += strArr[0]+"#";                
                if(command.getTaskEventDate() != null) {
                    updatedItem += command.getTaskEventDate()+"#";
                }
                else{
                    updatedItem += strArr[1]+"#";
                }
                if(command.getTaskEventTime() != null) {
                    updatedItem += command.getTaskEventTime()+"#";
                }
                else{
                    updatedItem += strArr[2]+"#";
                }
                if(command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                }
                else{
                    updatedItem += strArr[3];
                }
                event.set(indexToUpdate, updatedItem);
            }
            else {
                msgLogger.add("Index choosen is not valid");
                throw new Exception("Index choosen is not valid");
            }

            taskStored.set(taskStored.indexOf(existingItem), updatedItem);
            Storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<String>(taskStored));
            msgLogger.add("task updated!");
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }

    }

    // ================================================================
    // Undo command method
    // ================================================================
    private static void redoCommand() throws FileNotFoundException {
        String message = "";
        try {
            message = "redo successfully";
            taskStored = new ArrayList<String>(history.redo());
            Storage.saveToFile(taskStored);
            event = initList("event", taskStored);
            deadline = initList("deadline", taskStored);
            floatingTask = initList("floating", taskStored);
            msgLogger.add(message);  
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }
    }

    // ================================================================
    // Redo command method
    // ================================================================
    private static void undoCommand() throws FileNotFoundException {
        String message = "";
        try {
            message = "undo successfully";
            taskStored = new ArrayList<String>(history.undo());
            Storage.saveToFile(taskStored);
            event = initList("event", taskStored);
            deadline = initList("deadline", taskStored);
            floatingTask = initList("floating", taskStored);
            msgLogger.add(message);
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }   
    }

    // ================================================================
    // "View" command methods
    // ================================================================

    private static void viewTask(Command com){
        for (int i = 0; i<taskStored.size(); i++){
            if (taskStored.get(i).contains(com.getTaskEventDate())){
                currentList.add(i);
                String[] str = taskStored.get(i).trim().split("#");
                event.add(str[1] + " " + str[0] ) ;
            }   
        }
    }

    // ================================================================
    // "view today's task" command methods
    // ================================================================
    /*
    private static String viewUpcommingTask(){
        String message = "Top 10 Upcomming Tasks: \n";
        int index = 1;
        taskList = storage.retrieveTexts();
        for (int i = 0; i<10; i++){
                String[] str = taskList.get(i).trim().split("#");
                message += (index++) + ". " + str[1] + " " + str[0] + "\n" ;
                upcommingTask.add(i);
                currentList.add(i);
        }
        if(currentList.isEmpty() && upcommingTask.isEmpty()){
                message += "There is no task need to be finished.";
        }
        return message;
    }
     */ 
/*
    private static void viewTodayTask(){
        int index = 1;
        taskStored = Storage.retrieveTexts();

        for (int i = 0; i<taskStored.size(); i++){
            if (taskStored.get(i).contains(currentDate)){
                todayTask.add(i);
                currentList.add(i);
                String[] str = taskStored.get(i).trim().split("#");
                event.add( (index++) + ". " + str[1] + " " + str[0] ) ;
            }   
        }
        if(currentList.isEmpty() && todayTask.isEmpty()){
            event.add("There is no task need to be finished.");
        }
    }
*/
    // ================================================================
    // Getter methods to retrieve lists for UI
    // ================================================================
    public static String getMessageLog(){
        String messageToPrint = "";
        for(int i=0; i<msgLogger.size(); i++) {
            messageToPrint += msgLogger.get(i) + "\n";
        }
        return messageToPrint.trim();
    }

    public static String getEvents(){
        String messageToPrint = "";
        if(deadline.size() == 0) {
            return messageToPrint = "No events";
        }
        for(int i=0; i<event.size(); i++) {
            messageToPrint += "E" + (i+1) + ". "+ event.get(i) + "\n";
        }
        return messageToPrint.trim();
    }

    public static String getDeadline(){
        String messageToPrint = "";
        if(deadline.size() == 0) {
            return messageToPrint = "No tasks";
        }
        for(int i=0; i<deadline.size(); i++) {
            messageToPrint += "D" + (i+1) + ". "+ deadline.get(i) + "\n";
        }
        return messageToPrint.trim();
    }

    public static String getFloatingTask(){
        String messageToPrint = "";
        if(floatingTask.size() == 0) {
            return messageToPrint = "No tasks";
        }
        for(int i=0; i<floatingTask.size(); i++) {
            messageToPrint += "F" + (i+1) + ". "+ floatingTask.get(i) + "\n";
        }
        return messageToPrint.trim();
    }

}
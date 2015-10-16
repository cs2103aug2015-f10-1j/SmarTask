import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logic {
    private static ArrayList <String> taskList ;
    private static ArrayList <String> recurringList;
    private static ArrayList <Integer> todayTask;

    private static ArrayList <Integer> currentList;
    private static ArrayList <String> searchList; 
    private static CommandHistory history;
    private static String curDate;
    // messages pass to the UI
    private static ArrayList <String> msgLogger;
    private static ArrayList <String> events;
    private static ArrayList <String> deadline;
    private static ArrayList <String> floatingTask;

    Logic(){
        taskList = new ArrayList <String>();
        recurringList = new ArrayList <String>();
        todayTask = new ArrayList <Integer>();
        currentList = new ArrayList <Integer>();
        searchList = new ArrayList <String> ();
        msgLogger = new ArrayList<String>();
        events = new ArrayList<String>();
        deadline = new ArrayList<String>();
        floatingTask = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        curDate = dateFormat.format(cal.getTime());
    }

    public static void executeCommand (String userInput) throws Exception{
        msgLogger.add("command : " + userInput);
        Storage.createFile();
        taskList = Storage.retrieveTexts();
        history = new CommandHistory(taskList);

        Command command = CommandParser.parse(userInput);
        switch (command.getCommandType()){
            case ADD : 
                addTask(command);
                break;
            case REPEAT : 
                addRec(command);
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
            case UNDO :
                undoCommand();
                break;
            case REDO :
                redoCommand();
                break;
            case EXIT :
                break;
            default :
                msgLogger.add( "invalid command");
        }
        //taskList.clear();
    }
    // ================================================================
    // "Add" command methods
    // ================================================================

    private static void addTask(Command command) throws Exception{
        try{
            String taskType = command.getTaskType();
            String detailStored = "";

            if(taskType.equals("floating")) {
                detailStored = taskType + "#" + command.getTaskDescription();
                floatingTask.add(detailStored);
            } 
            else if(taskType.equals("event")) {
                detailStored = taskType + "#" + command.getTaskEventDate() + "#" + command.getTaskEventTime() + "#" + command.getTaskDescription();
                events.add(detailStored);
            }
            else if(taskType.equals("deadline")) {
                detailStored = taskType + "#" + command.getTaskDeadline() + "#" + command.getTaskDescription();
                deadline.add(detailStored);
            }
            else {
                throw new Exception("Fail to add an invalid task");
            }

            //taskList.add(detailStored);
            //sortForAdd();

            //history.addChangeToHistory(taskList);
            //Storage.saveToFile(taskList);
            msgLogger.add( "add " + command.getTaskDescription() + " successful!");     

        }catch (FileNotFoundException e){
            msgLogger.add(e.toString());
        }

    }

    private static void sortForAdd(){
        Collections.sort(taskList, new Comparator<String>() {
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

    // ================================================================
    // "Addrc" command methods
    // ================================================================

    private static void addRec(Command com) throws FileNotFoundException{  
        String detailStored =  com.getTaskRepeatPeriod() +"#" + com.getTaskDescription();
        recurringList.add(detailStored);
        Storage.saveToFileRC(recurringList);
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
    public static void printArrayList (){
        for (int i=0; i<taskList.size(); i++){
            System.out.println(taskList.get(i));
        }
    }

    // ================================================================
    // "search" command methods
    // ================================================================

    private static void searchTask(Command com) throws FileNotFoundException{
        searchList.clear();
        int index = 1;
        for (int i = 0; i<taskList.size(); i++){
            if (taskList.get(i).contains((CharSequence) com.getSearchKeyword())){
                currentList.add(i);
                String[] str = taskList.get(i).trim().split("#");
                msgLogger.add((index++) + ". " + str[1] + " " + str[0] ) ;
            }
        }

    }

    // ================================================================
    // "Delete" command methods
    // ================================================================

    private static void deleteTask(Command com){
        try{
            int index = currentList.get(com.getTaskID()-1);
            if (com.getTaskEventDate().equals(curDate)){
                todayTask.remove(com.getTaskID()-1);
            }
            taskList.remove(index);
            currentList.remove(com.getTaskID()-1);
            history.addChangeToHistory(taskList);
            Storage.saveToFile(taskList); 
            msgLogger.add( "delete task no. " + com.getTaskID() + " successfully!");
        }catch(Exception e){
            msgLogger.add("Error. Invalid task number");
        }

    }

    // ================================================================
    // "View" command methods
    // ================================================================

    private static void viewTask(Command com){

        // taskList = Storage.retrieveTexts();
        for (int i = 0; i<taskList.size(); i++){
            if (taskList.get(i).contains(com.getTaskEventDate())){
                currentList.add(i);
                String[] str = taskList.get(i).trim().split("#");
                events.add(str[1] + " " + str[0] ) ;
            }	
        }
    }

    // ================================================================
    // "Update" command methods
    // ================================================================

    private static void updateTask(Command com) throws FileNotFoundException{

        int taskListIndex = currentList.get(com.getTaskID()-1);
        String[] str = taskList.get(taskListIndex).trim().split("#");
        str[1] = com.getTaskDescription();
        String updateString = "";
        for (int i=0; i < str.length-1; i++){
            updateString += (str[i] + "#");
        }
        updateString += str[str.length-1];
        taskList.set(taskListIndex, updateString);
        history.addChangeToHistory(taskList);
        Storage.saveToFile(taskList);
        msgLogger.add("task updated!");

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
    private static void viewTodayTask(){
        int index = 1;
        taskList = Storage.retrieveTexts();

        for (int i = 0; i<taskList.size(); i++){
            if (taskList.get(i).contains(curDate)){
                todayTask.add(i);
                currentList.add(i);
                String[] str = taskList.get(i).trim().split("#");
                events.add( (index++) + ". " + str[1] + " " + str[0] ) ;
            }	
        }
        if(currentList.isEmpty() && todayTask.isEmpty()){
            events.add("There is no task need to be finished.");
        }


    }

    // ================================================================
    // undo command methods
    // ================================================================
    private static String redoCommand() {
        String message = "redo successfully";
        return message;
    }

    // ================================================================
    // redo command methods
    // ================================================================
    private static String undoCommand() {
        String message = "undo successfully";
        return message;
    }

    public static String getMessageLog(){
        String messageLog = "";
        for(int i=0; i<msgLogger.size(); i++) {
            messageLog += msgLogger.get(i) + "\n";
        }
        return messageLog.trim();
    }
    
    public static String getEvents(){
        String messageLog = "";
        for(int i=0; i<events.size(); i++) {
            messageLog += events.get(i) + "\n";
        }
        return messageLog.trim();
    }
    
    public static String getDeadline(){
        String messageLog = "";
        for(int i=0; i<deadline.size(); i++) {
            messageLog += deadline.get(i) + "\n";
        }
        return messageLog.trim();
    }
    public static String getFloatingTask(){
        String messageLog = "";
        for(int i=0; i<floatingTask.size(); i++) {
            messageLog += floatingTask.get(i) + "\n";
        }
        return messageLog.trim();
    }

}

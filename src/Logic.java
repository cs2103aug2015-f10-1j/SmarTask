import java.beans.EventSetDescriptor;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.RowFilter.ComparisonType;

import org.junit.internal.Throwables;

/**
 * 
 * 
 * @author Liang Mengfei
 *
 */


public class Logic {
	private static Storage storage = new Storage ();
    private static  ArrayList<Task> taskStored = storage.retrieveFile();
    private static ArrayList<String> msgLogger = new ArrayList<String>();
    private static ArrayList<String> eventForLogic = initList("event", taskStored);
    private static ArrayList<String> deadlineForLogic = initList("deadline", taskStored);
    private static  ArrayList<String> floatingTaskForLogic = initList("floating", taskStored);
    private static  ArrayList<String> repeatedTask = new ArrayList <String>();
    private static ArrayList<String> event;
    private static ArrayList<String> deadline;
    private static  ArrayList<String> floating;
    private static ArrayList<String> repeat;
    private static ArrayList <Integer> searchList;
   
  //  private int id=1;
    private  int taskCode ;

    private  ArrayList<Integer> currentList = new ArrayList <Integer>();
    private  static CommandHistory history = new CommandHistory(new ArrayList<Task>(taskStored));
  //  private  String currentDate = initDate();

    public static void executeCommand (String userInput) throws Exception {
    	Logic logic = new Logic ();
        if(userInput.trim().isEmpty()) {
            msgLogger.add("Please enter a command.");
        }
        else {
            try {
                msgLogger.add("command : " + userInput);
                Command command = CommandParser.parse(userInput);

                switch (command.getCommandType()){
                    case ADD : 
                        logic.addTask(command);
                        break;
                    case REPEAT : 
                        logic.addRepeatTask(command);
                        break;
                    case DELETE :
                        logic.deleteTask(command);
                        break;
                    case VIEW :
                    //    logic.viewTask (command);
                        break;
                    case UPDATE :
                        logic.updateTask(command);
                        break;
                    case SEARCH:
                        logic.searchTask(command);
                        break;
                    case COMPLETE :
                        logic.completeTask(command);
                        break;
                    case UNDO :
                        logic.undoCommand();
                        break;
                    case REDO :
                        logic.redoCommand();
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

	private static ArrayList<String> initList(String type, ArrayList<Task> taskStored) {
        ArrayList<String> list = new ArrayList <String>();
        floating  = new ArrayList<String>();
        event = new ArrayList<String>();
        deadline = new ArrayList<String>();
        repeat = new ArrayList<String>();
        for (int i = 0; i<taskStored.size(); i++){
            if(taskStored.get(i).getType().equals(Task.getTypeFromString(type)) && taskStored.get(i).getIsComplete()==false){
                if (type.equals("floating")){
                	list.add(taskStored.get(i).getFloatingString());
                	floating.add(taskStored.get(i).getFloatingStringForUI());
                }else if (type.equals("event")){
                	list.add(taskStored.get(i).getEventString());
                	event.add(taskStored.get(i).getEventStringForUI());
                }else if (type.equals("deadline")){
                	list.add(taskStored.get(i).getDeadlineString());
                	deadline.add(taskStored.get(i).getDeadlineStringForUI());
                }else if (type.equals("repeat")){
                	list.add(taskStored.get(i).getRepeatString());
                	repeat.add(taskStored.get(i).getRepeatStringForUI());
                }
             //   msgLogger.add(taskStored.get(i).toString());
            }
        }
        return list;
    }
    
    private int getID(){
    	DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    	Calendar cal = Calendar.getInstance();
    	String s = dateFormat.format(cal.getTime());
    	DateFormat dateFormat2 = new SimpleDateFormat("HHmmss");
        Date date = new Date();
    	String sID = dateFormat2.format(date);
    	int sNum = Integer.parseInt(s);
    	int sIDNum = Integer.parseInt(sID);
    	taskCode = sNum + sIDNum;
    	return taskCode;
    }
/*
    private String initDate() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
*/
    // ================================================================
    // "Add" command methods
    // ================================================================

  //  @SuppressWarnings("null")
	private void addTask(Command command) throws Exception{
        try{
        	
            String taskType = command.getTaskType();
            ArrayList <String> detailStored = new ArrayList <String> ();
            ArrayList <String> detailTask = new ArrayList <String> ();
            Task.Type type;
            taskCode = getID();
            Task task;

            if(taskType.equals("floating")) {
                detailStored.add(taskType+"#"+command.getTaskDescription() + "#" + taskCode);
                detailTask.add(command.getTaskDescription()+ "#" + taskCode);
                type = Task.Type.FLOATING;
          //      msgLogger.add(detailStored.toString() + "\n" + detailTask.toString());
                task = new Task (type, detailTask);
                if (!isCollision(task)) floatingTaskForLogic.add(detailStored.get(0));
                floating.add(command.getTaskDescription());
                
            } 
            else if(taskType.equals("event")) {
                detailStored.add(taskType + "#" + command.getTaskEventDate() + "#" + command.getTaskEventTime() + "#" + command.getTaskDescription() +"#"+ taskCode);
                detailTask.add(command.getTaskEventDate() + "#" + command.getTaskEventTime() + "#" + command.getTaskDescription()+ "#" + taskCode);
                type = Task.Type.EVENT;
                task = new Task (type, detailTask);
                if (!isCollision(task)) eventForLogic.add(detailStored.get(0));
                event.add(command.getTaskEventDate() + " " + command.getTaskEventTime() + " " + command.getTaskDescription()+" "+ taskCode);
            }
            else if(taskType.equals("deadline")) {
                detailStored.add(taskType + "#" + command.getTaskDeadline() + "#" + command.getTaskDescription()+"#"+ taskCode);
                detailTask.add(command.getTaskDeadline() + "#" + command.getTaskDescription()+ "#" + taskCode);
                type = Task.Type.DEADLINE;
                task = new Task (type, detailTask);
                if (!isCollision(task)) deadlineForLogic.add(detailStored.get(0));
                deadline.add(command.getTaskDeadline() + " " + command.getTaskDescription());
            }
            else {
                throw new Exception("Fail to add an invalid task");
            }
            Boolean isColl = isCollision(task);
            if (!isColl) {
            	taskStored.add(task);
                //  System.out.println(taskStored.get(0).getDescription());
                 //  sortForAdd();
                   storage.saveToFile(taskStored);
                   msgLogger.add("add " + command.getTaskDescription() + " successful!");  
                   history.addChangeToHistory(new ArrayList<Task>(taskStored));   	
            }else {
            	
                msgLogger.add("Collision Task!");
            	return;
            }
            

        }catch (FileNotFoundException e){
            msgLogger.add(e.toString());
        }

    }
	
	private static Boolean isCollision (Task task){
		Boolean boo = false;
		
		for (int i=0; i<taskStored.size(); i++){
			if (taskStored.get(i).getDescription().equals(task.getDescription())){
				if (taskStored.get(i).getType().equals(task.getType())){
					if (taskStored.get(i).getType().equals(Task.getTypeFromString("deadline"))){
						if (taskStored.get(i).getDeadline().equals(task.getDeadline())){
							boo = true;
							break;
						}
					} else if (taskStored.get(i).getType().equals(Task.getTypeFromString("floating"))){
						boo = true;
						break;
					} else if(taskStored.get(i).getType().equals(Task.getTypeFromString("event"))) {
						if (taskStored.get(i).getEventDate().equals(task.getEventDate()) && taskStored.get(i).getEventTime().equals(task.getEventTime())){
							boo = true;
							break;
						}
					} else {
						if (taskStored.get(i).getRepeatPeriod().equals(task.getRepeatPeriod())){
							boo = true;
							break;
						}
					}
				}
			}
		}
		return boo;
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

	private void addRepeatTask(Command com) throws FileNotFoundException{  
    	ArrayList <String> detailStored = new ArrayList <String> ();
    	ArrayList <String> detailTask = new ArrayList <String> ();
    	taskCode = getID();
    	String taskType = com.getTaskRepeatType();
    	Task.Type type = Task.Type.REPEAT;
    	Task task = null;
    //	msgLogger.add(taskType);
    	if (taskType.equals("day")){
    		detailTask.add( taskType+"#" +com.getTaskRepeatDayFrequency() +"#" + com.getTaskDescription() + "#" + taskCode);
    		detailStored.add(com.getTaskRepeatDayFrequency() +"#" + com.getTaskDescription() + "#" + taskCode);
    		task = new Task (type, detailStored);
    		if (!isCollision(task)) repeat.add(com.getTaskRepeatDayFrequency() +" " + com.getTaskDescription() + " " + taskCode);
    	} else if (com.getTaskRepeatType().equals("week")){
    		detailTask.add(taskType +"#"+com.getTaskRepeatDuration()+"#"+ com.getTaskDescription() + "#" + taskCode);
    		detailStored.add(com.getTaskRepeatDuration()+"#"+ com.getTaskDescription() + "#" + taskCode);
    		task = new Task (type, detailStored);
    		if (!isCollision(task))repeat.add(com.getTaskRepeatDuration()+" "+ com.getTaskDescription() + " " + taskCode);
    	} else if (com.getTaskRepeatType().equals("month")){
    		if (com.getTaskRepeatMonthFrequencyBySpecificDayOfWeek()!=null){
    			detailTask.add(taskType +"#" +com.getTaskRepeatMonthFrequencyBySpecificDayOfWeek()[1]+"#" + com.getTaskDescription() + "#" + taskCode);
        		detailStored.add(com.getTaskRepeatMonthFrequencyBySpecificDayOfWeek()[1]+"#" + com.getTaskDescription() + "#" + taskCode);
        		task = new Task (type, detailStored);
        		if (!isCollision(task)) repeat.add(com.getTaskRepeatMonthFrequencyBySpecificDayOfWeek()[1]+" " + com.getTaskDescription() + " " + taskCode);
    		} else {
    			detailTask.add(taskType +"#" +com.getTaskRepeatMonthFrequencyBySpecificDate()+"#" + com.getTaskDescription() + "#" + taskCode);
        		detailStored.add(com.getTaskRepeatMonthFrequencyBySpecificDate()+"#" + com.getTaskDescription() + "#" + taskCode);
        		task = new Task (type, detailStored);
        		if (!isCollision(task)) repeat.add(com.getTaskRepeatMonthFrequencyBySpecificDate()+" " + com.getTaskDescription() + " " + taskCode);
    		}
    		
    	} else if (com.getTaskRepeatType().equals("year")){
    		detailTask.add(taskType +"#" +com.getTaskRepeatYearFrequency()+"#" + com.getTaskDescription() + "#" + taskCode);
    		detailStored.add(com.getTaskRepeatYearFrequency()+"#" + com.getTaskDescription() + "#" + taskCode);
    		task = new Task (type, detailStored);
    		if (!isCollision(task)) repeat.add(com.getTaskRepeatYearFrequency()+" " + com.getTaskDescription() + " " + taskCode);
    	}
       // detailStored.add(com.getTaskRepeatPeriod() +"#" + com.getTaskDescription() + "#" + getID());
    //	msgLogger.add(detailTask.get(0));
    	
        taskStored.add(task);
        msgLogger.add(task.getDescription());
        repeatedTask.add(detailTask.get(0));
        storage.saveToFile(taskStored);
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

    public void printArrayList(){
        for (int i=0; i<taskStored.size(); i++){
            System.out.println(taskStored.get(i));
        }
    }

    // ================================================================
    // "search" command methods
    // ================================================================

    private void searchTask(Command command) throws FileNotFoundException{
        ArrayList<String> keyWordList = command.getSearchKeyword();
        String keyword = "";
        taskStored.clear();
        Task.Type taskType;
        int index = 1;
        searchList = new ArrayList<Integer>();
        taskStored = storage.retrieveFile(); // get the latest task from the storage
        for(int i=0; i< keyWordList.size(); i++) {
            keyword = keyWordList.get(i).toLowerCase();
            for(int j=0; j<taskStored.size(); j++) {
                String[] arr = taskStored.get(j).getDescription().split(" ");
                for(int k=0; k<arr.length; k++) {
                    if (arr[k].toLowerCase().contains(keyword)){
                    	taskCode = taskStored.get(j).getID();
                    	taskType = taskStored.get(j).getType();
                    	if (taskType.equals(Task.Type.DEADLINE)){
                    		msgLogger.add((index++)+" " + taskStored.get(j).getDescription() + " deadline is : " + taskStored.get(j).getDeadline());
                    	} else if (taskType.equals(Task.Type.EVENT)){
                    		msgLogger.add((index++)+ " "+ taskStored.get(j).getDescription() + " start time is : " + taskStored.get(j).getEventDate() + " " + taskStored.get(j).getEventTime());
                    	} else if (taskType.equals(Task.Type.FLOATING)){
                    		msgLogger.add((index++)+ " " + taskStored.get(j).getDescription());
                    	} else if (taskType.equals(Task.Type.REPEAT)){
                    		msgLogger.add((index++)+ " " + taskStored.get(j).getDescription() + " repeating peroid is : " + taskStored.get(j).getRepeatPeriod() );
                    	}
                        
                        searchList.add(taskCode);
                    	//  msgLogger.add(taskStored.get(j).getDescription());
                    }
                }
            }
        }
    }

    // ================================================================
    // "Delete" command methods
    // ================================================================

    private void deleteTask(Command command){
        String taskType = command.getTaskType();
        try{
        	int indexToRemove = command.getTaskID()-1;
            String  removedItem = "";
            String currentLine = "";
            if (taskType != null){
            if(taskType.equals("deadline")) {
            	 currentLine = deadlineForLogic.get(indexToRemove);
            	 removedItem = deadlineForLogic.remove(indexToRemove);
            	 String str[] = currentLine.split("#");
            	 taskCode = Integer.parseInt(str[str.length-1]);
            }
            else if(taskType.equals("floating")) {
            	currentLine = floatingTaskForLogic.get(indexToRemove);
            //	msgLogger.add(currentLine);
            	removedItem = floatingTaskForLogic.remove(indexToRemove);
            	String str[] = currentLine.split("#");
            	taskCode = Integer.parseInt(str[str.length-1]);
            }
            else if(taskType.equals("event")) {
            	currentLine = eventForLogic.get(indexToRemove);
            	removedItem = eventForLogic.remove(indexToRemove);
            	String str[] = currentLine.split("#");
            	taskCode = Integer.parseInt(str[str.length-1]);
            } else if (taskType.equals("repeat")){
            	currentLine = repeatedTask.get(indexToRemove);
            	removedItem = repeatedTask.get(indexToRemove);
            	String str[] = currentLine.split("#");
            	taskCode = Integer.parseInt(str[str.length-1]);
            } 
            }
            else {
            	msgLogger.add(Integer.toString(searchList.get(indexToRemove)));
            	taskCode = searchList.get(indexToRemove);
            	
            }
         //   String str[] = currentLine.split("#");
         //   taskCode = Integer.parseInt(str[str.length-1]);
         //   msgLogger.add(Integer.toString(taskCode));
            for (int i=0; i<taskStored.size(); i++){
            	if (taskStored.get(i).getID() == taskCode){
            		taskStored.remove(i);
            		break;
            	}
            }
            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("deleted " + taskType + " index " + command.getTaskID() + " successfully!");

            /*int index = currentList.get(com.getTaskID()-1);
            if (com.getTaskEventDate().equals(currentDate)){
                todayTask.remove(com.getTaskID()-1);
            }*/
        }catch(Exception e){
            if(taskType.equals("deadline") && deadlineForLogic.size() == 0) {
                msgLogger.add("There is no deadline task to delete!!");
            }
            else if(taskType.equals("floating") && floatingTaskForLogic.size() == 0) {
                msgLogger.add("There is no floating task to delete!!");
            }
            else if(taskType.equals("event") && eventForLogic.size() == 0) {
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

    private void completeTask(Command command) {
        String taskType = command.getTaskType();
        try{
            int indexToComplete = command.getTaskID()-1;
            String completedItem = "";
            String currentLine = " ";
            

            if(taskType.equals("deadline")) {
            	currentLine = deadlineForLogic.get(indexToComplete);
                completedItem = deadlineForLogic.remove(indexToComplete);
            }
            else if(taskType.equals("floating")) {
            	currentLine = floatingTaskForLogic.get(indexToComplete);
                completedItem = floatingTaskForLogic.remove(indexToComplete);
            }
            else if(taskType.equals("event")) {
                currentLine = eventForLogic.get(indexToComplete);
                completedItem = eventForLogic.remove(indexToComplete);
            }

         //   taskStored.remove(new String(completedItem));
            String str[] = currentLine.split("#");
            taskCode = Integer.parseInt(str[str.length-1]);
            
            for (int i=0; i<taskStored.size(); i++){
            	if (taskStored.get(i).getID() == taskCode){
            		taskStored.get(i).setIsComplete(true);
            		break;
            	}
            }
            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("completed " + taskType + " index " + command.getTaskID());

        }catch(Exception e){
            if(taskType.equals("deadline") && deadlineForLogic.size() == 0) {
                msgLogger.add("There is no deadline task to complete!!");
            }
            else if(taskType.equals("floating") && floatingTaskForLogic.size() == 0) {
                msgLogger.add("There is no floating task to complete!!");
            }
            else if(taskType.equals("event") && eventForLogic.size() == 0) {
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

    private void updateTask(Command command) throws FileNotFoundException{
        String taskType = command.getTaskType();
        String updatedItem = "";
        String updateItemForUI = "";
        String existingItem = "";
        String updatedTask = "";
        Task.Type type;
        ArrayList <String> updatedLine = new ArrayList <String>();

        try {
            int indexToUpdate = command.getTaskID()-1;
           
            if(taskType.equals("deadline")) {
                existingItem = deadlineForLogic.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                taskCode = Integer.parseInt(strArr[strArr.length-1]);
            //    msgLogger.add(Integer.toString(taskCode));
                updatedItem += strArr[0]+"#";
                if(command.getTaskDeadline() != null) {
                    updatedItem += command.getTaskDeadline()+"#";
                    updatedTask += command.getTaskDeadline()+"#";
                    updateItemForUI +=command.getTaskDeadline()+ " ";
                }
                else{
                    updatedItem += strArr[1]+"#";
                    updatedTask += strArr[1]+"#";
                    updateItemForUI += strArr[1]+" ";
                }
                
                
             //   msgLogger.add("Deadline Desc: " + command.getTaskDescription());

                if(!command.getTaskDescription().isEmpty()) {
                    updatedItem += command.getTaskDescription();
                    updatedTask +=command.getTaskDescription();
                    updateItemForUI += command.getTaskDescription();
                //    msgLogger.add("Using new desc");
                }
                else{
                    updatedItem += strArr[2];
                    updatedTask += strArr[2];
                    updateItemForUI += command.getTaskDescription();
                //    msgLogger.add("Using current desc");
                }
             //   msgLogger.add(updatedItem);
                updatedItem = updatedItem +"#"+Integer.toString(taskCode);
                updatedTask = updatedTask +"#"+Integer.toString(taskCode);
                updateItemForUI  += " " + Integer.toString(taskCode);
              //  msgLogger.add(updatedItem);
              //  updatedLine = new ArrayList<String> ();
              //  updatedLine.add(updatedItem);
                type = Task.Type.DEADLINE;
             
             //   msgLogger.add(Integer.toString(indexToUpdate));
                deadlineForLogic.set(indexToUpdate, updatedItem);
                deadline.set(indexToUpdate, updateItemForUI);
            //    updatedTask = new Task (Task.Type.DEADLINE, updatedLine);
            }
            else if(taskType.equals("floating")) {
                existingItem = floatingTaskForLogic.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                taskCode = Integer.parseInt(strArr[strArr.length -1]);
                updatedItem += strArr[0]+"#";                
                if(command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                    updatedTask +=command.getTaskDescription();
                    updateItemForUI += command.getTaskDescription();
                }
                
                else{
                    updatedItem += strArr[1];
                    updatedTask += strArr[1];
                    updateItemForUI += strArr[1];
                }
                updatedItem += "#"+Integer.toString(taskCode);
                updatedTask += "#"+Integer.toString(taskCode);
                updateItemForUI += " " + Integer.toString(taskCode);
               // updatedLine = new ArrayList<String> ();
               // updatedLine.add(updatedItem);
              //  updatedTask = new Task (Task.Type.FLOATING, updatedLine);
                type = Task.Type.FLOATING;
                floatingTaskForLogic.set(indexToUpdate, updatedItem);
                floating.set(indexToUpdate, updateItemForUI);
            }
            else if(taskType.equals("event")) {
                existingItem = eventForLogic.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                taskCode = Integer.parseInt(strArr[strArr.length -1]);
                updatedItem += strArr[0]+"#";                
                if(command.getTaskEventDate() != null) {
                    updatedItem += command.getTaskEventDate()+"#";
                    updatedTask += command.getTaskEventDate() + "#";
                    updateItemForUI += command.getTaskEventDate() + " ";
                }
                else{
                    updatedItem += strArr[1]+"#";
                    updatedTask += strArr[1]+"#";
                    updateItemForUI += strArr[1] + " ";
                }
                if(command.getTaskEventTime() != null) {
                    updatedItem += command.getTaskEventTime()+"#";
                    updatedTask += command.getTaskEventTime()+"#";
                    updateItemForUI += command.getTaskEventTime() + " ";
                }
                else{
                    updatedItem += strArr[2]+"#";
                    updatedTask += strArr[2]+"#";
                    updateItemForUI += strArr[2] + " ";
                }
                if(command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                    updatedTask += command.getTaskDescription();
                    updateItemForUI += command.getTaskDescription();
                }
                else{
                    updatedItem += strArr[3];
                    updatedTask += strArr[3];
                    updateItemForUI += strArr[3];
                }
                updatedItem += "#"+Integer.toString(taskCode);
                updatedTask += "#"+Integer.toString(taskCode);
                updateItemForUI += " " + Integer.toString(taskCode);
             //   updatedLine = new ArrayList <String> ();
             //   updatedLine.add(updatedItem);
             //   updatedTask = new Task (Task.Type.EVENT, updatedLine);
                eventForLogic.set(indexToUpdate, updatedItem);
                event.set(indexToUpdate, updateItemForUI);
                type = Task.Type.EVENT;
            }
            else {
                msgLogger.add("Index choosen is not valid");
                throw new Exception("Index choosen is not valid");
            }
            for (int i=0; i<taskStored.size(); i++){
            	if (taskStored.get(i).getID() == taskCode){
            	//	System.out.println(i);
            	//	msgLogger.add(Integer.toString(taskStored.size()));
            	//    msgLogger.add(Integer.toString(taskStored.get(i).getID()));
            	//    msgLogger.add(Integer.toString(i));
            		taskStored.remove(i);
           // 		msgLogger.add(Integer.toString(taskStored.size()));
            		updatedLine = new ArrayList<String>();
            		updatedLine.add(updatedTask);
            //		msgLogger.add(updatedLine.get(0));
            		Task task = new Task (type,updatedLine);
                    taskStored.add(task);
            //        msgLogger.add(Integer.toString(taskStored.size()));
            		storage.saveToFile(taskStored);
            		break;
            	}
            }
            
          //  storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("task updated!");
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }

    }

    // ================================================================
    // redo command method
    // ================================================================
    private void redoCommand() throws FileNotFoundException {
        String message = "";
        try {
            message = "redo successfully";
            taskStored = new ArrayList<Task>(history.redo());
            storage.saveToFile(taskStored);
            eventForLogic = initList("event", taskStored);
            deadlineForLogic = initList("deadline", taskStored);
            floatingTaskForLogic = initList("floating", taskStored);
            msgLogger.add(message);  
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }
    }

    // ================================================================
    // undo command method
    // ================================================================
    private void undoCommand() throws FileNotFoundException {
        String message = "";
        try {
            message = "undo successfully";
            taskStored = new ArrayList<Task>(history.undo());
            storage.saveToFile(taskStored);
            eventForLogic = initList("event", taskStored);
            deadlineForLogic = initList("deadline", taskStored);
            floatingTaskForLogic = initList("floating", taskStored);
            msgLogger.add(message);
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }   
    }

    // ================================================================
    // "View" command methods
    // ================================================================
/*
    private void viewTask(Command com){
        for (int i = 0; i<taskStored.size(); i++){
            if (taskStored.get(i).contains(com.getTaskEventDate())){
                currentList.add(i);
                String[] str = taskStored.get(i).trim().split("#");
                event.add(str[1] + " " + str[0] ) ;
            }   
        }
    }
*/
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
        if(event.size() == 0) {
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
        if(floating.size() == 0) {
            return messageToPrint = "No tasks";
        }
        for(int i=0; i<floating.size(); i++) {
            messageToPrint += "F" + (i+1) + ". "+ floating.get(i) + "\n";
        }
        return messageToPrint.trim();
    }
    
    public static String getRecurringTask(){
        String messageToPrint = "";
        if(repeat.size() == 0) {
            return messageToPrint = "No tasks";
        }
        for(int i=0; i<repeat.size(); i++) {
            messageToPrint += "R" + (i+1) + ". "+ repeat.get(i) + "\n";
        }
        return messageToPrint.trim();
    }
    

}
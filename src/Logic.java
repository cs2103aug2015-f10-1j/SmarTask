import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logic {
    private static CommandParser commandParser;
    private static ArrayList <String> taskList ;
    private static ArrayList <String> recurringList;
    private static ArrayList <Integer> todayTask;

    private static ArrayList <Integer> currentList;
    private static ArrayList <String> searchList; 
    private static Storage storage; 
    private static CommandHistory history;
    private static String curDate;
    // messages pass to the UI
    private static ArrayList <String> msglog;
    private static ArrayList <String> events;
    private static ArrayList <String> deadline;
    private static ArrayList <String> floatingTask;

    public Logic (){
        commandParser = new CommandParser();
        taskList = new ArrayList <String>();
        recurringList = new ArrayList <String>();
        storage = new Storage();
        todayTask = new ArrayList <Integer>();
        currentList = new ArrayList <Integer>();
        searchList = new ArrayList <String> ();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Calendar cal = Calendar.getInstance();
    	curDate = dateFormat.format(cal.getTime());
    }
    
    public static void executeCommand (String userInput) throws FileNotFoundException{
	String displayMessage = "";
	storage.createFile();
	taskList = storage.retrieveTexts();
	history = new CommandHistory(taskList);

	Command command = commandParser.parse(userInput);
	switch (command.getCommandType()){
        	case ADD : 
        	    addTask(command);
        	    break;
        	case ADDRECURRENCE : 
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
        	    msglog.add( "invalid command");
	}
	taskList.clear();
    }
    // ================================================================
    // "Add" command methods
    // ================================================================

    private static String addTask(Command com) throws FileNotFoundException{
	String message;
	String detailStored = com.getTaskTime() + "#" + com.getTaskTitle();
	taskList.add(detailStored);
	sortForAdd();
	history.addChangeToHistory(taskList);
	storage.saveToFile(taskList); 
	message = "add " + com.getTaskTitle() + " successful!";
	return message;

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

    private static String addRec(Command com) throws FileNotFoundException{
	String message;
	storage = new Storage();     
	String detailStored =  com.getRecurringPeriod() +"#" + com.getTaskTitle();
	recurringList.add(detailStored);
	storage.saveToFileRC(recurringList);
	message =  "addrc " + com.getTaskTitle() + " successful!";
	return message;

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

    private static String searchTask(Command com) throws FileNotFoundException{
        String message = "Search Result: \n";
        searchList.clear();
        int index = 1;
        for (int i = 0; i<taskList.size(); i++){
            if (taskList.get(i).contains(com.getSearchKeyword())){
                currentList.add(i);
                String[] str = taskList.get(i).trim().split("#");
                message += (index++) + ". " + str[1] + " " + str[0] + "\n" ;
            }
        }
        return message;
    }

    // ================================================================
    // "Delete" command methods
    // ================================================================

    private static String deleteTask(Command com){

	String message = "";
	try{
	    int index = currentList.get(com.getTaskNumber()-1);
	    if (com.getTaskTime().equals(curDate)){
		todayTask.remove(com.getTaskNumber()-1);
	    }
	    taskList.remove(index);
	    currentList.remove(com.getTaskNumber()-1);
	    history.addChangeToHistory(taskList);
	    storage.saveToFile(taskList); 
	    message = "delete task no. " + com.getTaskNumber() + " successfully!";
	}catch(Exception e){
	    message = "Error. Invalid task number";
	}
	return message;

    }

    // ================================================================
    // "View" command methods
    // ================================================================

    public static String viewTask(Command com){
	String message = "To do tasks on " + com.getTaskTime() + "\n";
	int index = 1;

	//	taskList = Storage.retrieveTexts();
	for (int i = 0; i<taskList.size(); i++){
	    if (taskList.get(i).contains(com.getTaskTime())){
		currentList.add(i);
		String[] str = taskList.get(i).trim().split("#");
		message += (index++) + ". " + str[1] + " " + str[0] + "\n" ;
	    }	
	}

	return message;
    }

    // ================================================================
    // "Update" command methods
    // ================================================================

    private static String updateTask(Command com) throws FileNotFoundException{
	String message = "";
	int taskListIndex = currentList.get(com.getTaskNumber()-1);
	String[] str = taskList.get(taskListIndex).trim().split("#");
	str[1] = com.getTaskTitle();
	String updateString = "";
	for (int i=0; i < str.length-1; i++){
	    updateString += (str[i] + "#");
	}
	updateString += str[str.length-1];
	taskList.set(taskListIndex, updateString);
	history.addChangeToHistory(taskList);
	storage.saveToFile(taskList);
	return message;
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
    private static String viewTodayTask(){
	String message = "Today's Task: \n";
	int index = 1;
	taskList = storage.retrieveTexts();

	for (int i = 0; i<taskList.size(); i++){
	    if (taskList.get(i).contains(curDate)){
		todayTask.add(i);
		currentList.add(i);
		String[] str = taskList.get(i).trim().split("#");
		message += (index++) + ". " + str[1] + " " + str[0] + "\n" ;
	    }	
	}
	if(currentList.isEmpty() && todayTask.isEmpty()){
	    message += "There is no task need to be finished.";
	}

	return message;
    }
    
    // ================================================================
    // "undo command methods
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
    
    public ArrayList getMessageLog (){
    	return msglog;
    }
    public ArrayList getEvents(){
    	return events;
    }
    public ArrayList getDeadline(){
    	return deadline;
    }
    public ArrayList getFloatingTask(){
    	return floatingTask;
    }
    
}

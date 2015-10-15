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

    public Logic (){
	commandParser = new CommandParser();
	storage = new Storage();
	taskList = new ArrayList <String>();
	recurringList = new ArrayList <String>();

	todayTask = new ArrayList <Integer>();
	currentList = new ArrayList <Integer>();
	searchList = new ArrayList <String> ();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Calendar cal = Calendar.getInstance();
	curDate = dateFormat.format(cal.getTime());
    }

    public static String executeCommand (String userInput) throws FileNotFoundException{
	String displayMessage = "";
	storage.createFile();
	taskList = storage.retrieveTexts();
	history = new CommandHistory(taskList);

	Command command = commandParser.parse(userInput);
	switch (command.getCommandType()){
        	case ADD : 
        	    displayMessage = addTask(command);
        	    break;
        	case ADDRECURRENCE : 
        	    displayMessage = addRec(command);
        	    break;
        	case DELETE :
        	    displayMessage = deleteTask(command);
        	    break;
        	case VIEW :
        	    displayMessage = viewTask (command);
        	    break;
        	case UPDATE :
        	    displayMessage = updateTask(command);
        	    break;
        	case SEARCH:
        	    displayMessage = searchTask(command);
        	    break;
        	case UNDO :
        	    break;
        	case REDO :
        	    break;
        	case EXIT :
        	    break;
        	default :
        	    return "invalid command";
	}

	taskList.clear();
	return displayMessage;

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
	currentList.clear();
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

}

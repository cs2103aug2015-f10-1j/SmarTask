import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Logic {
    private static CommandParser commandParser;
    //private Storage storage;
    private static ArrayList <String> taskList ;
    private static ArrayList <String> recList;
  //  private static HashMap <Integer, Integer> currentList;
    private static ArrayList <Integer> currentList;
 
    static Storage storage; 

    public Logic (){
        commandParser = new CommandParser();
        taskList = new ArrayList <String>();
        recList = new ArrayList <String>();
        storage = new Storage();
    //    currentList = new HashMap <Integer, Integer>();
        currentList = new ArrayList <Integer>();
      
        
    }

    public static String executeCommand (String userInput) throws FileNotFoundException{
        String displayMessage = "";
        
        taskList = storage.retrieveTexts(storage.createFile());
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
           // case SEARCH:
            //	displayMessage = searchTask(command);
            //	break;
            case EXIT :
                break;
            default :
                // return Command.Type.INVALID;
        }
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
        storage.saveToFile(taskList); 
        message = "add " + com.getTaskTitle() + " successful!";
        return message;

    }
    
    public static void sortForAdd(){
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
        recList.add(detailStored);
        storage.saveToFileRC(recList); // BUG HERE
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
    // "Delete" command methods
    // ================================================================

    public static String deleteTask(Command com){
        String message = "";
        try{
        	int index = currentList.get(com.getTaskNumber());
        	taskList.remove(index);
            currentList.remove(com.getTaskNumber());
            message = "delete "+com.getTaskTitle() + " successful!";
        }catch(Exception e){
        	message = "Error. Invalid task number";
        }
        
        return message;
    }
    
 // ================================================================
    // "View" command methods
    // ================================================================

    public static String viewTask(Command com){
    	String message = "";
    	int index = 1;
    	
    //	taskList = Storage.retrieveTexts();
    	for (int i=0; i<taskList.size(); i++){
    		if (taskList.get(i).contains(com.getTaskTime())){
    			currentList.add(i);
    			String[] str = taskList.get(i).trim().split("#");
    			message += (index++) + str[1] + "\n";
    		}	
    	}
    	
    	return message;
    }

    // ================================================================
    // "show to user" command methods
    // ================================================================
    private static String showToUser(String message){
     //   System.out.println( message);
		return message;
    }
}
/*
class LogicTest {
	public static void main(String[] arg) throws FileNotFoundException {
		Logic logic = new Logic();
		logic.executeCommand("addrc <title1> <Wed>");
		logic.executeCommand("addrc <title2> <Tue>");
		logic.executeCommand("addrc <title3> <Mon>");
	//	logic.executeCommand("addrc <title4> <Mon, 11:00>");
	//	logic.executeCommand("addrc <title5> <Fri, 04:00>");
	//	logic.executeCommand("addrc <title6> <Sat, 01:00>");
	//	logic.executeCommand("addrc <title7> <Sun, 03:00>");
		System.out.println("dgerge");
		logic.printArrayList();
	}
	
}
*/
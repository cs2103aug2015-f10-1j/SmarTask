import java.io.FileNotFoundException;
import java.util.*;


public class Logic {
    private static CommandParser commandParser;
    //private Storage storage;
    private static ArrayList <String> inputList ;
    private static ArrayList <String> taskList;
    static Storage storage; 

    private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String VIEW = "view";
    private static final String EXIT = "exit";

    public Logic (){
        commandParser = new CommandParser();
        inputList = new ArrayList <String>();
    }

    public static void executeCommand (String userInput) throws FileNotFoundException{
        String displayMessage = "";
        Command command = commandParser.parse(userInput);
        switch (command.getCommandType()){
            case ADD : 
                displayMessage = addTask(command);
                break;
            case DELETE :
                displayMessage = deleteTask(command);
                break;
            case VIEW :
                displayMessage = viewTask ();
                break;
            case EXIT :
                break;
            default :
                // return Command.Type.INVALID;
        }
        showToUser(displayMessage);

    }

    // ================================================================
    // "Add" command methods
    // ================================================================

    private static String addTask(Command com) throws FileNotFoundException{
        String message;
        storage = new Storage();
        message = ADD + " " + com.getTaskTitle() + " successful!";
        String detailStored = com.getTaskTitle() + " " + com.getTaskLabel() + " "+
                com.getTaskDetail() + " " + com.getTaskTime();
        inputList.add(detailStored);
        //storage.saveToFile(); // BUG HERE
        return message;

    }

    // ================================================================
    // "Delete" command methods
    // ================================================================

    public static String deleteTask(Command com){
        String message = "";
        if (taskList.get(com.getTaskNumber()) != null){
        	message = DELETE +" "+com.getTaskTitle() + " successful!";
            taskList.remove(com.getTaskNumber());
        }else{
        	message = "Error. Invalid task number";
        }
        
        return message;
    }
    
 // ================================================================
    // "View" command methods
    // ================================================================

    public static String viewTask(){
    	String message = "";
    	taskList = Storage.getArrayList();
    	for (int i=0; i<taskList.size(); i++){
    		message += taskList.get(i) + "\n";
    	}
    	
    	return message;
    }

    // ================================================================
    // "show to user" command methods
    // ================================================================
    private static void showToUser(String message){
        System.out.println(message);
    }


}

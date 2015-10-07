import java.io.FileNotFoundException;
import java.util.*;


public class Logic {
    private static CommandParser commandParser;
    //private Storage storage;
    private static ArrayList <String> inputList ;
    private static ArrayList <String> taskList;
    static Storage storage; 

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
        showToUser(displayMessage);

    }

    // ================================================================
    // "Add" command methods
    // ================================================================

    private static String addTask(Command com) throws FileNotFoundException{
        String message;
        storage = new Storage();
        message = "add " + com.getTaskTitle() + " successful!";
        String detailStored = com.getTaskTitle() + " " + com.getTaskLabel() + " "+
                com.getTaskDetail() + " " + com.getTaskTime();
        inputList.add(detailStored);
        //storage.saveToFile(); // BUG HERE
        return message;

    }
    
    // ================================================================
    // "Addrc" command methods
    // ================================================================

    private static String addRec(Command com) throws FileNotFoundException{
        String message;
        storage = new Storage();
        message =  "add " + com.getTaskTitle() + " successful!";
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
        try{
        	message = "delete "+com.getTaskTitle() + " successful!";
            taskList.remove(com.getTaskNumber());
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
    	taskList = Storage.getArrayList();
    	for (int i=0; i<taskList.size(); i++){
    		if (taskList.get(i).contains(com.getTaskTime()))
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

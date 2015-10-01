import java.io.FileNotFoundException;
import java.util.*;


public class Logic {
	private CommandParser commandParser;
	//private Storage storage;
	private ArrayList <String> taskList ;
	private Command targetTask;
	Storage storage = new Storage (); 
	
	private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String VIEW = "view";
    private static final String EXIT = "exit";
    
	
	public Logic (){
		commandParser = new CommandParser();
		taskList = new ArrayList <String>();
	}
	
	public void executeCommand (String userInput) throws FileNotFoundException{
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
		//	viewTask (command);
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
	
	@SuppressWarnings("static-access")
	private String addTask(Command com) throws FileNotFoundException{
		String message;
		message = ADD+ com.getTaskTitle() + "successful!";
		String detailStored = com.getTaskTitle() + " " + com.getTaskLabel() + " "+
		               com.getTaskDetail() + " " + com.getTaskTime();
		taskList.add(detailStored);
		storage.saveToFile();
		return message;
		
	}
	
	// ================================================================
    // "Delete" command methods
    // ================================================================
	
	private String deleteTask(Command com){
		String message = "";
		message = DELETE +" "+com.getTaskTitle() + " successful!";
		taskList.remove(com.getTaskNumber());
		return message;
	}
	
	
	
	// ================================================================
    // "show to user" command methods
    // ================================================================
	private void showToUser(String message){
		System.out.println(message);
	}
	

}

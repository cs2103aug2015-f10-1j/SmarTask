import java.io.IOException;
import java.util.*;
public class Logic {
	private static CommandParser commandParser;
	//private Storage storage;
	private static ArrayList <String> taskList ;
	private Command targetTask;
	
	private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String VIEW = "view";
    private static final String EXIT = "exit";
    
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		String userCommand = sc.nextLine();
		executeCommand(userCommand);
	}
	
	
	
	public Logic (){
		commandParser = new CommandParser();
		taskList = new ArrayList <String>();
	}
	
	public static void executeCommand (String userInput){
		String displayMessage = "";
		Command command = commandParser.parse(userInput);
		switch (command.getCommandType()){
		
		case ADD : 
			displayMessage = addTask(command);
			break;
		case DELETE :
		//	deleteTask(command);
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
	
	private static String addTask(Command com){
		String message;
		message = ADD+ com.getTaskTitle() + "successful!";
		String detailStored = com.getTaskTitle() + " " + com.getTaskLabel() + " "+
		               com.getTaskDetail() + " " + com.getTaskTime();
		taskList.add(detailStored);
		
		return message;
		
	}
	
	// ================================================================
    // "show to user" command methods
    // ================================================================
	private static void showToUser(String message){
		System.out.println(message);
	}
	

}

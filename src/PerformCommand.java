import java.util.*;
public class PerformCommand {
	private Command command;
	private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String VIEW = "view";
    private static final String EXIT = "exit";
    private ArrayList <String> tasklist;
	
	public PerformCommand (){
		command = CommandParser.getCommand();
		tasklist = new ArrayList<String>();
	}
	
	public void differenciateCommand (){
		String displayMessage;
		switch (command.getCommandType()){
		
		case ADD : 
			displayMessage = addTask(command);
			break;
		case DELETE :
			displayMessage = deleteTask(command);
			break;
		case VIEW :
			displayMessage = viewTask (command);
			break;
		case EXIT :
			break;
		}
		showToUser(displayMessage);
		
	}
	
	// ================================================================
    // "Add" command methods
    // ================================================================
	
	private String addTask(Command com){
		String message;
		
		
		return message;
		
	}

}

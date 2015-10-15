import java.util.*;

/**
 * CommandParser parses user's input to create Command objects that have the
 * appropriate fields initialised. For example, the "add" command requires the
 * taskTitle, taskLabel, taskDetail, taskTime field to be initialised.
 * 
 * @author Bobby Lin
 *
 */

public class CommandParser {
    private static final int POSITION_ZERO_PARAM_ARGUMENT = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    private static final int POSITION_SECOND_PARAM_ARGUMENT = 2;

    private static final String REGEX_WHITESPACES = " ";

    private static final String USER_COMMAND_ADD = "add";
    private static final String USER_COMMAND_ADDRECURRENCE = "addrc";
    private static final String USER_COMMAND_DELETE = "delete";
    private static final String USER_COMMAND_DELETERECURRENCE = "deleterc";
    private static final String USER_COMMAND_UPDATE = "update";
    private static final String USER_COMMAND_VIEW = "view";
    private static final String USER_COMMAND_UNDO= "undo";
    private static final String USER_COMMAND_REDO = "redo";
    private static final String USER_COMMAND_COMPLETE = "complete";
    private static final String USER_COMMAND_EXIT = "exit";

    public CommandParser() {
    }

    public static Command parse(String userInput) {
	Command command;
	ArrayList<String> parameters = splitString(userInput);
	String userCommand = getUserCommand(parameters);
	ArrayList<String> arguments = getUserArguments(parameters);

	switch (userCommand.toLowerCase()) {
	case USER_COMMAND_ADD :
	    command = initAddCommand(arguments);
	    break;

	case USER_COMMAND_ADDRECURRENCE :
	    command = initAddRecurrenceCommand(arguments);
	    break;

	case USER_COMMAND_DELETE :
	    command = initDeleteCommand(arguments);
	    break;

	case USER_COMMAND_DELETERECURRENCE :
	    command = initDeleteRecurrenceCommand(arguments);
	    break;

	case USER_COMMAND_VIEW :
	    command = initViewDateCommand(arguments);
	    break;

	case USER_COMMAND_UPDATE :
	    command = initUpdateCommand(arguments);
	    break;

	case USER_COMMAND_UNDO :
	    command = initUndoCommand();
	    break;

	case USER_COMMAND_REDO :
	    command = initRedoCommand();
	    break;

	case USER_COMMAND_COMPLETE :
	    command = initCompleteCommand(arguments);
	    break;

	case USER_COMMAND_EXIT :
	    command = initExitCommand();
	    break;

	default :
	    command = initInvalidCommand();
	}
	return command;
    }

    private static ArrayList<String> splitString(String arguments) {
	String[] strArray = arguments.trim().split(REGEX_WHITESPACES, 2);
	return new ArrayList<String>(Arrays.asList(strArray));
    }

    private static ArrayList<String> getUserArguments(ArrayList<String> parameters) {
	String[] strArray = extractParameter(parameters);
	return new ArrayList<String>(Arrays.asList(strArray));
    }

    private static String[] extractParameter(ArrayList<String> parameters) {
	String line = parameters.get(1);
	String[] strArray = line.trim().split(">");
	for(int i = 0 ;i < strArray.length; i++) {
	    strArray[i] = strArray[i].trim().replaceAll("<", "");
	}
	return strArray;
    }

    private static String getUserCommand(ArrayList<String> parameters) {
	return parameters.get(POSITION_ZERO_PARAM_ARGUMENT);
    }

    // ================================================================
    // Create add command method
    // ================================================================

    private static Command initAddCommand(ArrayList<String> arguments) {
	Command command = new Command(Command.Type.ADD);
	command.setTaskTitle(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
	command.setTaskTime(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
	return command;
    }

    // ================================================================
    // Create add recurrence command method
    // ================================================================

    private static Command initAddRecurrenceCommand(ArrayList<String> arguments) {
	Command command = new Command(Command.Type.ADDRECURRENCE);
	command.setTaskTitle(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
	command.setRecurringPeriod(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
	return command;
    }

    // ================================================================
    // Create delete command method
    // ================================================================

    private static Command initDeleteCommand(ArrayList<String> arguments) {
	Command command = new Command(Command.Type.DELETE);
	command.setTaskNumber(Integer.parseInt(arguments.get(POSITION_ZERO_PARAM_ARGUMENT)));
	command.setTaskTime(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
	return command;
    }

    // ================================================================
    // Create delete recurrence command method
    // ================================================================

    private static Command initDeleteRecurrenceCommand(ArrayList<String> arguments) {
	Command command = new Command(Command.Type.DELETERECURRENCE);
	command.setTaskNumber(Integer.parseInt(arguments.get(POSITION_ZERO_PARAM_ARGUMENT)));
	command.setRecurringPeriod(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
	return command;
    }

    // ================================================================
    // Create view all tasks of a day command method
    // ================================================================

    private static Command initViewDateCommand(ArrayList<String> arguments) {
	Command command = new Command(Command.Type.VIEW);
	command.setTaskTime(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
	return command;
    }

    // ================================================================
    // Create update command method
    // ================================================================

    private static Command initUpdateCommand(ArrayList<String> arguments) {
	Command command = new Command(Command.Type.UPDATE);
	command.setTaskNumber(Integer.parseInt(arguments.get(POSITION_ZERO_PARAM_ARGUMENT)));
	command.setTaskTitle(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
	command.setTaskTime(arguments.get(POSITION_SECOND_PARAM_ARGUMENT));
	return command;
    }

    // ================================================================
    // Create complete command method
    // ================================================================

    private static Command initCompleteCommand(ArrayList<String> arguments) {
	Command command = new Command(Command.Type.COMPLETE);
	command.setTaskNumber(Integer.parseInt(arguments.get(POSITION_ZERO_PARAM_ARGUMENT)));
	command.setTaskTime(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
	return command;
    }

    // ================================================================
    // Create exit command method
    // ================================================================

    private static Command initExitCommand() {
	return new Command(Command.Type.EXIT);
    }

    // ================================================================
    // Create invalid command method
    // ================================================================

    private static Command initInvalidCommand() {
	return new Command(Command.Type.INVALID);
    }

    // ================================================================
    // TO DO: Create search command method
    // ================================================================
    private Command initSearchCommand() {
	return new Command(Command.Type.SEARCH);
    }

    // ================================================================
    // TO DO: Create undo command method
    // ================================================================
    private static Command initUndoCommand() {
	return new Command(Command.Type.UNDO);
    }

    // ================================================================
    // TO DO: Create redo command method
    // ================================================================
    private static Command initRedoCommand() {
	return new Command(Command.Type.REDO);
    }

}
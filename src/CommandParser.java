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
    private static final int POSITION_PARAM_COMMAND = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    private static final int POSITION_SECOND_PARAM_ARGUMENT = 2;
    private static final int POSITION_THIRD_PARAM_ARGUMENT = 3;

    private static final String REGEX_WHITESPACES = " ";

    private static final String USER_COMMAND_ADD = "add";
    private static final String USER_COMMAND_DELETE = "delete";
    private static final String USER_COMMAND_VIEW = "view";
    private static final String USER_COMMAND_EXIT = "exit";

    public CommandParser() {
    }

    public Command parse(String userInput) {
        Command command;
        ArrayList<String> parameters = splitString(userInput);
        String userCommand = getUserCommand(parameters);
        ArrayList<String> arguments = getUserArguments(parameters);

        switch (userCommand.toLowerCase()) {
        case USER_COMMAND_ADD :
            command = initAddCommand(arguments);
            break;

        case USER_COMMAND_DELETE :
            command = initDeleteCommand(arguments);
            break;

        case USER_COMMAND_VIEW :
            command = initViewCommand(arguments);
            break;

        case USER_COMMAND_EXIT :
            command = initExitCommand();
            break;

        default :
            command = initInvalidCommand();
        }
        return command;
    }

    private ArrayList<String> splitString(String arguments) {
        String[] strArray = arguments.trim().split(REGEX_WHITESPACES, 2);
        return new ArrayList<String>(Arrays.asList(strArray));
    }

    private ArrayList<String> getUserArguments(ArrayList<String> parameters) {
        String[] strArray = extractParameter(parameters);
        return new ArrayList<String>(Arrays.asList(strArray));
    }

    private String[] extractParameter(ArrayList<String> parameters) {
        String line = parameters.get(1);
        String[] strArray = line.trim().split(">");
        for(int i = 0 ;i < strArray.length; i++) {
            strArray[i] = strArray[i].trim().replaceAll("<", "");
        }
        return strArray;
    }

    private String getUserCommand(ArrayList<String> parameters) {
        return parameters.get(POSITION_PARAM_COMMAND);
    }

    // ================================================================
    // Create add command method
    // ================================================================

    private Command initAddCommand(ArrayList<String> arguments) {
        Command command = new Command(Command.Type.ADD);
        command.setTaskTitle(arguments.get(POSITION_PARAM_COMMAND));
        command.setTaskLabel(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
        command.setTaskDetail(arguments.get(POSITION_SECOND_PARAM_ARGUMENT));
        command.setTaskTime(arguments.get(POSITION_THIRD_PARAM_ARGUMENT));
        return command;
    }

    // ================================================================
    // Create delete command method
    // ================================================================

    private Command initDeleteCommand(ArrayList<String> arguments) {
        Command command = new Command(Command.Type.DELETE);
        command.setTaskTime(arguments.get(POSITION_PARAM_COMMAND));
        command.setTaskNumber(Integer.parseInt(arguments.get(POSITION_FIRST_PARAM_ARGUMENT)));
        return command;
    }

    // ================================================================
    // TO DO: Create view task command method
    // ================================================================

    private Command initViewCommand(ArrayList<String> arguments) {
        Command command = new Command(Command.Type.VIEW);
        command.setTaskNumber(Integer.parseInt(arguments.get(POSITION_PARAM_COMMAND)));
        return command;
    }

    // ================================================================
    // Create exit command method
    // ================================================================

    private Command initExitCommand() {
        return new Command(Command.Type.EXIT);
    }

    // ================================================================
    // Create invalid command method
    // ================================================================

    private Command initInvalidCommand() {
        return new Command(Command.Type.INVALID);
    }

}

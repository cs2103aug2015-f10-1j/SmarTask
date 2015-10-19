import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommandParser parses user's input to create Command objects that have the
 * appropriate fields initialised. For example, the "add" command requires the
 * taskDetail and taskTime field to be initialised.
 * 
 * @author Bobby Lin
 *
 */

public class CommandParser {
    private static final int POSITION_ZERO_PARAM_ARGUMENT = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    private static final int POSITION_SECOND_PARAM_ARGUMENT = 2;
    private static final int TWOPARTS = 2;

    private static final String REGEX_WHITESPACES = " ";

    private static final String USER_COMMAND_ADD = "add";
    private static final String USER_COMMAND_DELETE = "delete";
    private static final String USER_COMMAND_UPDATE = "update";
    private static final String USER_COMMAND_VIEW = "view";
    private static final String USER_COMMAND_SEARCH = "search";
    private static final String USER_COMMAND_UNDO= "undo";
    private static final String USER_COMMAND_REDO = "redo";
    private static final String USER_COMMAND_REPEAT = "repeat";
    private static final String USER_COMMAND_CANCEL_REPEAT = "cancel";
    private static final String USER_COMMAND_COMPLETE = "complete";
    private static final String USER_COMMAND_EXIT = "exit";
    private static final String MSG_INCORRECT_FORMAT = "Error in attributes: Ensure attributes are entered in valid format";
    private static final String MSG_NULL_POINTER = "Error: Null pointer";
    private static ArrayList<String> parserLogger = new ArrayList<String>();

    public static Command parse(String userInput) throws Exception {
        addToParserLogger("command: " + userInput);
        Command command;
        ArrayList<String> parameters = splitStringIntoTwoParts(userInput);
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
                command = initViewDateCommand(arguments);
                break;

            case USER_COMMAND_UPDATE :
                command = initUpdateCommand(arguments);
                break;

            case USER_COMMAND_COMPLETE :
                command = initCompleteCommand(arguments);
                break;

            case USER_COMMAND_SEARCH :
                command = initSearchCommand(arguments);
                break;

            case USER_COMMAND_REPEAT :
                command = initRepeatCommand(arguments);
                break;

            case USER_COMMAND_CANCEL_REPEAT :
                command = initCancelRepeatCommand(arguments);
                break;

            case USER_COMMAND_UNDO :
                command = initUndoCommand();
                break;

            case USER_COMMAND_REDO :
                command = initRedoCommand();
                break;

            case USER_COMMAND_EXIT :
                command = initExitCommand();
                break;

            default :
                command = initInvalidCommand();
        }

        assert command != null : "command is null"; 

        return command;
    }

    private static void addToParserLogger(String userInput) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        parserLogger.add(dateFormat.format(date) + " --- " + userInput );
    }

    private static ArrayList<String> splitStringIntoTwoParts(String arguments) throws Exception {
        assert arguments !=null : "String argument in splitStringIntoTwoParts(String arguments) is null";
        try {
            String[] strArray = arguments.trim().split(REGEX_WHITESPACES, TWOPARTS);
            return new ArrayList<String>(Arrays.asList(strArray));
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } 
    }
    
    private static ArrayList<String> splitStringByLeftCurlyBracket(String arguments) {
        assert arguments !=null : "String argument in splitStringBySpace(String arguments) is null";
        String[] strArray = arguments.trim().split("(?=\\{)");
        return new ArrayList<String>(Arrays.asList(strArray));
    }

    private static ArrayList<String> splitStringBySpace(String arguments) {
        assert arguments !=null : "String argument in splitStringBySpace(String arguments) is null";
        String[] strArray = arguments.trim().split(REGEX_WHITESPACES);
        return new ArrayList<String>(Arrays.asList(strArray));
    }

    private static ArrayList<String> getUserArguments(ArrayList<String> parameters) {
        assert parameters !=null;
        String[] strArray = extractParameter(parameters);
        return new ArrayList<String>(Arrays.asList(strArray));
    }

    private static String[] extractParameter(ArrayList<String> parameters) {
        String line;
        if(parameters.size() == 1) {
            line = parameters.get(POSITION_ZERO_PARAM_ARGUMENT);
        }
        else {
            line = parameters.get(POSITION_FIRST_PARAM_ARGUMENT);
        }

        String[] strArray = line.trim().split(">>");
        return strArray;
    }

    private static String getUserCommand(ArrayList<String> parameters) {
        return parameters.get(POSITION_ZERO_PARAM_ARGUMENT);
    }

    public static void viewCommandParserLog() {
        System.out.println("==============CommandParser Log=================");
        for(int i = 0; i < parserLogger.size(); i++) {
            System.out.println(parserLogger.get(i));
        }
    }

    // ================================================================
    // Create add command method
    // ================================================================

    private static Command initAddCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.ADD);
            if(arguments.size() == 1) {
                command.setTaskType("floating");
                command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
            }
            else if(arguments.size() == 2) {
                command.setTaskType("deadline");
                command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
                command.setTaskDeadline(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
            }
            else if(arguments.size() == 3) {
                command.setTaskType("event");
                command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
                command.setTaskEventDate(arguments.get(POSITION_FIRST_PARAM_ARGUMENT));
                command.setTaskEventTime(arguments.get(POSITION_SECOND_PARAM_ARGUMENT));
            }
            return command;

        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }

    }

    // ================================================================
    // Create delete command method
    // ================================================================

    private static Command initDeleteCommand(ArrayList<String> arguments) throws Exception {  
        try {
            Command command = new Command(Command.Type.DELETE);
            String alphaIndex = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
            if(alphaIndex.startsWith("D") || alphaIndex.startsWith("d")) {
                command.setTaskType("deadline");
            }
            else if(alphaIndex.startsWith("E") || alphaIndex.startsWith("e")) {
                command.setTaskType("event");
            }
            else if(alphaIndex.startsWith("F") || alphaIndex.startsWith("f")) {
                command.setTaskType("floating");
            }
            command.setTaskID(Integer.parseInt(arguments.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[a-zA-Z]", "")));
            if(command.getTaskID()<=0) {
                throw new Exception(MSG_NULL_POINTER);
            }
            return command;
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
    }

    // ================================================================
    // Create view all tasks of a day command method
    // ================================================================

    private static Command initViewDateCommand(ArrayList<String> arguments) throws Exception {    
        try {
            Command command = new Command(Command.Type.VIEW);
            return command;
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
    }

    // ================================================================
    // Create update command method
    // ================================================================

    private static Command initUpdateCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.UPDATE);
            String alphaIndex = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
            ArrayList<String> parameters = splitStringIntoTwoParts(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));

            if(alphaIndex.startsWith("D") || alphaIndex.startsWith("d")) {
                command.setTaskType("deadline");
                command.setTaskID(Integer.parseInt(parameters.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[a-zA-Z]", "")));
                command.setTaskDeadline(getDeadline(parameters.get(POSITION_FIRST_PARAM_ARGUMENT))[0]);
                command.setTaskDescription(parameters.get(POSITION_FIRST_PARAM_ARGUMENT).replaceAll("[0-9]{1,2}:[0-9]{2}", "")
                        .replaceAll("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d", "").trim());   
            }
            else if(alphaIndex.startsWith("E") || alphaIndex.startsWith("e")) {
                command.setTaskType("event");
                command.setTaskID(Integer.parseInt(parameters.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[a-zA-Z]", "")));
                command.setTaskEventDate(getDate(parameters.get(POSITION_FIRST_PARAM_ARGUMENT))[0]);
                command.setTaskEventTime(getDuration(parameters.get(POSITION_FIRST_PARAM_ARGUMENT))[0]);
                command.setTaskDescription(parameters.get(POSITION_FIRST_PARAM_ARGUMENT).replaceAll("[0-9]{1,2}:[0-9]{2}[- -.][0-9]{1,2}:[0-9]{2}", "")
                        .replaceAll("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d", "").trim());
            }
            else if(alphaIndex.startsWith("F") || alphaIndex.startsWith("f")) {
                command.setTaskType("floating");
                command.setTaskID(Integer.parseInt(parameters.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[a-zA-Z]", "")));
                command.setTaskDescription(parameters.get(POSITION_FIRST_PARAM_ARGUMENT));
            }else {
                addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
                throw new Exception(MSG_INCORRECT_FORMAT);
            }
            
            return command;
            
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
    }
    
    private static String[] getDate(String desc) {
        int count=0;
        String[] allMatches = new String[2];
        Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(desc);
        while (m.find()) {
            allMatches[count] = m.group();
            count++;
        }
        return allMatches;
    }
    
    private static String[] getDuration(String desc) {
        int count=0;
        String[] allMatches = new String[2];
        Matcher m = Pattern.compile("[0-9]{1,2}:[0-9]{2}[- -.][0-9]{1,2}:[0-9]{2}").matcher(desc);
        while (m.find()) {
            allMatches[count] = m.group();
            count++;
        }
        return allMatches;
    }
    
    private static String[] getDeadline(String desc) {
        int count=0;
        String[] allMatches = new String[2];
        Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d [0-9]{1,2}:[0-9]{2}").matcher(desc);
        while (m.find()) {
            allMatches[count] = m.group();
            count++;
        }
        return allMatches;
    }

    // ================================================================
    // Create complete command method
    // ================================================================

    private static Command initCompleteCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.COMPLETE);
            String alphaIndex = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
            
            if(alphaIndex.startsWith("D") || alphaIndex.startsWith("d")) {
                command.setTaskType("deadline");
            }
            else if(alphaIndex.startsWith("E") || alphaIndex.startsWith("e")) {
                command.setTaskType("event");
            }
            else if(alphaIndex.startsWith("F") || alphaIndex.startsWith("f")) {
                command.setTaskType("floating");
            }
            command.setTaskID(Integer.parseInt(arguments.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[a-zA-Z]", "")));
            if(command.getTaskID()<=0) {
                throw new Exception(MSG_NULL_POINTER);
            }
            
            return command;
            
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
    }

    // ================================================================
    // Create search command method
    // ================================================================
    private static Command initSearchCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.SEARCH);
            ArrayList<String> keywords = splitStringBySpace(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
            command.setSearchKeyword(keywords);
            return command; 
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
    }

    // ================================================================
    // Create recurrence using repeat command method
    // ================================================================
    private static Command initRepeatCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.REPEAT);
            ArrayList<String> param = splitStringByLeftCurlyBracket(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
            command.setDateOfRepeatAdded(getDate(param .get(POSITION_ZERO_PARAM_ARGUMENT))[POSITION_ZERO_PARAM_ARGUMENT].trim());
            command.setTaskRepeatDuration(getDuration(param .get(POSITION_ZERO_PARAM_ARGUMENT))[POSITION_ZERO_PARAM_ARGUMENT].trim());
            command.setTaskDescription(param.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[0-9]{1,2}:[0-9]{2}[- -.][0-9]{1,2}:[0-9]{2}", "")
                    .replaceAll("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d", "").trim());
            
            String content = param.get(POSITION_FIRST_PARAM_ARGUMENT).replaceAll("\\{", "").replaceAll("\\}", "").trim();
            String[] recurrenceParam = content.split(",");
            String type = recurrenceParam[POSITION_ZERO_PARAM_ARGUMENT].trim();
            String frequency = recurrenceParam[POSITION_FIRST_PARAM_ARGUMENT].trim();
            String endDate = recurrenceParam[POSITION_SECOND_PARAM_ARGUMENT].trim();
            
            command.setTaskRepeatType(type);
            
            if(type.equals("day")) {
        	command.setTaskRepeatDayFrequency(frequency);
            }
            else if(type.equals("week")) {
        	String[] days = frequency.split("/");
        	command.setTaskRepeatOnDayOfWeek(createIsDayTrue(days));
            }
            else if(type.equals("month")) {
        	String[] fqParam = frequency.split("\\s+");
        	if(fqParam.length == 2) {
        	    command.setTaskRepeatMonthFrequencyBySpecificDate(fqParam[1].trim());
        	}else {
        	    fqParam = fqParam[0].split("-");
        	    command.setTaskRepeatMonthFrequencyBySpecificDayOfWeek(fqParam);
        	}
        	
        	command.setTaskRepeatMonthFrequency(frequency);
            }
            else if (type.equals("year")) {
        	command.setTaskRepeatYearFrequency(frequency);
            }
            else {
        	addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
                throw new Exception(MSG_INCORRECT_FORMAT);
            }
            
            command.setTaskRepeatEndDate(endDate);
            
            return command;
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
    }
    
    private static Boolean[] createIsDayTrue(String[] list) {
	Boolean[] isDayTrue = new Boolean[7];
	String[] wk = {"mon","tue","wed","thu","fri","sat","sun"};
	ArrayList<String> week = new ArrayList<String>(Arrays.asList(wk));
	Arrays.fill(isDayTrue, false);
	String day = "";
	int index = -1;
	for(int i=0; i<list.length; i++) {
	    index = week.indexOf(list[i]);
	    if(index != -1) {
		isDayTrue[index] = true;
	    }
	}
	return isDayTrue;
    }
    
    // ================================================================
    // Create cancel recurrence using cancel repeat command method
    // ================================================================
    private static Command initCancelRepeatCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.CANCEL_REPEAT);
            return command;
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger("exception: " + MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }

    }

    // ================================================================
    // Create undo command method
    // ================================================================
    private static Command initUndoCommand() throws Exception {
        try {
            Command command = new Command(Command.Type.UNDO);
            return command;
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } 
    }

    // ================================================================
    // Create redo command method
    // ================================================================
    private static Command initRedoCommand() throws Exception {
        try {
            Command command = new Command(Command.Type.REDO);
            return command;
        } catch (NullPointerException e) {
            addToParserLogger("exception: " + MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        }  
    }

    // ================================================================
    // Create invalid command method
    // ================================================================

    private static Command initInvalidCommand() {
        return new Command(Command.Type.INVALID);
    }

    // ================================================================
    // Create exit command method
    // ================================================================

    private static Command initExitCommand() {

        return new Command(Command.Type.EXIT);
    }

}
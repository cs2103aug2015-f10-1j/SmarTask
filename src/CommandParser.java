import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.joestelmach.natty.*;

/**
 * CommandParser parses user's input to create Command objects that have the
 * appropriate fields initialised. For example, the "add" command requires the
 * taskDetail and taskTime field to be initialised.
 * 
 * @author Bobby Lin
 *
 */

public class CommandParser {

    private static final int SIZE_0 = 0;
    private static final String DATEFORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    private static final int SIZE_2 = 2;
    private static final int SIZE_1 = 1;
    private static final int SIZE_3 = 3;
    private static final String YEAR = "year";
    private static final String REGEX_MINUS = "-";
    private static final String MONTH = "month";
    private static final String WEEK = "week";
    private static final String DAY = "day";
    private static final String[] WEEK_ARRAY = {"mon","tue","wed","thu","fri","sat","sun"};
    private static final String REPEAT = "repeat";
    private static final String SMALL_CAP_F = "f";
    private static final String SMALL_CAP_E = "e";
    private static final String SMALL_CAP_D = "d";
    private static final String SMALL_CAP_R = "r";
    private static final String FLOATING = "floating";
    private static final String EVENT = "event";
    private static final String DEADLINE = "deadline";
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
    private static final String USER_COMMAND_REPEAT = REPEAT;
    private static final String USER_COMMAND_STOP_REPEAT = "stop";
    private static final String USER_COMMAND_COMPLETE = "complete";
    private static final String USER_COMMAND_SET_FILEPATH = "setfilepath";
    private static final String USER_COMMAND_EXIT = "exit";

    private static final String MSG_COMMAND = "command: ";
    private static final String MSG_PARSER_LOG= "==============CommandParser Log=================";
    private static final String MSG_INCORRECT_FORMAT = "exception: Error in attributes: Ensure attributes are entered in valid format";
    private static final String MSG_NULL_POINTER = "exception: Error: Null pointer";
    private static ArrayList<String> parserLogger = new ArrayList<String>();
    private static String[] arr = {"Jan", "Feb", "Mar", "Apr", "May","Jun","Jul","Aug", "Sep","Oct","Nov", "Dec"};
    private static final ArrayList<String> monthInString = new ArrayList<String>(Arrays.asList(arr));

    public static Command parse(String userInput) throws Exception {
        addToParserLogger(MSG_COMMAND + userInput);
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

            case USER_COMMAND_STOP_REPEAT :
                command = initStopRepeatCommand(arguments);
                break;

            case USER_COMMAND_UNDO :
                command = initUndoCommand();
                break;

            case USER_COMMAND_REDO :
                command = initRedoCommand();
                break;

            case USER_COMMAND_SET_FILEPATH :
                command = initSetFilePathCommand(arguments);
                break;

            case USER_COMMAND_EXIT :
                command = initExitCommand();
                break;

            default :
                command = initInvalidCommand();
        }

        assert command != null : MSG_NULL_POINTER; 

        return command;
    }

    private static void addToParserLogger(String userInput) {
        DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT_YYYY_MM_DD_HH_MM_SS);
        Date date = new Date();
        parserLogger.add(dateFormat.format(date) + REGEX_WHITESPACES + userInput );
    }

    private static ArrayList<String> splitStringIntoTwoParts(String arguments) throws Exception {
        assert arguments !=null : MSG_NULL_POINTER;
        try {
            String[] strArray = arguments.trim().split(REGEX_WHITESPACES, TWOPARTS);
            return new ArrayList<String>(Arrays.asList(strArray));
        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
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
        ArrayList<String> list = new ArrayList<>();
        list.add(parameters.get(POSITION_FIRST_PARAM_ARGUMENT));
        return list;
    }

    private static String getUserCommand(ArrayList<String> parameters) {
        return parameters.get(POSITION_ZERO_PARAM_ARGUMENT);
    }

    public static void viewCommandParserLog() {
        System.out.println(MSG_PARSER_LOG);
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
            com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
            List<DateGroup> dateGroup = parseDate.parse(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));

            if(dateGroup.size() == SIZE_0) {
                command.setTaskType(FLOATING);
                command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
            }
            else {
                DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
                List<Date> dates = group.getDates();
                String[] date = dates.toString().replace("[", "").replace("]", "").split(",\\s");
                if(date.length == SIZE_1) {
                    command.setTaskType(DEADLINE);
                    command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT).split("by")[0].trim());
                    command.setTaskDeadline(date[0]);
                }
                else if(date.length == SIZE_2) {
                    command.setTaskType(EVENT);
                    command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT).split("on")[0].trim());
                    command.setTaskEventStart(date[POSITION_ZERO_PARAM_ARGUMENT]);
                    command.setTaskEventEnd(date[POSITION_FIRST_PARAM_ARGUMENT]);
                }
            }

            return command;

        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER);
            
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
            
        } catch (NumberFormatException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
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
            if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_D)) {
                command.setTaskType(DEADLINE);
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_E)) {
                command.setTaskType(EVENT);
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_F)) {
                command.setTaskType(FLOATING);
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_R)) {
                command.setTaskType(REPEAT);
            } 
            else {
                throw new Exception(MSG_NULL_POINTER);
            }

            command.setTaskID(extractTaskIdParam(arguments));
            if(command.getTaskID()<=0) {
                throw new Exception(MSG_NULL_POINTER);
            }
            return command;
            
        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
            
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
            
        } catch (NumberFormatException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
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
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
            
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
            
        } catch (NumberFormatException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
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

            if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_D)) {
                command.setTaskType(DEADLINE);
                command.setTaskID(extractTaskIdParam(parameters));
                command.setTaskDeadline(getDeadline(parameters.get(POSITION_FIRST_PARAM_ARGUMENT))[0]);
                command.setTaskDescription(extractDescriptionParam(parameters));   
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_E)) {
                command.setTaskType(EVENT);
                command.setTaskID(extractTaskIdParam(parameters));
                command.setTaskEventDate(getDate(parameters.get(POSITION_FIRST_PARAM_ARGUMENT))[0]);
                command.setTaskEventTime(getDuration(parameters.get(POSITION_FIRST_PARAM_ARGUMENT))[0]);
                command.setTaskDescription(extractDescriptionParam(parameters));
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_F)) {
                command.setTaskType(FLOATING);
                command.setTaskID(extractTaskIdParam(parameters));
                command.setTaskDescription(parameters.get(POSITION_FIRST_PARAM_ARGUMENT));
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_R)) {
                command.setTaskType(REPEAT);
                command.setTaskID(extractTaskIdParam(parameters));
                
                
            }
            else {
                addToParserLogger(MSG_INCORRECT_FORMAT);
                throw new Exception(MSG_INCORRECT_FORMAT);
            }

            return command;

        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
            
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
            
        } catch (NumberFormatException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
            
        }
    }

    private static int extractTaskIdParam(ArrayList<String> parameters) {
	return Integer.parseInt(parameters.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[a-zA-Z]", ""));
    }

    private static String extractDescriptionParam(ArrayList<String> parameters) {
	return parameters.get(POSITION_FIRST_PARAM_ARGUMENT).replaceAll("[0-9]{1,2}:[0-9]{2}", "")
	        .replaceAll("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d", "").trim();
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

            if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_D)) {
                command.setTaskType(DEADLINE);
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_E)) {
                command.setTaskType(EVENT);
            }
            else if(alphaIndex.toLowerCase().startsWith(SMALL_CAP_F)) {
                command.setTaskType(FLOATING);
            }
            command.setTaskID(extractTaskIdParam(arguments));
            if(command.getTaskID()<=0) {
                throw new Exception(MSG_NULL_POINTER);
            }

            return command;

        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        } catch (NumberFormatException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
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
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
    }

    // ================================================================
    // Create recurrence using repeat command method
    // ================================================================
    private static Command initRepeatCommand(ArrayList<String> arguments) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Command command = new Command(Command.Type.REPEAT);
            String[] param = arguments.get(POSITION_ZERO_PARAM_ARGUMENT).split("-start");
            command.setTaskDescription(param[POSITION_ZERO_PARAM_ARGUMENT].trim());

            param = param[POSITION_FIRST_PARAM_ARGUMENT].split("-every");
            String dateTimeLine = param[POSITION_ZERO_PARAM_ARGUMENT].trim();

            ArrayList<String> time = extractTime(dateTimeLine);
            command.setRepeatStartTime(time.get(0));
            command.setRepeatEndTime(time.get(1));

            ArrayList<Date> date = extractNattyTwoDates(dateTimeLine);
            command.setDateAdded(date.get(0));

            String inputOfRecurrence = param[POSITION_FIRST_PARAM_ARGUMENT].trim();

            if(inputOfRecurrence.contains("day")) {
                command.setRepeatType("day");
                String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
                command.setDayInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
                remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split("-until");

                if(remainingParam.length > 1) {
                    ArrayList<Date> line = extractNattyTwoDates(remainingParam[POSITION_FIRST_PARAM_ARGUMENT].trim());
                    command.setRepeatUntil(line.get(0));
                }
                else {
                    command.setRepeatUntil(dateFormat.parse("01/12/9999"));
                }
            }

            else if (inputOfRecurrence.contains("week")){
                command.setRepeatType("week");
                String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
                command.setWeekInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
                
                remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split("-on");
                remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split("-until");
                
                String line = remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim();
                command.setIsDaySelected(setSelectedDay(line, new Boolean[7]));
                command.setGetDaySelectedString();
                
                if(remainingParam.length > SIZE_1) {
                    command.setRepeatUntil(extractNattyTwoDates(remainingParam[POSITION_FIRST_PARAM_ARGUMENT].trim()).get(0));
                }
                else {
                    command.setRepeatUntil(dateFormat.parse("01/12/9999"));
                }
            }

            else if (inputOfRecurrence.contains("month")) {
                command.setRepeatType("month");
                String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
                command.setMonthInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
                
                remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split("-on");
                remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split("-until");
                
                String line = remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim();
                command.setMonthRepeatPattern(line);

                if(remainingParam.length > SIZE_1) {
                    command.setRepeatUntil(extractNattyTwoDates(remainingParam[POSITION_FIRST_PARAM_ARGUMENT].trim()).get(0));
                }
                else {
                    command.setRepeatUntil(dateFormat.parse("01/12/9999"));
                }
            }

            else if (inputOfRecurrence.contains("year")) {
                command.setRepeatType("year");
                String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
                command.setDayInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
                remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split("-until");
                
                if(remainingParam.length > SIZE_1) {
                    ArrayList<Date> line = extractNattyTwoDates(remainingParam[POSITION_FIRST_PARAM_ARGUMENT].trim());
                    command.setRepeatUntil(line.get(0));
                }
                
                else {
                    command.setRepeatUntil(dateFormat.parse("01/12/9999"));
                }
            }

            return command;

        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 

        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);

        } catch (NumberFormatException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);

        }
    }

    private static Boolean[] setSelectedDay(String line, Boolean[] isSelected) throws Exception {
        String[] days = line.split(",");
        Arrays.fill(isSelected, false);
        int dayNum;
        for(int i = 0; i <days.length; i++) {
            days[i] = days[i].trim();
            dayNum = getDayIndex(days[i]);
            isSelected[dayNum] = true;
        }
        return isSelected;
    }

    private static int getDayIndex(String day) throws Exception {
        if(day.toLowerCase().equals("mon")) {
            return 0;
        }
        else if(day.toLowerCase().equals("tue")) {
            return 1;
        }
        else if(day.toLowerCase().equals("wed")) {
            return 2;
        }
        else if(day.toLowerCase().equals("thu")) {
            return 3;
        }
        else if(day.toLowerCase().equals("fri")) {
            return 4;
        }
        else if(day.toLowerCase().equals("sat")) {
            return 5;
        }
        else if(day.toLowerCase().equals("sun")) {
            return 6;
        }
        else {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }
        
    }

    private static ArrayList<Date> extractNattyTwoDates(String dateTime) throws Exception {
        try {
            ArrayList<Date> dateStartEnd = new ArrayList<Date>(3);
            com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
            List<DateGroup> dateGroup = parseDate.parse(dateTime);
            DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            List<Date> dates = group.getDates();

            String[] arr = dates.toString().replace("[", "").replace("]", "").split(",\\s");
            String[] param = arr[0].split(" ");

            int mth = monthInString.indexOf(param[1]) + 1;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateStartEnd.add(dateFormat.parse(param[2] + "/" + mth + "/" + param[5]));
            return dateStartEnd;
            
        } catch (ParseException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
        }

    }

    private static ArrayList<String> extractTime(String dateTime) throws Exception {
        ArrayList<String> dateStartEnd = new ArrayList<String>(3);

        com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
        List<DateGroup> dateGroup = parseDate.parse(dateTime);
        DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
        List<Date> dates = group.getDates();

        String[] arr = dates.toString().replace("[", "").replace("]", "").split(",\\s");
        String[] start = arr[0].split(" ");
        dateStartEnd.add(start[3]);

        String[] end = arr[1].split(" ");
        dateStartEnd.add(end[3]);

        return dateStartEnd;

    }

    // ================================================================
    // Create stop recurrence command method
    // ================================================================
    private static Command initStopRepeatCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.STOP_REPEAT);
            String[] param = arguments.get(POSITION_ZERO_PARAM_ARGUMENT).split(REGEX_WHITESPACES, SIZE_2);
            command.setTaskID(Integer.parseInt(param[POSITION_ZERO_PARAM_ARGUMENT]));
            String[] dateParam = param[POSITION_FIRST_PARAM_ARGUMENT].split(",");
            ArrayList<Date> listOfDates = extractDatesIntoArrayList(dateParam);
            command.setStopRepeat(listOfDates);
            
            return command;
            
        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
            
        } catch (IndexOutOfBoundsException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
            
        } catch (NumberFormatException e) {
            addToParserLogger(MSG_INCORRECT_FORMAT);
            throw new Exception(MSG_INCORRECT_FORMAT);
            
        }

    }

    private static ArrayList<Date> extractDatesIntoArrayList(String[] dateParam) throws ParseException {
        ArrayList<Date> list = new ArrayList<Date>();
        com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
        List<DateGroup> dateGroup;
        DateGroup group;
        List<Date> dates;
        
        for(int i =0; i< dateParam.length; i++) {
            dateGroup = parseDate.parse(dateParam[i].trim());
            group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            dates = group.getDates();

            String[] param = dates.toString().replace("[", "").replace("]", "").split(" ");
            int mth = monthInString.indexOf(param[1]) + 1;
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            list.add(dateFormat.parse(param[2] + "/" + mth + "/" + param[5]));
        }
        
        return list;
    }

    // ================================================================
    // Create undo command method
    // ================================================================
    private static Command initUndoCommand() throws Exception {
        try {
            Command command = new Command(Command.Type.UNDO);
            return command;
        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
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
            addToParserLogger(MSG_NULL_POINTER);
            throw new Exception(MSG_NULL_POINTER); 
        }  
    }

    // ================================================================
    // Create setFilePath command method
    // ================================================================
    private static Command initSetFilePathCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.SETFILEPATH);  
            command.setFilePath(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
            return command;
        } catch (NullPointerException e) {
            addToParserLogger(MSG_NULL_POINTER);
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
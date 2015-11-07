import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.internal.Throwables;

import com.joestelmach.natty.*;

/**
 * CommandParser parses user's input to create Command objects that have the
 * appropriate fields initialised. For example, the "add" command requires the
 * taskDetail and taskTime field to be initialised.
 * 
 * @author A0108235M-Sebastian Quek (Collate Project)
 */

public class CommandParser {

    private static final String KEYWORD_BY = "-by";
    private static final int SIZE_0 = 0;
    private static final int SIZE_1 = 1;
    private static final int SIZE_2 = 2;
    private static final int SIZE_3 = 3;
    private static final int POSITION_ZERO_PARAM_ARGUMENT = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    private static final int TWOPARTS = 2;
    private static final int WEEKSIZE = 8;
    private static final String REGEX_WHITESPACES = " ";

    private static final String DATEFORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    private static final String INFINITY_DATE = "12/12/9999";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static String[] arr = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    private static final ArrayList<String> monthInString = new ArrayList<String>(Arrays.asList(arr));
    private static ArrayList<String> parserLogger = new ArrayList<String>();

    private static final String SMALL_CAP_F = "f";
    private static final String SMALL_CAP_D = "d";
    private static final String SMALL_CAP_E = "e";
    private static final String SMALL_CAP_R = "r";
    private static final String FLOATING = "floating";
    private static final String EVENT = "event";
    private static final String DEADLINE = "deadline";
    private static final String REPEAT = "repeat";
    private static final String KEYWORD_UNTIL = "-until";
    private static final String KEYWORD_EVERY = "-every";
    private static final String KEYWORD_START = "-start";
    private static final String KEYWORD_ON = "-on";

    private static final String USER_COMMAND_ADD = "add";
    private static final String USER_COMMAND_DELETE = "delete";
    private static final String USER_COMMAND_UPDATE = "update";
    private static final String USER_COMMAND_VIEW = "view";
    private static final String USER_COMMAND_SEARCH = "search";
    private static final String USER_COMMAND_UNDO = "undo";
    private static final String USER_COMMAND_REDO = "redo";
    private static final String USER_COMMAND_REPEAT = "repeat";
    private static final String USER_COMMAND_STOP_REPEAT = "stop";
    private static final String USER_COMMAND_COMPLETE = "complete";
    private static final String USER_COMMAND_SET_FILEPATH = "setfilepath";
    private static final String USER_COMMAND_EXIT = "exit";

    private static final String MSG_COMMAND = "command: ";
    private static final String MSG_PARSER_LOG_HEADER = "=====CommandParser Log=====";
    private static final String MSG_INVALID_FORMAT = "Command format is invalid";
    private static final String MSG_NULL_POINTER = "Error: Null pointer";

    public static Command parse(String userInput) throws Exception {
        addToParserLogger(userInput);
        return initCommand(userInput);
    }

    private static Command initCommand(String userInput) throws Exception {
        Command command;
        ArrayList<String> parameters = getUserCommandAndArguments(userInput);
        String userCommand = getUserCommand(parameters);
        ArrayList<String> arguments = getUserArguments(parameters);

        switch (userCommand.toLowerCase()) {
            case USER_COMMAND_ADD :
                command = initAddCommand(arguments);
                break;

            case USER_COMMAND_DELETE :
                command = initDeleteCommand(arguments);
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

            case USER_COMMAND_VIEW :
                command = initViewDateCommand(arguments);
                break;

            case USER_COMMAND_EXIT :
                command = initExitCommand();
                break;

            default:
                command = initInvalidCommand();
                break;
        }

        assert command != null : MSG_NULL_POINTER;
        return command;
    }

    /******************************************************************/
    /******************************************************************
     Main command methods
     ******************************************************************/
    /******************************************************************/

    // ================================================================
    // Create add command method
    // ================================================================

    private static Command initAddCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.ADD);
            setAddCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create delete command method
    // ================================================================

    private static Command initDeleteCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.DELETE);
            setDeleteCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create update command method
    // ================================================================

    private static Command initUpdateCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.UPDATE);   
            setUpdateCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create complete command method
    // ================================================================

    private static Command initCompleteCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.COMPLETE);
            setCompleteCommandAttribute(arguments, command);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create search command method
    // ================================================================

    private static Command initSearchCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.SEARCH);
            setSearchCommandAttribute(arguments, command);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create repeat command method for recurrence
    // ================================================================

    private static Command initRepeatCommand(ArrayList<String> arguments) throws Exception {
        try {     
            Command command = new Command(Command.Type.REPEAT);          
            setRepeatCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create stop recurrence command method
    // ================================================================

    private static Command initStopRepeatCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.STOP_REPEAT);
            setStopRepeatCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create undo command method
    // ================================================================

    private static Command initUndoCommand() throws Exception {
        try {
            return new Command(Command.Type.UNDO);
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create redo command method
    // ================================================================

    private static Command initRedoCommand() throws Exception {
        try {
            return new Command(Command.Type.REDO);
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create setFilePath command method
    // ================================================================

    private static Command initSetFilePathCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.SETFILEPATH);
            setFilePathCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create invalid command method
    // ================================================================

    private static Command initInvalidCommand() {
        return new Command(Command.Type.INVALID);
    }

    // ================================================================
    // Create view command method
    // ================================================================

    private static Command initViewDateCommand(ArrayList<String> arguments) throws Exception {
        try {
            Command command = new Command(Command.Type.VIEW);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // ================================================================
    // Create exit command method
    // ================================================================

    private static Command initExitCommand() {
        return new Command(Command.Type.EXIT);
    }

    /******************************************************************/
    /******************************************************************
     Methods to set command attributes
     ******************************************************************/
    /******************************************************************/

    // ================================================================
    // Set add command attributes
    // ================================================================

    private static void setAddCommandAttribute(Command command, ArrayList<String> arguments) throws Exception {
        setAttributeForAddType(command, arguments);
    }

    // ================================================================
    // Set delete command attributes
    // ================================================================

    private static void setDeleteCommandAttribute(Command command, ArrayList<String> arguments) throws Exception {
        String taskType = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
        setCommandTaskType(command, taskType);
        setCommandTaskID(command, arguments);
    }

    // ================================================================
    // Set update command attributes
    // ================================================================

    private static void setUpdateCommandAttribute(Command command, ArrayList<String> arguments) throws Exception {    
        String type = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
        ArrayList<String> parameters = splitStringIntoTwoWithWhiteSpaces(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
        setAttributeForUpdateType(command, type, parameters);
    }

    // ================================================================
    // Set complete command attributes
    // ================================================================

    private static void setCompleteCommandAttribute(ArrayList<String> arguments, Command command) throws Exception {
        String taskType = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
        setCommandTaskType(command, taskType);    
        setCommandTaskID(command, arguments);
    }

    // ================================================================
    // Set search command attributes
    // ================================================================

    private static void setSearchCommandAttribute(ArrayList<String> arguments, Command command) {
        setSearchKeyword(command, arguments);
    }

    // ================================================================
    // Set set filepath command attributes
    // ================================================================

    private static void setFilePathCommandAttribute(Command command, ArrayList<String> arguments) throws Exception {
        setFilePath(command, arguments);
    }

    // ================================================================
    // Set repeat command attributes
    // ================================================================

    private static void setRepeatCommandAttribute(Command command, ArrayList<String> arguments) 
            throws Exception{

        String[] param = arguments.get(POSITION_ZERO_PARAM_ARGUMENT).split(KEYWORD_START);
        command.setTaskDescription(param[POSITION_ZERO_PARAM_ARGUMENT].trim());

        param = param[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_EVERY);
        String dateTimeLine = param[POSITION_ZERO_PARAM_ARGUMENT].trim();

        setStartAndEndTime(command, dateTimeLine);
        setDateAdded(command, dateTimeLine);
        setStopRepeatToInfinityDate(command);
        setAttributeForRepeatType(command, param[POSITION_FIRST_PARAM_ARGUMENT].trim());
    }

    // ================================================================
    // Set stop repeat command attributes
    // ================================================================

    private static void setStopRepeatCommandAttribute(Command command, ArrayList<String> arguments) throws Exception {
        try {
            String[] param = arguments.get(POSITION_ZERO_PARAM_ARGUMENT).split(REGEX_WHITESPACES, SIZE_2);
            setCommandTaskType(command, param[POSITION_ZERO_PARAM_ARGUMENT]);    
            setCommandTaskID(command, param);
            String[] dateParam = param[POSITION_FIRST_PARAM_ARGUMENT].split(",");
            setStopRepeatDate(command, dateParam);
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    /******************************************************************/
    /******************************************************************
     Auxiliary methods to support set command attributes
     ******************************************************************/
    /*******************************************************************/

    // ================================================================
    // Setters of attribute
    // ================================================================

    private static void setAttributeForAddType(Command command, 
            ArrayList<String> arguments) throws Exception {

        com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
        List<DateGroup> dateGroup = parseDate.parse(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));

        if (dateGroup.size() == SIZE_0) {
            checkKeywordUsed(arguments);
            command.setTaskType(FLOATING);
            setFloatDescription(command, arguments);
        } else {
            DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            List<Date> dates = group.getDates();
            String[] date = dates.toString().replace("[", "").replace("]", "").split(",\\s");

            if (date.length == SIZE_1) {
                command.setTaskType(DEADLINE);
                setDeadlineDescription(command, arguments);
                command.setTaskDeadline(checkForNull(date[POSITION_ZERO_PARAM_ARGUMENT]));
            } else if (date.length == SIZE_2) {
                command.setTaskType(EVENT);
                setEventDescription(command, arguments);    
                command.setTaskEventStart(checkForNull(date[POSITION_ZERO_PARAM_ARGUMENT]));
                command.setTaskEventEnd(checkForNull(date[POSITION_FIRST_PARAM_ARGUMENT]));
            }
        }
    }

    private static String checkForNull(String string) throws Exception {
        if(string == null) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
        return string;
    }

    private static void checkKeywordUsed(ArrayList<String> arguments) throws Exception {
        if (isKeywordUsed(arguments)) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static boolean isKeywordUsed(ArrayList<String> arguments) {
        return arguments.get(POSITION_ZERO_PARAM_ARGUMENT).contains(KEYWORD_ON) 
                || arguments.get(POSITION_ZERO_PARAM_ARGUMENT).contains(KEYWORD_BY)||
                arguments.get(POSITION_ZERO_PARAM_ARGUMENT).contains(KEYWORD_UNTIL)||
                arguments.get(POSITION_ZERO_PARAM_ARGUMENT).contains(KEYWORD_START)||
                arguments.get(POSITION_ZERO_PARAM_ARGUMENT).contains(KEYWORD_EVERY);
    }

    private static void setFloatDescription(Command command, ArrayList<String> arguments) throws Exception {
        if(isNotFloatDescription(arguments)) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        } else {
            command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
        }
    }

    private static void setEventDescription(Command command, ArrayList<String> arguments) throws Exception {
        if (isNotEventDescription(arguments)) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        } else {
            command.setTaskDescription(arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                    .split(KEYWORD_ON)[POSITION_ZERO_PARAM_ARGUMENT].trim());
        }
    }

    private static boolean isNotEventDescription(ArrayList<String> arguments) {
        return arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                .split(KEYWORD_ON)[POSITION_ZERO_PARAM_ARGUMENT].trim().isEmpty();
    }

    private static void setDeadlineDescription(Command command, ArrayList<String> arguments) throws Exception {
        String param = arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                .split(KEYWORD_BY)[POSITION_ZERO_PARAM_ARGUMENT].trim();
        ArrayList<String> list = new ArrayList<String>();
        list.add(param);
        checkKeywordUsed(list);
        
        if (isNotDeadlineDescription(arguments)) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        } else {
            command.setTaskDescription(param);
        }
    }

    private static boolean isNotDeadlineDescription(ArrayList<String> arguments) {
        return arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                .split(KEYWORD_BY)[POSITION_ZERO_PARAM_ARGUMENT].trim().isEmpty();
    }

    private static boolean isNotFloatDescription(ArrayList<String> arguments) {
        return arguments.get(POSITION_ZERO_PARAM_ARGUMENT).trim().isEmpty();
    }

    private static void setCommandTaskID(Command command, String[] param) {
        command.setTaskID(Integer.parseInt(param[POSITION_ZERO_PARAM_ARGUMENT].replaceAll("[a-zA-Z]", "")));
    }

    private static void setStopRepeatDate(Command command, ArrayList<Date> list) throws Exception {
        try {
            command.setStopRepeat(list);
            command.setStopRepeatInString();
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }

    }

    private static void setStopRepeatToInfinityDate(Command command) throws ParseException, Exception {
        ArrayList<Date> list = new ArrayList<Date>();
        list.add(dateFormat.parse(INFINITY_DATE));
        setStopRepeatDate(command, list);
    }

    private static void setDateAdded(Command command, String dateTimeLine) throws Exception {
        ArrayList<Date> date = extractNattyTwoDates(dateTimeLine);
        command.setDateAdded(date.get(0));
    }

    private static void setStartAndEndTime(Command command, String dateTimeLine) throws Exception {
        ArrayList<String> time = extractTime(dateTimeLine);
        command.setRepeatStartTime(time.get(0));
        command.setRepeatEndTime(time.get(1));
    }

    private static void setAttributeForRepeatType(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        if (inputOfRecurrence.contains("day")) {
            setAttributeForDay(command, inputOfRecurrence);
        } else if (inputOfRecurrence.contains("week")) {
            setAttributeForWeek(command, inputOfRecurrence);
        } else if (inputOfRecurrence.contains("month")) {
            setAttributeForMonth(command, inputOfRecurrence);
        } else if (inputOfRecurrence.contains("year")) {
            setAttributeForYear(command, inputOfRecurrence);
        }
    }

    private static void setAttributeForYear(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        command.setRepeatType("year");
        String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
        command.setYearInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
        remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_UNTIL);
        setRepeatUntil(command, remainingParam);
    }

    private static void setAttributeForMonth(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        command.setRepeatType("month");
        String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
        command.setMonthInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
        remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_UNTIL);
        setRepeatUntil(command, remainingParam);
    }

    private static void setAttributeForWeek(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        command.setRepeatType("week");
        String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
        command.setWeekInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
        remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_ON);
        remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_UNTIL);
        String line = remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim();
        command.setIsDaySelected(setSelectedDay(line));
        command.setDaySelectedString();
        setRepeatUntil(command, remainingParam);
    }

    private static void setAttributeForDay(Command command, String inputOfRecurrence) throws Exception, ParseException {
        command.setRepeatType("day");
        String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
        command.setDayInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
        remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_UNTIL);
        setRepeatUntil(command, remainingParam);
    }

    private static void setStopRepeatDate(Command command, String[] dateParam) throws Exception {
        ArrayList<Date> listOfDates;
        try {
            listOfDates = extractDatesIntoArrayList(dateParam);
            command.setStopRepeat(listOfDates);
            command.setStopRepeatInString();
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static void setSearchKeyword(Command command, ArrayList<String> arguments) {
        ArrayList<String> keywords = splitStringBySpace(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));
        command.setSearchKeyword(keywords);
    }

    private static void setCommandTaskID(Command command, ArrayList<String> arguments) throws Exception {
        command.setTaskID(extractTaskIdParam(arguments));
    }

    private static void setCommandTaskType(Command command, String taskType) throws Exception {
        if (isDeadlineType(taskType)) {
            command.setTaskType(DEADLINE);
        } else if (isEventType(taskType)) {
            command.setTaskType(EVENT);
        } else if (isFloatingType(taskType)) {
            command.setTaskType(FLOATING);
        } else if (isRepeatType(taskType)) {
            command.setTaskType(REPEAT);
        } else {
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static void setRepeatUntil(Command command, String[] remainingParam)
            throws Exception, ParseException {
        if (remainingParam.length > SIZE_1) {
            ArrayList<Date> line = extractNattyTwoDates(remainingParam[POSITION_FIRST_PARAM_ARGUMENT].trim());
            command.setRepeatUntil(line.get(POSITION_ZERO_PARAM_ARGUMENT));
        } else {
            command.setRepeatUntil(dateFormat.parse(INFINITY_DATE));
        }
    }

    private static void setFilePath(Command command, ArrayList<String> arguments) throws Exception {
        String line = arguments.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("/", "\\");
        if(checkFilePath(line)) {
            command.setFilePath(line);
        } else {
            System.out.println(line);
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        };                
        
    }
    
    private static boolean checkFilePath(String line) {
        return Pattern.compile("([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\?[a-zA-Z0-9._-].(?i)(txt)").matcher(line).find();
    }

    // ================================================================
    // Set attributes of individual update command
    // ================================================================

    

    private static void setAttributeForUpdateType(Command command, String type, ArrayList<String> parameters)
            throws Exception{
        if (isDeadlineType(type)) {
            updateDeadlineTask(command, parameters);
        } else if (isEventType(type)) {
            updateEventTask(command, parameters);
        } else if (isFloatingType(type)) {
            updateFloatingTask(command, parameters);
        } else if (isRepeatType(type)) {
            updateRepeatTask(command, parameters);
        } else {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static void updateFloatingTask(Command command, ArrayList<String> parameters) 
            throws Exception {
        command.setTaskType(FLOATING);
        setCommandTaskID(command, parameters);
        command.setTaskDescription(parameters.get(POSITION_FIRST_PARAM_ARGUMENT));
    }

    private static void updateEventTask(Command command, ArrayList<String> parameters) 
            throws Exception {
        command.setTaskType(EVENT);
        setCommandTaskID(command, parameters);

        String line = parameters.get(POSITION_FIRST_PARAM_ARGUMENT);

        if (line.contains("-on")) {
            String[] param = parameters.get(POSITION_FIRST_PARAM_ARGUMENT).split("-on");
            if (param.length == 2) {
                if (!param[0].trim().isEmpty()) {
                    command.setTaskDescription(param[0].trim());
                }
                ArrayList<String> list = extractTime(param[1]);
                command.setTaskEventStart(list.get(0));
                command.setTaskEventEnd(list.get(1));
            } else {
                ArrayList<String> list = extractTime(param[1]);
                command.setTaskEventStart(list.get(0));
                command.setTaskEventEnd(list.get(1));
            }
        } else {
            command.setTaskDescription(line.trim());
        }

    }

    private static String getDateInString(String string) {
        com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
        List<DateGroup> dateGroup = parseDate.parse(string);
        DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
        List<Date> dates = group.getDates();
        String[] arr = dates.toString().replace("[", "").replace("]", "").split(",\\s");
        return arr[0];
    }

    private static void updateDeadlineTask(Command command, ArrayList<String> parameters) throws Exception {
        command.setTaskType(DEADLINE);
        setCommandTaskID(command, parameters);
        String line = parameters.get(POSITION_FIRST_PARAM_ARGUMENT);

        if (line.contains(KEYWORD_BY)) {
            String date;
            String[] param = parameters.get(POSITION_FIRST_PARAM_ARGUMENT).split(KEYWORD_BY);
            if (param.length == 2) {
                if (!param[0].trim().isEmpty()) {
                    command.setTaskDescription(param[0].trim());
                }
                date =  getDateInString(param[1]);
                command.setTaskDeadline(date);
            } else {
                date =  getDateInString(param[1]);
                command.setTaskDeadline(date);
            }
        } else {
            command.setTaskDescription(line.trim());
        }

    }

    private static void updateRepeatTask(Command command, ArrayList<String> parameters)
            throws ParseException, Exception {
        command.setTaskType(REPEAT);
        String[] line;

        setCommandTaskID(command, parameters);
        String input = parameters.get(POSITION_FIRST_PARAM_ARGUMENT);

        if (input.contains(KEYWORD_UNTIL)) {
            line = input.split(KEYWORD_UNTIL);
            checkAttributeValidity(line);

            com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
            List<DateGroup> dateGroup = parseDate.parse(line[POSITION_FIRST_PARAM_ARGUMENT]);
            DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            List<Date> dates = group.getDates();

            String[] arr = dates.toString().replace("[", "").replace("]", "").split(",\\s");
            String[] param = arr[0].split(" ");
            int mth = monthInString.indexOf(param[1]) + 1;

            command.setRepeatUntil(dateFormat.parse(param[2] + "/" + mth + "/" + param[5]));
            input = line[0];
        }

        if (input.contains(KEYWORD_ON)) {
            line = input.split(KEYWORD_ON);
            checkAttributeValidity(line);
            String paramArr = line[POSITION_FIRST_PARAM_ARGUMENT].trim();
            command.setIsDaySelected(setSelectedDay(paramArr));
            command.setDaySelectedString();
            input = line[0];
        }

        if (input.contains(KEYWORD_EVERY)) {
            line = input.split(KEYWORD_EVERY);
            checkAttributeValidity(line);
            String[] remainingParam = line[1].trim().split(REGEX_WHITESPACES, SIZE_2);
            String type = remainingParam[1];

            if (type.equals("day")) {
                command.setDayInterval(remainingParam[0]);
                command.setRepeatType("day");
            } else if (type.equals("week")) {
                command.setWeekInterval(remainingParam[0]);
                command.setRepeatType("week");
            } else if (type.equals("month")) {
                command.setMonthInterval(remainingParam[0]);
                command.setRepeatType("month");
            } else if (type.equals("year")) {
                command.setYearInterval(remainingParam[0]);
                command.setRepeatType("year");
            }

            input = line[0];
        }

        if (input.contains(KEYWORD_START)) {
            line = input.split(KEYWORD_START);
            checkAttributeValidity(line);
            String dateTimeLine = line[POSITION_FIRST_PARAM_ARGUMENT].trim();
            setStartAndEndTime(command, dateTimeLine);
            setDateAdded(command, dateTimeLine);
            input = line[0];
        }

        String param = input.trim();
        if(isDescription(param)) {
            if(!param.isEmpty()) {
                command.setTaskDescription(param);
            } 
        } 

    }

    private static void checkAttributeValidity(String[] line) throws Exception {
        if(hasOtherKeywords(line[POSITION_FIRST_PARAM_ARGUMENT])) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static boolean hasOtherKeywords(String string) {
        return string.contains(KEYWORD_UNTIL) || string.contains(KEYWORD_ON) ||
                string.contains(KEYWORD_EVERY) || string.contains(KEYWORD_START);
    }

    // ================================================================
    // Support checking of task type
    // ================================================================

    private static boolean isDescription(String param) {
        if (param.contains(KEYWORD_START)) {
            return false;
        } else if (param.contains(KEYWORD_EVERY)) {
            return false;
        } else if (param.contains(KEYWORD_ON)) {
            return false;
        } else if (param.contains(KEYWORD_UNTIL)) {
            return false;
        } 
        return true;
    }

    private static boolean isRepeatType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(SMALL_CAP_R);
    }

    private static boolean isFloatingType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(SMALL_CAP_F);
    }

    private static boolean isEventType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(SMALL_CAP_E);
    }

    private static boolean isDeadlineType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(SMALL_CAP_D);
    }

    private static int extractTaskIdParam(ArrayList<String> parameters) throws Exception {
        int id = Integer.parseInt(parameters.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll("[a-zA-Z]", "")) ;
        if (id <= 0) {
            throw new Exception(MSG_INVALID_FORMAT);
        }
        return id;
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
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static ArrayList<String> extractTime(String dateTime) throws Exception {
        ArrayList<String> dateStartEnd = new ArrayList<String>(SIZE_3);

        com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
        List<DateGroup> dateGroup = parseDate.parse(dateTime);
        DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
        List<Date> dates = group.getDates();

        String[] arr = dates.toString().replace("[", "").replace("]", "").split(",\\s");

        //String[] start = arr[POSITION_ZERO_PARAM_ARGUMENT].split(" ");
        //dateStartEnd.add(start[SIZE_3]);

        //String[] end = arr[POSITION_FIRST_PARAM_ARGUMENT].split(" ");
        //dateStartEnd.add(end[POSITION_THREE_PARAM_ARGUMENT]);

        dateStartEnd.add(arr[0]);
        dateStartEnd.add(arr[1]);

        return dateStartEnd;
    }

    private static ArrayList<Date> extractDatesIntoArrayList(String[] dateParam) throws ParseException {
        ArrayList<Date> list = new ArrayList<Date>();
        com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();
        List<DateGroup> dateGroup;
        DateGroup group;
        List<Date> dates;

        for (int i = 0; i < dateParam.length; i++) {
            dateGroup = parseDate.parse(dateParam[i].trim());
            group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            dates = group.getDates();
            String[] param = dates.toString().replace("[", "").replace("]", "").split(" ");
            int mth = monthInString.indexOf(param[SIZE_1]) + 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            list.add(dateFormat.parse(param[2] + "/" + mth + "/" + param[5]));
        }

        return list;
    }

    private static Boolean[] setSelectedDay(String line) throws Exception {
        String[] days = line.split(",");
        Boolean[] isSelected = new Boolean[WEEKSIZE];
        Arrays.fill(isSelected, false);
        int dayNum;
        isSelected[0] = null;

        for (int i = 0; i < days.length; i++) {
            days[i] = days[i].trim();
            dayNum = getDayIndex(days[i]);
            isSelected[dayNum] = true;
        }

        return isSelected;
    }

    private static int getDayIndex(String day) throws Exception {
        if (day.toLowerCase().equals("mon")) {
            return 2;
        } else if (day.toLowerCase().equals("tue")) {
            return 3;
        } else if (day.toLowerCase().equals("wed")) {
            return 4;
        } else if (day.toLowerCase().equals("thu")) {
            return 5;
        } else if (day.toLowerCase().equals("fri")) {
            return 6;
        } else if (day.toLowerCase().equals("sat")) {
            return 7;
        } else if (day.toLowerCase().equals("sun")) {
            return 1;
        } else {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    /******************************************************************/
    /******************************************************************
     Other auxiliary methods
     ******************************************************************/
    /******************************************************************/

    private static ArrayList<String> getUserCommandAndArguments(String userInput) throws Exception {
        ArrayList<String> parameters = splitStringIntoTwoWithWhiteSpaces(userInput);
        return parameters;
    }

    private static ArrayList<String> getUserArguments(ArrayList<String> parameters) {
        assert parameters != null;
        ArrayList<String> list = new ArrayList<>();
        if (parameters.size() == 1) {
            return list;
        }
        list.add(parameters.get(POSITION_FIRST_PARAM_ARGUMENT));
        return list;
    }

    private static String getUserCommand(ArrayList<String> parameters) {
        return parameters.get(POSITION_ZERO_PARAM_ARGUMENT);
    }

    private static ArrayList<String> splitStringBySpace(String arguments) {
        assert arguments != null : "Input is not entered correctly";
        String[] strArray = arguments.trim().split(REGEX_WHITESPACES);
        return new ArrayList<String>(Arrays.asList(strArray));
    }

    private static ArrayList<String> splitStringIntoTwoWithWhiteSpaces(String arguments) throws Exception {
        assert arguments != null : MSG_NULL_POINTER;
        try {
            String[] strArray = arguments.trim().split(REGEX_WHITESPACES, TWOPARTS);
            return new ArrayList<String>(Arrays.asList(strArray));
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static void addToParserLogger(String userInput) {
        DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT_YYYY_MM_DD_HH_MM_SS);
        Date date = new Date();
        parserLogger.add(dateFormat.format(date) + REGEX_WHITESPACES + MSG_COMMAND + userInput);
    }

    public static void viewCommandParserLog() {
        System.out.println(MSG_PARSER_LOG_HEADER);
        for (int i = 0; i < parserLogger.size(); i++) {
            System.out.println(parserLogger.get(i));
        }
    }

}
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import com.joestelmach.natty.*;

/**
 * CommandParser parses user's input to create Command objects that have the
 * appropriate fields initialised. For example, the "add" command requires the
 * taskDetail and taskTime field to be initialised.
 * 
 * @@author A0108235M-Sebastian Quek (Collate Project)
 */

public class CommandParser {

    // Main commands

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

    // Keyboard shortcuts for command

    private static final String USER_COMMAND_ADD_SHORTCUT = "ad";
    private static final String USER_COMMAND_DELETE_SHORTCUT = "de";
    private static final String USER_COMMAND_UPDATE_SHORTCUT = "ud";
    private static final String USER_COMMAND_COMPLETE_SHORTCUT = "cp";
    private static final String USER_COMMAND_SEARCH_SHORTCUT = "sh";
    private static final String USER_COMMAND_REPEAT_SHORTCUT = "rp";
    private static final String USER_COMMAND_STOP_REPEAT_SHORTCUT = "sr";
    private static final String USER_COMMAND_SET_FILEPATH_SHORTCUT = "sfp";
    private static final String USER_COMMAND_VIEW_SHORTCUT = "vd";

    // Keywords in command

    private static final String KEYWORD_CHAR_F = "f";
    private static final String KEYWORD_CHAR_D = "d";
    private static final String KEYWORD_CHAR_E = "e";
    private static final String KEYWORD_CHAR_R = "r";
    private static final String KEYWORD_FLOATING = "floating";
    private static final String KEYWORD_EVENT = "event";
    private static final String KEYWORD_DEADLINE = "deadline";
    private static final String KEYWORD_REPEAT = "repeat";
    private static final String KEYWORD_YEAR = "year";
    private static final String KEYWORD_MONTH = "month";
    private static final String KEYWORD_WEEK = "week";
    private static final String KEYWORD_DAY = "day";
    private static final String KEYWORD_UNTIL = "-until";
    private static final String KEYWORD_EVERY = "-every";
    private static final String KEYWORD_START = "-start";
    private static final String KEYWORD_ON = "-on";
    private static final String KEYWORD_BY = "-by";
    private static final String KEYWORD_SUN = "sun";
    private static final String KEYWORD_MON = "mon";
    private static final String KEYWORD_TUE = "tue";
    private static final String KEYWORD_WED = "wed";
    private static final String KEYWORD_THU = "thu";
    private static final String KEYWORD_FRI = "fri";
    private static final String KEYWORD_SAT = "sat";

    // Regex

    private static final String REGEX_SLASH = "/";
    private static final String REGEX_BACKSLASH = "\\";
    private static final String REGEX_BLANK = "";
    private static final String REGEX_ALL_ALPHA = "[a-zA-Z]";
    private static final String REGEX_COMMA_WHITESPACE = ",\\s";
    private static final String REGEX_RIGHT_SQBRACKET = "]";
    private static final String REGEX_LEFT_SQBRACKET = "[";
    private static final String REGEX_COMMA = ",";
    private static final String REGEX_WHITESPACES = " ";
    private static final String REGEX_WINDOWS_FILEPATH = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\"
            + "?[a-zA-Z0-9._-].(?i)(txt)";

    // Integers constants

    private static final int SIZE_0 = 0;
    private static final int SIZE_1 = 1;
    private static final int SIZE_2 = 2;
    private static final int SIZE_3 = 3;
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;
    private static final int INDEX_6 = 6;
    private static final int INDEX_7 = 7;
    private static final int NEGATIVE_ONE = -1;
    private static final int POSITION_ZERO_PARAM_ARGUMENT = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    private static final int POSITION_SECOND_PARAM_ARGUMENT = 2;
    private static final int POSITION_FIFTH_PARAM_ARGUMENT = 5;
    private static final int TWOPARTS = 2;
    private static final int WEEKSIZE = 8;

    // Dates formats

    private static final String DATEFORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    private static final String INFINITY_DATE = "12/12/9999";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final ArrayList<String> monthInString = new ArrayList<String>(Arrays.asList(createMonthArray()));
    private static ArrayList<String> parserLogger = new ArrayList<String>();
    private static com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();

    // Auxiliary messages

    private static final String MSG_COMMAND = "command: ";
    private static final String MSG_PARSER_LOG_HEADER = "=====CommandParser Log=====";
    private static final String MSG_INVALID_FORMAT = "Command format is invalid";
    private static final String MSG_NULL_POINTER = "Error found: Please try again";


    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // CommandParser main processing methods
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************

    public static Command parse(String userInput) throws Exception {
        addToParserLogger(userInput);
        return initCommand(userInput);
    }

    private static Command initCommand(String userInput) throws Exception {
        Command command;
        ArrayList<String> parameters = getUserCommandAndArguments(userInput);
        String userCommand = checkUserCommandShortcut(getUserCommand(parameters));
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

            default :
                command = initInvalidCommand();
                break;
        }

        assert command != null : MSG_NULL_POINTER;
        return command;
    }


    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // Command methods
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************

    // =========================================================================
    // Create add command
    // =========================================================================

    private static Command initAddCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.ADD);
            setAddCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create delete command
    // =========================================================================

    private static Command initDeleteCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.DELETE);
            setDeleteCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create update command
    // =========================================================================

    private static Command initUpdateCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.UPDATE);   
            setUpdateCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create complete command
    // =========================================================================

    private static Command initCompleteCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.COMPLETE);
            setCompleteCommandAttribute(arguments, command);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create search command
    // =========================================================================

    private static Command initSearchCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.SEARCH);
            setSearchCommandAttribute(arguments, command);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create repeat command for recurrence
    // =========================================================================

    private static Command initRepeatCommand(ArrayList<String> arguments) 
            throws Exception {
        try {     
            Command command = new Command(Command.Type.REPEAT);          
            setRepeatCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create stop recurrence command
    // =========================================================================

    private static Command initStopRepeatCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.STOP_REPEAT);
            setStopRepeatCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create undo command
    // =========================================================================

    private static Command initUndoCommand() throws Exception {
        try {
            return new Command(Command.Type.UNDO);
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create redo command
    // =========================================================================

    private static Command initRedoCommand() throws Exception {
        try {
            return new Command(Command.Type.REDO);
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create setFilePath command
    // =========================================================================

    private static Command initSetFilePathCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.SETFILEPATH);
            setFilePathCommandAttribute(command, arguments);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create invalid command
    // =========================================================================

    private static Command initInvalidCommand() {
        return new Command(Command.Type.INVALID);
    }

    // =========================================================================
    // Create view command
    // =========================================================================

    private static Command initViewDateCommand(ArrayList<String> arguments) 
            throws Exception {
        try {
            Command command = new Command(Command.Type.VIEW);
            return command;
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // =========================================================================
    // Create exit command
    // =========================================================================

    private static Command initExitCommand() {
        return new Command(Command.Type.EXIT);
    }


    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // Methods to set command attributes
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************


    // =========================================================================
    // Set add command attributes
    // =========================================================================

    private static void setAddCommandAttribute(Command command, ArrayList<String> arguments) 
            throws Exception {
        setAttributeForAddType(command, arguments);
    }

    // =========================================================================
    // Set delete command attributes
    // =========================================================================

    private static void setDeleteCommandAttribute(Command command, ArrayList<String> arguments) 
            throws Exception {
        String taskType = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
        setCommandTaskType(command, taskType);
        setCommandTaskID(command, arguments);
    }

    // =========================================================================
    // Set update command attributes
    // =========================================================================

    private static void setUpdateCommandAttribute(Command command, ArrayList<String> arguments) 
            throws Exception {    
        String type = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
        ArrayList<String> parameters = splitStringIntoTwoWithWhiteSpaces(type);
        setAttributeForUpdateType(command, type, parameters);
    }

    // =========================================================================
    // Set complete command attributes
    // =========================================================================

    private static void setCompleteCommandAttribute(ArrayList<String> arguments, Command command) 
            throws Exception {
        String taskType = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
        setCommandTaskType(command, taskType);    
        setCommandTaskID(command, arguments);
    }

    // =========================================================================
    // Set search command attributes
    // =========================================================================

    private static void setSearchCommandAttribute(ArrayList<String> arguments, Command command) {
        setSearchKeyword(command, arguments);
    }

    // =========================================================================
    // Set set filepath command attributes
    // =========================================================================

    private static void setFilePathCommandAttribute(Command command, ArrayList<String> arguments) 
            throws Exception {
        setFilePath(command, arguments);
    }

    // =========================================================================
    // Set repeat command attributes
    // =========================================================================

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

    // =========================================================================
    // Set stop repeat command attributes
    // =========================================================================

    private static void setStopRepeatCommandAttribute(Command command, ArrayList<String> arguments) 
            throws Exception {
        try {
            String[] param = arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                    .split(REGEX_WHITESPACES, SIZE_2);
            setCommandTaskType(command, param[POSITION_ZERO_PARAM_ARGUMENT]);    
            setCommandTaskID(command, param);
            String[] dateParam = param[POSITION_FIRST_PARAM_ARGUMENT].split(REGEX_COMMA);
            setStopRepeatDate(command, dateParam);
        } catch (Exception e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    // *************************************************************************
    // *************************************************************************
    // Auxiliary methods to support set command attributes
    // *************************************************************************
    // *************************************************************************

    // =========================================================================
    // Setters of attribute
    // =========================================================================

    private static void setAttributeForAddType(Command command, 
            ArrayList<String> arguments) throws Exception {

        List<DateGroup> dateGroup = parseDate.parse(arguments.get(POSITION_ZERO_PARAM_ARGUMENT));

        if (dateGroup.size() == SIZE_0) {
            checkKeywordUsed(arguments);
            command.setTaskType(KEYWORD_FLOATING);
            setFloatDescription(command, arguments);
        } else {
            DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            List<Date> dates = group.getDates();
            String[] date = dates.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                    .replace(REGEX_RIGHT_SQBRACKET, REGEX_BLANK).split(REGEX_COMMA_WHITESPACE);
            int numOfDates = date.length;

            if (numOfDates == SIZE_1) {
                command.setTaskType(KEYWORD_DEADLINE);
                setDeadlineDescription(command, arguments);
                command.setTaskDeadline(checkForNull(date[POSITION_ZERO_PARAM_ARGUMENT]));
            } else if (numOfDates == SIZE_2) {
                command.setTaskType(KEYWORD_EVENT);
                setEventDescription(command, arguments);    
                command.setTaskEventStart(checkForNull(date[POSITION_ZERO_PARAM_ARGUMENT]));
                command.setTaskEventEnd(checkForNull(date[POSITION_FIRST_PARAM_ARGUMENT]));
            }
        }
    }

    private static void setCommandTaskID(Command command, String[] param) {
        command.setTaskID(Integer.parseInt(param[POSITION_ZERO_PARAM_ARGUMENT]
                .replaceAll(REGEX_ALL_ALPHA, REGEX_BLANK)));
    }

    private static void setStopRepeatDate(Command command, ArrayList<Date> list) 
            throws Exception {
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
        command.setDateAdded(date.get(POSITION_ZERO_PARAM_ARGUMENT));
    }

    private static void setStartAndEndTime(Command command, String dateTimeLine) throws Exception {
        ArrayList<String> time = extractTime(dateTimeLine);
        command.setRepeatStartTime(time.get(POSITION_ZERO_PARAM_ARGUMENT));
        command.setRepeatEndTime(time.get(POSITION_FIRST_PARAM_ARGUMENT));
    }

    private static void setAttributeForRepeatType(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        if (inputOfRecurrence.contains(KEYWORD_DAY)) {
            setAttributeForDay(command, inputOfRecurrence);
        } else if (inputOfRecurrence.contains(KEYWORD_WEEK)) {
            setAttributeForWeek(command, inputOfRecurrence);
        } else if (inputOfRecurrence.contains(KEYWORD_MONTH)) {
            setAttributeForMonth(command, inputOfRecurrence);
        } else if (inputOfRecurrence.contains(KEYWORD_YEAR)) {
            setAttributeForYear(command, inputOfRecurrence);
        } else {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static void setAttributeForYear(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        command.setRepeatType(KEYWORD_YEAR);
        String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
        command.setYearInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
        remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_UNTIL);
        setRepeatUntil(command, remainingParam);
    }

    private static void setAttributeForMonth(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        command.setRepeatType(KEYWORD_MONTH);
        String[] remainingParam = inputOfRecurrence.split(REGEX_WHITESPACES, SIZE_2);
        command.setMonthInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT].trim());
        remainingParam = remainingParam[POSITION_FIRST_PARAM_ARGUMENT].split(KEYWORD_UNTIL);
        setRepeatUntil(command, remainingParam);
    }

    private static void setAttributeForWeek(Command command, String inputOfRecurrence)
            throws Exception, ParseException {
        command.setRepeatType(KEYWORD_WEEK);
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
        command.setRepeatType(KEYWORD_DAY);
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
            command.setTaskType(KEYWORD_DEADLINE);
        } else if (isEventType(taskType)) {
            command.setTaskType(KEYWORD_EVENT);
        } else if (isFloatingType(taskType)) {
            command.setTaskType(KEYWORD_FLOATING);
        } else if (isRepeatType(taskType)) {
            command.setTaskType(KEYWORD_REPEAT);
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
        String line = arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                .replaceAll(REGEX_SLASH, REGEX_BACKSLASH);
        if(checkFilePath(line)) {
            command.setFilePath(line);
        } else {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        };                

    }

    private static boolean checkFilePath(String line) {
        return Pattern.compile(REGEX_WINDOWS_FILEPATH).matcher(line).find();
    }

    // ================================================================
    // Set attributes of individual update command
    // ================================================================ 

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
            command.setTaskDescription(checkForNull(arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                    .split(KEYWORD_ON)[POSITION_ZERO_PARAM_ARGUMENT].trim()));
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
            command.setTaskDescription(checkForNull(param));
        }
    }

    private static boolean isNotDeadlineDescription(ArrayList<String> arguments) {
        return arguments.get(POSITION_ZERO_PARAM_ARGUMENT)
                .split(KEYWORD_BY)[POSITION_ZERO_PARAM_ARGUMENT].trim().isEmpty();
    }

    private static boolean isNotFloatDescription(ArrayList<String> arguments) {
        return arguments.get(POSITION_ZERO_PARAM_ARGUMENT).trim().isEmpty();
    }

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
        command.setTaskType(KEYWORD_FLOATING);
        setCommandTaskID(command, parameters);
        command.setTaskDescription(parameters.get(POSITION_FIRST_PARAM_ARGUMENT));
    }

    private static void updateEventTask(Command command, ArrayList<String> parameters) 
            throws Exception {
        command.setTaskType(KEYWORD_EVENT);
        setCommandTaskID(command, parameters);

        String line = parameters.get(POSITION_FIRST_PARAM_ARGUMENT);

        if (line.contains(KEYWORD_ON)) {
            String[] param = parameters.get(POSITION_FIRST_PARAM_ARGUMENT).split(KEYWORD_ON);
            if (param.length == SIZE_2) {
                if (!param[POSITION_ZERO_PARAM_ARGUMENT].trim().isEmpty()) {
                    command.setTaskDescription(param[POSITION_ZERO_PARAM_ARGUMENT].trim());
                }
                ArrayList<String> list = extractTime(param[POSITION_FIRST_PARAM_ARGUMENT]);
                command.setTaskEventStart(list.get(POSITION_ZERO_PARAM_ARGUMENT));
                command.setTaskEventEnd(list.get(POSITION_FIRST_PARAM_ARGUMENT));
            } else {
                ArrayList<String> list = extractTime(param[POSITION_FIRST_PARAM_ARGUMENT]);
                command.setTaskEventStart(list.get(POSITION_ZERO_PARAM_ARGUMENT));
                command.setTaskEventEnd(list.get(POSITION_FIRST_PARAM_ARGUMENT));
            }
        } else {
            command.setTaskDescription(line.trim());
        }

    }

    private static String getDateInString(String string) {
        List<DateGroup> dateGroup = parseDate.parse(string);
        DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
        List<Date> dates = group.getDates();
        String[] arr = dates.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                .replace(REGEX_RIGHT_SQBRACKET, REGEX_BLANK).split(REGEX_COMMA_WHITESPACE);
        return arr[POSITION_ZERO_PARAM_ARGUMENT];
    }

    private static void updateDeadlineTask(Command command, ArrayList<String> parameters) throws Exception {
        command.setTaskType(KEYWORD_DEADLINE);
        setCommandTaskID(command, parameters);
        String line = parameters.get(POSITION_FIRST_PARAM_ARGUMENT);

        if (line.contains(KEYWORD_BY)) {
            String date;
            String[] param = parameters.get(POSITION_FIRST_PARAM_ARGUMENT).split(KEYWORD_BY);
            if (param.length == SIZE_2) {
                if (!param[POSITION_ZERO_PARAM_ARGUMENT].trim().isEmpty()) {
                    command.setTaskDescription(param[POSITION_ZERO_PARAM_ARGUMENT].trim());
                }
                date =  getDateInString(param[POSITION_FIRST_PARAM_ARGUMENT]);
                command.setTaskDeadline(date);
            } else {
                date =  getDateInString(param[POSITION_FIRST_PARAM_ARGUMENT]);
                command.setTaskDeadline(date);
            }
        } else {
            command.setTaskDescription(line.trim());
        }

    }

    private static void updateRepeatTask(Command command, ArrayList<String> parameters)
            throws ParseException, Exception {
        command.setTaskType(KEYWORD_REPEAT);
        String[] line;

        setCommandTaskID(command, parameters);
        String input = parameters.get(POSITION_FIRST_PARAM_ARGUMENT);

        if (input.contains(KEYWORD_UNTIL)) {
            line = input.split(KEYWORD_UNTIL);
            checkAttributeValidity(line);

            List<DateGroup> dateGroup = parseDate.parse(line[POSITION_FIRST_PARAM_ARGUMENT]);
            DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            List<Date> dates = group.getDates();

            String[] arr = dates.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                    .replace(REGEX_RIGHT_SQBRACKET, REGEX_BLANK).split(REGEX_COMMA_WHITESPACE);
            String[] param = arr[POSITION_ZERO_PARAM_ARGUMENT].split(REGEX_WHITESPACES);
            int mth = monthInString.indexOf(param[POSITION_FIRST_PARAM_ARGUMENT]) + SIZE_1;

            command.setRepeatUntil(dateFormat.parse(param[POSITION_SECOND_PARAM_ARGUMENT] 
                    + REGEX_SLASH + mth + REGEX_SLASH + param[POSITION_FIFTH_PARAM_ARGUMENT]));
            input = line[POSITION_ZERO_PARAM_ARGUMENT];
        }

        if (input.contains(KEYWORD_ON)) {
            line = input.split(KEYWORD_ON);
            checkAttributeValidity(line);
            String paramArr = line[POSITION_FIRST_PARAM_ARGUMENT].trim();
            command.setIsDaySelected(setSelectedDay(paramArr));
            command.setDaySelectedString();
            input = line[POSITION_ZERO_PARAM_ARGUMENT];
        }

        if (input.contains(KEYWORD_EVERY)) {
            line = input.split(KEYWORD_EVERY);
            checkAttributeValidity(line);
            String[] remainingParam = line[POSITION_FIRST_PARAM_ARGUMENT]
                    .trim().split(REGEX_WHITESPACES, SIZE_2);
            String type = remainingParam[POSITION_FIRST_PARAM_ARGUMENT];

            if (type.equals(KEYWORD_DAY)) {
                command.setDayInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT]);
                command.setRepeatType(KEYWORD_DAY);
            } else if (type.equals(KEYWORD_WEEK)) {
                command.setWeekInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT]);
                command.setRepeatType(KEYWORD_WEEK);
            } else if (type.equals(KEYWORD_MONTH)) {
                command.setMonthInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT]);
                command.setRepeatType(KEYWORD_MONTH);
            } else if (type.equals(KEYWORD_YEAR)) {
                command.setYearInterval(remainingParam[POSITION_ZERO_PARAM_ARGUMENT]);
                command.setRepeatType(KEYWORD_YEAR);
            }

            input = line[POSITION_ZERO_PARAM_ARGUMENT];
        }

        if (input.contains(KEYWORD_START)) {
            line = input.split(KEYWORD_START);
            checkAttributeValidity(line);
            String dateTimeLine = line[POSITION_FIRST_PARAM_ARGUMENT].trim();
            setStartAndEndTime(command, dateTimeLine);
            setDateAdded(command, dateTimeLine);
            input = line[POSITION_ZERO_PARAM_ARGUMENT];
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
        } else {
            return true;
        }
    }

    private static boolean isRepeatType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(KEYWORD_CHAR_R);
    }

    private static boolean isFloatingType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(KEYWORD_CHAR_F);
    }

    private static boolean isEventType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(KEYWORD_CHAR_E);
    }

    private static boolean isDeadlineType(String taskTypeIndicator) {
        return taskTypeIndicator.toLowerCase().startsWith(KEYWORD_CHAR_D);
    }

    private static int extractTaskIdParam(ArrayList<String> parameters) throws Exception {
        int id = Integer.parseInt(parameters.get(POSITION_ZERO_PARAM_ARGUMENT).replaceAll(REGEX_ALL_ALPHA, REGEX_BLANK)) ;
        if (id <= SIZE_0) {
            throw new Exception(MSG_INVALID_FORMAT);
        }
        return id;
    }

    private static ArrayList<Date> extractNattyTwoDates(String dateTime) throws Exception {
        try {
            ArrayList<Date> dateStartEnd = new ArrayList<Date>(SIZE_3);
            List<DateGroup> dateGroup = parseDate.parse(dateTime);
            DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            List<Date> dates = group.getDates();

            String[] arr = dates.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                    .replace(REGEX_RIGHT_SQBRACKET, REGEX_BLANK).split(REGEX_COMMA_WHITESPACE);
            String[] param = arr[POSITION_ZERO_PARAM_ARGUMENT].split(REGEX_WHITESPACES);
            int mth = monthInString.indexOf(param[POSITION_FIRST_PARAM_ARGUMENT]) + SIZE_1;

            dateStartEnd.add(dateFormat.parse(param[POSITION_SECOND_PARAM_ARGUMENT] 
                    + REGEX_SLASH + mth + REGEX_SLASH + param[POSITION_FIFTH_PARAM_ARGUMENT]));
            return dateStartEnd;
        } catch (ParseException e) {
            addToParserLogger(MSG_INVALID_FORMAT);
            throw new Exception(MSG_INVALID_FORMAT);
        }
    }

    private static ArrayList<String> extractTime(String dateTime) throws Exception {
        ArrayList<String> dateStartEnd = new ArrayList<String>(SIZE_3);

        List<DateGroup> dateGroup = parseDate.parse(dateTime);
        DateGroup group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
        List<Date> dates = group.getDates();

        String[] arr = dates.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                .replace(REGEX_RIGHT_SQBRACKET, REGEX_BLANK).split(REGEX_COMMA_WHITESPACE);

        dateStartEnd.add(arr[POSITION_ZERO_PARAM_ARGUMENT]);
        dateStartEnd.add(arr[POSITION_FIRST_PARAM_ARGUMENT]);

        return dateStartEnd;
    }

    private static ArrayList<Date> extractDatesIntoArrayList(String[] dateParam) throws ParseException {
        ArrayList<Date> list = new ArrayList<Date>();
        List<DateGroup> dateGroup;
        DateGroup group;
        List<Date> dates;

        for (int i = 0; i < dateParam.length; i++) {
            dateGroup = parseDate.parse(dateParam[i].trim());
            group = dateGroup.get(POSITION_ZERO_PARAM_ARGUMENT);
            dates = group.getDates();

            String[] param = dates.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                    .replace(REGEX_RIGHT_SQBRACKET, REGEX_BLANK).split(REGEX_WHITESPACES);
            int mth = monthInString.indexOf(param[SIZE_1]) + SIZE_1;

            list.add(dateFormat.parse(param[POSITION_SECOND_PARAM_ARGUMENT] + REGEX_SLASH 
                    + mth + REGEX_SLASH + param[POSITION_FIFTH_PARAM_ARGUMENT]));
        }

        return list;
    }

    private static Boolean[] setSelectedDay(String line) throws Exception {
        String[] days = line.split(REGEX_COMMA);
        Boolean[] isSelected = new Boolean[WEEKSIZE];
        Arrays.fill(isSelected, false);
        int dayNum;
        isSelected[POSITION_ZERO_PARAM_ARGUMENT] = null;

        for (int i = 0; i < days.length; i++) {
            days[i] = days[i].trim();
            dayNum = getDayIndex(days[i]);
            isSelected[dayNum] = true;
        }

        return isSelected;
    }

    private static int getDayIndex(String day) throws Exception {

        int dayIndex = NEGATIVE_ONE;

        switch (day.toLowerCase()) {
            case KEYWORD_SUN :
                dayIndex = INDEX_1;
                break;

            case KEYWORD_MON :
                dayIndex = INDEX_2;
                break;

            case KEYWORD_TUE :
                dayIndex = INDEX_3;
                break;

            case KEYWORD_WED :
                dayIndex = INDEX_4;
                break;

            case KEYWORD_THU :
                dayIndex = INDEX_5;
                break;

            case KEYWORD_FRI :
                dayIndex = INDEX_6;
                break;

            case KEYWORD_SAT :
                dayIndex = INDEX_7;
                break;

            default :
                addToParserLogger(MSG_INVALID_FORMAT);
                throw new Exception(MSG_INVALID_FORMAT);
        }

        return dayIndex;

    }

    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // Other auxiliary methods
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************

    private static String checkUserCommandShortcut(String userCommand) {
        String actualCommand;

        switch (userCommand.toLowerCase()) {
            case USER_COMMAND_ADD_SHORTCUT :
                actualCommand = USER_COMMAND_ADD;
                break;

            case USER_COMMAND_DELETE_SHORTCUT :
                actualCommand = USER_COMMAND_DELETE;
                break;

            case USER_COMMAND_UPDATE_SHORTCUT :
                actualCommand = USER_COMMAND_UPDATE;
                break;

            case USER_COMMAND_COMPLETE_SHORTCUT :
                actualCommand = USER_COMMAND_COMPLETE;
                break;

            case USER_COMMAND_SEARCH_SHORTCUT :
                actualCommand = USER_COMMAND_SEARCH;
                break;

            case USER_COMMAND_REPEAT_SHORTCUT :
                actualCommand = USER_COMMAND_REPEAT;
                break;

            case USER_COMMAND_STOP_REPEAT_SHORTCUT :
                actualCommand = USER_COMMAND_STOP_REPEAT;
                break;

            case USER_COMMAND_SET_FILEPATH_SHORTCUT :
                actualCommand = USER_COMMAND_SET_FILEPATH;
                break;

            case USER_COMMAND_VIEW_SHORTCUT :
                actualCommand = USER_COMMAND_VIEW;
                break;

            default :
                actualCommand = userCommand;
                break;
        }

        return actualCommand;

    }

    private static ArrayList<String> getUserCommandAndArguments(String userInput) throws Exception {
        ArrayList<String> parameters = splitStringIntoTwoWithWhiteSpaces(userInput);
        return parameters;
    }

    private static ArrayList<String> getUserArguments(ArrayList<String> parameters) {
        assert parameters != null;
        ArrayList<String> list = new ArrayList<>();
        if (parameters.size() == SIZE_1) {
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
    
    private static String[] createMonthArray() {
        String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        return month;
    }

}
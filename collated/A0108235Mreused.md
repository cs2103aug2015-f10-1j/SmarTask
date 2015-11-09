# A0108235Mreused
###### src\Command.java
``` java
 */

public class Command {

    private static final String REGEX_BLANK = "";
    private static final String REGEX_AT_SIGN = "@";
    private static final int SIZE_1 = 1;

    public enum Type {
        ADD, DELETE, UPDATE, COMPLETE, VIEW, EXIT, INVALID, SEARCH, UNDO, REDO, 
        REPEAT, STOP_REPEAT, SETFILEPATH
    }

    private int taskID;
    private Type type;
    private String taskType;
    private String taskDescription;
    private String taskDeadline;
    private String taskEventStart;
    private String taskEventEnd;
    private String taskEventDate;
    private String taskEventTime;
    private String filePath;
    private ArrayList<String> searchKeyword;

    // Additional attributes for recurring task
    private String repeatType;
    private Date dateAdded;
    private Date repeatUntil;
    private String repeatStartTime;
    private String repeatEndTime;
    private ArrayList<Date> stopRepeat;
    private String stopRepeatInString;
    private String dayInterval;
    private String weekInterval;
    private Boolean[] isDaySelected;
    private String daySelectedString;
    private String monthInterval;
    private String yearInterval;

    public Command(Type type) {
        this.type = type;
    }

    // =========================================================================
    // Getters method to support Logic methods
    // =========================================================================
    
    public String getTaskDescription() {
        return taskDescription;
    }

    public Type getCommandType() {
        return type;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public String getTaskEventDate() {
        return taskEventDate;
    }

    public String getTaskEventTime() {
        return taskEventTime;
    }

    public ArrayList<String> getSearchKeyword() {
        return searchKeyword;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getTaskType() {
        return taskType;
    }
    
    public String getRepeatStartTime() {
        return repeatStartTime;
    }
    
    public String getRepeatEndTime() {
        return repeatEndTime;
    }
    
    public Date getRepeatUntil() {
        return repeatUntil;
    }

    public String getWeekInterval() {
        return weekInterval;
    }
    
    public Boolean[] getIsDaySelected() {
        return isDaySelected;
    }
    
    public String getMonthInterval() {
        return monthInterval;
    } 

    public ArrayList<Date> getStopRepeat() {
        return stopRepeat;
    }
    
    public String getDaySelectedString() {
        return daySelectedString;
    }
    
    public String getStopRepeatInString() {
        return stopRepeatInString;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTaskEventStart() {
        return taskEventStart;
    }

    public String getTaskEventEnd() {
        return taskEventEnd;
    }
    
    public String getRepeatType() {
        return repeatType;
    }
    
    public String getDayInterval() {
        return dayInterval;
    }

    public String getYearInterval() {
        return yearInterval;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    // =========================================================================
    // Setters method to support CommandParser methods
    // =========================================================================
    
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public void setTaskEventDate(String taskEventDate) {
        this.taskEventDate = taskEventDate;
    }

    public void setTaskEventTime(String taskEventTime) {
        this.taskEventTime = taskEventTime;
    }

    public void setSearchKeyword(ArrayList<String> searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public void setTaskEventStart(String taskEventStart) {
        this.taskEventStart = taskEventStart;
    }

    public void setTaskEventEnd(String taskEventEnd) {
        this.taskEventEnd = taskEventEnd;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public void setDayInterval(String dayInterval) {
        this.dayInterval = dayInterval;
    }

    public void setYearInterval(String yearInterval) {
        this.yearInterval = yearInterval;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setRepeatStartTime(String repeatStartTime) {
        this.repeatStartTime = repeatStartTime;
    }

    public void setRepeatEndTime(String repeatEndTime) {
        this.repeatEndTime = repeatEndTime;
    }

    public void setRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public void setWeekInterval(String weekInterval) {
        this.weekInterval = weekInterval;
    }
    
    public void setIsDaySelected(Boolean[] isDaySelected) {
        this.isDaySelected = isDaySelected;
    }
    
    public void setMonthInterval(String monthInterval) {
        this.monthInterval = monthInterval;
    }

    public void setStopRepeat(ArrayList<Date> stopRepeat) {
        this.stopRepeat = stopRepeat;
    }

    public void setDaySelectedString() {
        String index = REGEX_BLANK;

        for (int i = 1; i < this.isDaySelected.length; i++) {
            if (isDaySelected[i] == true) {
                if (i != this.isDaySelected.length - 1) {
                    index += i + " ";
                } else {
                    index += i;
                }
            }
        }

        this.daySelectedString = index.trim();
    }

    public void setStopRepeatInString() {
        String dates = REGEX_BLANK;

        for (int i = 0; i < stopRepeat.size(); i++) {
            if (i != stopRepeat.size() - SIZE_1) {
                dates += stopRepeat.get(i).toString() + REGEX_AT_SIGN;
            } else {
                dates += stopRepeat.get(i).toString();
            }
        }

        this.stopRepeatInString = dates;
    }
}
```
###### src\CommandHistory.java
``` java
 */

public class CommandHistory {

    private static final int SIZE_1 = 1;
    private static final String MSG_UNDO_FAIL = "Fail to undo.";
    private static final String MSG_REDO_FAIL = "Fail to redo.";

    public Stack <ArrayList<Task>> undoStack;
    public Stack <ArrayList<Task>> redoStack;

    public CommandHistory(ArrayList<Task> storedTask) {
        undoStack = new Stack <ArrayList<Task>>();
        undoStack.push(storedTask);
        redoStack = new Stack <ArrayList<Task>>();
    }

    public void addChangeToHistory(ArrayList<Task> storedTask) {
        undoStack.push(storedTask);
    }

    // =========================================================================
    // Execute undo
    // =========================================================================

    public ArrayList<Task> undo() throws Exception {
        if (isInvalidUndoStack()) {
            throw new Exception(MSG_UNDO_FAIL);
        }

        try {
            redoStack.push(undoStack.pop());
        } catch (Exception e) {
            throw new Exception(MSG_UNDO_FAIL);
        }

        return undoStack.peek();
    }

    // =========================================================================
    // Execute redo
    // =========================================================================

    public ArrayList<Task> redo() throws Exception {
        if (isInvalidRedoStack()) {
            throw new Exception(MSG_REDO_FAIL);
        }

        try {
            undoStack.push(redoStack.pop());
        } catch (Exception e) {
            throw new Exception(MSG_REDO_FAIL);
        }

        return undoStack.peek();
    }

    // =========================================================================
    // Auxiliary methods
    // =========================================================================

    private boolean isInvalidRedoStack() {
        return redoStack.empty() || redoStack.peek() == null;
    }

    private boolean isInvalidUndoStack() {
        return undoStack.empty() || undoStack.peek() == null || undoStack.size() == SIZE_1;
    }

}
```
###### src\CommandParser.java
``` java
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
    private static final String KEYWORD_JAN = "Jan";
    private static final String KEYWORD_FEB = "Feb";
    private static final String KEYWORD_MAR = "Mar";
    private static final String KEYWORD_APR = "Apr";
    private static final String KEYWORD_MAY = "May";
    private static final String KEYWORD_JUN = "Jun";
    private static final String KEYWORD_JUL = "Jul";
    private static final String KEYWORD_AUG = "Aug";
    private static final String KEYWORD_SEP = "Sep";
    private static final String KEYWORD_OCT = "Oct";
    private static final String KEYWORD_NOV = "Nov";
    private static final String KEYWORD_DEC = "Dec";

    // Regex

    private static final String REGEX_SLASH = "/";
    private static final String REGEX_BLANK = "";
    private static final String REGEX_ALL_ALPHA = "[a-zA-Z]";
    private static final String REGEX_COMMA_WHITESPACE = ",\\s";
    private static final String REGEX_RIGHT_SQBRACKET = "]";
    private static final String REGEX_LEFT_SQBRACKET = "[";
    private static final String REGEX_COMMA = ",";
    private static final String REGEX_WHITESPACES = " ";
    private static final String REGEX_WIN_FILEPATH = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\"
            + "?[a-zA-Z0-9._-].(?i)(txt)";
    private static final String REGEX_MAC_FILEPATH = "([a-zA-Z]:)?(/[a-zA-Z0-9._-]+)+/"
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
    private static final String INFINITY_DATE = "12/12/9999";
    private static final String DATEFORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final ArrayList<String> MONTH_ARRAYLIST = new ArrayList<String>(Arrays.asList(createMonthArray()));
    private static ArrayList<String> parserLogger = new ArrayList<String>();
    private static com.joestelmach.natty.Parser parseDate = new com.joestelmach.natty.Parser();

    // Auxiliary messages

    private static final String MSG_COMMAND = "command: ";
    private static final String MSG_PARSER_LOG_HEADER = "=====CommandParser Log=====";
    private static final String MSG_INVALID_FORMAT = "Command format is invalid";
    private static final String MSG_INVALID_FILEPATH = "Invalid filepath."
            + " Ensure filepath format '...\\...\\<yourfilename>.txt' is followed";
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
            addToParserLogger(MSG_INVALID_FILEPATH);
            throw new Exception(MSG_INVALID_FILEPATH);
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
        list.add(DATE_FORMAT.parse(INFINITY_DATE));
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
            command.setRepeatUntil(DATE_FORMAT.parse(INFINITY_DATE));
        }
    }

    private static void setFilePath(Command command, ArrayList<String> arguments) throws Exception {
        String line = arguments.get(POSITION_ZERO_PARAM_ARGUMENT);
        if(checkFilePath(line)) {
            command.setFilePath(line);
        } else {
            addToParserLogger(MSG_INVALID_FILEPATH);
            throw new Exception(MSG_INVALID_FILEPATH);
        };                

    }

    private static boolean checkFilePath(String line) {
        String OS = System.getProperty("os.name").toLowerCase();
        if(isMac(OS)) {
            return Pattern.compile(REGEX_MAC_FILEPATH).matcher(line).find();
        } else {
            return Pattern.compile(REGEX_WIN_FILEPATH).matcher(line).find();
        }
        
    }
    
    private static boolean isMac(String OS) {
        return (OS.indexOf("mac") >= 0);
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
            int mth = MONTH_ARRAYLIST.indexOf(param[POSITION_FIRST_PARAM_ARGUMENT]) + SIZE_1;

            command.setRepeatUntil(DATE_FORMAT.parse(param[POSITION_SECOND_PARAM_ARGUMENT] 
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
            int mth = MONTH_ARRAYLIST.indexOf(param[POSITION_FIRST_PARAM_ARGUMENT]) + SIZE_1;

            dateStartEnd.add(DATE_FORMAT.parse(param[POSITION_SECOND_PARAM_ARGUMENT] 
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
            int mth = MONTH_ARRAYLIST.indexOf(param[SIZE_1]) + SIZE_1;

            list.add(DATE_FORMAT.parse(param[POSITION_SECOND_PARAM_ARGUMENT] + REGEX_SLASH 
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
        String[] month = {KEYWORD_JAN, KEYWORD_FEB, KEYWORD_MAR, KEYWORD_APR, 
                KEYWORD_MAY, KEYWORD_JUN,  KEYWORD_JUL, KEYWORD_AUG, 
                KEYWORD_SEP, KEYWORD_OCT, KEYWORD_NOV, KEYWORD_DEC};
        return month;
    }

}
```
###### src\CommandParserUnitTest.java
``` java
 */

public class CommandParserUnitTest {

    // Used in Command testing

    private static final String HEADER_TWO_PARAM_COMMANDS = "\n----Two param----";
    private static final String HEADER_SINGLE_PARAM_COMMANDS = "\n----Single param----";
    private static final String HEADER_SHORTCUT_COMMAND = "\n----Shortcuts command----";

    private static final String HEADER_ADD_COMMAND = "\n----Add----";
    private static final String HEADER_SETFILEPATH_COMMAND = "\n----SetFilePath commands----";
    private static final String HEADER_REPEAT_COMMAND = "\n----Repeat commands----";
    private static final String HEADER_UPDATE_COMMAND = "\n----Update----";
    private static final String HEADER_UPDATE_COMMAND_SELECTED_PARAM = "\n----Update selected param----";

    private static final String HEADER_UPDATE_REPEAT_COMMAND = "\n----Update: all param of repeat ----";
    private static final String HEADER_STOP_REPEAT_COMMAND = "\n----Stop repeat----";
    private static final String HEADER_UPDATE_REPEAT_DAY_PARAM = "\n----Update: selected param of Day repeat----";
    private static final String HEADER_UPDATE_REPEAT_WEEK_PARAM = "\n----Update: selected param of Week repeat----";
    private static final String HEADER_UPDATE_REPEAT_MONTH_PARAM = "\n----Update: selected param of Month repeat----";
    private static final String HEADER_UPDATE_REPEAT_YEAR_PARAM = "\n----Update: selected param of Year repeat----";

    // Used in Error testing

    private static final String HEADER_ERRORS_TWO_PARAM = "\n----Errors: Two param----";
    private static final String HEADER_ERRORS_SETFILEPATH = "\n----Errors: Set Filepath----";
    private static final String HEADER_ERRORS_ADD = "\n----Errors: Add----";
    private static final String HEADER_ERRORS_REPEAT = "\n----Error: Repeat----";
    private static final String HEADER_ERRORS_UPDATE = "\n----Error: Update----";
    private static final String HEADER_ERRORS_UPDATE_DAY_PARAM = "\n----Error: Day update----";
    private static final String HEADER_ERRORS_UPDATE_WEEK_PARAM = "\n----Error: Week update----";
    private static final String HEADER_ERRORS_UPDATE_MONTH_PARAM = "\n----Error: Month update----";
    private static final String HEADER_ERRORS_UPDATE_YEAR_PARAM = "\n----Error: Year update----";
    private static final String HEADER_ERRORS_INVALID_FILEPATH = "Invalid filepath."
            + " Ensure filepath format '...\\...\\<yourfilename>.txt' is followed";
    private static final String HEADER_ERRORS_INVALID_FORMAT = "Command format is invalid";

    String input;
    public ArrayList<String> actual; 
    public ArrayList<String> expected;
    public String actualMsg;
    public String expectedMsg;

    private void executeTestWithArrayList() {
        printUnitTestWithArrayList();
        assertEquals(expected, actual);
        System.out.println();
    }

    private void executeTestWithString() {
        printUnitTestWithString();
        assertEquals(actualMsg, expectedMsg);
        System.out.println();
    }

    private void printUnitTestWithString() {
        System.out.println("   Input: \"" + input + "\"");
        System.out.println("Expected: " + expectedMsg);
        System.out.println("  Actual: " + actualMsg);
    }

    private void printUnitTestWithArrayList() {
        System.out.println("   Input: \"" + input + "\"");
        System.out.println("Expected: " + expected.toString());
        System.out.println("  Actual: " + actual.toString());
    }

    @Test
    public final void testSingleParamCommand() throws Exception {

        System.out.println(HEADER_SINGLE_PARAM_COMMANDS);

        input = "undo";
        actual = new ArrayList<String>();
        Command undo = CommandParser.parse(input);
        actual.add(undo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("UNDO"));
        executeTestWithArrayList();

        input = "redo";
        actual = new ArrayList<String>();
        Command redo = CommandParser.parse(input);
        actual.add(redo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("REDO"));
        executeTestWithArrayList();

        input = "invalid command here";
        actual = new ArrayList<String>();
        Command invalid = CommandParser.parse(input);
        actual.add(invalid.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("INVALID"));
        executeTestWithArrayList();

        input = "view";
        actual = new ArrayList<String>();
        Command view = CommandParser.parse(input);
        actual.add(view.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("VIEW"));
        executeTestWithArrayList();

        input = "exit";
        actual = new ArrayList<String>();
        Command exit = CommandParser.parse(input);
        actual.add(exit.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("EXIT"));
        executeTestWithArrayList();

    }

    @Test
    public final void testSingleParamUpperCapsCommand() throws Exception {

        System.out.println(HEADER_SINGLE_PARAM_COMMANDS);

        input = "UNDO";
        actual = new ArrayList<String>();
        Command undo = CommandParser.parse(input);
        actual.add(undo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("UNDO"));
        executeTestWithArrayList();

        input = "REDO";
        actual = new ArrayList<String>();
        Command redo = CommandParser.parse(input);
        actual.add(redo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("REDO"));
        executeTestWithArrayList();

        input = "AAAAAAAAAAAADDDDDDDDDD";
        actual = new ArrayList<String>();
        Command invalid = CommandParser.parse(input);
        actual.add(invalid.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("INVALID"));
        executeTestWithArrayList();

        input = "EXIT";
        actual = new ArrayList<String>();
        Command exit = CommandParser.parse(input);
        actual.add(exit.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("EXIT"));
        executeTestWithArrayList();

    }

    @Test
    public final void testTwoParamCommand() throws Exception {

        System.out.println(HEADER_TWO_PARAM_COMMANDS);

        // Complete command

        input = "complete d1";
        actual = new ArrayList<String>();
        Command completeDeadline = CommandParser.parse(input);
        actual.add(completeDeadline.getCommandType().toString());
        actual.add(completeDeadline.getTaskType());
        actual.add(String.valueOf(completeDeadline.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","deadline","1"));
        executeTestWithArrayList();

        input = "complete e2";
        actual = new ArrayList<String>();
        Command completeEvent = CommandParser.parse(input);
        actual.add(completeEvent.getCommandType().toString());
        actual.add(completeEvent.getTaskType());
        actual.add(String.valueOf(completeEvent.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","event","2"));
        executeTestWithArrayList();

        input = "complete f3";
        actual = new ArrayList<String>();
        Command completeFloat = CommandParser.parse(input);
        actual.add(completeFloat.getCommandType().toString());
        actual.add(completeFloat.getTaskType());
        actual.add(String.valueOf(completeFloat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","floating","3"));
        executeTestWithArrayList();

        input = "complete r4";
        actual = new ArrayList<String>();
        Command completeRepeat = CommandParser.parse(input);
        actual.add(completeRepeat.getCommandType().toString());
        actual.add(completeRepeat.getTaskType());
        actual.add(String.valueOf(completeRepeat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","repeat","4"));
        executeTestWithArrayList();

        // Delete command

        input = "delete d1";
        actual = new ArrayList<String>();
        Command deleteDeadline = CommandParser.parse(input);
        actual.add(deleteDeadline.getCommandType().toString());
        actual.add(deleteDeadline.getTaskType());
        actual.add(String.valueOf(deleteDeadline.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","deadline","1"));
        executeTestWithArrayList();

        input = "delete e2";
        actual = new ArrayList<String>();
        Command deleteEvent = CommandParser.parse(input);
        actual.add(deleteEvent.getCommandType().toString());
        actual.add(deleteEvent.getTaskType());
        actual.add(String.valueOf(deleteEvent.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","event","2"));
        executeTestWithArrayList();

        input = "delete f3";
        actual = new ArrayList<String>();
        Command deleteFloat = CommandParser.parse(input);
        actual.add(deleteFloat.getCommandType().toString());
        actual.add(deleteFloat.getTaskType());
        actual.add(String.valueOf(deleteFloat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","floating","3"));
        executeTestWithArrayList();

        input = "delete r4";
        actual = new ArrayList<String>();
        Command deleteRepeat = CommandParser.parse(input);
        actual.add(deleteRepeat.getCommandType().toString());
        actual.add(deleteRepeat.getTaskType());
        actual.add(String.valueOf(deleteRepeat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","repeat","4"));
        executeTestWithArrayList();

        // Search command

        input = "search oneword";
        actual = new ArrayList<String>();
        Command search1 = CommandParser.parse(input);
        actual.add(search1.getCommandType().toString());
        actual.addAll(search1.getSearchKeyword());
        expected = new ArrayList<String>(Arrays.asList("SEARCH","oneword"));
        executeTestWithArrayList();

        input = "search meeting office";
        actual = new ArrayList<String>();
        Command search2 = CommandParser.parse(input);
        actual.add(search2.getCommandType().toString());
        actual.addAll(search2.getSearchKeyword());
        expected = new ArrayList<String>(Arrays.asList("SEARCH","meeting","office"));
        executeTestWithArrayList();

    }

    @Test
    public final void testCommandShortcuts() throws Exception {

        Command command;

        System.out.println(HEADER_SHORTCUT_COMMAND);

        // Shortcut 'cp' for complete command

        input = "cp d1";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.add(command.getTaskType());
        actual.add(String.valueOf(command.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","deadline","1"));
        executeTestWithArrayList();

        // Shortcut 'sh' for search command

        input = "sh meeting office";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.addAll(command.getSearchKeyword());
        expected = new ArrayList<String>(Arrays.asList("SEARCH","meeting","office"));
        executeTestWithArrayList();

        // Using 'de' for delete command

        input = "de e2";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.add(command.getTaskType());
        actual.add(String.valueOf(command.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","event","2"));
        executeTestWithArrayList();

        // Shortcut 'sfp' for set filepath command

        input = "sfp C:\\Users\\Jim\\Dropbox\\SmarTask\\worktask.txt";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.add(command.getFilePath());
        expected = new ArrayList<String>(Arrays.asList("SETFILEPATH",
                "C:\\Users\\Jim\\Dropbox\\SmarTask\\worktask.txt"));
        executeTestWithArrayList();

        // Shortcut 'rp' for repeating command

        input = "rp submit weekly report -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.add(command.getTaskDescription());
        actual.add(command.getDateAdded().toString());
        actual.add(command.getRepeatStartTime());
        actual.add(command.getRepeatEndTime());
        actual.add(command.getWeekInterval());
        actual.add(command.getRepeatUntil().toString());
        actual.add(command.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("REPEAT","submit weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", "2", "Fri Dec 25 00:00:00 SGT 2015",
                "Sun Dec 12 00:00:00 SGT 9999"));
        executeTestWithArrayList();

        // Shortcut 'ud' to update

        input = "ud e2 Meeting with CEO -on 26 Nov 5 to 6pm";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.add(command.getTaskType());
        actual.add(String.valueOf(command.getTaskID()));
        actual.add(command.getTaskDescription());
        actual.add(command.getTaskEventStart());
        actual.add(command.getTaskEventEnd());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","event", "2", 
                "Meeting with CEO", "Thu Nov 26 17:00:00 SGT 2015", 
                "Thu Nov 26 18:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Shortcut 'sr' to stop repeat

        input = "sr r2 15 oct";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.add(command.getTaskType());
        actual.add(String.valueOf(command.getTaskID()));
        actual.add(command.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("STOP_REPEAT","repeat", "2", 
                "Thu Oct 15 00:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Shortcut 'vd' to stop repeat

        input = "vd";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("VIEW"));
        executeTestWithArrayList();

    }


    @Test
    public final void testSetFilePathCommand() throws Exception {

        System.out.println(HEADER_SETFILEPATH_COMMAND);

        // set filepath command

        Command setFilepath;

        // set filepath command

        input = "setfilepath C:\\Users\\Jim\\Desktop\\storage.txt";
        actual = new ArrayList<String>();
        setFilepath = CommandParser.parse(input);
        actual.add(setFilepath.getCommandType().toString());
        actual.add(setFilepath.getFilePath());
        expected = new ArrayList<String>(Arrays.asList(
                "SETFILEPATH","C:\\Users\\Jim\\Desktop\\storage.txt"));

    }

    @Test
    public final void testAddCommand() throws Exception {

        Command addDeadline, addEvent, addFloat;

        System.out.println(HEADER_ADD_COMMAND);

        // Add event

        input = "add Meeting with Boss -on 26 Nov 5 to 6pm";
        actual = new ArrayList<String>();
        addEvent = CommandParser.parse(input);
        actual.add(addEvent.getCommandType().toString());
        actual.add(addEvent.getTaskType());
        actual.add(addEvent.getTaskDescription());
        actual.add(addEvent.getTaskEventStart());
        actual.add(addEvent.getTaskEventEnd());
        expected = new ArrayList<String>(Arrays.asList("ADD","event", "Meeting with Boss"
                , "Thu Nov 26 17:00:00 SGT 2015", "Thu Nov 26 18:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Add deadline

        input = "add Finish assignment -by 10 Dec 12pm";
        actual = new ArrayList<String>();
        addDeadline = CommandParser.parse(input);
        actual.add(addDeadline.getCommandType().toString());
        actual.add(addDeadline.getTaskType());
        actual.add(addDeadline.getTaskDescription());
        actual.add(addDeadline.getTaskDeadline());
        expected = new ArrayList<String>(Arrays.asList("ADD","deadline", "Finish assignment"
                , "Thu Dec 10 12:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Add deadline

        input = "add Finish assignment -by today 12am";
        actual = new ArrayList<String>();
        addDeadline = CommandParser.parse(input);
        actual.add(addDeadline.getCommandType().toString());
        actual.add(addDeadline.getTaskType());
        actual.add(addDeadline.getTaskDescription());
        actual.add(addDeadline.getTaskDeadline());
        String todayString = todayDate();
        expected = new ArrayList<String>(Arrays.asList("ADD","deadline", "Finish assignment"
                , todayString));
        executeTestWithArrayList();

        // Add deadline

        input = "add Finish assignment -by tomorrow 12am";
        actual = new ArrayList<String>();
        addDeadline = CommandParser.parse(input);
        actual.add(addDeadline.getCommandType().toString());
        actual.add(addDeadline.getTaskType());
        actual.add(addDeadline.getTaskDescription());
        actual.add(addDeadline.getTaskDeadline());
        String tomorrowString = tomorrowDate();
        expected = new ArrayList<String>(Arrays.asList("ADD","deadline", "Finish assignment"
                , tomorrowString));
        executeTestWithArrayList();

        // Add floating

        input = "add Travel to LA";
        actual = new ArrayList<String>();
        addFloat = CommandParser.parse(input);
        actual.add(addFloat.getCommandType().toString());
        actual.add(addFloat.getTaskType());
        actual.add(addFloat.getTaskDescription());
        expected = new ArrayList<String>(Arrays.asList("ADD","floating", "Travel to LA"));
        executeTestWithArrayList();

    }

    @Test
    public final void testRepeatCommand() throws Exception {

        System.out.println(HEADER_REPEAT_COMMAND);

        Command repeatDay;

        // Repeat by day

        input = "repeat submit daily report -start 15 Nov 5 to 6 pm -every 1 day -until 15 Dec 2016";
        actual = new ArrayList<String>();
        repeatDay = CommandParser.parse(input);
        actual.add(repeatDay.getCommandType().toString());
        actual.add(repeatDay.getTaskDescription());
        actual.add(repeatDay.getDateAdded().toString());
        actual.add(repeatDay.getRepeatStartTime());
        actual.add(repeatDay.getRepeatEndTime());
        actual.add(repeatDay.getDayInterval());
        actual.add(repeatDay.getRepeatUntil().toString());
        actual.add(repeatDay.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("REPEAT","submit daily report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016",
                "Sun Dec 12 00:00:00 SGT 9999"));
        executeTestWithArrayList();

        // Repeat by day without stating repeatUntil (Result: repeat forever)

        input = "repeat submit daily report -start 15 Nov 5 to 6 pm -every 1 day";
        actual = new ArrayList<String>();
        repeatDay = CommandParser.parse(input);
        actual.add(repeatDay.getCommandType().toString());
        actual.add(repeatDay.getTaskDescription());
        actual.add(repeatDay.getDateAdded().toString());
        actual.add(repeatDay.getRepeatStartTime());
        actual.add(repeatDay.getRepeatEndTime());
        actual.add(repeatDay.getDayInterval());
        actual.add(repeatDay.getRepeatUntil().toString());
        actual.add(repeatDay.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("REPEAT","submit daily report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Sun Dec 12 00:00:00 SGT 9999",
                "Sun Dec 12 00:00:00 SGT 9999"));
        executeTestWithArrayList();

        // Repeat by week

        input = "repeat submit weekly report -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec";
        actual = new ArrayList<String>();
        Command repeatWeek = CommandParser.parse(input);
        actual.add(repeatWeek.getCommandType().toString());
        actual.add(repeatWeek.getTaskDescription());
        actual.add(repeatWeek.getDateAdded().toString());
        actual.add(repeatWeek.getRepeatStartTime());
        actual.add(repeatWeek.getRepeatEndTime());
        actual.add(repeatWeek.getWeekInterval());
        actual.add(repeatWeek.getRepeatUntil().toString());
        actual.add(repeatWeek.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("REPEAT","submit weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", "2", "Fri Dec 25 00:00:00 SGT 2015",
                "Sun Dec 12 00:00:00 SGT 9999"));
        executeTestWithArrayList();

        // Repeat by month

        input = "repeat submit monthly report -start 15 Nov 5 to 6 pm -every 1 month -until 15 Dec 2016";
        actual = new ArrayList<String>();
        Command repeatMonth = CommandParser.parse(input);
        actual.add(repeatMonth.getCommandType().toString());
        actual.add(repeatMonth.getTaskDescription());
        actual.add(repeatMonth.getDateAdded().toString());
        actual.add(repeatMonth.getRepeatStartTime());
        actual.add(repeatMonth.getRepeatEndTime());
        actual.add(repeatMonth.getMonthInterval());
        actual.add(repeatMonth.getRepeatUntil().toString());
        actual.add(repeatMonth.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("REPEAT","submit monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016",
                "Sun Dec 12 00:00:00 SGT 9999"));
        executeTestWithArrayList();

        // Repeat by year

        input = "repeat submit yearly report -start 15 Nov 5 to 6 pm -every 1 year -until 15 Dec 2016";
        actual = new ArrayList<String>();
        Command repeatYear = CommandParser.parse(input);
        actual.add(repeatYear.getCommandType().toString());
        actual.add(repeatYear.getTaskDescription());
        actual.add(repeatYear.getDateAdded().toString());
        actual.add(repeatYear.getRepeatStartTime());
        actual.add(repeatYear.getRepeatEndTime());
        actual.add(String.valueOf(repeatYear.getYearInterval()));
        actual.add(repeatYear.getRepeatUntil().toString());
        actual.add(repeatYear.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("REPEAT","submit yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016",
                "Sun Dec 12 00:00:00 SGT 9999"));
        executeTestWithArrayList();

    }

    @Test
    public final void testUpdateCommand() throws Exception {

        System.out.println(HEADER_UPDATE_COMMAND);

        // Update floating

        input = "update f1 Travel to New York";
        actual = new ArrayList<String>();
        Command updateFloat = CommandParser.parse(input);
        actual.add(updateFloat.getCommandType().toString());
        actual.add(updateFloat.getTaskType());
        actual.add(String.valueOf(updateFloat.getTaskID()));
        actual.add(updateFloat.getTaskDescription());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","floating", "1", 
                "Travel to New York"));
        executeTestWithArrayList();

        // Update event

        input = "update e2 Meeting with CEO -on 26 Nov 5 to 6pm";
        actual = new ArrayList<String>();
        Command updateEvent = CommandParser.parse(input);
        actual.add(updateEvent.getCommandType().toString());
        actual.add(updateEvent.getTaskType());
        actual.add(String.valueOf(updateEvent.getTaskID()));
        actual.add(updateEvent.getTaskDescription());
        actual.add(updateEvent.getTaskEventStart());
        actual.add(updateEvent.getTaskEventEnd());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","event", "2", 
                "Meeting with CEO", "Thu Nov 26 17:00:00 SGT 2015", 
                "Thu Nov 26 18:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Update deadline

        input = "update d3 Finish project -by 10 Dec 12pm";
        actual = new ArrayList<String>();
        Command updateDeadline = CommandParser.parse(input);
        actual.add(updateDeadline.getCommandType().toString());
        actual.add(updateDeadline.getTaskType());
        actual.add(String.valueOf(updateDeadline.getTaskID()));
        actual.add(updateDeadline.getTaskDescription());
        actual.add(updateDeadline.getTaskDeadline());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","deadline", "3",
                "Finish project", "Thu Dec 10 12:00:00 SGT 2015"));
        executeTestWithArrayList();

    }

    @Test
    public final void testUpdateCommandWithSelectedParam() throws Exception {

        System.out.println(HEADER_UPDATE_COMMAND_SELECTED_PARAM);

        // Update event description

        input = "update e1 Meeting with CEO";
        actual = new ArrayList<String>();
        Command updateEventDescription = CommandParser.parse(input);
        actual.add(updateEventDescription.getCommandType().toString());
        actual.add(updateEventDescription.getTaskType());
        actual.add(String.valueOf(updateEventDescription.getTaskID()));
        actual.add(updateEventDescription.getTaskDescription());
        actual.add(updateEventDescription.getTaskEventStart());
        actual.add(updateEventDescription.getTaskEventEnd());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","event", "1", 
                "Meeting with CEO", null, null));
        executeTestWithArrayList();

        // Update event date and time

        input = "update e2 -on 26 Nov 5 to 6pm";
        actual = new ArrayList<String>();
        Command updateEventDateTime = CommandParser.parse(input);
        actual.add(updateEventDateTime.getCommandType().toString());
        actual.add(updateEventDateTime.getTaskType());
        actual.add(String.valueOf(updateEventDateTime.getTaskID()));
        actual.add(updateEventDateTime.getTaskDescription());
        actual.add(updateEventDateTime.getTaskEventStart());
        actual.add(updateEventDateTime.getTaskEventEnd());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","event", "2", 
                null, "Thu Nov 26 17:00:00 SGT 2015", "Thu Nov 26 18:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Update deadline description

        input = "update d3 Finish project";
        actual = new ArrayList<String>();
        Command updateDeadlineDescription = CommandParser.parse(input);
        actual.add(updateDeadlineDescription.getCommandType().toString());
        actual.add(updateDeadlineDescription.getTaskType());
        actual.add(String.valueOf(updateDeadlineDescription.getTaskID()));
        actual.add(updateDeadlineDescription.getTaskDescription());
        actual.add(updateDeadlineDescription.getTaskDeadline());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","deadline", "3",
                "Finish project", null));
        executeTestWithArrayList();

        // Update deadline date time

        input = "update d4 -by 10 Dec 12pm";
        actual = new ArrayList<String>();
        Command updateDeadlineTime = CommandParser.parse(input);
        actual.add(updateDeadlineTime.getCommandType().toString());
        actual.add(updateDeadlineTime.getTaskType());
        actual.add(String.valueOf(updateDeadlineTime.getTaskID()));
        actual.add(updateDeadlineTime.getTaskDescription());
        actual.add(updateDeadlineTime.getTaskDeadline());
        expected = new ArrayList<String>(Arrays.asList("UPDATE","deadline", "4",
                null, "Thu Dec 10 12:00:00 SGT 2015"));
        executeTestWithArrayList();

    }

    @Test
    public final void testUpdateRepeatCommand() throws Exception {

        System.out.println(HEADER_UPDATE_REPEAT_COMMAND);

        // Update day

        input = "update r1 email daily report -start 15 Nov 5 to 6 pm -every 1 day -until 15 Dec 2016";
        Command repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","day", "1","email daily report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016",
                null));
        executeTestWithArrayList();

        // Update week

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -every 2 week -on mon,fri -until 25 Dec";
        Command repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", "week", "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015","2", "2 6", "Fri Dec 25 00:00:00 SGT 2015", null));
        executeTestWithArrayList();

        // Update month

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -every 1 month -until 15 Dec 2016";
        Command repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "month", "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Update year

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm -every 1 year -until 15 Dec 2016";
        Command repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "year", "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

    }

    @Test
    public final void testStopRepeatCommand() throws Exception {

        System.out.println(HEADER_STOP_REPEAT_COMMAND);

        // Stop at a date

        input = "stop r2 15 oct";
        actual = new ArrayList<String>();
        Command stop1 = CommandParser.parse(input);
        actual.add(stop1.getCommandType().toString());
        actual.add(stop1.getTaskType());
        actual.add(String.valueOf(stop1.getTaskID()));
        actual.add(stop1.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("STOP_REPEAT","repeat", "2", 
                "Thu Oct 15 00:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Stop at more than one dates

        input = "stop r1 15 oct, 25 nov, 6 aug";
        actual = new ArrayList<String>();
        Command stop = CommandParser.parse(input);
        actual.add(stop.getCommandType().toString());
        actual.add(stop.getTaskType());
        actual.add(String.valueOf(stop.getTaskID()));
        actual.add(stop.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("STOP_REPEAT","repeat", "1", 
                "Thu Oct 15 00:00:00 SGT 2015@Wed Nov 25 00:00:00 SGT 2015@Thu Aug 06 00:00:00 SGT 2015"));
        executeTestWithArrayList();

        // Stop at more than one dates

        input = "stop r1 15 oct, 25 nov";
        actual = new ArrayList<String>();
        Command stop2 = CommandParser.parse(input);
        actual.add(stop2.getCommandType().toString());
        actual.add(stop2.getTaskType());
        actual.add(String.valueOf(stop2.getTaskID()));
        actual.add(stop2.getStopRepeatInString());
        expected = new ArrayList<String>(Arrays.asList("STOP_REPEAT","repeat", "1", 
                "Thu Oct 15 00:00:00 SGT 2015@Wed Nov 25 00:00:00 SGT 2015"));
        executeTestWithArrayList();

    }

    @Test
    public final void testUpdateDayRepeatCommand() throws Exception {

        Command repeatDay;

        System.out.println(HEADER_UPDATE_REPEAT_DAY_PARAM);

        // Excluding: repeatUntil

        input = "update r1 email daily report -start 15 Nov 5 to 6 pm -every 1 day";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", "day", "1","email daily report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", null, null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r1 email daily report -start 15 Nov 5 to 6 pm";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "1","email daily report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, null, null));
        executeTestWithArrayList();

        // Only description

        input = "update r1 email daily report";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "1","email daily report",
                null, null, null, null, null, null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, description

        input = "update r1 -start 15 Nov 5 to 6 pm -every 1 day";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", "day", "1", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", null, null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, description

        input = "update r1 -every 1 day";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", "day", "1", null,
                null, null, null, "1", null, null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, description

        input = "update r5 -until 15 Dec 2016";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", null, "5", null,
                null, null, null, null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Excluding: description, interval, repeat type

        input = "update r1 -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", null, "1", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

    }

    @Test
    public final void testUpdateRepeatCommandWithSelectedParam() throws Exception {

        Command repeatWeek;

        System.out.println(HEADER_UPDATE_REPEAT_WEEK_PARAM);

        // All attributes

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "week", "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", "2", "1 4 7", "Fri Dec 25 00:00:00 SGT 2015", null));
        executeTestWithArrayList();

        // Excluding: repeatUntil

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","week", "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", "2", "1 4 7", null, null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -on tue, thu";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", null, "3 5" ,null, null));
        executeTestWithArrayList();

        // Only description

        input = "update r2 email weekly report";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "2","email weekly report",
                null, null, null, null, null, null, null));
        executeTestWithArrayList();

        // Only description and day selected

        input = "update r2 email weekly report -on tue, thu";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "2","email weekly report",
                null, null, null, null, "3 5", null, null));
        executeTestWithArrayList();

        // Only day selected

        input = "update r2 -on tue, thu";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "2",null,
                null, null, null, null, "3 5", null, null));
        executeTestWithArrayList();



        input = "update r2 email weekly report -st 25 Dec 9-11am -ev 2 week -on sun, sat, wed -ut 25 Dec";

    }

    @Test
    public final void testUpdateMonthRepeatCommand() throws Exception {

        Command repeatMonth;

        System.out.println(HEADER_UPDATE_REPEAT_MONTH_PARAM);

        // All attributes

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -every 1 month -until 15 Dec 2016";
        repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "month", "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Excluding: repeatUntil

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -every 1 month";
        repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","month", "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", null, null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm";
        repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", null, "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, null, null));
        executeTestWithArrayList();

        // Excluding: interval, repeat type

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Excluding: interval, repeat type

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Only description, interval and repeatType

        input = "update r3 email monthly report -every 1 month";
        repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "month", "3","email monthly report",
                null, null, null, "1", null, null));
        executeTestWithArrayList();

    }

    @Test
    public final void testUpdateYearRepeatCommand() throws Exception {

        Command repeatYear;

        System.out.println(HEADER_UPDATE_REPEAT_YEAR_PARAM);

        // All attributes

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm -every 1 year -until 15 Dec 2016";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","year", "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm -every 1 year";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","year", "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", null, null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, interval, repeat type  

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat",null, "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, null, null));
        executeTestWithArrayList();

        // Only description

        input = "update r4 email yearly report";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat",null, "4", "email yearly report",
                null, null, null, null, null, null));
        executeTestWithArrayList();

        // Excluding: interval, repeat type

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Excluding: description

        input = "update r4 -start 15 Nov 5 to 6 pm -every 1 year -until 15 Dec 2016";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","year", "4", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Excluding: description, interval, repeatType

        input = "update r4 -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat",null, "4", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeTestWithArrayList();

        // Excluding: repeatUntil, dateAdded, startTime, endTime

        input = "update r4 email yearly report -every 1 year";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","year", "4", "email yearly report",
                null, null, null, "1", null, null));
        executeTestWithArrayList();

    }

    // =========================================================================
    // Errors testing
    // =========================================================================

    @Test
    public final void testTwoParamErrors() throws Exception {

        System.out.println(HEADER_ERRORS_TWO_PARAM);

        input = "undo aaaaaaaaaaaaaa";
        actual = new ArrayList<String>();
        Command undo = CommandParser.parse(input);
        actual.add(undo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("UNDO"));
        executeTestWithArrayList();

        input = "redo aaaaaaaaaaaaaaaaa";
        actual = new ArrayList<String>();
        Command redo = CommandParser.parse(input);
        actual.add(redo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("REDO"));
        executeTestWithArrayList();

        input = "exit aaaaaaaaaaaa";
        actual = new ArrayList<String>();
        Command exit = CommandParser.parse(input);
        actual.add(exit.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("EXIT"));
        executeTestWithArrayList();

    }

    @Test
    public final void testSetFilePathErrors() throws Exception {

        @SuppressWarnings("unused")
        Command filePath;

        System.out.println(HEADER_ERRORS_SETFILEPATH);

        // setfilepath using whitespaces

        try {
            input = "setfilepath            ";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FILEPATH;
            executeTestWithString();
        }

        // setfilepath using random string

        try {
            input = "sfp abcdefgh asdoaskjdkasld asjdlasldj";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FILEPATH;
            executeTestWithString();
        }

        // setfilepath using without naming text file

        try {
            input = "setfilepath C:/Users/Jim/SmarTask/bin";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FILEPATH;
            executeTestWithString();
        }

        // setfilepath using without naming text file

        try {
            input = "setfilepath C:/Users/Jim/SmarTask/bin/.txt";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FILEPATH;
            executeTestWithString();
        }

    }

    @Test
    public final void testAddErrors() throws Exception {

        @SuppressWarnings("unused")
        Command add;

        System.out.println(HEADER_ERRORS_ADD);

        // add nothing
        try {
            input = "add";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add whitespaces
        try {
            input = "add         ";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add deadline without description
        try {
            input = "add      -by 5 nov";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add deadline using keyword with whitespaces
        try {
            input = "add      -by         ";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add event without description
        try {
            input = "add     -on 6 Nov 9 to 10pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using invalid time
        try {
            input = "add     -on 6 Nov 9pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using invalid time
        try {
            input = "add meeting -on 6 Nov 9pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using invalid time
        try {
            input = "add meeting -on 9pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using keyword with whitespaces
        try {
            input = "add     -on         ";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

    }

    @Test
    public final void testAddRepeatErrors() throws Exception {

        @SuppressWarnings("unused")
        Command repeat;

        System.out.println(HEADER_ERRORS_REPEAT);

        // Repeat without recurring attributes

        try {
            input = "repeat asdasdasdad";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat asdasdasdad -start 15 Nov 5 to 6 pm";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat asdasdasdad -every 1 day";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // Entering with whitespaces in attributes

        try {
            input = "repeat         -start 15 Nov 5 to 6 pm";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -start          ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -start          -every           ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -start          -every         -until      ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -every    aaa    -until      ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat   -start  -every  -on    -until   ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }


        try {
            input = "repeat      word     -until      ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      word     -until  wrong*!#*&^#!*^";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

    }

    @Test
    public final void testUpdateErrors() throws Exception {

        @SuppressWarnings("unused")
        Command update;

        System.out.println(HEADER_ERRORS_UPDATE);

        // Update event with random string in date, start and end time

        try {
            input = "update e1 meeting at KL -on asdjjadsljd asjdalsd";
            update = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update e1 -on asdjjadsljd asjdalsd";
            update = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // Update deadline with random string in date, start and end time

        try {
            input = "update d1 submit application -by asdjjadsljd asjdalsd";
            update = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update d1 -by asdjjadsljd asjdalsd";
            update = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

    }

    @Test
    public final void testUpdateRepeatDayErrors() throws Exception {

        @SuppressWarnings("unused")
        Command repeatDay;

        System.out.println(HEADER_ERRORS_UPDATE_DAY_PARAM);

        // Wrong sequence: repeatUntil

        try {
            input = "update r1 email daily report -until 15 Dec 2016 -start 15 Nov 5 to 6 pm -every 1 day";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 -every 1 day email daily report -until 15 Dec 2016 -start 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 -every adadsasd day -until adsasdasd -start asdasdad";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 -every 1 day -start asdasdad";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // Wrong keyword

        try {
            input = "update r1 daily report -every 2 days -start 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 daily report -everysa 2 days -started 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 daily report -everysa 2 days -started 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 daily report -every NINE days -start 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

    }

    @Test
    public final void testUpdateRepeatWeekErrors() throws Exception {

        @SuppressWarnings("unused")
        Command repeatWeek;

        System.out.println(HEADER_ERRORS_UPDATE_WEEK_PARAM);

        // Wrong sequence: day selected

        try {
            input = "update r4 email weekly report -on mon, sun -every 1 month email daily report "
                    + "-start 15 Nov 5 to 6 pm -until 15 Dec 2016 ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // Wrong keywords

        try {
            input = "update r4 email weekly report -onnnnnnn mon, sun -every 1 month email daily report "
                    + "-start 15 Nov 5 to 6 pm -until 15 Dec 2016 ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 email weekly report -on mon, sun -every 1 month email daily report "
                    + "-starting 15 Nov 5 to 6 pm untilwwwwww 15 Dec 2016 ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // Random string in attributes

        try {
            input = "update r4 email weekly report -on afasfafaf -every asfffaff"
                    + "-start 15 Nov 5 to 6 pm -until 15 Dec 2016 ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 email weekly report -on afasfafaf -every asfffaff"
                    + " -start afsasffasf -until afasfafsaf ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 -start afsasffasf -until afasfafsaf ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

    }

    @Test
    public final void testUpdateRepeatMonthErrors() throws Exception {

        @SuppressWarnings("unused")
        Command repeatMonth;

        System.out.println(HEADER_ERRORS_UPDATE_MONTH_PARAM);

        // Wrong sequence: start

        try {
            input = "update r4 email monthly report -every 1 month email daily report "
                    + "-start 15 Nov 5 to 6 pm -until 15 Dec 2016 ";
            repeatMonth = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // Random String in attributes

        try {
            input = "update r4 email monthly report -every 1 afsasfsfafs "
                    + "-start 15 Nov 5 to 6 pm -until 15 Dec 2016 ";
            repeatMonth = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 email monthly report -every adssadsadasd "
                    + "-start asdadasdasd -until asdasdasd ";
            repeatMonth = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 -every adssadsadasd -start asdadasdasd ";
            repeatMonth = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

    }

    @Test
    public final void testUpdateRepeatYearErrors() throws Exception {

        @SuppressWarnings("unused")
        Command repeatYear;

        System.out.println(HEADER_ERRORS_UPDATE_YEAR_PARAM);

        // Wrong sequence: repeatUntil

        try {
            input = "update r4 email yearly report -every 1 year email daily report -until 15 Dec 2016 -start 15 Nov 5 to 6 pm";
            repeatYear = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

        // Random String in attributes

        try {
            input = "update r4 email yearly report -every afafaf until afsafasf -start afasfaf";
            repeatYear = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = HEADER_ERRORS_INVALID_FORMAT;
            executeTestWithString();
        }

    }

    // ========================================================================
    // Methods to initialize variables during testing
    // ========================================================================

    private String todayDate() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.HOUR_OF_DAY, 0);
        Date todayDate = today.getTime();
        String todayString = todayDate.toString();

        return todayString;
    }

    private String tomorrowDate() {
        Calendar tomorrowDate = Calendar.getInstance();
        tomorrowDate.add(Calendar.DATE, 1);
        tomorrowDate.set(Calendar.HOUR, 0);
        tomorrowDate.set(Calendar.MINUTE, 0);
        tomorrowDate.set(Calendar.SECOND, 0);
        tomorrowDate.set(Calendar.HOUR_OF_DAY, 0);

        Date date = tomorrowDate.getTime();
        String tomorrowString = date.toString();

        return tomorrowString;
    }

    private ArrayList<String> initDayRepeat(Command repeatDay) {
        ArrayList<String> actual = new ArrayList<String>();

        actual.add(repeatDay.getCommandType().toString());
        actual.add(repeatDay.getTaskType());
        actual.add(repeatDay.getRepeatType());
        actual.add(String.valueOf(repeatDay.getTaskID()));
        actual.add(repeatDay.getTaskDescription());
        if(repeatDay.getDateAdded() != null) {
            actual.add(repeatDay.getDateAdded().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatDay.getRepeatStartTime());
        actual.add(repeatDay.getRepeatEndTime());
        actual.add(repeatDay.getDayInterval());
        if(repeatDay.getRepeatUntil() != null) {
            actual.add(repeatDay.getRepeatUntil().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatDay.getStopRepeatInString());

        return actual;
    }

    private ArrayList<String> initWeekRepeat(Command repeatWeek) {
        ArrayList<String> actual = new ArrayList<String>(); 

        actual.add(repeatWeek.getCommandType().toString());
        actual.add(repeatWeek.getTaskType());
        actual.add(repeatWeek.getRepeatType());
        actual.add(String.valueOf(repeatWeek.getTaskID()));
        actual.add(repeatWeek.getTaskDescription());
        if(repeatWeek.getDateAdded() != null) {
            actual.add(repeatWeek.getDateAdded().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatWeek.getRepeatStartTime());
        actual.add(repeatWeek.getRepeatEndTime());
        actual.add(repeatWeek.getWeekInterval());
        actual.add(repeatWeek.getDaySelectedString());
        if(repeatWeek.getRepeatUntil() != null) {
            actual.add(repeatWeek.getRepeatUntil().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatWeek.getStopRepeatInString());

        return actual;
    }

    private ArrayList<String> initMonthRepeat(Command repeatMonth) {
        ArrayList<String> actual = new ArrayList<String>();

        actual.add(repeatMonth.getCommandType().toString());
        actual.add(repeatMonth.getTaskType());
        actual.add(repeatMonth.getRepeatType());
        actual.add(String.valueOf(repeatMonth.getTaskID()));
        actual.add(repeatMonth.getTaskDescription());
        if(repeatMonth.getDateAdded() != null) {
            actual.add(repeatMonth.getDateAdded().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatMonth.getRepeatStartTime());
        actual.add(repeatMonth.getRepeatEndTime());
        actual.add(repeatMonth.getMonthInterval());
        if(repeatMonth.getRepeatUntil() != null) {
            actual.add(repeatMonth.getRepeatUntil().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatMonth.getStopRepeatInString());

        return actual;
    }

    private ArrayList<String> initYearRepeat(Command repeatYear) {
        ArrayList<String> actual = new ArrayList<String>();

        actual.add(repeatYear.getCommandType().toString());
        actual.add(repeatYear.getTaskType());
        actual.add(repeatYear.getRepeatType());
        actual.add(String.valueOf(repeatYear.getTaskID()));
        actual.add(repeatYear.getTaskDescription());
        if(repeatYear.getDateAdded() != null) {
            actual.add(repeatYear.getDateAdded().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatYear.getRepeatStartTime());
        actual.add(repeatYear.getRepeatEndTime());
        actual.add(repeatYear.getYearInterval());
        if(repeatYear.getRepeatUntil() != null) {
            actual.add(repeatYear.getRepeatUntil().toString());
        } else {
            actual.add(null);
        }
        actual.add(repeatYear.getStopRepeatInString());

        return actual;
    }

}
```

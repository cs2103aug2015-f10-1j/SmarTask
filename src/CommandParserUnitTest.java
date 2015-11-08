import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * Unit test for CommandParser
 * 
 * Test is divided into two main parts:
 * 1) Testing whether input can be processed correctly
 * 2) How CommandParser handles errors in inputs
 * 
 * @@author A0108235M-reused A0121795B
 */

public class CommandParserUnitTest {

    // Used in Command testing

    private static final String INVALID_FORMAT = "Command format is invalid";
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

        input = "sfp C:\\Users\\Jim\\Dropbox\\SmarTask\\Work\\worktask.txt";
        actual = new ArrayList<String>();
        command = CommandParser.parse(input);
        actual.add(command.getCommandType().toString());
        actual.add(command.getFilePath());
        expected = new ArrayList<String>(Arrays.asList("SETFILEPATH",
                "C:\\Users\\Jim\\Dropbox\\SmarTask\\Work\\worktask.txt"));
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

        input = "setfilepath C:\\Users\\Jim\\Dropbox\\storage.txt";
        actual = new ArrayList<String>();
        setFilepath = CommandParser.parse(input);
        actual.add(setFilepath.getCommandType().toString());
        actual.add(setFilepath.getFilePath());
        expected = new ArrayList<String>(Arrays.asList(
                "SETFILEPATH","C:\\Users\\Jim\\Dropbox\\storage.txt"));
        executeTestWithArrayList();

        // set filepath command

        input = "setfilepath C:\\Users\\Jim\\Desktop\\storage.txt";
        actual = new ArrayList<String>();
        setFilepath = CommandParser.parse(input);
        actual.add(setFilepath.getCommandType().toString());
        actual.add(setFilepath.getFilePath());
        expected = new ArrayList<String>(Arrays.asList(
                "SETFILEPATH","C:\\Users\\Jim\\Desktop\\storage.txt"));

        // set filepath command

        input = "setfilepath C:\\Users\\Jim\\Dropbox\\SmarTask\\Work\\worktask.txt";
        actual = new ArrayList<String>();
        setFilepath = CommandParser.parse(input);
        actual.add(setFilepath.getCommandType().toString());
        actual.add(setFilepath.getFilePath());
        expected = new ArrayList<String>(Arrays.asList("SETFILEPATH",
                "C:\\Users\\Jim\\Dropbox\\SmarTask\\Work\\worktask.txt"));
        executeTestWithArrayList();

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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // setfilepath using random string

        try {
            input = "sfp abcdefgh asdoaskjdkasld asjdlasldj";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // setfilepath using MAC / UNIX filepath

        try {
            input = "setfilepath C:/Users/Jim/SmarTask/bin/storage.txt";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // setfilepath using without naming text file

        try {
            input = "setfilepath C:\\Users\\Jim\\SmarTask\\bin";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // setfilepath using without naming text file

        try {
            input = "setfilepath C:\\Users\\Jim\\SmarTask\\bin\\.txt";
            filePath = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add whitespaces
        try {
            input = "add         ";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add deadline without description
        try {
            input = "add      -by 5 nov";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add deadline using keyword with whitespaces
        try {
            input = "add      -by         ";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add event without description
        try {
            input = "add     -on 6 Nov 9 to 10pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using invalid time
        try {
            input = "add     -on 6 Nov 9pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using invalid time
        try {
            input = "add meeting -on 6 Nov 9pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using invalid time
        try {
            input = "add meeting -on 9pm";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // add event using keyword with whitespaces
        try {
            input = "add     -on         ";
            add = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat asdasdasdad -start 15 Nov 5 to 6 pm";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat asdasdasdad -every 1 day";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // Entering with whitespaces in attributes

        try {
            input = "repeat         -start 15 Nov 5 to 6 pm";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -start          ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -start          -every           ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -start          -every         -until      ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      -every    aaa    -until      ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat   -start  -every  -on    -until   ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }


        try {
            input = "repeat      word     -until      ";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "repeat      word     -until  wrong*!#*&^#!*^";
            repeat = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update e1 -on asdjjadsljd asjdalsd";
            update = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // Update deadline with random string in date, start and end time

        try {
            input = "update d1 submit application -by asdjjadsljd asjdalsd";
            update = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update d1 -by asdjjadsljd asjdalsd";
            update = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 -every 1 day email daily report -until 15 Dec 2016 -start 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 -every adadsasd day -until adsasdasd -start asdasdad";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 -every 1 day -start asdasdad";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // Wrong keyword

        try {
            input = "update r1 daily report -every 2 days -start 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 daily report -everysa 2 days -started 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 daily report -everysa 2 days -started 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r1 daily report -every NINE days -start 15 Nov 5 to 6 pm";
            repeatDay = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 email weekly report -on mon, sun -every 1 month email daily report "
                    + "-starting 15 Nov 5 to 6 pm untilwwwwww 15 Dec 2016 ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 email weekly report -on afasfafaf -every asfffaff"
                    + " -start afsasffasf -until afasfafsaf ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 -start afsasffasf -until afasfafsaf ";
            repeatWeek = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 email monthly report -every adssadsadasd "
                    + "-start asdadasdasd -until asdasdasd ";
            repeatMonth = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        try {
            input = "update r4 -every adssadsadasd -start asdadasdasd ";
            repeatMonth = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
            expectedMsg = INVALID_FORMAT;
            executeTestWithString();
        }

        // Random String in attributes

        try {
            input = "update r4 email yearly report -every afafaf until afsafasf -start afasfaf";
            repeatYear = CommandParser.parse(input);
            fail("Should have thrown exception but did not!");
        } catch (Exception e) {
            actualMsg = e.getMessage();
            expectedMsg = INVALID_FORMAT;
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
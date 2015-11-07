import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Unit test for CommandParser
 * 
 * Testing is divided into two main parts:
 * 1) Testing whether input can be processed correctly
 * 2) How CommandParser handles errors in inputs
 * 
 * @author A0108235M
 */

public class CommandParserUnitTest {

    // Command testing

    private static final String HEADER_TWO_PARAM_COMMANDS = "\n----Two param----";
    private static final String HEADER_SINGLE_PARAM_COMMANDS = "\n----Single param----";

    private static final String HEADER_ADD_COMMAND = "\n----Add----";
    private static final String HEADER_REPEAT_COMMAND = "\n----REPEAT commands----";
    private static final String HEADER_UPDATE_COMMAND = "\n----Update----";
    private static final String HEADER_UPDATE_COMMAND_SELECTED_PARAM = "\n----Update selected param----";

    private static final String HEADER_UPDATE_REPEAT_COMMAND = "\n----Update: all param of repeat ----";
    private static final String HEADER_STOP_REPEAT_COMMAND = "\n----Stop repeat----";
    private static final String HEADER_UPDATE_REPEAT_DAY_PARAM = "\n----Update: selected param of Day repeat----";
    private static final String HEADER_UPDATE_REPEAT_WEEK_PARAM = "\n----Update: selected param of Week repeat----";
    private static final String HEADER_UPDATE_REPEAT_MONTH_PARAM = "\n----Update: selected param of Month repeat----";
    private static final String HEADER_UPDATE_REPEAT_YEAR_PARAM = "\n----Update: selected param of Year repeat----";

    // Error testing

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

    private void executeUnitTest() {
        printUnitTest();
        assertEquals(expected, actual);
        System.out.println();
    }

    private void printUnitTest() {
        System.out.println("   Input: " + input);
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
        executeUnitTest();

        input = "redo";
        actual = new ArrayList<String>();
        Command redo = CommandParser.parse(input);
        actual.add(redo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("REDO"));
        executeUnitTest();

        input = "invalid command here";
        actual = new ArrayList<String>();
        Command invalid = CommandParser.parse(input);
        actual.add(invalid.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("INVALID"));
        executeUnitTest();

        input = "exit";
        actual = new ArrayList<String>();
        Command exit = CommandParser.parse(input);
        actual.add(exit.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("EXIT"));
        executeUnitTest();

    }

    @Test
    public final void testSingleParamUpperCapsCommand() throws Exception {

        System.out.println(HEADER_SINGLE_PARAM_COMMANDS);

        input = "UNDO";
        actual = new ArrayList<String>();
        Command undo = CommandParser.parse(input);
        actual.add(undo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("UNDO"));
        executeUnitTest();

        input = "REDO";
        actual = new ArrayList<String>();
        Command redo = CommandParser.parse(input);
        actual.add(redo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("REDO"));
        executeUnitTest();

        input = "AAAAAAAAAAAADDDDDDDDDD";
        actual = new ArrayList<String>();
        Command invalid = CommandParser.parse(input);
        actual.add(invalid.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("INVALID"));
        executeUnitTest();

        input = "EXIT";
        actual = new ArrayList<String>();
        Command exit = CommandParser.parse(input);
        actual.add(exit.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("EXIT"));
        executeUnitTest();

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
        executeUnitTest();

        input = "complete e2";
        actual = new ArrayList<String>();
        Command completeEvent = CommandParser.parse(input);
        actual.add(completeEvent.getCommandType().toString());
        actual.add(completeEvent.getTaskType());
        actual.add(String.valueOf(completeEvent.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","event","2"));
        executeUnitTest();

        input = "complete f3";
        actual = new ArrayList<String>();
        Command completeFloat = CommandParser.parse(input);
        actual.add(completeFloat.getCommandType().toString());
        actual.add(completeFloat.getTaskType());
        actual.add(String.valueOf(completeFloat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","floating","3"));
        executeUnitTest();

        input = "complete r4";
        actual = new ArrayList<String>();
        Command completeRepeat = CommandParser.parse(input);
        actual.add(completeRepeat.getCommandType().toString());
        actual.add(completeRepeat.getTaskType());
        actual.add(String.valueOf(completeRepeat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("COMPLETE","repeat","4"));
        executeUnitTest();

        // Delete command

        input = "delete d1";
        actual = new ArrayList<String>();
        Command deleteDeadline = CommandParser.parse(input);
        actual.add(deleteDeadline.getCommandType().toString());
        actual.add(deleteDeadline.getTaskType());
        actual.add(String.valueOf(deleteDeadline.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","deadline","1"));
        executeUnitTest();

        input = "delete e2";
        actual = new ArrayList<String>();
        Command deleteEvent = CommandParser.parse(input);
        actual.add(deleteEvent.getCommandType().toString());
        actual.add(deleteEvent.getTaskType());
        actual.add(String.valueOf(deleteEvent.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","event","2"));
        executeUnitTest();

        input = "delete f3";
        actual = new ArrayList<String>();
        Command deleteFloat = CommandParser.parse(input);
        actual.add(deleteFloat.getCommandType().toString());
        actual.add(deleteFloat.getTaskType());
        actual.add(String.valueOf(deleteFloat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","floating","3"));
        executeUnitTest();

        input = "delete r4";
        actual = new ArrayList<String>();
        Command deleteRepeat = CommandParser.parse(input);
        actual.add(deleteRepeat.getCommandType().toString());
        actual.add(deleteRepeat.getTaskType());
        actual.add(String.valueOf(deleteRepeat.getTaskID()));
        expected = new ArrayList<String>(Arrays.asList("DELETE","repeat","4"));
        executeUnitTest();

        // Search command

        input = "search oneword";
        actual = new ArrayList<String>();
        Command search1 = CommandParser.parse(input);
        actual.add(search1.getCommandType().toString());
        actual.addAll(search1.getSearchKeyword());
        expected = new ArrayList<String>(Arrays.asList("SEARCH","oneword"));
        executeUnitTest();

        input = "search meeting office";
        actual = new ArrayList<String>();
        Command search2 = CommandParser.parse(input);
        actual.add(search2.getCommandType().toString());
        actual.addAll(search2.getSearchKeyword());
        expected = new ArrayList<String>(Arrays.asList("SEARCH","meeting","office"));
        executeUnitTest();

        // set filepath command

        input = "setfilepath C:/Users/Jim/Dropbox";
        actual = new ArrayList<String>();
        Command setFilepath = CommandParser.parse(input);
        actual.add(setFilepath.getCommandType().toString());
        actual.add(setFilepath.getFilePath());
        expected = new ArrayList<String>(Arrays.asList("SETFILEPATH","C:/Users/Jim/Dropbox"));
        executeUnitTest();

    }

    @Test
    public final void testAddCommand() throws Exception {

        System.out.println(HEADER_ADD_COMMAND);

        // Add event

        input = "add Meeting with Boss -on 26 Nov 5 to 6pm";
        actual = new ArrayList<String>();
        Command addEvent = CommandParser.parse(input);
        actual.add(addEvent.getCommandType().toString());
        actual.add(addEvent.getTaskType());
        actual.add(addEvent.getTaskDescription());
        actual.add(addEvent.getTaskEventStart());
        actual.add(addEvent.getTaskEventEnd());
        expected = new ArrayList<String>(Arrays.asList("ADD","event", "Meeting with Boss"
                , "Thu Nov 26 17:00:00 SGT 2015", "Thu Nov 26 18:00:00 SGT 2015"));
        executeUnitTest();

        // Add deadline

        input = "add Finish assignment -by 10 Dec 12pm";
        actual = new ArrayList<String>();
        Command addDeadline = CommandParser.parse(input);
        actual.add(addDeadline.getCommandType().toString());
        actual.add(addDeadline.getTaskType());
        actual.add(addDeadline.getTaskDescription());
        actual.add(addDeadline.getTaskDeadline());
        expected = new ArrayList<String>(Arrays.asList("ADD","deadline", "Finish assignment"
                , "Thu Dec 10 12:00:00 SGT 2015"));
        executeUnitTest();

        // Add floating

        input = "add Travel to LA";
        actual = new ArrayList<String>();
        Command addFloat = CommandParser.parse(input);
        actual.add(addFloat.getCommandType().toString());
        actual.add(addFloat.getTaskType());
        actual.add(addFloat.getTaskDescription());
        expected = new ArrayList<String>(Arrays.asList("ADD","floating", "Travel to LA"));
        executeUnitTest();

    }

    @Test
    public final void testRepeatCommand() throws Exception {

        System.out.println(HEADER_REPEAT_COMMAND);

        // Repeat by day

        input = "repeat submit daily report -start 15 Nov 5 to 6 pm -every 1 day -until 15 Dec 2016";
        actual = new ArrayList<String>();
        Command repeatDay = CommandParser.parse(input);
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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

        // Update week

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -every 2 week -on mon,fri -until 25 Dec";
        Command repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", "week", "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015","2", "2 6", "Fri Dec 25 00:00:00 SGT 2015", null));
        executeUnitTest();

        // Update month

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -every 1 month -until 15 Dec 2016";
        Command repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "month", "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

        // Update year

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm -every 1 year -until 15 Dec 2016";
        Command repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "year", "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

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
        executeUnitTest();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r1 email daily report -start 15 Nov 5 to 6 pm";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "1","email daily report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, null, null));
        executeUnitTest();

        // Excluding: repeatUntil, interval, repeat type, repeatStartTime, repeatEndtime,
        //            dateAdded

        input = "update r1 email daily report";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "1","email daily report",
                null, null, 
                null, null, null, null));
        executeUnitTest();

        // Excluding: repeatUntil, description

        input = "update r1 -start 15 Nov 5 to 6 pm -every 1 day";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", "day", "1", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", null, null));
        executeUnitTest();

        // Excluding: repeatUntil, description

        input = "update r1 -every 1 day";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", "day", "1", null,
                null, null, null, "1", null, null));
        executeUnitTest();

        // Excluding: repeatUntil, description

        input = "update r5 -until 15 Dec 2016";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", null, "5", null,
                null, null, null, null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

        // Excluding: description, interval, repeat type

        input = "update r1 -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatDay = CommandParser.parse(input);
        actual = initDayRepeat(repeatDay);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", null, "1", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

        //"update r1 email daily report -start 15 Nov 5 to 6 pm -every 1 day -until 15 Dec 2016";

    }

    @Test
    public final void testUpdateRepeatCommandWithSelectedParam() throws Exception {

        Command repeatWeek;

        System.out.println(HEADER_UPDATE_REPEAT_WEEK_PARAM);

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "week", "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", "2", "1 4 7", "Fri Dec 25 00:00:00 SGT 2015", null));
        executeUnitTest();

        // Excluding: repeatUntil

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","week", "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", "2", "1 4 7", null, null));
        executeUnitTest();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r2 email weekly report -start 25 Dec 9 to 11am -on tue, thu";
        repeatWeek = CommandParser.parse(input);
        actual = initWeekRepeat(repeatWeek);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "2","email weekly report",
                "Fri Dec 25 00:00:00 SGT 2015", "Fri Dec 25 09:00:00 SGT 2015", 
                "Fri Dec 25 11:00:00 SGT 2015", null, "3 5" ,null, null));
        executeUnitTest();

    }

    @Test
    public final void testUpdateMonthRepeatCommand() throws Exception {

        System.out.println(HEADER_UPDATE_REPEAT_MONTH_PARAM);

        // All attributes

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -every 1 month -until 15 Dec 2016";

        Command repeatMonth = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", "month", "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

        // Excluding: repeatUntil

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm -every 1 month";
        actual = new ArrayList<String>();
        Command repeatMonth1 = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth1);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","month", "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", null, null));
        executeUnitTest();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r3 email monthly report -start 15 Nov 5 to 6 pm";
        actual = new ArrayList<String>();
        Command repeatMonth2 = CommandParser.parse(input);
        actual = initMonthRepeat(repeatMonth2);
        expected = new ArrayList<String>(Arrays.asList("UPDATE", "repeat", null, "3","email monthly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, null, null));
        executeUnitTest();

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
        executeUnitTest();

        // Excluding: repeatUntil, interval, repeat type

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm -every 1 year";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","year", "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", null, null));
        executeUnitTest();

        // Excluding: repeatUntil, interval, repeat type  

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat",null, "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, null, null));
        executeUnitTest();


        // Only description

        input = "update r4 email yearly report";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat",null, "4", "email yearly report",
                null, null, null, null, null, null));
        executeUnitTest();

        // Excluding: interval, repeat type

        input = "update r4 email yearly report -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat", null, "4", "email yearly report",
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

        // Excluding: description

        input = "update r4 -start 15 Nov 5 to 6 pm -every 1 year -until 15 Dec 2016";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","year", "4", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", "1", "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

        // Excluding: description, interval, repeatType

        input = "update r4 -start 15 Nov 5 to 6 pm -until 15 Dec 2016";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat",null, "4", null,
                "Sun Nov 15 00:00:00 SGT 2015", "Sun Nov 15 17:00:00 SGT 2015", 
                "Sun Nov 15 18:00:00 SGT 2015", null, "Thu Dec 15 00:00:00 SGT 2016", null));
        executeUnitTest();

        // Excluding: repeatUntil, dateAdded, startTime, endTime

        input = "update r4 email yearly report -every 1 year";
        repeatYear = CommandParser.parse(input);
        actual = initYearRepeat(repeatYear);
        expected = new ArrayList<String>(Arrays.asList("UPDATE","repeat","year", "4", "email yearly report",
                null, null, null, "1", null, null));
        executeUnitTest();

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
        executeUnitTest();

        input = "redo aaaaaaaaaaaaaaaaa";
        actual = new ArrayList<String>();
        Command redo = CommandParser.parse(input);
        actual.add(redo.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("REDO"));
        executeUnitTest();

        input = "exit aaaaaaaaaaaa";
        actual = new ArrayList<String>();
        Command exit = CommandParser.parse(input);
        actual.add(exit.getCommandType().toString());
        expected = new ArrayList<String>(Arrays.asList("EXIT"));
        executeUnitTest();

    }

    @Test
    public final void testSetFilePathErrors() throws Exception {

        System.out.println(HEADER_ERRORS_SETFILEPATH);

    }

    @Test
    public final void testAddErrors() throws Exception {

        System.out.println(HEADER_ERRORS_ADD);

        input = "add -on 26 Nov 5 to 6pm";

    }

    @Test
    public final void testRepeatErrors() throws Exception {

        System.out.println(HEADER_ERRORS_REPEAT);

    }

    @Test
    public final void testUpdateErrors() throws Exception {

        System.out.println(HEADER_ERRORS_UPDATE);

    }

    @Test
    public final void testRepeatDayErrors() throws Exception {

        System.out.println(HEADER_ERRORS_UPDATE_DAY_PARAM);


    }

    @Test
    public final void testRepeatWeekErrors() throws Exception {

        System.out.println(HEADER_ERRORS_UPDATE_WEEK_PARAM);


    }

    @Test
    public final void testRepeatMonthErrors() throws Exception {

        System.out.println(HEADER_ERRORS_UPDATE_MONTH_PARAM);


    }

    @Test
    public final void testRepeatYearErrors() throws Exception {

        System.out.println(HEADER_ERRORS_UPDATE_YEAR_PARAM);


    }

    // ========================================================================
    // Methods to initialize ArrayList during testing
    // ========================================================================

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
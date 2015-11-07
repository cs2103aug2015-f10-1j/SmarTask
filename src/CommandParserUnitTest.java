import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Unit test for CommandParser
 * 
 * @author A0108235M
 */

public class CommandParserUnitTest {

    private static final String TWO_PARAM_COMMANDS = "\n----Two param commands----";
    private static final String SPECIAL_CASES_IN_SINGLE_PARAM = "\n----Special cases - single param ----";
    private static final String SINGLE_PARAM_COMMANDS = "\n----Single param commands----";
    private static final String ADD_COMMAND = "\n----ADD commands----";
    private static final String UPDATE_COMMAND = "\n----UPDATE commands----";
    private static final String REPEAT_COMMAND = "\n----REPEAT commands----";
    private static final String UPDATE_REPEAT_COMMAND = "\n----UPDATE repeat commands----";
    private static final String STOP_REPEAT_COMMAND = "\n----STOP_REPEAT commands----";
    private static final String ERRORS = "\n----ERRORS inputs----";
       
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

        System.out.println(SINGLE_PARAM_COMMANDS);

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
    public final void testSpecialCasesForSingleParamCommand() throws Exception {

        System.out.println(SPECIAL_CASES_IN_SINGLE_PARAM);

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
    public final void testTwoParamCommand() throws Exception {

        System.out.println(TWO_PARAM_COMMANDS);

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
        
        System.out.println(ADD_COMMAND);
        
        // Add event

        input = "add Meeting with Boss on 26 Nov 5 to 6pm";
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

        input = "add Finish assignment by 10 Dec 12pm";
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
        
        System.out.println(REPEAT_COMMAND);

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
        
        System.out.println(UPDATE_COMMAND);
        
    }
    
    @Test
    public final void testUpdateRepeatCommand() throws Exception {
        
        System.out.println(UPDATE_REPEAT_COMMAND);
        
    }
    
    @Test
    public final void testStopRepeatCommand() throws Exception {
        
        System.out.println(STOP_REPEAT_COMMAND);
        
    }
    
    @Test
    public final void testErrorInput() throws Exception {
        
        System.out.println(ERRORS);
        
    }

}
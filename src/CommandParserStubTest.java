import java.util.Date;
import java.util.List;

import org.w3c.dom.css.ElementCSSInlineStyle;

import com.joestelmach.natty.*;

/**
 * CommandParserStubTest checks if the CommandParser returns the correct Command object
 * by using stubs command.
 * 
 * @author Bobby Lin
 *
 */

public class CommandParserStubTest {

    public static void main(String[] args) throws Exception {

        //=================================================================================
        //============================Adding Recurrence tasks==============================
        //=================================================================================

        // Daily
        try {
            Command repeat = CommandParser.parse("repeat daily -on 15 Nov 5 to 6 pm -every 2 day -until 15 Dec 2016");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Day Interval: " 
            + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Daily
        try {
            Command repeat = CommandParser.parse("repeat daily -on 15 Dec 8 to 9 pm -every 2 day");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Day Interval: " 
            + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Yearly
        try {
            Command repeat = CommandParser.parse("repeat yearly -on 15 Dec 2 to 5 pm -every 1 year");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Year Interval: " 
            + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Yearly
        try {
            Command repeat = CommandParser.parse("repeat yearly -on 15 Nov 5 to 6 pm -every 1 year -until 25 Dec 2020");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Year Interval: " 
            + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Weekly
        try {
            Command repeat = CommandParser.parse("repeat weekly -on 25 Dec 9 to 11am -every 2 week -for sun, sat, wed -until 25 Dec 15");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Week Interval: " 
            + repeat.getWeekInterval() + " ==Until:" + repeat.getRepeatUntil());

            for(int i =0; i<repeat.getIsDaySelected().length; i++) {
                if(repeat.getIsDaySelected()[i] == true)
                    System.out.println("DAY " + (i+1));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Weekly
        try {
            Command repeat = CommandParser.parse("repeat weekly -on 25 Dec 9 to 11am -every 2 week -for mon");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Week Interval: " 
            + repeat.getWeekInterval() + " ==Until:" + repeat.getRepeatUntil());

            for(int i = 0; i<repeat.getIsDaySelected().length; i++) {
                if(repeat.getIsDaySelected()[i] == true)
                    System.out.println("DAY " + (i+1));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Monthly
        try {
            Command repeat = CommandParser.parse("repeat monthly -on 25 Dec 9 to 11am -every 2 week -for mon");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Month Interval: " 
            + repeat.getMonthInterval() + " ==Until:" + repeat.getRepeatUntil());

            for(int i = 0; i<repeat.getIsDaySelected().length; i++) {
                if(repeat.getIsDaySelected()[i] == true)
                    System.out.println("DAY " + (i+1));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*repeat meeting -on 15 Dec 4 to 6pm -every 1 month -for 2 mon
          repeat meeting -on 27 Dec 1 to 2pm -every 2 month -for 23 -until 25 Dec
          repeat meeting -on 20 Dec 9 to 10am -every 2 month -for last 
          repeat meeting -on 21 Dec 8 to 11am -every 1 month -for 1 sun -until 25 Dec
         */

        //=================================================================================
        //============================Deleting Recurrence tasks============================
        //=================================================================================

        // This will delete the whole recurrence task
        try {
            Command repeat = CommandParser.parse("delete R1");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + repeat.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //=================================================================================
        //============================Temporarily stop recurrence==========================
        //=================================================================================

        // Stop recurrence on specifc frequency
        try {
            Command repeat = CommandParser.parse("stop R1 {15/10/15, 20/10/15}");
            System.out.print(repeat.getCommandType());
            for(int i=0; i< repeat.getStopRepeat().size(); i++ ) {
                System.out.print(" " + repeat.getStopRepeat().get(i));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Stop recurrence on specifc frequency
        try {
            Command repeat = CommandParser.parse("stop R1 {mon, tue, sat}");
            System.out.print(repeat.getCommandType());
            for(int i=0; i< repeat.getStopRepeat().size(); i++ ) {
                System.out.print(" " + repeat.getStopRepeat().get(i));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //=================================================================================
        //================================Adding tasks=====================================
        //=================================================================================

        // Adding event task
        try {
            Command add = CommandParser.parse("add Meeting with Boss on 10 Nov 5 to 6pm");
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription() 
            + " ==Start== " + add.getTaskEventStart() + " ==End== " + add.getTaskEventEnd());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding deadline task
        try {
            Command add = CommandParser.parse("add Finish assignment by 10 Dec 12pm");
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription() 
            + " " + add.getTaskDeadline());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding floating task
        try {
            Command add = CommandParser.parse("add Read textbook");
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    } 

}

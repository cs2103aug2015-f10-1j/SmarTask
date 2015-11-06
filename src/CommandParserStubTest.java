/**
 * CommandParserStubTest checks if the CommandParser returns the correct Command
 * object by using stubs command.
 * 
 * @author A0108235M
 */

public class CommandParserStubTest {

    public static void main(String[] args) throws Exception {

        // ==================================================
        // Adding day recurrence tasks
        // ==================================================

        try {
            Command repeat = CommandParser.parse("repeat daily -start 15 Nov 5 to 6 pm -every 2 day -until 15 Dec 2016");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType()
            + " ==Day Interval: " + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil() + " " + repeat.getStopRepeatInString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("repeat daily -start 15 Dec 8 to 9 pm -every 2 day");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType()
            + " ==Day Interval: " + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ==================================================
        // Adding year recurrence tasks
        // ==================================================

        try {
            Command repeat = CommandParser.parse("repeat yearly -start 15 Dec 2 to 5 pm -every 1 year");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType()
            + " ==Year Interval: " + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser
                    .parse("repeat yearly -start 15 Nov 5 to 6 pm -every 1 year -until 25 Dec 2020");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType()
            + " ==Year Interval: " + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ==================================================
        // Adding week recurrence tasks
        // ==================================================

        try {
            Command repeat = CommandParser
                    .parse("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType()
            + " ==Week Interval: " + repeat.getWeekInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser
                    .parse("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on mon, wed, fri");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType()
            + " ==Week Interval: " + repeat.getWeekInterval() + " Day selected: "
            + repeat.getDaySelectedString() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ==================================================
        // Adding month recurrence tasks
        // ==================================================

        try {
            Command repeat = CommandParser.parse("repeat meeting -start 27 Dec 1 to 2pm -every 2 month -until 25 Dec");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + ", "
            + repeat.getRepeatType() + " ==Month Interval: " + repeat.getMonthInterval() + " ==Until:"
            + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ==================================================
        // Adding month recurrence tasks
        // ==================================================

        try {
            Command repeat = CommandParser.parse("repeat meeting -start 27 Dec 1 to 2pm -every 1 month -until 25 Dec");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded()
            + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime() + ", "
            + repeat.getRepeatType() + " ==Month Interval: " + repeat.getMonthInterval() + " ==Until:"
            + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // =========================================================================
        // Updating a recurrence task
        // =========================================================================

        try {
            Command repeat = CommandParser.parse(
                    "update r10111512 team project meeting -start 19 Nov 9 to 11am -every 5 week -on sun, mon, sat -until 25 Dec 2019");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + " " + repeat.getTaskID()
            + " " + repeat.getTaskDescription() + " " + " Day selected: " + repeat.getDaySelectedString()
            + " Interval " + repeat.getWeekInterval() + "==Until: " + repeat.getRepeatUntil() + " "
            + repeat.getDateAdded() + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("update r10111512 team project meeting -start 19 Nov 9 to 11am");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + " " + repeat.getTaskID()
            + " " + repeat.getTaskDescription() + " " + " Day selected: " + repeat.getDaySelectedString()
            + " Interval " + repeat.getWeekInterval() + " " + repeat.getRepeatUntil() + " "
            + repeat.getDateAdded() + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("update r10111512 team project meeting -every 5 week -until 25 Dec");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + " " + repeat.getTaskID()
            + " " + repeat.getTaskDescription() + " " + " Day selected: " + repeat.getDaySelectedString()
            + " Interval " + repeat.getWeekInterval() + " " + repeat.getRepeatUntil() + " "
            + repeat.getDateAdded() + " " + repeat.getRepeatStartTime() + " " + repeat.getRepeatEndTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // =========================================================================
        // Deleting whole recurrence task
        // =========================================================================

        try {
            Command repeat = CommandParser.parse("delete R1");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + repeat.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // =========================================================================
        // Temporarily stop recurrence
        // =========================================================================

        try {
            Command repeat = CommandParser.parse("stop r1 15 oct, 25 nov, 6 aug");
            System.out.println(
                    repeat.getCommandType() + " ID: " + repeat.getTaskID() + " " + repeat.getTaskType() + " " + repeat.getStopRepeatInString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // =================================================================================
        // Adding tasks
        // =================================================================================

        // Adding event task
        try {
            Command add = CommandParser.parse("add Meeting with Boss on 10 Nov 5 to 6pm");
            System.out.println(add.getCommandType() + " " + add.getTaskType() + " " + add.getTaskDescription()
            + " ==Start== " + add.getTaskEventStart() + " ==End== " + add.getTaskEventEnd());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding deadline task
        try {
            Command add = CommandParser.parse("add Finish assignment by 10 Dec 12pm");
            System.out.println(add.getCommandType() + " " + add.getTaskType() + " " + add.getTaskDescription() + " "
                    + add.getTaskDeadline());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding floating task
        try {
            Command add = CommandParser.parse("add Read textbook");
            System.out.println(add.getCommandType() + " " + add.getTaskType() + " " + add.getTaskDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // =========================================================================
        // Updating an event task
        // =========================================================================

        try {
            Command event = CommandParser.parse("update e1 team project meeting 5 Nov 5 to 6pm");
            System.out.println(event.getCommandType() + " " + event.getTaskDescription() + " " + 
                    event.getTaskEventDate() + " " + event.getTaskEventEnd());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // =========================================================================
        // Updating a deadline task
        // =========================================================================

        try {
            Command event = CommandParser.parse("update d1 team project meeting 5/11/2015");
            System.out.println(event.getCommandType() + " " + event.getTaskDescription() + "==" + 
                    event.getTaskDeadline());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // =========================================================================
        // Completing a task
        // =========================================================================

        try {
            Command complete = CommandParser.parse("complete d1");
            System.out.println(complete.getCommandType() + " " + complete.getTaskType() + " " + complete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}

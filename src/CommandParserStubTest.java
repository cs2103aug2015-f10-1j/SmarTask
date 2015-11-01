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
        //============================Testing Recurrence tasks=====================================
        //=================================================================================
        // Command: repeat <task description> hh:mm-hh:mm dd/mm/yyyy {<period> , <frequency>, <until dd/mm/yy or forever>}

        // Daily: repeat <task description> hh:mm-hh:mm dd/mm/yyyy {day , every X day, Until dd/mm/yy or forever}
        try {
            Command repeat = CommandParser.parse("repeat meeting -on 15 Nov 5 to 6 pm -every 2 day -until 25 Dec 15");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Interval: " + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Daily: repeat <task description> hh:mm-hh:mm dd/mm/yyyy {day , every X day, Until dd/mm/yy or forever}
        try {
            Command repeat = CommandParser.parse("repeat meeting -on 15 Nov 5 to 6 pm -every 2 day");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskDescription() + " " + repeat.getDateAdded() + " " + repeat.getRepeatStartTime()
            + " " + repeat.getRepeatEndTime() + " " + repeat.getRepeatType() + " ==Interval: " + repeat.getDayInterval() + " ==Until:" + repeat.getRepeatUntil());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // This will delete the whole recurrence task
        try {
            Command repeat = CommandParser.parse("delete R1");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + repeat.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription() + " ==Start== " + add.getTaskEventStart() + " ==End== " + add.getTaskEventEnd());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding deadline task
        try {
            Command add = CommandParser.parse("add Finish assignment by 10 Dec 12pm");
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription() + " " + add.getTaskDeadline());
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

        /*
        // view task from a specific day
        try {
            Command view = CommandParser.parse("view <09/10/2015>");
            System.out.println(view.getCommandType() + " " + view.getTaskTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // search task with keywords
        try {
            Command search = CommandParser.parse("search meeting jardine Singapore");
            String keywords = "";
            for(int index = 0 ; index < search.getSearchKeyword().size(); index++) {
                keywords = keywords + search.getSearchKeyword().get(index) + " - ";
            }
            System.out.println(search.getCommandType() + " " + keywords);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("============================Testing exception handling============================");
        // Test exception handling for adding task
        try {
            System.out.println("add <dsffdadsf safsdfsf");
            Command add = CommandParser.parse("add <dsffdadsf safsdfsf");
            System.out.println(add.getCommandType() + " " + add.getTaskTitle() + " " + add.getTaskTime());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test exception handling for updating task
        try {
            System.out.println("update <2> <Ar <09/10/2015>");
            Command add = CommandParser.parse("update <2> <Ar <09/10/2015>");
            System.out.println(add.getCommandType() + " " + add.getTaskTitle() + " " + add.getTaskTime());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test exception handling for deleting task
        try {
            System.out.println("delete <1 <09/10201");
            Command delete = CommandParser.parse("delete <1 <09/10201");
            System.out.println(delete.getCommandType() + " " + delete.getTaskNumber() + " " + delete.getTaskTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test exception handling for complete task
        try {
            System.out.println("complete <1a> <09adasdaf/10/2015>");
            Command complete = CommandParser.parse("complete <1a> <09adasdaf/10/2015>");
            System.out.println(complete.getCommandType() + " " + complete.getTaskNumber() + " " +complete.getTaskTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // [TO DEBUG: Test exception handling for view task from a specific day]
        try {
            Command view = CommandParser.parse("view <abc <129302");
            System.out.println("Error: " + view.getCommandType() + " " + view.getTaskTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        CommandParser.viewCommandParserLog();

        System.out.println("=================================Testing assertion================================");

        // Test assertion handling for undo
        try {
            Command undo = CommandParser.parse(null);
            System.out.println(undo.getCommandType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test assertion handling for undo
        try {
            Command redo = CommandParser.parse(null);
            System.out.println(redo.getCommandType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("This message should not appear");
         */
    } 

}

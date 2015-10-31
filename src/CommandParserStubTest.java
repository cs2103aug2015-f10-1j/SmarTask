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
            Command repeat = CommandParser.parse("repeat team meeting 18:00-20:00 14/10/2015 {day, 1, forever}");
            System.out.println(repeat.getCommandType() + " " + repeat.getDateOfRepeatAdded() + " " + repeat.getTaskRepeatDuration() + " " 
                    + repeat.getTaskDescription() + " " + repeat.getTaskRepeatType()  + " " +repeat.getTaskRepeatDayFrequency() 
                    + " " + repeat.getTaskRepeatEndDate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Weekly: repeat <task description> hh:mm-hh:mm dd/mm/yyyy {week , <Mon-Sun>,  <until dd/mm/yy or forever>}
        try {
            Command repeat = CommandParser.parse("repeat team meeting 08:00-10:00 09/11/2015 {week, mon/tue/wed ,forever}");
            System.out.println(repeat.getCommandType() + " " + repeat.getDateOfRepeatAdded() + " " + repeat.getTaskRepeatDuration() + " " 
                    + repeat.getTaskDescription() + " " + repeat.getTaskRepeatType()  + " " + repeat.getTaskRepeatEndDate());
            Boolean[] week = repeat.isTaskRepeatOnDayOfWeek();
            for(int i =0; i< week.length; i++) {
                if(week[i]) {
                    System.out.println("Day " + (i + 1) + " of Week");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("repeat team meeting 08:00-10:00 09/11/2015 {week, sun/thu/wed ,forever}");
            System.out.println(repeat.getCommandType() + " " + repeat.getDateOfRepeatAdded() + " " + repeat.getTaskRepeatDuration() + " " 
                    + repeat.getTaskDescription() + " " + repeat.getTaskRepeatType()  + " " + repeat.getTaskRepeatEndDate());
            Boolean[] week = repeat.isTaskRepeatOnDayOfWeek();
            for(int i =0; i< week.length; i++) {
                if(week[i]) {
                    System.out.println("Day " + (i + 1) + " of Week");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        // Monthly: repeat month {Duration X-Y time, Repeat: Every X month, On Xst/nd/rd/th of month or every third <Mon-Sun> of month, Until Date or Forever}
        try {
            Command repeat = CommandParser.parse("repeat team meeting 09:00-13:00 29/10/2015 {month, on 19, 15/06/2016}");
            System.out.println(repeat.getCommandType() + " " + repeat.getDateOfRepeatAdded() + " " + repeat.getTaskRepeatDuration() + " " 
                    + repeat.getTaskDescription() + " " + repeat.getTaskRepeatType()  + " " +repeat.getTaskRepeatMonthFrequency() 
                    + " " + repeat.getTaskRepeatEndDate());
            System.out.println("Repeating Monthly on day : " + repeat.getTaskRepeatMonthFrequencyBySpecificDate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("repeat team meeting 09:00-13:00 29/10/2015 {month, 2-sun, 15/06/2016}");
            System.out.println(repeat.getCommandType() + " " + repeat.getDateOfRepeatAdded() + " " + repeat.getTaskRepeatDuration() + " " 
                    + repeat.getTaskDescription() + " " + repeat.getTaskRepeatType()  + " " +repeat.getTaskRepeatMonthFrequency() 
                    + " " + repeat.getTaskRepeatEndDate());
            System.out.println("Repeating monthly on : " + repeat.getTaskRepeatMonthFrequencyBySpecificDayOfWeek()[1] + " of week " +
                    repeat.getTaskRepeatMonthFrequencyBySpecificDayOfWeek()[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Yearly: repeat <task description> hh:mm-hh:mm dd/mm/yyyy {year , every X year,  <until dd/mm/yy or forever>}
        try {
            Command repeat = CommandParser.parse("repeat team meeting 14:00-18:00 19/12/2015 {year, 1, forever}");
            System.out.println(repeat.getCommandType() + " " + repeat.getDateOfRepeatAdded() + " " + repeat.getTaskRepeatDuration() + " " 
                    + repeat.getTaskDescription() + " " + repeat.getTaskRepeatType()  + " " +repeat.getTaskRepeatYearFrequency() 
                    + " " + repeat.getTaskRepeatEndDate());
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
        //============================Updating Recurrence tasks============================
        //=================================================================================
        try {
            Command repeat = CommandParser.parse("update R3 team meeting");
            System.out.println(repeat.getCommandType()  + " " + repeat.getTaskType() + " " + repeat.getTaskID() + " " + repeat.getTaskDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("update R4 18:00-20:00 14/10/2015");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + repeat.getTaskID() + " "  
                    +  repeat.getTaskEventDate() + " " + repeat.getTaskEventTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("update R2 team meeting 18:00-20:00 14/10/2015");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + repeat.getTaskID() + " " +  repeat.getTaskEventDate() + " " 
                    + repeat.getTaskEventTime() + " " +   repeat.getTaskDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Command repeat = CommandParser.parse("update R1 team meeting 18:00-20:00 14/10/2015 {day, 1, forever}");
            System.out.println(repeat.getCommandType() + " " + repeat.getTaskType() + " " + repeat.getTaskID() + " " +  repeat.getTaskEventDate() + 
                    " " + repeat.getTaskEventTime() + " " + repeat.getTaskDescription());
            for(int i =0; i < repeat.getUpdateRepeat().size(); i++) {
                System.out.println("Attribute "+ (i+1) +" " + repeat.getUpdateRepeat().get(i));
            }
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

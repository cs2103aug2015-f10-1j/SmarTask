
/**
 * CommandParserStubTest checks if the CommandParser returns the correct Command object
 * by using stubs command.
 * 
 * @author Bobby Lin
 *
 */

public class CommandParserStubTest {

    public static void main(String[] args) throws Exception {

        // invalid command      
        try {
            Command invalid = CommandParser.parse("This is an invalid command");
            System.out.println(invalid.getCommandType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // exit
        try {
            Command exit = CommandParser.parse("exit");
            System.out.println(exit.getCommandType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // undo
        try {
            Command undo = CommandParser.parse("undo");
            System.out.println(undo.getCommandType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // redo
        try {
            Command redo = CommandParser.parse("redo");
            System.out.println(redo.getCommandType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // complete task
        try {
            Command complete = CommandParser.parse("complete D1");
            System.out.println(complete.getCommandType() + " " + complete.getTaskType() + " " + complete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // complete task
        try {
            Command complete = CommandParser.parse("complete F1");
            System.out.println(complete.getCommandType() + " " + complete.getTaskType() + " " + complete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // complete task
        try {
            Command complete = CommandParser.parse("complete E1");
            System.out.println(complete.getCommandType() + " " + complete.getTaskType() + " " + complete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        // Adding event task
        try {
            Command add = CommandParser.parse("add Meeting with Boss>>09/10/2015>>13:00-14:00");
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription() + " " + add.getTaskEventDate() + " " + add.getTaskEventTime());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding deadline task
        try {
            Command add = CommandParser.parse("add Meeting with Boss>>09/10/2015 12:00");
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription() + " " + add.getTaskDeadline());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding floating task
        try {
            Command add = CommandParser.parse("add Meeting with Boss");
            System.out.println(add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Deleting deadline task
        try {
            Command delete = CommandParser.parse("delete D1");
            System.out.println(delete.getCommandType() + " " + delete.getTaskType() + " " + delete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Deleting event task
        try {
            Command delete = CommandParser.parse("delete E2");
            System.out.println(delete.getCommandType() + " " + delete.getTaskType() + " " + delete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Deleting floating task
        try {
            Command delete = CommandParser.parse("delete F4");
            System.out.println(delete.getCommandType() + " " + delete.getTaskType() + " " + delete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Deleting floating task
        try {
            Command delete = CommandParser.parse("delete f4");
            System.out.println(delete.getCommandType() + " " + delete.getTaskType() + " " + delete.getTaskID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //=======================================================================================================
        //====================================Testing Updating of Deadline tasks=================================
        //=======================================================================================================

        // Update description and deadline
        try {
            Command update = CommandParser.parse("update D1 Arrange meeting 09/10/2015 09:00");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + 
                    " "  + update.getTaskDescription() + " " + update.getTaskDeadline());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Update deadline
        try {
            Command update = CommandParser.parse("update D2 09/10/2015 09:00");
            if(update.getTaskDescription().isEmpty()) {
                System.out.println("No description");
            }
            System.out.println(update.getCommandType() + " " + update.getTaskDescription() + update.getTaskID() + " " + update.getTaskDeadline());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Update description
        try {
            Command update = CommandParser.parse("update D3 Arrange meeting");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + 
                    " "  + update.getTaskDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //=======================================================================================================
        //====================================Testing Updating of Floating tasks=================================
        //=======================================================================================================

        try {
            Command update = CommandParser.parse("update F4 This is a floating task");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + " "  + update.getTaskDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //=======================================================================================================
        //====================================Testing Updating of Event tasks====================================
        //=======================================================================================================

        // Update description, date and duration
        try {
            Command update = CommandParser.parse("update E5 Arrange meeting 09/10/2015 13:00-14:00");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + 
                    " "  + update.getTaskDescription() + " " + update.getTaskEventDate() + " " + update.getTaskEventTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Update description and duration
        try {
            Command update = CommandParser.parse("update E6 Arrange meeting 13:00-14:00");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + 
                    " "  + update.getTaskDescription() + " " + update.getTaskEventTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Update date
        try {
            Command update = CommandParser.parse("update E7 09/10/2015");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + " " + update.getTaskEventDate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Updating duration
        try {
            Command update = CommandParser.parse("update E8 13:00-14:00");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + " " + update.getTaskEventTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Update description
        try {
            Command update = CommandParser.parse("update E9 Marketing Research Survey");
            System.out.println(update.getCommandType() + " " + update.getTaskID() + 
                    " "  + update.getTaskDescription());
        } catch (Exception e) {
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

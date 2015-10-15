/**
 * CommandParserStubTest checks if the CommandParser returns the correct Command object
 * by using stubs command.
 * 
 * @author Bobby Lin
 *
 */

public class CommandParserStubTest {

    public static void main(String[] args) {
        // Test exception handling during adding task
        try {
            Command add = CommandParser.parse("add <dsffdadsf safsdfsf");
            System.out.println(add.getCommandType() + " " + add.getTaskTitle() + " " + add.getTaskTime());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Adding task
        try {
            Command add2 = CommandParser.parse("add <Meeting with Boss> <09/10/2015 12:00>");
            System.out.println(add2.getCommandType() + " " + add2.getTaskTitle() + " " + add2.getTaskTime());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test exception handling during adding task
        try {
            Command add = CommandParser.parse("update <2> <Ar <09/10/2015>");
            System.out.println(add.getCommandType() + " " + add.getTaskTitle() + " " + add.getTaskTime());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Updating task
        try {
            Command update = CommandParser.parse("update <2> <Arrange meeting with customer> <09/10/2015>");
            System.out.println(update.getCommandType() + " " + update.getTaskNumber() + 
                    " "  + update.getTaskTitle() + " " + update.getTaskTime());
        } catch (Exception e) {
            // TODO: handle exception
        }

        // deleting task
        try {
            Command delete = CommandParser.parse("delete <1> <09/10/2015>");
            System.out.println(delete.getCommandType() + " " + delete.getTaskNumber() + " " + delete.getTaskTime());
        } catch (Exception e7) {
            // TODO Auto-generated catch block
            e7.printStackTrace();
        }

        // view task from a specific day
        try {
            Command view = CommandParser.parse("view <09/10/2015>");
            System.out.println(view.getCommandType() + " " + view.getTaskTime());
        } catch (Exception e6) {
            // TODO Auto-generated catch block
            e6.printStackTrace();
        }

        // complete task
        try {
            Command complete = CommandParser.parse("complete <1> <09/10/2015>");
            System.out.println(complete.getCommandType() + " " + complete.getTaskNumber() + " " +complete.getTaskTime());
        } catch (Exception e5) {
            // TODO Auto-generated catch block
            e5.printStackTrace();
        }


        // search task with keywords
        try {
            Command search = CommandParser.parse("search meeting jardine Singapore");
            String keywords = "";
            for(int index = 0 ; index < search.getSearchKeyword().size(); index++) {
                keywords = keywords + search.getSearchKeyword().get(index) + " - ";
            }
            System.out.println(search.getCommandType() + " " + keywords);
        } catch (Exception e4) {
            // TODO Auto-generated catch block
            e4.printStackTrace();
        }

        // undo

        try {
            Command undo = CommandParser.parse("undo");
            System.out.println(undo.getCommandType());
        } catch (Exception e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }

        // redo

        try {
            Command redo = CommandParser.parse("redo");
            System.out.println(redo.getCommandType());
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }


        // invalid command      
        try {
            Command invalid = CommandParser.parse("This is an invalid command");
            System.out.println(invalid.getCommandType());
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        // exit
        try {
            Command exit = CommandParser.parse("exit");
            System.out.println(exit.getCommandType());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    } 

}

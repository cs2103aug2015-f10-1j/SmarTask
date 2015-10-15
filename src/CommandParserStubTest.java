/**
 * CommandParserStubTest checks if the CommandParser returns the correct Command object
 * by using stubs command.
 * 
 * @author Bobby Lin
 *
 */

public class CommandParserStubTest {

    public static void main(String[] args) {
        // adding task
        Command add = CommandParser.parse("add <meeting with team-mates> <09/10/2015 18:00>");
        System.out.println(add.getCommandType() + " " + add.getTaskTitle() + " " + add.getTaskTime());

        // updating task
        Command update = CommandParser.parse("update <2> <Arrange meeting with customer> <09/10/2015>");
        System.out.println(update.getCommandType() + " " + update.getTaskNumber() + 
                " "  + update.getTaskTitle() + " " + update.getTaskTime());

        // deleting task
        Command delete = CommandParser.parse("delete <1> <09/10/2015>");
        System.out.println(delete.getCommandType() + " " + delete.getTaskNumber() + " " + delete.getTaskTime());

        // view task from a specific day
        Command view = CommandParser.parse("view <09/10/2015>");
        System.out.println(view.getCommandType() + " " + view.getTaskTime());
        
        // complete task
        Command complete = CommandParser.parse("complete <1> <09/10/2015>");
        System.out.println(complete.getCommandType() + " " + complete.getTaskNumber() + " " +complete.getTaskTime());
        
        // search task with keywords
        Command search = CommandParser.parse("search meeting jardine Singapore");
        String keywords = "";
        for(int index = 0 ; index < search.getSearchKeyword().size(); index++) {
            keywords = keywords + search.getSearchKeyword().get(index) + " - ";
        }
        System.out.println(search.getCommandType() + " " + keywords);

        // undo
        Command undo = CommandParser.parse("undo");
        System.out.println(undo.getCommandType());

        // redo
        Command redo = CommandParser.parse("redo");
        System.out.println(redo.getCommandType());

        // invalid command
        Command invalid = CommandParser.parse("This is an invalid command");
        System.out.println(invalid.getCommandType());
        
        // exit
        Command exit = CommandParser.parse("exit");
        System.out.println(exit.getCommandType());

    }

}

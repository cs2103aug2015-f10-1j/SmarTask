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
        Command addCommand = CommandParser.parse("add <meeting with team-mates> <09/10/2015 18:00>");
        System.out.println(addCommand.getCommandType() + " " + addCommand.getTaskTitle() + " " + addCommand.getTaskTime());

        // updating task
        Command updateCommand = CommandParser.parse("update <2> <Arrange meeting with customer> <09/10/2015>");
        System.out.println(updateCommand.getCommandType() + " " + updateCommand.getTaskNumber() + 
                " "  + updateCommand.getTaskTitle() + " " + updateCommand.getTaskTime());

        // deleting task
        Command deleteCommand = CommandParser.parse("delete <1> <09/10/2015>");
        System.out.println(deleteCommand.getCommandType() + " " + deleteCommand.getTaskNumber() + " " + deleteCommand.getTaskTime());

        // view task from a specific day
        Command viewCommand = CommandParser.parse("view <09/10/2015>");
        System.out.println(viewCommand.getCommandType() + " " + viewCommand.getTaskTime());

        // undo
        Command undo = CommandParser.parse("undo");
        System.out.println(undo.getCommandType());

        // redo
        Command redo = CommandParser.parse("redo");
        System.out.println(redo.getCommandType());

        // invalid command
        Command invalid = CommandParser.parse("sfhasfhasfjfas");
        System.out.println(invalid.getCommandType());
        
        // exit
        Command exit = CommandParser.parse("exit");
        System.out.println(exit.getCommandType());

    }

}

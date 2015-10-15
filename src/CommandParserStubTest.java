

public class CommandParserStubTest {

    public static void main(String[] args) {
        Command command = CommandParser.parse("add <meeting with team-mates> <09/10/2015 18:00>");
        System.out.println(command.getCommandType() + " " + command.getTaskTitle() + " " + command.getTaskTime());
    }

}

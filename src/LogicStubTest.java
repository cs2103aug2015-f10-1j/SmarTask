import java.io.FileNotFoundException;

public class LogicStubTest {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        Logic logic = new Logic();
        try {
            logic.executeCommand("add meeting with team-mates");
            logic.executeCommand("add meeting with team-mates for integration>>08/10/2015>>18:00-20:00");
            logic.executeCommand("add meeting with team-mates for integration>>08/10/2015 18:00");
            logic.executeCommand("add testing program component");
            logic.executeCommand("add meeting with team-mates for integration>>08/10/2015 18:00");
            // Logic.executeCommand("view <09/10/2015>");
            // Logic.executeCommand("delete <2> <09/10/2015>");
            // Logic.executeCommand("update <2> <UPDATED> <09/10/2015>"));
            // Logic.executeCommand("view <09/10/2015>"));

            System.out.println(logic.getMessageLog());
            System.out.println(logic.getFloatingTask());
            System.out.println(logic.getEvents());
            System.out.println(logic.getDeadline());


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

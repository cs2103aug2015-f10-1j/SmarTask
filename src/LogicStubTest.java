import java.io.FileNotFoundException;

public class LogicStubTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Logic logic = new Logic();
		try {
			logic.executeCommand("update r1 team project meeting -start 19 Nov 9 to 11am");
			// Logic.executeCommand("view <09/10/2015>");
			// Logic.executeCommand("delete <2> <09/10/2015>");
			// Logic.executeCommand("update <2> <UPDATED> <09/10/2015>"));
			// Logic.executeCommand("view <09/10/2015>"));
			
			System.out.println(logic.getFloatingTask());
			System.out.println(logic.getEvents());
			System.out.println(logic.getDeadline());
			System.out.println(logic.getMessageLog());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

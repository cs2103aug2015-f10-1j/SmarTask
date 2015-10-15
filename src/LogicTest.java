import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;

public class LogicTest {
    Logic logic = new Logic();
    @Test
    public void test() {
	Logic logic = new Logic();
	//logic.executeCommand("add <meeting with team-mates> <09/10/2015 18:00>");
	//logic.executeCommand("add <testing program component> <09/10/2015 14:00>");
	//logic.executeCommand("add <meeting with team-mates for integration> <08/10/2015 18:00>");
	//logic.printArrayList();
	try {
	    System.out.print(logic.executeCommand("view <09/10/2015>"));
	    System.out.print(logic.executeCommand("delete <2> <09/10/2015>"));
	    System.out.print(logic.executeCommand("view <09/10/2015>"));
	    //System.out.print(logic.executeCommand("update <2> <UPDATED> <09/10/2015>"));
	    //System.out.print(logic.executeCommand("view <09/10/2015>"));
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}

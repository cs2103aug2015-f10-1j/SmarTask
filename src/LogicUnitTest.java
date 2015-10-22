import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogicUnitTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
	
	// expectList contains the answer that are expected in the operation
	static ArrayList<String> expectList;

    @Test
    public final void testLogic() {
      //  fail("Not yet implemented"); // TODO
      expectList = new ArrayList <String>();
    }

    @Test
    public final void testExecuteCommand() throws Exception {
      //  fail("Not yet implemented"); // TODO
      Logic logic = new Logic ();
      expectList.clear();
      expectList.add("add Meeting with Boss successful!");
      expectList.add("task updated!");
      expectList.add("add Meeting successful");
      expectList.add("deleted floating index 1 successfully!");
      expectList.add("");
      logic.executeCommand("add Meeting with Boss>>09/10/2015 12:00");
      logic.executeCommand("update D1 09/10/2015 09:00");
      logic.executeCommand("add Meeting");
      logic.executeCommand("delete F1");
      
      
    	
    }

    @Test
    public final void testPrintArrayList() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetMessageLog() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetEvents() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetDeadline() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetFloatingTask() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testObject() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetClass() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testHashCode() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testEquals() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testClone() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testToString() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testNotify() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testNotifyAll() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testWaitLong() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testWaitLongInt() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testWait() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testFinalize() {
        fail("Not yet implemented"); // TODO
    }

}

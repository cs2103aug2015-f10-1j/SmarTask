import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Unit test for Logic
 * 
 * Testing whether input can be processed correctly
 * Only cover add,update,delete,view, search, complete
 * 
 * @@author A0125994R
 */



public class LogicUnitTest {
	/*
	 * @BeforeClass public static void setUpBeforeClass() throws Exception { }
	 * @AfterClass public static void tearDownAfterClass() throws Exception { }
	 * @Before public void setUp() throws Exception { }
	 * @After public void tearDown() throws Exception { }
	 */
	// expectList contains the answer that are expected in the operation
	
	public static final String MESSAGE_INVALID_INDEX = "Cannot find the item to delete!!";
	public static final String MESSAGE_NO_CURRENT_TASK = "There is no task due on today.";
	public static final String MESSAGE_SEARCH_NOT_FOUND = "Results not found!";
	public static final String MESSAGE_DEADLINE_EMPTY = "There is no deadline task to delete!!";
    public static final String MESSAGE_EVENT_EMPTY = "There is no event task to delete!!";
    public static final String MESSAGE_NOTHING_TO_DELETE = "Cannot find the item to delete!!";
    public static final String MESSAGE_NO_FLOATING_TO_DELETE = "There is no floating task to delete!!";
    public static final String MESSAGE_NOTHING_TO_UPDATE = "Please enter a valid index. There is nothing to update.";
    public static final String MESSAGE_NO_FLOATING_TO_COMPLETE = "There is no floating task to complete!!";
	
	
	public ArrayList<String> actual; 
    public ArrayList<String> expected;
    public String actualMsg;
    public String expectedMsg;
    String input;
    public static String output;

	// @Test
	/*
	 * public final void testExecuteCommand() throws Exception { // fail(
	 * "Not yet implemented"); // TODO Logic logic = new Logic ();
	 * expectList.clear(); expectList.add("add Meeting with Boss successful!");
	 * expectList.add("task updated!"); expectList.add("add Meeting successful"
	 * ); expectList.add("deleted floating index 1 successfully!");
	 * expectList.add("undo successfully"); expectList.add(
	 * "add Meeting with Boss successful!"); logic.executeCommand(
	 * "add Meeting with Boss>>09/10/2015 12:00"); logic.executeCommand(
	 * "update D1 09/10/2015 09:00"); logic.executeCommand("add Meeting");
	 * logic.executeCommand("delete F1"); logic.executeCommand("undo");
	 * logic.executeCommand("add Meeting with Boss>>09/10/2015>>13:00-14:00"); }
	 */
	@Test
	public final void testAddTask() throws Exception {
		input = "add meeting with feifei";
		actual = new ArrayList<String>();
		expected = new ArrayList<String>();
		expected.add("command : add meeting with feifei");
		expected.add("add meeting with feifei successful!");
		Logic.executeCommand(input);
		output=expected.get(0) + "\n" + expected.get(1);
		assertEquals(output, Logic.getMessageLog());
		
		input = "add Meeting with Boss -on 10 Nov 5 to 6pm";
        actual = new ArrayList<String>();
        expected = new ArrayList<String>();
        expected.add(output +"\n"+"command : add Meeting with Boss -on 10 Nov 5 to 6pm");
        expected.add("add Meeting with Boss successful!");
        Logic.executeCommand(input);
        output=expected.get(0) + "\n" + expected.get(1);
        assertEquals(output, Logic.getMessageLog());
	}
	
	@Test
	public final void testUpdateTask() throws Exception {
	    input = "update F1 haha";
		expected = new ArrayList<String>();
		expected.add(output);
		expected.add("command : "+input+"\n"+MESSAGE_NOTHING_TO_UPDATE);
		System.out.println(Logic.getFloatingTask());
		Logic.executeCommand(input);
		output =expected.get(0) + "\n" + expected.get(1);
		assertEquals(output, Logic.getMessageLog());
	}

	@Test
	public final void testDeleteTask() throws Exception {
		input = "delete F2";
		actual = new ArrayList<String>();
		expected = new ArrayList<String>();
		expected.add(output+"\ncommand : "+input);
		expected.add(MESSAGE_NO_FLOATING_TO_DELETE);
		Logic.executeCommand(input);
		output = expected.get(0) + "\n" + expected.get(1);
		assertEquals(output, Logic.getMessageLog());
		
		input = "delete d1";
		actual = new ArrayList<String>();
        expected = new ArrayList<String>();
        expected.add(output+"\ncommand : "+input);
        expected.add(MESSAGE_DEADLINE_EMPTY);
        System.out.println(Logic.getDeadline());
        Logic.executeCommand(input);
        output = expected.get(0) + "\n" + expected.get(1);
        assertEquals(output, Logic.getMessageLog());
        
        input = "delete E1";
        actual = new ArrayList<String>();
        expected = new ArrayList<String>();
        expected.add(output+"\ncommand : "+input);
        expected.add(MESSAGE_EVENT_EMPTY);
        System.out.println(Logic.getEvents());
        Logic.executeCommand(input);
        output = expected.get(0) + "\n" + expected.get(1);
        assertEquals(output, Logic.getMessageLog());
        
        input = "delete r1";
        actual = new ArrayList<String>();
        expected = new ArrayList<String>();
        expected.add(output+"\ncommand : "+input);
        expected.add(MESSAGE_NOTHING_TO_DELETE);
        System.out.println(Logic.getEvents());
        Logic.executeCommand(input);
        output = expected.get(0) + "\n" + expected.get(1);
        assertEquals(output, Logic.getMessageLog());
     
	}

	@Test
	public final void testViewTask() throws Exception {
		expected = new ArrayList<String>();
		input = "view";
		expected.add(output+"\n"+"command : "+input);
		expected.add(MESSAGE_NO_CURRENT_TASK);
		Logic.executeCommand(input);
		output = expected.get(0) + "\n" + expected.get(1);
		assertEquals(output, Logic.getMessageLog());
	}
	
	@Test
    public final void testSearchTask() throws Exception {
        expected = new ArrayList<String>();
        input = "search baby";
        expected.add(output+"\n"+"command : "+input);
        expected.add(MESSAGE_SEARCH_NOT_FOUND);
        Logic.executeCommand(input);
        output = expected.get(0) + "\n" + expected.get(1);
        assertEquals(output, Logic.getMessageLog());
    }
	
	@Test
    public final void testCompleteTask() throws Exception {
        expected = new ArrayList<String>();
        input = "complete f1";
        expected.add(output+"\n"+"command : "+input);
        expected.add(MESSAGE_NO_FLOATING_TO_COMPLETE);
        Logic.executeCommand(input);
        output = expected.get(0) + "\n" + expected.get(1);
        assertEquals(output, Logic.getMessageLog());
    }

	
}

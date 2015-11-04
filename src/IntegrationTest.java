import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class IntegrationTest {
	
	IntegrationTest inte = new IntegrationTest();
	Logic logic = new Logic();
	
	String output = "";
	String expected = "";
	
	@Test
	// Test the features including add different types of tasks
	public void testphase1() throws Exception {

		// add floating tasks
		logic.executeCommand("add buy patatoes");
		output = logic.getFloatingTask();
		expected = 

		initialize();
		// add event tasks
		
		initialize();
		// add deadline tasks
		
		initialize();
	}
	
	@Test
	// Test the features including modifying or deleting tasks
	public void testphase2() throws IOException {
		// update tasks
		
		initialize();
		// delete tasks
		
		initialize();
		// complete tasks
		
		initialize();
	}
	
	@Test
	// Test the features including recurring tasks
	public void testphase3() throws IOException {
		initialize();
	}
	
	@Test
	// Test the features including storage functions
	public void testphase4() throws IOException {
		// search task
		
		initialize();
		// set file path
		
		initialize();
	}
	
	private void initialize() {
		output = "";
		expected = "";
	}
}

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class IntegrationTest {
	
	IntegrationTest inte = new IntegrationTest();
	Logic logic = new Logic();
	
	File testFile;
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	private String output = "";
	private String expected = "";

	
	@Test
	// Test the features including add different types of tasks
	public void testphase1() throws Exception {

		// add floating tasks
		logic.executeCommand("add buy potatoes");
		output = logic.getMessageLog();
		expected = "add buy potatoes successful!";
		
		assertEquals(expected, output);
		initialize();
		
		logic.executeCommand("add buy potatoes");
		output = readFile(testFile);
		expected = "{\"type\":\"FLOATING\",\"id\":4284924,\"description\":\"buy patatoes\",\"isComplete\":false}";
		
		assertEquals(expected, output);
		initialize();
		
		// add event tasks
		logic.executeCommand("add Meeting with Boss on 10 Nov 5 to 6pm");
		output = logic.getMessageLog();
		expected = "add Meeting with Boss successful!";
		
		assertEquals(expected, output);
		initialize();
		
		logic.executeCommand("add Meeting with Boss on 10 Nov 5 to 6pm");
		output = readFile(testFile);
		expected = "{\"type\":\"EVENT\",\"id\":4312564,\"description\":\"Meeting with Boss\",\"eventStart\":\"Tue Nov 10 17:00:00 SGT 2015\",\"eventEnd\":\"Tue Nov 10 18:00:00 SGT 2015\",\"isComplete\":false}";
		
		assertEquals(expected, output);
		initialize();
		
		// add deadline tasks
		logic.executeCommand("add Finish assignment by 10 Dec 12pm");
		output = logic.getMessageLog();
		expected = "add Meeting with Boss successful!";
		
		assertEquals(expected, output);
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
	
	//----------------Utility methods------------------------------	
	private String readFile(File testFile) {
		String content = "";
		initReader(testFile);
		try {
			content = reader.readLine();
			if (!(content == null)) {
			System.out.println(content);
			//savePath = savePath + path;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeReader();
		return content;
	}
	
	private int getID(){
    	DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    	Calendar cal = Calendar.getInstance();
    	String s = dateFormat.format(cal.getTime());
    	DateFormat dateFormat2 = new SimpleDateFormat("HHmmss");
        Date date = new Date();
    	String sID = dateFormat2.format(date);
    	int sNum = Integer.parseInt(s);
    	int sIDNum = Integer.parseInt(sID);
    	int taskCode = sNum + sIDNum;
    	return taskCode;
	}
	
	// -------------------Initialization methods -----------------------------
	private void initialize() {
		File tempFile = new File(testFile.getAbsolutePath());
		//PrintWriter pw = new PrintWriter(tempFile);
		writer.print("");
		writer.close();
		testFile.delete();
		tempFile.renameTo(testFile);
		
		output = "";
		expected = "";
	}
	
	private boolean initReader(File taskFile) {
		try {
			reader = new BufferedReader(new FileReader(taskFile));
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

	private void closeReader() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

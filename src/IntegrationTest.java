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

/**
 * 
 * @author A0130034A and 
 */

public class IntegrationTest {

	Logic logic = new Logic();

	File testFile;

	private BufferedReader reader;
	private PrintWriter writer;
<<<<<<< HEAD
	
	private String outputCommand = "";
	private String expectedCommand = "";
	private String outputStorage = "";
	private String expectedStorage = "";
=======
	private String output = "";
	private String expected = "";
>>>>>>> 1ebd9348f9204ac17bfee4347dfa80ff6ee1ceb2

	@Test
	// Test the features including add different types of tasks
	public void testphase1() throws Exception {

		// add floating tasks
		logic.executeCommand("add buy potatoes");
		
		outputCommand = logic.getMessageLog();
		expectedCommand = "add buy potatoes successful!";
//		initialize();
//		logic.executeCommand("add buy potatoes");
		outputStorage = readFile(testFile);
		expectedStorage = "{\"type\":\"FLOATING\",\"id\":4284924,\"description\":\"buy patatoes\",\"isComplete\":false}";
		
		assertEquals(expectedCommand, outputCommand);
		assertEquals(expectedStorage, outputStorage);
		
		initialize();

		// add event tasks
		logic.executeCommand("add Meeting with Boss on 10 Nov 5 to 6pm");
		
		outputCommand = logic.getMessageLog();
		expectedCommand = "add Meeting with Boss successful!";

		outputStorage = readFile(testFile);
		expectedStorage = "{\"type\":\"EVENT\",\"id\":4312564,\"description\":\"Meeting with Boss\",\"eventStart\":\"Tue Nov 10 17:00:00 SGT 2015\",\"eventEnd\":\"Tue Nov 10 18:00:00 SGT 2015\",\"isComplete\":false}";
		
		assertEquals(expectedCommand, outputCommand);
		assertEquals(expectedStorage, outputStorage);
		
		initialize();

		// add deadline tasks
		logic.executeCommand("add Finish assignment by 10 Dec 12pm");
		
		outputCommand = logic.getMessageLog();
		expectedCommand = "add Finish assignment successful!";

		outputStorage = readFile(testFile);
		expectedStorage = "{\"type\":\"EVENT\",\"id\":4312564,\"description\":\"Meeting with Boss\",\"eventStart\":\"Tue Nov 10 17:00:00 SGT 2015\",\"eventEnd\":\"Tue Nov 10 18:00:00 SGT 2015\",\"isComplete\":false}";
		
		assertEquals(expectedCommand, outputCommand);
		assertEquals(expectedStorage, outputStorage);
		
		initialize();
	}

	@Test
	// Test the features including modifying or deleting tasks
	public void testphase2() throws Exception {
		// update tasks
		
		assertEquals(expectedCommand, outputCommand);
		assertEquals(expectedStorage, outputStorage);
		initialize();
		// delete tasks
		logic.executeCommand("add buy potatoes");
		logic.executeCommand("delete F1");
		
		outputCommand = logic.getMessageLog();
		expectedCommand = "deleted floating index 1 successfully!";
		outputStorage = readFile(testFile);
		expectedStorage = "";
		
		assertEquals(expectedCommand, outputCommand);
		assertEquals(expectedStorage, outputStorage);

		initialize();
		
		// complete tasks

		logic.executeCommand("add Finish assignment by 10 Dec 12pm");
		logic.executeCommand("complete D1");
		
		outputCommand = logic.getMessageLog();
		expectedCommand = "completed deadline index 1";
		outputStorage = readFile(testFile);
		expectedStorage = "{\"type\":\"DEADLINE\",\"id\":4336029,\"description\":\"Finish assignment\",\"deadline\":\"Thu Dec 10 12:00:00 SGT 2015\",\"isComplete\":true}";
		
		assertEquals(expectedCommand, outputCommand);
		assertEquals(expectedStorage, outputStorage);

		initialize();
	}

	@Test
	// Test the features including recurring tasks
	public void testphase3() throws Exception {
		// add daily recurring tasks
		logic.executeCommand("repeat daily -start 15 Nov 5 to 6 pm -every 2 day -until 15 Dec 2016");
		output = logic.getMessageLog();
		expected = "addrc daily successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat daily -start 15 Nov 5 to 6 pm -every 2 day -until 15 Dec 2016");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6115646,\"description\":\"daily\",\"taskRepeatType\":\"day\",\"dateAdded\":\"Nov 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"17:00:00\",\"taskRepeatEndTime\":\"18:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Day\":\"2\",\"taskRepeatUntil\":\"Dec 15, 2016 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
		
		logic.executeCommand("repeat daily -start 15 Dec 8 to 9 pm -every 2 day");
		output = logic.getMessageLog();
		expected = "addrc daily successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat daily -start 15 Dec 8 to 9 pm -every 2 day");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6115750,\"description\":\"daily\",\"taskRepeatType\":\"day\",\"dateAdded\":\"Dec 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"20:00:00\",\"taskRepeatEndTime\":\"21:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Day\":\"2\",\"taskRepeatUntil\":\"Dec 1, 9999 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
		
		// add weekly recurring tasks
		logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec 15");
		output = logic.getMessageLog();
		expected = "addrc weekly successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec 15");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6123533,\"description\":\"weekly\",\"taskRepeatType\":\"week\",\"dateAdded\":\"Dec 25, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"09:00:00\",\"taskRepeatEndTime\":\"11:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Week\":\"2\",\"isDaySelected\":[null,true,false,false,true,false,false,true],\"taskRepeatUntil\":\"Dec 25, 2015 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
		
		logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on mon, wed, fri");
		output = logic.getMessageLog();
		expected = "addrc weekly successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on mon, wed, fri");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6123551,\"description\":\"weekly\",\"taskRepeatType\":\"week\",\"dateAdded\":\"Dec 25, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"09:00:00\",\"taskRepeatEndTime\":\"11:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Week\":\"2\",\"isDaySelected\":[null,false,true,false,true,false,true,false],\"taskRepeatUntil\":\"Dec 1, 9999 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
		
		// add monthly recurring tasks
		logic.executeCommand("repeat meeting -start 27 Dec 1 to 2pm -every 2 month -until 25 Dec");
		output = logic.getMessageLog();
		expected = "addrc meeting successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat meeting -start 27 Dec 1 to 2pm -every 2 month -until 25 Dec");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6124826,\"description\":\"meeting\",\"taskRepeatType\":\"month\",\"dateAdded\":\"Dec 27, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"13:00:00\",\"taskRepeatEndTime\":\"14:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Month\":\"2\",\"taskRepeatUntil\":\"Dec 25, 2015 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
				
		logic.executeCommand("repeat meeting -start 27 Dec 1 to 2pm -every 1 month -until 25 Dec");
		output = logic.getMessageLog();
		expected = "addrc meeting successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat meeting -start 27 Dec 1 to 2pm -every 1 month -until 25 Dec");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6124845,\"description\":\"meeting\",\"taskRepeatType\":\"month\",\"dateAdded\":\"Dec 27, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"13:00:00\",\"taskRepeatEndTime\":\"14:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Month\":\"1\",\"taskRepeatUntil\":\"Dec 25, 2015 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
		
		// add yearly recurring tasks
		logic.executeCommand("repeat yearly -start 15 Dec 2 to 5 pm -every 1 year");
		output = logic.getMessageLog();
		expected = "addrc yearly successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat yearly -start 15 Dec 2 to 5 pm -every 1 year");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6116773,\"description\":\"yearly\",\"taskRepeatType\":\"year\",\"dateAdded\":\"Dec 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"14:00:00\",\"taskRepeatEndTime\":\"17:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Year\":\"1\",\"taskRepeatUntil\":\"Dec 1, 9999 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
		
		logic.executeCommand("repeat yearly -start 15 Nov 5 to 6 pm -every 1 year -until 25 Dec 2020");
		output = logic.getMessageLog();
		expected = "addrc yearly successful!";

		assertEquals(expected, output);
		initialize();

		logic.executeCommand("repeat yearly -start 15 Nov 5 to 6 pm -every 1 year -until 25 Dec 2020");
		output = readFile(testFile);
		expected = "{\"type\":\"REPEAT\",\"id\":6117167,\"description\":\"yearly\",\"taskRepeatType\":\"year\",\"dateAdded\":\"Nov 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"17:00:00\",\"taskRepeatEndTime\":\"18:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Year\":\"1\",\"taskRepeatUntil\":\"Dec 25, 2020 12:00:00 AM\",\"isComplete\":false}";

		assertEquals(expected, output);
		initialize();
		
		// delete recurring tasks
		logic.executeCommand("delete R1");
		output = logic.getMessageLog();
		expected = "deleted repeat index 1 successfully!";

		assertEquals(expected, output);
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

	// ----------------Utility methods------------------------------
	private String readFile(File testFile) {
		String content = "";
		initReader(testFile);
		try {
			content = reader.readLine();
			if (!(content == null)) {
				System.out.println(content);
				// savePath = savePath + path;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeReader();
		return content;
	}

	private int getID() {
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
		// PrintWriter pw = new PrintWriter(tempFile);
		writer.print("");
		writer.close();
		testFile.delete();
		tempFile.renameTo(testFile);

		
		outputCommand = "";
		expectedCommand = "";
		outputStorage = "";
		expectedStorage = "";

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

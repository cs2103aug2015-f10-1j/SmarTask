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
 * @@author A0130034A and A0116633L
 */

public class IntegrationTest {

    public static final File FILE_TASKFILE = new File(".." + File.separator + "SmarTask" + File.separator + "bin" + File.separator + "storage.txt");

    private BufferedReader reader;
    private PrintWriter writer;

    private String outputCommand = "";
    private String expectedCommand = "";
    private String outputStorage = "";
    private String expectedStorage = "";
    private String output = "";
    private String expected = "";

    @Test
    // Test the features including add different types of tasks
    public void testphase1() throws Exception {

        // add floating tasks
        Logic.executeCommand("add buy potatoes");

        outputCommand = Logic.getMessageLog();
        expectedCommand = "add buy potatoes successful!";
 
        outputStorage = readFile(FILE_TASKFILE);
        expectedStorage = "{\"type\":\"FLOATING\",\"id\":4284924,\"description\":\"buy patatoes\",\"isComplete\":false}";

        assertEquals(expectedCommand, outputCommand);
        assertEquals(expectedStorage, outputStorage);

        initialize();

        // add event tasks
        Logic.executeCommand("add Meeting with Boss -on 10 Nov 5 to 6pm");

        outputCommand = Logic.getMessageLog();
        expectedCommand = "add Meeting with Boss successful!";

        outputStorage = readFile(FILE_TASKFILE);
        expectedStorage = "{\"type\":\"EVENT\",\"id\":4312564,\"description\":\"Meeting with Boss\",\"eventStart\":\"Tue Nov 10 17:00:00 SGT 2015\",\"eventEnd\":\"Tue Nov 10 18:00:00 SGT 2015\",\"isComplete\":false}";

        assertEquals(expectedCommand, outputCommand);
        assertEquals(expectedStorage, outputStorage);

        initialize();

        // add deadline tasks
        Logic.executeCommand("add Finish assignment -by 10 Dec 12pm");

        outputCommand = Logic.getMessageLog();
        expectedCommand = "add Finish assignment successful!";

        outputStorage = readFile(FILE_TASKFILE);
        expectedStorage = "{\"type\":\"EVENT\",\"id\":4312564,\"description\":\"Meeting with Boss\",\"eventStart\":\"Tue Nov 10 17:00:00 SGT 2015\",\"eventEnd\":\"Tue Nov 10 18:00:00 SGT 2015\",\"isComplete\":false}";

        assertEquals(expectedCommand, outputCommand);
        assertEquals(expectedStorage, outputStorage);

        initialize();
    }

    @Test
    // Test the features including modifying or deleting tasks
    public void testphase2() throws Exception {
        // update tasks
        Logic.executeCommand("add Finish assignment -by 10 Dec 12pm");
        Logic.executeCommand("update d1 -by 9 Nov 10pm");
        
        outputCommand = Logic.getMessageLog();
        expectedCommand = "task is successfully updated!!";
        outputStorage = readFile(FILE_TASKFILE);
        expectedStorage = "{\"type\":\"DEADLINE\",\"id\":8324231,\"description\":\"Finish assignment\",\"deadline\":\"Mon Nov 09 22:00:00 SGT 2015\",\"isComplete\":false}";

        assertEquals(expectedCommand, outputCommand);
        assertEquals(expectedStorage, outputStorage);
        initialize();
        // delete tasks
        Logic.executeCommand("add buy potatoes");
        Logic.executeCommand("delete F1");

        outputCommand = Logic.getMessageLog();
        expectedCommand = "deleted floating index 1 successfully!";
        outputStorage = readFile(FILE_TASKFILE);
        expectedStorage = "";

        assertEquals(expectedCommand, outputCommand);
        assertEquals(expectedStorage, outputStorage);

        initialize();

        // complete tasks

        Logic.executeCommand("add Finish assignment -by 10 Dec 12pm");
        Logic.executeCommand("complete D1");

        outputCommand = Logic.getMessageLog();
        expectedCommand = "completed deadline index 1";
        outputStorage = readFile(FILE_TASKFILE);
        expectedStorage = "{\"type\":\"DEADLINE\",\"id\":4336029,\"description\":\"Finish assignment\",\"deadline\":\"Thu Dec 10 12:00:00 SGT 2015\",\"isComplete\":true}";

        assertEquals(expectedCommand, outputCommand);
        assertEquals(expectedStorage, outputStorage);

        initialize();
    }

    @Test
    // Test the features including recurring tasks
    public void testphase3() throws Exception {
        // add daily recurring tasks
        Logic.executeCommand("repeat daily -start 15 Nov 5 to 6 pm -every 2 day -until 15 Dec 2016");
        output = Logic.getMessageLog();
        expected = "addrc daily successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat daily -start 15 Nov 5 to 6 pm -every 2 day -until 15 Dec 2016");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6115646,\"description\":\"daily\",\"taskRepeatType\":\"day\",\"dateAdded\":\"Nov 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"17:00:00\",\"taskRepeatEndTime\":\"18:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Day\":\"2\",\"taskRepeatUntil\":\"Dec 15, 2016 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat daily -start 15 Dec 8 to 9 pm -every 2 day");
        output = Logic.getMessageLog();
        expected = "addrc daily successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat daily -start 15 Dec 8 to 9 pm -every 2 day");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6115750,\"description\":\"daily\",\"taskRepeatType\":\"day\",\"dateAdded\":\"Dec 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"20:00:00\",\"taskRepeatEndTime\":\"21:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Day\":\"2\",\"taskRepeatUntil\":\"Dec 1, 9999 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        // add weekly recurring tasks
        Logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec 15");
        output = Logic.getMessageLog();
        expected = "addrc weekly successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on sun, sat, wed -until 25 Dec 15");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6123533,\"description\":\"weekly\",\"taskRepeatType\":\"week\",\"dateAdded\":\"Dec 25, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"09:00:00\",\"taskRepeatEndTime\":\"11:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Week\":\"2\",\"isDaySelected\":[null,true,false,false,true,false,false,true],\"taskRepeatUntil\":\"Dec 25, 2015 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on mon, wed, fri");
        output = Logic.getMessageLog();
        expected = "addrc weekly successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat weekly -start 25 Dec 9 to 11am -every 2 week -on mon, wed, fri");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6123551,\"description\":\"weekly\",\"taskRepeatType\":\"week\",\"dateAdded\":\"Dec 25, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"09:00:00\",\"taskRepeatEndTime\":\"11:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Week\":\"2\",\"isDaySelected\":[null,false,true,false,true,false,true,false],\"taskRepeatUntil\":\"Dec 1, 9999 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        // add monthly recurring tasks
        Logic.executeCommand("repeat monthly -start 27 Dec 1 to 2pm -every 2 month -until 25 Dec");
        output = Logic.getMessageLog();
        expected = "addrc monthly successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat monthly -start 27 Dec 1 to 2pm -every 2 month -until 25 Dec");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6124826,\"description\":\"meeting\",\"taskRepeatType\":\"month\",\"dateAdded\":\"Dec 27, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"13:00:00\",\"taskRepeatEndTime\":\"14:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Month\":\"2\",\"taskRepeatUntil\":\"Dec 25, 2015 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat monthly -start 27 Dec 1 to 2pm -every 1 month");
        output = Logic.getMessageLog();
        expected = "addrc monthly successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat monthly -start 27 Dec 1 to 2pm -every 1 month");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6124845,\"description\":\"meeting\",\"taskRepeatType\":\"month\",\"dateAdded\":\"Dec 27, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"13:00:00\",\"taskRepeatEndTime\":\"14:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Month\":\"1\",\"taskRepeatUntil\":\"Dec 1, 9999 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        // add yearly recurring tasks
        Logic.executeCommand("repeat yearly -start 15 Dec 2 to 5 pm -every 1 year");
        output = Logic.getMessageLog();
        expected = "addrc yearly successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat yearly -start 15 Dec 2 to 5 pm -every 1 year");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6116773,\"description\":\"yearly\",\"taskRepeatType\":\"year\",\"dateAdded\":\"Dec 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"14:00:00\",\"taskRepeatEndTime\":\"17:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Year\":\"1\",\"taskRepeatUntil\":\"Dec 1, 9999 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat yearly -start 15 Nov 5 to 6 pm -every 1 year -until 25 Dec 2020");
        output = Logic.getMessageLog();
        expected = "addrc yearly successful!";

        assertEquals(expected, output);
        initialize();

        Logic.executeCommand("repeat yearly -start 15 Nov 5 to 6 pm -every 1 year -until 25 Dec 2020");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6117167,\"description\":\"yearly\",\"taskRepeatType\":\"year\",\"dateAdded\":\"Nov 15, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"17:00:00\",\"taskRepeatEndTime\":\"18:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Year\":\"1\",\"taskRepeatUntil\":\"Dec 25, 2020 12:00:00 AM\",\"isComplete\":false}";

        assertEquals(expected, output);
        initialize();

        // delete recurring tasks
        Logic.executeCommand("repeat daily -start 15 Nov 5 to 6 pm -every 2 day -until 15 Dec 2016");
        Logic.executeCommand("delete R1");
        output = Logic.getMessageLog();
        expected = "deleted repeat index 1 successfully!";

        assertEquals(expected, output);
        initialize();
        
        // update recurring tasks
        Logic.executeCommand("repeat team project meeting -start 18 Nov 9 to 11am -every 2 week -on mon, fri, sat");
        Logic.executeCommand("update r1 team project meeting -start 19 Nov 9 to 11am -every 5 week -on sun, mon, sat -until 25 Dec 2019");
        output = Logic.getMessageLog();
        expected = "deleted repeat index 1 successfully!";
        
        assertEquals(expected, output);
        initialize();
        
        Logic.executeCommand("repeat team project meeting -start 18 Nov 9 to 11am -every 2 week -on mon, fri, sat");
        Logic.executeCommand("update r1 team project meeting -start 19 Nov 9 to 11am -every 5 week -on sun, mon, sat -until 25 Dec 2019");
        output = readFile(FILE_TASKFILE);
        expected = "{\"type\":\"REPEAT\",\"id\":6124826,\"description\":\"meeting\",\"taskRepeatType\":\"month\",\"dateAdded\":\"Dec 27, 2015 12:00:00 AM\",\"taskRepeatStartTime\":\"13:00:00\",\"taskRepeatEndTime\":\"14:00:00\",\"stopRepeat\":[\"Dec 12, 9999 12:00:00 AM\"],\"taskRepeatInterval_Month\":\"2\",\"taskRepeatUntil\":\"Dec 25, 2015 12:00:00 AM\",\"isComplete\":false}";
        
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
        File tempFile = new File(FILE_TASKFILE.getAbsolutePath());
        writer.print("");
        writer.close();
        FILE_TASKFILE.delete();
        tempFile.renameTo(FILE_TASKFILE);

        outputCommand = "";
        expectedCommand = "";
        outputStorage = "";
        expectedStorage = "";
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

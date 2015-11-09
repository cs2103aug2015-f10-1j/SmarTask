import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import com.google.gson.Gson;

public class StorageUnitTest {
    private static final String DEFAULT_FILENAME = "storage.txt";
    private static final String DEFAULT_PATHFILENAME = "filepath.txt";
    private static final String DEFAULT_PATH = Storage.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private static final String TEST_PATH = "D:/";

    File taskFile = new File(DEFAULT_PATH + File.separator + DEFAULT_FILENAME);
    File pathFile = new File(DEFAULT_PATHFILENAME);

    PrintWriter writer;
    BufferedReader reader;

    /*
     * public StorageUnitTest() { // TODO Auto-generated constructor stub }
     */

    @Test
    public void initTest() {
        taskFile.delete();
        pathFile.delete();

        Storage storageTest = Storage.getInstance();

        assertEquals(false, taskFile.exists());
        assertEquals(false, pathFile.exists());
    }

    @Test
    public void readAndSaveFileTest() throws Exception {
        Storage storageTest = Storage.getInstance();
        ArrayList<String> taskAttributes = new ArrayList<String>();
        taskAttributes.add("FakeAttribute" + "#" + "000000");

        Task taskA = new Task(Task.Type.FLOATING, taskAttributes);
        Task taskB = new Task(Task.Type.FLOATING, taskAttributes);
        
        ArrayList<Task> testListA = new ArrayList<Task>();
        ArrayList<Task> testListB = new ArrayList<Task>();
        testListA.add(taskA);
        testListA.add(taskB);
        storageTest.saveToFile(testListA);
        testListB = storageTest.retrieveFile();
        for(int i = 0; i < testListA.size(); i++){
            assertEquals(testListA.get(i).getType().toString(), testListB.get(i).getType().toString());
            assertEquals(testListA.get(i).getDescription().toString(), testListB.get(i).getDescription().toString());
        }

    }

    /*private void toString(ArrayList<Task> taskList) {
        // TODO Auto-generated method stub
        String result = "";
        for(int i = 0; i < taskList.size(); i++) {
            result = taskList.get(i).getType().toString();
            System.out.println(taskList.get(i).toString());
        }
    }*/

    @Test
    /*public void setPathTest() {
        Storage storageTest = Storage.getInstance();
        storageTest.setSavePath(TEST_PATH);

        assertEquals(TEST_PATH,)
        assertEquals(true, taskFile.exists());
    }*/

    @After
    public void clearTask() {
        taskFile.delete();
        pathFile.delete();
    }

}

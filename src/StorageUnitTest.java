import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Test;

import com.google.gson.Gson;

public class StorageUnitTest {
	private static final String DEFAULT_FILENAME = "storage.txt";

	File taskFile = new File(DEFAULT_FILENAME);
	PrintWriter writer;
	BufferedReader reader;

	/*
	 * public StorageUnitTest() { // TODO Auto-generated constructor stub }
	 */

	@Test
	public void initTest() {
		taskFile.delete();

		Storage storageTest = Storage.getInstance();

		assertEquals(true, taskFile.exists());
	}

	@Test
	public void readFile() {
		// do not know how to test...
	}

	@Test
	public void setPathTest() {

	}

	@After
	public void clearTask() {
		taskFile.delete();
	}

}

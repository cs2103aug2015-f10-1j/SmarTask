import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Storage component - Nothing should be stored in Storage
 * 
 * @author Kevin Zhang
 */

public class Storage {
	
	private static final String DEFAULT_FILENAME = "storage.txt";
	private static final String DEFAULT_PATHFILENAME = "filepath.txt";
	private static final String DEFAULT_PATH = System.getProperty("user.home") + "/Desktop";
	private static Storage taskOrganiser;

	private File taskFile;
	private File pathFile;
	private BufferedReader reader;
	private PrintWriter writer;
	private Gson gson;

	public Storage() {
		System.out.println("Storage constructed");
		gson = new Gson();
		pathFile = new File(DEFAULT_PATHFILENAME);
		createIfNotExists(pathFile);

		if (retrieveSavePath() == null) {
			System.out.println("The save path retrieved from path file is null, task file is created in main folder");
			taskFile = new File(DEFAULT_PATH + File.separator + DEFAULT_FILENAME);
			createIfNotExists(taskFile);
		} else {
			System.out.println("The save path retrieved from path file is not null, task file is created in a set location");
			taskFile = new File(retrieveSavePath() + File.separator + DEFAULT_FILENAME);
		}
	}

	public static Storage getInstance() {
		if (taskOrganiser == null) {
			taskOrganiser = new Storage();
		}
		
		return taskOrganiser;
	}

	private void createIfNotExists(File taskFile) {
		try {
			if (!taskFile.exists()) {
				taskFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------
	private String retrieveSavePath() {
		String savePath = "";
		initReader(pathFile);
		
		try {
			savePath = reader.readLine();
			
			if (!(savePath == null)) {
				System.out.println(savePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		closeReader();
		return savePath;
	}

	public ArrayList<Task> retrieveFile() {
		String text = "";
		ArrayList<Task> taskList = new ArrayList<Task>();
		System.out.println("retrieve from " + taskFile.getAbsolutePath());

		try {
			if (!initReader(taskFile)) {
				return taskList;
			}
			
			while ((text = reader.readLine()) != null) {
				Task task = gson.fromJson(text, Task.class);
				taskList.add(task);
			}
		} catch (IOException | JsonSyntaxException e) {
			e.printStackTrace();
		}
		closeReader();

		if (taskList == null || taskList.isEmpty()) {
			taskList = new ArrayList<Task>();
		}
		
		return taskList;
	}

	public void saveToFile(ArrayList<Task> taskList) {
		System.out.println("save to " + taskFile.getAbsolutePath());
		try {
			writer = new PrintWriter(taskFile, "UTF-8");
			
			for (Task task : taskList) {
				writer.println(gson.toJson(task));
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		writer.close();
	}

	public void setSavePath(String inputPath) {
		String savePath = "";
		
		try {
			File tempFile = new File(pathFile.getAbsolutePath());
			PrintWriter pw = new PrintWriter(tempFile);
			pw.print("");
			pw.close();
			pathFile.delete();
			tempFile.renameTo(pathFile);
			FileWriter fileW = new FileWriter(pathFile);
			BufferedWriter buffW = new BufferedWriter(fileW);
			buffW.write(inputPath);
			buffW.newLine();
			buffW.close();
			initReader(pathFile);
			savePath = reader.readLine();
			closeReader();

			if (taskFile.renameTo(new File(savePath + File.separator + DEFAULT_FILENAME))) {
				System.out.println(savePath + File.separator + DEFAULT_FILENAME);
				System.out.println("Moved successful!");
			} else {
				System.out.println("Failed to move!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Initialization Methods
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
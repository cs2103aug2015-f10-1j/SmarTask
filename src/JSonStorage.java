import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;

/**
 * @author A0130034A
 */

public class JSonStorage {

	private static boolean OVERWRITE = false;
	private static boolean APPEND = true;
	private static String DEFAULT_FILENAME = "Data.txt";
	private ArrayList<Task> taskList;
	private static String fileName;
	private static String oldFileName;
	private static String DEFAULT_FILEPATH = "Path.txt";
	private static String DEFAULT_CONFIG = "Config.txt";
	private static String DEFAULT_INNERIMAGEFILE = "InnerImagePath.txt";
	private String oldLocation;

	public JSonStorage() throws Exception {
		taskList = new ArrayList<Task>();
		initializeStorage();
	}

	public void add(Task task) {
		try {
			Gson gson = new Gson();
			FileWriter writer = new FileWriter(fileName, APPEND);
			writer.write(gson.toJson(task));
			writer.write('\n');
			writer.close();
			taskList.add(task);
			System.out.println(taskList.size());
			sortByDate();
			rewriteFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(int taskID) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskID == taskList.get(i).getID()) {
				taskList.remove(i);
			}
		}
		
		sortByDate();
		rewriteFile();
	}

	public void rewriteFile() {
		try {
			Gson gson = new Gson();
			FileWriter writer = new FileWriter(fileName, OVERWRITE);
			
			for (int i = 0; i < taskList.size(); i++) {
				writer.write(gson.toJson(taskList.get(i)));
				writer.write('\n');
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Task> retrieveAllTasks() {
		return taskList;
	}

	public void initializeStorage() throws Exception {
		if (checkPathFileExist()) {
			oldFileName = getPath();
			fileName = getPath();
		} else {
			createFile(DEFAULT_FILENAME);
			fileName = DEFAULT_FILENAME;
			oldFileName = getPath(DEFAULT_FILENAME);
		}

		Gson gson = new Gson();
		Task task = new Task(null, null); // TODO: add parameters for Task

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String currentLine = reader.readLine();
			
			while (currentLine != null) {
				task = gson.fromJson(currentLine, Task.class);
				taskList.add(task);
				currentLine = reader.readLine();
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String retrieveFile(String filePathInput) {
		String currLine = "", pathOut = null;
		File file = new File(filePathInput);
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			while ((currLine = br.readLine()) != null) {
				pathOut = currLine;
			}
			
			fr.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pathOut;
	}

	public void saveToFile(String fileInput, String newPath) {
		File file = new File(fileInput);
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.flush();
			fw.write(newPath);
			fw.write('\n');
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------------------------------//
	public String getPath() {
		String path = retrieveFile(DEFAULT_FILEPATH);
		return path;
	}

	public void addPath(String newPath) {
		createFile(DEFAULT_FILEPATH);
		File file = new File(DEFAULT_FILEPATH);
		if (checkPathFileExist() && file.length() > 0) {
			oldLocation = getPath();
			oldFileName = oldLocation;
		} else {
			oldLocation = "Data.txt";
		}

		saveToFile(DEFAULT_FILEPATH, newPath);
		copyAndDelete(oldLocation, newPath);
	}

	public void addSetting(String input) {
		createFile(DEFAULT_CONFIG);
		saveToFile(DEFAULT_CONFIG, input);
	}

	public void addInnerImage() {
		createFile(DEFAULT_INNERIMAGEFILE);
	}

	// Copy and delete
	public static void copyAndDelete(String oldPath, String newFilePath) {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {
			File afile = new File(oldPath); // original path
			File bfile = new File(newFilePath); // new path
			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);
			byte[] buffer = new byte[65536];
			int length;
			
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();
			afile.delete();
			fileName = newFilePath;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createFile(String fileName) {
		File file = new File(fileName);
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checkPathFileExist() {
		File file = new File(DEFAULT_FILEPATH);
		
		if (file.exists()) {
			return true;
		}
		return false;
	}

	private String getPath(String name) {
		File f = new File(name);
		return f.getAbsolutePath();
	}

	private void sortByDate() {
		Collections.sort(taskList, new Comparator<Task>() {
			DateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			@Override
			public int compare(Task task1, Task task2) {
				try {
					return f.parse(task1.getDeadline()).compareTo(f.parse(task2.getDeadline()));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
	}
}
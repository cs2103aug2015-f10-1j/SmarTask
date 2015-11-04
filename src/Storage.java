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
 * @author Kevin
 *
 */

public class Storage {
	private static final String DEFAULT_FILENAME = "storage.txt";

	private static final String DEFAULT_PATHFILENAME = "filepath.txt";

	private static Storage taskOrganiser;

	private File taskFile;
	private File pathFile;
	private BufferedReader reader;
	private PrintWriter writer;
	private String savePath = "";

	private Gson gson;

	public Storage() {
		gson = new Gson();
		pathFile = new File(DEFAULT_PATHFILENAME);
		createIfNotExists(pathFile);

		//taskFile = new File(retrieveSavePath() + File.separator + DEFAULT_FILENAME);
		//System.out.println(retrieveSavePath() + File.separator + DEFAULT_FILENAME);
		//createIfNotExists(taskFile);
		
		if (retrieveSavePath() == null) {
			taskFile = new File(DEFAULT_FILENAME);
			createIfNotExists(taskFile);
		}
		else {
			taskFile = new File(retrieveSavePath() + File.separator + DEFAULT_FILENAME);
		}
	}
	


	public Storage getInstance() {
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
	
	
	
//-------------------------------------------------------------------------------------------

	//not getting the string from the file
	private String retrieveSavePath() {
		initReader(pathFile);
		try {
			savePath = reader.readLine();
			if (!(savePath == null)) {
			System.out.println(savePath);
			//savePath = savePath + path;
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
			
			if(taskFile.renameTo(new File(savePath + File.separator + DEFAULT_FILENAME))){
				System.out.println(savePath + File.separator + DEFAULT_FILENAME);
	    		System.out.println("Moved successful!");
	            }else{
	    		System.out.println("Failed to move!");
	            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*if(taskFile.exists()) {
			System.out.println(savePath);
			try {
				
				if(taskFile.renameTo(new File(savePath + inputPath + File.separator + DEFAULT_FILENAME))){
				//System.out.println(inputPath + File.separator + DEFAULT_FILENAME);
	    		System.out.println("Moved successful!");
	            }else{
	    		System.out.println("Failed to move!");
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		}*/

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

/*

public class Storage {
	public static String DEFAULT_FILELOCATION = System.getProperty("user.home") + "/Desktop";
    public static String DEFAULT_FILENAME = "taskFile.txt";

	private static Storage taskOrganiser;

	private static File taskFile;
	private static BufferedReader reader;
	private static PrintWriter writer;
	private ArrayList<Task> taskList;

	
	private static Gson gson;

	public Storage() {
		gson = new Gson();
		taskFile = new File(DEFAULT_FILENAME);
		
		createFile();
	}
	
    public void createFile() {
    		if (!checkFileExist()) {
    			taskFile = new File(DEFAULT_FILELOCATION);
    		}
    }
    
	public static Storage getInstance() {
		if (taskOrganiser == null) {
			taskOrganiser = new Storage();
		}
		return taskOrganiser;
	}
	
	public static ArrayList<Task> retrieveFile() {
		String text = "";
		ArrayList<Task> taskList = new ArrayList<Task>();
		
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

	public static void saveToFile(ArrayList<Task> taskList) {
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

	// Initialization Methods
	private static boolean initReader(File taskFile) {
		try {
			reader = new BufferedReader(new FileReader(taskFile));
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

	private static void closeReader() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Utility Methods
	public boolean checkFileExist() {
		File file = new File(DEFAULT_FILELOCATION);
		if (file.exists()) {
			return true;
		}
		return false;
	}
	

}






*/


/*import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    
    public static ArrayList<String> taskList = new ArrayList<String>();; 
    public static String DEFAULT_LOCATION = System.getProperty("user.home") + "/Desktop" ;
    public static String DEFAULT_FILENAME = "/taskFile.txt";
    public static String DEFAULT_FILELOCATION = DEFAULT_LOCATION + DEFAULT_FILENAME;
    public static File taskFile = new File(DEFAULT_FILELOCATION);
    
    public Storage() {
	taskList = new ArrayList<String>();
    }
    
    public static void createFile(){	
	taskFile = new File(DEFAULT_FILELOCATION);
    }

    public static ArrayList<String> retrieveTexts() {
	try {
	    Scanner scanner = new Scanner(taskFile);
	    while (scanner.hasNextLine()) {
		taskList.add(scanner.nextLine());
	    }
	    scanner.close();
	} catch (FileNotFoundException e) {
	}
	return taskList;
    }

    public static void saveToFile(ArrayList<String> taskList) throws FileNotFoundException {			
	try {
	    File tempFile = new File(taskFile.getAbsolutePath());
	    PrintWriter pw = new PrintWriter(tempFile);
	    pw.print("");
	    pw.close();
	    taskFile.delete();
	    tempFile.renameTo(taskFile);
	    FileWriter fileW = new FileWriter(taskFile);
	    BufferedWriter buffW = new BufferedWriter(fileW);
	    for (int i = 0; i < taskList.size(); i++) {
		buffW.write(taskList.get(i));
		buffW.newLine();
	    }
	    buffW.close();
	} catch (IOException e) {
	}
    }

    public static void saveToFileRC(ArrayList<String> rcTaskList) throws FileNotFoundException {
	try {
	    File tempFile = new File(taskFile.getAbsolutePath());
	    PrintWriter pw = new PrintWriter(tempFile);
	    pw.print("");
	    pw.close();
	    taskFile.delete();
	    tempFile.renameTo(taskFile);
	    FileWriter fileW = new FileWriter(taskFile);
	    BufferedWriter buffW = new BufferedWriter(fileW);
	    for (int i = 0; i < rcTaskList.size(); i++) {
		buffW.write(rcTaskList.get(i));
		buffW.newLine();
	    }
	    buffW.close();
	} catch (IOException e) {			
	}		
    }

}
*/
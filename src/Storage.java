import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    public File taskFile;
    public static ArrayList<String> taskList; 

    public Storage() {
	taskList = new ArrayList<String>();
    }
    
    public void createFile(){	
	taskFile = new File("C:\\Users\\David_Chong\\Documents\\Test Environment\\taskFile.txt");
    }

    public ArrayList<String> retrieveTexts() {
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

    public void saveToFile(ArrayList<String> taskList) throws FileNotFoundException {			
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

    public void saveToFileRC(ArrayList<String> rcTaskList) throws FileNotFoundException {
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

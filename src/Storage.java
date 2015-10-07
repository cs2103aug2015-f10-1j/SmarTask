import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class Storage {
	
	public File taskFile;

	public static ArrayList<String> taskList; 
	

	
	private ArrayList retrieveTexts(File file) {
		try {
			File taskFile = new File("C:\\Users\\user\\Desktop\\taskFile.txt");
			Scanner scanner = new Scanner(taskFile);
			while (scanner.hasNextLine()) {
				taskList.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
		}
		return taskList;
	}
	
	private void saveToFile(ArrayList taskList) throws FileNotFoundException {
		try {
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
	public ArrayList <String> getArrayList (){
		return taskList;
	}

}

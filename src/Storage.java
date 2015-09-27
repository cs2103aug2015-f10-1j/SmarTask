import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class Storage {
	
	private static File file;

	public static ArrayList<String> text; 
	
	public static void main(String[] args) {

	}
	
	private static void retrieveTexts(File file) {
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				text.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
		}
	}
	
	private static void saveToFile() throws FileNotFoundException {
		try {
			FileWriter fileW = new FileWriter(file);
			BufferedWriter buffW = new BufferedWriter(fileW);
			for (int i = 0; i < text.size(); i++) {
				buffW.write(text.get(i));
				buffW.newLine();
			}
			buffW.close();
		} catch (IOException e) {
		}
	}

}

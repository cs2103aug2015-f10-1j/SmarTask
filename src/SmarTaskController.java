import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * SmarTask Controller acts as a initializer and gives the action properties to the fxml file.
 * 
 * @author David Chong
 *
 */

public class SmarTaskController implements Initializable {
  
	@FXML private TextArea mainWindow;    //Value injected by FXML Loader
	@FXML private TextArea displayWindow;	//Value injected by FXMLoader
	@FXML private TextArea taskWindow;    //Value injected by FXMLoader
	@FXML private TextArea specialTaskWindow; //Value injected by FXMLoader
	@FXML private TextField inputWindow;   //Value injected by FXMLoader
	public ArrayList<String> logDisplay;
	public ArrayList<String> taskDeadlineDisplay;
	public ArrayList<String> specialTaskDisplay;
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert mainWindow != null : "fx:id=\"mainWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert displayWindow != null : "fx:id=\"displayWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert taskWindow != null : "fx:id=\"taskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert specialTaskWindow != null : "fx:id=\"specialTaskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert inputWindow != null : "fx:id=\"inputWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		logDisplay = Logic.getMessageLog();
		taskDeadlineDisplay = Logic.getDeadline();
		specialTaskDisplay = Logic.getFloatingTask();
		
		try {
			displayText(displayWindow, logDisplay);
			displayText(taskWindow, taskDeadlineDisplay);
			displayText(specialTaskWindow, specialTaskDisplay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void displayText(TextArea display, ArrayList<String> toDisplay) {
		display.clear();
		for(int i=0; i < toDisplay.size(); i++) {
			String textToDisplay = toDisplay.get(i);
			display.insertText(i, textToDisplay);
		}
	}
	
	@FXML
	public void onEnter(KeyEvent ke) {
		if(ke.getCode() == KeyCode.ENTER) {
			String userCommand = inputWindow.getText();
			inputWindow.clear();
	        try {
	            Logic.executeCommand(userCommand);
	            displayWindow.clear();
	            logDisplay = Logic.getMessageLog();
	            displayText(displayWindow, logDisplay);
	            taskWindow.clear();
	            taskDeadlineDisplay = Logic.getDeadline();
	            displayText(taskWindow, taskDeadlineDisplay);
	            specialTaskDisplay = Logic.getFloatingTask();
	            displayText(specialTaskWindow, specialTaskDisplay);
	        } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	        }
		}
	}
}
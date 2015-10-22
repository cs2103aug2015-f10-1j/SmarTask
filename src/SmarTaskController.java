import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
    @FXML private TextArea eventWindow; //Value injected by FXMLoader
    @FXML private TextArea taskWindow;    //Value injected by FXMLoader
    @FXML private TextArea specialTaskWindow; //Value injected by FXMLoader
    @FXML private TextArea recurringTaskWindow; //Value injected by FXMLoader
    @FXML private TextField inputWindow;   //Value injected by FXMLoader
    private static String logDisplay;
    private static String eventDisplay;
    private static String taskDeadlineDisplay;
    private static String specialTaskDisplay;
    private static String recurringTaskDisplay;
    
    final KeyCombination crtlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
	final KeyCombination crtlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert mainWindow != null : "fx:id=\"mainWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert displayWindow != null : "fx:id=\"displayWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert eventWindow != null : "fx:id=\"eventWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert taskWindow != null : "fx:id=\"taskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert specialTaskWindow != null : "fx:id=\"specialTaskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert recurringTaskWindow != null : "fx:id=\"recurringTaskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert inputWindow != null : "fx:id=\"inputWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        updateDisplay();
    }
    
    public void updateDisplay() {
    	logDisplay = Logic.getMessageLog();
        eventDisplay = Logic.getEvents();
        taskDeadlineDisplay = Logic.getDeadline();
        specialTaskDisplay = Logic.getFloatingTask();
        recurringTaskDisplay = Logic.getDeadline();
        updateWindows(displayWindow, logDisplay);
        updateWindows(eventWindow, eventDisplay);
        updateWindows(taskWindow, taskDeadlineDisplay);
        updateWindows(specialTaskWindow, specialTaskDisplay);
        updateWindows(recurringTaskWindow, recurringTaskDisplay);
    }

    private void updateWindows(TextArea display, String toDisplay) {
        display.clear();
        display.setText(toDisplay);
    }

    @FXML
    public void keyboardLog(KeyEvent ke) {
        if(ke.getCode() == KeyCode.ENTER) {
            enterKeyEvent();
        } else if(ke.getCode() == KeyCode.UP) {
        	upKeyEvent();
        } else if(crtlZ.match(ke)) {
        	controlZKeyEvent();
        } else if(crtlY.match(ke)) {
        	controlYKeyEvent();
        }
    }
    
    @FXML
    public void secondKeyboardLog(KeyEvent ke) {
        if(ke.getCode() == KeyCode.UP) {
        	upKeyEvent();
        } else if(crtlZ.match(ke)) {
        	controlZKeyEvent();
        } else if(crtlY.match(ke)) {
        	controlYKeyEvent();
        }
    }
    
    private void enterKeyEvent() {
    	String userCommand = inputWindow.getText();
        inputWindow.clear();
        try {
            Logic.executeCommand(userCommand);
            updateDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void upKeyEvent() {
    	String pastCommand = "Displays Last User Command";
    	inputWindow.setText(pastCommand);
    }
    
    private void controlZKeyEvent() {
    	try {
    	    Logic.executeCommand("undo");
    	    updateDisplay();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void controlYKeyEvent() {
    	try {
    	    Logic.executeCommand("redo");
    	    updateDisplay();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
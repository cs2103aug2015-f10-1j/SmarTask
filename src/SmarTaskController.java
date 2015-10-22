import java.net.URL;
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
    @FXML private TextArea eventWindow; //Value injected by FXMLoader
    @FXML private TextArea taskWindow;    //Value injected by FXMLoader
    @FXML private TextArea specialTaskWindow; //Value injected by FXMLoader
    @FXML private TextField inputWindow;   //Value injected by FXMLoader
    public String logDisplay;
    public String eventDisplay;
    public String taskDeadlineDisplay;
    public String specialTaskDisplay;
    public String recurringTaskDisplay;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert mainWindow != null : "fx:id=\"mainWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert displayWindow != null : "fx:id=\"displayWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert eventWindow != null : "fx:id=\"eventWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert taskWindow != null : "fx:id=\"taskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert specialTaskWindow != null : "fx:id=\"specialTaskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert inputWindow != null : "fx:id=\"inputWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        logDisplay = Logic.getMessageLog();
        eventDisplay = Logic.getEvents();
        taskDeadlineDisplay = Logic.getDeadline();
        specialTaskDisplay = Logic.getFloatingTask();

        try {
            displayText(displayWindow, logDisplay);
            displayText(eventWindow, eventDisplay);
            displayText(taskWindow, taskDeadlineDisplay);
            displayText(specialTaskWindow, specialTaskDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displayText(TextArea display, String toDisplay) {
        display.clear();
        display.setText(toDisplay);
    }

    @FXML
    public void keyboardLog(KeyEvent ke) {
        if(ke.getCode() == KeyCode.ENTER) {
            enterKeyEvent();
        } else if(ke.getCode() == KeyCode.UP) {
        	upKeyEvent();
        } else if(ke.getCode() == KeyCode.CONTROL) {
        	if(ke.getCode() == KeyCode.Z) {
        		controlZKeyEvent();
        	} else if(ke.getCode() == KeyCode.Y) {
        		controlYKeyEvent();
        	}
        }
    }
    
    public void enterKeyEvent() {
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
            specialTaskWindow.clear();
            specialTaskDisplay = Logic.getFloatingTask();
            displayText(specialTaskWindow, specialTaskDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void upKeyEvent() {
    	String pastCommand = "Displays Last User Command";
    	inputWindow.setText(pastCommand);
    }
    
    public void controlZKeyEvent() {
    	try {
    	    Logic.executeCommand("undo");
    	    displayWindow.clear();
    	    logDisplay = Logic.getMessageLog();
    	    displayText(displayWindow, logDisplay);
    	    taskWindow.clear();
            taskDeadlineDisplay = Logic.getDeadline();
            displayText(taskWindow, taskDeadlineDisplay);
            specialTaskWindow.clear();
            specialTaskDisplay = Logic.getFloatingTask();
            displayText(specialTaskWindow, specialTaskDisplay);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void controlYKeyEvent() {
    	try {
    	    Logic.executeCommand("redo");
    	    displayWindow.clear();
    	    logDisplay = Logic.getMessageLog();
    	    displayText(displayWindow, logDisplay);
    	    taskWindow.clear();
            taskDeadlineDisplay = Logic.getDeadline();
            displayText(taskWindow, taskDeadlineDisplay);
            specialTaskWindow.clear();
            specialTaskDisplay = Logic.getFloatingTask();
            displayText(specialTaskWindow, specialTaskDisplay);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
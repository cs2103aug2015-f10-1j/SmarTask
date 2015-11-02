import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * It determines the behavior of the fxml file and is the main class to add UI enhancements.
 * 
 * @author David Chong
 *
 */

public class SmarTaskController implements Initializable {

	@FXML private TextArea displayWindow;	//Value injected by FXMLoader
    @FXML private TextArea taskWindow;    //Value injected by FXMLoader
    @FXML private TextField inputWindow;   //Value injected by FXMLoader
	private static final String MESSAGE_WELCOME = "Welcome to SmarTask! In case you need a refresher, please type \"help\" to get a list of all the SmarTask commands.";
	private static final String TASK_DEADLINE = "Deadline Tasks:";
	private static final String TASK_EVENTS = "Event Tasks:";
	private static final String TASK_FLOATING = "Floating Tasks:";
	private static final String TASK_RECURRING = "Recurring Tasks:";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_REDO = "redo";
	private final KeyCombination crtlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
	private final KeyCombination crtlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
    private static String logDisplay;
    private static String deadlineTasks;
    private static String eventTasks;
    private static String floatingTasks;
    private static String recurringTasks;
    private static Stack<String> pastCommands;
    private static Stack<String> poppedCommands;
	
	/**
	 * Checks if the variables referenced from the fxml file have been properly referenced
	 * If not, method will return a error message
	 * 
	 * @param fxmlFileLocation	the file location of the fxml file in the system
	 * @param resources the resources associated with the fxml file
	 */
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert displayWindow != null : "fx:id=\"displayWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert taskWindow != null : "fx:id=\"taskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert inputWindow != null : "fx:id=\"inputWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        pastCommands = new Stack<String>();
        poppedCommands = new Stack<String>();
        
        try {
			updateDisplay();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        displayWindow.setText(MESSAGE_WELCOME);
        
        displayWindow.textProperty().addListener(new ChangeListener<Object>() {
        	@Override
        	public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
        		displayWindow.selectPositionCaret(displayWindow.getLength());
        		displayWindow.deselect();
        	}
        });
    }
    
    /**
	 * Method updates the information display from Logic to the user so they can preview the information stored in SmarTask.
     * @throws ParseException 
	 */
    public void updateDisplay() throws ParseException {
    	logDisplay = Logic.getMessageLog();
        updateWindows(displayWindow, logDisplay);
        updateWindows(taskWindow);
    }
    
    private void updateWindows(TextArea display, String toDisplay) {
        display.clear();
        display.setText(toDisplay);
    }
    
    private void updateWindows(TextArea display) throws ParseException {
        display.clear();
        deadlineTasks = Logic.getDeadline();
        eventTasks = Logic.getEvents();
        floatingTasks = Logic.getFloatingTask();
        recurringTasks = Logic.getRecurringTask();
    	String lineBreak = "\n";
        display.setText(TASK_DEADLINE);
        display.appendText(lineBreak);
        display.appendText(deadlineTasks);
        display.appendText(lineBreak);
        display.appendText(lineBreak);
        display.appendText(TASK_EVENTS);
        display.appendText(lineBreak);
        display.appendText(eventTasks);
        display.appendText(lineBreak);
        display.appendText(lineBreak);
        display.appendText(TASK_FLOATING);
        display.appendText(lineBreak);
        display.appendText(floatingTasks);
        display.appendText(lineBreak);
        display.appendText(lineBreak);
        display.appendText(TASK_RECURRING);
        display.appendText(lineBreak);
        display.appendText(recurringTasks);
    }

    /**
	 * Checks the keys pressed by the user and activates certain events according to which keys have been pressed.
	 * 
	 * @param ke the key pressed by the user as recorded by the system
	 */
    @FXML
    public void keyboardLog(KeyEvent ke) {
        if(ke.getCode() == KeyCode.ENTER) {
            enterKeyEvent();
        } else if(ke.getCode() == KeyCode.UP) {
        	upKeyEvent();
        } else if(ke.getCode() == KeyCode.DOWN) {
        	downKeyEvent();
        } else if(crtlZ.match(ke)) {
        	controlZKeyEvent();
        } else if(crtlY.match(ke)) {
        	controlYKeyEvent();
        }
    }
    
    /**
	 * Checks the keys pressed by the user and activates certain events according to which keys have been pressed.
	 * 
	 * @param ke the key pressed by the user as recorded by the system
	 */
    @FXML
    public void specialKeyboardLog(KeyEvent ke) {
        if(ke.getCode() == KeyCode.UP) {
        	upKeyEvent();
        } else if(ke.getCode() == KeyCode.DOWN) {
        	downKeyEvent();
        } else if(crtlZ.match(ke)) {
        	controlZKeyEvent();
        } else if(crtlY.match(ke)) {
        	controlYKeyEvent();
        }
    }
    
    private void enterKeyEvent() {
    	String userCommand = inputWindow.getText();
    	if(!poppedCommands.isEmpty()) {
    		String poppedCommand = poppedCommands.pop();
    		pastCommands.push(poppedCommand);
    	}
    	pastCommands.push(userCommand);
        inputWindow.clear();
        try {
            Logic.executeCommand(userCommand);
            updateDisplay();
        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println("Error! Invalid Command sent to Logic!");
        }
    }
    
    private void upKeyEvent() {
    	if(pastCommands.isEmpty()) {
    		inputWindow.clear();
    	} else {
    		inputWindow.clear();
    		String pastCommand = pastCommands.pop();
    		poppedCommands.push(pastCommand);
    		inputWindow.setText(pastCommand);
    	}
    }
    
    private void downKeyEvent() {
    	if(poppedCommands.isEmpty()) {
    		inputWindow.clear();
    	} else {
    		inputWindow.clear();
    		String pastCommand = poppedCommands.pop();
    		pastCommands.push(pastCommand);
    		inputWindow.setText(pastCommand);
    	}
    }
    
    private void controlZKeyEvent() {
    	try {
    	    Logic.executeCommand(COMMAND_UNDO);
    	    updateDisplay();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.err.println("Error! Invalid Undo Command sent to Logic!");
    	}
    }
    
    private void controlYKeyEvent() {
    	try {
    	    Logic.executeCommand(COMMAND_REDO);
    	    updateDisplay();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.err.println("Error! Invalid Redo Command sent to Logic!");
    	}
    }
}
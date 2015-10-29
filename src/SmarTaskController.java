import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

	private static final String MESSAGE_WELCOME = "Welcome to SmarTask! In case you need a refresher, please type \"help\" to get a list of all the SmarTask commands.";
	private static final String TASK_DEADLINE = "Deadline Tasks";
	private static final String TASK_EVENTS = "Event Tasks";
	private static final String TASK_FLOATING = "Floating Tasks";
	private static final String TASK_RECURRING = "Recurring Tasks";
	
    @FXML private TextArea displayWindow;	//Value injected by FXMLoader
    @FXML private TextArea taskWindow;    //Value injected by FXMLoader
    @FXML private TextField inputWindow;   //Value injected by FXMLoader
    private static String logDisplay;
    private static String taskDisplay;
    private static ArrayList<String> pastCommands;
    private static int commandCounter;
    
    final KeyCombination crtlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
	final KeyCombination crtlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
	
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
        updateDisplay();
        pastCommands = new ArrayList<String>();
        commandCounter = 1;
    }
    
    /**
	 * Method updates the information display from Logic to the user so they can preview the information stored in SmarTask.
	 */
    public void updateDisplay() {
    	logDisplay = Logic.getMessageLog();
    	taskDisplay = Logic.getDeadline();
        updateWindows(displayWindow, logDisplay);
        updateWindows(taskWindow, taskDisplay);
    }
    
    private void updateWindows(TextArea display, String toDisplay) {
        display.clear();
        display.setText(toDisplay);
    }
    
    private void updateDisplayWindows(TextArea display, String toDisplay) {
        display.clear();
        display.setText(toDisplay);
    }
    
    private TreeView<String> createTree() {
    	TreeItem<String> deadlineTaskTree = createSubTree(TASK_DEADLINE, Logic.getDeadline());
        TreeItem<String> eventsTaskTree = createSubTree(TASK_EVENTS, Logic.getEvents());
        TreeItem<String> floatingTaskTree = createSubTree(TASK_FLOATING, Logic.getFloatingTask());
        TreeItem<String> recurringTaskTree = createSubTree(TASK_RECURRING, Logic.getRecurringTask());
        TreeItem<String> taskTree = new TreeItem<String>("All Tasks");
        taskTree.setExpanded(true);
        taskTree.getChildren().add(deadlineTaskTree);
        taskTree.getChildren().add(eventsTaskTree);
        taskTree.getChildren().add(floatingTaskTree);
        taskTree.getChildren().add(recurringTaskTree);
        TreeView<String> taskList = new TreeView<String>(taskTree);
        return taskList;
    }
    
    private TreeItem<String> createSubTree(String taskTitle, String toDisplay) {
    	TreeItem<String> taskList = new TreeItem<String>(taskTitle);
    	taskList.setExpanded(true);
	    for (int i = 1; i < toDisplay.length(); i++) {
	        TreeItem<String> taskItem = new TreeItem<String>("Message" + i);            
	        taskList.getChildren().add(taskItem);
	    } 
	    return taskList;
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
    	pastCommands.add(userCommand);
        inputWindow.clear();
        try {
            Logic.executeCommand(userCommand);
            updateDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void upKeyEvent() {
    	int recall = pastCommands.size() - commandCounter;
    	if(recall < 0) {
    		inputWindow.clear();
    	} else {
    		commandCounter++;
    		inputWindow.clear();
    		String pastCommand = pastCommands.get(recall);
    		inputWindow.setText(pastCommand);
    	}
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
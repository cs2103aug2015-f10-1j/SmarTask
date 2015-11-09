import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

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
import javafx.stage.Stage;

/**
 * SmarTask Controller acts as a initializer and gives the action properties to
 * the fxml file. It determines the behavior of the fxml file and is the main
 * class to add UI enhancements.
 * 
 * @@author A0116633L
 */

public class SmarTaskController implements Initializable {

    @FXML
    private TextArea displayWindow;
    @FXML
    private TextArea taskWindow;
    @FXML
    private TextField inputWindow;
    
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_EXIT = "exit";
    private static final String FILE_HELPMANUAL = ".." + File.separator + "SmarTask" + File.separator + "helpManual.txt";
    private static final String MESSAGE_WELCOME = "Welcome to SmarTask! In case you need a refresher, please type \"help\" to get a list of all the SmarTask commands.";
    private static final String MESSAGE_INVALIDCOMMAND = "Error! Invalid Command sent to Logic!";
    private static final String MESSAGE_INVALIDUNDO = "Error! Invalid Undo Command sent to Logic!";
    private static final String MESSAGE_INVALIDREDO = "Error! Invalid Redo Command sent to Logic!";
    private static final String TASK_DEADLINE = "Deadline Tasks:";
    private static final String TASK_EVENTS = "Event Tasks:";
    private static final String TASK_FLOATING = "Floating Tasks:";
    private static final String TASK_RECURRING = "Recurring Tasks:";
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
     * Checks if the variables referenced from the fxml file have been properly
     * referenced If not, method will return a error message
     * 
     * @param fxmlFileLocation
     *            the file location of the fxml file in the system
     * @param resources
     *            the resources associated with the fxml file
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
     * Method updates the information display from Logic to the user so they can
     * preview the information stored in SmarTask.
     * 
     * @throws ParseException
     */
    private void updateDisplay() throws ParseException {
        logDisplay = Logic.getMessageLog();
        deadlineTasks = Logic.getDeadline();
        eventTasks = Logic.getEvents();
        floatingTasks = Logic.getFloatingTask();
        recurringTasks = Logic.getRecurringTask();
        updateWindows(displayWindow, logDisplay);
        updateWindows(taskWindow);
    }
    
    private void updateWindows(TextArea display, String toDisplay) {
        display.clear();
        display.setText(toDisplay);
        String emptyString = "";
        display.appendText(emptyString);
    }
    
    private void updateWindows(TextArea display) {
        display.clear();
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
    
    private void updateWindows(TextArea display, ArrayList<String> toDisplay) {
        display.clear();
        String lineBreak = "\n";
        
        for (int i = 0; i< toDisplay.size(); i++) {
            String tempString = toDisplay.get(i);
            display.appendText(tempString);
            display.appendText(lineBreak);
        }
        String emptyString = "";
        display.appendText(emptyString);
    }

    /**
     * Checks the keys pressed by the user and activates certain events
     * according to which keys have been pressed.
     * 
     * @param ke
     *            the key pressed by the user as recorded by the system
     */
    @FXML
    public void keyboardLog(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            enterKeyEvent();
        } else if (ke.getCode() == KeyCode.UP) {
            upKeyEvent();
        } else if (ke.getCode() == KeyCode.DOWN) {
            downKeyEvent();
        } else if (crtlZ.match(ke)) {
            controlZKeyEvent();
        } else if (crtlY.match(ke)) {
            controlYKeyEvent();
        }
    }

    /**
     * Checks the keys pressed by the user and activates certain events
     * according to which keys have been pressed.
     * 
     * @param ke
     *            the key pressed by the user as recorded by the system
     */
    @FXML
    public void specialKeyboardLog(KeyEvent ke) {
        if (ke.getCode() == KeyCode.UP) {
            upKeyEvent();
        } else if (ke.getCode() == KeyCode.DOWN) {
            downKeyEvent();
        } else if (crtlZ.match(ke)) {
            controlZKeyEvent();
        } else if (crtlY.match(ke)) {
            controlYKeyEvent();
        }
    }
    
    private void enterKeyEvent() {
        String userCommand = inputWindow.getText();
        
        if (!poppedCommands.isEmpty()) {
            String poppedCommand = poppedCommands.pop();
            pastCommands.push(poppedCommand);
        }
        
        pastCommands.push(userCommand);
        inputWindow.clear();
        
        if (userCommand.toLowerCase().equals(COMMAND_EXIT)) {
            exitCommand();
        } else if (userCommand.toLowerCase().equals(COMMAND_HELP)) {
            helpCommand();
        } else {
        	try {
                Logic.executeCommand(userCommand);
                updateDisplay();
            } catch (Exception e) {
                System.err.println(MESSAGE_INVALIDCOMMAND);
            }
        }
    }
    
    private void exitCommand() {
        Stage stage = (Stage) inputWindow.getScene().getWindow();
        stage.close();
    }
    
    private void helpCommand() {
        File file = new File(FILE_HELPMANUAL);
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList<String> helpManual = new ArrayList<String>();
        
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String fileOutput = null;
            
            while ((fileOutput = br.readLine()) != null) {
                helpManual.add(fileOutput);
            }
        } catch (IOException e) {
            System.err.println("Error! Cannot find help file!");
        }
        
        updateWindows(taskWindow, helpManual);
    }
    
    private void upKeyEvent() {
        if (pastCommands.isEmpty()) {
            inputWindow.clear();
        } else {
            inputWindow.clear();
            String pastCommand = pastCommands.pop();
            poppedCommands.push(pastCommand);
            inputWindow.setText(pastCommand);
        }
    }
    
    private void downKeyEvent() {
        if (poppedCommands.isEmpty()) {
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
            System.err.println(MESSAGE_INVALIDUNDO);
        }
    }
    
    private void controlYKeyEvent() {
        try {
            Logic.executeCommand(COMMAND_REDO);
            updateDisplay();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(MESSAGE_INVALIDREDO);
        }
   }
}
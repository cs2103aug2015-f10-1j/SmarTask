import java.util.ArrayList;
import java.util.Stack;

/**
 * CommandHistory helps to undo and redo any command made by user.
 * Two stacks are used to store different versions of the data at a specific time
 * of command execution.
 * 
 * @author Bobby Lin
 *
 */

public class CommandHistory {
    
    Stack<ArrayList<String>> undoStack ;
    Stack<ArrayList<String>> redoStack;
    
    public CommandHistory(ArrayList<String> taskList) {
	undoStack = new Stack<ArrayList<String>>();
	undoStack.add(taskList);
	redoStack = new Stack<ArrayList<String>>();
    }
    
    public void addChangeToHistory(ArrayList<String> taskList) {
	undoStack.add(taskList);
    }
    
    public ArrayList<String> undoCommand() {
	redoStack.add(undoStack.pop());
	return undoStack.peek();
    }
    
    public ArrayList<String> redoCommand() {
	undoStack.add(redoStack.pop());
	return undoStack.peek();
    }

}

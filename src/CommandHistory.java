import java.util.ArrayList;
import java.util.EmptyStackException;
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
    
    public ArrayList<String> undo() {
	if(undoStack.isEmpty() || undoStack == null || redoStack == null) {
	    return null;
	}
	try {
	    redoStack.add(undoStack.pop());
	}
	catch(EmptyStackException e) {
	    return null;
	}
	return undoStack.peek();
    }
    
    public ArrayList<String> redo() {
	if(redoStack.isEmpty() || redoStack == null || undoStack == null) {
	    return null;
	}
	try {
		undoStack.add(redoStack.pop());
	}
	catch(EmptyStackException e) {
	    return null;
	}
	return undoStack.peek();
    }

}

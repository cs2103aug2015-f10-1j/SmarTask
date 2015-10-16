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

    Stack<ArrayList<String>> undoStack;
    Stack<ArrayList<String>> redoStack;

    public CommandHistory(ArrayList<String> storedTask) {
        undoStack = new Stack<ArrayList<String>>();
        undoStack.push(storedTask);
        redoStack = new Stack<ArrayList<String>>();
    }

    public void addChangeToHistory(ArrayList<String> storedTask) {
        undoStack.push(storedTask);
    }

    public ArrayList<String> undo() {
        try {
            redoStack.push(undoStack.pop());
        }
        catch(EmptyStackException e) {
            return null;
        }
        return undoStack.peek();
    }

    public ArrayList<String> redo() {
        try {
            undoStack.push(redoStack.pop());
        }
        catch(EmptyStackException e) {
            return null;
        }
        return undoStack.peek();
    }

}

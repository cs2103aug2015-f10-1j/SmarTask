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
    private final String MSG_UNDO_FAIL = "cannot undo!!!";
    private final String MSG_REDO_FAIL = "cannot redo!!!";

    public CommandHistory(ArrayList<String> storedTask) {
        undoStack = new Stack<ArrayList<String>>();
        undoStack.push(storedTask);
        redoStack = new Stack<ArrayList<String>>();
    }

    public void addChangeToHistory(ArrayList<String> storedTask) {
        undoStack.push(storedTask);
    }

    public ArrayList<String> undo() throws Exception {
        if(undoStack.empty() || undoStack.peek() == null || undoStack.size() == 1) {
            throw new Exception(MSG_UNDO_FAIL);
        }
        try {
            redoStack.push(undoStack.pop());
        }
        catch(Exception e) {
            throw new Exception(MSG_UNDO_FAIL);
        }
        return undoStack.peek();
    }

    public ArrayList<String> redo() throws Exception {
        if(redoStack.empty() || redoStack.peek() == null || redoStack.size() == 1) {
            throw new Exception(MSG_REDO_FAIL);
        }
        try {
            undoStack.push(redoStack.pop());
        }
        catch(Exception e) {
            throw new Exception(MSG_REDO_FAIL);
        }
        return undoStack.peek();
    }

}

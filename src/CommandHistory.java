import java.util.ArrayList;
import java.util.Stack;

/**
 * CommandHistory helps to undo and redo any command made by user. Two stacks
 * are used to store different versions of the data at a specific time of
 * command execution.
 * 
 * @@author A0108235M-reused A0121284N
 */

public class CommandHistory {

    private final String MSG_UNDO_FAIL = "cannot undo!!!";
    private final String MSG_REDO_FAIL = "cannot redo!!!";

    public Stack<ArrayList<Task>> undoStack;
    public Stack<ArrayList<Task>> redoStack;

    public CommandHistory(ArrayList<Task> storedTask) {
        undoStack = new Stack<ArrayList<Task>>();
        undoStack.push(storedTask);
        redoStack = new Stack<ArrayList<Task>>();
    }

    public void addChangeToHistory(ArrayList<Task> storedTask) {
        undoStack.push(storedTask);
    }

    public ArrayList<Task> undo() throws Exception {
        if (undoStack.empty() || undoStack.peek() == null || undoStack.size() == 1) {
            throw new Exception(MSG_UNDO_FAIL);
        }

        try {
            redoStack.push(undoStack.pop());
        } catch (Exception e) {
            throw new Exception(MSG_UNDO_FAIL);
        }

        return undoStack.peek();
    }

    public ArrayList<Task> redo() throws Exception {
        if (redoStack.empty() || redoStack.peek() == null) {
            throw new Exception(MSG_REDO_FAIL);
        }

        try {
            undoStack.push(redoStack.pop());
        } catch (Exception e) {
            throw new Exception(MSG_REDO_FAIL);
        }

        return undoStack.peek();
    }
}
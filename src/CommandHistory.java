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

    private static final int SIZE_1 = 1;
    private static final String MSG_UNDO_FAIL = "Fail to undo.";
    private static final String MSG_REDO_FAIL = "Fail to redo.";

    public Stack <ArrayList<Task>> undoStack;
    public Stack <ArrayList<Task>> redoStack;

    public CommandHistory(ArrayList<Task> storedTask) {
        undoStack = new Stack <ArrayList<Task>>();
        undoStack.push(storedTask);
        redoStack = new Stack <ArrayList<Task>>();
    }

    public void addChangeToHistory(ArrayList<Task> storedTask) {
        undoStack.push(storedTask);
    }

    // =========================================================================
    // Execute undo
    // =========================================================================

    public ArrayList<Task> undo() throws Exception {
        if (isInvalidUndoStack()) {
            throw new Exception(MSG_UNDO_FAIL);
        }

        try {
            redoStack.push(undoStack.pop());
        } catch (Exception e) {
            throw new Exception(MSG_UNDO_FAIL);
        }

        return undoStack.peek();
    }

    // =========================================================================
    // Execute redo
    // =========================================================================

    public ArrayList<Task> redo() throws Exception {
        if (isInvalidRedoStack()) {
            throw new Exception(MSG_REDO_FAIL);
        }

        try {
            undoStack.push(redoStack.pop());
        } catch (Exception e) {
            throw new Exception(MSG_REDO_FAIL);
        }

        return undoStack.peek();
    }

    // =========================================================================
    // Auxiliary methods
    // =========================================================================

    private boolean isInvalidRedoStack() {
        return redoStack.empty() || redoStack.peek() == null;
    }

    private boolean isInvalidUndoStack() {
        return undoStack.empty() || undoStack.peek() == null || undoStack.size() == SIZE_1;
    }

}
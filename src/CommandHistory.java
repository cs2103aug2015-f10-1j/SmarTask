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
    
    Stack<ArrayList <String>> undoStack = new Stack<ArrayList<String>>();
    Stack<ArrayList <String>> redoStack = new Stack<ArrayList<String>>();
    
    public CommandHistory() {
	// TODO Auto-generated constructor stub
    }

}

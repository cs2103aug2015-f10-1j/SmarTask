import java.util.ArrayList;

/**
 * CommandHistoryStubTest checks if undo and redo works
 * 
 * @author A0108235M
 */

public class CommandHistoryStubTest {

	public static void main(String[] args) {
		ArrayList<Task> storedTask = new ArrayList<Task>();
		storedTask.add(new Task());
		CommandHistory history = new CommandHistory(new ArrayList<Task>(storedTask));
		printArrayList(history.undoStack.peek());
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		storedTask.add(new Task());
		history.addChangeToHistory(new ArrayList<Task>(storedTask));
		printArrayList(history.undoStack.peek());
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		storedTask.add(new Task());
		history.addChangeToHistory(new ArrayList<Task>(storedTask));
		printArrayList(history.undoStack.peek());
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		storedTask.add(new Task());
		history.addChangeToHistory(new ArrayList<Task>(storedTask));
		printArrayList(history.undoStack.peek());
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After undo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After undo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After redo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After redo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After undo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {

		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After undo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After undo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

		System.out.print("After undo : ");
		try {
			printArrayList(history.undo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());

	}

	public static void printArrayList(ArrayList<Task> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
	}
}

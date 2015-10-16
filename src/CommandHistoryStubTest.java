import java.util.ArrayList;

public class CommandHistoryStubTest {

    public static void main(String[] args) {
        ArrayList<String> storedTask = new ArrayList<String>();
        storedTask.add("Task 0");
        CommandHistory history = new CommandHistory(new ArrayList<String>(storedTask));
        printArrayList(history.undoStack.peek());
        System.out.println("Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());
        
        storedTask.add("Task 1");
        history.addChangeToHistory(new ArrayList<String>(storedTask));
        printArrayList(history.undoStack.peek());
        System.out.println("Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());
        
        storedTask.add("Task 2");
        history.addChangeToHistory(new ArrayList<String>(storedTask));
        printArrayList(history.undoStack.peek());
        System.out.println("Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());
        
        storedTask.add("Task 3");
        history.addChangeToHistory(new ArrayList<String>(storedTask));
        printArrayList(history.undoStack.peek());
        System.out.println("Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());
        
        System.out.print("After undo : ");
        printArrayList(history.undo());
        System.out.println("Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());
        
        System.out.print("After undo : ");
        printArrayList(history.undo());
        System.out.println("Undo Stack Size: " + history.undoStack.size() + " Redo Stack Size: " + history.redoStack.size());
    }
    
    public static void printArrayList(ArrayList<String> list) {
        for(int i = 0; i <list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

}

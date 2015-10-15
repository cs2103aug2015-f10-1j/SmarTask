import java.util.*;

/**
 * Command is a class that contains all the required information for Logic to
 * execute it. It is created by CommandParser's parse method.
 * 
 * @author Bobby Lin
 *
 */

public class Command {
    public enum Type {
        ADD, DELETE, UPDATE, COMPLETE, VIEW, EXIT, INVALID, SEARCH,
        UNDO, REDO, REPEAT, CANCEL_REPEAT
    }

    private Type type;
    private ArrayList<String> taskInput;
    private String taskTitle;
    private String taskLabel;
    private String taskDetail;
    private String taskTime;
    private String recurringPeriod;
    private ArrayList<String> searchKeyword;
    private int taskNumber;

    public Command(Type type) {
        this.setTaskInput(new ArrayList<String>());
        this.type = type;
    }

    public Type getCommandType() {
        return type;
    }

    // ================================================================
    // Getters method to support Logic methods
    // ================================================================

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskLabel() {
        return taskLabel;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public ArrayList<String> getTaskInput() {
        return taskInput;
    }
    
    public int getTaskNumber() {
        return taskNumber;
    }
    
    public ArrayList<String> getSearchKeyword() {
        return searchKeyword;
    }
    
    public String getRecurringPeriod() {
        return recurringPeriod;
    }

    // ================================================================
    // Setters method to support CommandParser methods
    // ================================================================

    public void setTaskInput(ArrayList<String> taskInput) {
        this.taskInput = taskInput;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskLabel(String taskLabel) {
        this.taskLabel = taskLabel;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public void setSearchKeyword(ArrayList<String> arguments) {
        this.searchKeyword = arguments;
    }

    public void setRecurringPeriod(String recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }


}

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
        ADD, DELETE, VIEW, EXIT, INVALID
    }
	
	private Type type;
	private ArrayList<String> taskInput;
	private String taskTitle;
	private String taskLabel;
	private String taskDetail;
	private String taskTime;
    
    public Command(Type type) {
    	this.setTaskInput(new ArrayList<String>());
    	this.type = type;
    }

    public Type getCommandType() {
        return type;
    }
    
    // ================================================================
    // "Add" command methods
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

    // ================================================================
    // TO DO: "Delete" command methods
    // ================================================================
    
    
    // ================================================================
    // TO DO: "View" command methods
    // ================================================================

    
}

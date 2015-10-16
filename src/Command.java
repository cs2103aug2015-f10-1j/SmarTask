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
    private String taskType;
    private String taskDescription;
    private String taskDeadline;
    private String taskEventDate;
    private String taskEventTime;
    private String taskRepeatPeriod;
    private ArrayList<String> searchKeyword;
    private int taskID;

    public Command(Type type) {
        this.type = type;
    }

    // ================================================================
    // Getters method to support Logic methods
    // ================================================================
    
    public String getTaskDescription() {
	return taskDescription;
    }
    
    public Type getCommandType() {
        return type;
    }
    
    public String getTaskDeadline() {
	return taskDeadline;
    }
    
    public String getTaskEventDate() {
	return taskEventDate;
    }
    
    public String getTaskEventTime() {
	return taskEventTime;
    }

    public String getTaskRepeatPeriod() {
	return taskRepeatPeriod;
    }
    
    public ArrayList<String> getSearchKeyword() {
	return searchKeyword;
    }
    
    public int getTaskID() {
	return taskID;
    }
    
    public String getTaskType() {
	return taskType;
    }
   
    // ================================================================
    // Setters method to support CommandParser methods
    // ================================================================
    
    public void setTaskDescription(String taskDescription) {
	this.taskDescription = taskDescription;
    }
    
    public void setTaskDeadline(String taskDeadline) {
	this.taskDeadline = taskDeadline;
    }

    public void setTaskEventDate(String taskEventDate) {
   	this.taskEventDate = taskEventDate;
       }
    
    public void setTaskEventTime(String taskEventTime) {
	this.taskEventTime = taskEventTime;
    }
    
    public void setSearchKeyword(ArrayList<String> searchKeyword) {
	this.searchKeyword = searchKeyword;
    }
    
    public void setTaskRepeatPeriod(String taskRepeatPeriod) {
	this.taskRepeatPeriod = taskRepeatPeriod ;
    }
    
    public void setTaskID(int taskID) {
	this.taskID = taskID;
    }
    
    public void setTaskType(String taskType) {
	this.taskType = taskType;
    }
    
}

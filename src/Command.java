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
	UNDO, REDO, REPEAT, STOP_REPEAT
    }

    private int taskID;
    private Type type;
    private String taskType;
    private String taskDescription;
    private String taskDeadline;
    private String taskEventDate;
    private String taskEventTime;
    private ArrayList<String> searchKeyword;

    // Additional attributes for recurrencing task
    private String dateOfRepeatAdded;
    private String taskRepeatDuration;
    private String taskRepeatType;
    private String taskRepeatDayFrequency;
    private String taskRepeatMonthFrequency;
    private Boolean[] isTaskRepeatOnDayOfWeek = new Boolean[7]; // Example: Mon, Tues, Fri
    private String taskRepeatMonthFrequencyBySpecificDate; // Example: on 19 of every month
    private String[] taskRepeatMonthFrequencyBySpecificDayOfWeek; //  Example: first-mon of every month
    private String taskRepeatYearFrequency;
    private String taskRepeatEndDate;
    private ArrayList<String> stopRepeat;
    private ArrayList<String> updateRepeat;

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
    // Getters method to support recurring task
    // ================================================================
    
    public ArrayList<String> getStopRepeat() {
	return stopRepeat;
    }
    
    public String getDateOfRepeatAdded() {
	return dateOfRepeatAdded;
    }

    public String getTaskRepeatDuration() {
	return taskRepeatDuration;
    }

    public String getTaskRepeatEndDate() {
	return taskRepeatEndDate;
    }

    public String getTaskRepeatType() {
	return taskRepeatType;
    }

    public void setTaskRepeatDayFrequency(String taskRepeatDayFrequency) {
	this.taskRepeatDayFrequency = taskRepeatDayFrequency;
    }

    public String getTaskRepeatDayFrequency() {
	return taskRepeatDayFrequency;
    }

    public String getTaskRepeatMonthFrequency() {
	return taskRepeatMonthFrequency;
    }

    public String getTaskRepeatYearFrequency() {
	return taskRepeatYearFrequency;
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

    public void setTaskID(int taskID) {
	this.taskID = taskID;
    }

    public void setTaskType(String taskType) {
	this.taskType = taskType;
    }

    // ================================================================
    // Setters method to support recurring task
    // ================================================================

    public void setStopRepeat(ArrayList<String> cancelRepeatDateAndTime) {
	this.stopRepeat = cancelRepeatDateAndTime;
    }

    public void setDateOfRepeatAdded(String strings) {
	this.dateOfRepeatAdded = strings;
    }

    public void setTaskRepeatDuration(String taskRepeatDuration) {
	this.taskRepeatDuration = taskRepeatDuration;
    }

    public void setTaskRepeatEndDate(String taskRepeatEndDate) {
	this.taskRepeatEndDate = taskRepeatEndDate;
    }

    public void setTaskRepeatType(String taskRepeatType) {
	this.taskRepeatType = taskRepeatType;
    }

    public void setTaskRepeatMonthFrequency(String taskRepeatMonthFrequency) {
	this.taskRepeatMonthFrequency = taskRepeatMonthFrequency;
    }

    public void setTaskRepeatYearFrequency(String taskRepeatYearFrequency) {
	this.taskRepeatYearFrequency = taskRepeatYearFrequency;
    }

    public String[] getTaskRepeatMonthFrequencyBySpecificDayOfWeek() {
	return taskRepeatMonthFrequencyBySpecificDayOfWeek;
    }

    public void setTaskRepeatMonthFrequencyBySpecificDayOfWeek(String[] taskRepeatMonthFrequencyBySpecificDayOfWeek) {
	this.taskRepeatMonthFrequencyBySpecificDayOfWeek = taskRepeatMonthFrequencyBySpecificDayOfWeek;
    }

    public Boolean[] isTaskRepeatOnDayOfWeek() {
	return isTaskRepeatOnDayOfWeek;
    }

    public void setTaskRepeatOnDayOfWeek(Boolean[] taskRepeatWeekFrequencyByDay) {
	this.isTaskRepeatOnDayOfWeek = taskRepeatWeekFrequencyByDay;
    }

    public String getTaskRepeatMonthFrequencyBySpecificDate() {
	return taskRepeatMonthFrequencyBySpecificDate;
    }

    public void setTaskRepeatMonthFrequencyBySpecificDate(String taskRepeatMonthFrequencyBySpecificDate) {
	this.taskRepeatMonthFrequencyBySpecificDate = taskRepeatMonthFrequencyBySpecificDate;
    }

    public ArrayList<String> getUpdateRepeat() {
	return updateRepeat;
    }

    public void setUpdateRepeat(ArrayList<String> updateRepeat) {
	this.updateRepeat = updateRepeat;
    }

}

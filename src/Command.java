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
        UNDO, REDO, REPEAT, STOP_REPEAT, SETFILEPATH
    }

    private int taskID;
    private Type type;
    private String taskType;
    private String taskDescription;
    
    private String taskDeadline;
    
    private String taskEventStart;
    private String taskEventEnd;
    private String taskEventDate;
    private String taskEventTime;
    
    private String filePath;
    private ArrayList<String> searchKeyword;

    // Additional attributes for recurrencing task
    private String repeatType;
    private String dateAdded;
    private String repeatStartTime;
    private String repeatEndTime;
    private String repeatUntil;
    private ArrayList<String> stopRepeat;

    // ======= Day recurrence =======
    private String dayInterval;
    
    /*
    private String taskRepeatMonthFrequency;
    private Boolean[] isTaskRepeatOnDayOfWeek = new Boolean[7]; // Example: Mon, Tues, Fri
    private String taskRepeatMonthFrequencyBySpecificDate; // Example: on 19 of every month
    private String[] taskRepeatMonthFrequencyBySpecificDayOfWeek; //  Example: first-mon of every month
    private String taskRepeatYearFrequency;
    
    */
    
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
    
    public String getFilePath() {
        return filePath;
    }

    // ================================================================
    // Setters method to support recurring task
    // ================================================================

    public void setStopRepeat(ArrayList<String> cancelRepeatDateAndTime) {
        this.stopRepeat = cancelRepeatDateAndTime;
    }

    public ArrayList<String> getUpdateRepeat() {
        return updateRepeat;
    }

    public void setUpdateRepeat(ArrayList<String> updateRepeat) {
        this.updateRepeat = updateRepeat;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTaskEventStart() {
        return taskEventStart;
    }

    public void setTaskEventStart(String taskEventStart) {
        this.taskEventStart = taskEventStart;
    }

    public String getTaskEventEnd() {
        return taskEventEnd;
    }

    public void setTaskEventEnd(String taskEventEnd) {
        this.taskEventEnd = taskEventEnd;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }
    
    public String getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(String repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public String getDayInterval() {
        return dayInterval;
    }

    public void setDayInterval(String dayInterval) {
        this.dayInterval = dayInterval;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getRepeatStartTime() {
        return repeatStartTime;
    }

    public void setRepeatStartTime(String repeatStartTime) {
        this.repeatStartTime = repeatStartTime;
    }

    public String getRepeatEndTime() {
        return repeatEndTime;
    }

    public void setRepeatEndTime(String repeatEndTime) {
        this.repeatEndTime = repeatEndTime;
    }

}

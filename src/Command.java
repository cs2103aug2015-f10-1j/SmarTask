import java.util.*;

/**
 * Command is a class that contains all the required information for Logic to
 * execute it. It is created by CommandParser's parse method.
 * 
 * @author Bobby Lin
 */

public class Command {

    public enum Type {
        ADD, DELETE, UPDATE, COMPLETE, VIEW, EXIT, INVALID, SEARCH, UNDO, REDO, REPEAT, STOP_REPEAT, SETFILEPATH
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

    // Additional attributes for recurring task
    private String repeatType;
    private Date dateAdded;
    private Date repeatUntil;
    private String repeatStartTime;
    private String repeatEndTime;
    private ArrayList<Date> stopRepeat;
    private String stopRepeatInString;

    // ======= Day repeat =======
    private String dayInterval;

    // ======= Week repeat =======
    private String weekInterval;
    private Boolean[] isDaySelected;
    private String daySelectedString;

    // ======= Month repeat =======
    private String monthInterval;

    // ======= Year repeat =======
    private String yearInterval;

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

    public String getDayInterval() {
        return dayInterval;
    }

    public void setDayInterval(String dayInterval) {
        this.dayInterval = dayInterval;
    }

    public String getYearInterval() {
        return yearInterval;
    }

    public void setYearInterval(String yearInterval) {
        this.yearInterval = yearInterval;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
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

    public Date getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public String getWeekInterval() {
        return weekInterval;
    }

    public void setWeekInterval(String weekInterval) {
        this.weekInterval = weekInterval;
    }

    public Boolean[] getIsDaySelected() {
        return isDaySelected;
    }

    public void setIsDaySelected(Boolean[] isDaySelected) {
        this.isDaySelected = isDaySelected;
    }

    public String getMonthInterval() {
        return monthInterval;
    }

    public void setMonthInterval(String monthInterval) {
        this.monthInterval = monthInterval;
    }

    public ArrayList<Date> getStopRepeat() {
        return stopRepeat;
    }

    public void setStopRepeat(ArrayList<Date> stopRepeat) {
        this.stopRepeat = stopRepeat;
    }

    public String getDaySelectedString() {
        return daySelectedString;
    }

    public void setDaySelectedString() {
        String index = "";

        for (int i = 1; i < this.isDaySelected.length; i++) {
            if (isDaySelected[i] == true) {
                if (i != this.isDaySelected.length - 1) {
                    index += i + " ";
                } else {
                    index += i;
                }
            }
        }

        this.daySelectedString = index;
    }

    public String getStopRepeatInString() {
        return stopRepeatInString;
    }

    public void setStopRepeatInString() {
        String dates = "";

        for (int i = 0; i < stopRepeat.size(); i++) {
            if (i != stopRepeat.size() - 1) {
                dates += stopRepeat.get(i).toString() + "@";
            } else {
                dates += stopRepeat.get(i).toString();
            }
        }

        this.stopRepeatInString = dates;
    }
}
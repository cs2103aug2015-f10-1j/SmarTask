import java.util.*;

/**
 * Command is a class that contains all the required information for Logic to
 * execute it. It is created by CommandParser's parse method.
 * 
 * @@author A0108235M-Sebastian Quek (Collate Project)
 */

public class Command {

    private static final String REGEX_BLANK = "";
    private static final String REGEX_AT_SIGN = "@";
    private static final int SIZE_1 = 1;

    public enum Type {
        ADD, DELETE, UPDATE, COMPLETE, VIEW, EXIT, INVALID, SEARCH, UNDO, REDO, 
        REPEAT, STOP_REPEAT, SETFILEPATH
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
    private String dayInterval;
    private String weekInterval;
    private Boolean[] isDaySelected;
    private String daySelectedString;
    private String monthInterval;
    private String yearInterval;

    public Command(Type type) {
        this.type = type;
    }

    // =========================================================================
    // Getters method to support Logic methods
    // =========================================================================
    
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
    
    public String getRepeatStartTime() {
        return repeatStartTime;
    }
    
    public String getRepeatEndTime() {
        return repeatEndTime;
    }
    
    public Date getRepeatUntil() {
        return repeatUntil;
    }

    public String getWeekInterval() {
        return weekInterval;
    }
    
    public Boolean[] getIsDaySelected() {
        return isDaySelected;
    }
    
    public String getMonthInterval() {
        return monthInterval;
    } 

    public ArrayList<Date> getStopRepeat() {
        return stopRepeat;
    }
    
    public String getDaySelectedString() {
        return daySelectedString;
    }
    
    public String getStopRepeatInString() {
        return stopRepeatInString;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTaskEventStart() {
        return taskEventStart;
    }

    public String getTaskEventEnd() {
        return taskEventEnd;
    }
    
    public String getRepeatType() {
        return repeatType;
    }
    
    public String getDayInterval() {
        return dayInterval;
    }

    public String getYearInterval() {
        return yearInterval;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    // =========================================================================
    // Setters method to support CommandParser methods
    // =========================================================================
    
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public void setTaskEventStart(String taskEventStart) {
        this.taskEventStart = taskEventStart;
    }

    public void setTaskEventEnd(String taskEventEnd) {
        this.taskEventEnd = taskEventEnd;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public void setDayInterval(String dayInterval) {
        this.dayInterval = dayInterval;
    }

    public void setYearInterval(String yearInterval) {
        this.yearInterval = yearInterval;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setRepeatStartTime(String repeatStartTime) {
        this.repeatStartTime = repeatStartTime;
    }

    public void setRepeatEndTime(String repeatEndTime) {
        this.repeatEndTime = repeatEndTime;
    }

    public void setRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public void setWeekInterval(String weekInterval) {
        this.weekInterval = weekInterval;
    }
    
    public void setIsDaySelected(Boolean[] isDaySelected) {
        this.isDaySelected = isDaySelected;
    }
    
    public void setMonthInterval(String monthInterval) {
        this.monthInterval = monthInterval;
    }

    public void setStopRepeat(ArrayList<Date> stopRepeat) {
        this.stopRepeat = stopRepeat;
    }

    public void setDaySelectedString() {
        String index = REGEX_BLANK;

        for (int i = 1; i < this.isDaySelected.length; i++) {
            if (isDaySelected[i] == true) {
                if (i != this.isDaySelected.length - 1) {
                    index += i + " ";
                } else {
                    index += i;
                }
            }
        }

        this.daySelectedString = index.trim();
    }

    public void setStopRepeatInString() {
        String dates = REGEX_BLANK;

        for (int i = 0; i < stopRepeat.size(); i++) {
            if (i != stopRepeat.size() - SIZE_1) {
                dates += stopRepeat.get(i).toString() + REGEX_AT_SIGN;
            } else {
                dates += stopRepeat.get(i).toString();
            }
        }

        this.stopRepeatInString = dates;
    }
}
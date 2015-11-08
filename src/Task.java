import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Task is an object class that contains all attributes of a task
 * 
 * @@author A0108235M
 */

public class Task {
    
    // Keywords used in task object methods
    
    private static final String KEYWORD_DAY = "day";
    private static final String KEYWORD_WEEK = "week";
    private static final String KEYWORD_MONTH = "month";
    private static final String KEYWORD_YEAR = "year";
    private static final String KEYWORD_REPEAT = "repeat";
    private static final String KEYWORD_DEADLINE = "deadline";
    private static final String KEYWORD_EVENT = "event";
    private static final String KEYWORD_FLOATING = "floating";
    
    // Regex
    
    private static final String REGEX_RIGHT_SQBRACKET = "]";
    private static final String REGEX_LEFT_SQBRACKET = "[";
    private static final String REGEX_COMMA = ",";
    private static final String REGEX_BLANK = "";
    private static final String REGEX_WHITESPACE = " ";
    private static final String REGEX_HASH = "#";
    private static final String REGEX_AT_SIGN = "@";
   
    // Integer constants  
    
    private static final int INDEX_0 = 0;
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;
    private static final int INDEX_6 = 6;
    private static final int INDEX_7 = 7;
    private static final int INDEX_8 = 8;
    private static final int INDEX_9 = 9;
    private static final int SIZE_0 = 0;
    private static final int SIZE_8 = 8;
    
    private static final String MSG_INVALID_FORMAT_FOUND = "Invalid format found";
    private static final DateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
    
    public enum Type {
        EVENT, DEADLINE, FLOATING, REPEAT
    }

    private Type type;
    private int id;
    private String description;
    private String deadline;
    private String eventStart;
    private String eventEnd;
    private String timeAdded;

    // Additional attributes used by repeated task

    private String taskRepeatType;
    private Date dateAdded;
    private String taskRepeatStartTime;
    private String taskRepeatEndTime;
    private String taskNextOccurrence;
    private ArrayList<Date> stopRepeat;
    private String stopRepeatInString;
    private String taskRepeatInterval_Day;
    private String taskRepeatInterval_Week;
    private Boolean[] isDaySelected;
    private String taskRepeatInterval_Month;
    private String taskRepeatInterval_Year;
    private Date taskRepeatUntil;
    private Boolean isComplete;

    public Task() {
    }

    public Task(Type type, ArrayList<String> attributeList) throws Exception {
        setType(type);
        setAttributes(type, attributeList);
    }

    // =========================================================================
    // Getter and Setters of Task object
    // =========================================================================
    
    private void setAttributes(Type type, ArrayList<String> attributeList) throws Exception {
        this.type = type;
        String[] list = null;
        if (attributeList.size() > SIZE_0) {
            list = extractParam(attributeList);
        }
        initTask(type, list);
    }

    public Type getType() {
        return type;
    }

    public int getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getEventStart() {
        return eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }

    public void setIsComplete(Boolean bo) {
        this.isComplete = bo;
    }

    public String getTaskRepeatType() {
        return taskRepeatType;
    }

    public void setTaskRepeatType(String taskRepeatType) {
        this.taskRepeatType = taskRepeatType;
    }

    public Date getTaskRepeatUntil() {
        return taskRepeatUntil;
    }

    public void setTaskRepeatUntil(String taskRepeatUntil) throws ParseException {
        Date date = sdf.parse(taskRepeatUntil);
        this.taskRepeatUntil = date;
    }

    public String getTaskNextOccurrence() {
        return taskNextOccurrence;
    }

    public void setTaskNextOccurrence(String taskNextOccurrence) {
        this.taskNextOccurrence = taskNextOccurrence;
    }

    public String getTaskRepeatInterval_Day() {
        return taskRepeatInterval_Day;
    }

    public void setTaskRepeatInterval_Day(String taskRepeatInterval_Day) {
        this.taskRepeatInterval_Day = taskRepeatInterval_Day;
    }

    public String getTaskRepeatInterval_Year() {
        return taskRepeatInterval_Year;
    }

    public void setTaskRepeatInterval_Year(String taskRepeatInterval_Year) {
        this.taskRepeatInterval_Year = taskRepeatInterval_Year;
    }

    public String getTaskRepeatStartTime() {
        return taskRepeatStartTime;
    }

    public void setTaskRepeatStartTime(String taskRepeatStartTime) {
        this.taskRepeatStartTime = taskRepeatStartTime;
    }

    public String getTaskRepeatEndTime() {
        return taskRepeatEndTime;
    }

    public void setTaskRepeatEndTime(String taskRepeatEndTime) {
        this.taskRepeatEndTime = taskRepeatEndTime;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) throws ParseException {
        Date date = sdf.parse(dateAdded);
        this.dateAdded = date;
    }

    public String getTaskRepeatInterval_Week() {
        return taskRepeatInterval_Week;
    }

    public void setTaskRepeatInterval_Week(String taskRepeatInterval_Week) {
        this.taskRepeatInterval_Week = taskRepeatInterval_Week;
    }

    public String getTaskRepeatInterval_Month() {
        return taskRepeatInterval_Month;
    }

    public void setTaskRepeatInterval_Month(String taskRepeatInterval_Month) {
        this.taskRepeatInterval_Month = taskRepeatInterval_Month;
    }

    public Boolean[] getIsDaySelected() {
        return isDaySelected;
    }

    public void setIsDaySelected(String list) {
        isDaySelected = new Boolean[SIZE_8];
        Arrays.fill(isDaySelected, false);
        String[] days = list.split(REGEX_WHITESPACE);
        this.isDaySelected[INDEX_0] = null;

        for (int i = 0; i < days.length; i++) {
            this.isDaySelected[Integer.parseInt(days[i].trim())] = true;
        }
    }

    public String getStopRepeatInString() {
        return stopRepeatInString;
    }

    public void setStopRepeatInString(String stopRepeatInString) {
        this.stopRepeatInString = stopRepeatInString;
    }

    public ArrayList<Date> getStopRepeat() {
        return stopRepeat;
    }

    public void setStopRepeat(String input) throws ParseException {
        String[] dateArr = input.split(REGEX_AT_SIGN);
        
        Date date;
        this.stopRepeat = new ArrayList<Date>();

        for (int i = 0; i < dateArr.length; i++) {
            date = sdf.parse(dateArr[i]);
            this.stopRepeat.add(date);
        }
    }

    public void setDefaultStopRepeat(String input) throws ParseException {
        this.stopRepeat = new ArrayList<Date>();
        if (input.contains(REGEX_COMMA)) {
            String[] param = input.split(REGEX_COMMA);
            for(int i =0; i <param.length; i++) {
                Date date = sdf.parse(param[i].trim());
                this.stopRepeat.add(date);
            }
        } else {
            Date date = sdf.parse(input);
            this.stopRepeat.add(date);
        }

    }

    public String convertBooleanDaySelectedToString() {
        String index = REGEX_BLANK;
        for(int i=0; i < this.isDaySelected.length; i++) {
            if(this.isDaySelected[i]) {
                index += i + REGEX_WHITESPACE;
            }
        }
        return index.trim();
    }

    // =========================================================================
    // Methods to initalize task
    // =========================================================================

    private void initTask(Type type, String[] list) throws Exception {
        if (type.equals(Task.Type.EVENT)) {
            initEventTask(list);
        } else if (type.equals(Task.Type.DEADLINE)) {
            initDeadlineTask(list);
        } else if (type.equals(Task.Type.FLOATING)) {
            initFloatingTask(list);
        } else if (type.equals(Task.Type.REPEAT)) {
            initRepeatTask(list);
        }
    }

    private void initRepeatTask(String[] list) throws Exception {
        try {
            String repeatType = list[INDEX_0];
            if (repeatType.equals(KEYWORD_DAY)) {
                initDayRepeat(list);
            } else if (repeatType.equals(KEYWORD_YEAR)) {
                initYearRepeat(list);
            } else if (repeatType.equals(KEYWORD_WEEK)) {
                initWeekRepeat(list);
            } else if (repeatType.equals(KEYWORD_MONTH)) {
                initMonthRepeat(list);
            }
        } catch (ParseException e) {
            throw new Exception(MSG_INVALID_FORMAT_FOUND);
        }
    }

    private void initMonthRepeat(String[] list) throws ParseException {
        this.taskRepeatType = list[INDEX_0];
        setDateAdded(list[INDEX_1]);
        this.taskRepeatStartTime = list[INDEX_2];
        this.taskRepeatEndTime = list[INDEX_3];
        this.setTaskRepeatInterval_Month(list[INDEX_4]);
        setTaskRepeatUntil(list[INDEX_5]);
        this.description = list[INDEX_6];
        this.id = Integer.parseInt(list[INDEX_7]);
        String param = list[INDEX_8];
        if(param.contains(REGEX_AT_SIGN)) {
            setStopRepeat(param);
        } else {
            setDefaultStopRepeat(param);
        }
        this.isComplete = false;
    }

    private void initWeekRepeat(String[] list) throws ParseException {
        this.taskRepeatType = list[INDEX_0];
        setDateAdded(list[INDEX_1]);
        this.taskRepeatStartTime = list[INDEX_2];
        this.taskRepeatEndTime = list[INDEX_3];
        this.setTaskRepeatInterval_Week(list[INDEX_4]);
        setIsDaySelected(list[INDEX_5]);
        setTaskRepeatUntil(list[INDEX_6]);
        this.description = list[INDEX_7];
        this.id = Integer.parseInt(list[INDEX_8]);
        String param = list[INDEX_9];
        if(param.contains(REGEX_AT_SIGN)) {
            setStopRepeat(param);
        } else {
            setDefaultStopRepeat(param);
        }
        this.isComplete = false;
    }

    private void initYearRepeat(String[] list) throws ParseException {
        this.taskRepeatType = list[INDEX_0];
        setDateAdded(list[INDEX_1]);
        this.taskRepeatStartTime = list[INDEX_2];
        this.taskRepeatEndTime = list[INDEX_3];
        this.taskRepeatInterval_Year = list[INDEX_4];
        setTaskRepeatUntil(list[INDEX_5]);
        this.description = list[INDEX_6];
        this.id = Integer.parseInt(list[INDEX_7]);
        String param = list[INDEX_8];
        if(param.contains(REGEX_AT_SIGN)) {
            setStopRepeat(param);
        } else {
            setDefaultStopRepeat(param);
        }
        this.isComplete = false;
    }

    private void initDayRepeat(String[] list) throws ParseException {
        this.taskRepeatType = list[INDEX_0];
        setDateAdded(list[INDEX_1]);
        this.taskRepeatStartTime = list[INDEX_2];
        this.taskRepeatEndTime = list[INDEX_3];
        this.taskRepeatInterval_Day = list[INDEX_4];
        setTaskRepeatUntil(list[INDEX_5]);
        this.description = list[INDEX_6];
        this.id = Integer.parseInt(list[INDEX_7]);
        String param = list[INDEX_8];
        if(param.contains(REGEX_AT_SIGN)) {
            setStopRepeat(param);
        } else {
            setDefaultStopRepeat(param);
        }
        this.isComplete = false;
    }

    private void initFloatingTask(String[] list) {
        this.description = list[INDEX_0];
        this.id = Integer.parseInt(list[INDEX_1]);
        this.isComplete = false;
    }

    private void initDeadlineTask(String[] list) {
        this.deadline = list[INDEX_0];
        this.description = list[INDEX_1];
        this.id = Integer.parseInt(list[INDEX_2]);
        this.isComplete = false;
    }

    private void initEventTask(String[] list) {
        this.eventStart = list[INDEX_0];
        this.eventEnd = list[INDEX_1];
        this.description = list[INDEX_2];
        this.id = Integer.parseInt(list[INDEX_3]);
        this.isComplete = false;
    }

    // =========================================================================
    // Auxiliary methods for Task object
    // =========================================================================
    
    private String[] extractParam(ArrayList<String> attributeList) {
        return attributeList.get(INDEX_0).trim().split(REGEX_HASH);
    }

    public static Type getTypeFromString(String type) {
        if (type == KEYWORD_FLOATING) {
            return Type.FLOATING;
        } else if (type == KEYWORD_EVENT) {
            return Type.EVENT;
        } else if (type == KEYWORD_DEADLINE) {
            return Type.DEADLINE;
        } else if (type == KEYWORD_REPEAT) {
            return Type.REPEAT;
        } else {
            return null;
        }
    }

    public String getFloatingString() {
        return this.description + REGEX_HASH + this.id;
    }

    public String getEventString() {
        return this.eventStart + REGEX_HASH + this.eventEnd + REGEX_HASH + this.description 
                + REGEX_HASH + this.id;
    }

    public String getDeadlineString() {
        return this.deadline + REGEX_HASH + this.description + REGEX_HASH + this.id;
    }

    public String getRepeatString() {
        if (this.taskRepeatType.equals(KEYWORD_DAY)) {                
            return getDayRepeatString();
        } else if (this.taskRepeatType.equals(KEYWORD_WEEK)) {
            return getWeekRepeatString();
        } else if (this.taskRepeatType.equals(KEYWORD_MONTH)) {
            return getMonthRepeatString();
        } else if (this.taskRepeatType.equals(KEYWORD_YEAR)) {
            return getYearRepeatString();
        } else {
            return null;
        }     
    }

    private String getYearRepeatString() {
        return this.taskRepeatType + REGEX_HASH + this.dateAdded + REGEX_HASH 
                + this.taskRepeatStartTime + REGEX_HASH + this.taskRepeatEndTime 
                + REGEX_HASH + this.taskRepeatInterval_Year + REGEX_HASH
                + this.taskRepeatUntil + REGEX_HASH + this.description 
                + REGEX_HASH + this.id + REGEX_HASH + this.stopRepeat.toString()
                .replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK).replace(REGEX_RIGHT_SQBRACKET,REGEX_BLANK);
    }

    private String getMonthRepeatString() {
        return this.taskRepeatType + REGEX_HASH + this.dateAdded + REGEX_HASH 
                + this.taskRepeatStartTime + REGEX_HASH + this.taskRepeatEndTime + REGEX_HASH 
                + this.taskRepeatInterval_Month + REGEX_HASH+ this.taskRepeatUntil 
                + REGEX_HASH + this.description + REGEX_HASH + this.id+ REGEX_HASH 
                + this.stopRepeat.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                .replace(REGEX_RIGHT_SQBRACKET,REGEX_BLANK);
    }

    private String getWeekRepeatString() {
        return this.taskRepeatType + REGEX_HASH + this.dateAdded + REGEX_HASH 
                + this.taskRepeatStartTime + REGEX_HASH + this.taskRepeatEndTime 
                + REGEX_HASH + this.taskRepeatInterval_Week + REGEX_HASH
                + this.convertBooleanDaySelectedToString() 
                + REGEX_HASH + this.taskRepeatUntil + REGEX_HASH 
                + this.description + REGEX_HASH + this.id + REGEX_HASH + this.stopRepeat.toString()
                .replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK).replace(REGEX_RIGHT_SQBRACKET,REGEX_BLANK);
    }

    private String getDayRepeatString() {
        return this.taskRepeatType + REGEX_HASH + this.dateAdded + REGEX_HASH 
                + this.taskRepeatStartTime + REGEX_HASH + this.taskRepeatEndTime + REGEX_HASH 
                + this.taskRepeatInterval_Day + REGEX_HASH + this.taskRepeatUntil + REGEX_HASH 
                + this.description + REGEX_HASH + this.id + REGEX_HASH 
                + this.stopRepeat.toString().replace(REGEX_LEFT_SQBRACKET, REGEX_BLANK)
                .replace(REGEX_RIGHT_SQBRACKET,REGEX_BLANK);
    }

}
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Task is an object class that contains all attibutes of a task
 * 
 * @author Bobby Lin
 *
 */

public class Task {

    private static final String empty = "-1";
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

    // Repeated task
    private String taskRepeatType;
    private Date dateAdded;
    private String taskRepeatStartTime;
    private String taskRepeatEndTime;
    private String taskNextOccurrence;

    // For day repeat
    private String taskRepeatInterval_Day;

    // For week repeat
    private String taskRepeatInterval_Week;
    private Boolean[] isDaySelected;

    // For month repeat
    private String taskRepeatInterval_Month;

    // For year repeat
    private String taskRepeatInterval_Year;
    
    private Date taskRepeatUntil;
    private Boolean isComplete;

    public Task() {

    }

    public Task(Type type, ArrayList<String> attributeList) throws Exception {
        setType(type);
        setAttributes(type, attributeList);
    }

    public static Type getTypeFromString(String type) {
        if(type == "floating") {
            return Type.FLOATING;
        }
        else if(type == "event") {
            return Type.EVENT;
        }
        else if(type == "deadline") {
            return Type.DEADLINE;
        }
        else if(type == "repeat") {
            return Type.REPEAT;
        }
        else {
            return null;
        }
    }

    public String getFloatingString (){
        return "floating"+ "#" + this.description;
    }

    public String getEventString (){
        return "event"+ "#" + this.eventStart + "#" + this.eventEnd + "#" +this.description;
    }

    public String getDeadlineString (){
        return "deadline"+ "#" + this.deadline + "#" + this.description;      
    }

    public String getRepeatString (){
        return "repeat"+ "#" + this.getDateAdded() + "#" + this.description;    
    }

    private void setAttributes(Type type, ArrayList<String> attributeList) throws Exception {
        this.type = type;
        String[] list = null;
        if (attributeList.size()>0){
            list = attributeList.get(0).trim().split("#");
        }

        if (type.equals(Task.Type.EVENT)){
            this.eventStart = list[0];
            this.eventEnd = list[1];
            this.description = list[2];
            this.id = Integer.parseInt(list[3]);
            this.isComplete = false;
        } 

        else if (type.equals(Task.Type.DEADLINE)) {
            this.deadline = list[0];
            this.description = list[1];
            this.id = Integer.parseInt(list[2]);
            this.isComplete = false;
        } 

        else if (type.equals(Task.Type.FLOATING)) {
            this.description = list[0];
            this.id = Integer.parseInt(list[1]);
            this.isComplete = false;
        } 

        else if (type.equals(Task.Type.REPEAT)) {
            try {
                if(list[0].equals("day")) {
                    this.taskRepeatType = list[0];             
                    setDateAdded(list[1]);     
                    this.taskRepeatStartTime = list[2];
                    this.taskRepeatEndTime = list[3];
                    this.taskRepeatInterval_Day = list[4];
                    setTaskRepeatUntil(list[5]);
                    this.description = list[6];
                    this.id = Integer.parseInt(list[7]);
                    this.isComplete = false;
                }

                else if(list[0].equals("year")) {
                    this.taskRepeatType = list[0];
                    setDateAdded(list[1]);
                    this.taskRepeatStartTime = list[2];
                    this.taskRepeatEndTime = list[3];
                    this.taskRepeatInterval_Year = list[4];
                    setTaskRepeatUntil(list[5]);
                    this.description = list[6];
                    this.id = Integer.parseInt(list[7]);
                    this.isComplete = false;
                }
                else if(list[0].equals("week")) {
                    this.taskRepeatType = list[0];
                    setDateAdded(list[1]);
                    this.taskRepeatStartTime = list[2];
                    this.taskRepeatEndTime = list[3];
                    this.setTaskRepeatInterval_Week(list[4]);
                    setIsDaySelected(list[5]);
                    setTaskRepeatUntil(list[6]);
                    this.description = list[7];
                    this.id = Integer.parseInt(list[8]);
                    this.isComplete = false;
                }
                else if (list[0].equals("month")) {
                    this.taskRepeatType = list[0];
                    setDateAdded(list[1]);
                    this.taskRepeatStartTime = list[2];
                    this.taskRepeatEndTime = list[3];
                    this.setTaskRepeatInterval_Month(list[4]);
                    setTaskRepeatUntil(list[5]);
                    this.description = list[6];
                    this.id = Integer.parseInt(list[7]);
                    this.isComplete = false;
                }
            }
            catch (ParseException e) {
                throw new Exception("invalid format");
            }
        }       
    }

    // ================================================================
    // Getter methods to get attributes of Task object
    // ================================================================

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

    public Boolean getIsComplete(){
        return isComplete;
    }

    // ================================================================
    // Setter methods to initialize Task object
    // ================================================================

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

    public void setIsComplete(Boolean bo){
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        Date date = sdf.parse(taskRepeatUntil);
        this.taskRepeatUntil =  date;
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
        DateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
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
        isDaySelected = new Boolean[7];
        Arrays.fill(isDaySelected, false);

        String[] days = list.split(",");

        for(int i=0; i< days.length; i++) {
            this.isDaySelected[Integer.parseInt(days[i].trim())] = true;
        }

    }

}

import java.util.ArrayList;

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
    private String repeatPeriod;
    private String dateAdded;
    private String timeAdded;
    private Boolean isComplete;
    
    // For repeated task
    private String taskRepeatType;
    private String taskRepeatDuration;
    private String taskNextOccurrence;
    private String taskRepeatUntil;
    private String taskRepeatInterval_Day;
    private String taskRepeatInterval_Year;
    
    public Task() {

    }

    public Task(Type type, ArrayList<String> attributeList) {
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
        return "floating"+ "#" + this.description +"#"+ Integer.toString(this.id) ;
    }
    public String getEventString (){
        return "event"+ "#" + this.eventStart + "#" + this.eventEnd + "#" +this.description + "#"+ Integer.toString(this.id) ;

    }
    public String getDeadlineString (){
        return "deadline"+ "#" + this.deadline + "#" + this.description + "#"+ Integer.toString(this.id) ;      
    }
    public String getRepeatString (){
        return "repeat"+ "#" + this.repeatPeriod + "#" + this.description + "#"+ Integer.toString(this.id) ;    
    }
    public String getFloatingStringForUI (){
        return this.description +" "+ Integer.toString(this.id) ;
    }
    public String getEventStringForUI (){
        return this.eventStart + " " + this.eventEnd + " " +this.description + " "+ Integer.toString(this.id) ;

    }
    public String getDeadlineStringForUI (){
        return this.deadline + " " + this.description + " "+ Integer.toString(this.id) ;        
    }
    public String getRepeatStringForUI (){
        return  this.repeatPeriod + " " + this.description + " "+ Integer.toString(this.id) ;   
    }

    private void setAttributes(Type type, ArrayList<String> attributeList) {
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
        } else if (type.equals(Task.Type.DEADLINE)) {
            this.deadline = list[0];
            this.description = list[1];
            this.id = Integer.parseInt(list[2]);
            this.isComplete = false;
        } else if (type.equals(Task.Type.FLOATING)) {
            this.description = list[0];
            this.id = Integer.parseInt(list[1]);
            this.isComplete = false;
        } else {
            this.repeatPeriod = list[0];
            this.description = list[1];
            this.id = Integer.parseInt(list[2]);
            this.isComplete = false;
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

    public String getRepeatPeriod() {
        return repeatPeriod;
    }

    public String getDateAdded() {
        return dateAdded;
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

    public void setRepeatPeriod(String repeatPeriod) {
        this.repeatPeriod = repeatPeriod;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
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

    public String getTaskRepeatDuration() {
        return taskRepeatDuration;
    }

    public void setTaskRepeatDuration(String taskRepeatDuration) {
        this.taskRepeatDuration = taskRepeatDuration;
    }

    public String getTaskRepeatUntil() {
        return taskRepeatUntil;
    }

    public void setTaskRepeatUntil(String taskRepeatUntil) {
        this.taskRepeatUntil = taskRepeatUntil;
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

}

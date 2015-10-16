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
    private String id;
    private String description;
    private String deadline;
    private String eventDate;
    private String eventTime;
    private String repeatPeriod;
    private String dateAdded;
    private String timeAdded;

    public Task(Type type, ArrayList<String> attributeList) {
	setType(type);
	setAttributes(type, attributeList);
    }

    private void setAttributes(Type type, ArrayList<String> attributeList) {

    }

    // ================================================================
    // Getter methods to get attributes of Task object
    // ================================================================

    public Type getType() {
	return type;
    }

    public String getID() {
	return id;
    }

    public String getDescription() {
	return description;
    }
    
    public String getDeadline() {
	return deadline;
    }

    public String getEventDate() {
	return eventDate;
    }
    
    public String getEventTime() {
	return eventTime;
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

    // ================================================================
    // Setter methods to initialize Task object
    // ================================================================

    public void setType(Type type) {
	this.type = type;
    }

    public void setID(String id) {
	this.id = id;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setDeadline(String deadline) {
	this.deadline = deadline;
    }

    public void setEventDate(String eventDate) {
	this.eventDate = eventDate;
    }
    
    public void setEventTime(String eventTime) {
	this.eventTime = eventTime;
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

}

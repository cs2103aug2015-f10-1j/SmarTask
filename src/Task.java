
/**
 * Task is an object class that contains all attibutes of a task
 * 
 * @author Bobby Lin
 *
 */

public class Task {

    public enum Type {
        EVENT, DEADLINE, FLOATING, REPEAT
    }

    private Type type;
    private String id;
    private String description;
    private String date;
    private String time;
    private String startTime;
    private String endTime;
    private String period;
    private String dateAdded;
    private String timeAdded;

    public Task(Type type) {
        setType(type);
        setID("");
        setDescription("");
        setDate("");
        setStartTime("");
        setEndTime("");
        setPeriod("");
        setDateAdded("");
        setTimeAdded("");
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
    
    public String getDate() {
        return date;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public String getTime() {
        return time;
    }

    public String getPeriod() {
        return period;
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

    public void setDate(String date) {
        this.date = date;
    }
    
    public void setTime(String time) {
        this.time = time;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }

}

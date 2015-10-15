

public class Task {

    public enum Type {
        EVENT, DEADLINE, FLOATING, REPEAT
    }

    private Type type;
    private String id;
    private String description;
    private String date;
    private String startTime;
    private String endTime;

    public Task(Type type) {
        setType(type);
        setID("");
        setDescription("");
        setDate("");
        setStartTime("");
        setEndTime("");
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

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}

import java.util.ArrayList;
import java.util.Arrays;

public class Task {
	
	private String taskTitle;
    private String taskLabel;
    private String taskDetail;
    private String taskTime;
    private String recurringPeriod;
    private int taskNumber;
    private ArrayList <String> task;
    
    
    //
    private final String SPLIT = "#";
    
    private Task(String str){
    	task = new ArrayList <String>();
    	task = splitString(str);
    	taskTime = task.get(0);
    	taskTitle = task.get(1);
    	taskLabel = task.get(2);
    }
    
    private static ArrayList<String> splitString(String arguments) {
        String[] strArray = arguments.trim().split("SPLIT");
        return new ArrayList<String>(Arrays.asList(strArray));
    }
    
    
    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskLabel() {
        return taskLabel;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public String getTaskTime() {
        return taskTime;
    }
    
    public int getTaskNumber() {
        return taskNumber;
    }
    
    public String getRecurringPeriod() {
        return recurringPeriod;
    }
    
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskLabel(String taskLabel) {
        this.taskLabel = taskLabel;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public void setRecurringPeriod(String recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }


}

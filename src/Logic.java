import java.beans.EventSetDescriptor;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.Calendar.*;

import javax.swing.RowFilter.ComparisonType;

import org.junit.internal.Throwables;

/**
 * 
 * 
 * @author Liang Mengfei
 *
 */


public class Logic {
	private static Storage storage = new Storage();
    private static  ArrayList<Task> taskStored = storage.retrieveFile();
    private static ArrayList<String> msgLogger = new ArrayList<String>();
    private static ArrayList<String> event;
    private static ArrayList<String> deadline;
    private static  ArrayList<String> floating;
    private static  ArrayList<String> repeatedTask = new ArrayList <String>();
    private static ArrayList <Integer> searchList;
    
    // error message
    private static final String MESSAGE_DEADLINE_EMPTY = "There is no deadline task to delete!!";
    private static final String MESSAGE_FLOATING_EMPTY = "There is no floating task to delete!!";
    private static final String MESSAGE_EVENT_EMPTY = "There is no event task to delete!!";
    private static final String MESSAGE_NOTHING_TO_DELETE = "Cannot find the item to delete!!";
    private static final String MESSAGE_DEADLINE_CANT_COMPLETE = "There is no deadline task to complete!!";
    private static final String MESSAGE_FLOATING_CANT_COMPLETE = "There is no floating task to complete!!";
    private static final String MESSAGE_EVENT_CANT_COMPLETE = "There is no event task to complete!!";
    private static final String MESSAGE_NOTHING_TO_COMPLETE = "Cannot find item to complete!!";
    private static final String MESSAGE_INVALID_INDEX = "Index choosen is not valid";
    private static final String MESSAGE_INVALID_COMMAND = "Invalid Command. Please enter the correct command.";
    
    // task type
    private static final String FLOATING_TASK = "floating";
    private static final String EVENT_TASK = "event";
    private static final String DEADLINE_TASK = "deadline";
    private static final String RECURRING_TASK = "repeat";
    private static final String DAY_REC = "day";
    private static final String WEEK_REC = "week";
    private static final String MONTH_REC = "month";
    private static final String YEAR_REC = "year";
    private static final String STOP_REC = "stop";
    
    // success message
    private static final String ADD_STOP_SUC = "Add stop command to recurring task successfully!";
    
    
    
    private  int taskCode ;
    private  static CommandHistory history = new CommandHistory(new ArrayList<Task>(taskStored));
    private static  Date currentDate;
    private  static String currentTime;
    private static  Date currentDay;

    public static void executeCommand (String userInput) throws Exception {
    	Logic logic = new Logic ();
        if(userInput.trim().isEmpty()) {
            msgLogger.add("Please enter a command.");
        }
        else {
            try {
                msgLogger.add("command : " + userInput);
                Command command = CommandParser.parse(userInput);

                switch (command.getCommandType()){
                    case ADD : 
                        logic.addTask(command);
                        break;
                    case REPEAT : 
                        logic.addRepeatTask(command);
                        break;
                    case DELETE :
                        logic.deleteTask(command);
                        break;
                    case UPDATE :
                        logic.updateTask(command);
                        break;
                    case SEARCH:
                        logic.searchTask(command);
                        break;
                    case COMPLETE :
                        logic.completeTask(command);
                        break;
                    case UNDO :
                        logic.undoCommand();
                        break;
                    case REDO :
                        logic.redoCommand();
                        break;
                    case STOP_REPEAT :
                    	logic.stopRec(command);
                    case EXIT :
                  //      System.exit(-1);
                        break;
                    case SETFILEPATH :
                    	storage.setSavePath(command.getFilePath());
                    	break;
                  //  case HELP :
                  //  	logic.help();
                  //  	break;
                    default :
                        msgLogger.add(MESSAGE_INVALID_COMMAND);
                }
            } catch (Exception e) {
                msgLogger.add(e.getMessage());
            }
        }
    }

	private static ArrayList<String> initList(String type, ArrayList<Task> taskStored) throws ParseException {
        ArrayList<String> list = new ArrayList <String>();
        Task task = null;
        initDate();
        for (int i = 0; i<taskStored.size(); i++){
            if(taskStored.get(i).getType().equals(Task.getTypeFromString(type)) && taskStored.get(i).getIsComplete()==false){
            	task = taskStored.get(i);
            	if (!isOverdue(task)){
            		if (type.equals(FLOATING_TASK)){
                    	list.add(taskStored.get(i).getFloatingString());
                    }else if (type.equals(EVENT_TASK)){
                    	list.add(taskStored.get(i).getEventString());
                    }else if (type.equals(DEADLINE_TASK)){
                    	list.add(taskStored.get(i).getDeadlineString());
                    }else if (type.equals(RECURRING_TASK)){
                    	if(!isStop(task)){
                    		if (compareDate(task)){
                    		list.add(taskStored.get(i).getRepeatString());
                    	}	
                    	}
                    	
                    }
            	}
            }
        }
        return list;
    }
	
	// check whether the recurring task need to stop today
	private static boolean isStop(Task task){
		boolean boo = false;
		ArrayList <Date> stopDate = new ArrayList<Date>();
		stopDate = task.getStopRepeat();
		
		Calendar calCur = Calendar.getInstance();
		calCur.setTime(currentDate);
		int monthCur = calCur.get(Calendar.MONTH);
		int dayCur = calCur.get(Calendar.DAY_OF_MONTH);
		
		for (int i=0; i<stopDate.size(); i++){
			Calendar calTask = Calendar.getInstance();
			calTask.setTime(stopDate.get(0));
			int monthForTask = calTask.get(Calendar.MONTH);
			int dayForTask = calTask.get(Calendar.DAY_OF_MONTH);
			
			if (monthForTask == monthCur && dayForTask == dayCur){
				boo = true;
				break;
				
			}
		}
		return boo;
	}
	
	
	// check whther the task is overdue
	private static boolean isOverdue(Task task) throws ParseException{
		boolean isOver = false;
		initDate();
		if (task.getType().equals(Task.Type.FLOATING)){
			
		} else if (task.getType().equals(Task.Type.EVENT)){
			Date date = convertStringToDate(task.getEventEnd());
			if (date.before(currentDate)){
				isOver = true;
			}
		} else if (task.getType().equals(Task.Type.DEADLINE)){
			Date date = convertStringToDate(task.getDeadline());
			if (date.before(currentDate)){
				isOver = true;
			}
			
		} else if (task.getType().equals(Task.Type.REPEAT)){
			
		}
		return isOver;
	}
	
	private static Date convertStringToDate(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        Date date = sdf.parse(dateStr);
        return date;
	}
	
	private static boolean compareDate(Task task){
		boolean isToday = false;
		if (task.getTaskRepeatType().equals(DAY_REC)){
			if (task.getTaskRepeatUntil().after(currentDate)){
			   if (getDifferenceDays(task.getDateAdded(), currentDate) % Integer.parseInt(task.getTaskRepeatInterval_Day()) == 0){
				   isToday = true;
			   }
			}
		} else if (task.getTaskRepeatType().equals(WEEK_REC)){
			if(task.getTaskRepeatUntil().after(currentDate)){
				if (getWeeksBetween(task.getDateAdded(),currentDate) % Integer.parseInt(task.getTaskRepeatInterval_Week()) == 0){
				    if (isSameDay(task)){
				    	isToday = true ;
				    }
				}
			}	
		} else if (task.getTaskRepeatType().equals(MONTH_REC)){
			if(task.getTaskRepeatUntil().after(currentDate)){
				if (isNextMonth(task)){
					isToday = true;
				}
				
			}
			
		} else if (task.getTaskRepeatType().equals(YEAR_REC)){
			if (task.getTaskRepeatUntil().after(currentDate)){
				if (getYearBetweenDates(task.getDateAdded(), currentDate)>0){
					   isToday = true;
				   }
				}
		}
		return isToday;
	}
	
	//check the month recurring task
	
	private static boolean isNextMonth(Task task){
		boolean boo = false;
		Calendar calTask = Calendar.getInstance();
		calTask.setTime(task.getDateAdded());
		int monthForTask = calTask.get(Calendar.MONTH);
		int dayForTask = calTask.get(Calendar.DAY_OF_MONTH);
		Calendar calCur = Calendar.getInstance();
		calCur.setTime(currentDate);
		int monthCur = calCur.get(Calendar.MONTH);
		int dayCur = calCur.get(Calendar.DAY_OF_MONTH);
		
		if ((Math.abs(monthForTask-monthCur) % Integer.parseInt(task.getTaskRepeatInterval_Month())) == 0){
			if (dayForTask == dayCur){
				boo = true;
			}
		}
		return boo;
	}
		
	// check the date
	private static boolean isSameDay(Task task){
		boolean boo = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (task.getIsDaySelected()[dayOfWeek]==true){
			boo = true;
		}
		return boo;
	}
	
	// get the week difference between two dates
	private static int getWeeksBetween(Date a, Date b){
		 if (b.before(a)) {
		        return -getWeeksBetween(b, a);
		    }
		    a = resetTime(a);
		    b = resetTime(b);

		    Calendar cal = new GregorianCalendar();
		    cal.setTime(a);
		    int weeks = 0;
		    while (cal.getTime().before(b)) {
		        // add another week
		        cal.add(Calendar.WEEK_OF_YEAR, 1);
		        weeks++;
		    }
		    return weeks;
		}

	private static Date resetTime (Date d) {
		   Calendar cal = new GregorianCalendar();
		   cal.setTime(d);
		   cal.set(Calendar.HOUR_OF_DAY, 0);
		   cal.set(Calendar.MINUTE, 0);
		   cal.set(Calendar.SECOND, 0);
		   cal.set(Calendar.MILLISECOND, 0);
		   return cal.getTime();
	}
	
	
	// get the year difference between two dates
	private static int getYearBetweenDates(Date d1, Date d2){
		Calendar a = getCalendar(d1);
		Calendar b = getCalendar(d2);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH)>b.get(Calendar.MONTH) ||(a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))){
			diff--;
		}
		return diff;
	}
	// convert the date to calendar format
	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.US);
	    cal.setTime(date);
	    return cal;
	}
	// get the num of days different between current date and the recurring task starting date
	private static int getDifferenceDays(Date d1, Date d2){
		int daysDiff = 0;
		long diff = d1.getTime() - d2.getTime();
		long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	    daysDiff = (int)diffDays;
	    return daysDiff;
	}
   // get the unique task code
    private int getID(){
    	DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    	Calendar cal = Calendar.getInstance();
    	String s = dateFormat.format(cal.getTime());
    	DateFormat dateFormat2 = new SimpleDateFormat("HHmmss");
        Date date = new Date();
    	String sID = dateFormat2.format(date);
    	int sNum = Integer.parseInt(s);
    	int sIDNum = Integer.parseInt(sID);
    	taskCode = sNum + sIDNum;
    	return taskCode;
    }
    // get the current date, time
    private static void initDate() throws ParseException{
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        currentDate = dateFormat.parse(dateFormat.format((cal.getTime())));
        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    	currentTime = dateFormat2.format(cal.getTime());  
    	DateFormat dateFormat3 = new SimpleDateFormat("E");
    	currentDay = dateFormat3.parse(dateFormat3.format(cal.getTime()));;  
    }

    // ================================================================
    // "Add" command methods
    // ================================================================


	private void addTask(Command command) throws Exception{
        try{
        	
            String taskType = command.getTaskType();
         //   ArrayList <String> detailStored = new ArrayList <String> ();
            ArrayList <String> detailTask = new ArrayList <String> ();
            Task.Type type;
            taskCode = getID();
            Task task;

            if(taskType.equals(FLOATING_TASK)) {
              //  detailStored.add(command.getTaskDescription() + "#" + taskCode);
                detailTask.add(command.getTaskDescription()+ "#" + taskCode);
                type = Task.Type.FLOATING;
                task = new Task (type, detailTask);
                if (!isCollision(task)) floating.add(detailTask.get(0));
            } 
            else if(taskType.equals(EVENT_TASK)) {
             //   detailStored.add(taskType + "#" + command.getTaskEventStart() + "#" + command.getTaskEventEnd() + "#" + command.getTaskDescription() +"#"+ taskCode);
                detailTask.add(command.getTaskEventStart() + "#" + command.getTaskEventEnd() + "#" + command.getTaskDescription()+ "#" + taskCode);
                type = Task.Type.EVENT;
                task = new Task (type, detailTask);
                if (!isCollision(task)) event.add(detailTask.get(0));
            }
            else if(taskType.equals(DEADLINE_TASK)) {
             //   detailStored.add(taskType + "#" + command.getTaskDeadline() + "#" + command.getTaskDescription()+"#"+ taskCode);
                detailTask.add(command.getTaskDeadline() + "#" + command.getTaskDescription()+ "#" + taskCode);
                type = Task.Type.DEADLINE;
                task = new Task (type, detailTask);
                if (!isCollision(task)) deadline.add(detailTask.get(0));
            }
            else {
                throw new Exception("Fail to add an invalid task");
            }
            Boolean isColl = isCollision(task);
            if (!isColl) {
            	taskStored.add(task);
                   storage.saveToFile(taskStored);
                   msgLogger.add("add " + command.getTaskDescription() + " successful!");  
                   history.addChangeToHistory(new ArrayList<Task>(taskStored));   	
            }else {
            	
                msgLogger.add("Collision Task!");
            	return;
            }
            

        }catch (FileNotFoundException e){
            msgLogger.add(e.toString());
        }

    }
	
	private static Boolean isCollision (Task task){
		Boolean boo = false;
		
		for (int i=0; i<taskStored.size(); i++){
			if (taskStored.get(i).getDescription().equals(task.getDescription())){
				if (taskStored.get(i).getType().equals(task.getType())){
					if (taskStored.get(i).getType().equals(Task.getTypeFromString(DEADLINE_TASK))){
						if (taskStored.get(i).getDeadline().equals(task.getDeadline())){
							boo = true;
							break;
						}
					} else if (taskStored.get(i).getType().equals(Task.getTypeFromString(FLOATING_TASK))){
						boo = true;
						break;
					} else if(taskStored.get(i).getType().equals(Task.getTypeFromString(EVENT_TASK))) {
						if (taskStored.get(i).getEventStart().equals(task.getEventStart()) && taskStored.get(i).getEventEnd().equals(task.getEventEnd())){
							boo = true;
							break;
						}
					} else {
						// handle recurring task
						String recurringType = task.getTaskRepeatType();
						if (taskStored.get(i).getTaskRepeatType().equals(recurringType)){
							if (recurringType.equals(DAY_REC)){
								if (taskStored.get(i).getTaskRepeatInterval_Day().equals(task.getTaskRepeatInterval_Day())){
									if (taskStored.get(i).getTaskRepeatUntil().compareTo(task.getTaskRepeatUntil())==0){
										if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded())==0){
											boo = true;
											break;
										}
									}
								}
												
							} else if (recurringType.equals(WEEK_REC)){
								if (taskStored.get(i).getTaskRepeatInterval_Week().equals(task.getTaskRepeatInterval_Week())){
									if (taskStored.get(i).getTaskRepeatUntil().compareTo(task.getTaskRepeatUntil())==0){
										if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded())==0){
											boo = true;
											break;
										}
									}
								}
								
							} else if (recurringType.equals(MONTH_REC)){
								if (taskStored.get(i).getTaskRepeatInterval_Month().equals(task.getTaskRepeatInterval_Month())){
									if (taskStored.get(i).getTaskRepeatUntil().compareTo(task.getTaskRepeatUntil())==0){
										if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded())==0){
											boo = true;
											break;
										}
									}
								}
								
							} else if (recurringType.equals(YEAR_REC)){
								if (taskStored.get(i).getTaskRepeatInterval_Year().equals(task.getTaskRepeatInterval_Year())){
									if (taskStored.get(i).getTaskRepeatUntil().compareTo(task.getTaskRepeatUntil())==0){
										if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded())==0){
											boo = true;
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return boo;
	}
/*
    private static void sortForAdd(){
        Collections.sort(taskStored, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
*/
    // ================================================================
    // "Repeat task" command methods
    // ================================================================

	private void addRepeatTask(Command com) throws Exception{  
    	ArrayList <String> detailStored = new ArrayList <String> ();
    //	ArrayList <String> detailTask = new ArrayList <String> ();
    	taskCode = getID();
    	String taskType = com.getRepeatType();
    	Task.Type type = Task.Type.REPEAT;
    	Task task = null;
    	if (taskType.equals(DAY_REC)){
    	//	msgLogger.add( taskType+"#" +com.getDateAdded().toString() +"#" + com.getRepeatStartTime()+"#" +com.getRepeatEndTime()+"#"+com.getDayInterval()+"#" + com.getRepeatUntil().toString()+"#"+com.getTaskDescription() + "#" + taskCode+"#" + com.getStopRepeatInString());
    		detailStored.add(taskType+"#"+com.getDateAdded().toString() +"#" + com.getRepeatStartTime()+"#" +com.getRepeatEndTime()
    		                 +"#"+com.getDayInterval()+"#" + com.getRepeatUntil().toString()+"#"+com.getTaskDescription()+ "#" 
    				         + taskCode+"#" + com.getStopRepeatInString());
    	//	msgLogger.add("here");
    		task = new Task (type, detailStored);
       //     msgLogger.add(task.getDescription());
    		if (!isCollision(task)){
    			repeatedTask.add(detailStored.get(0));
    			taskStored.add(task);
    			msgLogger.add("here1");
    			storage.saveToFile(taskStored);
    	        msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
    		} else {
    			msgLogger.add("Collision Task!");
    		}
    	} 
    	else if (taskType.equals(WEEK_REC)){
    	//	msgLogger.add(taskType +"#"+com.getDateAdded()+"#"+ com.getRepeatStartTime() +"#"+ 
    	//                   com.getRepeatEndTime()+"#"+com.getWeekInterval()+"#"+com.getIsDaySelected()
    	//                   + "#"+com.getRepeatUntil()+"#" +com.getTaskDescription()+"#" + taskCode);
    		detailStored.add(taskType +"#"+com.getDateAdded()+"#"+ com.getRepeatStartTime() +"#"+ 
	                   com.getRepeatEndTime()+"#"+com.getWeekInterval()+"#"+com.getGetDaySelectedString()
	                   + "#"+com.getRepeatUntil()+"#" +com.getTaskDescription()+"#" + taskCode+"#" + com.getStopRepeatInString());
    		task = new Task (type, detailStored);
    		if (!isCollision(task)){
    			repeatedTask.add(detailStored.get(0));
    			taskStored.add(task);
    			storage.saveToFile(taskStored);
    	        msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
    		} else {
    			msgLogger.add("Collision Task!");
    		}
    			
    	}
    	else if (taskType.equals(MONTH_REC)){
    		detailStored.add(taskType +"#" +com.getDateAdded()+"#" + com.getRepeatStartTime() +"#"+com.getRepeatEndTime()+"#"+
    	                   com.getMonthInterval()+"#"+com.getRepeatUntil()+"#"+com.getTaskDescription() + "#" + taskCode+"#" + com.getStopRepeatInString());
    	//	detailTask.add(taskType +"#" +com.getDateAdded()+"#" + com.getRepeatStartTime() +"#"+com.getRepeatEndTime()+"#"+
	     //              com.getMonthInterval()+"#"+com.getRepeatUntil()+"#"+com.getTaskDescription() + "#" + taskCode);
    		task = new Task (type, detailStored);
    		if (!isCollision(task)){
    			repeatedTask.add(detailStored.get(0));
    			taskStored.add(task);
    			storage.saveToFile(taskStored);
    	        msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
    		} else {
    			msgLogger.add("Collision Task!");
    		}
    	}
    	
    	 else if (taskType.equals(YEAR_REC)){
    	//	 detailTask.add( taskType+"#" +com.getDateAdded() +"#" + com.getRepeatStartTime()+"#" +com.getRepeatEndTime()+"#"+com.getDayInterval()+"#" + com.getRepeatUntil()+"#"+com.getTaskDescription() + "#" + taskCode);
     		detailStored.add(taskType+"#"+com.getDateAdded() +"#" + com.getRepeatStartTime()+"#" +com.getRepeatEndTime()+"#"+
    	                     com.getDayInterval()+"#" + com.getRepeatUntil()+"#"+com.getTaskDescription()+ "#" + taskCode+"#" + com.getStopRepeatInString());
     		task = new Task (type, detailStored);
     		if (!isCollision(task)) {
     			repeatedTask.add(detailStored.get(0));
     			taskStored.add(task);
     			storage.saveToFile(taskStored);
    	        msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
     		} else {
     			msgLogger.add("Collision Task!");
     		}
    	}
    	
        
        
    }

    /* 
    public static void sortForRec(){
    	Collections.sort(inputList, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("EEE");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
     */

    public void printArrayList(){
        for (int i=0; i<taskStored.size(); i++){
            System.out.println(taskStored.get(i));
        }
    }

    // ================================================================
    // "search" command methods
    // ================================================================

    private void searchTask(Command command) throws FileNotFoundException{
        ArrayList<String> keyWordList = command.getSearchKeyword();
        String keyword = "";
        taskStored.clear();
        Task.Type taskType;
        int index = 1;
        searchList = new ArrayList<Integer>();
        taskStored = storage.retrieveFile(); // get the latest task from the storage
        for(int i=0; i< keyWordList.size(); i++) {
            keyword = keyWordList.get(i).toLowerCase();
            for(int j=0; j<taskStored.size(); j++) {
                String[] arr = taskStored.get(j).getDescription().split(" ");
                for(int k=0; k<arr.length; k++) {
                    if (arr[k].toLowerCase().contains(keyword)){
                    	taskCode = taskStored.get(j).getID();
                    	taskType = taskStored.get(j).getType();
                    	if (taskType.equals(Task.Type.DEADLINE)){
                    		msgLogger.add((index++)+" " + taskStored.get(j).getDescription() + " deadline is : " + taskStored.get(j).getDeadline());
                    	} else if (taskType.equals(Task.Type.EVENT)){
                    		msgLogger.add((index++)+ " "+ taskStored.get(j).getDescription() + " start time is : " + taskStored.get(j).getEventStart() + " " + taskStored.get(j).getEventEnd());
                    	} else if (taskType.equals(Task.Type.FLOATING)){
                    		msgLogger.add((index++)+ " " + taskStored.get(j).getDescription());
                    	} else if (taskType.equals(Task.Type.REPEAT)){
                    		msgLogger.add((index++)+ " " + taskStored.get(j).getDescription() + " repeating peroid end in : " + taskStored.get(i).getTaskRepeatEndTime());
                    	}
                        
                        searchList.add(taskCode);
                        System.out.println(taskCode);
                    	//  msgLogger.add(taskStored.get(j).getDescription());
                    }
                }
            }
        }
    }

    // ================================================================
    // "Delete" command methods
    // ================================================================

    private void deleteTask(Command command){
        String taskType = command.getTaskType();
        try{
        	int indexToRemove = command.getTaskID()-1;
            String  removedItem = "";
            String currentLine = "";
            if (taskType != null){
            if(taskType.equals(DEADLINE_TASK)) {
            	 currentLine = deadline.get(indexToRemove);
            	 removedItem = deadline.remove(indexToRemove);
            	 String str[] = currentLine.split("#");
            	 taskCode = Integer.parseInt(str[str.length-1]);
            }
            else if(taskType.equals(FLOATING_TASK)) {
            	currentLine = floating.get(indexToRemove);
            	removedItem = floating.remove(indexToRemove);
            	String str[] = currentLine.split("#");
            	taskCode = Integer.parseInt(str[str.length-1]);
            }
            else if(taskType.equals(EVENT_TASK)) {
            	currentLine = event.get(indexToRemove);
            	removedItem = event.remove(indexToRemove);
            	String str[] = currentLine.split("#");
            	taskCode = Integer.parseInt(str[str.length-1]);
            } else if (taskType.equals(RECURRING_TASK)){
            	currentLine = repeatedTask.get(indexToRemove);
            	removedItem = repeatedTask.get(indexToRemove);
            	String str[] = currentLine.split("#");
            	taskCode = Integer.parseInt(str[str.length-1]);
            } 
            }
            
            else {
            	
            	msgLogger.add(Integer.toString(searchList.get(indexToRemove)));
            	taskCode = searchList.get(indexToRemove);
            	
            }
          //  String str[] = currentLine.split("#");
          //  taskCode = Integer.parseInt(str[str.length-1]);
          //  msgLogger.add(Integer.toString(taskCode));
            for (int i=0; i<taskStored.size(); i++){
            	if (taskStored.get(i).getID() == taskCode){
            		taskStored.remove(i);
            		break;
            	}
            }
            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("deleted " + taskType + " index " + command.getTaskID() + " successfully!");
        }catch(Exception e){
            if(taskType.equals("deadline") && deadline.size() == 0) {
                msgLogger.add(MESSAGE_DEADLINE_EMPTY);
            }
            else if(taskType.equals("floating") && floating.size() == 0) {
                msgLogger.add(MESSAGE_FLOATING_EMPTY);
            }
            else if(taskType.equals("event") && event.size() == 0) {
                msgLogger.add(MESSAGE_EVENT_EMPTY);
            }
            else { 
                msgLogger.add(MESSAGE_NOTHING_TO_DELETE);
            }
        }

    }

    // ================================================================
    // "Complete" command method
    // ================================================================

    private void completeTask(Command command) {
        String taskType = command.getTaskType();
        try{
            int indexToComplete = command.getTaskID()-1;
            String completedItem = "";
            String currentLine = " ";
            

            if(taskType.equals("deadline")) {
            	currentLine = deadline.get(indexToComplete);
                completedItem = deadline.remove(indexToComplete);
            }
            else if(taskType.equals("floating")) {
            	currentLine = floating.get(indexToComplete);
                completedItem = floating.remove(indexToComplete);
            }
            else if(taskType.equals("event")) {
                currentLine = event.get(indexToComplete);
                completedItem = event.remove(indexToComplete);
            }

         //   taskStored.remove(new String(completedItem));
            String str[] = currentLine.split("#");
            taskCode = Integer.parseInt(str[str.length-1]);
            
            for (int i=0; i<taskStored.size(); i++){
            	if (taskStored.get(i).getID() == taskCode){
            		taskStored.get(i).setIsComplete(true);
            		break;
            	}
            }
            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("completed " + taskType + " index " + command.getTaskID());

        }catch(Exception e){
            if(taskType.equals("deadline") && deadline.size() == 0) {
                msgLogger.add(MESSAGE_DEADLINE_CANT_COMPLETE);
            }
            else if(taskType.equals("floating") && floating.size() == 0) {
                msgLogger.add(MESSAGE_FLOATING_CANT_COMPLETE);
            }
            else if(taskType.equals("event") && event.size() == 0) {
                msgLogger.add(MESSAGE_EVENT_CANT_COMPLETE);
            }
            else { 
                msgLogger.add(MESSAGE_NOTHING_TO_COMPLETE);
            }
        }

    }

    // ================================================================
    // "Update" command methods
    // ================================================================

    private void updateTask(Command command) throws FileNotFoundException{
        String taskType = command.getTaskType();
        String updatedItem = "";
        String existingItem = "";
        String updatedTask = "";
        Task.Type type;
        ArrayList <String> updatedLine = new ArrayList <String>();

        try {
            int indexToUpdate = command.getTaskID()-1;
           
            if(taskType.equals("deadline")) {
                existingItem = deadline.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                taskCode = Integer.parseInt(strArr[strArr.length-1]);

                updatedItem += strArr[0]+"#";
                if(command.getTaskDeadline() != null) {
                    updatedItem += command.getTaskDeadline()+"#";
                    updatedTask += command.getTaskDeadline()+"#";
                }
                else{
                    updatedItem += strArr[1]+"#";
                    updatedTask += strArr[1]+"#";
                }
  
                if(!command.getTaskDescription().isEmpty()) {
                    updatedItem += command.getTaskDescription();
                    updatedTask +=command.getTaskDescription();
                }
                else{
                    updatedItem += strArr[2];
                    updatedTask += strArr[2];                 
                
                }
            
                updatedItem = updatedItem +"#"+Integer.toString(taskCode);
                updatedTask = updatedTask +"#"+Integer.toString(taskCode);
              
                type = Task.Type.DEADLINE;          
                deadline.set(indexToUpdate, updatedItem);
           
            }
            else if(taskType.equals("floating")) {
                existingItem = floating.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                taskCode = Integer.parseInt(strArr[strArr.length -1]);
                updatedItem += strArr[0]+"#";                
                if(command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                    updatedTask +=command.getTaskDescription();
                }
                
                else{
                    updatedItem += strArr[1];
                    updatedTask += strArr[1];
                }
                updatedItem += "#"+Integer.toString(taskCode);
                updatedTask += "#"+Integer.toString(taskCode);
                type = Task.Type.FLOATING;
                floating.set(indexToUpdate, updatedItem);
            }
            else if(taskType.equals("event")) {
                existingItem = event.get(indexToUpdate);
                String[] strArr = existingItem.split("#");
                taskCode = Integer.parseInt(strArr[strArr.length -1]);
                updatedItem += strArr[0]+"#";                
                if(command.getTaskEventDate() != null) {
                    updatedItem += command.getTaskEventDate()+"#";
                    updatedTask += command.getTaskEventDate() + "#";
                }
                else{
                    updatedItem += strArr[1]+"#";
                    updatedTask += strArr[1]+"#";
                }
                if(command.getTaskEventTime() != null) {
                    updatedItem += command.getTaskEventTime()+"#";
                    updatedTask += command.getTaskEventTime()+"#";
                }
                else{
                    updatedItem += strArr[2]+"#";
                    updatedTask += strArr[2]+"#";
                }
                if(command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                    updatedTask += command.getTaskDescription();
                }
                else{
                    updatedItem += strArr[3];
                    updatedTask += strArr[3];
                }
                updatedItem += "#"+Integer.toString(taskCode);
                updatedTask += "#"+Integer.toString(taskCode);
                event.set(indexToUpdate, updatedItem);
                type = Task.Type.EVENT;
            }
            else {
                msgLogger.add(MESSAGE_INVALID_INDEX);
                throw new Exception(MESSAGE_INVALID_INDEX);
            }
            for (int i=0; i<taskStored.size(); i++){
            	if (taskStored.get(i).getID() == taskCode){
         
            		taskStored.remove(i);
            		updatedLine = new ArrayList<String>();
            		updatedLine.add(updatedTask);
            		Task task = new Task (type,updatedLine);
                    taskStored.add(task);   
            		storage.saveToFile(taskStored);
            		break;
            	}
            }
            
            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("task updated!");
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }

    }
 // ================================================================
    // "help" command methods  --> need to complete
    // ================================================================
    
    private void help(){
    	String helpMsg;
    	helpMsg = "add task for floating task : <add your task > \n";
    	helpMsg +="add task for event task with starting date and end date : <> \n";
    	helpMsg +="add task for task that have a deadline : <> \n";
    	helpMsg +="delete task for floating task : <F> \n";
    	
    }
    
 // ================================================================
    // stop recurring task command method
    // ================================================================
    private void stopRec(Command command) throws ParseException{
    	
    	int taskID = command.getTaskID();
    	for (int i=0; i<taskStored.size(); i++){
    		if (taskStored.get(i).getID() == taskID){
    			String str;
    			str = taskStored.get(i).getStopRepeatInString() +"@"+ command.getStopRepeatInString();
    			taskStored.get(i).setStopRepeat(command.getStopRepeatInString());
    			break;
    		}
    	}
    	storage.saveToFile(taskStored);
    	history.addChangeToHistory(new ArrayList<Task>(taskStored));
        msgLogger.add(ADD_STOP_SUC);
    }
   
    

    // ================================================================
    // redo command method
    // ================================================================
    private void redoCommand() throws FileNotFoundException {
        String message = "";
        try {
            message = "redo successfully";
            taskStored = new ArrayList<Task>(history.redo());
            storage.saveToFile(taskStored);
            event = initList("event", taskStored);
            deadline = initList("deadline", taskStored);
            floating = initList("floating", taskStored);
            msgLogger.add(message);  
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }
    }

    // ================================================================
    // undo command method
    // ================================================================
    private void undoCommand() throws FileNotFoundException {
        String message = "";
        try {
            message = "undo successfully";
            taskStored = new ArrayList<Task>(history.undo());
            storage.saveToFile(taskStored);
            event = initList("event", taskStored);
            deadline = initList("deadline", taskStored);
            floating = initList("floating", taskStored);
            msgLogger.add(message);
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }   
    }

    // ================================================================
    // "View" command methods
    // ================================================================
/*
    private void viewTask(Command com){
        for (int i = 0; i<taskStored.size(); i++){
            if (taskStored.get(i).contains(com.getTaskEventDate())){
                currentList.add(i);
                String[] str = taskStored.get(i).trim().split("#");
                event.add(str[1] + " " + str[0] ) ;
            }   
        }
    }
*/
    // ================================================================
    // "view today's task" command methods
    // ================================================================
    /*
    private static String viewUpcommingTask(){
        String message = "Top 10 Upcomming Tasks: \n";
        int index = 1;
        taskList = storage.retrieveTexts();
        for (int i = 0; i<10; i++){
                String[] str = taskList.get(i).trim().split("#");
                message += (index++) + ". " + str[1] + " " + str[0] + "\n" ;
                upcommingTask.add(i);
                currentList.add(i);
        }
        if(currentList.isEmpty() && upcommingTask.isEmpty()){
                message += "There is no task need to be finished.";
        }
        return message;
    }
     */ 
/*
    private static void viewTodayTask(){
        int index = 1;
        taskStored = Storage.retrieveTexts();

        for (int i = 0; i<taskStored.size(); i++){
            if (taskStored.get(i).contains(currentDate)){
                todayTask.add(i);
                currentList.add(i);
                String[] str = taskStored.get(i).trim().split("#");
                event.add( (index++) + ". " + str[1] + " " + str[0] ) ;
            }   
        }
        if(currentList.isEmpty() && todayTask.isEmpty()){
            event.add("There is no task need to be finished.");
        }
    }
*/
    // ================================================================
    // Getter methods to retrieve lists for UI
    // ================================================================
    public static String getMessageLog(){
        String messageToPrint = "";
        for(int i=0; i<msgLogger.size(); i++) {
            messageToPrint += msgLogger.get(i) + "\n";
        }
        return messageToPrint.trim();
    }

    public static String getEvents() throws ParseException{
    	event= initList("event", taskStored);
        String messageToPrint = "";
        if(event.size() == 0) {
            return messageToPrint = "No events";
        }
        for(int i=0; i<event.size(); i++) {
            messageToPrint += "E" + (i+1) + ". "+ event.get(i).replace("#"," ")+ "\n";
        }
        return messageToPrint.trim();
    }

    public static String getDeadline() throws ParseException{
    	deadline= initList("deadline", taskStored);
    	String messageToPrint = "";
        if(deadline.size() == 0) {
            return messageToPrint = "No tasks";
        }
        for(int i=0; i<deadline.size(); i++) {
            messageToPrint += "D" + (i+1) + ". "+ deadline.get(i).replace("#"," ") + "\n";
        }
        return messageToPrint.trim();
    }

    public static String getFloatingTask() throws ParseException{
    	floating= initList("floating", taskStored);
        String messageToPrint = "";
        if(floating.size() == 0) {
            return messageToPrint = "No tasks";
        }
        for(int i=0; i<floating.size(); i++) {
            messageToPrint += "F" + (i+1) + ". "+ floating.get(i).replace("#"," ") + "\n";
        }
        return messageToPrint.trim();
    }
    
    public static String getRecurringTask() throws ParseException{
    	repeatedTask= initList("repeat", taskStored);
        String messageToPrint = "";
        if(repeatedTask.size() == 0) {
            return messageToPrint = "No tasks";
        }
        for(int i=0; i<repeatedTask.size(); i++) {
            messageToPrint += "R" + (i+1) + ". "+ repeatedTask.get(i).replace("#"," ") + "\n";
        }
        return messageToPrint.trim();
    }
    

}
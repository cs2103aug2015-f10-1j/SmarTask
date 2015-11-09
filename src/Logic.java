import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @@author A0125994R
 * 
 */

public class Logic {

    private static Storage storage = new Storage();
    private static ArrayList<Task> taskStored = storage.retrieveFile();
    private static ArrayList<String> msgLogger = new ArrayList<String>();
    private static ArrayList<String> event;
    private static ArrayList<String> deadline;
    private static ArrayList<String> floating;
    private static ArrayList<String> repeatedTask = new ArrayList<String>();
    private static ArrayList<Integer> searchList;

    // Error messages to display to the user
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
    private static final String MESSAGE_COLLISION_TASK = "Collision Task!";
    private static final String MESSAGE_FAIL_TO_ADD = "Fail to add an invalid task";
    private static final String MESSAGE_NO_TASK = "No tasks";
    private static final String MESSAGE_ASK_FOR_COMMAND = "Please enter a command.";
    private static final String MESSAGE_NO_TODAY_TASK = "There is no task due on today.";
    private static final String MESSAGE_SEARCH_NOT_FOUND ="Results not found!";

    // Successful messages to display to the user
    private static final String ADD_STOP_SUC = "Add stop command to recurring task successfully!";
    private static final String UNDO_SUCCESSFUL = "undo successfully";
    private static final String REDO_SUCCESSFUL = "redo successfully";
    private static final String TASK_UPDATED = "task is successfully updated!!";

    // Different types of tasks that can be added
    private static final String FLOATING_TASK = "floating";
    private static final String EVENT_TASK = "event";
    private static final String DEADLINE_TASK = "deadline";
    private static final String RECURRING_TASK = "repeat";
    private static final String DAY_REC = "day";
    private static final String WEEK_REC = "week";
    private static final String MONTH_REC = "month";
    private static final String YEAR_REC = "year";

    private static int taskCode;
    private static CommandHistory history = new CommandHistory(new ArrayList<Task>(taskStored));
    private static Date currentDateAndTime;
    private static Date currentDate;
    private static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
    
    // // Regex
    private static final String REGEX_HASH = "#";
    private static final String REGEX_AT_SIGN = "@";
    private static final String REGEX_BLANK = "";
    private static final String REGEX_WHITESPACE = " ";
    

    // ================================================================
    // "executeCommand" direct the command to method
    // ================================================================

    public static void executeCommand(String userInput) throws Exception {

        if (userInput.trim().isEmpty()) {
            msgLogger.add(MESSAGE_ASK_FOR_COMMAND);
        } else {
            try {
                msgLogger.add("command : " + userInput);
                Command command = CommandParser.parse(userInput);
                switch (command.getCommandType()) {
                case ADD:
                    addTask(command);
                    break;

                case REPEAT:
                    addRepeatTask(command);
                    break;

                case DELETE:
                    deleteTask(command);
                    break;

                case UPDATE:
                    updateTask(command);
                    break;

                case SEARCH:
                    searchTask(command);
                    break;

                case COMPLETE:
                    completeTask(command);
                    break;

                case UNDO:
                    undoCommand();
                    break;

                case REDO:
                    redoCommand();
                    break;

                case VIEW:
                    viewTodayTask();
                    break;

                case STOP_REPEAT:
                    stopRec(command);
                    break;

                case SETFILEPATH:
                    storage.setSavePath(command.getFilePath());
                    break;

                case INVALID:
                    msgLogger.add(MESSAGE_INVALID_COMMAND);
                    break;

                }
            } catch (Exception e) {
                msgLogger.add(e.getMessage());
            }
        }
    }

    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // Main command methods
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************

    // ================================================================
    // "Add" command methods
    // ================================================================
    private static void addTask(Command command) throws Exception {
        try {
            String taskType = command.getTaskType();
            ArrayList<String> detailTask = new ArrayList<String>();
            Task.Type type;
            taskCode = getID();
            Task task;

            if (taskType.equals(FLOATING_TASK)) {
                detailTask.add(command.getTaskDescription() + REGEX_HASH + taskCode);
                type = Task.Type.FLOATING;
                task = new Task(type, detailTask);
                if (!isCollision(task)) {
                    msgLogger.add("add " + command.getTaskDescription() + " successful!");
                    floating.add(detailTask.get(0));
                } else {
                    msgLogger.add(MESSAGE_COLLISION_TASK);
                }
            } else if (taskType.equals(EVENT_TASK)) {
                detailTask.add(command.getTaskEventStart() + REGEX_HASH + command.getTaskEventEnd() + REGEX_HASH
                        + command.getTaskDescription() + REGEX_HASH + taskCode);
                type = Task.Type.EVENT;
                task = new Task(type, detailTask);

                if (!isCollision(task)) {
                    msgLogger.add("add " + command.getTaskDescription() + " successful!");
                    event.add(detailTask.get(0));
                } else {
                    msgLogger.add(MESSAGE_COLLISION_TASK);
                }
            } else if (taskType.equals(DEADLINE_TASK)) {
                detailTask.add(command.getTaskDeadline() + REGEX_HASH + command.getTaskDescription() + "#" + taskCode);
                type = Task.Type.DEADLINE;
                task = new Task(type, detailTask);
                if (!isCollision(task)) {
                    msgLogger.add("add " + command.getTaskDescription() + " successful!");
                    deadline.add(detailTask.get(0));
                } else {
                    msgLogger.add(MESSAGE_COLLISION_TASK);
                }
            } else {
                throw new Exception(MESSAGE_FAIL_TO_ADD);
            }

            taskStored.add(task);
            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));

        } catch (FileNotFoundException e) {
            msgLogger.add(e.toString());
        }
    }

    // ================================================================
    // "Repeat task" command methods
    // ================================================================
    private static void addRepeatTask(Command com) throws Exception {
        ArrayList<String> detailStored = new ArrayList<String>();
        taskCode = getID();
        String taskType = com.getRepeatType();
        Task.Type type = Task.Type.REPEAT;
        Task task = null;

        if (taskType.equals(DAY_REC)) {
            detailStored.add(taskType + REGEX_HASH + com.getDateAdded().toString() + REGEX_HASH + com.getRepeatStartTime() + REGEX_HASH
                    + com.getRepeatEndTime() + REGEX_HASH + com.getDayInterval() + REGEX_HASH + com.getRepeatUntil().toString() + REGEX_HASH
                    + com.getTaskDescription() + REGEX_HASH + taskCode + REGEX_HASH + com.getStopRepeatInString());
            task = new Task(type, detailStored);

            if (!isCollision(task)) {
                repeatedTask.add(detailStored.get(0));
                taskStored.add(task);
                storage.saveToFile(taskStored);
                history.addChangeToHistory(new ArrayList<Task>(taskStored));
                msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
            } else {
                msgLogger.add(MESSAGE_COLLISION_TASK);
            }
        } else if (taskType.equals(WEEK_REC)) {
            detailStored.add(taskType + REGEX_HASH + com.getDateAdded().toString() + REGEX_HASH + com.getRepeatStartTime() + REGEX_HASH
                    + com.getRepeatEndTime() + REGEX_HASH + com.getWeekInterval() + REGEX_HASH + com.getDaySelectedString() + REGEX_HASH
                    + com.getRepeatUntil() + REGEX_HASH + com.getTaskDescription() + REGEX_HASH + taskCode + REGEX_HASH
                    + com.getStopRepeatInString());
            task = new Task(type, detailStored);

            if (!isCollision(task)) {
                repeatedTask.add(detailStored.get(0));
                taskStored.add(task);
                storage.saveToFile(taskStored);
                history.addChangeToHistory(new ArrayList<Task>(taskStored));
                msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
            } else {
                msgLogger.add(MESSAGE_COLLISION_TASK);
            }
        } else if (taskType.equals(MONTH_REC)) {
            detailStored.add(taskType + REGEX_HASH + com.getDateAdded().toString() + REGEX_HASH + com.getRepeatStartTime() + REGEX_HASH
                    + com.getRepeatEndTime() + REGEX_HASH + com.getMonthInterval() + REGEX_HASH + com.getRepeatUntil() + REGEX_HASH
                    + com.getTaskDescription() + REGEX_HASH + taskCode + REGEX_HASH + com.getStopRepeatInString());
            task = new Task(type, detailStored);

            if (!isCollision(task)) {
                repeatedTask.add(detailStored.get(0));
                taskStored.add(task);
                storage.saveToFile(taskStored);
                history.addChangeToHistory(new ArrayList<Task>(taskStored));
                msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
            } else {
                msgLogger.add(MESSAGE_COLLISION_TASK);
            }
        }

        else if (taskType.equals(YEAR_REC)) {
            detailStored.add(taskType + REGEX_HASH + com.getDateAdded().toString() + REGEX_HASH + com.getRepeatStartTime() + REGEX_HASH
                    + com.getRepeatEndTime() + REGEX_HASH + com.getDayInterval() + REGEX_HASH + com.getRepeatUntil() + REGEX_HASH
                    + com.getTaskDescription() + REGEX_HASH + taskCode + REGEX_HASH + com.getStopRepeatInString());
            task = new Task(type, detailStored);

            if (!isCollision(task)) {
                repeatedTask.add(detailStored.get(0));
                taskStored.add(task);
                storage.saveToFile(taskStored);
                history.addChangeToHistory(new ArrayList<Task>(taskStored));
                msgLogger.add("addrc " + com.getTaskDescription() + " successful!");
            } else {
                msgLogger.add(MESSAGE_COLLISION_TASK);
            }
        }

    }

    // ================================================================
    // "search" command methods
    // ================================================================
    private static void searchTask(Command command) throws FileNotFoundException {
        ArrayList<String> keyWordList = command.getSearchKeyword();
        String keyword = REGEX_BLANK;
        taskStored.clear();
        Task.Type taskType;
        int index = 1;
        searchList = new ArrayList<Integer>();
        taskStored = storage.retrieveFile(); // Get the latest task from the
                                             // storage

        for (int i = 0; i < keyWordList.size(); i++) {
            keyword = keyWordList.get(i).toLowerCase();

            for (int j = 0; j < taskStored.size(); j++) {
                String[] arr = taskStored.get(j).getDescription().split(REGEX_WHITESPACE);

                for (int k = 0; k < arr.length; k++) {
                    if (arr[k].toLowerCase().contains(keyword)) {
                        taskCode = taskStored.get(j).getID();
                        taskType = taskStored.get(j).getType();

                        if (taskType.equals(Task.Type.DEADLINE)) {
                            msgLogger.add((index++) + REGEX_WHITESPACE + taskStored.get(j).getDescription() + " deadline is : "
                                    + taskStored.get(j).getDeadline());
                        } else if (taskType.equals(Task.Type.EVENT)) {
                            msgLogger.add((index++) + REGEX_WHITESPACE + taskStored.get(j).getDescription() + " start time is : "
                                    + taskStored.get(j).getEventStart() + " " + taskStored.get(j).getEventEnd());
                        } else if (taskType.equals(Task.Type.FLOATING)) {
                            msgLogger.add((index++) + REGEX_WHITESPACE + taskStored.get(j).getDescription());
                        } else if (taskType.equals(Task.Type.REPEAT)) {
                            msgLogger.add((index++) + REGEX_WHITESPACE + taskStored.get(j).getDescription()
                                    + " repeating peroid end in : " + taskStored.get(i).getTaskRepeatEndTime());
                        }
                        searchList.add(taskCode);
                    }
                }
            }
        }
        if (searchList.size()==0){
            msgLogger.add(MESSAGE_SEARCH_NOT_FOUND);
        }
    }

    // ================================================================
    // "Delete" command methods
    // ================================================================
    private static void deleteTask(Command command) {
        String taskType = command.getTaskType();

        try {
            int indexToRemove = command.getTaskID() - 1;
            String removedItem = REGEX_BLANK;
            String currentLine = REGEX_BLANK;

            if (taskType != null) {
                if (taskType.equals(DEADLINE_TASK)) {
                    currentLine = deadline.get(indexToRemove);
                    removedItem = deadline.remove(indexToRemove);
                    String str[] = currentLine.split(REGEX_HASH);
                    taskCode = Integer.parseInt(str[str.length - 1]);
                } else if (taskType.equals(FLOATING_TASK)) {
                    currentLine = floating.get(indexToRemove);
                    removedItem = floating.remove(indexToRemove);
                    String str[] = currentLine.split(REGEX_HASH);
                    taskCode = Integer.parseInt(str[str.length - 1]);
                } else if (taskType.equals(EVENT_TASK)) {
                    currentLine = event.get(indexToRemove);
                    removedItem = event.remove(indexToRemove);
                    String str[] = currentLine.split(REGEX_HASH);
                    taskCode = Integer.parseInt(str[str.length - 1]);
                } else if (taskType.equals(RECURRING_TASK)) {
                    currentLine = repeatedTask.get(indexToRemove);
                    removedItem = repeatedTask.get(indexToRemove);
                    String str[] = currentLine.split(REGEX_HASH);
                    taskCode = Integer.parseInt(str[str.length - 1]);
                }
            } else {
                if (searchList.size() != 0) {
                    msgLogger.add(Integer.toString(searchList.get(indexToRemove)));
                    taskCode = searchList.get(indexToRemove);
                } else {
                    msgLogger.add(MESSAGE_INVALID_INDEX);
                }

            }

            for (int i = 0; i < taskStored.size(); i++) {
                if (taskStored.get(i).getID() == taskCode) {
                    taskStored.remove(i);
                    break;
                }
            }

            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("deleted " + taskType + " index " + command.getTaskID() + " successfully!");
        } catch (Exception e) {
            if (taskType.equals(DEADLINE_TASK) && deadline.size() == 0) {
                msgLogger.add(MESSAGE_DEADLINE_EMPTY);
            } else if (taskType.equals(FLOATING_TASK) && floating.size() == 0) {
                msgLogger.add(MESSAGE_FLOATING_EMPTY);
            } else if (taskType.equals(EVENT_TASK) && event.size() == 0) {
                msgLogger.add(MESSAGE_EVENT_EMPTY);
            } else {
                msgLogger.add(MESSAGE_NOTHING_TO_DELETE);
            }
        }
    }

    // ================================================================
    // "Complete" command method
    // ===============================================================
    private static void completeTask(Command command) {
        String taskType = command.getTaskType();

        try {
            int indexToComplete = command.getTaskID() - 1;
            String currentLine = REGEX_WHITESPACE;

            if (taskType.equals(DEADLINE_TASK)) {
                currentLine = deadline.get(indexToComplete);
                deadline.remove(indexToComplete);
            } else if (taskType.equals(FLOATING_TASK)) {
                currentLine = floating.get(indexToComplete);
                floating.remove(indexToComplete);
            } else if (taskType.equals(EVENT_TASK)) {
                currentLine = event.get(indexToComplete);
                event.remove(indexToComplete);
            } else if (taskType.equals(RECURRING_TASK)) {
                currentLine = repeatedTask.get(indexToComplete);
                repeatedTask.remove(indexToComplete);
            }

            String str[] = currentLine.split(REGEX_HASH);
            taskCode = Integer.parseInt(str[str.length - 1]);

            for (int i = 0; i < taskStored.size(); i++) {
                if (taskStored.get(i).getID() == taskCode) {
                    if (taskType.equals(RECURRING_TASK)) {
                        String stopStr = REGEX_BLANK;
                        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                        if (taskStored.get(i).getStopRepeatInString() != null) {
                            stopStr = taskStored.get(i).getStopRepeatInString() + REGEX_AT_SIGN + df.format(currentDate);
                        } else {
                            stopStr = df.format(currentDate);
                        }

                        taskStored.get(i).setStopRepeat(stopStr);
                        break;
                    } else {
                        taskStored.get(i).setIsComplete(true);
                        break;
                    }
                }
            }

            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
            msgLogger.add("completed " + taskType + " index " + command.getTaskID());
        } catch (Exception e) {
            if (taskType.equals("deadline") && deadline.size() == 0) {
                msgLogger.add(MESSAGE_DEADLINE_CANT_COMPLETE);
            } else if (taskType.equals("floating") && floating.size() == 0) {
                msgLogger.add(MESSAGE_FLOATING_CANT_COMPLETE);
            } else if (taskType.equals("event") && event.size() == 0) {
                msgLogger.add(MESSAGE_EVENT_CANT_COMPLETE);
            } else {
                msgLogger.add(MESSAGE_NOTHING_TO_COMPLETE);
            }
        }
    }
    // ================================================================
    // "View" command method is use to view all the task that due on today
    // ===============================================================

    private static void viewTodayTask() throws ParseException {
        initializedArrayList();
        int count=0;

        for (int i = 0; i < deadline.size(); i++) {
            String[] str = deadline.get(i).split(REGEX_HASH);
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            SimpleDateFormat getDate = new SimpleDateFormat("MMM dd yyyy");
            Date timeDue = df.parse(str[0]);
            if (getDate.format(timeDue).equals(getDate.format(currentDateAndTime))) {
                msgLogger.add("D" + (i + 1) + ". " + str[1] + " due on " + str[0]);
                count++;
            }
        }
        for (int i = 0; i < event.size(); i++) {
            String[] str = event.get(i).split(REGEX_HASH);
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            SimpleDateFormat getTime = new SimpleDateFormat("MMM dd");
            Date timeStart = df.parse(str[0]);
            Date timeEnd = df.parse(str[1]);
            if (getTime.format(timeStart).equals(getTime.format(currentDateAndTime))) {
                msgLogger.add("E" + (i + 1) + ". " + str[2] + " from " + getTime.format(timeStart) + " to "
                        + getTime.format(timeEnd));
                count++;
            }
        }
        for (int i = 0; i < repeatedTask.size(); i++) {
            String[] str = repeatedTask.get(i).split(REGEX_HASH);
            msgLogger.add("R" + (i + 1) + ". " + str[1] + REGEX_WHITESPACE + str[0]);
            count++;
        } 
        if (count==0) {
            msgLogger.add(MESSAGE_NO_TODAY_TASK);
        }
    }

    // ================================================================
    // "Update" command methods
    // ================================================================
    private static void updateTask(Command command) throws FileNotFoundException {
        String taskType = command.getTaskType();
        String updatedItem = REGEX_BLANK;
        String existingItem = REGEX_BLANK;
        String updatedTask = REGEX_BLANK;
        Task.Type type;
        ArrayList<String> updatedLine = new ArrayList<String>();

        try {
            int indexToUpdate = command.getTaskID() - 1;

            if (taskType.equals(FLOATING_TASK)) {
                existingItem = floating.get(indexToUpdate);
                String[] strArr = existingItem.split(REGEX_HASH);
                taskCode = Integer.parseInt(strArr[strArr.length - 1]);
                updatedItem += strArr[0] + REGEX_HASH;

                if (command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                    updatedTask += command.getTaskDescription();
                } else {
                    updatedItem += strArr[1];
                    updatedTask += strArr[1];
                }

                updatedItem += REGEX_HASH + Integer.toString(taskCode);
                updatedTask += REGEX_HASH + Integer.toString(taskCode);
                type = Task.Type.FLOATING;
                floating.set(indexToUpdate, updatedItem);
                msgLogger.add(TASK_UPDATED);
            } else if (taskType.equals(DEADLINE_TASK)) {
                existingItem = deadline.get(indexToUpdate);
                String[] strArr = existingItem.split(REGEX_HASH);
                taskCode = Integer.parseInt(strArr[strArr.length - 1]);

                if (command.getTaskDeadline() != null) {
                    updatedItem += command.getTaskDeadline() + REGEX_HASH;
                    updatedTask += command.getTaskDeadline() + REGEX_HASH;
                } else {
                    updatedItem += strArr[0] + REGEX_HASH;
                    updatedTask += strArr[0] + REGEX_HASH;
                }

                if (command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                    updatedTask += command.getTaskDescription();
                } else {
                    updatedItem += strArr[1];
                    updatedTask += strArr[1];
                }
                updatedItem = updatedItem + REGEX_HASH + Integer.toString(taskCode);
                updatedTask = updatedTask + REGEX_HASH + Integer.toString(taskCode);
                type = Task.Type.DEADLINE;
                deadline.set(indexToUpdate, updatedItem);
                msgLogger.add(TASK_UPDATED);
            } else if (taskType.equals(EVENT_TASK)) {
                existingItem = event.get(indexToUpdate);
                String[] strArr = existingItem.split(REGEX_HASH);
                taskCode = Integer.parseInt(strArr[strArr.length - 1]);

                if (command.getTaskEventStart() != null) {
                    updatedItem += command.getTaskEventStart() + REGEX_HASH;
                    updatedTask += command.getTaskEventStart().toString() + REGEX_HASH;

                } else {
                    updatedItem += strArr[0] + REGEX_HASH;
                    updatedTask += strArr[0] + REGEX_HASH;
                }

                if (command.getTaskEventEnd() != null) {
                    updatedItem += command.getTaskEventEnd() + REGEX_HASH;
                    updatedTask += command.getTaskEventEnd() + REGEX_HASH;
                } else {
                    updatedItem += strArr[1] + REGEX_HASH;
                    updatedTask += strArr[1] + REGEX_HASH;
                }

                if (command.getTaskDescription() != null) {
                    updatedItem += command.getTaskDescription();
                    updatedTask += command.getTaskDescription();
                } else {
                    updatedItem += strArr[2];
                    updatedTask += strArr[2];
                }

                updatedItem += REGEX_HASH + Integer.toString(taskCode);
                updatedTask += REGEX_HASH + Integer.toString(taskCode);
                event.set(indexToUpdate, updatedItem);
                msgLogger.add(TASK_UPDATED);
                type = Task.Type.EVENT;
            } else if (taskType.equals(RECURRING_TASK)) {
                existingItem = repeatedTask.get(indexToUpdate);

                String[] strArr = existingItem.split(REGEX_HASH);

                String repeatType = strArr[0];
                updatedItem += strArr[0] + REGEX_HASH;
                updatedTask += strArr[0] + REGEX_HASH;

                if (command.getDateAdded() != null) {
                    updatedItem += command.getDateAdded().toString() + REGEX_HASH;
                    updatedTask += command.getDateAdded().toString() + REGEX_HASH;
                } else {
                    updatedItem += strArr[1] + REGEX_HASH;
                    updatedTask += strArr[1] + REGEX_HASH;
                }

                if (command.getRepeatStartTime() != null) {
                    updatedItem += command.getRepeatStartTime().toString() + REGEX_HASH;
                    updatedTask += command.getRepeatStartTime().toString() + REGEX_HASH;
                } else {
                    updatedItem += strArr[2] + REGEX_HASH;
                    updatedTask += strArr[2] + REGEX_HASH;
                }

                if (command.getRepeatEndTime() != null) {
                    updatedItem += command.getRepeatEndTime().toString() + REGEX_HASH;
                    updatedTask += command.getRepeatEndTime().toString() + REGEX_HASH;
                } else {
                    updatedItem += strArr[3] + REGEX_HASH;
                    updatedTask += strArr[3] + REGEX_HASH;
                }

                if (repeatType.equals(DAY_REC)) {
                    if (command.getDayInterval() != null) {
                        updatedItem += command.getDayInterval() + REGEX_HASH;
                        updatedTask += command.getDayInterval() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[4] + REGEX_HASH;
                        updatedTask += strArr[4] + REGEX_HASH;
                    }

                    if (command.getRepeatUntil() != null) {
                        updatedItem += command.getRepeatUntil() + REGEX_HASH;
                        updatedTask += command.getRepeatUntil() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[5] + REGEX_HASH;
                        updatedTask += strArr[5] + REGEX_HASH;
                    }

                    if (command.getTaskDescription() != null) {
                        updatedItem += command.getTaskDescription() + REGEX_HASH;
                        updatedTask += command.getTaskDescription() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[6] + REGEX_HASH;
                        updatedTask += strArr[6] + REGEX_HASH;
                    }
                    taskCode = Integer.parseInt(strArr[7].trim());
                    updatedItem += Integer.toString(taskCode) + REGEX_HASH + strArr[8];
                    updatedTask += Integer.toString(taskCode) + REGEX_HASH + strArr[8];

                } else if (repeatType.equals(WEEK_REC)) {
                    if (command.getWeekInterval() != null) {
                        updatedItem += command.getWeekInterval() + REGEX_HASH;
                        updatedTask += command.getWeekInterval() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[4] + REGEX_HASH;
                        updatedTask += strArr[4] + REGEX_HASH;
                    }

                    if (command.getDaySelectedString() != null) {
                        updatedItem += command.getDaySelectedString() + REGEX_HASH;
                        updatedTask += command.getDaySelectedString() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[5] + REGEX_HASH;
                        updatedTask += strArr[5] + REGEX_HASH;
                    }

                    if (command.getRepeatUntil() != null) {
                        updatedItem += command.getRepeatUntil() + REGEX_HASH;
                        updatedTask += command.getRepeatUntil() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[6] + REGEX_HASH;
                        updatedTask += strArr[6] + REGEX_HASH;
                    }

                    if (command.getTaskDescription() != null) {
                        updatedItem += command.getTaskDescription() + REGEX_HASH;
                        updatedTask += command.getTaskDescription() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[7] + REGEX_HASH;
                        updatedTask += strArr[7] + REGEX_HASH;
                    }
                    taskCode = Integer.parseInt(strArr[8].trim());
                    updatedItem += Integer.toString(taskCode) + REGEX_HASH + strArr[9];
                    updatedTask += Integer.toString(taskCode) + REGEX_HASH + strArr[9];

                } else if (repeatType.equals(MONTH_REC)) {
                    if (command.getMonthInterval() != null) {
                        updatedItem += command.getMonthInterval() + REGEX_HASH;
                        updatedTask += command.getMonthInterval() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[4] + REGEX_HASH;
                        updatedTask += strArr[4] + REGEX_HASH;
                    }

                    if (command.getRepeatUntil() != null) {
                        updatedItem += command.getRepeatUntil() + REGEX_HASH;
                        updatedTask += command.getRepeatUntil() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[5] + REGEX_HASH;
                        updatedTask += strArr[5] + REGEX_HASH;
                    }

                    if (command.getTaskDescription() != null) {
                        updatedItem += command.getTaskDescription() + REGEX_HASH;
                        updatedTask += command.getTaskDescription() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[6] + REGEX_HASH;
                        updatedTask += strArr[6] + REGEX_HASH;
                    }
                    taskCode = Integer.parseInt(strArr[7].trim());
                    updatedItem += Integer.toString(taskCode) + REGEX_HASH + strArr[8];
                    updatedTask += Integer.toString(taskCode) + REGEX_HASH + strArr[8];

                } else if (repeatType.equals(YEAR_REC)) {
                    if (command.getYearInterval() != null) {
                        updatedItem += command.getYearInterval() + REGEX_HASH;
                        updatedTask += command.getYearInterval() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[4] + REGEX_HASH;
                        updatedTask += strArr[4] + REGEX_HASH;
                    }

                    if (command.getRepeatUntil() != null) {
                        updatedItem += command.getRepeatUntil() + REGEX_HASH;
                        updatedTask += command.getRepeatUntil() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[5] + REGEX_HASH;
                        updatedTask += strArr[5] + REGEX_HASH;
                    }

                    if (command.getTaskDescription() != null) {
                        updatedItem += command.getTaskDescription() + REGEX_HASH;
                        updatedTask += command.getTaskDescription() + REGEX_HASH;
                    } else {
                        updatedItem += strArr[6] + REGEX_HASH;
                        updatedTask += strArr[6] + REGEX_HASH;
                    }
                    taskCode = Integer.parseInt(strArr[7].trim());
                    updatedItem += Integer.toString(taskCode) + REGEX_HASH + strArr[8];
                    updatedTask += Integer.toString(taskCode) + REGEX_HASH + strArr[8];
                }

                repeatedTask.set(indexToUpdate, updatedItem);
                msgLogger.add(TASK_UPDATED);
                type = Task.Type.REPEAT;
            } else {
                msgLogger.add(MESSAGE_INVALID_INDEX);
                throw new Exception(MESSAGE_INVALID_INDEX);
            }

            for (int i = 0; i < taskStored.size(); i++) {
                if (taskStored.get(i).getID() == taskCode) {
                    taskStored.remove(i);
                    updatedLine = new ArrayList<String>();
                    updatedLine.add(updatedTask);
                    Task task = new Task(type, updatedLine);
                    taskStored.add(task);
                    storage.saveToFile(taskStored);
                    break;
                }
            }

            storage.saveToFile(taskStored);
            history.addChangeToHistory(new ArrayList<Task>(taskStored));
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }
    }

    // ================================================================
    // stop recurring task command method
    // ================================================================
    private static void stopRec(Command command) throws ParseException {
        int taskID = command.getTaskID() - 1;
        String stopItem = repeatedTask.get(taskID);
        String[] stop = stopItem.split(REGEX_HASH);
        taskID = Integer.parseInt(stop[stop.length - 2]);

        for (int i = 0; i < taskStored.size(); i++) {
            if (taskStored.get(i).getID() == taskID) {
                String str = REGEX_BLANK;
                if (taskStored.get(i).getStopRepeatInString() != null) {
                    str = taskStored.get(i).getStopRepeatInString() + REGEX_AT_SIGN + command.getStopRepeatInString();
                } else {
                    str = command.getStopRepeatInString();
                }

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
    private static void redoCommand() throws FileNotFoundException {
        String message = REGEX_BLANK;

        try {
            message = REDO_SUCCESSFUL;
            taskStored = new ArrayList<Task>(history.redo());
            storage.saveToFile(taskStored);
            event = initList(EVENT_TASK, taskStored);
            deadline = initList(DEADLINE_TASK, taskStored);
            floating = initList(FLOATING_TASK, taskStored);
            msgLogger.add(message);
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }
    }

    // ================================================================
    // undo command method
    // ================================================================
    private static void undoCommand() throws FileNotFoundException {
        String message = REGEX_BLANK;

        try {
            message = UNDO_SUCCESSFUL;
            taskStored = new ArrayList<Task>(history.undo());
            storage.saveToFile(taskStored);
            event = initList(EVENT_TASK, taskStored);
            deadline = initList(DEADLINE_TASK, taskStored);
            floating = initList(FLOATING_TASK, taskStored);
            msgLogger.add(message);
        } catch (Exception e) {
            msgLogger.add(e.getMessage());
        }
    }

    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // helping method for main methods of logic
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************

    private static void initializedArrayList() throws ParseException {
        event = initList(EVENT_TASK, taskStored);
        deadline = initList(DEADLINE_TASK, taskStored);
        repeatedTask = initList(RECURRING_TASK, taskStored);
    }

    private static ArrayList<String> initList(String type, ArrayList<Task> taskStored) throws ParseException {
        ArrayList<String> list = new ArrayList<String>();
        Task task = null;
        initDate();

        for (int i = 0; i < taskStored.size(); i++) {
            if (taskStored.get(i).getType().equals(Task.getTypeFromString(type))
                    && taskStored.get(i).getIsComplete() == false) {
                task = taskStored.get(i);
                if (!isOverdue(task)) {
                    if (type.equals(FLOATING_TASK)) {
                        list.add(taskStored.get(i).getFloatingString());
                    } else if (type.equals(EVENT_TASK)) {
                        list.add(taskStored.get(i).getEventString());
                    } else if (type.equals(DEADLINE_TASK)) {
                        list.add(taskStored.get(i).getDeadlineString());
                    } else if (type.equals(RECURRING_TASK)) {
                        if (!isStop(task)) {
                            if (compareDate(task)) {
                                list.add(taskStored.get(i).getRepeatString());
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    // Check whether the recurring task need to stop today
    private static boolean isStop(Task task) {
        boolean boo = false;
        ArrayList<Date> stopDate = new ArrayList<Date>();
        stopDate = task.getStopRepeat();

        Calendar calCur = Calendar.getInstance();
        calCur.setTime(currentDateAndTime);
        int monthCur = calCur.get(Calendar.MONTH);
        int dayCur = calCur.get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < stopDate.size(); i++) {
            Calendar calTask = Calendar.getInstance();
            calTask.setTime(stopDate.get(i));
            int monthForTask = calTask.get(Calendar.MONTH);
            int dayForTask = calTask.get(Calendar.DAY_OF_MONTH);

            if (stopDate.get(i).before(currentDate)) {
                stopDate.remove(i);
                task.getStopRepeat().remove(i);
            }

            if (monthForTask == monthCur && dayForTask == dayCur) {
                boo = true;
                break;
            }
        }
        return boo;
    }

    // Check whether the task is overdue
    private static boolean isOverdue(Task task) throws ParseException {
        boolean isOver = false;
        initDate();

        if (task.getType().equals(Task.Type.FLOATING)) {
        } else if (task.getType().equals(Task.Type.EVENT)) {
            Date date = convertStringToDate(task.getEventEnd());
            if (date.before(currentDateAndTime)) {
                isOver = true;
            }
        } else if (task.getType().equals(Task.Type.DEADLINE)) {
            Date date = convertStringToDate(task.getDeadline());
            if (date.before(currentDateAndTime)) {
                isOver = true;
            }
        } else if (task.getType().equals(Task.Type.REPEAT)) {
        }

        return isOver;
    }

    // convert the string date into a Date type
    private static Date convertStringToDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = sdf.parse(dateStr);
        return date;
    }

    // compare the task date with the current date
    private static boolean compareDate(Task task) {
        boolean isToday = false;
        // for daily comparison
        if (task.getTaskRepeatType().equals(DAY_REC)) {
            if (task.getDateAdded().equals(currentDateAndTime)) {
                isToday = true;
            } else if (task.getTaskRepeatUntil().after(currentDateAndTime)
                    && task.getDateAdded().before(currentDateAndTime)) {
                if (getDifferenceDays(task.getDateAdded(), currentDateAndTime)
                        % Integer.parseInt(task.getTaskRepeatInterval_Day()) == 0) {
                    isToday = true;
                }
            }
            // for weekly comparison
        } else if (task.getTaskRepeatType().equals(WEEK_REC)) {
            if (task.getDateAdded().equals(currentDateAndTime)) {
                isToday = true;
            } else if (task.getTaskRepeatUntil().after(currentDateAndTime)
                    && task.getDateAdded().before(currentDateAndTime)) {
                if (getWeeksBetween(task.getDateAdded(), currentDateAndTime)
                        % Integer.parseInt(task.getTaskRepeatInterval_Week()) == 0) {
                    if (isSameDay(task)) {
                        isToday = true;
                    }
                }
            }
            // for monthly comparison
        } else if (task.getTaskRepeatType().equals(MONTH_REC)) {
            if (task.getDateAdded().equals(currentDateAndTime)) {
                isToday = true;
            } else if (task.getTaskRepeatUntil().after(currentDateAndTime)
                    && task.getDateAdded().before(currentDateAndTime)) {
                if (isNextMonth(task)) {
                    isToday = true;
                }
            }
            // for yearly comparison
        } else if (task.getTaskRepeatType().equals(YEAR_REC)) {
            if (task.getDateAdded().equals(currentDateAndTime)) {
                isToday = true;
            } else if (task.getTaskRepeatUntil().after(currentDateAndTime)
                    && task.getDateAdded().before(currentDateAndTime)) {
                if (getYearBetweenDates(task.getDateAdded(), currentDateAndTime) > 0) {
                    isToday = true;
                }
            }
        }
        return isToday;
    }

    // Check whether this month should prompt this task
    private static boolean isNextMonth(Task task) {
        boolean boo = false;
        Calendar calTask = Calendar.getInstance();
        calTask.setTime(task.getDateAdded());
        int monthForTask = calTask.get(Calendar.MONTH);
        int dayForTask = calTask.get(Calendar.DAY_OF_MONTH);
        Calendar calCur = Calendar.getInstance();
        calCur.setTime(currentDateAndTime);
        int monthCur = calCur.get(Calendar.MONTH);
        int dayCur = calCur.get(Calendar.DAY_OF_MONTH);

        if ((Math.abs(monthForTask - monthCur) % Integer.parseInt(task.getTaskRepeatInterval_Month())) == 0) {
            if (dayForTask == dayCur) {
                boo = true;
            }
        }

        return boo;
    }

    // Check the date with the current day of week
    private static boolean isSameDay(Task task) {
        boolean boo = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDateAndTime);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if (task.getIsDaySelected()[dayOfWeek] == true) {
            boo = true;
        }

        return boo;
    }

    // Get the week difference between two dates
    private static int getWeeksBetween(Date a, Date b) {
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

    private static Date resetTime(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Get the year difference between two dates
    private static int getYearBetweenDates(Date d1, Date d2) {
        Calendar a = getCalendar(d1);
        Calendar b = getCalendar(d2);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);

        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
                || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }

        return diff;
    }

    // Convert the date to calendar format
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    // Get the number of days different between current date and the recurring
    // task starting date
    private static int getDifferenceDays(Date d1, Date d2) {
        int daysDiff = 0;
        long diff = d1.getTime() - d2.getTime();
        long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        daysDiff = (int) diffDays;
        return daysDiff;
    }

    // Get the unique task code
    private static int getID() {
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

    // Get the current date, time
    private static void initDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        currentDateAndTime = dateFormat.parse(dateFormat.format((cal.getTime())));
        DateFormat dateFormat2 = new SimpleDateFormat("dd/MM");
        currentDate = dateFormat2.parse(dateFormat.format(cal.getTime()));
    }

    // check whether there is collision task
    private static Boolean isCollision(Task task) {
        Boolean boo = false;
        for (int i = 0; i < taskStored.size(); i++) {
            if (taskStored.get(i).getDescription().equals(task.getDescription())) {
                if (taskStored.get(i).getType().equals(task.getType())) {
                    if (taskStored.get(i).getType().equals(Task.getTypeFromString(DEADLINE_TASK))) {
                        if (taskStored.get(i).getDeadline().equals(task.getDeadline())) {
                            boo = true;
                            break;
                        }
                    } else if (taskStored.get(i).getType().equals(Task.getTypeFromString(FLOATING_TASK))) {
                        boo = true;
                        break;
                    } else if (taskStored.get(i).getType().equals(Task.getTypeFromString(EVENT_TASK))) {
                        if (taskStored.get(i).getEventStart().equals(task.getEventStart())
                                && taskStored.get(i).getEventEnd().equals(task.getEventEnd())) {
                            boo = true;
                            break;
                        }
                    } else {
                        // Handle recurring task
                        String recurringType = task.getTaskRepeatType();
                        if (taskStored.get(i).getTaskRepeatType().equals(recurringType)) {
                            if (recurringType.equals(DAY_REC)) {
                                if (taskStored.get(i).getTaskRepeatInterval_Day()
                                        .equals(task.getTaskRepeatInterval_Day())) {
                                    if (taskStored.get(i).getTaskRepeatUntil()
                                            .compareTo(task.getTaskRepeatUntil()) == 0) {
                                        if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded()) == 0) {
                                            boo = true;
                                            break;
                                        }
                                    }
                                }
                            } else if (recurringType.equals(WEEK_REC)) {
                                if (taskStored.get(i).getTaskRepeatInterval_Week()
                                        .equals(task.getTaskRepeatInterval_Week())) {
                                    if (taskStored.get(i).getTaskRepeatUntil()
                                            .compareTo(task.getTaskRepeatUntil()) == 0) {
                                        if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded()) == 0) {
                                            boo = true;
                                            break;
                                        }
                                    }
                                }
                            } else if (recurringType.equals(MONTH_REC)) {
                                if (taskStored.get(i).getTaskRepeatInterval_Month()
                                        .equals(task.getTaskRepeatInterval_Month())) {
                                    if (taskStored.get(i).getTaskRepeatUntil()
                                            .compareTo(task.getTaskRepeatUntil()) == 0) {
                                        if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded()) == 0) {
                                            boo = true;
                                            break;
                                        }
                                    }
                                }
                            } else if (recurringType.equals(YEAR_REC)) {
                                if (taskStored.get(i).getTaskRepeatInterval_Year()
                                        .equals(task.getTaskRepeatInterval_Year())) {
                                    if (taskStored.get(i).getTaskRepeatUntil()
                                            .compareTo(task.getTaskRepeatUntil()) == 0) {
                                        if (taskStored.get(i).getDateAdded().compareTo(task.getDateAdded()) == 0) {
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

    // ================================================================
    // Getter methods to retrieve lists for UI
    // ================================================================
    public static String getMessageLog() {
        String messageToPrint = REGEX_BLANK;

        for (int i = 0; i < msgLogger.size(); i++) {
            if (msgLogger.get(i) != null){
                 messageToPrint += msgLogger.get(i) + "\n";
            }
        }

        return messageToPrint.trim();
    }

    public static String getEvents() throws ParseException {
        event = initList(EVENT_TASK, taskStored);
        String messageToPrint = REGEX_BLANK;

        if (event.size() == 0) {
            return messageToPrint = MESSAGE_NO_TASK;
        }

        for (int i = 0; i < event.size(); i++) {
            String[] str = event.get(i).split(REGEX_HASH);
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            SimpleDateFormat getTime = new SimpleDateFormat("dd MMM, HH:mm");
            Date timeStart = df.parse(str[0]);
            Date timeEnd = df.parse(str[1]);
            messageToPrint += "E" + (i + 1) + ". " + str[2] + " from " + getTime.format(timeStart) + " to "
                    + getTime.format(timeEnd) + "\n";
        }

        return messageToPrint.trim();
    }

    public static String getDeadline() throws ParseException {
        deadline = initList(DEADLINE_TASK, taskStored);
        String messageToPrint = REGEX_BLANK;

        if (deadline.size() == 0) {
            return messageToPrint = MESSAGE_NO_TASK;
        }

        for (int i = 0; i < deadline.size(); i++) {
            String[] str = deadline.get(i).split(REGEX_HASH);
            messageToPrint += "D" + (i + 1) + ". " + str[1] + " due on " + str[0] + "\n";
        }

        return messageToPrint.trim();
    }

    public static String getFloatingTask() throws ParseException {
        floating = initList(FLOATING_TASK, taskStored);

        String messageToPrint = REGEX_BLANK;

        if (floating.size() == 0) {
            return messageToPrint = MESSAGE_NO_TASK;
        }
        for (int i = 0; i < floating.size(); i++) {
            String[] str = floating.get(i).split(REGEX_HASH);
            messageToPrint += "F" + (i + 1) + ". " + str[0] + "\n";
        }

        return messageToPrint.trim();
    }

    public static String getRecurringTask() throws ParseException {
        repeatedTask = initList(RECURRING_TASK, taskStored);
        String messageToPrint = REGEX_BLANK;

        if (repeatedTask.size() == 0) {
            return messageToPrint = MESSAGE_NO_TASK;
        }

        for (int i = 0; i < repeatedTask.size(); i++) {
            String[] str = repeatedTask.get(i).split(REGEX_HASH);
            messageToPrint += "R" + (i + 1) + ". " + str[6] + REGEX_WHITESPACE + str[2] + ", repeat every " + str[4] + REGEX_WHITESPACE + str[0]
                    + "\n";
        }

        return messageToPrint.trim();
    }
}
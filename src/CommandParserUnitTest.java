import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.javafx.collections.SetAdapterChange;

public class CommandParserUnitTest {

    @Before
    public void setUp() throws Exception {
        Command invalid = CommandParser.parse("This is an invalid command");
        Command exit = CommandParser.parse("exit");
        Command undo = CommandParser.parse("undo");
        Command redo = CommandParser.parse("redo");
        Command complete = CommandParser.parse("complete D1");
        Command search = CommandParser.parse("search meeting NUS");
        Command delete = CommandParser.parse("delete D1");
        Command add = CommandParser.parse("add Meeting with Boss");
        Command update = CommandParser.parse("update D1 Arrange meeting 09/10/2015 09:00");
        String[] arr1 = new String[100];
        arr1[0] = invalid.getCommandType().toString();
        arr1[1] = exit.getCommandType().toString();
        arr1[2] = undo.getCommandType().toString();
        arr1[3] = redo.getCommandType().toString();
        arr1[4] = complete.getCommandType().toString();
        arr1[5] = add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription();
        arr1[6] = delete.getCommandType() + " " + delete.getTaskType() + " " + delete.getTaskID();
        arr1[7] = update.getCommandType() + " " + update.getTaskType() + " "  + update.getTaskID() + 
                " "  + update.getTaskDescription() + " " + update.getTaskDeadline();
        
        
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testParse() throws Exception {
        Command invalid = CommandParser.parse("This is an invalid command");
        Command exit = CommandParser.parse("exit");
        Command undo = CommandParser.parse("undo");
        Command redo = CommandParser.parse("redo");
        Command complete = CommandParser.parse("complete D1");
        Command search = CommandParser.parse("search meeting NUS");
        Command delete = CommandParser.parse("delete D1");
        Command add = CommandParser.parse("add Meeting with Boss");
        Command update = CommandParser.parse("update D1 Arrange meeting 09/10/2015 09:00");
        String[] arr1 = new String[100];
        arr1[0] = invalid.getCommandType().toString();
        arr1[1] = exit.getCommandType().toString();
        arr1[2] = undo.getCommandType().toString();
        arr1[3] = redo.getCommandType().toString();
        arr1[4] = complete.getCommandType().toString();
        arr1[5] = add.getCommandType() + " " + add.getTaskType() +" " + add.getTaskDescription();
        arr1[6] = delete.getCommandType() + " " + delete.getTaskType() + " " + delete.getTaskID();
        arr1[7] = update.getCommandType() + " " + update.getTaskType() + " "  + update.getTaskID() + 
                " "  + update.getTaskDescription() + " " + update.getTaskDeadline();
              
        Command invalid1 = CommandParser.parse("This is an invalid command");
        Command exit1 = CommandParser.parse("exit");
        Command undo1 = CommandParser.parse("undo");
        Command redo1 = CommandParser.parse("redo");
        Command complete1 = CommandParser.parse("complete D1");
        Command search1 = CommandParser.parse("search meeting NUS");
        Command delete1 = CommandParser.parse("delete D1");
        Command add1 = CommandParser.parse("add Meeting with Boss");
        Command update1 = CommandParser.parse("update D1 Arrange meeting 09/10/2015 09:00");
        
        String[] arr = new String[100];
        arr[0] = invalid1.getCommandType().toString();
        arr[1] = exit1.getCommandType().toString();
        arr[2] = undo1.getCommandType().toString();
        arr[3] = redo1.getCommandType().toString();
        arr[4] = complete1.getCommandType().toString();
        arr[5] = add1.getCommandType() + " " + add1.getTaskType() +" " + add1.getTaskDescription();
        arr[6] = delete1.getCommandType() + " " + delete1.getTaskType() + " " + delete1.getTaskID();
        arr[7] = update1.getCommandType() + " " + update1.getTaskType() + " "  + update1.getTaskID() + 
                " "  + update1.getTaskDescription() + " " + update1.getTaskDeadline();   
        
        
        assertArrayEquals("invalid", arr, arr1);
        assertArrayEquals("exit", arr, arr1);
        assertArrayEquals("undo", arr, arr1);
        assertArrayEquals("complete", arr, arr1);
        assertArrayEquals("add", arr, arr1);
        assertArrayEquals("delete", arr, arr1);
        assertArrayEquals("valid", arr, arr1);
    }

}

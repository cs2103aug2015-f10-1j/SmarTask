import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for CommandParser
 * 
 * @author A0108235M
 */

public class CommandParserUnitTest {

    @Test
    public final void testSetFilePathCommand() {
        try {
            Command filepath = CommandParser.parse("setfilepath " + System.getProperty("user.home"));
            assertEquals("Set file path command test fails", Command.Type.SETFILEPATH, filepath.getCommandType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
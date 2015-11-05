import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
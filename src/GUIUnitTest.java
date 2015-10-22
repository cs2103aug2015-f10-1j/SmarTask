import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class GUIUnitTest extends GuiTest {
	
	@Override
    protected Parent getRootNode() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("SmarTaskUI.fxml"));
            return parent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parent;
    }
	
	@Test
    public void testKeyEvents() {
        TextField inputWindow = find("#inputWindow");
        inputWindow.setText("add hello world");
        verifyThat("#inputWindow", hasText("add hello world"));
    }
}

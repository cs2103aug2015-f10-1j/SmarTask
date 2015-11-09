import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
}
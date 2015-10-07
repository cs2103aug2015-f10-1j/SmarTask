import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SmarTaskController implements Initializable {
  
	@FXML private TextArea mainWindow;    //Value injected by FXML Loader
	@FXML private TextArea displayWindow;	//Value injected by FXMLoader
	@FXML private TextArea taskWindow;    //Value injected by FXMLoader
	@FXML private TextField inputWindow;   //Value injected by FXMLoader
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert mainWindow != null : "fx:id=\"mainWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert displayWindow != null : "fx:id=\"displayWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert taskWindow != null : "fx:id=\"taskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert inputWindow != null : "fx:id=\"inputWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
	}
	
	@FXML
	public void onEnter(KeyEvent ke) {
		if(ke.getCode() == KeyCode.ENTER) {
			Logic logic = new Logic();
			String userCommand = inputWindow.getText();
			System.out.println(userCommand);
			inputWindow.clear();
	        try {
	            String logicReturn = logic.executeCommand(userCommand);
	            displayWindow.appendText(logicReturn);
	        } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	        }
		}
	}
}
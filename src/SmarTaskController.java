import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SmarTaskController implements Initializable {

	@FXML // fx:id = "commandButton"
	private Button commandButton;	//Value injected by FXMLoader
	private TextArea displayWindow; //Value injected by FXMLoader
	private TextArea taskWindow; //Value injected by FXMLoader
	private TextArea inputWindow; //Value injected by FXMLoader
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert commandButton != null : "fx:id=\"commandButton\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        assert displayWindow != null : "fx:id=\"displayWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert taskWindow != null : "fx:id=\"taskWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
		assert inputWindow != null : "fx:id=\"inputWindow\" was not injected: check your FXML file 'SmarTaskUI.fxml'.";
        
		Logic logic = new Logic();
		
		final Text actionTarget = new Text();

	    commandButton.setOnAction(new EventHandler<ActionEvent>() {

	            public void handle(ActionEvent e) {
	                String userCommand = inputWindow.getText();
	                try {
	                    logic.executeCommand(userCommand);
	                } catch (FileNotFoundException e1) {
	                    // TODO Auto-generated catch block
	                    e1.printStackTrace();
	                }
	                actionTarget.setFill(Color.FIREBRICK);
	                actionTarget.setText("Command has been entered");
	            }
	        });	
	}	
}
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * GUI is a class that receives commands from the user and pass it down to 
 * This program will serve as the main window of interaction between user and SmarTask.
 * 
 * @author David Chong
 *
 */

public class GUI extends Application {
	
	private static final String MESSAGE_WELCOME = "Welcome to SmarTask!";
	private static final String MESSAGE_GUIDE = "In case you need a refresher, type \" help \" to get a list of all the SmarTask commands.";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("SmarTask Main Window");
		primaryStage.show();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text messageWelcome = new Text(MESSAGE_WELCOME);
		messageWelcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(messageWelcome, 0, 0, 2, 1);
		
		Text messageGuide = new Text(MESSAGE_GUIDE);
		messageGuide.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(messageGuide, 0, 0, 2, 5);
		
		TextField userInputField = new TextField();
		grid.add(userInputField, 0, 4);
		
		Button btn = new Button("Enter");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		final Text actionTarget = new Text();
		grid.add(actionTarget, 0, 5);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent e) {
				String userCommand = userInputField.getText();
				try {
                    Logic.executeCommand(userCommand);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
				actionTarget.setFill(Color.FIREBRICK);
				actionTarget.setText("Command has been entered");
			}
		});
		
		Scene scene = new Scene(grid, 700, 600);
		primaryStage.setScene(scene);
	}
}

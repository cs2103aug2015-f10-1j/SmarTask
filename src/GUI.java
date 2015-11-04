import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * GUI is a class that loads up the fxml file to display to the user.
 * This program will serve as the main window of interaction between user and SmarTask.
 * 
 * @author David Chong
 *
 */

public class GUI extends Application {
	
    public static void main(String[] args) {
    	launch(args);
    }
    
    public void start(Stage primaryStage) {
    	try {
        	Parent root = FXMLLoader.load(getClass().getResource("SmarTaskUI.fxml"));
        	Scene scene = new Scene(root, 700, 800);
        	primaryStage.setTitle("SmarTask");
    	    primaryStage.setScene(scene);
    	    primaryStage.show();
    	} catch (IOException e) {
    	    System.err.println("Error! SmarTaskUI.fxml cannot be found!");
    	}
    }
}
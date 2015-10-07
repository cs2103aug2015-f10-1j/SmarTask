import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * GUI is a class that receives commands from the user and pass it down to 
 * This program will serve as the main window of interaction between user and SmarTask.
 * 
 * @author David Chong
 *
 */

public class GUI extends Application {
  
    public static void main(String[] args) {
        Application.launch(args);
    }
    
   @Override
    public void start(Stage primaryStage) {
	   try {
	    	Parent root = FXMLLoader.load(getClass().getResource("SmarTaskUI.fxml"));
	        primaryStage.setTitle("SmarTask Main Window");
	        primaryStage.setScene(new Scene(root, 1050, 700));
	        primaryStage.show(); 
	   } catch(Exception e) {
		   e.printStackTrace();
	   }       
    }
}
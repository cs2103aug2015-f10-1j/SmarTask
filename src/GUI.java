import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * GUI is a class that receives commands from the user and pass it down to 
 * This program will serve as the main window of interaction between user and SmarTask.
 * 
 * @author David Chong
 *
 */

public class GUI extends Application {
	
	public static int tabChanger;
	
    public static void main(String[] args) {
        tabChanger = 0;
    	launch(args);
    }
    
    public void start(Stage primaryStage) {
	   try {
		   if(tabChanger == 0) {
			   Parent root = FXMLLoader.load(getClass().getResource("SmarTaskUI.fxml"));
		       primaryStage.setTitle("SmarTask Main Window");
		       primaryStage.setScene(new Scene(root, 1050, 700));
			   primaryStage.show();
		   } else if(tabChanger == 1) {
			   Parent root = FXMLLoader.load(getClass().getResource("SmarTaskUIRecurringTasks.fxml"));
		       primaryStage.setTitle("SmarTask Recurring Tasks");
		       primaryStage.setScene(new Scene(root, 1050, 700));
			   primaryStage.show();
		   }
	   } catch(Exception e) {
		   e.printStackTrace();
	   }       
    }
    
    public void keyboardLog(KeyEvent ke) {
    	if(ke.getCode() == KeyCode.TAB) {
    		if(tabChanger == 0) {
    			tabChanger = 1;
    		} else {
    			tabChanger = 0;
    		}
    	}
    }
}
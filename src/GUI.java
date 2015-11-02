import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * GUI is a class that loads up the fxml file to display to the user.
 * This program will serve as the main window of interaction between user and SmarTask.
 * 
 * @author David Chong
 *
 */

public class GUI extends Application {

	private Stage stage;
	
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
		//stage = primaryStage;
    	//Scene scene = chooseFileScene(primaryStage);
    	//primaryStage.setTitle("SmarTask");
    	//primaryStage.setScene(scene);
        //primaryStage.show();
    }
    
    private Scene chooseFileScene(Stage primaryStage) {
        final FileChooser fileChooser = new FileChooser();
        final Button createFileButton = new Button("Create new file");
        final Button openFileButton = new Button("Open a file");
        final Button defaultFileButton = new Button("Default file location");
 
        createFileButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(final ActionEvent e) {
        		File file = fileChooser.showSaveDialog(primaryStage);
        		if (file != null) {
        			createFile(file);
        			try {
        				stage.setScene(createSmarTaskScene());
        			} catch (IOException e1) {
						e1.printStackTrace();
					}
                }
           }
        });
 
        openFileButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(final ActionEvent e) {
        		File file = fileChooser.showOpenDialog(primaryStage);
        		if (file != null) {
        			openFile(file);
        			try {
        				stage.setScene(createSmarTaskScene());
        			} catch (IOException e2) {
        				e2.printStackTrace();
        			}
                }
            }
        });
        
        defaultFileButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(final ActionEvent e) {
        		try {
        			stage.setScene(createSmarTaskScene());
        		} catch (IOException e3) {
        			e3.printStackTrace();
        		}
        	}
        });
 
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(createFileButton, 0, 0);
        GridPane.setConstraints(openFileButton, 1, 0);
        GridPane.setConstraints(defaultFileButton, 2, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(createFileButton, openFileButton, defaultFileButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        Scene scene = new Scene(rootGroup);
        return scene;
    }
    
    protected Scene createSmarTaskScene() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("SmarTaskUI.fxml"));
    	Scene scene = new Scene(root, 700, 800);
    	return scene;
    }
    
    private void createFile(File file) {
    	try {
    		String command = "setfilepath " + file.getAbsolutePath();
    		Logic.executeCommand(command);
    		System.out.println(command);
    	} catch (Exception e) {
    		Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, e);
    	}
    }
    
    private void openFile(File file) {
        try {
        	String command = "setfilepath " + file.getAbsolutePath();
        	Logic.executeCommand(command);
        	System.out.println(command);
        } catch (Exception e) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
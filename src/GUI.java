import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
	
	//private static boolean fileChose;
	
	private Desktop desktop = Desktop.getDesktop();
	
    public static void main(String[] args) {
    	launch(args);
    	//fileChose = false;
    }
    
    public void start(Stage primaryStage) {
    	/*if(!fileChose) {
    	primaryStage.setTitle("File Chooser Sample");
    	 
        final FileChooser fileChooser = new FileChooser();
        final Button createFileButton = new Button("Create new file");
        final Button openFileButton = new Button("Open a file");
 
        createFileButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        openFile(file);
                        fileChose = true;
                    }
                }
            });
 
        openFileButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        openFile(file);
                        fileChose = true;
                    }
                }
            });
 
 
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(createFileButton, 0, 0);
        GridPane.setConstraints(openFileButton, 1, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(createFileButton, openFileButton);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        primaryStage.setScene(new Scene(rootGroup));
        primaryStage.show();
    	} else {*/
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("SmarTaskUI.fxml"));
    		Scene scene = new Scene(root, 700, 800);
    		primaryStage.setTitle("SmarTask Main Window");
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
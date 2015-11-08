import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
 * GUI is a class that loads up the fxml file to display to the user. This
 * program will serve as the main window of interaction between user and
 * SmarTask.
 * 
 * @author A0116633L
 */

public class GUI extends Application {

    private static final String BUTTON_CHOOSELOCATION = "Choose Storage Location";
    private static final String BUTTON_DEFAULTLOCATION = "Use Default Location";
    private static final String COMMAND_FILEPATH = "setfilepath ";
    private static final String FILE_SMARTASKUI = "SmarTaskUI.fxml";
    private static final String MESSAGE_FXMLERROR = "Error! SmarTaskUI.fxml cannot be found!";
    private static final String MESSAGE_FILEPATHERROR = "Error! Filepath.txt cannot be read!";
    private static final String SCENE_TITLE = "SmarTask";

    private Stage stage;
    private String defaultFileLocation = GUI.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + "storage.txt";
    private static String filePath = "../SmarTask/" + "filepath.txt";
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
        if (checkFile(filePath)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(FILE_SMARTASKUI));
                Scene scene = new Scene(root, 700, 800);
                primaryStage.setTitle(SCENE_TITLE);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                System.err.println(MESSAGE_FXMLERROR);
            }
        } else {
    	    stage = primaryStage;
    	    Scene scene = chooseFileScene(primaryStage);
    	    primaryStage.setTitle(SCENE_TITLE);
    	    primaryStage.setScene(scene);
    	    primaryStage.show();
        }
    }
    
    private static boolean checkFile(String fileName) {
        File file = new File(fileName);
        FileReader fr = null;
        BufferedReader br = null;
        
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            if (br.readLine() != null) {
                br.close();
                return true;
            } else {
                br.close();
                return false;
            }
        } catch (IOException e) {
            System.err.println(MESSAGE_FILEPATHERROR);
        }
        
        return false;
    }
    
    private Scene chooseFileScene(Stage primaryStage) {
        final FileChooser fileChooser = new FileChooser();
        final Button createFileButton = new Button(BUTTON_CHOOSELOCATION);
        final Button defaultFileButton = new Button(BUTTON_DEFAULTLOCATION);
        
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
        
        defaultFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                try {
                    File file = new File(defaultFileLocation);
                    defaultFile(file);
                    stage.setScene(createSmarTaskScene());
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(createFileButton, 0, 0);
        GridPane.setConstraints(defaultFileButton, 1, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(createFileButton, defaultFileButton);
        
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
        
        Scene scene = new Scene(rootGroup);
        return scene;
    }
    
    protected Scene createSmarTaskScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(FILE_SMARTASKUI));
        Scene scene = new Scene(root, 700, 800);
        return scene;
    }
    
    private void createFile(File file) {
        try {
            String command = COMMAND_FILEPATH + file.getAbsolutePath();
            Logic.executeCommand(command);
        } catch (Exception e) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void defaultFile(File file) {
        try {
            String command = COMMAND_FILEPATH + file.getAbsolutePath();
            Logic.executeCommand(command);    
        } catch (Exception e) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, e);
        }
   }
}
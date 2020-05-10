package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Screen3Controller {
	   @FXML
	    private VBox screen3;

	    @FXML
	    private TextArea outputText;

	    @FXML
	    private Button btnReconfigure;

	    @FXML
	    private Button btnSave;
	    
	    @FXML
	    void SaveData(ActionEvent event) {
	    	try {
	    		FileChooser fileChooser = new FileChooser();
				File file = null;
				FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Schedule", "*.txt");
				fileChooser.getExtensionFilters().add(extensionFilter);
				fileChooser.setInitialFileName("savedSchedule");
				file = fileChooser.showSaveDialog(null);
				
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(outputText.getText().toString());
				objectOutputStream.close();
				fileOutputStream.close();
				System.out.printf("Schedule Saved at : " + file.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    @FXML
	    void reconfigure(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen1.fxml"));
	    	Parent root = loader.load();
	    	Stage stage = new Stage();
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Schedule Generator");
		    stage.show();
	    }
	    
	    public void setOutput(String text) {
	    	outputText.setText(text);
	    }
}

package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Screen1Controller implements Initializable{

    @FXML
    private ChoiceBox<String> busDropDown;

    @FXML
    private TextField busText;

    @FXML
    private ChoiceBox<String> cDropDown;

    @FXML
    private Label cLabel1;

    @FXML
    private Label cLabel2;

    @FXML
    private TextField cText1;

    @FXML
    private TextField cText2;
    
    @FXML
    private Button btnConfigure;
    
    Alert a = new Alert(AlertType.NONE);

    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		busDropDown.getItems().addAll("Novabus LFSE+ 294kW", "Novabus LFSE+ 394kW");
		cDropDown.getItems().addAll("Heliox", "ABB");
		
		 EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
           public void handle(ActionEvent e) 
           { 
               if(cDropDown.getValue()=="Heliox") {
            	   cLabel1.setText("Model: OC 450kW");
            	   cLabel2.setText("Model: FAST DC 50kW");
               }
               else {
            	   cLabel1.setText("Model: HVC 300PD 300kW");
            	   cLabel2.setText("Model: HVC 100PU-S 100kW");
               } 
           } 
       }; 
 
       // Set on action
       cDropDown.setOnAction(event); 
	}
	 @FXML
	    void configure(ActionEvent event) throws IOException {
		 
	     FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen2.fxml"));
	    
	     Parent root = (Parent) loader.load();
		 Screen2Controller controller = loader.getController();
		 
		 if(busDropDown.getValue()==null || cDropDown.getValue()==null || busText.getText().isEmpty() || cText1.getText().isEmpty() || cText2.getText().isEmpty()) {
			 a.setAlertType(AlertType.WARNING);
			 a.setHeaderText("None's are not accepted.");
			 a.show();
		 }
		 else {
			 try {
		    	   
				 float temp = Float.parseFloat(cText1.getText());
				 float temp1 = Float.parseFloat(cText2.getText());
				 float temp2 = Float.parseFloat(busText.getText());
				 	
				   controller.assignValues(temp, temp1, temp2);
				   controller.setLabelText((busDropDown.getValue()).toString());
			       controller.setchargerText(cDropDown.getValue());
			       Stage stage = new Stage();
			       Scene scene = new Scene(root);
			       stage.setScene(scene);
			       stage.setTitle("Configuration Plan");
			       
			       
			       /*Stage stage1 = (Stage) btnConfigure.getScene().getWindow();
			       stage1.close();
			       stage1.setOnCloseRequest(e -> Platform.exit());*/
			       stage.show();
		       }
		       catch (Exception e) {
		    	   a.setAlertType(AlertType.WARNING);
		    	   a.setHeaderText("Invalid Input Format for price");
		    	   a.show();
		       }
		 }    
	 }
}

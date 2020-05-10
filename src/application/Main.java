package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			VBox root = FXMLLoader.load(Main.class.getResource("Screen1.fxml"));

	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Schedule Generator");
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

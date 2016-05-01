package GUI;
import java.io.IOException;

import GUI.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {
	 Stage primaryStage;
	 BorderPane rootLayout;
	 
	 public void start(Stage primaryStage) {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Money Manager");
			initRootLayout();
		}
	 public void initRootLayout(){
		 try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/Startup.fxml"));
				rootLayout = (BorderPane) loader.load();
				
				// Give the controller access to the main app.
		        StartupController controller = loader.getController();
		       // controller.setMainApp(this);
				
				Scene scene = new Scene(rootLayout); 
				primaryStage.setScene(scene);
				primaryStage.show();
				
				

			}catch(IOException e){
				e.printStackTrace();
			}
	 }
	 public Stage getPrimaryStage(){
			return primaryStage;
		}
		public static void main(String[] args) {
			launch(args);
		}

}

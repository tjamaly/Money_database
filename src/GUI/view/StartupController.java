package GUI.view;
import GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class StartupController {
	
	MainApp mainApp;

	public StartupController(){}
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
	public void initialize(){
		
	}
	public void handleLoginButton(){
		System.out.println("Login Button Pressed");
	}
	public void handleSignupButton(){
		System.out.println("SIGNUP Button Pressed");
	}
}

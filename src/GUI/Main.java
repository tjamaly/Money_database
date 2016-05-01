package GUI;
import java.io.IOException;


import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
public class Main extends Application{
	Button login = new Button("LOGIN") , signup = new Button("SIGNUP");
	VBox buttonholder = new VBox(8);
	Pane toppane = new Pane(), lowpane = new Pane();
	Label response = new Label();
	Stage window;
	String Title = "Money Manager";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage myStage) throws Exception {
		// TODO Auto-generated method stub
		window = myStage;
		window.setTitle(Title);
		
		BorderPane rootNode = new BorderPane();
		rootNode.setPrefSize(800,600);
		
		rootNode.setCenter(buttonholder);
		buttonholder.setAlignment(Pos.CENTER);
		buttonholder.getChildren().addAll(login,signup,response);
		toppane.setPrefHeight(80); lowpane.setPrefHeight(80);
		rootNode.setTop(toppane);
		rootNode.setBottom(lowpane);
		
		
		Scene myScene = new Scene(rootNode);
		window.setScene(myScene);
		
		login.setOnAction( e -> {
		window.setScene(Login.start(myStage,myScene));
		});
		
		signup.setOnAction(e->{
			window.setScene(Signup.start(myStage,myScene));
		});
		
		window.setOnCloseRequest(e -> {
			e.consume();
			if(Close.conformation())
				window.close();
		});
		
		myStage.show();
		
		
	}

}

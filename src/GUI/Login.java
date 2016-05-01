package GUI;

import java.sql.PreparedStatement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {
	static User user ;
	static Button login = new Button("LOGIN") , back = new Button("BACK");
	static VBox labelholder = new VBox(8);
	static HBox buttonholder = new HBox();
	static Pane toppane = new Pane(), lowpane = new Pane(),rightpane = new Pane() , leftpane = new Pane() ;
	static Label username = new Label("Username"), password = new Label("Password");
	static TextField uname = new TextField(), psswrd = new TextField();
	static String user_name, user_pass;
	static Label response= new Label();
	
	public static Scene start(Stage window ,Scene startup) { 
		
		BorderPane rootNode = new BorderPane();
		rootNode.setPrefSize(800,600);
		
		rootNode.setCenter(labelholder);
		rootNode.setTop(toppane);
		rootNode.setBottom(lowpane);
		rootNode.setLeft(leftpane);
		rootNode.setRight(rightpane);
		
		toppane.setPrefHeight(80); lowpane.setPrefHeight(80);
		leftpane.setPrefWidth(250); rightpane.setPrefWidth(250);
		labelholder.setAlignment(Pos.CENTER_LEFT);
		buttonholder.setAlignment(Pos.CENTER);
		buttonholder.setPadding(new Insets(0, 10, 0, 10));
		back.setPadding(new Insets(0, 10, 0, 10));
		
		login.setAlignment(Pos.CENTER);
		login.setPrefSize(80, 25);
		back.setAlignment(Pos.CENTER);
		back.setPrefSize(80, 25);
		
		uname.setPromptText("Enter Username");
		psswrd.setPromptText("Enter Password");
		
		if(buttonholder.getChildren().isEmpty()){
		buttonholder.getChildren().addAll(login,back);
		labelholder.getChildren().addAll(username,uname,password,psswrd,buttonholder,response);
		}
		
		Scene SScene = new Scene(rootNode);
		
		
		login.setOnAction( e ->{ 
				response.setText("LOGIN IS PRESSED");
				window.setScene(Dashboard.start(window));
		});
		
		back.setOnAction( e ->{ 	
		response.setText("BACK IS PRESSED");
		window.setScene(startup);
		});

		return SScene;
	}
	
}

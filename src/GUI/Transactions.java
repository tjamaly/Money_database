package GUI;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Transactions {
    static TableView<Wallet> walletview = new TableView<>();
    static TableView<Transaction> transview = new TableView<>();
    
    
    static Button dashboard_c = new Button("DASHBOARD") , wallets_c = new Button("WALLETS"), category_c = new Button("CATEGORY"), transactions_c = new Button("TRANSACTIONS"),charts_c = new Button("CHARTS");
	static Button add_trans = new Button ("ADD"), edit_trans = new Button("EDIT"), delete_trans = new Button("DELETE");
    static HBox menuholder = new HBox(), buttonholder = new HBox();
	static Pane  lowpane = new Pane();
	
	public static Scene start() { 
		
		BorderPane rootNode = new BorderPane();
		rootNode.setPrefSize(800,600);
		
		rootNode.setCenter(transview);
		rootNode.setTop(menuholder);
		rootNode.setBottom(lowpane);
		rootNode.setLeft(walletview);
		
		menuholder.setPrefHeight(60); lowpane.setPrefHeight(60);
		menuholder.setAlignment(Pos.CENTER_RIGHT);
		menuholder.setPadding(new Insets(10, 10, 10, 10));
		
		  //Wallets Display column
		
        TableColumn<Wallet, String> wnameColumn = new TableColumn<>("Wallets");
        wnameColumn.setMinWidth(200);
        wnameColumn.setCellValueFactory(new PropertyValueFactory<>("wallet_name"));
        
        //TODO change the datatype of TYPE field in Transaction to String. Use the Functions to Convert To & Fro
        TableColumn<Transaction, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        
        TableColumn<Transaction, String> catColumn = new TableColumn<>("Category");
        catColumn.setMinWidth(200);
        catColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        TableColumn<Transaction, Double> amntColumn = new TableColumn<>("Amount");
        amntColumn.setMinWidth(100);
        amntColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        TableColumn<Transaction, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("t_date"));
        
        TableColumn<Transaction, String> noteColumn = new TableColumn<>("Note");
        noteColumn.setMinWidth(200);
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        
        

		
		
		if(menuholder.getChildren().isEmpty()){
		menuholder.getChildren().addAll(dashboard_c, wallets_c, category_c , transactions_c,charts_c );
		buttonholder.getChildren().addAll(add_trans,edit_trans,delete_trans);
		walletview.getColumns().addAll(wnameColumn);
		walletview.setItems(getProduct());
		transview.getColumns().addAll(typeColumn,catColumn,amntColumn,dateColumn,noteColumn);
		transview.setItems(getTransactions());
		}
	
		dashboard_c.setOnAction( e ->{ 
			response.setText("LOGIN IS PRESSED");
			window.setScene(Dashboard.start());
	});
		wallets_c.setOnAction( e ->{ 
			response.setText("LOGIN IS PRESSED");
			window.setScene(Dashboard.start());
	});
		category_c.setOnAction( e ->{ 
			response.setText("LOGIN IS PRESSED");
			window.setScene(Dashboard.start());
	});
		transactions_c.setOnAction( e ->{ 
			response.setText("LOGIN IS PRESSED");
			window.setScene(Dashboard.start());
	});
		charts_c.setOnAction( e ->{ 
			response.setText("LOGIN IS PRESSED");
			window.setScene(Dashboard.start());
	});
		
		
		Scene SScene = new Scene(rootNode);
		return SScene;
		
	}
	 //Get all of the products
    public static ObservableList<Wallet> getProduct(){
        ObservableList<Wallet> products = FXCollections.observableArrayList();
        products.add(new Wallet(2,1,"Cash"));
        products.add(new Wallet(3,1,"Business"));
        products.add(new Wallet(4,1,"Sales"));
        products.add(new Wallet(3,2,"Personal"));
        return products;
    }
}


}

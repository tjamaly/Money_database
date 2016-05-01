package GUI;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class TransactionScene {
	
	    static TableView<Wallet> walletview = new TableView<>();
	    static TableView<Transaction> transview = new TableView<>();
	    static Connection conn = null;
		static String dbase = "money_manager.accdb";
	    static ResultSet result = null;
	    
	    
	    static Button dashboard_c = new Button("DASHBOARD") , wallets_c = new Button("WALLETS"), category_c = new Button("CATEGORY"), transactions_c = new Button("TRANSACTIONS"),charts_c = new Button("CHARTS");
		static Button add_trans = new Button ("ADD"), edit_trans = new Button("EDIT"), delete_trans = new Button("DELETE");
	    static HBox menuholder = new HBox(), buttonholder = new HBox();
	    static VBox centerpane = new VBox();
		static Pane  lowpane = new Pane();
		
		public static Scene start() { 
			
			
			BorderPane rootNode = new BorderPane();
			rootNode.setPrefSize(800,600);
			
			rootNode.setCenter(centerpane);
			rootNode.setTop(menuholder);
			rootNode.setBottom(lowpane);
			rootNode.setLeft(walletview);
			
			menuholder.setPrefHeight(60); lowpane.setPrefHeight(60);
			menuholder.setAlignment(Pos.CENTER_RIGHT);
			menuholder.setPadding(new Insets(10, 10, 10, 10));
			buttonholder.setPadding(new Insets(10,10,10,10));
	        buttonholder.setSpacing(10);
			
			 
			  //Wallets Display column
	        TableColumn<Wallet, String> wnameColumn = new TableColumn<>("Wallets");
	        wnameColumn.setMinWidth(200);
	        wnameColumn.setCellValueFactory(new PropertyValueFactory<>("wallet_name"));
	        
	        //Transaction Type Column
	        TableColumn<Transaction, String> typeColumn = new TableColumn<>("Type");
	        typeColumn.setMinWidth(100);
	        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
	        //Transaction Category Column
	        TableColumn<Transaction, String> catColumn = new TableColumn<>("Category");
	        catColumn.setMinWidth(200);
	        catColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
	        //Transaction amount Column
	        TableColumn<Transaction, Double> amntColumn = new TableColumn<>("Amount");
	        amntColumn.setMinWidth(100);
	        amntColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        //Transaction Date Column
	        TableColumn<Transaction, LocalDate> dateColumn = new TableColumn<>("Date");
	        dateColumn.setMinWidth(100);
	        dateColumn.setCellValueFactory(new PropertyValueFactory<>("t_date"));
	        //Transaction Note Column
	        TableColumn<Transaction, String> noteColumn = new TableColumn<>("Note");
	        noteColumn.setMinWidth(200);
	        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
	        
	        
	       
			
			
			if(menuholder.getChildren().isEmpty()){
			menuholder.getChildren().addAll(dashboard_c, wallets_c, category_c , transactions_c,charts_c );
			buttonholder.getChildren().addAll(add_trans,edit_trans,delete_trans);
			walletview.getColumns().addAll(wnameColumn);
			transview.getColumns().addAll(typeColumn,catColumn,amntColumn,dateColumn,noteColumn);
			centerpane.getChildren().addAll(transview,buttonholder);
			}
			walletview.setItems(getWallets(2));
			
			walletview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				transview.getItems().clear();
				transview.setItems(getTransactions(null, newSelection));
			});
			
			
		
//			dashboard_c.setOnAction( e ->{ 
//				response.setText("LOGIN IS PRESSED");
//				window.setScene(Dashboard.start());
//		});
//			wallets_c.setOnAction( e ->{ 
//				response.setText("LOGIN IS PRESSED");
//				window.setScene(Dashboard.start());
//		});
//			category_c.setOnAction( e ->{ 
//				response.setText("LOGIN IS PRESSED");
//				window.setScene(Dashboard.start());
//		});
//			transactions_c.setOnAction( e ->{ 
//				response.setText("LOGIN IS PRESSED");
//				window.setScene(Dashboard.start());
//		});
//			charts_c.setOnAction( e ->{ 
//				response.setText("LOGIN IS PRESSED");
//				window.setScene(Dashboard.start());
//		});
//
			//Buttons to interact with Transactions 
			add_trans.setOnAction( e ->{ 
				Wallet temp =  walletview.getSelectionModel().selectedItemProperty().get();
			    		newTransaction.display(2,temp);
				
		});
//			edit_trans.setOnAction( e ->{ 
//				editButtonClicked();
//		});
			delete_trans.setOnAction( e ->{ 
				deleteButtonClicked();
		});
			
			
			Scene SScene = new Scene(rootNode);
			return SScene;
			
		}
		  //Get all of the Wallets
		     static ObservableList<Wallet> getWallets(Integer id){
		    	ObservableList<Wallet> walletList = FXCollections.observableArrayList();
		    	try{
		    	//Load the jdcb-sql(odcb) bridge driver
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				
				//Enable Logging 
				DriverManager.setLogWriter(new PrintWriter((System.err)));
				
				//Get the Connection to the Database 
				conn=DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Taha/Desktop/UCanAccess/" + dbase);
				System.out.println("Database Connected");
				conn.setAutoCommit(false); //To commit changes we have to call conn.commit()
				
			
			PreparedStatement walletS = conn.prepareStatement("SELECT * FROM Wallet WHERE User_id = ?");
			walletS.setInt(1, id);
			result = walletS.executeQuery();
			while(result.next()){
				Wallet tempw = new Wallet();
				tempw.setWallet_id(result.getInt("Wallet_id"));
				System.out.println("Wallet ID" + tempw.getWallet_id());
				tempw.setUser_id(result.getInt("User_id"));
				System.out.println("User ID" + tempw.getUser_id());
				tempw.setWallet_name(result.getString("Wallet_name"));
				System.out.println("Wallet Name" + tempw.getWallet_name());
				walletList.add(tempw);
			}
		    	}catch(SQLException e){
		    		e.printStackTrace();
		    	}catch(ClassNotFoundException e){
		    		e.printStackTrace();
		    	}
		    	for(int i =0; i < walletList.size(); i++){
					System.out.println(walletList.get(i).getWallet_name());
				}
			return walletList;
		    }


	    static ObservableList<Transaction> getTransactions(Integer user_id,Wallet wallet){
		    	ObservableList<Transaction> transList = FXCollections.observableArrayList();
		    	try{
		    	//Load the jdcb-sql(odcb) bridge driver
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				
				//Enable Logging 
				DriverManager.setLogWriter(new PrintWriter((System.err)));
				
				//Get the Connection to the Database 
				conn=DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Taha/Desktop/UCanAccess/" + dbase);
				System.out.println("Database Connected");
				
	    	Integer wallet_id = wallet.getWallet_id();
			Byte type ;
			PreparedStatement getTrans = conn.prepareStatement("SELECT * FROM Transactions WHERE User_id = ? AND Wallet_id = ? ORDER BY T_date ");
			getTrans.setInt(1,user_id); 
			getTrans.setInt(2,wallet_id);
			result = getTrans.executeQuery();
			while(result.next()){
				Transaction tempt = new Transaction();
				tempt.setTransaction_id(result.getInt("Transaction_id"));
				tempt.setUser_id(result.getInt("User_id"));
				tempt.setWallet_id(result.getInt("Wallet_id"));
				type = result.getByte("Type");
				if(type == 1)
					tempt.setType("Income");
				else if (type == 0)
					tempt.setType("Expense");
				tempt.setCategory(result.getString("Category"));
				tempt.setAmount(result.getDouble("Amount"));
				tempt.setT_date(LocalDate.parse(result.getString("t_date")));
				tempt.setNote(result.getString("Note"));
				transList.add(tempt);
			}}catch(SQLException e){
		    		e.printStackTrace();
		    	}catch(ClassNotFoundException e){
		    		e.printStackTrace();
		    	}
		    	for(int i =0; i < transList.size(); i++){
					System.out.println(transList.get(i).getAmount());
				}
			return transList;
	    }
	    public static void deleteButtonClicked(){
	        ObservableList<Transaction> TransSelected, allTrans;
	        allTrans = transview.getItems();
	       	TransSelected = transview.getSelectionModel().getSelectedItems();

	        TransSelected.forEach(allTrans::remove);
	        try {
	        for(int i =0 ; i<TransSelected.size(); i++){
	        	PreparedStatement deleteTrans;
					deleteTrans = conn.prepareStatement("DELETE FROM Transactions WHERE Transaction_id = ? ");
				
	    		deleteTrans.setInt(1,TransSelected.get(i).getTransaction_id());
	    		deleteTrans.executeUpdate();
				
	        }
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	    
	    //
	    public static void addButtonClicked(){
	    	Wallet pass = new Wallet();
	    	
	    	
	    
	    }
//	    public void editButtonClicked(){
//	       
//	    }
	}


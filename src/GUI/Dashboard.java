package GUI;

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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import GUI.Access;

public class Dashboard {
	    
	static Connection conn = null;
	static String dbase = "money_manager.accdb";
    static ResultSet result = null;
    static Double  year_income = 0.0, year_exp = 0.0, year_bal = 0.0, lmonth_income = 0.0, lmonth_exp = 0.0, lmonth_bal = 0.0, month_income = 0.0, month_exp = 0.0, month_bal = 0.0;
		public static Scene start(Stage window) { 
			 TableView<Wallet> summaryview = new TableView<>();
		   
		    
		    
		     Button dashboard_c = new Button("DASHBOARD") , wallets_c = new Button("WALLETS"), category_c = new Button("CATEGORY"), transactions_c = new Button("TRANSACTIONS"),charts_c = new Button("CHARTS");
			 VBox labelholder = new VBox(8);
			 HBox menuholder = new HBox();
			 Pane  lowpane = new Pane(),rightpane = new Pane();
			 Label year_sum = new Label("Yearly Summary"), lmonth_sum = new Label("Last Months Summary"), month_sum = new Label ("Current Month's Summary"),
					year_sum_inflow = new Label(), year_sum_outflow = new Label(), year_sum_bal = new Label(),
					lmonth_sum_inflow = new Label(), lmonth_sum_outflow = new Label(), lmonth_sum_bal = new Label(), 
					month_sum_inflow = new Label(), month_sum_outflow = new Label(),month_sum_bal = new Label();
			 GridPane year_grid = new GridPane(), lmonth_grid = new GridPane(), month_grid = new GridPane();
			 Label year_sum_inflow_text = new Label("Yearly Inflow"), year_sum_outflow_text = new Label("Yearly Outflow"), year_sum_bal_text = new Label("Yearly Balance"),
					lmonth_sum_inflow_text = new Label("Last Month's Inflow"), lmonth_sum_outflow_text = new Label("Last Month's Outflow"), lmonth_sum_bal_text = new Label("Last Month's Balance"), 
					month_sum_inflow_text = new Label("Current Month's Inflow"), month_sum_outflow_text = new Label("Current Month's Outflow"),month_sum_bal_text = new Label("Current Month's Balance");
			
			BorderPane rootNode = new BorderPane();
			rootNode.setPrefSize(800,600);
			
			rootNode.setCenter(labelholder);
			rootNode.setTop(menuholder);
			rootNode.setBottom(lowpane);
			rootNode.setLeft(summaryview);
			rootNode.setRight(rightpane);
			
			menuholder.setPrefHeight(60); lowpane.setPrefHeight(60);
			rightpane.setPrefWidth(200);
			labelholder.setAlignment(Pos.CENTER_LEFT);
			menuholder.setAlignment(Pos.CENTER_RIGHT);
			menuholder.setPadding(new Insets(10, 10, 10, 10));
			
			  //Wallets Display column
			
	        TableColumn<Wallet, String> wnameColumn = new TableColumn<>("Wallets");
	        wnameColumn.setMinWidth(200);
	        wnameColumn.setCellValueFactory(new PropertyValueFactory<>("wallet_name"));
	        
	        //GridPane Alignments
	        year_grid.setPadding(new Insets(10, 10, 10, 10));lmonth_grid.setPadding(new Insets(10, 10, 10, 10));month_grid.setPadding(new Insets(10, 10, 10, 10));
	        year_grid.setVgap(8);lmonth_grid.setVgap(8);month_grid.setVgap(8);
	        year_grid.setHgap(10);lmonth_grid.setHgap(10);month_grid.setHgap(10);
	       //Setting Year Summary Constraints 
	        GridPane.setConstraints(year_sum_inflow_text, 0, 0);
	        GridPane.setConstraints(year_sum_inflow, 1, 0);
	        GridPane.setConstraints(year_sum_outflow_text, 0, 1);
	        GridPane.setConstraints(year_sum_outflow, 1, 1);
	        GridPane.setConstraints(year_sum_bal_text, 0, 2);
	        GridPane.setConstraints(year_sum_bal, 1, 2);
	      //Setting Last Month Summary Constraints 
	        GridPane.setConstraints(lmonth_sum_inflow_text, 0, 0);
	        GridPane.setConstraints(lmonth_sum_inflow, 1, 0);
	        GridPane.setConstraints(lmonth_sum_outflow_text, 0, 1);
	        GridPane.setConstraints(lmonth_sum_outflow, 1, 1);
	        GridPane.setConstraints(lmonth_sum_bal_text, 0,2 );
	        GridPane.setConstraints(lmonth_sum_bal, 1, 2);
	      //Setting Month Summary Constraints 
	        GridPane.setConstraints(month_sum_inflow_text, 0, 0);
	        GridPane.setConstraints(month_sum_inflow, 1, 0);
	        GridPane.setConstraints(month_sum_outflow_text, 0, 1);
	        GridPane.setConstraints(month_sum_outflow, 1, 1);
	        GridPane.setConstraints(month_sum_bal_text, 0, 2);
	        GridPane.setConstraints(month_sum_bal, 1, 2);

			
			
			if(menuholder.getChildren().isEmpty()){
			menuholder.getChildren().addAll(dashboard_c, wallets_c, category_c , transactions_c,charts_c );
			year_grid.getChildren().addAll(year_sum_inflow , year_sum_outflow , year_sum_bal ,year_sum_inflow_text, year_sum_outflow_text , year_sum_bal_text);
			lmonth_grid.getChildren().addAll(lmonth_sum_inflow , lmonth_sum_outflow , lmonth_sum_bal ,lmonth_sum_inflow_text, lmonth_sum_outflow_text , lmonth_sum_bal_text);
			month_grid.getChildren().addAll(month_sum_inflow , month_sum_outflow , month_sum_bal ,month_sum_inflow_text, month_sum_outflow_text , month_sum_bal_text);
			labelholder.getChildren().addAll(year_sum,year_grid,lmonth_sum,lmonth_grid, month_sum,month_grid);
			summaryview.getColumns().addAll(wnameColumn);
			}
			summaryview.setItems(getWallets(1));
			//Listen to Selection and Set Action. 
			summaryview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			    getSummary(newSelection);
			    year_sum_inflow.setText(Double.toString(year_income)); 
			    year_sum_outflow.setText(Double.toString(year_exp));
			    year_sum_bal.setText(Double.toString(year_bal));
			    lmonth_sum_inflow.setText(Double.toString(lmonth_income)); 
			    lmonth_sum_outflow.setText(Double.toString(lmonth_exp));
			    lmonth_sum_bal.setText(Double.toString(lmonth_bal));
			    month_sum_inflow.setText(Double.toString(month_income)); 
			    month_sum_outflow.setText(Double.toString(month_exp));
			    month_sum_bal.setText(Double.toString(month_bal));
			    
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
			transactions_c.setOnAction( e ->{ 
				window.setScene(TransactionScene.start());
		});
//			charts_c.setOnAction( e ->{ 
//				response.setText("LOGIN IS PRESSED");
//				window.setScene(Dashboard.start());
//		});
			
			
			Scene SScene = new Scene(rootNode);
			return SScene;
			
		}
		//Get Summary When a Wallet is Selected 
		static void getSummary(Wallet wallet){
			Double amnt;
			int wallet_id = wallet.getWallet_id();
			LocalDate date = LocalDate.now();
			DateTimeFormatter yf = DateTimeFormatter.ofPattern("yyyy");
			String year = "%" + yf.format(date);
			DateTimeFormatter mf = DateTimeFormatter.ofPattern("MM/yyyy");
			String month = "%" + mf.format(date);
			LocalDate lastdate = LocalDate.now().minusMonths(1);
			DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/yyyy");
			String lmonth = "%" + df.format(lastdate);
			
			PreparedStatement getamnt;
			try {
				//Load the jdcb-sql(odcb) bridge driver
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				
				//Enable Logging 
				DriverManager.setLogWriter(new PrintWriter((System.err)));
				
				//Get the Connection to the Database 
				conn=DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Taha/Desktop/UCanAccess/" + dbase);
				System.out.println("Database Connected");
				conn.setAutoCommit(false); //To commit changes we have to call conn.commit()
				
				getamnt = conn.prepareStatement("SELECT Amount FROM Transactions Where wallet_id = ? AND t_date = ?");
			
			getamnt.setInt(1, wallet_id);
			getamnt.setString(2, year);
			result = getamnt.executeQuery();
			while(result.next()){
				amnt = result.getDouble(1);
				if(amnt > 0)
					year_income += amnt;
				else if(amnt < 0 )
					year_exp += amnt;
				} 
				year_bal = year_income + year_exp;

				getamnt.setString(2, lmonth);
				result = getamnt.executeQuery();
				while(result.next()){
					amnt = result.getDouble(1);
				if(amnt > 0)
					lmonth_income += amnt;
				else if(amnt < 0 )
					lmonth_exp += amnt;
				} 
				lmonth_bal = lmonth_income + lmonth_exp;
				
				getamnt.setString(2, month);
				result = getamnt.executeQuery();
				while(result.next()){
					amnt = result.getDouble(1);
				if(amnt > 0)
					month_income += amnt;
				else if(amnt < 0 )
					month_exp += amnt;
				} 
				month_bal = month_income + month_exp;
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}
					
				
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
}
	    


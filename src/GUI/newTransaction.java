package GUI;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;

public class newTransaction {

    //Create variable
    static Transaction temp_trans ;
    static Connection conn = null;
	static String dbase = "money_manager.accdb";
    static ResultSet result = null;
    
    
    static Label type_text = new Label("Type"), cat_text = new Label("Category"), amount_text = new Label("Amount"), date_text = new Label("Date"), note_text = new Label("Note");
    static Label error = new Label();
    static ComboBox<String> typeBox = new ComboBox<>();
    static ComboBox<String> catBox = new ComboBox<>();
    static VBox layout = new VBox();
    static HBox buttonholder = new HBox();
    static DatePicker datePicker = new DatePicker();
    static TextField amount = new TextField() , note = new TextField();

    public static Transaction display(Integer user_id, Wallet wallet) {
        
    	Integer wallet_id = wallet.getWallet_id();

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Transaction");
        window.setMinWidth(350);
       

        Pane rootNode = new Pane();
        rootNode.setPrefSize(600,400);

        //getItems returns the ObservableList object which you can add items to
        typeBox.setPromptText("Select Transaction Type");
        if(typeBox.getItems().isEmpty()){
        typeBox.getItems().add("Income");
        typeBox.getItems().add("Expense");
        }

        //Populate Category Box 
        typeBox.setOnAction(e->{
        	catBox.setItems(getCategory(user_id));
        });
        

        //Create two buttons
        Button okButton = new Button("OK");
        Button noButton = new Button("CANCEL");

        //Date Picker Remove unnecessary weeks column 
        datePicker.setShowWeekNumbers(false);

        //Clicking will set answer and close window
        okButton.setOnAction(e -> {
            temp_trans = okPressed(user_id,wallet_id);
            window.close();
        });
        noButton.setOnAction(e -> {
        	temp_trans = null;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        //Add buttons
        if(buttonholder.getChildren().isEmpty()){
        buttonholder.getChildren().addAll(okButton,noButton);
        layout.getChildren().addAll(amount_text,amount,type_text,typeBox,cat_text,catBox, date_text, datePicker, note_text, note, error, buttonholder);
        }

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); 
        
        return temp_trans;
    }
    static ObservableList<String> getCategory(Integer user_id){
    	
    	//Clear UP the CatBox Items. 
    	catBox.getItems().clear();
    	
    	//TODO initialize catList with predefined data
    	ObservableList<String> catList = FXCollections.observableArrayList();
    	try{
    	//Load the jdcb-sql(odcb) bridge driver
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		
		//Enable Logging 
		DriverManager.setLogWriter(new PrintWriter((System.err)));
		
		//Get the Connection to the Database 
		conn=DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Taha/Desktop/UCanAccess/" + dbase);
		System.out.println("Database Connected");
		
		Byte type, zero = 0, one = 1;
		if(typeBox.getValue().equalsIgnoreCase("Income"))
			type = one;
		else 
			type = zero;
        //SQL Query to get the Categories for the Current User
        String category;
        PreparedStatement walletS = conn.prepareStatement("SELECT * FROM Category WHERE User_id = ? AND Type = ?");
        walletS.setInt(1, user_id);
        walletS.setByte(2, type);
        result = walletS.executeQuery();
        while(result.next()){
           category = result.getString("Description");
           catList.add(category); 
        }
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
        return catList;
    }
    
    static Transaction okPressed(Integer user_id, Integer wallet_id){
    	Transaction newtrans = null;
    	Statement S = null;
    	Integer transaction_id = null;
    	Double amount_t = null;
        String type_name= null;
        String cat_name = null;
        LocalDate date = null;
        String note_t = null;
        
        //Create Connections and get the Transaction_id using SQL 
    	try{
    		//Load the jdcb-sql(odcb) bridge driver
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		
    		//Enable Logging 
    		DriverManager.setLogWriter(new PrintWriter((System.err)));
    		
    		//Get the Connection to the Database 
    		conn=DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Taha/Desktop/UCanAccess/" + dbase);
    		System.out.println("Database Connected");
    		
    		S = conn.createStatement();
    		result = S.executeQuery("SELECT MAX(Transaction_id) AS max From Transactions"); 
			result.next(); 
			transaction_id = result.getInt("max") + 1;	
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
    	
    	// Get the Data from the Fields and Transaction_id from above and create a new Transaction item
        try{
         amount_t = Double.parseDouble(amount.getText());
         type_name = typeBox.getValue();
         cat_name = catBox.getValue();
         date = datePicker.getValue();
         note_t = note.getText();
         newtrans = new Transaction(transaction_id,user_id,wallet_id,type_name,cat_name,amount_t,date,note_t);
        }catch(InputMismatchException e){
            error.setText("Please Ensure you input correct data into the relevant fields");
        }
        //Use SQL to add the newly created Transaction item to the Database 
        try{
        	Byte one = 1 , zero = 0;
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String date_s = df.format(date);
			PreparedStatement newTrans = conn.prepareStatement("INSERT INTO Transactions(user_id,wallet_id,type,category,amount,t_date,note) VALUES (?,?,?,?,?,?,?)");
			newTrans.setInt(1, user_id); 
			newTrans.setInt(2, wallet_id);
			if(type_name.equalsIgnoreCase("Income"))
				newTrans.setByte(3, one);
			else 
				newTrans.setByte(3, zero);
			newTrans.setString(4, cat_name); 
			newTrans.setDouble(5, amount_t); 
			newTrans.setString(6, date_s); 
			newTrans.setString(7, note_t);
			newTrans.executeUpdate();
			newTrans.close();
        }catch(SQLException e){
        	e.printStackTrace();
        }
        //return the Transaction created
        return newtrans;
    }

}
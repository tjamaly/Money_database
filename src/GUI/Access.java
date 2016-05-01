package GUI;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateStringConverter;

//import net.ucanaccess.converters.TypesMap.AccessType;
//import net.ucanaccess.ext.FunctionType;
//import net.ucanaccess.jdbc.UcanaccessConnection;
//import net.ucanaccess.jdbc.UcanaccessDriver;

public class Access {

	//Variables 
	static Connection conn = null;
 	static String dbase = "money_manager.accdb";
    static ObservableList<Statement> statements = FXCollections.observableArrayList();
    static ObservableList<Wallet> wallets = FXCollections.observableArrayList();
    static ObservableList<Category> types = FXCollections.observableArrayList();
    static ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    static Statement S ;
    static ResultSet result = null;
	ArrayList<Wallet> walletList = new ArrayList<Wallet>();
    User user; 
    //_____________________________________________________________________________________________________________________________________
    
	
//The Running part of the Class which executes all the Sql Databae Statements.
	public void go (String[] args) {
	
	try{

	//Initial Testing SQL 	
	result = S.executeQuery("SELECT * FROM User");
	System.out.println("Got Result");
	dump(result,"The Full Table");
	//_________________________________________________________________________________________________________


	//Calling the Login Function which take care of login. 
	login("taha","taha");
	//___________________________________________________________________________________________________

	//Calling the Signup Function which takes care of the Signup Function 
	signup("mohsin","mohsin","mohsin","jagga@gmail.com");
	//___________________________________________________________________________________________________

		}catch(SQLException s){
			s.printStackTrace();
			System.err.println("Database Not Found");
		}
	}
	
	//Funciton to Create WAllet you pass to it the USER_id and the NAME of the WAllet 
	public void createWallet(Integer user_id, String wallet_name) throws SQLException{
		Wallet tempw ; 
		result = S.executeQuery("SELECT MAX(Wallet_id) AS max From Wallet"); 
		result.next(); 
		int wallet_id = result.getInt("max") + 1;
		PreparedStatement walletS = conn.prepareStatement("INSERT INTO Wallet (User_id,Wallet_name) VALUES (?,?)");
		walletS.setInt(1, user_id);
		walletS.setString(2, wallet_name);
		walletS.executeUpdate();

		tempw = new Wallet(wallet_id,user_id,wallet_name);
		
		wallets.add(tempw);
		
		result = S.executeQuery("SELECT * FROM Wallet");
		dump(result,"Added New Wallet");
	} 

	//Function that Updates a Wallet Pass to it THE USER_ID and WALLET_NAME
	public void updateWallet(Integer user_id, String wallet_name) throws SQLException{
		PreparedStatement updateW = conn.prepareStatement("UPDATE Wallet SET Wallet_name = ? WHERE User_id = ?"); 
		updateW.setString(1,wallet_name); 
		updateW.setInt(2,user_id);
		updateW.executeUpdate();

		result = S.executeQuery("SELECT * FROM Category");
		dump(result,"Updated Wallet");
	}

	//Function that Delete a Wallet Pass to it THE USER_ID and WALLET_NAME
	public void deleteWallet(Integer user_id, String wallet_name) throws SQLException{
		PreparedStatement updateW = conn.prepareStatement("DELETE FROM Wallet WHERE User_id = ? AND Wallet_name = ?"); 
		
		updateW.setInt(1,user_id);
		updateW.setString(2,wallet_name); 
		updateW.executeUpdate();

		result = S.executeQuery("SELECT * FROM Category");
		dump(result,"Updated Wallet");
	}
	
	//Function that gets a Wallet from the Databse, Creates the Object Wallet and adds it to the ARray Pass to it THE USER_ID 
//		public static ObservableList<Wallet> getWallets(Integer id) throws SQLException, ClassNotFoundException{
//			S = conn.createStatement();
//		ObservableList<Wallet> walletList = FXCollections.observableArrayList();
//		Wallet tempw = new Wallet();
//		PreparedStatement walletS = conn.prepareStatement("SELECT * FROM Wallets WHERE User_id = ?");
//		walletS.setInt(1, id);
//		result = walletS.executeQuery();
//		while(result.next()){
//			tempw.setWallet_id(result.getInt("Wallet_id"));
//			tempw.setUser_id(result.getInt("User_id"));
//			tempw.setWallet_name(result.getString("Wallet_name"));
//			walletList.add(tempw);
//		}
//		return walletList;
//	}
	
	public static void getWallets(Integer id) throws SQLException, ClassNotFoundException{

	Wallet tempw = new Wallet();
	PreparedStatement walletS = conn.prepareStatement("SELECT * FROM Wallet WHERE User_id = ?");
	walletS.setInt(1, id);
	result = walletS.executeQuery();
	while(result.next()){
		tempw.setWallet_id(result.getInt("Wallet_id"));
		tempw.setUser_id(result.getInt("User_id"));
		tempw.setWallet_name(result.getString("Wallet_name"));
		System.out.println("Wallet Name  " + tempw.getWallet_name());
		walletList.add(tempw);
	}
}

	

	//Function to get the Summary Amount for the YEAR , LASTMONTH , THISMONTH 
	public void getAmounts(Integer wallet_id) throws SQLException{
		Double amnt, year_income = 0.0, year_exp = 0.0, year_bal = 0.0, lmonth_income = 0.0, lmonth_exp = 0.0, lmonth_bal = 0.0, month_income = 0.0, month_exp = 0.0, month_bal = 0.0;
		LocalDate date = LocalDate.now();
		DateTimeFormatter yf = DateTimeFormatter.ofPattern("yyyy");
		String year = "%" + yf.format(date);
		DateTimeFormatter mf = DateTimeFormatter.ofPattern("MM/yyyy");
		String month = "%" + mf.format(date);
		LocalDate lastdate = LocalDate.now().minusMonths(1);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/yyyy");
		String lmonth = "%" + df.format(lastdate);
		
		PreparedStatement getamnt = conn.prepareStatement("SELECT Amount FROM Transactions Where wallet_id = ? AND t_date = ?");
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
				
			
				
			
		}
	
	//Create a New Transaction and Insert it into the Database 
		//You pass to it USER_ID, WALLET_ID , TYPE(I/E), CATEGORY(NAME), AMOUNT, TRANSACTION_DATE, NOTE
		public void createTransaction(Integer user_id, Integer wallet_id, String type, String category, Double amount, LocalDate t_date, String note ) throws SQLException{
			result = S.executeQuery("SELECT MAX(Transaction_id) AS max From Transactions"); 
			result.next(); 
			int transaction_id = result.getInt("max") + 1;
			Byte one = 1 , zero = 0;
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String date = df.format(t_date);
			Transaction tempt = new Transaction(transaction_id,user_id,wallet_id,type,category,amount,t_date,note);
			PreparedStatement newTrans = conn.prepareStatement("INSERT INTO Transactions(user_id,wallet_id,type,category,amount,t_date,note) VALUES (?,?,?,?,?,?,?)");
			newTrans.setInt(1, user_id); 
			newTrans.setInt(2, wallet_id);
			if(type.equalsIgnoreCase("Income"))
				newTrans.setByte(3, one);
			else 
				newTrans.setByte(3, zero);
			newTrans.setString(4, category); 
			newTrans.setDouble(5, amount); 
			newTrans.setString(6, date); 
			newTrans.setString(7, note);
			newTrans.executeUpdate();
			transactions.add(tempt);
			newTrans.close();
			
			S = conn.createStatement();
			result = S.executeQuery("SELECT * FROM Transactions");
			dump(result,"NEW TRANSACTIONS ADDED");
			
		}
	//Gets all the Transacrions that have been saved in the Database for the Given User_id and Waller_id. IT clears and Refreshes the transactions array on each call. 
	public void getTransactions(Integer user_id, Integer wallet_id) throws SQLException{
		transactions.clear();
		Transaction tempt = new Transaction();
		Byte type ;
		PreparedStatement getTrans = conn.prepareStatement("SELECT * FROM Transactions WHERE User_id = ? AND Wallet_id = ? ORDER BY T_date ");
		getTrans.setInt(1,user_id); 
		getTrans.setInt(2,wallet_id);
		result = getTrans.executeQuery();
		while(result.next()){
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
			transactions.add(tempt);
		}
	}	
		
	public void editTransactions(Integer transaction_id, Integer wallet_id, String type, String category, Double amount, LocalDate t_date, String note )throws SQLException{
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = df.format(t_date);
		Byte one = 1 , zero = 0;
		PreparedStatement editTrans = conn.prepareStatement("UPDATE Transactions "
			+"SET Wallet_id = ?, Type = ? , Category = ? , Amount = ?, T_date = ? , Note = ? WHERE Transaction_id = ?  ");
		editTrans.setInt(1, wallet_id);
		if(type.equalsIgnoreCase("Income"))
			editTrans.setByte(2, one);
		else 
			editTrans.setByte(2, zero);
		editTrans.setString(3, category); 
		editTrans.setDouble(4, amount); 
		editTrans.setString(5, date); 
		editTrans.setString(6, note);
		editTrans.setInt(7,transaction_id);
		editTrans.executeUpdate();
	}	

	public void deleteTransaction(Integer transaction_id)throws SQLException{
		PreparedStatement deleteTrans = conn.prepareStatement("DELETE FROM Transactions WHERE Transaction_id = ? "); 
		deleteTrans.setInt(1,transaction_id);
		deleteTrans.executeUpdate();
	}
	
	//Function to Creates a Category. Category can be either Income(1) or Expense (0).You pass to the Funciton Integer USER_ID , TYPE and NAME of Category 
	public void createCategory(Integer user_id, Integer type, String name) throws SQLException{
		Category tempc = new Category(user_id, type, name);
		PreparedStatement categoryS = conn.prepareStatement("INSERT INTO Category (User_id,Type,Description) VALUES (?,?,?)");
		categoryS.setInt(1, user_id);
		categoryS.setInt(2, type);
		categoryS.setString(3, name);
		categoryS.executeUpdate();
		
		result = S.executeQuery("SELECT * FROM Category");
		dump(result,"Added New Category");
		
		
		types.add(tempc);
	}
	
	public void getCategory(Integer user_id) throws SQLException{
		//Initialzing type with predfined Categories. 
		Category tempc = new Category();
		PreparedStatement walletS = conn.prepareStatement("SELECT * FROM Category WHERE User_id = ?");
		walletS.setInt(1, user_id);
		result = walletS.executeQuery();
		while(result.next()){
			tempc.setUser_id(result.getInt("User_id"));
			tempc.setType(result.getInt("Type"));
			tempc.setDescription(result.getString("Description"));
			types.add(tempc);
		}

		for(int i = 0; i < types.size(); i++)
		{
			System.out.println("Category NO. " + i + "\n"
								+ "UserID " + types.get(i).getUser_id() + "\n"
								+ "Type " + types.get(i).isType() + "\n"
								+ "Description " + types.get(i).getDescription() +"\n"
								+"________________________________________________________________");
		}
	}
	
	//Function that Edits the entry of Category in the Database.You can only Edit User Defined Categories and Not Programmer Defined. 
	public void editCategory(Integer user_id, Byte type, String category_name)throws SQLException{
		PreparedStatement updateCat = conn.prepareStatement("UPDATE Category SET Type = ? , Description = ? WHERE User_id = ?"); 
		updateCat.setByte(1,type);
		updateCat.setString(2,category_name); 
		updateCat.setInt(3,user_id);
		updateCat.executeUpdate();

		result = S.executeQuery("SELECT * FROM Category");
		dump(result,"Updated Category");
	}

	//Function that deltes a CAtegory again only User-defined CAtegories can be deleetd. Pass to it User_id and CATEGORY_NAME
	public void deleteCategory(Integer user_id, String category_name) throws SQLException{
		PreparedStatement deleteCat = conn.prepareStatement("DELETE FROM Category WHERE User_id = ? AND Description = ?"); 
		
		deleteCat.setInt(1,user_id);
		deleteCat.setString(2,category_name); 
		deleteCat.executeUpdate();

		result = S.executeQuery("SELECT * FROM Category");
		dump(result,"Delted Category");
	}
	
	
	// public void setWallets(Integer id, String name) throws SQLException{
	// 	Wallet tempw = new Wallet();
	// 	PreparedStatement walletS2 = conn.prepareStatement("SELECT * FROM Wallet Where User_id = ? AND Wallet_name = ?");
	// 	walletS2.setInt(1, id);
	// 	walletS2.setString(2, name);
	// 	result = walletS2.executeQuery();
	// 	result.next();
	// 	tempw.setId(result.getInt("Wallet_id"));
	// 	tempw.setUser_id(result.getInt("User_id"));
	// 	tempw.setName(result.getString("Wallet_name"));
	// 	walletS2.close();
	// 	wallets.add(tempw);
	// }
	
	public boolean login(String username, String password) throws SQLException{
		S = conn.createStatement();
		result = S.executeQuery("SELECT MAX(User_id) AS max From User"); 
		result.next(); 
		Integer user_id = result.getInt("max") + 1;	
			PreparedStatement loginS = conn.prepareStatement("SELECT COUNT(*) AS numofid FROM User WHERE Username = ? AND Password = ?");
			loginS.setString(1, username);
			loginS.setString(2, password);
			result = loginS.executeQuery();
			result.next();
			String ans = result.getString("numofid");
			if(ans.equalsIgnoreCase("1"))
			{
				return true;
				User newuser = new User(user_id, username, password);
			}
			loginS.close();
			return false;
	}

	public void setUser(String username, String password) throws SQLException{
		PreparedStatement loginS2 = conn.prepareStatement("SELECT * FROM User WHERE Username = ? AND Password = ?");
		loginS2.setString(1, username);
		loginS2.setString(2, password);
		result = loginS2.executeQuery();
		result.next();
		user.setId(result.getInt("User_id"));
		user.setUsername(result.getString("Username"));
		user.setPassword(result.getString("Password"));
		user.setEmail(result.getString("Email"));
		loginS2.close();
	}
	public boolean signup(String username, String password, String passwordc, String email) throws SQLException{
		if(!password.equals(passwordc)){
			System.out.println("The Password Entered does not match");
			return false;
		}
		result = S.executeQuery("SELECT MAX(User_id) AS max FROM User"); 
		result.next();
		int user_id = result.getInt("max") + 1;
		PreparedStatement signupS = conn.prepareStatement("INSERT INTO User(Username,Password,Email) VALUES (?,?,?)");
		signupS.setString(1, username);
		signupS.setString(2, password);
		signupS.setString(3, email);
		signupS.executeUpdate();
		user = new User(user_id, username, password, email);

		result = S.executeQuery("SELECT * FROM User");
		dump(result,"Added New User");
		
		
		return true;
	}
	
	private static void dump(ResultSet rs,String exName)
			throws SQLException {
		System.out.println("-------------------------------------------------");
		System.out.println();
		System.out.println(exName+" result:");
		System.out.println();
		while (rs.next()) {
			System.out.print("| ");
			int j=rs.getMetaData().getColumnCount();
			for (int i = 1; i <=j ; ++i) {
				Object o = rs.getObject(i);
				System.out.print(o + " | ");
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public static void main(String[] Args){
		try{
		//Load the jdcb-sql(odcb) bridge driver
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		
		//Enable Logging 
		DriverManager.setLogWriter(new PrintWriter((System.err)));
		
		//Get the Connection to the Database 
		conn=DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Taha/Desktop/UCanAccess/" + dbase);
		System.out.println("Database Connected");
		conn.setAutoCommit(false); //To commit changes we have to call conn.commit()
		
		S = conn.createStatement();
		
		//create an Instance of this class and call it. 
		Access ac = new Access();
		//Call the go function in this class which acts as the primary function. 
		//ac.go(Args);
		
		Access.getWallets(2);
		for(int i = 0; i < walletList.size(); i++){
			System.out.println(walletList.get(i).getWallet_id());
			System.out.println(walletList.get(i).getUser_id());
			System.out.println(walletList.get(i).getWallet_name());
		}
		
		
//		LocalDate t_date = LocalDate.now();
//		int d,m,y; 
//		d = t_date.getDayOfMonth();
//		m = t_date.getMonthValue() - 1; 
//		y = t_date.getYear();
//		//LocalDate a_date = LocalDate.parse("24/06/2016", df);
//		Calendar cal = Calendar.getInstance();
//		cal.set(y, m, d);
//		Date date = new Date(cal.getTimeInMillis());
//		System.out.println(d+"/"+m+"/"+y);
		
//		t_date.minusMonths(1);
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
//		PreparedStatement p = conn.prepareStatement("INSERT INTO dtrial(date) VALUES (?) ");
//		p.setDate(1, date);
//		p.executeUpdate();
//		
//		S = conn.createStatement();
//		result = S.executeQuery("SELECT * FROM dtrial");
//		dump(result,"DATE TRIAL");
		
//		System.out.println(date);
		//System.out.println(a_date);
		}catch(ClassNotFoundException e){
			System.out.println("Cant load Driver" + e);
		}catch (SQLException e){
			System.out.println("Database access failed" + e);
		}
	}
}

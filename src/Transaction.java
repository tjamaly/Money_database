import java.sql.Date;
import java.time.LocalDate;

public class Transaction {
	Integer transaction_id;
	Integer user_id; 
	Integer wallet_id; 
	Byte type;
	String category; 
	Double amount; 
	LocalDate t_date; 
	String note;
	
	public Transaction(){	
	}
	
	public Transaction(Integer transaction_id,Integer user_id,Integer wallet_id,Byte type,String category,Double amount,LocalDate t_date2, String note ){
		this.transaction_id = transaction_id; 
		this.user_id = user_id;
		this.wallet_id = wallet_id;
		this.type = type;
		this.category = category;
		this.amount = amount;
		this.t_date = t_date2;
		this.note = note;
	}
	
	public Integer getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Integer transaction_id) {
		this.transaction_id = transaction_id;
	}
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getWallet_id() {
		return wallet_id;
	}

	public void setWallet_id(Integer wallet_id) {
		this.wallet_id = wallet_id;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getT_date() {
		return t_date;
	}

	public void setT_date(LocalDate t_date) {
		this.t_date = t_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	

}

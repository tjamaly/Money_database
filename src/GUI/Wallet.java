package GUI;

public class Wallet {

Integer wallet_id; 
Integer user_id;
String wallet_name;

public Wallet(Integer wallet_id, Integer user_id, String wallet_name){
	this.wallet_id = wallet_id; 
	this.user_id = user_id; 
	this.wallet_name = wallet_name; 
}

public Wallet() {
}

public Integer getWallet_id() {
	return wallet_id;
}

public void setWallet_id(Integer wallet_id) {
	this.wallet_id = wallet_id;
}
public Integer getUser_id() {
	return user_id;
}
public void setUser_id(Integer user_id) {
	this.user_id = user_id;
}
public String getWallet_name() {
	return wallet_name;
}

public void setWallet_name(String wallet_name) {
	this.wallet_name = wallet_name;
}
}

package GUI;

public class Category {
Integer User_id; 
Integer type; 
String description;

public Category(){	
}
public Category( Integer uid, Integer type, String description){
	User_id = uid; 
	this.type = type; 
	this.description = description; 
}
public Integer getUser_id() {
	return User_id;
}
public void setUser_id(Integer user_id) {
	User_id = user_id;
}
public Integer isType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
}

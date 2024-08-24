package model;

public class User {
	private String fullname="";
	private String email;
	private String password;
	boolean role = false;
	
	public User(){}
	public User(String email, String password){
		this.email = email;
		this.password = password;
	}
	public User(String fullname, String email, String password){
		this(email, password);
		this.fullname = fullname;
	}
	public User(String fullname, String email, String password, boolean role){
		this(fullname,email,password);
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public boolean getRole() {
		return this.role;
	}
	@Override 
	public String toString() {
		return "Username: "+ this.getFullname() + " email:" + this.getEmail() + " password: " + getPassword();
	}
}

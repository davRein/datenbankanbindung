package de.dreinerts.databaseconnection;

public class User {
	private int nId;
	private String strUsername = "", strPwd = "";
	
	public User(int nId, String strUsername, String strPwd) {
		this.nId = nId;
		this.strUsername = strUsername;
		this.strPwd = strPwd;
	}
	
	public int getnId() {
		return nId;
	}
	public void setnId(int nId) {
		this.nId = nId;
	}
	public String getStrUsername() {
		return strUsername;
	}
	public void setStrUsername(String strUsername) {
		this.strUsername = strUsername;
	}
	public String getStrPwd() {
		return strPwd;
	}
	public void setStrPwd(String strPwd) {
		this.strPwd = strPwd;
	}
	
}

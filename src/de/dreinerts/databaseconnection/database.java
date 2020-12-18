package de.dreinerts.databaseconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.ds.PGSimpleDataSource;

public class database {
	private static final String TABLENAME = "public.\"user\"";

	private List<User> userList = new ArrayList<User>();

	// Constructor private because of Singleton pattern
	private database() {
	}

	private static database _instance = new database();

	// Returns an instance of the database class
	public static database getInstance() {
		if (_instance == null) {
			_instance = new database();
		}
		return _instance;
	}

	// Variable declaration
	private PGSimpleDataSource source;

	// Initializes the database with connection url, username and password
	public void initDb() {
		String url = "jdbc:postgresql://localhost:5432/learnfix";
		String strUser = "dreinerts";
		String strPwd = "mnidr1982!";

		source = new PGSimpleDataSource();
		source.setURL(url);
		source.setUser(strUser);
		source.setPassword(strPwd);
	}

	// Insert data to the database
	public void insert(String stmt) {
		try (Connection con = source.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(stmt);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Returns all records from a table
	public void selectAll() {
		String strSelectStmt = "SELECT * FROM " + TABLENAME;
		try (Connection con = source.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(strSelectStmt);
			ResultSet rs = pstm.executeQuery();
			
			if(!rs.next()) {
				System.out.println("Table is empty");
			} else {
				while (rs.next()) {
					// System.out.println(rs.getInt(1) + " : " + rs.getString(2) + " : " +
					// rs.getString(3));
					userList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(2)));
				}
				for (User user : userList) {
					System.out.println("ID: " 
							+ user.getnId() + "-> LOGIN: " 
							+ user.getStrUsername() + "-> PWD: "
							+ user.getStrPwd());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Returns a resultset from the table
	public boolean selectAny(String stmt) {
		try (Connection con = source.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(stmt);
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {
				userList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(2)));
			}
			
			if(userList.isEmpty()) {
				return false;
			} else {
				printList();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	
	public boolean exist(String stmt) {
		try(Connection con = source.getConnection()){
			PreparedStatement pstm = con.prepareStatement(stmt);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			if(rs.getInt(1) == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void update(String stmt) {
		try(Connection con = source.getConnection()){
			PreparedStatement pstm = con.prepareStatement(stmt);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getTablename() {
		// TODO Auto-generated method stub
		return TABLENAME;
	}
	
	private void printList() {
		for (User user : userList) {
			System.out.println("ID: " + user.getnId() + "-> LOGIN: " + user.getStrUsername() + "-> PWD: " + user.getStrPwd());
		}
	}

}




























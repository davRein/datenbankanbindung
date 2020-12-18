package de.dreinerts.databaseconnection;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Gui {

	private Scanner s = new Scanner(System.in);

	public Gui() {
		database.getInstance().initDb();
		initGui();
	}

	private void initGui() {
		System.out.println("Database example using PostreSQL\n");
		System.out.println("New user\t(1) ->");
		System.out.println("Change user\t(2) ->");
		System.out.println("Delete user\t(3) ->");
		System.out.println("Show user\t(4) ->");
		System.out.println("Show all user\t(5) ->");
		System.out.println("Exit\t\t(6) ->");
		System.out.print("\nSelection: ");

		try {
			int nSelection = s.nextInt();

			switch (nSelection) {
			case 1:
				database.getInstance().insert(insertNewUser());
				break;
			case 2:
				changeUser();
				break;
			case 3:

				break;
			case 4:
				searchForUser();
				break;
			case 5:
				database.getInstance().selectAll();
				break;
			case 6:
				s.close();
				System.exit(1);
				break;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private String insertNewUser() {
		String strUsername, strPwd;
		System.out.print("Enter new username: ");
		strUsername = s.next();
		System.out.print("Enter password: ");
		strPwd = s.next();
		String stmt = "INSERT INTO " + database.getInstance().getTablename() + "(username, pwd) VALUES ('" + strUsername
				+ "','" + strPwd + "');";
		System.out.println(stmt);
		s.close();
		return stmt;
	}

	private void searchForUser() {
		String strUsername;
		System.out.print("Search for: ");
		strUsername = s.next();
		String stmt = "SELECT * FROM " + database.getInstance().getTablename() + " WHERE username='" + strUsername
				+ "';";
		if (!database.getInstance().selectAny(stmt)) {
			System.out.println("User doesnt exist. please try again!\n");
			searchForUser();
		}
	}

	private void changeUser() {
		System.out.println("username\t (1)");
		System.out.println("password\t (2)");
		System.out.println("both\t\t (3)");
		System.out.println("go back\t\t (4)");
		
		try {
			int nSelection = s.nextInt();
			switch(nSelection) {
			case 1:
				changeUsername();
				break;
			case 2:
				changePassword();
				break;
			case 3:
	
				break;
			case 4:
				initGui();
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println(e);
		}
		
	}
	
	private void changeUsername() {
		String strOldUsername, strNewUsername;
		System.out.println("Enter old username: ");
		strOldUsername = s.next();
		System.out.println("Enter new username: ");
		strNewUsername = s.next();
		if(!strNewUsername.equalsIgnoreCase(strOldUsername)) {
			String stmt_checkIfExist = "SELECT COUNT(1) FROM " + database.getInstance().getTablename() + " WHERE username='" + strNewUsername + "';";
			if(database.getInstance().exist(stmt_checkIfExist)) {
				String stmt_updateUsername = "UPDATE " + database.getInstance().getTablename() + " SET username='" + strNewUsername + "' WHERE username='" + strOldUsername + "'";
				database.getInstance().update(stmt_updateUsername);
			}else {
				System.out.println("Username doesn't exist. Please choose a new one.");
				changeUsername();
			}
		}else {
			System.out.println("Please choose a username different from the previous one!");
			changeUsername();
		}
	}
	
	private void changePassword() {
		String strUsername, strPassword;
		System.out.println("Enter old username: ");
		strUsername = s.next();
		System.out.println("Enter new password");
		strPassword = s.next();
		String stmt_checkIfExist = "SELECT COUNT(1) FROM " + database.getInstance().getTablename() + " WHERE username='" + strUsername + "';";
		if(database.getInstance().exist(stmt_checkIfExist)) {
			String stmt_updatePassword = "UPDATE " + database.getInstance().getTablename() + " SET pwd='" + strPassword + "' WHERE username='" + strUsername + "'";
			database.getInstance().update(stmt_updatePassword);
		}else {
			System.out.println("Username doesn't exist.");
			initGui();
		}
	}
}











































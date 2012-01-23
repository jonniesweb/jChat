package database;

import message.LoginUser;
import message.User;
import server.UserAccount;


public class DatabaseConnection {
	
	private String address;


	/**
	 * @param server The main server class of the chat program
	 * @param address The address of the server. Example: "jdbc:hsqldb:hsql://localhost/jchat"
	 */
	public DatabaseConnection(String address) {
		this.address = address;
		
	}

	public boolean isUserInDatabase(LoginUser login) {
		
		// temporary testing purposes only
		return true;
		
	}
	
	
}

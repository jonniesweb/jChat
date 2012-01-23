/* Jonnie Simpson
 * Humberview S.S.
 * ICS 3U0, Created on 2011-01-07
 * ServerThread.java
 * --------------------------------------------
 * This class is used for processing the
 * Individual connections to the clients. This
 * class is threaded multiple times depending
 * on how many users are connected to the
 * server.
 * --------------------------------------------
 */

// package to segment the client and server portions
package server;

// various imports
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import message.LoginUser;
import message.Message;
import message.RegisterUser;
import message.User;

import common.ContentContainer;
import database.DatabaseConnection;



// extend thread so that this class can be threaded
public class ServerThread extends Thread {
	// define server from Server.java
	private Server server;

	// define a socket
	private Socket socket;
	private UserAccount userAccount;
	private DatabaseConnection databaseConnection = Server.databaseConnection;
			
	
	// Set up this class' constructor and then start the threaded portion
	public ServerThread( Server server, Socket socket, UserAccount userAccount ) {

		// take the passed server and socket and set it into these variables
		this.server = server;
		this.socket = socket;
		this.userAccount = userAccount;

		//Start the thread
		start();
	}

	// get messages from the client and tell Server.java
	public void run() {
		try {

			// create a datainputstream so that we can receive the data from the client
			ObjectInputStream input = new ObjectInputStream( socket.getInputStream() );

			// loop forever, until the client disconnects
			while (true) {

				// TODO: comment this

				ContentContainer objMessage;

//				objMessage = (ContentContainer) input.readObject();
				
				Object obj = input.readObject();
				
				if (obj.getClass() == Message.class.getClass()) {
					handleMessage((Message) obj);
				} else if (obj.getClass() == LoginUser.class.getClass()) {
					handleLogin((LoginUser) obj);
				} else if (obj.getClass() == User.class.getClass()) {
					handleUser((User) obj);
				} else if (obj.getClass() == RegisterUser.class.getClass()) {
					
				} else {
					System.out.println("Unknown message type recieved, dropped message");
				}


//				if (objMessage.getContentType() == 0) {
//					System.out.println("Recieved empty ContentContainer from ObjectInputStream");
//				} else if (objMessage.getContentType() == 1) {
//					server.sendToAll(objMessage);
//				} else if (objMessage.getContentType() == 2) {
//					// TODO: deal with receiving usernames
//				} else {
//					System.out.println("Unhandled ContentContainer content type");
//				}

			} 

			// handle exceptions
		} catch( EOFException ie ) {
			System.out.println("Client " + socket.getInetAddress().getHostName() + "/" + socket.getInetAddress().getHostAddress() + " disconnected");
		} catch( IOException ie ) {
			ie.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found error reading ContentContainer from ObjectInputStream");
		} finally {

			// once the client closes the connection pass the socket to server.removeConnection
			server.removeConnection( socket );
		}
	}

	private void handleUser(User user) {
		UserAccount userAccount = (UserAccount) user;
		
	}

	private void handleLogin(LoginUser login) {
		
		if(databaseConnection.isUserInDatabase(login)) {
			userAccount.sendObject(true);
		}
		
		
	}

	private void handleMessage(Message message) {
		
		
	}
}
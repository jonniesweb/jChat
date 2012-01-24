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

import client.NetworkConnection;

import message.Disconnect;
import message.LoginUser;
import message.Message;
import message.RegisterUser;
import message.User;

import common.ContentContainer;
import database.DatabaseConnection;



// extend thread so that this class can be threaded
public class ServerThread extends Thread {
	// define server from Server class
	private Server server;

	// define a socket
	private Socket socket;
	private UserAccount userAccount;
	private DatabaseConnection databaseConnection = Server.databaseConnection;
	private NetworkConnection networkConnection;
			
	
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

//				ContentContainer objMessage;

//				objMessage = (ContentContainer) input.readObject();
				
				Object obj = input.readObject();
				
				System.out.println(obj.getClass().getCanonicalName());
				System.out.println(LoginUser.class.getCanonicalName());
				
				if (obj.getClass().getCanonicalName() == Message.class.getCanonicalName()) {
					handleMessage((Message) obj);
				} else if (obj.getClass().getCanonicalName() == Disconnect.class.getCanonicalName()) {
					handleDisconnect((Disconnect) obj);
				} else if (obj.getClass().getCanonicalName() == LoginUser.class.getCanonicalName()) {
					handleLogin((LoginUser) obj);
				} else if (obj.getClass().getCanonicalName() == User.class.getCanonicalName()) {
					handleUser((User) obj);
				} else if (obj.getClass().getCanonicalName() == RegisterUser.class.getCanonicalName()) {
					
				} else {
					System.out.println("Unknown message type recieved, dropped message");
					obj = null;
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

	private void handleDisconnect(Disconnect disconnect) {
		server.sendToAll(disconnect);
		
	}

	private void handleUser(User user) {
		UserAccount userAccount = (UserAccount) user;
		
	}

	private void handleLogin(LoginUser login) {
		
		if(databaseConnection.isUserInDatabase(login)) {
			// temporary
			userAccount.sendObject(true);
		}
		
		
	}

	private void handleMessage(Message message) {
		server.sendMessageToAll(message);
		
	}
}
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
package chatServer;

// various imports
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

// extend thread so that this class can be threaded
public class ServerThread extends Thread {
	// define server from Server.java
	private Server server;

	// define a socket
	private Socket socket;

	// Set up this class' constructor and then start the threaded portion
	public ServerThread( Server server, Socket socket ) {

		// take the passed server and socket and set it into these variables
		this.server = server;
		this.socket = socket;

		//Start the thread
		start();
	}

	// get messages from the client and tell Server.java
	public void run() {
		try {

			// create a datainputstream so that we can receive the data from the client
			DataInputStream input = new DataInputStream( socket.getInputStream() );

			// loop forever, until the client disconnects
			while (true) {

				// get the message from the client
				String message = input.readUTF();

				// pass the message to server.sendToAll
				server.sendToAll( message );
			}
			// handle exceptions
		} catch( EOFException ie ) {
			// There wont be any end of file markers
		} catch( IOException ie ) {
			ie.printStackTrace();
		} finally {
			
			// once the client closes the connection pass the socket to server.removeConnection
			server.removeConnection( socket );
		}
	}
}
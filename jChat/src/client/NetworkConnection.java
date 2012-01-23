package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.ID;

/**
 * Class to abstract the networking layer by providing ways to get the
 * socket, input stream and output stream
 * @author Jonnie Simpson
 *
 */
public class NetworkConnection extends ID {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	/**
	 * Takes the given socket and creates an ObjectInputStream and
	 * ObjectOutputStream from it 
	 * @param Socket The socket that will be used and referred to in this class
	 */
	public NetworkConnection(String uuid, Socket socket) {
		super(uuid);
		this.socket = socket;

		try {
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the socket associated with the class
	 * @return socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Get the Object Input Stream from the socket
	 * @return ObjectInputStream
	 */
	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Get the Object Output Stream from the socket
	 * @return ObjectOutputStream
	 */
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
	public void sendMessage(Object object) {
		
		try {
			outputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

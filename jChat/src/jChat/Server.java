
/* Jonnie Simpson
 * Humberview S.S.
 * ICS 3U0, Created on 2010-01-07
 * Server.java
 * --------------------------------------------
 * This file is the server portion of
 * jChatServer. It handles all of the connected
 * clients using multiple threads
 * --------------------------------------------
 */

// package to segment the client and server portions
package jChat;

// various imports
import jChat.DisplayMessage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// extend JFrame so that we can use JFrame methods easily
public class Server extends JFrame
{
	// define the serversocket
	private ServerSocket serversocket;

	// define a new hashtable
	// if it messes up, remove arguments
	private Hashtable<Socket, DataOutputStream> hashtable = new Hashtable<Socket, DataOutputStream>();

	// create a JTextArea for the programs console
	private JTextArea textarea = new JTextArea();
	
	private DisplayMessage DisplayMessage = new DisplayMessage(textarea);
	
	private Socket socket;

	// pass the port to the private method listen
	public Server( int port ) throws IOException {

		// set the window name
		super("jChatServer");

		// confgure JFrame
		setBackground(new Color(99, 99, 99));
		getContentPane().setBackground(new Color(77, 77, 77));
		getContentPane().setForeground(new Color(180, 180, 180));
		listen( port );
	}
	private void listen( int port ) throws IOException {
		
		
		addWindowListener(new java.awt.event.WindowAdapter(){

			public void windowClosing(WindowEvent winEvt) {
				
				sendToAll("The chat server has shut down");
				
				try {
					serversocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
					System.exit(0);
			}
		});

		// Configure the container
		Container container = getContentPane();
		textarea.setForeground(new Color(180, 180, 180));
		textarea.setBackground(new Color(65, 65, 65));
		JScrollPane scrollPane = new JScrollPane(textarea);
		container.add(scrollPane, BorderLayout.CENTER);

		// Necessary textarea modifiers
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setEditable(false);
		textarea.setAutoscrolls(true);

		// configure the panel for the header text
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setForeground(new Color(180, 180, 180));
		panel.setBackground(new Color(77, 77, 77));

		// set up the JLabel for the header text
		JLabel lblJchatserverBy = new JLabel("jChatServer - By Jonnie Simpson");
		lblJchatserverBy.setFont(new Font("Tahoma", lblJchatserverBy.getFont().getStyle() | Font.BOLD, lblJchatserverBy.getFont().getSize()));
		lblJchatserverBy.setForeground(new Color(180, 180, 180));
		lblJchatserverBy.setBackground(new Color(64, 64, 64));
		panel.add(lblJchatserverBy);

		// set the size of the window
		setSize(300, 500);

		// Display the window after configuring
		setVisible(true);


		try {
			// create the serversocket on the specified port
			serversocket = new ServerSocket( port );
		} catch (IOException e) {
			DisplayMessage.PrintMessage("Error reserving port");
			e.printStackTrace();

		}

		// display to the console  what port its listening on
		DisplayMessage.PrintMessage("Listening on "+serversocket);

		// accept connections forever
		while (true) {

			// accept incoming connections
			socket = serversocket.accept();

			// tell the console that a client connected
			DisplayMessage.PrintMessage("Connected to "+ socket.getInetAddress().getHostName());

			// create a output stream to communicate with the client
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			// send necessary headers
			output.flush();

			// test welcome message
			output.writeUTF("Welcome to the Server!");

			// put this stream in a hashtable so that it can be referenced quickly
			hashtable.put( socket, output );


			// pass the method and serversocket to the ServerThread class
			new ServerThread( this, socket );
		}
	}

	// return everything in the hashtable
	private Enumeration getOutputStreams() {
		return hashtable.elements();
	}
	// take the passed message and send it to all clients
	void sendToAll( String message ) {

		// piggyback off this method to display it to the console
		DisplayMessage.PrintMessage(message);


		// don't send any messages that start with a /
		if (!message.startsWith("/")) {

			// only allow one method at a time to access this so that data doesn't get corrupt
			synchronized( hashtable ) {
				// do the following for each entry in the hashtable
				for (Enumeration enumeration = getOutputStreams(); enumeration.hasMoreElements(); )
				{
					// get the output stream for the socket in the hashtable
					DataOutputStream output = (DataOutputStream)enumeration.nextElement();

					try {
						// send the message to the client
						output.writeUTF( message );
					} catch( IOException ie ) {
						DisplayMessage.PrintMessage("Error sending message" + ie); 
					}
				}
			}
		} // end if
	}

	// called by the runnable in ServerThread.java
	void removeConnection( Socket socket ) {

		// only allow one method at a time to access this so that data doesn't get corrupt
		synchronized(hashtable) {

			// print to the console that its removing the connection
			DisplayMessage.PrintMessage("Removing connection to " + socket.getInetAddress().getHostName());

			// remove the socket from the hashtable so that data doesn't get sent to it
			hashtable.remove(socket);

			try {
				// close the socket
				socket.close();
			} catch( IOException ie ) {
				DisplayMessage.PrintMessage("Error closing " + socket.getInetAddress().getHostName());
				ie.printStackTrace();
			}
		}
	}

	// main method thats run first
	static public void main( String args[] ) {

		// use port #1337
		int port = 1337;

		// create a new applicatoin
		Server application = null;

		try {
			// start up the server and pass the port number
			application = new Server(port);
		} catch (IOException e) {
			System.out.println("Encountered an error starting the server application.");
		}

		// when the exit button is pressed tell JFrame to close
		//		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public class ContentContainer implements Serializable {
		
		private int contentType = 0;
		private int senderID = 0;
		private String messageText = null;
		private static final String programVer = "1.0";
		private String username = null;

		public void setMessage(int senderID, String messageText) {
			contentType = 1;
			this.senderID = senderID;
			this.messageText = messageText;
			
		}
		
		public void setUsername(int senderID, String username) {
			contentType = 2;
			this.senderID = senderID;
			this.username = username;
		}
		
		public String getText() {
			return messageText;
		}
		
		public int getContentType() {
			return contentType;
		}
		
		
	}
	
	
}
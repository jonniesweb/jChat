/* Jonnie Simpson
 * Humberview S.S.
 * ICS 3U0, Created on 2011-01-07
 * Client.java
 * --------------------------------------------
 * jChatClient - the client portion to the
 * jChatServer. This allows the clients to
 * communicate with other clients through the
 * server.
 * --------------------------------------------
 */

//package to segment the client and server portions
package client;

// Various imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

import common.ContentContainer;
import common.DisplayMessage;

// extend JFrame so that we can access its methods
// implement Runnable so that we can have threaded methods
public class Client extends JFrame implements Runnable {
	// define some Swing components
	private JPanel panel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();
	private JTextField textfield = new JTextField();
	private InputBox inputBox;
	


	// define socket connecting to the server
	private Socket socket;

	// define streams to communicate with the server
	private ObjectOutputStream output;
	private ObjectInputStream input;

	// create a random number between 0 and 100 to create a random username
	private Random randomgenerator = new Random();
	private int rndid = randomgenerator.nextInt(101);
	private String username = Integer.toString(rndid);

	// create a styled document for the jtextpane
	private StyledDocument document = new DefaultStyledDocument();
	private JTextPane textPane = new JTextPane(document);
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mntmExit = new JMenuItem("Exit");
	private final JMenuItem mntmChangeUsername = new JMenuItem("Change Username...");
	private final JMenu mnMiscellaneous = new JMenu("Miscellaneous");
	private final JMenuItem mntmPlayUt = new JMenuItem("Play UT2004");
	
	// define DisplayMessage to handle object messages
	private DisplayMessage displayMessage = new DisplayMessage(textPane, document);
	
	// define the logger
	private final static Logger log = Logger.getLogger(Client.class.getName());
	
	
	public Client(String host) {
		// call the superclass constructor and pass it the window title we want
		// to give, setTitle() could have easily been used
		super("jChatClient - Created by Jonnie Simpson");

		// when the user enters information in the text box and presses enter
		// the message is passed to processMessage
		textfield.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processMessage(e.getActionCommand());
			}
		});

		// set up the content pane
		Container container = getContentPane();
		getContentPane().setBackground(new Color(77, 77, 77));
		getContentPane().setForeground(new Color(180, 180, 180));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set up the textfield
		textfield.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textfield.setForeground(new Color(180, 180, 180));
		textfield.setBackground(new Color(65, 65, 65));
		setBounds(100, 100, 0, 0);

		// set up the textpane
		textPane.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textPane.setForeground(new Color(180, 180, 180));
		textPane.setBackground(new Color(65, 65, 65));
		textPane.setEditable(false);

		// put the textpane in the scrollpane
		scrollPane.setViewportView(textPane);

		// set up the panel
		panel.setForeground(new Color(180, 180, 180));
		panel.setBackground(new Color(77, 77, 77));
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		container.add(scrollPane, BorderLayout.CENTER);
		container.add(textfield, BorderLayout.SOUTH);

		// set the window size
		setSize(300, 500);

		setJMenuBar(menuBar);

		// menu option "exit" action listener
		menuBar.add(mnFile);
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.info("Exiting Program via menu");
				System.exit(0);
			}
		});

		final ActionListener actionUsernameSubmit = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				username = inputBox.getTextField();
				inputBox.dispose();
				log.info("Set username to: " + username);
			}
		};

		// menu item change username action listener
		mntmChangeUsername.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputBox = new InputBox();
				inputBox.setBodyText("Please enter your new username");
				inputBox.createDialog(actionUsernameSubmit, null);
				
			}
		});

		mnFile.add(mntmChangeUsername);

		mnFile.add(mntmExit);
		
		menuBar.add(mnMiscellaneous);
		
		// If the client machine is one belonging to Humberview they
		// have the ability to play UT2004
		mntmPlayUt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// start in windowed because I'm fly like that
				String cmd = "M:\\circus_4\\byp\\System\\UT2004.exe -windowed";
				Runtime run = Runtime.getRuntime();
				try {
					run.exec(cmd);
					log.info("Started UT2004");
					
					// If the computer isn't able to locate/start the program, 
					// print an error to the screen
				} catch (IOException e1) {
					displayMessage.PrintMessage("Unable to start UT2004");
					log.warning("Unable to start UT2004");
				}
				
			}
		});
		
		mnMiscellaneous.add(mntmPlayUt);

		// show the window
		setVisible(true);

		// Connect to the server
		try {
			// Initiate the connection
			socket = new Socket(host, 1337);
			// If connected tell the user it was successful
			displayMessage.PrintMessage("Connected to: "
					+ socket.getInetAddress().getHostName() + " on port: "
					+ socket.getPort());

			// create the input and output streams to communicate with the
			// server
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			
			log.info("Connected to: " + socket.getInetAddress().getHostName());
			
			// start this thread in the background
			new Thread(this).start();
		} catch (IOException ioException) {
			// if the server isn't up or other errors
			displayMessage.PrintMessage("Error connecting to server. IP address is invalid or the server is not running");
			log.severe("Unable to connect to Server. Host or network not up");
		}
	}

	// Gets called when the user types something
	private void processMessage(String message) {
		try {
			// Make sure the message isn't blank or more than 1000 characters
			if (!message.equals("")) {
				if ((message.length() <= 1000)) {

					// send the message to the server
					ContentContainer objMessage = new ContentContainer(rndid);
					
					objMessage.setUsername(username);
					objMessage.setMessage(message);
					
					output.writeObject(objMessage);
					
					log.info("Sent message: " + objMessage.getMessageText());

					// set the username if it starts with /username
					if (message.startsWith("/username")) {

						// make sure the username isn't longer than 15
						// characters
						if (message.substring(10).length() < 15) {

							// set the username and remove colons
							username = message.substring(10).replaceAll(":", " ");
						} else {
							displayMessage.PrintMessage("That username is too long!");
						} 
					} 
				} else {
					log.finer("Message is longer than 1000 characters");
				}
			} else {
				log.finer("Message is blank");
			}

			// Clear out text input field
			textfield.setText("");

		} catch (IOException ioException) {
			displayMessage.PrintMessage("Error: " + ioException);
			log.warning("Error sending message to Server: " + ioException);
		}
	}

	// background thread to receive and print messages
	public void run() {
		try {
			// Receive messages until program closed
			while (true) {
				
				ContentContainer objMessage;
				
				try {
					objMessage = (ContentContainer) input.readObject();


					if (objMessage.getContentType() == 0) {
						System.out.println("Recieved empty ContentContainer from ObjectInputStream");
						
					} else if (objMessage.getContentType() == 1) {
						displayMessage.PrintMessage(objMessage);
						log.info("Recieved message: " + objMessage.getMessageText());
						
					} else if (objMessage.getContentType() == 2) {
						// TODO: deal with receiving usernames
						
					} else {
						displayMessage.PrintMessage("Please get the newest version of JChatClient at bit.ly/jchatserver");
						System.out.println("Unhandled ContentContainer content type");
					}

				} catch (ClassNotFoundException e) {
					System.out.println("Class not found error reading ContentContainer from ObjectInputStream");
					log.warning("Unable to read message sent from server");
				}


			}
		} catch (IOException ioexception) {
			displayMessage.PrintMessage("Error: " + ioexception);
			log.warning("IOExecption: " + ioexception);
		}
	}

	// main method for testing purposes - normally never called
	public static void main(String[] args) {

		new Client("localhost");
		log.info("Attempting to connect to localhost");

	}
}
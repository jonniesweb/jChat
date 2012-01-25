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
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.swing.JButton;
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

import message.Disconnect;
import message.ID;
import message.Message;
import message.User;

import common.DisplayMessage;


// extend JFrame so that we can access its methods
// implement Runnable so that we can have threaded methods
public class Client extends JFrame implements Runnable {
	// define some Swing components
	private JPanel panel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();
	private JTextField textfield = new JTextField();
	private InputBox inputBox;

	private Map<ID, User> map = Collections.synchronizedMap(new HashMap<ID, User>());

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
	private User user;

	// define the logger
	private final static Logger log = Logger.getLogger(Client.class.getName());
	private JPanel userListPanel = new JPanel();
	private JButton btnAddUserClass;
	private final JMenu mnHelp = new JMenu("Help");
	private final JMenuItem mntmAbout = new JMenuItem("About");





	public Client(final User user, final NetworkConnection networkConnection) {
		// call the superclass constructor and pass it the window title we want
		// to give, setTitle() could have easily been used
		super("jChat Client - Created by Jonnie Simpson");
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		this.user = user;

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

		// setup adding the ActiveUserList panel to this JFrame
		getContentPane().add(userListPanel, BorderLayout.EAST);
		userListPanel.setLayout(new BorderLayout(0, 0));
		userListPanel.add(new ActiveUserList(), BorderLayout.CENTER);


		btnAddUserClass = new JButton("add user class");
		btnAddUserClass.addActionListener(new BtnAddUserClassActionListener());
		userListPanel.add(btnAddUserClass, BorderLayout.SOUTH);


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
		setSize(400, 500);

		// set up the menu bar
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.setForeground(new Color(180, 180, 180));
		menuBar.setBackground(new Color(65, 65, 65));
		setJMenuBar(menuBar);

		// menu option "exit" action listener
		menuBar.add(mnFile);
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				networkConnection.sendMessage(new Disconnect(user));
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
		mnFile.setForeground(new Color(180, 180, 180));


		// If the client machine is one belonging to Humberview they
		// have the ability to play UT2004
		mntmPlayUt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startUT2004();
			}
		});

		menuBar.add(mnMiscellaneous);
		mnMiscellaneous.add(mntmPlayUt);
		mnMiscellaneous.setForeground(new Color(180, 180, 180));

		menuBar.add(mnHelp);
		mntmAbout.addActionListener(new MntmAboutActionListener());

		mnHelp.add(mntmAbout);

		// show the window
		setVisible(true);
		
		addWindowListener(new java.awt.event.WindowAdapter(){

			public void windowClosing(WindowEvent winEvt) {
				
				networkConnection.sendMessage(new Disconnect(user));
				
				try {
					input.close();
					output.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					System.exit(0);
				}
				System.exit(0);
			}
		});

		// Initiate the connection
		this.socket = networkConnection.getSocket();
		// If connected tell the user it was successful
		displayMessage.PrintMessage("Connected to: "
				+ networkConnection.getSocket().getInetAddress().getHostName() + " on port: "
				+ networkConnection.getSocket().getPort());

		// get the input and output object streams from networkConnection
		input = networkConnection.getInputStream();
		output = networkConnection.getOutputStream();

		log.info("Connected to: " + networkConnection.getSocket().getInetAddress().getHostName());

		// adds this client to the list
		networkConnection.sendMessage(user);
		

		// start this thread in the background
		new Thread(this).start();
	}

	// Gets called when the user types something
	private void processMessage(String txtmessage) {
		try {
			// Make sure the message isn't blank or more than 1000 characters
			if (!txtmessage.equals("")) {
				if ((txtmessage.length() <= 1000)) {
					// send the message to the server
					output.writeObject(new Message(user.getStringID(), txtmessage, 0));

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

				try {
					Object obj = input.readObject();

					if (obj.getClass().getCanonicalName() == Message.class.getCanonicalName()) {
						Message message = (Message) obj;
						handleMessage(message);

					} else if (obj.getClass().getCanonicalName() == User.class.getCanonicalName()) {
						User user = (User) obj;
						UserMap.addUser(user);
					} else if (obj.getClass().getCanonicalName() == Disconnect.class.getCanonicalName()){
						handleDisconnect((Disconnect) obj);
					} else {
						System.out.println("Unhandled object recieved, dropping object");
					}


				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}



			}
		} catch (IOException ioexception) {
			displayMessage.PrintMessage("Error: " + ioexception);
			log.warning("IOExecption: " + ioexception);
		}
	}

	/**
	 * Prints the message to the screen by parsing message and looking up the
	 * usernames
	 * @param message The message to be printed to the screen
	 */
	private void handleMessage(Message message) {
		displayMessage.PrintMessage(message);
	}

	/**
	 * Removes the passed ID from the map of user
	 * @param obj The ID that you want to remove from the map
	 */
	private void handleDisconnect(Disconnect obj) {
		UserMap.remove(obj);

	}

	private void startUT2004() {
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

	// main method for testing purposes - normally never called
	public static void main(String[] args) {

		try {
			new Client(new User("testid", "testusername", null, null, 0, null, null), new NetworkConnection("testuuid", new Socket("localhost", 1337)));
		} catch (UnknownHostException e) {
			log.severe("Failed to connect to server");
			e.printStackTrace();
		} catch (IOException e) {
			log.severe("Failed to connect to server");
			e.printStackTrace();
		}
		log.info("Attempting to connect to localhost");

	}

	private class BtnAddUserClassActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ActiveUserList.addUser(new User(new ID("derp").getStringID(), "testusername" + (Math.random()*10), "bob saget", "chilling bro", 0, "horse", "the barn"));
		}
	}
	private class MntmAboutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new About();
		}
	}
}
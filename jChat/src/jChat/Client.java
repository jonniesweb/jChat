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
package jChat;

// Various imports
import java.awt.BorderLayout;
import java.awt.Button;
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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

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
	private String userid = Integer.toString(rndid);
	private String prevmessage = "";

	// create a default font for the console
	private Font font = new Font("Arial", Font.PLAIN, 12);
	private StyleContext context = new StyleContext();

	// create a styled document for the jtextpane
	private StyledDocument document = new DefaultStyledDocument();
	private JTextPane textpane = new JTextPane(document);
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mntmExit = new JMenuItem("Exit");
	private final JMenuItem mntmChangeUsername = new JMenuItem("Change Username...");
	private final JMenu mnMiscellaneous = new JMenu("Miscellaneous");
	private final JMenuItem mntmPlayUt = new JMenuItem("Play UT2004");

	public Client(String host) {
		// call the superclass constructor and pass it the window title we want
		// to give
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
		textpane.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textpane.setForeground(new Color(180, 180, 180));
		textpane.setBackground(new Color(65, 65, 65));
		textpane.setEditable(false);

		// put the textpane in the scrollpane
		scrollPane.setViewportView(textpane);

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
				System.exit(0);
			}
		});

		final ActionListener actionUsernameSubmit = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 userid = inputBox.getTextField();
				inputBox.dispose();
				
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
					
					// If the computer isn't able to locate/start the program, 
					// print an error to the screen
				} catch (IOException e1) {
					displayMessage("Unable to start UT2004");
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
			displayMessage("Connected to: "
					+ socket.getInetAddress().getHostName() + " on port: "
					+ socket.getPort());

			// create the input and output streams to communicate with the
			// server
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

			// start this thread in the background
			new Thread(this).start();
		} catch (IOException ioException) {
			// if the server isn't up or other errors
			displayMessage("Error connecting to server. IP address is invalid or the server is not running");
		}
	}

	// Gets called when the user types something
	private void processMessage(String message) {
		try {
			// Make sure the message isn't blank or more than 1000 characters
			if (!message.equals("")) {
				if ((message.length() <= 1000)) {

					// send the message to the server
					output.writeUTF(userid + ": " + message);

					// set the username if it starts with /username
					if (message.startsWith("/username")) {

						// make sure the username isn't longer than 15
						// characters
						if (message.substring(10).length() < 15) {

							// set the username and remove colons
							userid = message.substring(10).replaceAll(":", " ");
						} else {
							displayMessage("That username is too long!");
						} // end else
					} // end if
				} // end if
			} // end if

			// Clear out text input field
			textfield.setText("");

		} catch (IOException ioException) {
			displayMessage("Error:\n" + ioException);
		}
	}

	// background thread to recieve and print messages
	public void run() {
		try {
			// recieve messages until program closed
			while (true) {
				// get the message from the server
				String message = input.readUTF();

				// pass the message to displayMessage
				displayMessage(message);

			}
		} catch (IOException ioexception) {
			displayMessage("Error:\n" + ioexception);
		}
	}

	private void displayMessage(String message) {

		// remove accidental spaces before and after message
		message.trim();

		// add a line break
		message = message + "\n";

		// create an attribute set for the text style
		SimpleAttributeSet stlye = new SimpleAttributeSet();

		// if the message contains a recognized string, apply the requested
		// style and remove the command
		if (message.contains("/red")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.RED);
			message = message.replaceAll("/red", "");

		} // end if

		if (message.contains("/green")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.GREEN);
			message = message.replaceAll("/green", "");
		} // end if

		if (message.contains("/yellow")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.YELLOW);
			message = message.replaceAll("/yellow", "");
		} // end if

		if (message.contains("/blue")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.BLUE);
			message = message.replaceAll("/blue", "");
		} // end if

		if (message.contains("/magenta")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.MAGENTA);
			message = message.replaceAll("/magenta", "");
		} // end if

		if (message.contains("/cyan")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.CYAN);
			message = message.replaceAll("/cyan", "");
		} // end if

		if (message.contains("/orange")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.ORANGE);
			message = message.replaceAll("/orange", "");
		} // end if

		if (message.contains("/pink")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.PINK);
			message = message.replaceAll("/pink", "");
		} // end if

		if (message.contains("/black")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.BLACK);
			message = message.replaceAll("/black", "");
		} // end if

		if (message.contains("/white")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Foreground,
					Color.WHITE);
			message = message.replaceAll("/white", "");
		} // end if

		if (message.contains("/bold")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Bold,
					Boolean.TRUE);
			message = message.replaceAll("/bold", "");
		} // end if

		if (message.contains("/italic")) {
			stlye.addAttribute(StyleConstants.CharacterConstants.Italic,
					Boolean.TRUE);
			message = message.replaceAll("/italic", "");
		} // end if

		try {
			// print the message to the document which then goes to the textpane
			// and apply the custom style
			document.insertString(document.getLength(), message, stlye);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		// autoscroll the text
		textpane.setCaretPosition(textpane.getDocument().getLength());
	}

	// main method for testing purposes - normally never called
	public static void main(String[] args) {

		new Client("localhost");

	}
}
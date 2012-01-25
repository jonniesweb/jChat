package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import message.ID;
import message.LoginUser;
import message.RegisterUser;
import message.User;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import util.MD5;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 * Creates the Login Window so that the user can establish a connection,
 * then login or register an account with the server.
 * @author Jonnie Simpson
 *
 */
public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtLoginEmail;
	private JPasswordField txtLoginPassword;
	private JTextField txtRegisterEmail;
	private JPasswordField txtRegisterPassword;
	private JPasswordField txtRegisterPassword2;
	private JTextField txtIPAddress;
	private Socket socket;
	private NetworkConnection networkConnection;

	private boolean connected = false;
	private JPanel panelLogin = new JPanel();
	private JPanel panelRegister;
	private JPanel panelConnect = new JPanel();
	private JButton btnConnect;
	private JLabel lblStatus = new JLabel("");
	private JTextField txtUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		setTitle("jChat - Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 352, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(panelLogin, BorderLayout.WEST);
		panelLogin.setLayout(new MigLayout("", "[grow]", "[][][][][][][][]"));
		
		/*
		 * Action to attempt connecting to the server.
		 * If successful at connecting, create a network connection handler
		 * that will then be passed to Client
		 */
		ActionListener connectToServerAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new Socket(txtIPAddress.getText(), 1337);
					networkConnection = new NetworkConnection(null, socket);
					setLoginEnabled(true);
					setRegisterEnabled(true);
					setConnectEnabled(false);

				} catch (Exception e1) {
					lblStatus.setText("Connection Failed");
				}

			}
		};

		JLabel lblLogin = new JLabel("Login");
		panelLogin.add(lblLogin, "cell 0 0,alignx left");
		
		JLabel lblUsername = new JLabel("Username");
		panelLogin.add(lblUsername, "cell 0 1");
		
		txtUsername = new JTextField();
		panelLogin.add(txtUsername, "cell 0 2,growx");
		txtUsername.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		panelLogin.add(lblEmail, "cell 0 3,alignx left");

		txtLoginEmail = new JTextField();
		panelLogin.add(txtLoginEmail, "cell 0 4,growx");
		txtLoginEmail.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		panelLogin.add(lblPassword, "cell 0 5,alignx left");

		txtLoginPassword = new JPasswordField();
		panelLogin.add(txtLoginPassword, "cell 0 6,growx");

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				boolean result = login(txtLoginEmail.getText(), txtLoginPassword.getPassword());
				
				if (result) {
					dispose();
				}
			}
		});
		panelLogin.add(btnLogin, "cell 0 7,growx");

		panelRegister = new JPanel();
		contentPane.add(panelRegister, BorderLayout.EAST);
		panelRegister.setLayout(new MigLayout("", "[grow]", "[][][][][][][][][]"));

		JLabel lblRegister = new JLabel("Register");
		panelRegister.add(lblRegister, "cell 0 0,alignx right");

		JLabel lblEmail_1 = new JLabel("Email");
		panelRegister.add(lblEmail_1, "cell 0 1,alignx right");

		txtRegisterEmail = new JTextField();
		panelRegister.add(txtRegisterEmail, "cell 0 2,growx");
		txtRegisterEmail.setColumns(10);

		JLabel lblPassword_1 = new JLabel("Password");
		panelRegister.add(lblPassword_1, "cell 0 3,alignx right");

		txtRegisterPassword = new JPasswordField();
		panelRegister.add(txtRegisterPassword, "cell 0 4,growx");

		JLabel lblReenterPassword = new JLabel("Verify Password");
		panelRegister.add(lblReenterPassword, "cell 0 5,alignx right");

		txtRegisterPassword2 = new JPasswordField();
		panelRegister.add(txtRegisterPassword2, "cell 0 6,growx");

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtRegisterPassword.getPassword() == txtRegisterPassword2.getPassword()
						&& isValidEmail(txtRegisterEmail.getText())) {
					RegisterUser registerUser = new RegisterUser(txtRegisterEmail.getText(), txtRegisterPassword.getText());
				}
			}
		});
		panelRegister.add(btnRegister, "cell 0 7");

		contentPane.add(panelConnect, BorderLayout.NORTH);
		panelConnect.setLayout(new MigLayout("", "[][grow][]", "[15px][][]"));

		JLabel lblJchat = new JLabel("jChat - By Jonnie Simpson");
		panelConnect.add(lblJchat, "cell 0 0 3 1,alignx center,aligny top");
		lblJchat.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblServerIp = new JLabel("Server IP");
		panelConnect.add(lblServerIp, "cell 0 1,alignx trailing");

		txtIPAddress = new JTextField();
		txtIPAddress.addActionListener(connectToServerAction);
		panelConnect.add(txtIPAddress, "cell 1 1");
		txtIPAddress.setColumns(12);

		btnConnect = new JButton("Connect");

		btnConnect.addActionListener(connectToServerAction);
		panelConnect.add(btnConnect, "cell 2 1");
		
		panelConnect.add(lblStatus, "cell 0 2 3 1,alignx center");

		pack();

		setLoginEnabled(false);
		setRegisterEnabled(false);

	}
	
	private void testSendUser(String id) {
		networkConnection.sendMessage(new User(id, "testusername" + (Math.random()*10), "bob saget", "chilling bro", 0, "horse", "the barn"));
		
	}

	/*
	 * Disable or enable all components in the Connection Panel.
	 * @param b
	 */
	private void setConnectEnabled(boolean b) {
		Component[] components = panelConnect.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	/*
	 * Disable or enable all components in the Register Panel
	 * @param b
	 */
	private void setRegisterEnabled(boolean b) {
		Component[] components = panelRegister.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	/*
	 * Disable or enable all components in the Login Panel
	 * @param b
	 */
	private void setLoginEnabled(boolean b) {
		Component[] components = panelLogin.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	/*
	 * Attempts to login to the Server with the passed username and password
	 * hash by sending the <code>Login</code> class over the network
	 * @param email
	 * @param password
	 * @return boolean - success or failure to connect
	 */
	public boolean login(String email, char[] password) {

		String emailAddress;
		String passwordHash;

		// check the email
		if (isValidEmail(email)) {
			emailAddress = email;

		} else {
			lblStatus.setText("Email address must be valid");
			return false;
		}

		passwordHash = MD5.generateMD5(String.valueOf(password));
		
		// create a Login message to send to the server
		LoginUser loginUser = new LoginUser(emailAddress, passwordHash);
		networkConnection.sendMessage(loginUser);
		boolean result = getLoginResponse();
		
		/*
		 * refactor this into constructor
		 */
		if (result) {
			ID id = new ID(MD5.generateMD5(emailAddress));
			User user = new User(id.getStringID(), txtUsername.getText(), null, null, 0, null, null);
			networkConnection.setID(id.getStringID());
			new Client(user, networkConnection);
		}
		
		return true;
	}

	
	private boolean getLoginResponse() {
		try {
			boolean result = (Boolean) networkConnection.getInputStream().readObject();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks if the passed String contains an <code>@</code> symbol and a 
	 * <code>.</code>
	 * @param email The email address in String form that will be checked if
	 * its valid
	 * @return boolean <code>true</code> being the email is a valid email, 
	 * <code>false</code> being its not a valid email 
	 */
	public boolean isValidEmail(String email) {
		if (email.contains("@") && email.contains(".")) {
			return true;

		} else {
			return false;
		}
	}

}

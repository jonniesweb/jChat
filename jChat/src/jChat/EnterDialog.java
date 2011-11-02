/* Jonnie Simpson
 * Humberview S.S.
 * ICS 3U0, Created on 2011-01-24 - just in time!
 * EnterDialog.java
 * --------------------------------------------
 * This program is first launched from the jar.
 * It asks the user for the server ip address
 * and password. THIS IS THE MENU.
 * --------------------------------------------
 */

// package to segment the client and server portions
package jChat;

// various imports
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

// extend JDialog so that we can easily call JDialog methods
public class EnterDialog extends JDialog {
	private JTextField ipAddressField;
	private JPasswordField pwdAljgfhsoitbdfogrntgv;
	private JLabel lblerr = new JLabel("");

	// main method, first thing to run
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// create a dialog
					EnterDialog dialog = new EnterDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EnterDialog() {
		
		// set up the contentpane and window
		getContentPane().setBackground(new Color(77, 77, 77));
		getContentPane().setForeground(new Color(180, 180, 180));
		setBackground(new Color(255, 255, 255));
		setForeground(new Color(0, 0, 0));
		setResizable(false);
		setTitle("jChatClient - Menu");
		setAlwaysOnTop(true);
		setBounds(100, 100, 402, 205);
		getContentPane().setLayout(null);

		// set up the welcome and instruction labels
		JLabel lblWelcomeToJchatclient = new JLabel("Welcome to jChatClient!");
		lblWelcomeToJchatclient.setForeground(new Color(180, 180, 180));
		lblWelcomeToJchatclient.setBackground(new Color(75, 75, 75));
		lblWelcomeToJchatclient.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToJchatclient.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWelcomeToJchatclient.setSize(new Dimension(392, 0));
		lblWelcomeToJchatclient.setBounds(new Rectangle(0, 0, 396, 30));
		lblWelcomeToJchatclient.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWelcomeToJchatclient.setPreferredSize(new Dimension(392, 30));
		getContentPane().add(lblWelcomeToJchatclient);

		JLabel lblPleaseEnterThe = new JLabel("Please enter the server IP address and password:");
		lblPleaseEnterThe.setForeground(new Color(180, 180, 180));
		lblPleaseEnterThe.setBackground(new Color(75, 75, 75));
		lblPleaseEnterThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseEnterThe.setBounds(0, 30, 396, 30);
		getContentPane().add(lblPleaseEnterThe);
		
		// set up the textfield labels
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setForeground(new Color(180, 180, 180));
		lblIpAddress.setBackground(new Color(75, 75, 75));
		lblIpAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblIpAddress.setBounds(10, 71, 66, 14);
		getContentPane().add(lblIpAddress);

		//set up the ipAddressField
		ipAddressField = new JTextField();
		ipAddressField.setForeground(new Color(180, 180, 180));
		ipAddressField.setBackground(new Color(65, 65, 65));
		ipAddressField.setBounds(86, 69, 300, 20);
		getContentPane().add(ipAddressField);
		ipAddressField.setColumns(10);
		
		// set up the password label
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(180, 180, 180));
		lblPassword.setBackground(new Color(75, 75, 75));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(10, 96, 66, 14);
		getContentPane().add(lblPassword);

		// set up the passwordfield
		pwdAljgfhsoitbdfogrntgv = new JPasswordField();
		pwdAljgfhsoitbdfogrntgv.setText("aljgfhso5itbdfogrnt4gv");
		pwdAljgfhsoitbdfogrntgv.setBackground(new Color(65, 65, 65));
		pwdAljgfhsoitbdfogrntgv.setForeground(new Color(180, 180, 180));
		pwdAljgfhsoitbdfogrntgv.setBounds(86, 94, 300, 20);
		getContentPane().add(pwdAljgfhsoitbdfogrntgv);

		// create a button to process the input
		JButton btnConnect = new JButton("Connect");
		
		// whenever the connect button is pressed
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// fill these variables in from the textfields
				String ipaddress = ipAddressField.getText();
				String password = pwdAljgfhsoitbdfogrntgv.getText();
				
				// hash the given password
				int hashpass = password.hashCode();

				// compare the given password hash with the real password hash
				// you definitely couldn't decompile this then change the if statement
				if (hashpass == 38272589) {
					
					// print to the screen this message
					lblerr.setText("Password Accepted!");
					
					// create and pass the IP address to Client.Client
					Client application;

					application = new Client(ipaddress);
					
					// hide the window
					setVisible(false);

				} else {
					// tell the user the password was wrong
					lblerr.setText("Wrong Password!");

				} // end else

			}
		});
		
		// set up the button and the error label
		btnConnect.setBounds(153, 120, 89, 23);
		getContentPane().add(btnConnect);
		lblerr.setForeground(new Color(180, 180, 180));
		lblerr.setBackground(new Color(75, 75, 75));
		lblerr.setHorizontalAlignment(SwingConstants.CENTER);
		lblerr.setBounds(10, 152, 376, 14);
		getContentPane().add(lblerr);

	}
}

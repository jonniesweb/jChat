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
package client;

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

// extend JDialog so that we can easily call JDialog methods
public class EnterDialog extends JDialog {
	private JTextField ipAddressField;
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
		setBounds(100, 100, 0, 0);
		setResizable(false);
		setTitle("jChatClient");
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
		lblWelcomeToJchatclient.setBounds(new Rectangle(-1, 0, 396, 30));
		lblWelcomeToJchatclient.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWelcomeToJchatclient.setPreferredSize(new Dimension(392, 30));
		getContentPane().add(lblWelcomeToJchatclient);

		JLabel lblPleaseEnterThe = new JLabel("Enter the server IP address or hostname");
		lblPleaseEnterThe.setForeground(new Color(180, 180, 180));
		lblPleaseEnterThe.setBackground(new Color(75, 75, 75));
		lblPleaseEnterThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseEnterThe.setBounds(-1, 30, 396, 30);
		getContentPane().add(lblPleaseEnterThe);

		ActionListener actionSubmit = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// start the client
				new Client(ipAddressField.getText());

				// hide and get rid of the window
				dispose();

			}
		};
		
		//set up the ipAddressField
		ipAddressField = new JTextField();
		ipAddressField.setText("10.31.143.172");
		ipAddressField.addActionListener(actionSubmit);
		ipAddressField.setForeground(new Color(180, 180, 180));
		ipAddressField.setBackground(new Color(65, 65, 65));
		ipAddressField.setBounds(9, 69, 376, 20);
		getContentPane().add(ipAddressField);
		ipAddressField.setColumns(10);

		// create a button to process the input
		JButton btnConnect = new JButton("Connect");
		btnConnect.setMaximumSize(new Dimension(200, 25));
		btnConnect.setAlignmentX(Component.CENTER_ALIGNMENT);



		btnConnect.addActionListener(actionSubmit);

		// set up the button and the error label
		btnConnect.setBounds(127, 120, 140, 23);
		getContentPane().add(btnConnect);
		lblerr.setForeground(new Color(180, 180, 180));
		lblerr.setBackground(new Color(75, 75, 75));
		lblerr.setHorizontalAlignment(SwingConstants.CENTER);
		lblerr.setBounds(10, 152, 376, 14);
		getContentPane().add(lblerr);

	}
}

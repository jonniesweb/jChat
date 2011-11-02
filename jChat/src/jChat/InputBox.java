package jChat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class InputBox extends JDialog {
	private JTextField textField;

	public InputBox() {

		setAlwaysOnTop(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblDefaultText = new JLabel("default text");
		GridBagConstraints gbc_lblDefaultText = new GridBagConstraints();
		gbc_lblDefaultText.insets = new Insets(0, 0, 5, 0);
		gbc_lblDefaultText.gridx = 0;
		gbc_lblDefaultText.gridy = 0;
		getContentPane().add(lblDefaultText, gbc_lblDefaultText);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 2;
		getContentPane().add(btnSubmit, gbc_btnSubmit);
	}

	public static void main(String[] args) {

	}
	
	public InputBox(String windowName, String description) {
		
		
		
	}

}

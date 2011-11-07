package jChat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

public class InputBox extends JDialog {

	private JTextField textField;
	private JLabel lblBodyText;
	private JButton btnSubmit;
	private JPanel panel;
	private JButton btnCancel;

	public InputBox() {

		setTitle("JChat");

		setAlwaysOnTop(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		setBounds(100, 100, 0, 0);

		lblBodyText = new JLabel("");
		GridBagConstraints gbc_lblDefaultText = new GridBagConstraints();
		gbc_lblDefaultText.insets = new Insets(0, 0, 5, 0);
		gbc_lblDefaultText.gridx = 0;
		gbc_lblDefaultText.gridy = 0;
		getContentPane().add(lblBodyText, gbc_lblDefaultText);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		getContentPane().add(panel, gbc_panel);

		btnSubmit = new JButton("Submit");
		panel.add(btnSubmit);

		btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		
		setSize(400, 125);
		setVisible(true);
	}

	public JDialog createDialog(ActionListener submitListener, ActionListener cancelListener) {

		btnSubmit.addActionListener(submitListener);
		
		// if no ActionListener is added, default cancel to disposing of the window
		if (btnCancel.getAction() == null) {
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose(); 
				}
			});
			
		} else {
			btnCancel.addActionListener(cancelListener);
		}

		
		return this;
	}

	// set body text
	public void setBodyText(String description) {
		lblBodyText.setText(description);
	}

	// set window title
	public void setWindowTitle(String windowTitle) {
		setTitle(windowTitle);
	}
	
	// set Submit Buttons text
	public void setButtonText(String buttonText) {
		btnSubmit.setText(buttonText);
	}

	// returns the text contained in the Text Field
	public String getTextField() {
		return textField.getText();
	}
	
	// method to close the window if it isn't done by the ActionListener
	public void disposeWindow() {
		dispose();
	}
}

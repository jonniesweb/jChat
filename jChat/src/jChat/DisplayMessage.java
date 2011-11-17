package jChat;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class DisplayMessage {

	private StyledDocument document;
	private JTextPane textPane;

	private JTextArea textArea;
	private boolean textType;
	
	private ContentContainer objMessage;

	public DisplayMessage(JTextPane textPane, StyledDocument styledDocument) {

		this.textPane = textPane;
		document = styledDocument;
		textType = true;
	}

	public DisplayMessage(JTextArea textarea) {

		this.textArea = textarea;
		textType = false;
	}

	public void PrintMessage(String message) {

		message.trim();
		message += "\n";

		aPrintingMethod(message);

	}

	public void PrintMessage(ContentContainer contentContainer) {

		String message;
		objMessage = contentContainer;
		message = objMessage.getMessageText();
		message.trim();
		message += "\n";

		aPrintingMethod(message);

	}

	private void aPrintingMethod(String message) {

		if (textType == true) {
			// remove accidental spaces before and after message
			message.trim();

			// create an attribute set for the text style
			SimpleAttributeSet style = new SimpleAttributeSet();

			// if the message contains a recognised string, apply the requested
			// style and remove the command
			if (message.contains("/red")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.RED);
				message = message.replaceAll("/red", "");

			} 

			if (message.contains("/green")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.GREEN);
				message = message.replaceAll("/green", "");
			} 

			if (message.contains("/yellow")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.YELLOW);
				message = message.replaceAll("/yellow", "");
			} 

			if (message.contains("/blue")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.BLUE);
				message = message.replaceAll("/blue", "");
			} 

			if (message.contains("/magenta")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.MAGENTA);
				message = message.replaceAll("/magenta", "");
			} 

			if (message.contains("/cyan")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.CYAN);
				message = message.replaceAll("/cyan", "");
			} 

			if (message.contains("/orange")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.ORANGE);
				message = message.replaceAll("/orange", "");
			} 

			if (message.contains("/pink")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.PINK);
				message = message.replaceAll("/pink", "");
			} 

			if (message.contains("/black")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.BLACK);
				message = message.replaceAll("/black", "");
			} 

			if (message.contains("/white")) {
				style.addAttribute(StyleConstants.CharacterConstants.Foreground,
						Color.WHITE);
				message = message.replaceAll("/white", "");
			} 

			if (message.contains("/bold")) {
				style.addAttribute(StyleConstants.CharacterConstants.Bold,
						Boolean.TRUE);
				message = message.replaceAll("/bold", "");
			} 

			if (message.contains("/italic")) {
				style.addAttribute(StyleConstants.CharacterConstants.Italic,
						Boolean.TRUE);
				message = message.replaceAll("/italic", "");
			} 
			
			message.trim();
			
			// TODO: handle printing things without a username
			
			if(objMessage != null)
				try {
					document.insertString(document.getLength(), objMessage.getUsername() + ": ", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			try {
				
				// print the message to the document which then goes to the textpane
				// and apply the custom style
				document.insertString(document.getLength(), message, style);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			// autoscroll the text
			textPane.setCaretPosition(textPane.getDocument().getLength());
			
		} else {
			
			if (objMessage != null) {
				textArea.append(objMessage.getUsername() + ": " + message);
				
			} else {
				// print the message to the text area
				textArea.append(message);
			}


			// autoscroll the textarea
			textArea.setCaretPosition(textArea.getDocument().getLength());

		}


	}
}
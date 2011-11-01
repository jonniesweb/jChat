package jChat;

import javax.swing.JTextArea;

public class DisplayMessage {
	
	private JTextArea tArea;
	
	public DisplayMessage(JTextArea textarea) {
		
		tArea = textarea;
	}
	
	public void PrintMessage(String message, ContentContainer objmessage) {

		// remove accidental spaces before and after message
		message.trim();

		message = message + "\n";

		// displays the message to the textarea
		tArea.append(message);
		
		tArea.append(message);
		
		if (objmessage != null) {
			tArea.append(objmessage.getMessageText());
		}

		// autoscroll the textarea
		tArea.setCaretPosition(tArea.getDocument().getLength());
	}

	public void PrintMessage(String message) {
		tArea.append(message);

	}

	public void PrintMessage(ContentContainer objMessage) {

			String message;

			message = objMessage.getMessageText();
			message.trim();
			message += "\n";


	}

}
package jChat;

import java.io.Serializable;

public class ContentContainer implements Serializable {
	
	private int contentType = 0; // type of message being sent
	private int senderID = 0; // the unique id that clients have. could be used as a passphrase to distinguish users
	private String messageText = "test!"; // plain message text
	private static final String programVer = "1.0"; // program version to alert users of a newer version
	
	public ContentContainer(int senderID) {
		this.senderID = senderID;
	}

	public void setMessage(int senderID, String messageText) {
		contentType = 1;
		this.messageText = messageText;
	}
	
	public void setUsername(int senderID, String username) {
		contentType = 2;
		messageText = username;
	}
	
	public String getMessageText() {
		return messageText;
	}
	
	public int getSenderID() {
		return senderID;
	}
	public String getUsername() {
		return messageText;
	}
	
	public int getContentType() {
		return contentType;
	}
	public ContentContainer getObject() {
		return this;
	}
	
}
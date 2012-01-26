package message;

public class Message extends ID {

	private String message;
	private int roomID;
	private String username;
	
	public Message(final String uuid, final String messageText, final int roomID, String username) {
		super(uuid);
		
		if (messageText.length() != 0) {
			this.message = messageText;
		} else {
			throw new IllegalArgumentException("Message must not be empty!");
		}
		
			this.roomID = roomID;
			this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public int getRoomID() {
		return roomID;
	}
	
	public String getUsername() {
		return username;
	}

}

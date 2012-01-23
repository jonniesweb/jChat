package message;

public class Message extends ID {

	private String message;
	private byte roomID;
	
	public Message(final String uuid, final String messageText, final byte roomID) {
		super(uuid);
		
		if (messageText.length() != 0) {
			this.message = messageText;
		} else {
			throw new IllegalArgumentException("Message must not be empty!");
		}
		
			this.roomID = roomID;

	}

	public String getMessage() {
		return message;
	}

	public byte getRoomID() {
		return roomID;
	}

}
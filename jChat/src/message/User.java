package message;

public class User extends ID {
	
	private String username;
	private String realName;
	private String statusMessage;
	private int currentRoomID;
	private String gender;
	private String location;
	private int level;

	public User(final String uuid, final String username, final String realName, final String statusMessage, final byte currentRoomID, final String gender, final String location) {
		super(uuid);
		
		if (username.length() != 0) {
			this.username = username;
		} else {
			throw new IllegalArgumentException("Username must be at least one character!");
		}
		
		this.realName = realName;
		this.statusMessage = statusMessage;
		this.currentRoomID = currentRoomID;
		
		
	}

	public String getUsername() {
		return username;
	}

	public String getGender() {
		return gender;
	}

	public String getLocation() {
		return location;
	}

	public String getRealName() {
		return realName;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public int getCurrentRoomID() {
		return currentRoomID;
	}
	
	public int getLevel() {
		return level;
	}

	
}

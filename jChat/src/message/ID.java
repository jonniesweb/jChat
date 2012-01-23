package message;

import java.io.Serializable;

public class ID implements Serializable {
	// md5 hash of email address, or 0 for anonymous
	private String uuid;
	
	public ID(final String uuid) {
		
	}
	
	public String getID() {
		return uuid;

	}
	
}

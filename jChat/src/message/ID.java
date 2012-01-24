package message;

import java.io.Serializable;

public class ID implements Serializable {
	// md5 hash of email address, or null for anonymous
	private String uuid;
	
	public ID(String uuid) {
		this.uuid = uuid;
	}
	
	public ID(ID id) {
		this.uuid = id.getStringID();
	}
	
	public String getStringID() {
		return uuid;

	}
	
	public ID getID() {
		return this;
	}
	
	public void setID(String uuid) {
		
		this.uuid = uuid;
		
	}
	
	
}

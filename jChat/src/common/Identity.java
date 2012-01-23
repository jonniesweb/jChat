package common;

import java.io.IOException;

public class Identity extends ContentContainer {
	
	String firstName;
	String lastName;
	String email;
//	String password;
	
	public Identity(int senderID) {
		super(senderID);		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		CharSequence atCharacter = "@.";
		
		if (email.contains(atCharacter)) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("Must be a valid email");
		}
		
	}
	
	
	

}

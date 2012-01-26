package message;

import java.io.Serializable;

public class LoginUser implements Serializable {
	
	private String email;
	private String hashPassword;
	private String username;

	public LoginUser(String email, String hashPassword, String username) {
		
		if (email.length() != 0) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("Email must be at least one character!");
		}
		
		if (hashPassword.length() != 0) {
			this.hashPassword = hashPassword;
		} else {
			throw new IllegalArgumentException("Password hash must be greater than one character!");
		}
		
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public String getHashPassword() {
		return hashPassword;
	}
	
	public String getUsername() {
		
		return username;
	}

}

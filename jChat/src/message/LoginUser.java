package message;

public class LoginUser {
	
	private String email;
	private String hashPassword;

	public LoginUser(String email, String hashPassword) {
		
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
		
	}

	public String getEmail() {
		return email;
	}

	public String getHashPassword() {
		return hashPassword;
	}

}

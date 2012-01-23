package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.User;

public class UserAccount extends User {
	
	private String hashPassword;
	private ObjectOutputStream outputstream;
	private int userLevel;

	public UserAccount(String uuid, String username, String realName,
			String statusMessage, byte currentRoomID, String gender,
			String location, String hashPassword, ObjectOutputStream outputStream) {
		super(uuid, username, realName, statusMessage, currentRoomID, gender, location);
		
		this.hashPassword = hashPassword;
		this.outputstream = outputStream;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public ObjectOutputStream getOutputstream() {
		return outputstream;
	}
	
	public void sendObject(Object obj) {
		
		try {
			outputstream.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

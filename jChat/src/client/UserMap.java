package client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import message.ID;
import message.User;

/**
 * Helper class that abstracts the Map from the rest of the program
 * @author Jonnie Simpson
 *
 */
public class UserMap {

	private static Map<ID, User> map = Collections.synchronizedMap(new HashMap<ID, User>());
	
	/**
	 * @return This classes static map
	 */
	public static Map<ID, User> getMap() {
		return map;
	}

	/**
	 * @param user Add an ID and User to the map
	 */
	public static void addUser(User user) {
		map.put(user.getID(), user);
	}
	
	/**
	 * @param user Remove a user object from the map
	 */
	public static void removeUser(User user) {
		map.remove(user.getID());
	}
	
}

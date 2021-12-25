package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;
import java.util.Map;

/*  Represents a database of all users/customers
    @author Chowdhury Zayn Ud-Din Shams
 */

public class UserDatabase implements Writable {
    private Map<String, User> userList = new HashMap<>();


    // MODIFIES: this
    // EFFECTS: Adds new user to userList
    public void addNewUser(User newUser) {
        this.userList.put(newUser.getUsername(), newUser);
        EventLog.getInstance().logEvent(new Event(newUser.getUsername() + " added as a new user!"));
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject(userList);
    }

    public Map<String, User> getUserList() {
        return userList;
    }

    //EFFECTS: - returns true if username matches with a key in userList and password
    //           matches with the password of the User stored in the corresponding value of the key
    //         - returns false otherwise
    public boolean checkLoginInfo(String username, String password) {
        boolean usernameMatch = userList.containsKey(username);
        if (usernameMatch) {
            int correctPassword = userList.get(username).getPassword();
            return password.hashCode() == correctPassword;
        } else {
            return false;
        }
    }

    //checks if database already contains user with the username
    public boolean usernameAlreadyExists(String username) {
        return userList.containsKey(username);
    }

    // REQUIRES: username is in userList
    // EFFECTS: returns User with the key of username in UserList
    public User getUser(String username) {
        return userList.get(username);
    }

    public void setUserList(Map<String, User> userList) {
        this.userList = userList;
    }
}

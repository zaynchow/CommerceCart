package model;

import java.util.ArrayList;
import java.util.List;

/* Represents an user account with an username, password and personal information about the user.
   @author Chowdhury Zayn Ud-Din Shams
 */

public class User {
    private String name;
    private Integer age;
    private String gender;
    private String location;
    private final List<Product> allPastOrders;
    private final String username;
    private int password;

    //MODIFIES: this
    //EFFECTS: Constructs a User with an username and an empty past orders list
    public User(String username) {
        this.username = username;
        allPastOrders = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Adds products to the all past orders list
    public void addToAllPastOrders(List<Product> products) {
        allPastOrders.addAll(products);
        for (Product product : products) {
            EventLog.getInstance().logEvent(new Event(product.getName() + " added to user's past orders."));
        }
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Product> getAllPastOrders() {
        return allPastOrders;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.hashCode();

    }

    public void setPasswordDatabase(int password) {
        this.password = password;
    }
}


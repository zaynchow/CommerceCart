package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDatabaseTest {
    UserDatabase userDatabaseData;
    User userA;
    User userB;
    User userC;

    @BeforeEach
    public void setUp(){
        userDatabaseData = new UserDatabase();
        userA = new User("Zayn");
        userB = new User("Jade");
        userC = new User("Mary");
    }
    @Test
    public void testAddNewUser(){
        userDatabaseData.addNewUser(userA);
        assertEquals(1, userDatabaseData.getUserList().size());
        assertTrue(userDatabaseData.getUserList().containsKey("Zayn"));
        assertEquals(userA, userDatabaseData.getUserList().get("Zayn"));
        userDatabaseData.addNewUser(userB);
        assertEquals(2, userDatabaseData.getUserList().size());
        assertTrue(userDatabaseData.getUserList().containsKey("Jade"));
        assertTrue(userDatabaseData.getUserList().containsKey("Zayn"));
    }

    @Test
    public void testCheckLoginInfo(){
        userDatabaseData.addNewUser(userC);
        userC.setPassword("correctPassword");
        assertTrue(userDatabaseData.checkLoginInfo("Mary","correctPassword" ));
        assertFalse(userDatabaseData.checkLoginInfo("Mary", "wrongPass"));
        assertFalse(userDatabaseData.checkLoginInfo("wrongUsername", "correctPassword"));
        assertFalse(userDatabaseData.checkLoginInfo("wrongUsername","wrongPassword"));
    }

    @Test
    public void testGetUser(){
        userDatabaseData.addNewUser(userB);
        userDatabaseData.addNewUser(userC);
        assertEquals(userB, userDatabaseData.getUser("Jade"));
        assertEquals(userC, userDatabaseData.getUser("Mary"));
    }

    @Test
    public void testUsernameAlreadyExists(){
        userDatabaseData.addNewUser(userA);
        assertTrue(userDatabaseData.usernameAlreadyExists("Zayn"));
        assertFalse(userDatabaseData.usernameAlreadyExists("Jade"));
    }

    @Test
    public void testSetUsrList(){
        Map<String, User> userList = new HashMap<>();
        User userA = new User("admin");
        User userB = new User("customer");
        userA.setPassword("adminpass");
        userA.setGender("m");
        userA.setLocation("Ontario");
        userA.setAge(34);
        userB.setPassword("customerpass");
        userB.setGender("f");
        userB.setLocation("British Columbia");
        userB.setAge(20);
        userList.put("admin",userA);
        userList.put("customer", userB);
        userDatabaseData.setUserList(userList);
        assertEquals(2,userDatabaseData.getUserList().size());
        assertTrue(userDatabaseData.getUserList().containsKey("admin"));
        assertTrue(userDatabaseData.getUserList().containsKey("customer"));
    }

}

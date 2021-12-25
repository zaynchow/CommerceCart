package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserTest {
    User userA;

    @BeforeEach
    public void setUp() {
        userA = new User("zayn");
    }

    @Test
    public void testAllPastOrders() {
        Product productA = new Product(0, "Apple", 30);
        Product productB = new Product(1, "Mango", 10);
        Product productC = new Product(2, "peach", 35);
        List<Product> lop1 = new ArrayList<>();
        lop1.add(productA);
        lop1.add(productB);
        lop1.add(productC);
        userA.addToAllPastOrders(lop1);
        assertEquals(3, userA.getAllPastOrders().size());
        assertTrue(userA.getAllPastOrders().contains(productA));
        assertTrue(userA.getAllPastOrders().contains(productB));
        assertTrue(userA.getAllPastOrders().contains(productC));
    }

    @Test
    public void testSetName(){
        userA.setName("Zayn Chowdhury");
        assertEquals("Zayn Chowdhury",userA.getName());
    }

    @Test
    public void testGetAge(){
        userA.setAge(19);
        assertEquals(19,userA.getAge());
    }
}

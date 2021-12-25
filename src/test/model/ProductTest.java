package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    Product apple;

    @BeforeEach
    public void setUp(){
        apple = new Product(1, "apple", 30);
    }

    @Test
    public void testIncrementQuantitySold() {
        assertEquals(0, apple.getQuantitySold());
        apple.incrementQuantitySold();
        assertEquals(1, apple.getQuantitySold());
        for (int i = 0; i < 9; i++) {
            apple.incrementQuantitySold();
        }
        assertEquals(10, apple.getQuantitySold());
    }

    @Test
    public void testSetId() {
        assertEquals(1,apple.getId());
        apple.setId(0);
        assertEquals(0,apple.getId());
    }

    @Test
    public void testGetPrice() {
        assertEquals(30,apple.getPrice());
        apple = new Product(0,"apple",40);
        assertEquals(40,apple.getPrice());
    }

    @Test
    public void testGetName(){
        assertEquals("apple",apple.getName());
        Product productB = new Product(2, "Mango", 10);
        assertEquals("Mango",productB.getName());
    }

}

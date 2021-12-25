package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    Shop testShop;
    Product productA;
    Product productB;
    Product productC;

    @BeforeEach
    public void setUp() {
        testShop = new Shop();
        productA = new Product(0, "Apple", 30);
        productB = new Product(1, "Mango", 10);
        productC = new Product(2, "peach", 35);
    }

    @Test
    public void testAddToInventory() {
        assertEquals(0, testShop.getAllProducts().size());
        testShop.addToInventory(productA);
        assertTrue(testShop.getAllProducts().contains(productA));
        assertEquals(1, testShop.getAllProducts().size());
        for (int i = 0; i < 5; i++) {
            testShop.addToInventory(productB);
        }
        assertEquals(6, testShop.getAllProducts().size());
        assertTrue(testShop.getAllProducts().contains(productB));
        assertTrue(testShop.getAllProducts().contains(productA));

    }

    @Test
    public void testRemoveFromInventory() {
        testShop.addToInventory(productA);
        testShop.addToInventory(productB);
        assertEquals(2, testShop.getAllProducts().size());
        testShop.removeFromInventory(productA.getId());
        assertEquals(1, testShop.getAllProducts().size());
        assertFalse(testShop.getAllProducts().contains(productA));

    }

    @Test
    public void testAddToCart() {
        assertEquals(0, testShop.getCart().size());
        testShop.addToCart(productA);
        assertTrue(testShop.getCart().contains(productA));
        assertEquals(1, testShop.getCart().size());
        for (int i = 0; i < 5; i++) {
            testShop.addToCart(productB);
        }
        assertEquals(6, testShop.getCart().size());
        assertTrue(testShop.getCart().contains(productB));
        assertTrue(testShop.getCart().contains(productA));

    }

    @Test
    public void testRemoveFromCart() {
        testShop.addToCart(productA);
        testShop.addToCart(productB);
        assertEquals(2, testShop.getCart().size());
        testShop.removeFromCart(productA);
        assertEquals(1, testShop.getCart().size());
        assertFalse(testShop.getCart().contains(productA));
    }

    @Test
    public void testGetCartTotal() {
        testShop.addToCart(productA);
        testShop.addToCart(productB);
        testShop.addToCart(productC);
        assertEquals(75, testShop.getCartTotal());
        testShop.removeFromCart(productA);
        assertEquals(45, testShop.getCartTotal());
    }

    @Test
    public void testEmptyCart() {
        testShop.addToCart(productA);
        testShop.addToCart(productB);
        testShop.addToCart(productC);
        assertEquals(3, testShop.getCart().size());
        testShop.emptyCart();
        assertEquals(0, testShop.getCart().size());
    }

    @Test
    public void testTotalProductCategories() {
        List<Product> lop1 = new ArrayList<>();
        ProductCategory productCategoryA = new ProductCategory("testCategory1", lop1);
        testShop.productCategories.add(productCategoryA);
        ProductCategory productCategoryB = new ProductCategory("testCategory2", lop1);
        testShop.productCategories.add(productCategoryB);
        assertEquals(2, testShop.totalProductCategories());
    }

    @Test
    public void testSearchCategoryByName(){
        List<Product> lop1 = new ArrayList<>();
        ProductCategory productCategoryA = new ProductCategory("Grocery", lop1);
        testShop.productCategories.add(productCategoryA);
        ProductCategory productCategoryB = new ProductCategory("Arcade", lop1);
        testShop.productCategories.add(productCategoryB);
        assertEquals(2, testShop.totalProductCategories());
        assertEquals(productCategoryA,testShop.searchCategoryByName("Grocery"));
        assertEquals(productCategoryB,testShop.searchCategoryByName("Arcade"));
        assertEquals(null,testShop.searchCategoryByName("Fruits"));
    }

    @Test
    public void testAddProductCategory(){
        List<Product> lop1 = new ArrayList<>();
        ProductCategory productCategoryA = new ProductCategory("testCategory1", lop1);
        testShop.addProductCategory(productCategoryA);
        assertTrue(testShop.productCategories.contains(productCategoryA));
    }

    @Test
    public void testRemoveProductCategory(){
        List<Product> lop1 = new ArrayList<>();
        ProductCategory productCategoryA = new ProductCategory("testCategory1", lop1);
        testShop.addProductCategory(productCategoryA);
        assertTrue(testShop.productCategories.contains(productCategoryA));
        testShop.removeProductCategory(productCategoryA);
        assertFalse(testShop.productCategories.contains(productCategoryA));
    }

}

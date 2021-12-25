package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCategoryTest {
    ProductCategory productCategory;
    Product productA;
    Product productB;
    Product productC;

    @BeforeEach
    public void setUp() {

        productA = new Product(1, "Apple", 30);
        productB = new Product(2, "Mango", 10);
        productC = new Product(3, "peach", 35);
        List<Product> lop1 = new ArrayList<>();
        productCategory = new ProductCategory("testCategory", lop1);

    }

    @Test
    public void testAddToProductCategory() {
        assertEquals(0, productCategory.getListOfProductsInCategory().size());
        productCategory.addToProductCategory(productA);
        assertEquals(1, productCategory.getListOfProductsInCategory().size());
        assertTrue(productCategory.getListOfProductsInCategory().contains(productA));
        productCategory.addToProductCategory(productB);
        assertEquals(2, productCategory.getListOfProductsInCategory().size());
    }

    @Test
    public void testRemoveFromCategory() {
        productCategory.getListOfProductsInCategory().add(productA);
        productCategory.getListOfProductsInCategory().add(productB);
        assertEquals(2, productCategory.getListOfProductsInCategory().size());
        productCategory.removeFromProductCategory(productA);
        assertEquals(1, productCategory.getListOfProductsInCategory().size());
        assertFalse(productCategory.getListOfProductsInCategory().contains(productA));
    }

    @Test
    public void testIncrementNumberOfTimesVisited() {
        assertEquals(0, productCategory.getNumberOfTimesVisited());
        productCategory.incrementNumberOfTimesVisited();
        assertEquals(1, productCategory.getNumberOfTimesVisited());
        for (int i = 0; i < 9; i++) {
            productCategory.incrementNumberOfTimesVisited();
        }
        assertEquals(10, productCategory.getNumberOfTimesVisited());
    }
}

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AnalyticsTest {

    Analytics analytics;
    Shop testShop;
    Product productA;
    Product productB;
    Product productC;
    List<Product> lop1;
    UserDatabase userDatabaseData;
    User userA;
    User userB;

    @BeforeEach
    public void setUp() {
        testShop = new Shop();
        analytics = new Analytics(testShop);
        productA = new Product(0, "Apple", 30);
        productB = new Product(1, "Mango", 10);
        productC = new Product(2, "peach", 35);
        lop1 = new ArrayList<>();
        lop1.add(productA);
        lop1.add(productB);
        lop1.add(productC);
        userDatabaseData = new UserDatabase();
        userA = new User("Zayn");
        userB = new User("Jade");
        userDatabaseData.addNewUser(userA);
        userDatabaseData.addNewUser(userB);
    }


    @Test
    public void testCalculateHighestSoldProduct() {
        productA.incrementQuantitySold();
        for (int i = 0; i < 5; i++) {
            productB.incrementQuantitySold();
        }
        testShop.addToInventory(productA);
        testShop.addToInventory(productB);
        testShop.addToInventory(productC);
        analytics.calculateHighestSoldProduct();
        assertEquals(productB, analytics.getHighestSoldProduct());
        assertEquals(5, analytics.getQuantitySoldOfHighestSoldProduct());
    }

    @Test
    public void testGetMostVisitedCategory() {
        ProductCategory proCatA = new ProductCategory("Grocery", lop1);
        ProductCategory proCatB = new ProductCategory("Furniture", lop1);
        ProductCategory proCatC = new ProductCategory("Bags", lop1);
        proCatA.incrementNumberOfTimesVisited();
        for (int i = 0; i < 5; i++) {
            proCatB.incrementNumberOfTimesVisited();
        }
        testShop.productCategories.add(proCatA);
        testShop.productCategories.add(proCatB);
        testShop.productCategories.add(proCatC);
        assertEquals("Furniture", analytics.getMostVisitedCategory());
    }

    @Test
    public void testAddToTotalRevenue() {
        assertEquals(0, analytics.getTotalRevenue());
        analytics.addToTotalRevenue(20.45);
        assertEquals(20.45, analytics.getTotalRevenue());
        analytics.addToTotalRevenue(1.30);
        assertEquals(21.75, analytics.getTotalRevenue());
    }

    @Test
    public void testIncrementTotalProductsSold() {
        assertEquals(0, analytics.getProductsSold());
        analytics.incrementTotalProductSold();
        assertEquals(1, analytics.getProductsSold());
        for (int i = 0; i < 9; i++) {
            analytics.incrementTotalProductSold();
        }
        assertEquals(10, analytics.getProductsSold());
    }

    @Test
    public void testIncrementTotalOfOrders() {
        assertEquals(0, analytics.getTotalNumberOfOrders());
        analytics.incrementTotalNumberOfOrders();
        assertEquals(1, analytics.getTotalNumberOfOrders());
        for (int i = 0; i < 9; i++) {
            analytics.incrementTotalNumberOfOrders();
        }
        assertEquals(10, analytics.getTotalNumberOfOrders());
    }

    @Test
    public void testGetAverageOrderValue() {
        analytics.addToTotalRevenue(20.45);
        analytics.addToTotalRevenue(18.30);
        analytics.incrementTotalNumberOfOrders();
        analytics.incrementTotalNumberOfOrders();
        assertEquals(19.375, analytics.getAverageOrderValue());
    }

    @Test
    public void testCalculateDemographics(){
        User userC = new User("Mary");
        User userD = new User("Harry");
        User userE = new User ("Jane");
        userDatabaseData.addNewUser(userC);
        userDatabaseData.addNewUser(userD);
        userA.setAge(18);
        userA.setGender("m");
        userA.setLocation("British Columbia");
        userB.setAge(20);
        userB.setGender("f");
        userB.setLocation("Alberta");
        userC.setAge(50);
        userC.setGender("m");
        userC.setLocation("Alberta");
        userD.setAge(19);
        userD.setGender("f");
        userD.setLocation("Ontario");
        analytics.calculateDemographics(userDatabaseData);
        assertEquals(26, analytics.getAverageAgeOfCustomers());
        assertEquals("Alberta", analytics.getHighestVisitedFromLocation());
        assertEquals(50, analytics.getMaleProportion());
        assertEquals(50,analytics.getFemaleProportion());
        userB.setLocation("British Columbia");
        analytics.calculateDemographics(userDatabaseData);
        assertEquals("British Columbia", analytics.getHighestVisitedFromLocation());
        userB.setLocation("Ontario");
        analytics.calculateDemographics(userDatabaseData);
        assertEquals("Ontario", analytics.getHighestVisitedFromLocation());
    }

    @Test
    public void testCalculateDemographicsEqualHighestVisitedFromLocation(){
        userA.setAge(18);
        userA.setGender("m");
        userA.setLocation("Ontario");
        userB.setAge(20);
        userB.setGender("f");
        userB.setLocation("Alberta");
        analytics.calculateDemographics(userDatabaseData);
        assertEquals("All countries have been visited from equally", analytics.getHighestVisitedFromLocation());

    }

}

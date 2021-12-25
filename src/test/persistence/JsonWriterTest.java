package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Shop shop = new Shop();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShop() {
        try {
            Shop shop = new Shop();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShop.json");
            writer.open();
            writer.writeShop(shop);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyShop.json");
            shop = reader.readShop();
            assertEquals(0, shop.getProductCategories().size());
            assertEquals(0, shop.getAllProducts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralShop() {
        try {
            Shop newShop = new Shop();
            Product chair = new Product(0, "chair", 20.5);
            chair.setImage("./data/Product Images/test logo.png");
            Product closet = new Product(1, "closet", 100);
            closet.setImage("./data/Product Images/test logo.png");
            Product bulb = new Product(2, "bulb", 2);
            bulb.setImage("./data/Product Images/test logo.png");
            newShop.addToInventory(chair);
            newShop.addToInventory(closet);
            newShop.addToInventory(bulb);
            List<Product> furniture = new ArrayList<>();
            furniture.add(chair);
            furniture.add(closet);
            newShop.getProductCategories().add(new ProductCategory("furniture", furniture));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShop.json");
            writer.open();
            writer.writeShop(newShop);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralShop.json");
            newShop = reader.readShop();
            assertEquals(3, newShop.getAllProducts().size());
            assertEquals(1, newShop.getProductCategories().size());
            checkProduct("chair", 20.50, newShop.getAllProducts().get(0));
            checkProduct("closet", 100, newShop.getAllProducts().get(1));
            checkProduct("bulb", 2, newShop.getAllProducts().get(2));
            List<Product> productsInCategory = newShop.getProductCategories().get(0).getListOfProductsInCategory();
            assertEquals(2, productsInCategory.size());
            assertEquals("furniture", newShop.getProductCategories().get(0).getCategoryName());
            checkProduct("chair", 20.50, newShop.getAllProducts().get(0));
            checkProduct("closet", 100, newShop.getAllProducts().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testReaderGeneralDatabase() {
        try {
            UserDatabase ud1 = new UserDatabase();
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
            List<Product> pastOrders = new ArrayList<>();
            Product p = new Product(0, "peach", 35);
            p.setImage("./data/Product Images/test-logo");
            pastOrders.add(p);
            userB.addToAllPastOrders(pastOrders);
            ud1.addNewUser(userA);
            ud1.addNewUser(userB);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDatabase.json");
            writer.open();
            writer.writeUserDatabase(ud1);
            writer.close();


            JsonReader reader = new JsonReader("./data/testWriterGeneralDatabase.json");
            Map<String, User> ud = reader.readDatabase();
            assertEquals(2, ud.size());
            assertTrue(ud.containsKey("admin"));
            List<String> allPastOrderNames = new ArrayList<>();
            List<Integer> allPastOrderPrices = new ArrayList<>();
            checkUser("admin", "adminpass".hashCode(), "m",
                    allPastOrderNames, allPastOrderPrices, ud.get("admin"));
            assertTrue(ud.containsKey("customer"));
            allPastOrderNames.add("peach");
            allPastOrderPrices.add(35);
            checkUser("customer", "customerpass".hashCode(), "f",
                    allPastOrderNames, allPastOrderPrices, ud.get("customer"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAnalytics() {

        try {
            Shop shop = new Shop();
            Analytics analytics = new Analytics(shop);
            analytics.setTotalRevenue(94.72);
            analytics.setProductsSold(82);
            analytics.setTotalNumberOfOrders(20);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAnalytics.json");
            writer.open();
            writer.writeAnalytics(analytics);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAnalytics.json");
            double totalRevenue = reader.readAnalyticsRevenue();
            int productsSold = reader.readAnalyticsProductsSold();
            double orderTotal = reader.readAnalyticsOrders();
            assertEquals(94.72, totalRevenue);
            assertEquals(82, productsSold);
            assertEquals(20.0, orderTotal);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}

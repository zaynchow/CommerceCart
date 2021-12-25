package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/Test Data Files/noSuchFile.json");
        try {
            Map<String, User> ud = reader.readDatabase();
            Shop shop = reader.readShop();
            double revenue = reader.readAnalyticsRevenue();
            int productsSold = reader.readAnalyticsProductsSold();
            int totalOrders = reader.readAnalyticsOrders();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/Test Data Files/testReaderEmptyShop.json");
        try {
            Shop shop = reader.readShop();
            assertEquals(0, shop.getAllProducts().size());
            assertEquals(0, shop.getProductCategories().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralShop() {
        JsonReader reader = new JsonReader("./data/Test Data Files/testReaderGeneralShop.json");
        try {
            Shop shop = reader.readShop();
            assertEquals(5, shop.getAllProducts().size());
            assertEquals(2, shop.getProductCategories().size());
            checkProduct("RDR2", 80, shop.getAllProducts().get(0));
            checkProduct("Call Of Duty", 65, shop.getAllProducts().get(1));
            checkProduct("GTA V", 20, shop.getAllProducts().get(3));
            assertEquals("./data/Product Images/RDR2.png",shop.getAllProducts().get(0).getImage());
            List<Product> productsInCategory = shop.getProductCategories().get(0).getListOfProductsInCategory();
            assertEquals(3, productsInCategory.size());
            assertEquals("Action", shop.getProductCategories().get(0).getCategoryName());
            checkProduct("RDR2", 80, productsInCategory.get(0));
            checkProduct("Call Of Duty", 65, productsInCategory.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReader reader = new JsonReader("./data/Test Data Files/testReaderGeneralDatabase.json");
        try {
            Map<String, User> ud = reader.readDatabase();
            assertEquals(2, ud.size());
            assertTrue(ud.containsKey("admin"));
            List<String> allPastOrderNames = new ArrayList<>();
            List<Integer> allPastOrderPrices = new ArrayList<>();
            checkUser( "admin", "adminpass".hashCode(), "m",
                    allPastOrderNames, allPastOrderPrices, ud.get("admin"));
            assertTrue(ud.containsKey("customer"));
            allPastOrderNames.add("GTA V");
            allPastOrderPrices.add(20);
            checkUser("customer", "customerpass".hashCode(), "f",
                    allPastOrderNames, allPastOrderPrices, ud.get("customer"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAnalytics() {
        JsonReader reader = new JsonReader("./data/Test Data Files/testReaderGeneralAnalytics.json");
        try {
            double totalRevenue = reader.readAnalyticsRevenue();
            int productsSold = reader.readAnalyticsProductsSold();
            double orderTotal = reader.readAnalyticsOrders();
            assertEquals(94.72, totalRevenue);
            assertEquals(81, productsSold);
            assertEquals(20.0, orderTotal);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

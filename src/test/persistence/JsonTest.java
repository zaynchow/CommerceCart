package persistence;

import model.Product;
import model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkProduct(String name, double price, Product product) {
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
    }

    protected void checkUser( String username, int password, String gender,
                             List<String> pastOrderName, List<Integer> pastOrderPrice, User user) {
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(gender, user.getGender());
        assertEquals(pastOrderName.size(), user.getAllPastOrders().size());
        assertEquals(pastOrderPrice.size(), user.getAllPastOrders().size());
        for (int i = 0; i < user.getAllPastOrders().size(); i++) {
            checkProduct(pastOrderName.get(i), pastOrderPrice.get(i), user.getAllPastOrders().get(i));
        }

    }
}

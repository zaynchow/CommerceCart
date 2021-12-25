package persistence;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import model.*;
import org.json.*;

/*
Represents a reader that reads Shop, UserDatabase and Analytics from JSON data stored in file
Some methods taken from JsonSerializationDemo project.
Github Link (JsonSerializationDemo) : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: reads user database from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Map<String, User> readDatabase() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDatabase(jsonObject);
    }

    // EFFECTS: parses user database from JSON object and returns it
    private Map<String, User> parseDatabase(JSONObject jsonObject) {
        HashMap<String, User> map = new HashMap<>();
        for (String s : jsonObject.keySet()) {
            JSONObject jsob = jsonObject.getJSONObject(s);
            User newUser = new User(jsob.getString("username"));
            newUser.setPasswordDatabase(jsob.getInt("password"));
            newUser.setLocation(jsob.getString("location"));
            newUser.setGender(jsob.getString("gender"));
            newUser.setAge(jsob.getInt("age"));
            JSONArray allPastOrdersJsonArray = jsob.getJSONArray("allPastOrders");
            for (int i = 0; i < allPastOrdersJsonArray.length(); i++) {
                Product newProduct = createProduct(allPastOrdersJsonArray.getJSONObject(i));
                newUser.getAllPastOrders().add(newProduct);
            }
            map.put(s, newUser);
        }
        return map;
    }

    // EFFECTS: reads shop from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Shop readShop() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShop(jsonObject);
    }

    // EFFECTS: parses Shop from JSON object and returns it
    private Shop parseShop(JSONObject jsonObject) {
        JSONArray inventory = jsonObject.getJSONArray("inventory");
        JSONArray productCategories = jsonObject.getJSONArray("product categories");
        Shop shop = new Shop();
        for (Object json : inventory) {
            JSONObject nextProduct = (JSONObject) json;
            Product newProduct = createProduct(nextProduct);
            shop.addToInventory(newProduct);
        }
        for (Object json : productCategories) {
            JSONObject nextCategory = (JSONObject) json;
            ProductCategory newCategory = addCategory(nextCategory);
            shop.getProductCategories().add(newCategory);
        }
        return shop;
    }

    // EFFECTS: parses product category from JSON object and returns it
    private ProductCategory addCategory(JSONObject nextCategory) {
        String categoryName = nextCategory.getString("category name");
        int numberVisited = nextCategory.getInt("number of times visited");
        JSONArray productsInCategory = nextCategory.getJSONArray("products in category");
        List<Product> productListForCategory = new ArrayList<>();
        for (Object json : productsInCategory) {
            JSONObject nextProduct = (JSONObject) json;
            Product newProduct = createProduct(nextProduct);
            productListForCategory.add(newProduct);
        }
        ProductCategory parsedCategory = new ProductCategory(categoryName, productListForCategory);
        parsedCategory.setNumberOfTimesVisited(numberVisited);
        return parsedCategory;
    }



    // EFFECTS: parses product from JSON object and returns it
    private Product createProduct(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String productName = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        int quantitySold = jsonObject.getInt("quantitySold");
        Product newProduct = new Product(id, productName, price);
        newProduct.setQuantitySold(quantitySold);
        newProduct.setImage(jsonObject.getString("image"));
        return newProduct;
    }

    // EFFECTS: parses total revenue for analytics from JSON object and returns it
    public double readAnalyticsRevenue() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getDouble("totalRevenue");
    }

    // EFFECTS: parses total number of orders for analytics from JSON object and returns it
    public int readAnalyticsOrders() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getInt("orderTotal");
    }

    // EFFECTS: parses total number of products sold for analytics from JSON object and returns it
    public int readAnalyticsProductsSold() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getInt("productsSold");
    }
}

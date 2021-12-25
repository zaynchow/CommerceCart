package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


/* Represents a shop with an inventory of products, a list of product categories and an empty cart for products
   @author Chowdhury Zayn Ud-Din Shams
*/

public class Shop implements Writable {
    protected List<Product> inventory = new ArrayList<>();
    protected List<Product> cart = new ArrayList<>();
    protected List<ProductCategory> productCategories = new ArrayList<>();

    //MODIFIES: this
    //EFFECTS: Adds a Product to the inventory
    public void addToInventory(Product product) {
        inventory.add(product.getId(), product);
        EventLog.getInstance().logEvent(new Event(product.getName() + " added to inventory!"));
    }

    //MODIFIES: this
    //EFFECTS: Removes a Product from the inventory
    public void removeFromInventory(int id) {
        inventory.remove(id);
        EventLog.getInstance().logEvent(new Event(inventory.get(id).getName() + " removed from inventory!"));
    }

    //MODIFIES: this
    //EFFECTS: Adds Product to cart (a list of Product)
    public void addToCart(Product product) {
        cart.add(product);
        EventLog.getInstance().logEvent(new Event(product.getName() + " added to cart!"));
    }

    //MODIFIES: this
    //EFFECTS: Removes Product from cart (a list of Product)
    public void removeFromCart(Product product) {
        cart.remove(product);
        EventLog.getInstance().logEvent(new Event(product.getName() + " removed from cart!"));
    }

    //EFFECTS: Returns the total value of all the products in the cart
    public double getCartTotal() {
        double total = 0.00;
        for (Product p : cart) {
            total += p.getPrice();
        }
        return total;
    }

    //MODIFIES: this
    //EFFECTS: Adds a product category to productCategories
    public void addProductCategory(ProductCategory category) {
        productCategories.add(category);
        EventLog.getInstance().logEvent(new Event(category.getCategoryName()
                + " added as a product category!"));
    }

    //MODIFIES: this
    //EFFECTS: Deletes a product category from productCategories
    public void removeProductCategory(ProductCategory category) {
        productCategories.remove(category);
        EventLog.getInstance().logEvent(new Event(category.getCategoryName()
                + " removed from product categories list"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("inventory", listToJson(1));
        json.put("product categories", listToJson(2));
        return json;
    }

    // EFFECTS: returns products in the inventory and product categories as a JSON array
    private JSONArray listToJson(int i) {
        JSONArray jsonArray = new JSONArray();
        if (i == 1) {
            for (Product p : inventory) {
                jsonArray.put(p.toJson());
            }
        } else {
            for (ProductCategory pc : productCategories) {
                jsonArray.put(pc.toJson());
            }
        }
        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: Empties cart to a new empty list of products
    public void emptyCart() {
        cart = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Cart emptied successfully!"));
    }

    public List<Product> getCart() {
        return cart;
    }

    public List<Product> getAllProducts() {
        return inventory;
    }

    //EFFECTS: Returns the total number of product categories
    public int totalProductCategories() {
        return productCategories.size();
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    //TODO: Add exception
    public ProductCategory searchCategoryByName(String name) {
        for (ProductCategory pc : productCategories) {
            if (pc.getCategoryName().equals(name)) {
                return pc;
            }
        }
        return null;
    }

}
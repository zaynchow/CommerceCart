package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

/*  Represents a single product category with a name and the products associated with the category. Also keeps track of
    the number of times the category was visited, for analytics
    @author Chowdhury Zayn Ud-Din Shams
*/

public class ProductCategory implements Writable {
    private final String categoryName;
    private final List<Product> listOfProductsInCategory;
    private int numberOfTimesVisited;

    //MODIFIES: this
    //EFFECTS: constructs a product category with an associated category name
    //         and the list of products in the product category
    public ProductCategory(String name, List<Product> listOfProducts) {
        this.categoryName = name;
        this.listOfProductsInCategory = listOfProducts;
        numberOfTimesVisited = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds a Product to the list of Products associated to the products category
    public void addToProductCategory(Product product) {
        this.listOfProductsInCategory.add(product);
        EventLog.getInstance().logEvent(new Event(product.getName() + " added to the category "
                + this.getCategoryName() + "!"));
    }

    //MODIFIES: this
    //EFFECTS: Removes a Product from the list of Products associated to the products category
    public void removeFromProductCategory(Product product) {
        this.listOfProductsInCategory.remove(product);
        EventLog.getInstance().logEvent(new Event(product.getName() + " removed from the category"
                + this.getCategoryName() + "!"));
    }

    //MODIFIES: this
    //EFFECTS: Increments the number of times the product category was visited by 1
    public void incrementNumberOfTimesVisited() {
        numberOfTimesVisited++;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("category name", categoryName);
        json.put("products in category", listToJson());
        json.put("number of times visited", numberOfTimesVisited);
        return json;
    }

    // EFFECTS: returns products in the product category as a JSON array
    private JSONArray listToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Product p : listOfProductsInCategory) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }


    public int getNumberOfTimesVisited() {
        return numberOfTimesVisited;
    }

    public List<Product> getListOfProductsInCategory() {
        return listOfProductsInCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setNumberOfTimesVisited(int numberOfTimesVisited) {
        this.numberOfTimesVisited = numberOfTimesVisited;
    }

}

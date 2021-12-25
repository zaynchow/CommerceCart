package model;

/* Represents a single product with an id, name and a price(in CAD). Also tracks the total quantity sold for analytics.
   @author Chowdhury Zayn Ud-Din Shams
*/

import org.json.JSONObject;
import persistence.Writable;

public class Product implements Writable {
    private Integer id;
    private final String name;
    private final double price;
    private int quantitySold;
    private String image;


    //REQUIRES: price > 0.00
    //MODIFIES: this
    //EFFECTS: Makes a new product with the corresponding id,name and price
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        quantitySold = 0;
    }

    //MODIFIES: this
    //EFFECTS: Increments quantitySold by 1
    public void incrementQuantitySold() {
        quantitySold++;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("price", price);
        json.put("quantitySold", quantitySold);
        json.put("image", image);
        return json;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public void setImage(String imagePath) {
        image = imagePath;
    }

    public String getImage() {
        return image;
    }

}

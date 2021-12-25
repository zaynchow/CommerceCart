package model;

/*  Represents analytics data for the shop
    @author Chowdhury Zayn Ud-Din Shams
*/

import org.json.JSONObject;
import persistence.Writable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Analytics implements Writable {
    private final Shop currentShop;
    private Product highestSoldProduct;
    private int quantitySoldOfHighestSoldProduct;
    private double totalRevenue;
    private int productsSold;
    private int visitNumberOfMostVisitedCategory;
    private String mostVisitedCategory;
    private int totalNumberOfOrders;
    private int averageAgeOfCustomers;
    private int maleProportion;
    private int femaleProportion;
    private String highestVisitedFromLocation;


    //MODIFIES: this
    //EFFECTS: Initialises the analytics
    public Analytics(Shop currentShop) {
        this.currentShop = currentShop;
        quantitySoldOfHighestSoldProduct = 0;
        totalRevenue = 0.00;
        visitNumberOfMostVisitedCategory = 0;
        totalNumberOfOrders = 0;
        mostVisitedCategory = "No categories visited yet";

    }

    //MODIFIES: this
    //EFFECTS: Calculates the highest sold product and the quantity sold of the highest sold product
    public void calculateHighestSoldProduct() {
        for (Product p : currentShop.getAllProducts()) {
            if (p.getQuantitySold() > quantitySoldOfHighestSoldProduct) {
                quantitySoldOfHighestSoldProduct = p.getQuantitySold();
                highestSoldProduct = p;
            }
        }
    }

    // EFFECTS: - Calculates the most visited product category and the number of times it was visited.
    //          - Returns the most visited product category
    public String getMostVisitedCategory() {
        for (ProductCategory pc : currentShop.getProductCategories()) {
            if (pc.getNumberOfTimesVisited() > visitNumberOfMostVisitedCategory) {
                mostVisitedCategory = pc.getCategoryName();
                visitNumberOfMostVisitedCategory = pc.getNumberOfTimesVisited();
            }
        }
        return mostVisitedCategory;
    }

    //REQUIRES: checkoutTotal > 0
    //MODIFIES: this
    //EFFECTS: Adds checkoutTotal to the current value of totalRevenue
    public void addToTotalRevenue(double checkoutTotal) {
        totalRevenue += checkoutTotal;
    }

    //MODIFIES: this
    //EFFECTS: Increments the number of products sold by 1
    public void incrementTotalProductSold() {
        productsSold++;
    }

    //MODIFIES: this
    //EFFECTS: Increments the number of orders by 1
    public void incrementTotalNumberOfOrders() {
        this.totalNumberOfOrders++;
    }

    //EFFECTS: Returns the average order value
    public double getAverageOrderValue() {
        return totalRevenue / totalNumberOfOrders;
    }

    //MODIFIES: this
    // Calculates demographics of users/customers for analytics
    public void calculateDemographics(UserDatabase newUserDatabase) {
        Collection<User> userList = newUserDatabase.getUserList().values();
        int totalAge = 0;
        int maleCount = 0;
        int femaleCount = 0;
        for (User u : userList) {
            totalAge += u.getAge();
            if (u.getGender().equals("m")) {
                maleCount++;
            } else {
                femaleCount++;
            }
        }

        calculateMostVisitedFromLocation(userList);
        averageAgeOfCustomers = totalAge / userList.size();
        femaleProportion = (femaleCount * 100 / userList.size());
        maleProportion = (maleCount * 100 / userList.size());
    }

    //MODIFIES: this
    //EFFECTS: Calculates the location where most users reside
    private void calculateMostVisitedFromLocation(Collection<User> userList) {
        int albertaCount = 0;
        int britishcolumCount = 0;
        int ontarioCount = 0;
        for (User u : userList) {
            if (u.getLocation().equals("Alberta")) {
                albertaCount++;
            } else if (u.getLocation().equals("British Columbia")) {
                britishcolumCount++;
            } else {
                ontarioCount++;
            }
        }
        if (albertaCount > britishcolumCount && albertaCount > ontarioCount) {
            highestVisitedFromLocation = "Alberta";
        } else if (britishcolumCount > albertaCount && britishcolumCount > ontarioCount) {
            highestVisitedFromLocation = "British Columbia";
        } else if (ontarioCount > albertaCount && ontarioCount > britishcolumCount) {
            highestVisitedFromLocation = "Ontario";
        } else {
            highestVisitedFromLocation = "All countries have been visited from equally";
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("totalRevenue", totalRevenue);
        json.put("productsSold", productsSold);
        json.put("orderTotal", totalNumberOfOrders);
        return json;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getProductsSold() {
        return productsSold;
    }

    //TODO: Add nuull pointer exception(NoProductsSoldException) and then change tests
    public Product getHighestSoldProduct() {
        return highestSoldProduct;
    }

    public int getQuantitySoldOfHighestSoldProduct() {
        return quantitySoldOfHighestSoldProduct;
    }

    public int getTotalNumberOfOrders() {
        return totalNumberOfOrders;
    }

    public int getAverageAgeOfCustomers() {
        return averageAgeOfCustomers;
    }

    public int getMaleProportion() {
        return maleProportion;
    }

    public int getFemaleProportion() {
        return femaleProportion;
    }

    public String getHighestVisitedFromLocation() {
        return highestVisitedFromLocation;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setProductsSold(int productsSold) {
        this.productsSold = productsSold;
    }

    public void setTotalNumberOfOrders(int totalNumberOfOrders) {
        this.totalNumberOfOrders = totalNumberOfOrders;
    }

}

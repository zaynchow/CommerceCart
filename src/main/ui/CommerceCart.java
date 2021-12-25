package ui;


import model.*;
import model.Shop;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;


/*
    E-commerce store application with admin panel for analytics and shop management
    @author Chowdhury Zayn Ud-Din Shams
*/

public class CommerceCart {
    private static final String JSON_STORE = "./data/Data Files/shop.json";
    private static final String JSON_DATABASE = "./data/Data Files/database.json";
    private static final String JSON_ANALYTICS = "./data/Data Files/analytics.json";
    protected Shop newShop;
    protected final UserDatabase newUserDatabase;
    protected final Analytics analytics;
    protected User currentUser;
    private final JsonWriter jsonWriterShop;
    private final JsonReader jsonReaderShop;
    private final JsonWriter jsonWriterDatabase;
    private final JsonReader jsonReaderDatabase;
    private final JsonWriter jsonWriterAnalytics;
    private final JsonReader jsonReaderAnalytics;
    private static CommerceCart currentSession;


    //MODIFIES: this
    //EFFECTS: Loads instances of shop, login, analytics from files to operate on
    //         during the particular shopping session. Displays the main store frame on startup.
    private CommerceCart() {
        newUserDatabase = new UserDatabase();
        jsonWriterDatabase = new JsonWriter(JSON_DATABASE);
        jsonReaderDatabase = new JsonReader(JSON_DATABASE);
        jsonWriterShop = new JsonWriter(JSON_STORE);
        jsonReaderShop = new JsonReader(JSON_STORE);
        jsonWriterAnalytics = new JsonWriter(JSON_ANALYTICS);
        jsonReaderAnalytics = new JsonReader(JSON_ANALYTICS);
        readDatabaseFromFile();
        readShopFromFile();
        readShopFromFile();
        analytics = new Analytics(newShop);
        readAnalyticsFromFile();
        readShopFromFile();
    }

    public static CommerceCart getInstance() {
        if (currentSession == null) {
            currentSession = new CommerceCart();
        }

        return currentSession;
    }


    //------------------------------------ JSON WRITE METHODS --------------------------------------//

    //  EFFECTS: Saves newSHop, analytics and newUserDatabase as JSON files
    public void saveDataToFiles() {
        saveAnalyticsToFile();
        saveDatabaseToFile();
        saveShopToFile();
    }

    //EFFECTS: Save newShop object to JSON file
    private void saveShopToFile() {
        try {
            jsonWriterShop.open();
            jsonWriterShop.writeShop(newShop);
            jsonWriterShop.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: Saves newUserDatabase to JSON file
    private void saveDatabaseToFile() {
        try {
            jsonWriterDatabase.open();
            jsonWriterDatabase.writeUserDatabase(newUserDatabase);
            jsonWriterDatabase.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_DATABASE);
        }
    }

    //EFFECTS: Saves analytics to JSON file
    private void saveAnalyticsToFile() {
        try {
            jsonWriterAnalytics.open();
            jsonWriterAnalytics.writeAnalytics(analytics);
            jsonWriterAnalytics.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_DATABASE);
        }
    }

    //------------------------------------ JSON READ METHODS --------------------------------------//

    //EFFECTS: Reads newShop object from JSON file
    private void readShopFromFile() {
        try {
            newShop = jsonReaderShop.readShop();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: Reads newUserDatabase from JSON file
    private void readDatabaseFromFile() {
        try {
            newUserDatabase.setUserList(jsonReaderDatabase.readDatabase());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_DATABASE);
        }
    }

    //EFFECTS: Reads analytics from JSON file
    private void readAnalyticsFromFile() {
        try {
            analytics.setProductsSold(jsonReaderAnalytics.readAnalyticsProductsSold());
            analytics.setTotalNumberOfOrders(jsonReaderAnalytics.readAnalyticsOrders());
            analytics.setTotalRevenue(jsonReaderAnalytics.readAnalyticsRevenue());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_ANALYTICS);
        }
    }


    //------------------------------------------- GETTERS & SETTERS ------------------------------------------//

    //MODIFIES: this
    //EFFECTS: sets this.currentUser
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}





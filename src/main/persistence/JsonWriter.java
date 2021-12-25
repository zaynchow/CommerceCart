package persistence;

import model.Analytics;
import model.Shop;
import model.UserDatabase;
import org.json.JSONObject;


import java.io.*;

/*
Represents a writer that writes JSON representation of Shop, UserDatabase and Analytics from JSON data stored in file
Some methods taken from JsonSerializationDemo project.
Github Link (JsonSerializationDemo) : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of shop to file
    public void writeShop(Shop shop) {
        JSONObject json = shop.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of User Database to file
    public void writeUserDatabase(UserDatabase userDatabase) {
        JSONObject json = userDatabase.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Analytics to file
    public void writeAnalytics(Analytics analytics) {
        JSONObject json = analytics.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}

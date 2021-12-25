package ui;

/*
    E-commerce store application with admin panel for analytics and shop management
    @author Chowdhury Zayn Ud-Din Shams
*/

import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {
        SplashScreen sc = new SplashScreen();
        sc.counter();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString() + "\n\n");
                }
            }
        }));
    }
}

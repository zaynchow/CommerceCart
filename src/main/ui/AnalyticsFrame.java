package ui;


import ui.components.MyCustomJButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/*
    Represents a frame to view analytics of shop
    @author Chowdhury Zayn Ud-Din Shams
*/

public class AnalyticsFrame extends NewFrame {
    private final JPanel panel;


    //MODIFIES:this
    //EFFECTS: Constructs an analytics panel
    public AnalyticsFrame() {
        CommerceCart.getInstance().analytics.calculateHighestSoldProduct();
        CommerceCart.getInstance().analytics.calculateDemographics(CommerceCart.getInstance().newUserDatabase);
        panel = new JPanel();
        panel.setBackground(new Color(0x120434));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));


        JLabel analyticsTitle = new JLabel("Analytics");
        styling(analyticsTitle);
        analyticsTitle.setFont(new Font("Helvetica", Font.BOLD, 40));
        analyticsTitle.setForeground(Color.orange);


        generateAnalyticsData();

        generateBackBtn();


        add(panel);
        revalidate();

    }

    //MODIFIES:this
    //EFFECTS: Constructs a button to go back to shop management frame
    private void generateBackBtn() {
        MyCustomJButton backButton = new MyCustomJButton("Back to Shop Management");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBorder(new EmptyBorder(200, 0, 0, 0));
        backButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        backButton.setForeground(Color.white);
        backButton.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });
        panel.add(backButton);
    }

    //MODIFIES:this
    //EFFECTS: Generates all analytics data of store
    private void generateAnalyticsData() {
        JLabel totalRevenue = new JLabel("Total Revenue: " + CommerceCart.getInstance().analytics.getTotalRevenue());
        styling(totalRevenue);


        int productsSoldNum = CommerceCart.getInstance().analytics.getProductsSold();
        JLabel productsSold = new JLabel("Total Products Sold: " + productsSoldNum);
        styling(productsSold);


        generateMostSoldProduct();


        double avgOrderValue = CommerceCart.getInstance().analytics.getAverageOrderValue();
        JLabel averageOrderValue = new JLabel("Average Order Value: " + avgOrderValue);
        styling(averageOrderValue);

        String mostVisitedCategoryName = CommerceCart.getInstance().analytics.getMostVisitedCategory();
        JLabel mostVisitedCategory = new JLabel("Most Visited Category: " + mostVisitedCategoryName);
        styling(mostVisitedCategory);


        int avgAge = CommerceCart.getInstance().analytics.getAverageAgeOfCustomers();
        JLabel averageAge = new JLabel("Average Age of Customers: " + avgAge);
        styling(averageAge);

        int maleProportion = CommerceCart.getInstance().analytics.getMaleProportion();
        int femaleProportion = CommerceCart.getInstance().analytics.getFemaleProportion();
        JLabel genderProportion = new JLabel("Gender Proportions: Male(" + maleProportion + "%) Female("
                + femaleProportion + "%)");
        styling(genderProportion);

        String highestVisitedFromLocation = CommerceCart.getInstance().analytics.getHighestVisitedFromLocation();
        JLabel mostVisitedLocation = new JLabel(" Most visited location: " + highestVisitedFromLocation);
        styling(mostVisitedLocation);
    }

    //MODIFIES:this
    //EFFECTS: generates the most sold product name if exists
    private void generateMostSoldProduct() {
        //TODO: Modify if statement after adding null point exception in analytics in analytics.java
        JLabel mostSoldProduct = new JLabel();
        if (CommerceCart.getInstance().analytics.getHighestSoldProduct() == null) {
            mostSoldProduct.setText("No products sold yet!");
        } else {
            String name = CommerceCart.getInstance().analytics.getHighestSoldProduct().getName();
            mostSoldProduct.setText("Highest Sold Product: " + name);
        }
        styling(mostSoldProduct);
    }

    //MODIFIES:this
    //EFFECTS: styles label and add it to the panel
    private void styling(JLabel label) {
        label.setForeground(Color.white);
        label.setFont(new Font("Helvetica", Font.BOLD, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(label);
    }
}

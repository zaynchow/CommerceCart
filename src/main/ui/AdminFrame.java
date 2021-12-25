package ui;

import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
    Represents a frame to manage shop and analytics
    @author Chowdhury Zayn Ud-Din Shams
*/

public class AdminFrame extends NewFrame {

    private JPanel mainPanel;


    //MODIFIES:this
    //EFFECTS: Constructs an admin panel
    public AdminFrame() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 30, 30));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(0x120434));


        generateCreateProductBtn();
        generateCreateProductCategoryBtn();
        generateDeleteProductBtn();
        generateDeleteCategoryBtn();
        generateCheckAnalyticsBtn();
        generateSignOutBtn();








        add(mainPanel);
        revalidate();
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles a button to signout from admin panel
    private void generateSignOutBtn() {
        MyCustomJButton signOut = new MyCustomJButton("Sign Out");
        signOut.setBackground(new Color(0x3E1680));
        signOut.setOpaque(true);
        signOut.setFont(new Font("Helvetica", Font.PLAIN, 22));
        signOut.setForeground(Color.white);
        signOut.addActionListener(e -> {
            dispose();
            CommerceCart.getInstance().currentUser = null;
            new ShopFrame();
        });
        mainPanel.add(signOut);
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles a button to check store analytics
    private void generateCheckAnalyticsBtn() {
        MyCustomJButton checkAnalytics = new MyCustomJButton("Check Analytics");
        checkAnalytics.setBackground(new Color(0x3E1680));
        checkAnalytics.setOpaque(true);
        checkAnalytics.setFont(new Font("Helvetica", Font.PLAIN, 22));
        checkAnalytics.setForeground(Color.white);
        checkAnalytics.addActionListener(e -> {
            dispose();
            new AnalyticsFrame();
        });
        mainPanel.add(checkAnalytics);
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles a button to delete a product category
    private void generateDeleteCategoryBtn() {
        MyCustomJButton deleteCategoryButton = new MyCustomJButton("Delete Product Category");
        deleteCategoryButton.setBackground(new Color(0x3E1680));
        deleteCategoryButton.setOpaque(true);
        deleteCategoryButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        deleteCategoryButton.setForeground(Color.white);
        deleteCategoryButton.addActionListener(e -> {
            dispose();
            new DeleteCategoryFrame();

        });
        mainPanel.add(deleteCategoryButton);
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles a button to delete a product
    private void generateDeleteProductBtn() {
        MyCustomJButton deleteProductButton = new MyCustomJButton("Delete Product");
        deleteProductButton.setBackground(new Color(0x3E1680));
        deleteProductButton.setOpaque(true);
        deleteProductButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        deleteProductButton.setForeground(Color.white);
        deleteProductButton.addActionListener(e -> {
            dispose();
            new DeleteProductFrame();
        });
        mainPanel.add(deleteProductButton);
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles a button to add a product category
    private void generateCreateProductCategoryBtn() {
        MyCustomJButton createCategoryButton = new MyCustomJButton("Create Product Category");
        createCategoryButton.setBackground(new Color(0x3E1680));
        createCategoryButton.setOpaque(true);
        createCategoryButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        createCategoryButton.setForeground(Color.white);
        createCategoryButton.addActionListener(e -> {
            dispose();
            new CreateCategoryFrame();

        });
        mainPanel.add(createCategoryButton);
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles a button to add a product
    private void generateCreateProductBtn() {
        MyCustomJButton createProductButton = new MyCustomJButton("Create Product");
        createProductButton.setBackground(new Color(0x3E1680));
        createProductButton.setOpaque(true);
        createProductButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        createProductButton.setForeground(Color.white);
        createProductButton.addActionListener(e -> {
            dispose();
            new CreateProductFrame();

        });
        mainPanel.add(createProductButton);
    }
}

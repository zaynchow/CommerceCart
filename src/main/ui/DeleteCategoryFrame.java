package ui;

import model.Product;
import model.ProductCategory;
import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/*
    Represents a frame to delete categories
    @author Chowdhury Zayn Ud-Din Shams
*/

public class DeleteCategoryFrame extends NewFrame {
    private static JTextField categoryName;
    private final JLabel success;
    private final JPanel rightPanel;
    private final JPanel leftPanel;
    private JLabel deleteCategoryLabel;
    private int btnPosY;
    private int btnPosX;


    //MODIFIES: this
    //EFFECTS: Constructs a frame for deleting product categories
    public DeleteCategoryFrame() {

        setLayout(new GridLayout(1, 2));

        leftPanel = new JPanel(null);
        leftPanel.setBackground(new Color(0x120434));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(new EmptyBorder(50, 0, 0, 0));

        rightPanel = new JPanel(null);
        rightPanel.setBackground(new Color(0x3E1680));

        createTitle();

        categoryNameInput();

        success = new JLabel();
        success.setBounds(categoryName.getX(), categoryName.getY() + 120, 600, 50);
        success.setFont(new Font("Helvetica", Font.BOLD, 18));

        generateDeleteFromCategoryBtn();

        generateBackButton();


        categoryTable();


        productTable();


        rightPanel.add(success);
        add(leftPanel);
        add(rightPanel);
        revalidate();
    }


    //MODIFIES: this
    //EFFECTS: Generates field and label to input category name
    private void categoryNameInput() {
        JLabel categoryNameLabel = new JLabel("Category Name: ");
        categoryNameLabel.setBounds(deleteCategoryLabel.getX() - 150, deleteCategoryLabel.getY() + 80, 600, 55);
        categoryNameLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        categoryNameLabel.setForeground(Color.white);
        rightPanel.add(categoryNameLabel);

        categoryName = new JTextField();
        categoryName.setBounds(categoryNameLabel.getX(), categoryNameLabel.getY() + 50, 600, 55);
        categoryName.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(categoryName);
    }


    //MODIFIES: this
    //EFFECTS: Creates title
    private void createTitle() {
        deleteCategoryLabel = new JLabel();
        deleteCategoryLabel.setText("Delete a Category");
        deleteCategoryLabel.setBounds(200, 100, 400, 100);
        deleteCategoryLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        deleteCategoryLabel.setForeground(Color.white);
        rightPanel.add(deleteCategoryLabel);
    }


    //MODIFIES: this
    //EFFECTS: Generates a button to delete categories from shop
    private void generateDeleteFromCategoryBtn() {
        MyCustomJButton deleteFromCategoryBtn = new MyCustomJButton("Delete Category");
        deleteFromCategoryBtn.setBounds(categoryName.getX(), categoryName.getY() + 70, 600, 50);
        deleteFromCategoryBtn.setFont(new Font("Helvetica", Font.PLAIN, 22));
        deleteFromCategoryBtn.setForeground(Color.white);
        deleteFromCategoryActionListener(deleteFromCategoryBtn);
        btnPosX = deleteFromCategoryBtn.getX();
        btnPosY = deleteFromCategoryBtn.getY();
        rightPanel.add(deleteFromCategoryBtn);
    }


    //MODIFIES: this
    //EFFECTS: Deletes category from shop
    private void deleteFromCategoryActionListener(MyCustomJButton deleteFromCategoryBtn) {
        deleteFromCategoryBtn.addActionListener(e -> {
            //TODO: USe hashcode and equals module from EDX
            boolean found = false;
            ProductCategory currentPC = null;
            for (ProductCategory pc : CommerceCart.getInstance().newShop.getProductCategories()) {
                if (pc.getCategoryName().equals(categoryName.getText())) {
                    currentPC = pc;
                    found = true;
                }
            }
            if (found) {
                CommerceCart.getInstance().newShop.removeProductCategory(currentPC);
                categoryTable();
                productTable();

                success.setText("Category deletion successful!");
                success.setForeground(Color.green);
            } else {
                success.setText("Category could not be found");
                success.setForeground(Color.red);
            }
            CommerceCart.getInstance().saveDataToFiles();

        });
    }


    //MODIFIES: this
    //EFFECTS: Generates a button to go back to shop management frame
    private void generateBackButton() {
        MyCustomJButton backButton = new MyCustomJButton("Back to Shop Management");
        backButton.setHorizontalAlignment(JLabel.CENTER);
        backButton.setBounds(btnPosX, btnPosY + 150, 600, 50);
        backButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        backButton.setForeground(Color.white);
        backButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        backButton.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });
        rightPanel.add(backButton);
    }


    //MODIFIES: this
    //EFFECTS: generates a table with products(which are in a category) and their associated categories
    private void categoryTable() {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Category Name", "Product Name"}, 0);
        JTable table = new JTable(dtm);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x120434));

        for (ProductCategory currentCategory : CommerceCart.getInstance().newShop.getProductCategories()) {
            for (Product currentProduct : currentCategory.getListOfProductsInCategory()) {
                Object[] obj = {currentCategory.getCategoryName(), currentProduct.getName()};
                dtm.addRow(obj);
            }
        }


        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(0x3E1680));
        tableHeader.setForeground(Color.WHITE);
        scrollPane.setBounds(50, 20, 600, 400);


        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT);

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);
        leftPanel.add(scrollPane);
    }

    //MODIFIES: this
    //EFFECTS: generates a table with product IDs and their associated products
    private void productTable() {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Product ID", "Product Name"}, 0);
        JTable table = new JTable();
        table.setModel(dtm);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x120434));


        for (Product currentProduct : CommerceCart.getInstance().newShop.getAllProducts()) {
            Object[] obj = {currentProduct.getId(), currentProduct.getName()};
            dtm.addRow(obj);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(0x3E1680));
        tableHeader.setForeground(Color.WHITE);
        scrollPane.setBounds(50, 450, 600, 400);


        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT);

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);
        leftPanel.add(scrollPane);
    }

}



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
import java.util.ArrayList;


/*
    Represents a frame to add products existing categories or create new categories
    @author Chowdhury Zayn Ud-Din Shams
*/

public class CreateCategoryFrame extends NewFrame {
    private static JTextField categoryName;
    private static JTextField existingCategory;
    private final JPanel rightPanel;
    private final JPanel leftPanel;
    private JLabel createCategoryLabel;
    private int buttonPosY;
    private int buttonPosX;


    //MODIFIES: this
    //EFFECTS: Constructs a frame for managing product categories
    public CreateCategoryFrame() {

        setLayout(new GridLayout(1, 2));
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(0x120434));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(new EmptyBorder(50, 0, 0, 0));

        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(0x3E1680));

        createNewCategoryOptions(this);

        addToExistingCategoryOptions(this);

        generateBackBtn(this);

        productCategoryTable();

        productIDTable();


        add(leftPanel);
        add(rightPanel);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Creates options for adding products to an existing category
    private void addToExistingCategoryOptions(NewFrame frame) {
        JLabel existingCategoryTitle = new JLabel();
        existingCategoryTitle.setText("Add to Existing Category");
        existingCategoryTitle.setBounds(200, categoryName.getY() + 200, 400, 100);
        existingCategoryTitle.setFont(new Font("Helvetica", Font.BOLD, 30));
        existingCategoryTitle.setForeground(Color.white);
        rightPanel.add(existingCategoryTitle);

        createExistingCategoryNameInputField(existingCategoryTitle);


        MyCustomJButton addToExistingCategoryBtn = new MyCustomJButton("Add Products to Category");
        addToExistingCategoryBtn.setBounds(existingCategory.getX(), existingCategory.getY() + 70, 600, 50);
        addToExistingCategoryBtn.setFont(new Font("Helvetica", Font.PLAIN, 22));
        addToExistingCategoryBtn.setForeground(Color.white);
        addToExistingCategoryBtn.addActionListener(e -> {
            frame.dispose();
            ProductCategory pc = CommerceCart.getInstance().newShop.searchCategoryByName(existingCategory.getText());
            if (pc != null) {
                new AddProductsToCategoryFrame(pc);
            }
        });
        rightPanel.add(addToExistingCategoryBtn);

        buttonPosX = addToExistingCategoryBtn.getX();
        buttonPosY = addToExistingCategoryBtn.getY();
    }

    //MODIFIES: this
    //EFFECTS: Creates label and text field for existing category name input
    private void createExistingCategoryNameInputField(JLabel existingCategoryTitle) {
        JLabel existingCategoryLabel = new JLabel("Existing Category Name: ");
        existingCategoryLabel.setBounds(existingCategoryTitle.getX() - 150, existingCategoryTitle.getY() + 80, 600, 55);
        existingCategoryLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        existingCategoryLabel.setForeground(Color.white);
        rightPanel.add(existingCategoryLabel);

        existingCategory = new JTextField();
        existingCategory.setBounds(existingCategoryLabel.getX(), existingCategoryLabel.getY() + 50, 600, 55);
        existingCategory.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(existingCategory);
    }

    //MODIFIES: this
    //EFFECTS: Creates options for adding products to a new category
    private void createNewCategoryOptions(NewFrame frame) {
        createCategoryLabel = new JLabel();
        createCategoryLabel.setText("Create New Category");
        createCategoryLabel.setBounds(200, 100, 400, 100);
        createCategoryLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        createCategoryLabel.setForeground(Color.white);
        rightPanel.add(createCategoryLabel);

        createNewCategoryNameInputField();


        MyCustomJButton addToNewCategoryBtn = new MyCustomJButton("Create New Category");
        addToNewCategoryBtn.setBounds(categoryName.getX(), categoryName.getY() + 70, 600, 50);
        addToNewCategoryBtn.setFont(new Font("Helvetica", Font.PLAIN, 22));
        addToNewCategoryBtn.setForeground(Color.white);
        addToNewCategoryBtn.addActionListener(e -> {
            frame.dispose();
            ProductCategory newProductCategory = new ProductCategory(categoryName.getText(), new ArrayList<>());
            CommerceCart.getInstance().newShop.addProductCategory(newProductCategory);
            new AddProductsToCategoryFrame(newProductCategory);
            CommerceCart.getInstance().saveDataToFiles();

        });
        rightPanel.add(addToNewCategoryBtn);
    }

    //MODIFIES: this
    //EFFECTS: Creates label and text field for new category name input
    private void createNewCategoryNameInputField() {
        JLabel categoryNameLabel = new JLabel("New Category Name: ");
        categoryNameLabel.setBounds(createCategoryLabel.getX() - 150, createCategoryLabel.getY() + 80, 600, 55);
        categoryNameLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        categoryNameLabel.setForeground(Color.white);
        rightPanel.add(categoryNameLabel);

        categoryName = new JTextField();
        categoryName.setBounds(categoryNameLabel.getX(), categoryNameLabel.getY() + 50, 600, 55);
        categoryName.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(categoryName);
    }

    //MODIFIES: this
    //EFFECTS: Creates a button to go back to shop management frame
    private void generateBackBtn(NewFrame frame) {
        MyCustomJButton backButton = new MyCustomJButton("Back to Shop Management");
        backButton.setHorizontalAlignment(JLabel.CENTER);
        backButton.setBounds(buttonPosX, buttonPosY + 150, 600, 50);
        backButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        backButton.setForeground(Color.white);
        backButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        backButton.addActionListener(e -> {
            frame.dispose();
            new AdminFrame();
        });
        rightPanel.add(backButton);
    }

    //MODIFIES: this
    //EFFECTS: generates a table with products(which are in a category) and their associated categories
    private void productCategoryTable() {
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
    private void productIDTable() {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Product ID", "Product Name"}, 0);
        JTable table = new JTable(dtm);
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

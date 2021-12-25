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
    Represents a frame to add products to a category
    @author Chowdhury Zayn Ud-Din Shams
*/

public class AddProductsToCategoryFrame extends NewFrame {

    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JTextField productID;
    private JButton addProductButton;
    private JLabel addProductTitle;
    private JLabel productIDLabel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    //MODIFIES: this
    //EFFECTS: Constructs a frame to add products to a category
    public AddProductsToCategoryFrame(ProductCategory currentPC) {

        setLayout(new GridLayout(1, 2));
        setUpLeftPanel();
        setUPRightPanel();
        generateForm();
        generateAddToCategoryBtn(currentPC, leftPanel);
        generateBackButton(this);


        scrollPane1 = categoryTable(currentPC);
        scrollPane2 = productTable();

        leftPanel.add(scrollPane1);
        leftPanel.add(scrollPane2);

        rightPanel.add(addProductTitle);
        rightPanel.add(productIDLabel);
        rightPanel.add(productID);
        rightPanel.add(addProductButton);


        add(leftPanel);
        add(rightPanel);
        revalidate();
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles the right panel
    private void setUPRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(0x3E1680));
    }

    //MODIFIES:this
    //EFFECTS: Constructs and styles the left panel
    private void setUpLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(0x120434));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
    }

    //MODIFIES: this
    //EFFECTS: Constructs and styles a button to add products to a category
    private void generateAddToCategoryBtn(ProductCategory currentPC, JPanel leftPanel) {
        addProductButton = new MyCustomJButton("Add Product To Category");
        addProductButton.setBounds(productID.getX(), productID.getY() + 70, 600, 50);
        addProductButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        addProductButton.setForeground(Color.white);
        addProductButton.addActionListener(e -> {
            int index = Integer.parseInt(productID.getText());
            Product selectedProduct = CommerceCart.getInstance().newShop.getAllProducts().get(index);
            currentPC.addToProductCategory(selectedProduct);
            productID.setText("");
            scrollPane1 = categoryTable(currentPC);
            leftPanel.add(scrollPane1);
            scrollPane2 = productTable();
            leftPanel.add(scrollPane2);
            CommerceCart.getInstance().saveDataToFiles();
        });
    }

    //MODIFIES: this
    //EFFECTS: Constructs and styles a button to return to shop management frame
    private void generateBackButton(NewFrame frame) {
        MyCustomJButton backButton = new MyCustomJButton("Back to Shop Management");
        backButton.setHorizontalAlignment(JLabel.CENTER);
        backButton.setBounds(addProductButton.getX(), addProductButton.getY() + 150, 600, 50);
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
    //EFFECTS: Constructs and styles a form to take inputs from the user
    private void generateForm() {
        addProductTitle = new JLabel();
        addProductTitle.setText("Add Products to Category");
        addProductTitle.setBounds(150, 100, 600, 100);
        addProductTitle.setFont(new Font("Helvetica", Font.BOLD, 25));
        addProductTitle.setForeground(Color.white);

        productIDLabel = new JLabel("Product ID: ");
        productIDLabel.setBounds(addProductTitle.getX() - 80, addProductTitle.getY() + 80, 600, 55);
        productIDLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        productIDLabel.setForeground(Color.white);

        productID = new JTextField();
        productID.setBounds(productIDLabel.getX(), productIDLabel.getY() + 50, 600, 55);
        productID.setFont(new Font("Helvetica", Font.PLAIN, 22));
    }


    //EFFECTS: Constructs and styles a table to display products and their associated categories
    private JScrollPane categoryTable(ProductCategory pc) {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Category Name", "Product Name"}, 0);
        JTable table = new JTable();
        table.setModel(dtm);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x120434));


        //TODO: Add Exception
        if (pc.getListOfProductsInCategory() != null) {
            for (Product p : pc.getListOfProductsInCategory()) {
                Object[] obj = {pc.getCategoryName(), p.getName()};
                dtm.addRow(obj);
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(0x3E1680));
        tableHeader.setForeground(Color.WHITE);
        scrollPane.setBounds(50, 20, 600, 400);


        styleTableHeader(tableHeader);

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);
        return scrollPane;
    }

    //EFFECTS: Constructs and styles a table to display all products in the inventory
    private JScrollPane productTable() {
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


        styleTableHeader(tableHeader);

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);
        return scrollPane;
    }

    //MODIFIES: tableHeader
    //EFFECTS: Styles the header of a table
    private void styleTableHeader(JTableHeader tableHeader) {
        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20)); // font name style size
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text
    }


}

package ui;

import model.Product;
import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

import static java.lang.Double.parseDouble;

/*
    Represents a frame to add products to the inventory
    @author Chowdhury Zayn Ud-Din Shams
*/

public class CreateProductFrame extends NewFrame {
    
    
    private static JTextField productName;
    private static JTextField productPrice;
    private static JLabel success;
    private JFileChooser fileChooser;
    private int response;
    private final JPanel leftPanel;
    private final JPanel rightPanel;
    private JButton productImage;
    private int buttonPosY;
    private int buttonPosX;

    //MODIFIES: this
    //EFFECTS: Constructs a frame to add products to the inventory
    public CreateProductFrame() {
        setLayout(new GridLayout(1, 2));
        leftPanel = new JPanel(null);
        leftPanel.setBackground(new Color(0x120434));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(new EmptyBorder(50, 0, 0, 0));


        rightPanel = new JPanel(null);
        rightPanel.setBackground(new Color(0x3E1680));


        generateForm();

        uploadProductImageBtn();


        createAddProductBtn(productImage);

        createSuccessLabel();


        createBackBtn();


        generateTable();





        add(leftPanel);
        add(rightPanel);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Creates success message
    private void createSuccessLabel() {
        success = new JLabel("");
        success.setForeground(Color.green);
        success.setBounds(buttonPosX, buttonPosY + 50, 600, 50);
        success.setFont(new Font("Helvetica", Font.BOLD, 18));
        rightPanel.add(success);
    }

    //MODIFIES: this
    //EFFECTS: Generates a button to upload product images
    private void uploadProductImageBtn() {
        productImage = new JButton("Upload Product Image (PNG only)");
        productImage.setForeground(Color.white);
        productImage.setBounds(productPrice.getX(), productPrice.getY() + 70, 400, 50);
        productImage.setFont(new Font("Helvetica", Font.PLAIN, 22));
        productImage.addActionListener(e -> {
            fileChooser = new JFileChooser();
            response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                productImage.setText(fileChooser.getSelectedFile().getName());
            }
        });
        rightPanel.add(productImage);
    }

    //MODIFIES: this
    //EFFECTS: Generates a form to input product information
    private void generateForm() {
        JLabel createProductLabel = new JLabel("Create Product");
        createProductLabel.setBounds(200, 100, 400, 100);
        createProductLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        createProductLabel.setForeground(Color.white);
        rightPanel.add(createProductLabel);

        JLabel productNameLabel = new JLabel("Product Name: ");
        productNameLabel.setBounds(createProductLabel.getX() - 150, createProductLabel.getY() + 200, 200, 55);
        productNameLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        productNameLabel.setForeground(Color.white);
        rightPanel.add(productNameLabel);

        productName = new JTextField();
        productName.setBounds(productNameLabel.getX(), productNameLabel.getY() + 50, 600, 55);
        productName.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(productName);

        JLabel productPriceLabel = new JLabel("Product Price: ");
        productPriceLabel.setBounds(createProductLabel.getX() - 150, createProductLabel.getY() + 300, 200, 55);
        productPriceLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        productPriceLabel.setForeground(Color.white);
        rightPanel.add(productPriceLabel);

        productPrice = new JTextField();
        productPrice.setBounds(productPriceLabel.getX(), productPriceLabel.getY() + 50, 600, 55);
        productPrice.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(productPrice);
    }

    //MODIFIES: this
    //EFFECTS: Generates a button to add product to the inventory
    private void createAddProductBtn(JButton productImage) {
        MyCustomJButton addProductButton = new MyCustomJButton("Add Product");
        addProductButton.setBounds(productImage.getX(), productImage.getY() + 70, 600, 50);
        addProductButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        addProductButton.setForeground(Color.white);
        addProductBtnActionListener(addProductButton);
        buttonPosX = addProductButton.getX();
        buttonPosY = addProductButton.getY();
        rightPanel.add(addProductButton);
    }

    //MODIFIES: this
    //EFFECTS: Adds products to the inventory
    private void addProductBtnActionListener(JButton addProductButton) {
        addProductButton.addActionListener(e -> {
            File newFile;
            if (response == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileChooser.getSelectedFile().getAbsoluteFile();
                newFile = new File("./data/Product Images/" + productName.getText() + ".png");
                try {
                    Files.copy(chosenFile.toPath(), newFile.toPath());
                } catch (Exception ex) {
                    System.out.println("Error");
                }

            }

            int productId = CommerceCart.getInstance().newShop.getAllProducts().size();
            Product newProduct = new Product(productId, productName.getText(), parseDouble(productPrice.getText()));
            newProduct.setImage("./data/Product Images/" + productName.getText() + ".png");
            CommerceCart.getInstance().newShop.addToInventory(newProduct);
            generateTable();
            success.setText("Product Added Successfully");
            CommerceCart.getInstance().saveDataToFiles();
        });
    }

    //MODIFIES: this
    //EFFECTS: Generates a button to go back to shop management
    private void createBackBtn() {
        MyCustomJButton backButton = new MyCustomJButton("Back to Shop Management");
        backButton.setHorizontalAlignment(JLabel.CENTER);
        backButton.setBounds(buttonPosX, buttonPosY + 200, 600, 50);
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
    //EFFECTS: Generates a table with all the products in the inventory and their corresponding prices
    private void generateTable() {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Product Name", "Price"}, 0);
        JTable table = new JTable();
        table.setModel(dtm);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x120434));

        for (Product currentItem : CommerceCart.getInstance().newShop.getAllProducts()) {
            Object[] obj = {currentItem.getName(), currentItem.getPrice()};
            dtm.addRow(obj);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(0x3E1680));
        tableHeader.setForeground(Color.WHITE);
        scrollPane.setBounds(50, 20, 600, 800);


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

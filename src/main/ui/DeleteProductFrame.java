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

import static java.lang.Integer.parseInt;

/*
    Represents a frame to deletes products from the inventory
    @author Chowdhury Zayn Ud-Din Shams
*/

public class DeleteProductFrame extends NewFrame {
    private static JTextField productID;
    private static JLabel success;
    private int deleteBtnPosX;
    private int deleteBtnPosY;
    private final JPanel rightPanel;
    private final JPanel leftPanel;
    private JLabel deleteProductLabel;

    //MODIFIES: this
    //EFFECTS: Constructs a frame to deletes products from the inventory
    public DeleteProductFrame() {
        setLayout(new GridLayout(1, 2));
        leftPanel = new JPanel(null);
        leftPanel.setBackground(new Color(0x120434));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(new EmptyBorder(50, 0, 0, 0));


        rightPanel = new JPanel(null);
        rightPanel.setBackground(new Color(0x3E1680));


        deleteProductTitle();

        deleteProductForm();

        generateDeleteProductButton();

        success = new JLabel("");
        success.setForeground(Color.green);
        success.setBounds(deleteBtnPosX, deleteBtnPosY + 50, 600, 50);
        success.setFont(new Font("Helvetica", Font.BOLD, 18));
        rightPanel.add(success);

        generateBackButton();

        generateTable();

        add(leftPanel);
        add(rightPanel);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Generates a form to input product information
    private void deleteProductForm() {
        JLabel productNameLabel = new JLabel("Product ID: ");
        productNameLabel.setBounds(deleteProductLabel.getX() - 150, deleteProductLabel.getY() + 200, 200, 55);
        productNameLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        productNameLabel.setForeground(Color.white);
        rightPanel.add(productNameLabel);

        productID = new JTextField();
        productID.setBounds(productNameLabel.getX(), productNameLabel.getY() + 50, 600, 55);
        productID.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(productID);
    }

    //MODIFIES: this
    //EFFECTS: Creates a title
    private void deleteProductTitle() {
        deleteProductLabel = new JLabel();
        deleteProductLabel.setText("Delete Product");
        deleteProductLabel.setBounds(200, 100, 400, 100);
        deleteProductLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        deleteProductLabel.setForeground(Color.white);
        rightPanel.add(deleteProductLabel);
    }

    //MODIFIES: this
    //EFFECTS: Generates a button to delete product from the inventory
    private void generateDeleteProductButton() {
        MyCustomJButton deleteProductButton = new MyCustomJButton("Delete Product");
        deleteProductButton.setBounds(productID.getX(), productID.getY() + 70, 600, 50);
        deleteBtnPosX = deleteProductButton.getX();
        deleteBtnPosY = deleteProductButton.getY();
        deleteProductButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        deleteProductButton.setForeground(Color.white);
        deleteProductBtnActionListener(deleteProductButton);
        rightPanel.add(deleteProductButton);
    }

    //MODIFIES: this
    //EFFECTS: Deletes products to the inventory
    private void deleteProductBtnActionListener(MyCustomJButton deleteProductButton) {
        deleteProductButton.addActionListener(e -> {
            if (CommerceCart.getInstance().newShop.getAllProducts().size() == 0) {
                System.out.println("There are no products in the inventory");
            } else if (parseInt(productID.getText()) < CommerceCart.getInstance().newShop.getAllProducts().size()) {
                Product p = CommerceCart.getInstance().newShop.getAllProducts().get(parseInt(productID.getText()));
                File file = new File("./data/Product Images/" + p.getName() + ".png");
                file.delete();
                CommerceCart.getInstance().newShop.removeFromInventory(parseInt(productID.getText()));
                for (int i = p.getId(); i < CommerceCart.getInstance().newShop.getAllProducts().size(); i++) {
                    CommerceCart.getInstance().newShop.getAllProducts().get(i).setId(i);
                }
                success.setForeground(Color.green);
                success.setText("Product Deleted Successfully");
                generateTable();
                CommerceCart.getInstance().saveDataToFiles();
            } else {
                success.setForeground(Color.red);
                success.setText("Invalid Product ID");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Generates a button to go back to shop management
    private void generateBackButton() {
        MyCustomJButton backButton = new MyCustomJButton("Back to Shop Management");
        backButton.setHorizontalAlignment(JLabel.CENTER);
        backButton.setBounds(deleteBtnPosX, deleteBtnPosY + 300, 600, 50);
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
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"ID", "Product Name", "Price"}, 0);
        JTable table = new JTable();
        table.setModel(dtm);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x120434));

        for (Product currentItem : CommerceCart.getInstance().newShop.getAllProducts()) {
            Object[] obj = {currentItem.getId(), currentItem.getName(), currentItem.getPrice()};
            dtm.addRow(obj);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(0x3E1680)); // change the Background color
        tableHeader.setForeground(Color.WHITE); // change the Foreground
        scrollPane.setBounds(50, 20, 600, 800);


        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20)); // font name style size
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);
        leftPanel.add(scrollPane);
    }

}


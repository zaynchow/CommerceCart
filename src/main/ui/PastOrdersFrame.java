package ui;

import model.Product;
import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/*
    Represents a frame to display past user orders of a specific user
    @author Chowdhury Zayn Ud-Din Shams
*/

public class PastOrdersFrame extends NewFrame {
    private final JPanel mainPanel;

    //MODIFIES: this
    //EFFECTS: Constructs a past order frame
    public PastOrdersFrame() {
        setLayout(new BorderLayout());


        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0x120434));
        mainPanel.setOpaque(true);
        JLabel cartLabel = new JLabel("Past Orders");
        cartLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        cartLabel.setForeground(Color.white);
        cartLabel.setBounds(50, 0, 600, 100);


        createPastOrderTable();


        continueShoppingBtn();


        mainPanel.add(cartLabel);
        add(generateHeader(this), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        revalidate();

    }

    //MODIFIES: this
    //EFFECTS: Generates a button to go back to shop frame
    private void continueShoppingBtn() {
        MyCustomJButton continueShoppingButton = new MyCustomJButton("Continue Shopping");
        continueShoppingButton.setOpaque(true);
        continueShoppingButton.setBackground(Color.white);
        continueShoppingButton.setBounds(getWidth() - 300, 630, 250, 50);
        continueShoppingButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        continueShoppingButton.setHorizontalAlignment(JButton.LEFT);
        continueShoppingButton.addActionListener(e -> {
            dispose();
            new ShopFrame();
        });
        mainPanel.add(continueShoppingButton);
    }

    //MODIFIES: this
    //EFFECTS: Generates a table to display all past orders
    private void createPastOrderTable() {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Product Name", "Price", "Subtotal"}, 0);
        JTable table = new JTable();
        table.setModel(dtm);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x120434));

        for (Product orderItem : CommerceCart.getInstance().currentUser.getAllPastOrders()) {
            Object[] obj = {orderItem.getName(), orderItem.getPrice(), orderItem.getPrice()};
            dtm.addRow(obj);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(0x3E1680)); // change the Background color
        tableHeader.setForeground(Color.WHITE); // change the Foreground
        scrollPane.setBounds(50, 100, getWidth() - 100, 500);


        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20)); // font name style size
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);
        mainPanel.add(scrollPane);
    }
}

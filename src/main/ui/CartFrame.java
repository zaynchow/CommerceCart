package ui;

import model.Product;
import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import static java.awt.Image.SCALE_SMOOTH;

/*
    Represents a frame to display products in the cart
    @author Chowdhury Zayn Ud-Din Shams
*/
public class CartFrame extends NewFrame {
    private final JPanel mainPanel;
    private int buttonPos;

    //MODIFIES: this
    //EFFECTS: Constructs a cart frame
    public CartFrame() {
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0x120434));
        mainPanel.setOpaque(true);

        createCartTitle();

        createTable();

        JLabel cartTotal = new JLabel("Cart Total: $" + CommerceCart.getInstance().newShop.getCartTotal());
        cartTotal.setFont(new Font("Helvetica", Font.BOLD, 24));
        cartTotal.setForeground(Color.white);
        cartTotal.setBounds(getWidth() - 650, 630, 200, 50);

        createContinueShoppingbtn(cartTotal);

        createCheckoutbtn(cartTotal);

        mainPanel.add(cartTotal);
        add(generateHeader(this), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        revalidate();


    }

    //MODIFIES: this
    //EFFECTS: Creates "Cart" title
    private void createCartTitle() {
        JLabel cartLabel = new JLabel("Cart");
        cartLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        cartLabel.setForeground(Color.white);
        cartLabel.setBounds(50, 0, 200, 100);
        mainPanel.add(cartLabel);
    }

    //MODIFIES: this
    //EFFECTS: Creates a checkout button to go to checkout page
    private void createCheckoutbtn(JLabel cartTotal) {
        MyCustomJButton checkoutButton = new MyCustomJButton("Move to Checkout");
        checkoutButton.setOpaque(true);
        checkoutButton.setBackground(new Color(0xffd800));
        checkoutButton.setBounds(buttonPos + 280, cartTotal.getY() + 50, 280, 50);
        checkoutButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        checkoutButton.setHorizontalAlignment(JButton.LEFT);
        String cardLogos = "./data/Product Images/card logos.png";
        Image scaledInstance = new ImageIcon(cardLogos).getImage().getScaledInstance(80, 30, SCALE_SMOOTH);
        checkoutButton.setIcon(new ImageIcon(scaledInstance));
        checkoutButton.setIconTextGap(10);
        checkoutButton.addActionListener(e -> {
            dispose();
            if (CommerceCart.getInstance().currentUser == null) {
                new SignUpFrame();
            } else {
                new CheckoutFrame();
            }
        });
        mainPanel.add(checkoutButton);
    }

    //MODIFIES: this
    //EFFECTS: Creates a continue shopping button to go back to shop frame
    private void createContinueShoppingbtn(JLabel cartTotal) {
        MyCustomJButton continueShoppingButton = new MyCustomJButton("Continue Shopping");
        continueShoppingButton.setOpaque(true);
        continueShoppingButton.setBackground(Color.white);
        continueShoppingButton.setBounds(cartTotal.getX(), cartTotal.getY() + 50, 250, 50);
        buttonPos = continueShoppingButton.getX();
        continueShoppingButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        continueShoppingButton.setHorizontalAlignment(JButton.LEFT);
        continueShoppingButton.addActionListener(e -> {
            dispose();
            new ShopFrame();
        });
        mainPanel.add(continueShoppingButton);
    }

    //MODIFIES: this
    //EFFECTS: Creates a table of all the products in the cart
    private void createTable() {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Product Name", "Price", "Subtotal"}, 0);
        JTable table = new JTable();
        table.setModel(dtm);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x120434));

        for (Product cartItem : CommerceCart.getInstance().newShop.getCart()) {
            Object[] obj = {cartItem.getName(), cartItem.getPrice(), cartItem.getPrice()};
            dtm.addRow(obj);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(0x3E1680));
        tableHeader.setForeground(Color.WHITE);
        scrollPane.setBounds(50, 100, getWidth() - 100, 500);


        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT);

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);

        mainPanel.add(scrollPane);
    }
}

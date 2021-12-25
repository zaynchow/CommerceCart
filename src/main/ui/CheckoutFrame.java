package ui;

import model.Product;
import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/*
    Represents a frame to checkout products from the shop
    @author Chowdhury Zayn Ud-Din Shams
*/

public class CheckoutFrame extends NewFrame {
    private final JPanel leftPanel;
    private final JPanel rightPanel;
    private JLabel cardLabel;
    private JLabel cardPinLabel;


    //MODIFIES: this
    // EFFECTS: Constructs a checkout frame
    public CheckoutFrame() {
        setLayout(new BorderLayout());


        JPanel mainPanel = new JPanel(new GridLayout());
        mainPanel.setBackground(new Color(0x120434));
        mainPanel.setOpaque(true);

        leftPanel = new JPanel(null);
        leftPanel.setBackground(new Color(0x120434));
        leftPanel.setOpaque(true);
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(0x120434));
        rightPanel.setOpaque(true);
        rightPanel.setBorder(new EmptyBorder(50, 0, 0, 0));

        generateLeftPanel();

        generateProductTable();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(generateHeader(this), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Constructs a table with all products
    private void generateProductTable() {
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Product Name", "Price", "Subtotal"}, 0);
        JTable table = new JTable();
        table.setModel(dtm);
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
        scrollPane.setBounds(50, 20, 600, 500);


        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT);

        tableHeader.setPreferredSize(new Dimension(300, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Helvetica", Font.BOLD, 16));
        table.setRowHeight(25);
        rightPanel.add(scrollPane);

        cartTotalLabel();
    }

    //MODIFIES: this
    //EFFECTS: Creates a label showing the total amount of items in the cart
    private void cartTotalLabel() {
        JLabel cartTotal = new JLabel("Checkout Total: $" + CommerceCart.getInstance().newShop.getCartTotal());
        cartTotal.setFont(new Font("Helvetica", Font.BOLD, 24));
        cartTotal.setForeground(Color.white);
        cartTotal.setBounds(50, 550, 600, 30);
        rightPanel.add(cartTotal);
    }

    //MODIFIES: this
    //EFFECTS: Constructs all elements of the left panel
    private void generateLeftPanel() {
        JLabel checkoutLabel = new JLabel("Checkout");
        checkoutLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        checkoutLabel.setForeground(Color.white);
        checkoutLabel.setBounds(50, 40, 200, 80);
        leftPanel.add(checkoutLabel);


        generateCardTypeRadio(checkoutLabel);


        generateForm();


        generatePayNowBtn();
    }

    //MODIFIES:this
    //EFFECTS: Creates a pay now button
    private void generatePayNowBtn() {
        MyCustomJButton payNowButton = new MyCustomJButton("Pay Now");
        payNowButton.setOpaque(true);
        payNowButton.setBackground(new Color(0xffd800));
        payNowButton.setBounds(cardPinLabel.getX(), cardPinLabel.getY() + 150, 280, 50);
        payNowButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        payNowButton.setHorizontalAlignment(JButton.LEFT);
        payNowButton.addActionListener(e -> {
            CommerceCart.getInstance().currentUser.addToAllPastOrders(CommerceCart.getInstance().newShop.getCart());
            CommerceCart.getInstance().analytics.addToTotalRevenue(CommerceCart.getInstance().newShop.getCartTotal());
            CommerceCart.getInstance().analytics.incrementTotalNumberOfOrders();
            CommerceCart.getInstance().saveDataToFiles();
            for (int i = 0; i < CommerceCart.getInstance().newShop.getCart().size(); i++) {
                CommerceCart.getInstance().newShop.getCart().get(i).incrementQuantitySold();
                CommerceCart.getInstance().analytics.incrementTotalProductSold();
            }
            CommerceCart.getInstance().newShop.getCart().clear();
            dispose();
            new ShopFrame();
        });
        leftPanel.add(payNowButton);
    }

    //MODIFIES: this
    //EFFECTS: Creates a form to take card information
    private void generateForm() {
        JLabel cardNumberLabel = new JLabel("Enter your card number:");
        cardNumberLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        cardNumberLabel.setForeground(Color.white);
        cardNumberLabel.setBounds(cardLabel.getX(), cardLabel.getY() + 100, 600, 55);
        leftPanel.add(cardNumberLabel);


        JTextField cardNumberText = new JTextField();
        cardNumberText.setBounds(cardNumberLabel.getX(), cardNumberLabel.getY() + 50, 600, 55);
        cardNumberText.setFont(new Font("Helvetica", Font.PLAIN, 22));
        cardNumberLabel.setLabelFor(cardNumberText);
        leftPanel.add(cardNumberText);


        cardPinLabel = new JLabel("Enter your pin number: ");
        cardPinLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        cardPinLabel.setForeground(Color.white);
        cardPinLabel.setBounds(cardNumberText.getX(), cardNumberText.getY() + 80, 600, 55);
        leftPanel.add(cardPinLabel);

        JTextField cardPinText = new JTextField();
        cardPinText.setBounds(cardPinLabel.getX(), cardPinLabel.getY() + 50, 600, 55);
        cardPinText.setFont(new Font("Helvetica", Font.PLAIN, 22));
        cardPinLabel.setLabelFor(cardPinText);
        leftPanel.add(cardPinText);
    }

    //MODIFIES: this
    //EFFECTS: creates radio buttons for selecting card type
    private void generateCardTypeRadio(JLabel checkoutLabel) {
        cardLabel = new JLabel("Select your card type: ");
        cardLabel.setBounds(checkoutLabel.getX(), checkoutLabel.getY() + 80, 600, 55);
        cardLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        cardLabel.setForeground(Color.white);
        leftPanel.add(cardLabel);

        JRadioButton visaButton = new JRadioButton("Visa");
        visaButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        visaButton.setSelected(true);
        visaButton.setForeground(Color.white);


        JRadioButton mastercardButton = new JRadioButton("MasterCard");
        mastercardButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        mastercardButton.setForeground(Color.white);


        ButtonGroup cardButton = new ButtonGroup();
        cardButton.add(visaButton);
        cardButton.add(mastercardButton);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout());
        cardPanel.setBounds(cardLabel.getX(), cardLabel.getY() + 30, 400, 90);
        cardPanel.setOpaque(false);
        cardPanel.add(visaButton);
        cardPanel.add(mastercardButton);
        leftPanel.add(cardPanel);
    }
}

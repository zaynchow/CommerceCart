package ui;

import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
    Represents a frame layout
    @author Chowdhury Zayn Ud-Din Shams
*/

public class NewFrame extends JFrame {
    private static MyCustomJButton cartButton;
    private static GridBagConstraints gbc;
    private static JPanel header;

    //MODIFIES: this
    //EFFECTS: Constructs a new frame
    public NewFrame() {
        this.setVisible(true);
        this.setTitle("CommerceCart - Shop With Confidence");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        ImageIcon image = new ImageIcon("./data/favicon.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(0x3E1680));
        this.setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: Creates a header
    public static JPanel generateHeader(NewFrame frame) {

        GridBagLayout gbl = new GridBagLayout();
        header = new JPanel();
        gbc = new GridBagConstraints();
        header.setBorder(new EmptyBorder(0, 0, 0, 20));
        header.setPreferredSize(new Dimension(100, 100));
        header.setBackground(new Color(0x3E1680));
        header.setLayout(gbl);


        createLogo();

        createEmptySpace();

        createSignInButton(frame);

        createCartButton(frame);
        return header;
    }

    //MODIFIES: this
    //EFFECTS: Creates a cart button
    private static void createCartButton(NewFrame frame) {
        cartButton = new MyCustomJButton(String.valueOf(CommerceCart.getInstance().newShop.getCartTotal()));
        cartButton.setFont(new Font("Helvetica", Font.BOLD, 16));
        cartButton.setPreferredSize(new Dimension(180, 44));
        String cartIconLocation = "./data/Product Images/cart-icon.png";
        Image cartImage = new ImageIcon(cartIconLocation).getImage();
        Image scaledInstance = cartImage.getScaledInstance(30, 25, Image.SCALE_SMOOTH);
        ImageIcon cartIcon = new ImageIcon(scaledInstance);
        cartButton.setIcon(cartIcon);
        cartButton.setOpaque(true);
        cartButton.setBackground(new Color(0xffd800));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.insets.left = 10;
        cartButton.addActionListener(e -> {
            frame.dispose();
            new CartFrame();
        });
        header.add(cartButton, gbc);
    }

    //MODIFIES: this
    //EFFECTS: Creates a sign in button
    private static void createSignInButton(NewFrame frame) {
        if (CommerceCart.getInstance().currentUser == null) {
            MyCustomJButton signInButton = new MyCustomJButton("Sign In");
            styleSignInBtn(signInButton);
            signInButton.addActionListener(e -> {
                LoginFrame lg = new LoginFrame();
                frame.dispose();
                lg.createLoginPanel();
            });
            header.add(signInButton, gbc);
        } else {
            MyCustomJButton currentUserButton = new MyCustomJButton("Hi, "
                    + CommerceCart.getInstance().currentUser.getUsername());
            styleSignInBtn(currentUserButton);
            currentUserButton.addActionListener(e -> new PastOrdersFrame());
            header.add(currentUserButton, gbc);
        }
    }

    //MODIFIES: this
    //EFFECTS: Styles the sign in button
    private static void styleSignInBtn(MyCustomJButton signInButton) {
        Image userImage = new ImageIcon("./data/Product Images/user-icon.png").getImage();
        signInButton.setFont(new Font("Helvetica", Font.BOLD, 16));
        signInButton.setPreferredSize(new Dimension(180, 44));
        ImageIcon userIcon = new ImageIcon(userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        signInButton.setIcon(userIcon);
        signInButton.setOpaque(true);
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridy = 0;
    }

    //MODIFIES: this
    //EFFECTS: Creates empty space
    private static void createEmptySpace() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        emptyPanel.setPreferredSize(new Dimension(600, 0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        header.add(emptyPanel, gbc);
    }

    //MODIFIES: this
    //EFFECTS: Creates header logo
    private static void createLogo() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon fullLogo = new ImageIcon("./data/Product Images/logo.png");
        Image image = fullLogo.getImage();
        Image newImage = image.getScaledInstance((int) (screenSize.getWidth() / 5), 90, Image.SCALE_SMOOTH);
        fullLogo = new ImageIcon(newImage);
        JLabel leftLogo = new JLabel();
        leftLogo.setIcon(fullLogo);

        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        header.add(leftLogo, gbc);
    }


    //EFFECTS: returns cartButton
    public static MyCustomJButton getCartButton() {
        return cartButton;
    }
}

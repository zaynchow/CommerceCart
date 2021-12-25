package ui;

import model.User;
import ui.components.MyCustomJButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Represents a frame for user login
    @author Chowdhury Zayn Ud-Din Shams
*/

public class LoginFrame extends NewFrame implements ActionListener {

    private static JTextField userText;
    private static JPasswordField passwordText;
    private static JLabel success;
    private User user;
    private MyCustomJButton newCustomer;
    private MyCustomJButton submit;
    private ButtonGroup roleButton;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel loginLabel;


    //MODIFIES: this
    //EFFECTS: Constructs a frame for user login
    public void createLoginPanel() {
        setLayout(new GridLayout(1, 2));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(0x120434));

        generateLogo(screenSize);

        rightPanel = new JPanel(null);
        rightPanel.setBackground(new Color(0x3E1680));

        generateTitle();

        generateForm();

        success = new JLabel("");
        success.setBounds(submit.getX(), submit.getY() + 50, 200, 50);
        submit.addActionListener(this);
        if (user != null) {
            return;
        }
        success.setFont(new Font("Helvetica", Font.BOLD, 18));
        rightPanel.add(success);

        generateBackButton();

        add(leftPanel);
        add(rightPanel);
        revalidate();


    }

    //MODIFIES: this
    //EFFECTS: generates CommerceCart logo in left panel
    private void generateLogo(Dimension screenSize) {
        ImageIcon fullLogo = new ImageIcon("./data/Product Images/logo.png");
        Image image = fullLogo.getImage();
        Image newImage = image.getScaledInstance((int) (screenSize.getWidth() / 2.2), 200, Image.SCALE_SMOOTH);
        fullLogo = new ImageIcon(newImage);
        JLabel leftLogo = new JLabel();
        leftLogo.setIcon(fullLogo);
        leftLogo.setVerticalAlignment(JLabel.CENTER);
        leftLogo.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(leftLogo);
    }

    //MODIFIES: this
    //EFFECTS: Creates "login" title
    private void generateTitle() {
        loginLabel = new JLabel();
        loginLabel.setText("Login");
        loginLabel.setBounds(300, 100, 200, 100);
        loginLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        loginLabel.setForeground(Color.white);
        rightPanel.add(loginLabel);
    }

    //MODIFIES: this
    //EFFECTS: Creates back button to go back to shop panel
    private void generateBackButton() {
        newCustomer = new MyCustomJButton("Continue as a new customer");
        newCustomer.setHorizontalAlignment(JLabel.CENTER);
        newCustomer.setBounds(submit.getX(), submit.getY() + 300, 600, 50);
        newCustomer.setFont(new Font("Helvetica", Font.PLAIN, 18));
        newCustomer.setForeground(Color.white);
        newCustomer.setBorder(new EmptyBorder(0, 0, 0, 0));
        newCustomer.addActionListener(this);
        rightPanel.add(newCustomer);
    }

    //MODIFIES: this
    //EFFECTS: Creates login form
    private void generateForm() {
        roleInput();
        usernameInput();
        passwordInput();
        submitButton();
    }


    //MODIFIES: this
    //EFFECTS: Generates a button to submit the form
    private void submitButton() {
        submit = new MyCustomJButton("Sign In");
        submit.setBounds(passwordText.getX(), passwordText.getY() + 70, 600, 50);
        submit.setFont(new Font("Helvetica", Font.PLAIN, 22));
        submit.setForeground(Color.white);
        rightPanel.add(submit);
    }

    //MODIFIES: this
    //EFFECTS: Creates password input field
    private void passwordInput() {
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(loginLabel.getX() - 200, loginLabel.getY() + 300, 200, 55);
        passwordLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        passwordLabel.setForeground(Color.white);
        rightPanel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(passwordLabel.getX(), passwordLabel.getY() + 50, 600, 55);
        passwordText.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(passwordText);
    }

    //MODIFIES: this
    //EFFECTS: Creates role input field
    private void roleInput() {
        JLabel roleLabel = new JLabel("Role: ");
        roleLabel.setBounds(loginLabel.getX() - 200, loginLabel.getY() + 100, 200, 55);
        roleLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        roleLabel.setForeground(Color.white);
        rightPanel.add(roleLabel);

        JRadioButton customerButton = new JRadioButton("customer");
        customerButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        customerButton.setSelected(true);
        customerButton.setForeground(Color.white);
        customerButton.setActionCommand("customer");

        JRadioButton adminButton = new JRadioButton("admin");
        adminButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        adminButton.setForeground(Color.white);
        adminButton.setActionCommand("admin");

        roleButton = new ButtonGroup();
        roleButton.add(customerButton);
        roleButton.add(adminButton);


        JPanel rolePanel = new JPanel(new GridLayout());
        rolePanel.setBounds(roleLabel.getX() - 5, roleLabel.getY() + 30, 400, 90);
        rolePanel.setOpaque(false);
        rolePanel.add(customerButton);
        rolePanel.add(adminButton);
        rightPanel.add(rolePanel);
    }

    //MODIFIES: this
    //EFFECTS: Creates username input field
    private void usernameInput() {
        JLabel userLabel = new JLabel("Username: ");
        userLabel.setBounds(loginLabel.getX() - 200, loginLabel.getY() + 200, 200, 55);
        userLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        userLabel.setForeground(Color.white);
        rightPanel.add(userLabel);

        userText = new JTextField();
        userText.setBounds(userLabel.getX(), userLabel.getY() + 50, 600, 55);
        userText.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(userText);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(newCustomer)) {
            dispose();
            new ShopFrame();
        } else {
            String username = userText.getText();
            String password = passwordText.getText();
            boolean logsuccess = CommerceCart.getInstance().newUserDatabase.checkLoginInfo(username, password);
            if (logsuccess) {
                success.setForeground(new Color(0x247804));
                success.setText("Login Successful!");
                user = CommerceCart.getInstance().newUserDatabase.getUser(username);
                CommerceCart.getInstance().setCurrentUser(user);
                dispose();
                if (username.equals("admin") && roleButton.getSelection().getActionCommand().equals("admin")) {
                    new AdminFrame();
                } else {
                    new ShopFrame();
                }
            } else {
                success.setForeground(Color.red);
                success.setText("Login Failed!");
            }
        }
    }
}

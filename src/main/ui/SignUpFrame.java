package ui;

import model.User;
import ui.components.MyCustomJButton;

import javax.swing.*;
import java.awt.*;

/*
    Represents a frame for user sign up
    @author Chowdhury Zayn Ud-Din Shams
*/


public class SignUpFrame extends NewFrame {
    private JLabel leftLogo;
    private final JPanel rightPanel;
    private JLabel createAccountLabel;
    private JTextField usernameText;
    private JTextField passwordText;
    private JTextField age;
    private JLabel genderLabel;
    private ButtonGroup genderButton;
    private JLabel province;
    private ButtonGroup provinceButton;

    //MODIFIES: this
    //EFFECTS: Constructs a frame for user signup
    public SignUpFrame() {
        setLayout(new GridLayout(1, 2));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        createLogo(screenSize);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(new Color(0x120434));
        leftPanel.setOpaque(true);
        leftPanel.add(leftLogo);


        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(0x3E1680));


        createTitle();

        createForm();


        add(leftPanel);
        add(rightPanel);
        repaint();
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Creates signup form
    private void createForm() {
        usernameInput();
        passwordInput();
        ageInput();
        genderInput();
        provinceInput();
        createAccountButton();
    }

    //MODIFIES: this
    //EFFECTS: Generates a button to create an account
    private void createAccountButton() {
        JLabel usernameTakenLabel = createUsernameAlreadyTakenLabel();


        MyCustomJButton createAccountButton = new MyCustomJButton("Create account");
        createAccountBtnStyling(createAccountButton);
        createAccountButton.addActionListener(e -> {
            if (CommerceCart.getInstance().newUserDatabase.usernameAlreadyExists(usernameText.getText())) {
                usernameTakenLabel.setVisible(true);
            } else {

                User newUser = new User(usernameText.getText());
                newUser.setPassword(passwordText.getText());
                newUser.setGender(genderButton.getSelection().getActionCommand());
                newUser.setLocation(provinceButton.getSelection().getActionCommand());
                newUser.setAge(Integer.parseInt(age.getText()));
                CommerceCart.getInstance().setCurrentUser(newUser);
                CommerceCart.getInstance().newUserDatabase.addNewUser(newUser);
                CommerceCart.getInstance().saveDataToFiles();
                dispose();
                new CheckoutFrame();
            }
        });
        rightPanel.add(createAccountButton);
    }

    //MODIFIES: this
    //EFFECTS: Styles createAccountButton
    private void createAccountBtnStyling(MyCustomJButton createAccountButton) {
        createAccountButton.setOpaque(true);
        createAccountButton.setBackground(new Color(0xffd800));
        createAccountButton.setBounds(province.getX(), province.getY() + 150, 280, 50);
        createAccountButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        createAccountButton.setHorizontalAlignment(JButton.LEFT);
    }

    //MODIFIES: this
    //EFFECTS: Creates an "username already taken" label and styles it
    private JLabel createUsernameAlreadyTakenLabel() {
        JLabel usernameTakenLabel = new JLabel("Username Already Taken!");
        usernameTakenLabel.setForeground(Color.red);
        usernameTakenLabel.setBounds(province.getX(), province.getY() + 210, 280, 50);
        usernameTakenLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        usernameTakenLabel.setVisible(false);
        rightPanel.add(usernameTakenLabel);
        return usernameTakenLabel;
    }

    //MODIFIES: this
    //EFFECTS: Creates radio-buttons for province input
    private void provinceInput() {
        JPanel provincePanel = new JPanel();


        province = new JLabel("Province ");
        province.setBounds(genderLabel.getX(), genderLabel.getY() + 100, 200, 55);
        province.setFont(new Font("Helvetica", Font.PLAIN, 22));
        province.setForeground(Color.white);
        rightPanel.add(province);

        generateProvinceButtonGroup(provincePanel);

        provincePanel.setLayout(new GridLayout());
        provincePanel.setBounds(province.getX(), province.getY() + 30, 600, 90);
        provincePanel.setOpaque(false);
        rightPanel.add(provincePanel);
    }

    //MODIFIES: this
    //EFFECTS: Creates radio-buttons group for province input
    private void generateProvinceButtonGroup(JPanel provincePanel) {
        JRadioButton bcButton = new JRadioButton("British Columbia");
        bcButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        bcButton.setActionCommand("British Columbia");
        bcButton.setSelected(true);
        bcButton.setForeground(Color.white);
        provincePanel.add(bcButton);

        JRadioButton ontarioButton = new JRadioButton("Ontario");
        ontarioButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        ontarioButton.setActionCommand("Ontario");
        ontarioButton.setForeground(Color.white);
        provincePanel.add(ontarioButton);


        JRadioButton albertaButton = new JRadioButton("Alberta");
        albertaButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        albertaButton.setActionCommand("Alberta");
        albertaButton.setForeground(Color.white);
        provincePanel.add(albertaButton);


        provinceButton = new ButtonGroup();
        provinceButton.add(bcButton);
        provinceButton.add(albertaButton);
        provinceButton.add(ontarioButton);
    }

    //MODIFIES: this
    //EFFECTS: Creates radio-buttons for gender input
    private void genderInput() {
        genderLabel = new JLabel("Gender: ");
        genderLabel.setBounds(createAccountLabel.getX(), age.getY() + 80, 200, 55);
        genderLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        genderLabel.setForeground(Color.white);
        rightPanel.add(genderLabel);

        JRadioButton maleButton = new JRadioButton("Male");
        maleButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        maleButton.setActionCommand("m");
        maleButton.setSelected(true);
        maleButton.setForeground(Color.white);


        JRadioButton femaleButton = new JRadioButton("Female");
        femaleButton.setActionCommand("f");
        femaleButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        femaleButton.setForeground(Color.white);


        genderButton = new ButtonGroup();
        genderButton.add(maleButton);
        genderButton.add(femaleButton);

        JPanel genderPanel = new JPanel(new GridLayout());
        genderPanel.setBounds(genderLabel.getX(), genderLabel.getY() + 30, 400, 90);
        genderPanel.setOpaque(false);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        rightPanel.add(genderPanel);
    }

    //MODIFIES: this
    //EFFECTS: Creates field for age input
    private void ageInput() {
        JLabel ageLabel = new JLabel("Age: ");
        ageLabel.setBounds(createAccountLabel.getX(), passwordText.getY() + 80, 600, 55);
        ageLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        ageLabel.setForeground(Color.white);
        rightPanel.add(ageLabel);

        age = new JTextField();
        age.setBounds(ageLabel.getX(), ageLabel.getY() + 50, 200, 55);
        age.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(age);
    }

    //MODIFIES: this
    //EFFECTS: Creates field for password input
    private void passwordInput() {
        JLabel passwordLabel = new JLabel("Create new password: ");
        passwordLabel.setBounds(createAccountLabel.getX(), usernameText.getY() + 80, 600, 55);
        passwordLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        passwordLabel.setForeground(Color.white);
        rightPanel.add(passwordLabel);

        passwordText = new JTextField();
        passwordText.setBounds(passwordLabel.getX(), passwordLabel.getY() + 50, 600, 55);
        passwordText.setFont(new Font("Helvetica", Font.PLAIN, 22));
        rightPanel.add(passwordText);
    }

    //MODIFIES: this
    //EFFECTS: Creates field for username input
    private void usernameInput() {
        JLabel userLabel = new JLabel(" Create new username: ");
        userLabel.setBounds(createAccountLabel.getX(), createAccountLabel.getY() + 80, 600, 55);
        userLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
        userLabel.setForeground(Color.white);
        rightPanel.add(userLabel);

        usernameText = new JTextField();
        usernameText.setBounds(userLabel.getX(), userLabel.getY() + 50, 600, 55);
        usernameText.setFont(new Font("Helvetica", Font.PLAIN, 22));
        userLabel.setLabelFor(usernameText);
        rightPanel.add(usernameText);
    }

    //MODIFIES: this
    //EFFECTS: Creates "Create a new account" title
    private void createTitle() {
        createAccountLabel = new JLabel("Create a new account");
        createAccountLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        createAccountLabel.setForeground(Color.white);
        createAccountLabel.setBounds(50, 40, 600, 80);
        rightPanel.add(createAccountLabel);
    }

    //MODIFIES: this
    //EFFECTS: Creates a logo for the left panel
    private void createLogo(Dimension screenSize) {
        ImageIcon fullLogo = new ImageIcon("./data/Product Images/logo.png");
        Image image = fullLogo.getImage();
        Image newImage = image.getScaledInstance((int) (screenSize.getWidth() / 2.2), 200, Image.SCALE_SMOOTH);
        fullLogo = new ImageIcon(newImage);
        leftLogo = new JLabel();
        leftLogo.setIcon(fullLogo);
        leftLogo.setVerticalAlignment(JLabel.CENTER);
        leftLogo.setHorizontalAlignment(JLabel.CENTER);
    }
}

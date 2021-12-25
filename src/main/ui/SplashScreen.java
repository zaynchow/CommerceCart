package ui;

import javax.swing.*;
import java.awt.*;

/*
    Represents a splash screen
    @author Chowdhury Zayn Ud-Din Shams
*/

public class SplashScreen extends NewFrame {

    private final JProgressBar progressBar;
    private final JLabel percentage;
    private final JPanel panel1;

    //MODIFIES: this
    //EFFECTS: Constructs a splash screen
    public SplashScreen() {
        progressBar = new JProgressBar();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setVisible(true);
        JPanel panel = new JPanel(new GridBagLayout());
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        setSize(new Dimension(new Dimension(900, 700)));

        panel.setBackground(new Color(0x120434));
        panel1.setBackground(new Color(0x120434));

        generateLogo(screenSize);

        percentage = new JLabel();
        percentage.setFont(new Font("Helvetica", Font.BOLD, 35));
        percentage.setForeground(Color.white);


        panel1.add(progressBar);
        panel1.add(percentage);

        panel.add(panel1);
        add(panel);


        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: Generates a logo
    private void generateLogo(Dimension screenSize) {
        ImageIcon fullLogo = new ImageIcon("./data/Product Images/logo.png");
        Image image = fullLogo.getImage();
        Image newImage = image.getScaledInstance((int) (screenSize.getWidth() / 2.2), 200, Image.SCALE_SMOOTH);
        fullLogo = new ImageIcon(newImage);
        JLabel leftLogo = new JLabel();
        leftLogo.setIcon(fullLogo);
        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel1.add(leftLogo);
    }

    //MODIFIES: this
    //EFFECTS: Creates counter for progressbar
    public void counter() {
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(40);
                this.progressBar.setValue(i);
                percentage.setText(progressBar.getString());
            }
            new ShopFrame();
            dispose();

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}

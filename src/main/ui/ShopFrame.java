package ui;

import model.Product;
import model.ProductCategory;
import ui.components.MyCustomJButton;
import ui.components.WrapLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
    Represents a frame to display all products in the shop
    @author Chowdhury Zayn Ud-Din Shams
*/

public class ShopFrame extends NewFrame {
    
    private JScrollPane scroll;
    private JPanel centerPanel;
    private JPanel sidebar;


    //MODIFIES: this
    //EFFECTS: Constructs a shop frame
    public ShopFrame() {
        
        setLayout(new BorderLayout());

        generateSidebar();

        add(centerPanel, BorderLayout.CENTER);
        add(generateHeader(this), BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: generates a sidebar with product categories
    private void generateSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 100));
        sidebar.setBackground(new Color(0x3E1680));
        sidebar.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, new Color(0x120434)));
        CardLayout c1 = new CardLayout();
        centerPanel = new JPanel(c1);
        generateNewProductPanel(CommerceCart.getInstance().newShop.getAllProducts());
        centerPanel.add(scroll, "All products");
        c1.show(centerPanel, "All products");


        JLabel productCategoryLabel = new JLabel("Product Categories");
        sidebar.add(productCategoryLabel);
        productCategoryLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        productCategoryLabel.setForeground(Color.white);
        productCategoryLabel.setBorder(BorderFactory.createEmptyBorder(40, 40, 0, 0));

        createAllProductsBtn(c1);

        displayAllCategories(c1);
    }

    //MODIFIES: this
    //EFFECTS: displays all product categories
    private void displayAllCategories(CardLayout c1) {
        ArrayList<MyCustomJButton> listOfButtons = new ArrayList<>();
        for (ProductCategory category : CommerceCart.getInstance().newShop.getProductCategories()) {
            MyCustomJButton button = new MyCustomJButton(category.getCategoryName());
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setFont(new Font("Helvetica", Font.PLAIN, 15));
            button.setForeground(Color.white);
            listOfButtons.add(button);
            generateNewProductPanel(category.getListOfProductsInCategory());
            centerPanel.add(scroll, category.getCategoryName());
            button.addActionListener(e -> {
                c1.show(centerPanel, category.getCategoryName());
                category.incrementNumberOfTimesVisited();
                CommerceCart.getInstance().saveDataToFiles();
            });
            button.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 0));
            sidebar.add(button);
        }
    }

    //MODIFIES: this
    //EFFECTS: Generates a all products button to display all products in the shop
    private void createAllProductsBtn(CardLayout c1) {
        MyCustomJButton allProductsbutton = new MyCustomJButton("All Games");
        allProductsbutton.setAlignmentX(Component.LEFT_ALIGNMENT);
        allProductsbutton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        allProductsbutton.setForeground(Color.white);
        sidebar.add(allProductsbutton);
        allProductsbutton.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 0));
        allProductsbutton.addActionListener(e -> c1.show(centerPanel, "All products"));
    }

    //MODIFIES: this
    //EFFECTS: Constricts a product panel displaying all 'products'.
    public void generateNewProductPanel(List<Product> products) {

        JPanel productPanel = new JPanel(new WrapLayout(WrapLayout.LEFT, 50, 40));
        productPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
        int verticalScrollbarAsNeeded = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
        int horizontalScrollbarNever = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
        scroll = new JScrollPane(productPanel, verticalScrollbarAsNeeded, horizontalScrollbarNever);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        productPanel.setBackground(new Color(0x120434));


        for (Product product : products) {
            GridBagConstraints c = new GridBagConstraints();
            GridBagLayout gbl = new GridBagLayout();
            JPanel singleProductPanel = new JPanel(gbl);


            generateProductImage(product, c, singleProductPanel);
            c = new GridBagConstraints();
            createProductLabel(product, c, singleProductPanel);


            createAddToCartBtn(product, c, singleProductPanel);
            productPanel.add(singleProductPanel);
        }


    }

    //MODIFIES: this
    //EFFECTS: Create the product label for a product with its name and price
    private void createProductLabel(Product product, GridBagConstraints c, JPanel singleProductPanel) {
        JLabel label = new JLabel(product.getName() + ": $" + product.getPrice());
        label.setFont(new Font("Helvetica", Font.BOLD, 15));
        label.setBackground(new Color(0x3E1680));
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(0, 10, 0, 0));
        c.fill = GridBagConstraints.BOTH;
        label.setForeground(Color.white);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 15;
        singleProductPanel.add(label, c);
    }

    //MODIFIES: this
    //EFFECTS: Generates the image of a particular product
    private void generateProductImage(Product product, GridBagConstraints c, JPanel singleProductPanel) {
        JLabel productImage = new JLabel();
        Image img = new ImageIcon(product.getImage()).getImage();
        ImageIcon productIcon = new ImageIcon(img.getScaledInstance(300, 400, Image.SCALE_SMOOTH));
        productImage.setIcon(productIcon);
        c.fill = GridBagConstraints.BOTH;
        productImage.setHorizontalAlignment(SwingConstants.CENTER);
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        singleProductPanel.add(productImage, c);
    }

    //MODIFIES: this
    //EFFECTS: Generates the add to cart button for a product
    private void createAddToCartBtn(Product product, GridBagConstraints c, JPanel singleProductPanel) {
        MyCustomJButton addToCartBtn = new MyCustomJButton("Add To Cart");
        addToCartBtn.setFont(new Font("Helvetica", Font.BOLD, 15));
        addToCartBtn.setBackground(new Color(0xffd800));
        addToCartBtn.setOpaque(true);
        addToCartBtn.addActionListener(e -> {
            CommerceCart.getInstance().newShop.addToCart(product);
            NewFrame.getCartButton().setText(String.valueOf(CommerceCart.getInstance().newShop.getCartTotal()));
        });
        Image bagImage = new ImageIcon("./data/Product Images/Shopping Bag.png").getImage();
        ImageIcon bagIcon = new ImageIcon(bagImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        addToCartBtn.setIcon(bagIcon);
        addToCartBtn.setIconTextGap(10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        singleProductPanel.add(addToCartBtn, c);
    }


}

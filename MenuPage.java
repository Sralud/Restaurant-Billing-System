import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuPage extends JPanel {
    private RestaurantBillingSystem mainFrame;
    private List<MenuItem> menuItems;
    private JPanel menuPanel;
    private JTextField quantityField;
    private JButton addToOrderButton;
    private JButton viewOrdersButton;
    private MenuItem selectedItem;

    public MenuPage(RestaurantBillingSystem mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        initializeMenu();
        createMenuPanel();
        createOrderPanel();

        viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(e -> mainFrame.showCard("ViewOrders"));

        add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        add(viewOrdersButton, BorderLayout.SOUTH);
    }

    private void initializeMenu() {
        menuItems = new ArrayList<>();
        // Appetizers
        menuItems.add(new MenuItem(1, "Siomai", 30.0, "Appetizers"));
        menuItems.add(new MenuItem(2, "Potato Salad", 60.0, "Appetizers"));
        menuItems.add(new MenuItem(3, "Onion Rings", 75.0, "Appetizers"));
        // Main Courses
        menuItems.add(new MenuItem(4, "Adobong Manok", 190.0, "Main Courses"));
        menuItems.add(new MenuItem(5, "Pork Sinigang", 215.0, "Main Courses"));
        menuItems.add(new MenuItem(6, "Pancit Bam-i", 170.0, "Main Courses"));
        // Desserts
        menuItems.add(new MenuItem(7, "Halo-Halo", 65.0, "Desserts"));
        menuItems.add(new MenuItem(8, "Leche Flan", 75.0, "Desserts"));
        menuItems.add(new MenuItem(9, "Egg Pie", 100.0, "Desserts"));
    }

    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        String currentCategory = "";
        for (MenuItem item : menuItems) {
            if (!item.getCategory().equals(currentCategory)) {
                currentCategory = item.getCategory();
                JLabel categoryLabel = new JLabel(currentCategory);
                categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
                menuPanel.add(categoryLabel);
            }
            JButton itemButton = new JButton(String.format("%s - Php %.2f", item.getName(), item.getPrice()));
            itemButton.addActionListener(e -> selectMenuItem(item));
            menuPanel.add(itemButton);
        }
    }

    private void createOrderPanel() {
        JPanel orderPanel = new JPanel();
        quantityField = new JTextField(5);
        addToOrderButton = new JButton("Add to Order");
        addToOrderButton.setEnabled(false);
        addToOrderButton.addActionListener(e -> addToOrder());

        orderPanel.add(new JLabel("Quantity:"));
        orderPanel.add(quantityField);
        orderPanel.add(addToOrderButton);

        add(orderPanel, BorderLayout.NORTH);
    }

    private void selectMenuItem(MenuItem item) {
        selectedItem = item;
        addToOrderButton.setEnabled(true);
    }

    private void addToOrder() {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > 0 && selectedItem != null) {
                OrderItem orderItem = new OrderItem(selectedItem, quantity);
                mainFrame.getViewOrdersPage().addOrderItem(orderItem);
                JOptionPane.showMessageDialog(this, "Item added to order successfully!");
                quantityField.setText("");
                addToOrderButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item and enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

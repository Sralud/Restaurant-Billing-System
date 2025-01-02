import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewOrdersPage extends JPanel {
    private RestaurantBillingSystem mainFrame;
    private List<OrderItem> orderItems;
    private JTextArea orderTextArea;
    private JButton backToMenuButton;
    private JButton proceedToPaymentButton;

    public ViewOrdersPage(RestaurantBillingSystem mainFrame) {
        this.mainFrame = mainFrame;
        this.orderItems = new ArrayList<>();
        setLayout(new BorderLayout());

        orderTextArea = new JTextArea(20, 40);
        orderTextArea.setEditable(false);

        backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.addActionListener(e -> mainFrame.showCard("Menu"));

        proceedToPaymentButton = new JButton("Proceed to Payment");
        proceedToPaymentButton.addActionListener(e -> {
            mainFrame.getPaymentPage().updateTotalAmount();
            mainFrame.showCard("Payment");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backToMenuButton);
        buttonPanel.add(proceedToPaymentButton);

        add(new JScrollPane(orderTextArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        updateOrderDisplay();
    }

    private void updateOrderDisplay() {
        StringBuilder sb = new StringBuilder();
        for (OrderItem item : orderItems) {
            sb.append(String.format("%s x%d - Php %.2f\n", 
                item.getMenuItem().getName(), 
                item.getQuantity(), 
                item.getMenuItem().getPrice() * item.getQuantity()));
        }
        orderTextArea.setText(sb.toString());
    }

    public double getTotalAmount() {
        return orderItems.stream()
                .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
                .sum();
    }
}
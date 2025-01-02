import javax.swing.*;
import java.awt.*;

public class PaymentPage extends JPanel {
    private RestaurantBillingSystem mainFrame;
    private JLabel totalAmountLabel;
    private JTextField paymentField;
    private JButton payButton;

    public PaymentPage(RestaurantBillingSystem mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JPanel paymentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        totalAmountLabel = new JLabel("Total Amount: Php 0.00");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        paymentPanel.add(totalAmountLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        paymentPanel.add(new JLabel("Enter Payment:"), gbc);

        paymentField = new JTextField(10);
        gbc.gridx = 1;
        paymentPanel.add(paymentField, gbc);

        payButton = new JButton("Pay");
        payButton.addActionListener(e -> processPayment());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        paymentPanel.add(payButton, gbc);

        add(paymentPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to View Orders");
        backButton.addActionListener(e -> mainFrame.showCard("ViewOrders"));
        add(backButton, BorderLayout.SOUTH);
    }

    public void updateTotalAmount() {
        double totalAmount = mainFrame.getViewOrdersPage().getTotalAmount();
        totalAmountLabel.setText(String.format("Total Amount: Php %.2f", totalAmount));
    }

    private void processPayment() {
        try {
            double totalAmount = mainFrame.getViewOrdersPage().getTotalAmount();
            double payment = Double.parseDouble(paymentField.getText());
            if (payment >= totalAmount) {
                double change = payment - totalAmount;
                JOptionPane.showMessageDialog(this, 
                    String.format("Payment successful!\nChange: Php %.2f\nThank you for your order!", change));
                // Reset the order after successful payment
                mainFrame.getViewOrdersPage().addOrderItem(null);
                mainFrame.showCard("Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient payment. Please enter a higher amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid payment amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
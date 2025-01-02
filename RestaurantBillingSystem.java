import javax.swing.*;
import java.awt.*;

public class RestaurantBillingSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private MenuPage menuPage;
    private ViewOrdersPage viewOrdersPage;
    private PaymentPage paymentPage;

    public RestaurantBillingSystem() {
        setTitle("Pabusog Ta Bai Virtual Restaurant");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        menuPage = new MenuPage(this);
        viewOrdersPage = new ViewOrdersPage(this);
        paymentPage = new PaymentPage(this);

        cardPanel.add(menuPage, "Menu");
        cardPanel.add(viewOrdersPage, "ViewOrders");
        cardPanel.add(paymentPage, "Payment");

        add(cardPanel);

        setVisible(true);
    }

    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    public ViewOrdersPage getViewOrdersPage() {
        return viewOrdersPage;
    }

    public PaymentPage getPaymentPage() {
        return paymentPage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestaurantBillingSystem());
    }
}
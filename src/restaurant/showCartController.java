package restaurant;
import java.math.BigDecimal;

import serverMain.CartItem;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import serverMain.Order;

public class showCartController {
    Main main;
    List<Order> orderList = new ArrayList<>();

    @FXML
    private Button confirmOrderButton;

    @FXML
    private Button BackButton;

    @FXML
    private TextArea ordeTextArea;

    public void setOrderInPage(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void showOrder() {
        StringBuilder t = new StringBuilder();
    
        for (Order o : orderList) {
            List<CartItem> cart = o.getOrderedFoods();
            int serial = 1;
            BigDecimal bill = BigDecimal.ZERO; // Initialize bill as zero
            t.append("Customer : ").append(o.getUserName()).append("\n");
            
            for (CartItem C : cart) {
                t.append(serial).append(". ");
                t.append(C.getFood().getName()).append(" x").append(C.getQuantity()).append("\n");
                serial++;
                BigDecimal itemPrice = BigDecimal.valueOf(C.getFood().getPrice());
                BigDecimal quantity = BigDecimal.valueOf(C.getQuantity());
                bill = bill.add(itemPrice.multiply(quantity)); // Use BigDecimal for precision
            }
            
            t.append("Total Bill : $").append(bill.toString()).append("\n\n");
        }
        
        ordeTextArea.setText(t.toString());
    }

    @FXML
    void confirmButtonAction(ActionEvent event) {
        orderList.clear();
        ordeTextArea.clear();
    }

    @FXML
    void BackButtonAction(ActionEvent event) {
        try {
            main.ShowDetailsPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

}

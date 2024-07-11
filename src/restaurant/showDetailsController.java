package restaurant;

import java.io.IOException;
import java.util.List;

import Database.File;
import Database.Food;
import Database.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import serverMain.Order;

public class showDetailsController {

    @FXML
    private ListView<String> FoodList;

    @FXML
    private Button backButton;

    @FXML
    private Button orders;

    @FXML
    private Button showMenu;

    @FXML
    private TextArea textArea;

    // File file = new File();
    private Main main;

    Order orderList;

    @FXML
    void GoToOptionPage(ActionEvent event) {
        try {
            main.Option();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showMenuItems(ActionEvent event) {
        FoodList.setOpacity(1);
        // List<Restaurant> rest = file.searchRestaurantbyName(main.getUserName());
        File main2 = new File();
        Restaurant rest = main2.ResWithExactName(main.getUserName());
        List<Food> menu = rest.getMenuList();
        ObservableList<String> foods = FXCollections.observableArrayList();
        // for(Restaurant r : rest)
        // {
        // menu = r.getMenuList();
        // }
        for (int i = 0; i < menu.size(); i++) {
            foods.add(i, "Name : " + menu.get(i).getName() + ",Category : " + menu.get(i).getCategory());
        }

        FoodList.setItems(foods);

    }

    @FXML
    void showOrders(ActionEvent event) {
        // List <CartItem> cart = orderList.getOrderedFoods();
        // int serial = 1;
        // String t = "";
        // for(CartItem C : cart)
        // {
        //     t += (serial + " " + C.getFood().getName() + " x" + C.getQuantity()) + "\n";
        //     serial++;
        // }
        // OrderListShow.setText(t);
        try {
            main.ShowOrderPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializee() {
        System.out.println("details " + main.getUserName());
        // List<Restaurant> rest = file.searchRestaurantbyName(main.getUserName());
        // textArea.setText("Restaurant Name :"+userName);
        Restaurant r = main.getRestaurant();
        String text = "Name : " + r.getName() + "\nID : " + r.getId() + "\nPrice : " + r.getPrice() + "\nScore : "
                + r.getScore() + "\nZIP : " + r.getZip() + "\nCategories : " + r.getCategories()[0] + ",\n"
                + r.getCategories()[1] + ",\n" + r.getCategories()[2];
        textArea.setText(text);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    // public void setOrderInPage(Order orderList) {
    //     this.orderList = orderList;

    // }

}

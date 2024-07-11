package serverMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

public class FSearchController {
    private Main main;
    // File file = new File();
    File file;

    @FXML
    private Button BackButton;

    @FXML
    private MenuItem Category;

    @FXML
    private MenuItem name;

    @FXML
    private MenuItem PriceRange;

    @FXML
    private MenuItem Show;

    @FXML
    private TableColumn<Food, Integer> ID;

    @FXML
    private TableColumn<Food, String> Name;

    @FXML
    private TableColumn<Food, Double> Price;

    @FXML
    private TableColumn<Food, String> Catagories;

    @FXML
    private Button SearchButton;

    @FXML
    private MenuButton SearchBy;

    @FXML
    private TextField SearchField;

    @FXML
    private TextField SearchField2;

    @FXML
    private TextField SearchField3;

    @FXML
    private Text ShowText;

    @FXML
    private TableView<Food> TableView;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Text showText2;

    @FXML
    private Text showText3;

    @FXML
    private TextArea textArea;

    @FXML
    private TextArea CartTextArea;

    @FXML
    private Button ConfirmOrderButton;

    private int selected = -1;

    List<Restaurant> resList;

    Order orderList = new Order();

    int serial;

    private List<CartItem> shoppingCart = new ArrayList<>();
    // List <CartItem> sendOrderList = new ArrayList<>();

    public void initialize() {
        ID.setCellValueFactory(new PropertyValueFactory<>("RestaurantId"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Catagories.setCellValueFactory(new PropertyValueFactory<>("Category"));
        // TODO changed the cart text area here
        CartTextArea.setOpacity(0);
        textArea.setOpacity(0);

        TableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Food selectedFood = TableView.getSelectionModel().getSelectedItem();
                if (selectedFood != null) {
                    // updating order list
                    // sendOrderList.add(selectedFood);
                    try {
                        main.getNetworkUtil().write(selectedFood);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CartItem cartItem = findCartItem(selectedFood);
                    if (cartItem != null) {
                        cartItem.incrementQuantity();

                    } else {
                        shoppingCart.add(new CartItem(selectedFood, 1));
                    }

                    updateCartTextArea();
                    CartTextArea.setOpacity(1);
                } else {

                    CartTextArea.setText("No Food Item Added to Cart Yet!");
                    CartTextArea.setOpacity(1);
                }
            }
        });
    }

    private CartItem findCartItem(Food selectedFood) {
        for (CartItem cartItem : shoppingCart) {
            if (cartItem.getFood().equals(selectedFood)) {
                return cartItem;
            }
        }
        return null;
    }

    private void updateCartTextArea() {
        CartTextArea.setOpacity(1);
        ConfirmOrderButton.setOpacity(1);
        StringBuilder cartContents = new StringBuilder("Shopping Cart:\n");
        Double bill = 0.0;
        for (CartItem cartItem : shoppingCart) {
            Food item = cartItem.getFood();
            int quantity = cartItem.getQuantity();

            cartContents.append("Name: ").append(item.getName()).append("\n");
            cartContents.append("Price: ").append(item.getPrice()).append("  ");
            cartContents.append("x").append(quantity).append("\n\n");

            bill += item.getPrice() * quantity;
        }

        String formattedBill = String.format("%.2f", bill);
        cartContents.append("Total Bill : $" + formattedBill);

        CartTextArea.setText(cartContents.toString());
    }

    @FXML
    void GoBack(ActionEvent event) throws Exception {
        main.Option();
    }

    @FXML
    void ConfirmOrderAction(ActionEvent event) {
        // for(CartItem c : shoppingCart)
        // {
        // id = c.getFood().getRestaurantId();
        // break;
        // }
        orderList.setUserName(main.getUserName());
        System.out.println("in order confirm " + main.getUserName());
        for (CartItem c : shoppingCart) {
            System.out.println(c.getFood().getName() + " " + c.getQuantity());
        }
        orderList.setOrderedFoods(shoppingCart);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("ORDER CONFIRMATION : ");
        a.setContentText("Order placed successfully!\n\nHappy Eating!");
        a.showAndWait();
        CartTextArea.setText(null);
        try {
            main.getNetworkUtil().write(orderList);
            // TODO cleared shopping cart here
            shoppingCart.clear();
            main.Option();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SeachInRestaurantChecked(ActionEvent event) {
        CartTextArea.setOpacity(0);
        SearchField.setOpacity(0);
        SearchField2.setOpacity(0);
        SearchField3.setOpacity(0);
        ShowText.setText(null);
        showText2.setText(null);
        showText3.setText(null);
        TableView.setOpacity(1);
        ShowText.setOpacity(0);
        showText2.setOpacity(0);
        showText3.setOpacity(0);
        // TODO textArea
        //textArea.setOpacity(0);
        Show.setText("Total Food Items");
        // textArea.setOpacity(0);
        if (checkBox.isSelected()) {
            CartTextArea.setOpacity(1);
            textArea.setOpacity(0);
            TableView.setOpacity(1);
            ConfirmOrderButton.setOpacity(1);
            Show.setText("Get Costliest Food");
        }
    }

    @FXML
    void SearchFood(ActionEvent event) {
        TableView.setOpacity(1);
        List<Food> food = new ArrayList<>();
        String s1 = null, s2 = null, s3 = null;
        switch (selected) {
            case 1:
                CartTextArea.setOpacity(0);
                s1 = SearchField.getText();
                food = file.searchFoodbyName(s1);
                break;
            case 2:
                CartTextArea.setOpacity(1);
                s1 = SearchField.getText();
                s2 = SearchField2.getText();
                ConfirmOrderButton.setOpacity(1);
                food = file.searchFoodWithRestaurantName(s1, s2);
                break;
            case 3:
                CartTextArea.setOpacity(0);
                s1 = SearchField.getText();
                food = file.searchFoodWithCategory(s1);
                break;
            case 4:
                CartTextArea.setOpacity(1);
                s1 = SearchField.getText();
                s2 = SearchField2.getText();
                food = file.searchFoodWithCategoryinRestaurant(s1, s2);
                break;
            case 5:
                CartTextArea.setOpacity(0);
                s1 = SearchField.getText();
                s2 = SearchField2.getText();
                food = file.searchFoodbyPrice(Double.parseDouble(s1), Double.parseDouble(s2));
                break;
            case 6:
                CartTextArea.setOpacity(1);
                s1 = SearchField.getText();
                s2 = SearchField2.getText();
                s3 = SearchField3.getText();
                food = file.searchFoodbyPriceinRestaurant(Double.parseDouble(s1), Double.parseDouble(s2), s3);
                break;
            case 7:
                TableView.setOpacity(1);
                textArea.setOpacity(1);
                CartTextArea.setOpacity(0);
                ShowText.setText("Total Food Item : ");
                String t = "";
                int c[] = file.showAllRestaurants();
                List<Restaurant> rest = file.getRestaurant();
                for (int i = 0; i < c.length; i++) {
                    t += rest.get(i).getName() + " :  " + c[i] + "\n\n";
                }
                textArea.setText(t);
                break;
            case 8:
                TableView.setOpacity(1);
                textArea.setOpacity(0);
                CartTextArea.setOpacity(1);
                s1 = SearchField.getText();

                ShowText.setText("Costliest Food Item : ");
                food = file.GetCostliestFood(s1);
                break;
        }
        // System.out.println(food.size());
        ObservableList<Food> f = FXCollections.observableArrayList(food);
        TableView.setItems(f);
    }

    @FXML
    void byCategory(ActionEvent event) {
        CartTextArea.setOpacity(0);
        SearchButton.setOpacity(1);
        textArea.setOpacity(0);
        showText2.setOpacity(0);
        showText3.setOpacity(0);
        SearchField.setText(null);
        SearchField2.setText(null);
        SearchField3.setText(null);
        SearchField.setOpacity(1);
        SearchField3.setOpacity(0);
        ShowText.setText("Enter Category : ");
        ShowText.setOpacity(1);
        selected = 3;
        if (checkBox.isSelected()) {
            CartTextArea.setOpacity(1);
            showText2.setText("Enter Restaurant Name: ");
            showText2.setOpacity(1);
            SearchField2.setOpacity(1);
            selected = 4;
        }

    }

    @FXML
    void byName(ActionEvent event) {
        CartTextArea.setOpacity(0);
        SearchButton.setOpacity(1);
        textArea.setOpacity(0);
        showText2.setOpacity(0);
        showText3.setOpacity(0);
        SearchField.setText(null);
        SearchField2.setText(null);
        SearchField3.setOpacity(0);
        SearchField.setOpacity(1);
        ShowText.setText("Enter Name : ");
        ShowText.setOpacity(1);
        selected = 1;
        if (checkBox.isSelected()) {
            CartTextArea.setOpacity(1);
            showText2.setText("Enter Restaurant Name : ");
            showText2.setOpacity(1);
            SearchField2.setOpacity(1);
            selected = 2;
        }
    }

    @FXML
    void byPrice(ActionEvent event) {
        CartTextArea.setOpacity(0);
        SearchButton.setOpacity(1);
        textArea.setOpacity(0);
        showText3.setOpacity(0);
        SearchField.setText(null);
        SearchField2.setText(null);
        SearchField3.setText(null);
        SearchField3.setOpacity(0);
        SearchField.setOpacity(1);
        SearchField.setOpacity(1);
        ShowText.setText("Enter Lower Range : ");
        ShowText.setOpacity(1);
        SearchField2.setOpacity(1);
        showText2.setText("Enter Higher Range : ");
        showText2.setOpacity(1);
        selected = 5;
        if (checkBox.isSelected()) {
            CartTextArea.setOpacity(1);
            showText3.setText("Enter Restaurant Name : ");
            showText3.setOpacity(1);
            SearchField3.setOpacity(1);
            selected = 6;
        }
    }

    @FXML
    void ShowAll(ActionEvent event) {
        CartTextArea.setOpacity(0);
        // textArea.setOpacity(0);
        TableView.setOpacity(0);
        ShowText.setOpacity(1);
        showText2.setOpacity(0);
        showText3.setOpacity(0);
        // SearchButton.setOpacity(0);
        SearchField.setText(null);
        SearchField.setOpacity(0);
        SearchField2.setOpacity(0);
        SearchField3.setOpacity(0);
        if (checkBox.isSelected() == false) {
            selected = 7;
            SearchButton.fire();
        }
        if (checkBox.isSelected()) {
            TableView.setOpacity(1);
            CartTextArea.setOpacity(1);
            ShowText.setOpacity(1);
            ShowText.setText("Enter Restaurant Name: ");
            SearchButton.setOpacity(1);
            SearchField.setOpacity(1);
            selected = 8;
        }

    }

    public void setMain(Main main) {
        this.main = main;
    }

    void setResList(List<Restaurant> resList) {
        file = new File(resList);
        this.resList = resList;
        System.out.println("in Food search page");
        for (Restaurant r : resList) {
            System.out.println(r);
        }
    }

}

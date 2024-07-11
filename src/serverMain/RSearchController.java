package serverMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// import Database.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableCell;
// import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class RSearchController {
    private Main main;
    // File file = new File();

    int ret = -1;

    @FXML
    private Button BackButton;

    @FXML
    private Button SearchButton;

    @FXML
    private MenuButton SearchBy;

    @FXML
    private TextField SearchField;

    @FXML
    private TextField SearchField2;

    @FXML
    private Text ShowText;

    @FXML
    private Text showText2;

    @FXML
    private TableView<Restaurant> TableView;

    @FXML
    private TableColumn<Restaurant, String[]> Catagories;

    @FXML
    private TableColumn<Restaurant, Integer> ID;

    @FXML
    private TableColumn<Restaurant, String> Name;

    @FXML
    private TableColumn<Restaurant, String> Price;

    @FXML
    private TableColumn<Restaurant, Double> Score;

    @FXML
    private TableColumn<Restaurant, String> Zip;

    @FXML
    private TableView<Food> menuList;

    @FXML
    private TableColumn<Food, String> FoodCategory;

    @FXML
    private TableColumn<Food, Integer> FoodID;

    @FXML
    private TableColumn<Food, String> FoodName;

    @FXML
    private TableColumn<Food, Double> FoodPrice;

    @FXML
    private TextArea textArea;

    @FXML
    private TextArea CartTextArea;

    List<Restaurant> resList;

    @FXML
    private Button confirmButton;

    private List<CartItem> shoppingCart = new ArrayList<>();
    Order orderList = new Order();

    File file;

    public void categoryRes() {
        List<String> s = new ArrayList<>();
        s = file.showCategoryWise();
        textArea.setOpacity(1);
        String t = "Categorywise Restaurant Names: \n\n";
        for (String S : s) {
            // System.out.println(t);
            if (!S.equals(""))
                t += S + "\n";
        }
        textArea.setText(t);
    }

    public void initializee() {
        Catagories.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCategories()));
        Catagories.setCellFactory(column -> {
            TableCell<Restaurant, String[]> cell = new TableCell<Restaurant, String[]>() {
                @Override
                protected void updateItem(String[] categories, boolean empty) {
                    super.updateItem(categories, empty);
                    if (empty || categories == null) {
                        setText("");
                    } else {
                        setText(String.join(", ", categories));
                    }
                }
            };
            return cell;
        });
        Catagories.setCellValueFactory(new PropertyValueFactory<>("categories"));
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        Score.setCellValueFactory(new PropertyValueFactory<>("score"));
        Zip.setCellValueFactory(new PropertyValueFactory<>("zip"));

        TableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                // System.out.println("clicked");
                Restaurant selectedRestaurant = TableView.getSelectionModel().getSelectedItem();
                if (selectedRestaurant != null) {
                    // Populate the menuList TableView based on the selected restaurant.
                    initializeMenuList(selectedRestaurant);
                }
            }
        });
    }

    private void initializeMenuList(Restaurant selectedRestaurant) {

        List<Food> menu = selectedRestaurant.getMenuList();

        ObservableList<Food> menuItems = FXCollections.observableArrayList(menu);
        menuList.setItems(menuItems);

        FoodID.setCellValueFactory(new PropertyValueFactory<>("RestaurantId"));
        FoodName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        FoodPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        FoodCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));

        menuList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                // System.out.println("Menu item clicked");
                Food selectedFood = menuList.getSelectionModel().getSelectedItem();
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
    void ConfirmAction(ActionEvent event) {
        // int id =-1;
        // for (CartItem c : shoppingCart) {
        // id = c.getFood().getRestaurantId();
        // }
        // orderList.setUserName(main.getUserName(),file.ResWithID(id).getName());
        if (shoppingCart.size() == 0) {
            CartTextArea.setText("No Food Selected");
        } else {
            orderList.setUserName(main.getUserName());
            System.out.println("ORDER FROM CUSTOMER " + main.getUserName());
            for (CartItem c : shoppingCart) {
                System.out.println(c.getFood().getName() + " " + c.getQuantity());
            }
            orderList.setOrderedFoods(shoppingCart);
            CartTextArea.setText(null);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("ORDER CONFIRMATION : ");
            a.setContentText("Order placed successfully!\n\nHappy Eating!");
            a.showAndWait();
            try {
                main.getNetworkUtil().write(orderList);
                // TODO cleared cart here in res search
                shoppingCart.clear();
                main.Option();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void GoBack(ActionEvent event) throws Exception {
        main.Option();
    }

    @FXML
    void SearchRestaurant(ActionEvent event) throws Exception {
        TableView.setOpacity(1);
        // textArea.setOpacity(0);
        String text = null;
        String text2 = null;
        List<Restaurant> rList = new ArrayList<>();

        // Restaurant r = null;
        switch (ret) {
            case 1:
                text = SearchField.getText();
                rList = file.searchRestaurantbyName(text);
                // System.out.println(text);
                // System.out.println(rList.size());
                break;
            case 2:
                text = SearchField.getText();
                text2 = SearchField2.getText();
                double d1 = Double.parseDouble(text);
                double d2 = Double.parseDouble(text2);
                rList = file.searchRestaurantbyScore(d1, d2);
                break;
            case 3:
                text = SearchField.getText();
                rList = file.searchRestaurantbyCategory(text);
                break;
            case 4:
                text = SearchField.getText();
                rList = file.searchRestaurantbyPrice(text);
                break;
            case 5:
                text = SearchField.getText();
                rList = file.searchRestaurantbyZip(text);
                break;
            // case 6:
            // TableView.setOpacity(0);
            // s = file.showCategoryWise();
            // textArea.setOpacity(1);
            // String t = "";
            // for (String S : s) {
            // // System.out.println(S);
            // t += S + "\n\n";
            // }
            // textArea.setText(t);
            // break;
        }
        // main.ShowResultPage();
        // System.out.println("click");

        // TableView.getItems().clear();
        // if (rList != null) {
        // ObservableList<Restaurant> list = FXCollections.observableArrayList(rList);
        // }

        ObservableList<Restaurant> list = FXCollections.observableArrayList(rList);
        TableView.setItems(list);
    }

    void setMain(Main main) {
        this.main = main;
    }

    @FXML
    void handleOptionName(ActionEvent event) {
        SearchButton.setOpacity(1);
        // textArea.setOpacity(0);
        SearchField.setText(null);
        showText2.setOpacity(0);
        SearchField2.setOpacity(0);
        SearchField.setOpacity(1);
        ShowText.setOpacity(1);
        ShowText.setText("Enter Restaurant Name: ");
        ret = 1;
    }

    @FXML
    void handleOptionScore(ActionEvent event) {
        SearchButton.setOpacity(1);
        // textArea.setOpacity(0);
        SearchField.setText(null);
        SearchField2.setText(null);
        ShowText.setOpacity(1);
        ShowText.setText("Enter Lower Range: ");
        showText2.setOpacity(1);
        showText2.setText("Enter Higher Range:");
        SearchField.setOpacity(1);
        SearchField2.setOpacity(1);
        ret = 2;
    }

    @FXML
    void handleOptionCategory(ActionEvent event) {
        SearchButton.setOpacity(1);
        // textArea.setOpacity(0);
        SearchField.setText(null);
        showText2.setOpacity(0);
        SearchField2.setOpacity(0);
        ShowText.setOpacity(1);
        SearchField.setOpacity(1);
        ShowText.setText("Enter Category Name: ");
        ret = 3;

    }

    @FXML
    void handleOptionPrice(ActionEvent event) {
        SearchButton.setOpacity(1);
        // textArea.setOpacity(0);
        SearchField.setText(null);
        showText2.setOpacity(0);
        SearchField2.setOpacity(0);
        ShowText.setOpacity(1);
        SearchField.setOpacity(1);
        ShowText.setText("Enter Price: ");
        ret = 4;
    }

    @FXML
    void handleOptionZipCode(ActionEvent event) {
        SearchButton.setOpacity(1);
        // textArea.setOpacity(0);
        SearchField.setText(null);
        ShowText.setOpacity(1);
        ShowText.setText("Enter ZIP code: ");
        SearchField.setOpacity(1);
        showText2.setOpacity(0);
        SearchField2.setOpacity(0);
        ret = 5;
    }

    // @FXML
    // void handleOptionShowAll(ActionEvent event) {
    // // SearchField.setText(null);
    // // SearchField2.setText(null);
    // showText2.setOpacity(0);
    // SearchField.setOpacity(0);
    // SearchField2.setOpacity(0);
    // ShowText.setOpacity(1);
    // ShowText.setText("Category Wise Restaurant Name: ");
    // ret = 6;
    // SearchButton.setOpacity(0);
    // SearchButton.fire();
    // // List<String> all = file.showCategoryWise();
    // }

    void setResList(List<Restaurant> resList) {
        file = new File(resList);
        this.resList = resList;
        System.out.println("in search page");
        // for (Restaurant r : resList) {
        // System.out.println(r);
        // }
    }

}

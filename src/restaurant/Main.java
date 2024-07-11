package restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import serverMain.CartItem;
import serverMain.Order;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.Restaurant;

public class Main extends Application {

    private Stage stage;
    private NetworkUtil networkUtil;
    private Restaurant restaurant;

    private String userName;
    private Order orders;
    private List<Order> OrderList = new ArrayList<>();

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();
    }

    private void connectToServer() throws IOException, ClassNotFoundException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        networkUtil.write("R");
        new ReadThread(this);
    }

    public void showLoginPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Login");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void showHomePage(String userName) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.init(userName);
        controller.setMain(this);
        // controller.setName(userName);
        stage.setTitle("Home");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public void Option() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OptionList.fxml"));
        Parent root = loader.load();

        OptionController controller = loader.getController();
        controller.setMain(this);
        // controller.setName(userName);
        controller.initializee();
        stage.setTitle("Options");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void showRegistrationPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("registrationPage.fxml"));
        Parent root = loader.load();
        registrationController controller = loader.getController();
        controller.setMain(this);
        stage.setTitle("registration");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void ShowDetailsPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ShowDetailsPage.fxml"));
        Parent root = loader.load();
        showDetailsController controller = loader.getController();
        controller.setMain(this);
        // controller.setOrderInPage(orders);
        controller.initializee();
        stage.setTitle("details");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void ShowOrderPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("showCart.fxml"));
        Parent root = loader.load();
        showCartController controller = loader.getController();
        controller.setMain(this);
        // orderlist update
        controller.setOrderInPage(OrderList);
        controller.showOrder();
        stage.setTitle("details");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void addFoodPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddFoodPage.fxml"));
        Parent root = loader.load();
        addFoodController controller = loader.getController();
        controller.setMain(this);
        // controller.initialize(userName);
        // controller.setName(userName);
        stage.setTitle("adding food");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        System.out.println("IN MAIN");
        System.out.println(restaurant);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setOrders(Order orders) {
        List<CartItem> cart = orders.getOrderedFoods();
        // RestMap.get(order.getRestaurantName()).write(order);
        System.out.println("ORDERS IN MAIN FUNC REST");
        for (CartItem C : cart) {
            System.out.println(C.getFood().getName() + " " + C.getQuantity());
        }

        int id = -1;
        if (cart.size() != 0) {
            id = orders.getOrderedFoods().get(0).getFood().getRestaurantId();
        }

        if (restaurant.getId() == id) {
            this.orders = orders;

            OrderList.add(orders);
            System.out.println("order set");
        } else {
            System.out.println("not set");
        }

        for (Order temp : OrderList) {
            List<CartItem> cartt = temp.getOrderedFoods();
            // RestMap.get(order.getRestaurantName()).write(order);
            System.out.println("ORDERS IN Ordered list FUNC REST");
            for (CartItem C : cartt) {
                System.out.println(C.getFood().getName() + " " + C.getQuantity());
            }

        }

    }

    public Order getOrders() {
        return orders;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

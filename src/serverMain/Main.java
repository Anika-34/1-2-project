package serverMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.Restaurant;

public class Main extends Application {

    private Stage stage;
    private NetworkUtil networkUtil;
    private List<Restaurant> restaurants;
    private String userName;

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        restaurants = new ArrayList<>();
        stage = primaryStage;
        connectToServer();
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        networkUtil.write("C");
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
        controller.setResList(restaurants);
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
        
        stage.setTitle("Options");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void RestaurantSearch() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Rsearch.fxml"));
        Parent root = loader.load();

        RSearchController controller = loader.getController();
        controller.setMain(this);
        controller.setResList(restaurants);
        controller.initializee();
        controller.categoryRes();
        
        stage.setTitle("SearchRestaurants");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    public void FoodSearch() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Fsearch.fxml"));
        Parent root = loader.load();

        FSearchController controller = loader.getController();
        controller.setMain(this);
        controller.setResList(restaurants);
        stage.setTitle("SearchFoods");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    public void setRestaurants(List<Restaurant> restaurants)
    {
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants()
    {
        return restaurants;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
        System.out.println("in main "+this.userName);
    }

    public String getUserName()
    {
        return userName;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

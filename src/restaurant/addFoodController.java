package restaurant;

import Database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class addFoodController {
    private Main main;
    private File file = new File();

    @FXML
    private Button BackButton;

    @FXML
    private TextField FoodCategory;

    @FXML
    private TextField Price;

    @FXML
    private TextField foodName;

    @FXML
    private Button submitAction;

    @FXML
    void AddFoodButton(ActionEvent event) {
        // List <Restaurant> rest = file.searchRestaurantbyName(userName);
        Restaurant r = main.getRestaurant();
        int id = r.getId();
        Food f = new Food(id, FoodCategory.getText(), foodName.getText(), Double.parseDouble(Price.getText()));
        

        if(file.addFood(f))
        {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Menu Update Confirmation");
            a.setContentText("Food Added Succesfully to Menu!");
            a.showAndWait();
        }

        else{
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Menu Update Confirmation");
            a.setContentText("Food Already Exists.");
            a.showAndWait();
        }

        // for(Restaurant r : rest)
        // {
        //     System.out.println("menu after" +r.getMenuList().size());
        // }
        try {
            main.getNetworkUtil().write(f);
            file.writeToInputFile2();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void goBacktoOption(ActionEvent event) {
        try {
            main.Option();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

}

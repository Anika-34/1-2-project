package serverMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OptionController {
    private Main main;

    @FXML
    private Button fButton;

    @FXML
    private Button rButton;

    @FXML
    private Button BackButton;

    @FXML
    void SearchFood(ActionEvent event) throws Exception{
        main.FoodSearch();
    }

    @FXML
    void searchRestaurant(ActionEvent event) throws Exception{
        main.RestaurantSearch();
    }

    @FXML
    void BackAction(ActionEvent event) {
        try {
            main.showHomePage(main.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMain(Main main)
    {
        this.main = main;
    }

}



package serverMain;

import java.util.List;

import Database.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController {

    private Main main;

    @FXML
    private Button NextButton;

    @FXML
    private Label message;

    @FXML
    private ImageView image;

    @FXML
    private Button button;

    List <Restaurant> resList;

    @FXML
    void ProceedToNext(ActionEvent event) {
        try{
            // main.showOptions();
            main.Option();
            // main.showRestaurantNames();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void init(String msg) {
        message.setText("WELCOME " + msg);
        Image img = new Image(Main.class.getResourceAsStream("customerHome.png"));
        image.setImage(img);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMain(Main main) {
        this.main = main;
    }

    void setResList(List <Restaurant> resList)
    {
        this.resList = resList;
        // System.out.println("in home page");
        // for(Restaurant r : resList)
        // {
        //     System.out.println(r);
        // }
    }

}

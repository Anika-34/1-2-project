package restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController {

    // private String userName;

    private Main main;

    @FXML
    private Button NextButton;

    @FXML
    private Button RegButton;

    @FXML
    private Label message;

    @FXML
    private ImageView image;

    @FXML
    private Button button;

    @FXML
    private ImageView image1;

    @FXML
    void ProceedToNext(ActionEvent event) {
        try{
            // main.showOptions();
            System.out.println("username in homecontroller "+ main.getUserName());
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
        Image img = new Image(Main.class.getResourceAsStream("panda.png"));
        image.setImage(img);
        Image img1 = new Image(Main.class.getResourceAsStream("homePlate.png"));
        image1.setImage(img1);
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

    // public void setName(String userName) {
    //     this.userName = userName;
    // }

}

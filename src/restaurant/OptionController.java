package restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class OptionController {
    private Main main;
    // String userName;

    // @FXML
    // private Button fButton;

    @FXML
    private Button rButton;

    @FXML
    private Button back;

    @FXML
    private Text LoginInfo;

    public void initializee()
    {
        LoginInfo.setText("Logged in as " + main.getUserName());
    }

    
    // @FXML
    // void addFoodButton(ActionEvent event) {
    //     try {
    //         main.addFoodPage();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    @FXML
    void viewDetailsButton(ActionEvent event) throws Exception{
        System.out.println("username in option "+main.getUserName());
        main.ShowDetailsPage();
    }

    @FXML
    void goBackToLogin(ActionEvent event) {
        try {
            main.showHomePage(main.getUserName());;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMain(Main main)
    {
        this.main = main;
    }

    // public void setName(String userName) {
    //     this.userName = userName;
    // }

}



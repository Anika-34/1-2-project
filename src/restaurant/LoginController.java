package restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.LoginDTO;

import java.io.IOException;


public class LoginController {

    private Main main;
    
    // @FXML
    // private Button RegButton;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;
    
    @FXML
    private ImageView Logo;

    public void initialize()
    {
        Image img = new Image(Main.class.getResourceAsStream("LogoLogin.png"));
        Logo.setImage(img);
    }

    @FXML
    void loginAction(ActionEvent event) {
        String userName = userText.getText();
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        try {
            main.getNetworkUtil().write(loginDTO);
            // loginDTO.setClientType("R");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    // @FXML
    // void goToRegistration(ActionEvent event) {
    //     try {
    //         main.showRegistrationPage();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    void setMain(Main main) {
        this.main = main;
    }

}

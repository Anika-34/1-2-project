package restaurant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Database.File;
import Database.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class registrationController {
    private Main main;

    @FXML
    private Button back;

    @FXML
    private TextField cat1;

    @FXML
    private TextField cat2;

    @FXML
    private TextField cat3;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField score;

    @FXML
    private Button submitButton;

    @FXML
    private TextField zip;

    @FXML
    private TextField Password;

    HashMap<String, String> userMap2;

    File file = new File();
    List<Restaurant> rest = file.getRestaurant();

    @FXML
    void goBacktoLogin(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submitAction(ActionEvent event) throws IOException {
        // Restaurant r = null;
        int id = rest.size() + 1;
        System.out.println("size  " + rest.size());
        String Name = name.getText();
        String Price = price.getText();
        String Zip = zip.getText();
        Double Score = Double.parseDouble(score.getText());
        String c1 = cat1.getText();
        String c2 = cat2.getText();
        String c3 = cat3.getText();
        String[] categories = new String[3];
        int index = 0;
        if (c1.isEmpty() == false)
            categories[index++] = c1;
        System.out.println("c1 " + index + c1);
        if (c2.isEmpty() == false)
            categories[index++] = c2;
        System.out.println("c2 " + index + c2);
        if (c3.isEmpty() == false)
            categories[index++] = c3;
        System.out.println("c3 " + index + c3);

        Restaurant r = new Restaurant(id, Name, Score, Price, Zip, categories);

        if (file.addNewRestaurant(r)) {
            for (Restaurant R : rest) {
                System.out.println(R.getName());
                System.out.println(R.getCategories()[0] + " " + R.getCategories()[1] + " " + R.getCategories()[2]);
            }
            try {
                file.writeToInputFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String passWord = Password.getText();

            try {
                readUser();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\resInfo.txt"))) {
                for (Map.Entry<String, String> entry : userMap2.entrySet()) {
                    bw.write(entry.getKey() + "," + entry.getValue());
                    bw.write(System.lineSeparator());
                }
                bw.write(Name + "," + passWord);
                bw.write(System.lineSeparator());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Registration Confirmation");
            a.setContentText("Restaurant Added Succesfully!");
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Registration Confirmation");
            a.setContentText("Restaurant Already Exists.");
            a.showAndWait();
        }

        // ReadThreadServer(HashMap<String, String> map, NetworkUtil networkUtil)
        // NetworkUtil networkUtil = new NetworkUtil(null);
        // new ReadThreadServer(userMap2,networkUtil);
        name.setText(null);
        price.setText(null);
        zip.setText(null);
        score.setText(null);
        cat1.setText("");
        cat2.setText("");
        cat3.setText("");
        Password.setText(null);
    }

    public void readUser() throws Exception {
        userMap2 = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(
                "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\resInfo.txt"));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            String[] array = line.split(",", -1);

            userMap2.put(array[0], array[1]);
        }
        br.close();
    }

    public void setMain(Main main) {
        this.main = main;
    }

}

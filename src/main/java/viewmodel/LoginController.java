package viewmodel;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LoginController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private GridPane rootpane;
    @FXML
    private Label topLabel;

    private FileReader fr;
    private Scanner sc;
    private int attempts = 0;
    public static String currUser;

    public void initialize() {
        try {
            fr = new FileReader("src/main/resources/logins.txt");
            sc = new Scanner(fr);
        } catch (FileNotFoundException e) {
            System.out.println("Logins file not found. Cannot proceed with login.");
        }

        rootpane.setBackground(new Background(
                createImage("https://edencoding.com/wp-content/uploads/2021/03/layer_06_1920x1080.png"),
                null, null, null, null, null
        ));

        rootpane.setOpacity(0);
        FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(7), rootpane);
        fadeOut2.setFromValue(0);
        fadeOut2.setToValue(1);
        fadeOut2.play();
    }

    private static BackgroundImage createImage(String url) {
        return new BackgroundImage(
                new Image(url),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
    }

    @FXML
    public void login(ActionEvent actionEvent) {
        if (attempts >= 10) {
            topLabel.setText("Too many attempts. Try again later.");
            return;
        }
        attempts++;

        System.out.println("Checking login...");
        topLabel.setText("Checking login...");
        if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            topLabel.setText("Username or password cannot be empty.");
            return;
        }

        String username = usernameTextField.getText();
        String password = passwordField.getText();

        try {
            boolean valid = false;
            fr = new FileReader("src/main/resources/logins.txt");
            sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                if (username.equals(sc.nextLine()) && password.equals(sc.nextLine())) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                username = currUser ;
                loadScene(actionEvent, "/view/db_interface_gui.fxml");
            } else {
                topLabel.setText("Invalid credentials. Try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signUp(ActionEvent actionEvent) {
        loadScene(actionEvent, "/view/signUp.fxml");
    }

    private void loadScene(ActionEvent actionEvent, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root, 900, 600);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {

    public TextField usernameTextField;
    public PasswordField passwordField;
    public Label topLabel;

    private int attempts = 0;

    public void login(ActionEvent actionEvent) throws FileNotFoundException {
        boolean valid = false;

        // Stop accepting after 10 attempts
        if (attempts >= 10) {
            topLabel.setText("Too many attempts. Please try again later.");
            return;
        } else {
            attempts++;
        }

        if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            topLabel.setText("Username or password is empty. Please try again.");
            return;
        }

        String[] login = new String[2];
        login[0] = usernameTextField.getText();
        login[1] = passwordField.getText();

        try (FileReader fr = new FileReader("src/main/resources/logins.txt");
             Scanner sc = new Scanner(fr)) {

            while (sc.hasNextLine()) {
                String username = sc.nextLine();
                String password = sc.nextLine();

                if (login[0].equals(username) && login[1].equals(password)) {
                    valid = true;
                    break;
                }
            }

            if (!valid) {
                topLabel.setText("Invalid username or password. (Attempt " + attempts + ")");
            } else {
                topLabel.setText("Valid login! Loading...");
                Parent root = FXMLLoader.load(getClass().getResource("/view/db_interface_gui.fxml"));
                Scene scene = new Scene(root, 920, 620);
                scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUp(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/signup.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




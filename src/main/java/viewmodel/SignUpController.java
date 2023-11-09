package viewmodel;

import dao.DbConnectivityClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Person;

public class SignUpController {

    public TextField usernameField;
    public PasswordField passwordField;

    public void createNewAccount(ActionEvent actionEvent) {
        try {
            // Collect form data
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate fields
            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Both fields must be filled!");
                alert.showAndWait();
                return;
            }

            // Connect to database and insert new user
            DbConnectivityClass db = new DbConnectivityClass();
            db.insertUser(new Person("FirstName", "LastName", "Department", "Major", username, ""));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Account successfully created! You can now log in.");
            alert.showAndWait();

            // Redirect back to login page
            goBack(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error creating account. Please try again.");
            alert.showAndWait();
        }
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
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

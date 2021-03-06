import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TabMonitor extends Application implements Initializable {
    boolean isPopup = true; // Fixes the new account tab from opening up twice.

    @Override
    public void start(Stage stage) throws Exception {
        Parent login = FXMLLoader.load(getClass().getResource("Tables/LoginPanel.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
//        Parent table = FXMLLoader.load(getClass().getResource("Interface/Login.fxml"));
        // Set all panels
        stage.getIcons().add(new Image("/Sprites/Logo.png"));
        Scene scene = new Scene(login);
        stage.setScene(scene);
        stage.setTitle("2160162");
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // All FXML Methods and variables
    @FXML
    private Button signin;
    public TextField email;
    public PasswordField pass;
    public Text error;

    /* Login button trigger */
    public void login() throws IOException {
        DatabaseAPI database = new DatabaseAPI();
        String inputEmail = email.getText();
        String inputPass = pass.getText();
        database.checkAccount(inputEmail, inputPass);
        if (email.getText().trim().isEmpty()) {
            error.setText("Please type in your email");
        } else if (pass.getText().trim().isEmpty()) {
            error.setText("Please type in your password");
        } else if (database.checkAccount(inputEmail,inputPass)) {
            Stage stage;
            Parent root;
            if (database.checkAdmin(inputEmail, inputPass) == 1) {
                root = FXMLLoader.load(getClass().getResource("Tables/AdminTable.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("Tables/UserTable.fxml"));
            }
            System.out.println("Validated:" +database.validated);
            stage = (Stage) signin.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setMaximized(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } else {
            error.setText("Login failed");
        }
    }

    public void setError(String string) {
        error.setText(string);
    }

    /* Popup to create account */
    public void newUser() throws IOException {
        if (isPopup) {
            isPopup = false;

            Stage popupWindow = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Tables/NewUser.fxml"));
            Scene scene = new Scene(root);

            popupWindow.setTitle("Create account");
            popupWindow.setScene(scene);
            popupWindow.showAndWait();
            isPopup = false;
        } else {
            TabMonitor tabMonitor = new TabMonitor();
            tabMonitor.setError("You're already creating an account");
        }
    }

}
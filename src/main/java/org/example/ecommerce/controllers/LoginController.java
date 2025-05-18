package org.example.ecommerce.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.ecommerce.models.User;
import org.example.ecommerce.services.AuthService;
import org.example.ecommerce.utils.Session;

public class LoginController {

    @FXML
    private ImageView decorativeImage;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final AuthService authService = new AuthService();

    public void initialize() {
        try {
            Image img = new Image(getClass().getResource("/assets/Sign.jpg").toExternalForm());
            decorativeImage.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML

    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        User loggedInUser = authService.login(email, password);

        if (loggedInUser != null) {
            String role = loggedInUser.getRole();
            System.out.println("Logged in role: " + role);

            try {
                FXMLLoader loader;
                String path;

                if ("admin".equalsIgnoreCase(role)) {
                    path = "/views/admin_dashboard.fxml";
                } else {
                    path = "/views/home.fxml";
                }

                loader = new FXMLLoader(getClass().getResource(path));
                Parent root = loader.load();
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Welcome " + loggedInUser.getName());
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load the page.");
            }

        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }


    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
    @FXML
    private void goToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Sign Up page.");
        }
    }

}

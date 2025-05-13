package org.example.ecommerce.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private ImageView decorativeImage;

    @FXML
    private TextField emailField, codeField;
    @FXML
    private PasswordField newPasswordField, confirmPasswordField;

    @FXML
    private Button verifyButton, resetPassButton;

    private String generatedCode;

    @FXML
    public void initialize() {
        try {
            Image img = new Image(getClass().getResource("/assets/Sign.jpg").toExternalForm());
            decorativeImage.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendCode() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            showAlert("Error", "Please enter your email.");
            return;
        }


        generatedCode = generateCode();
        showAlert("Verification Code", "Your code is: " + generatedCode);

        codeField.setVisible(true);
        verifyButton.setVisible(true);
    }

    @FXML
    private void handleVerifyCode() {
        if (codeField.getText().equals(generatedCode)) {
            newPasswordField.setVisible(true);
            confirmPasswordField.setVisible(true);
            resetPassButton.setVisible(true);
            showAlert("Verified", "You can now reset your password.");
        } else {
            showAlert("Invalid Code", "Please check your code again.");
        }
    }

    @FXML
    private void handleResetPassword() {
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (!newPass.equals(confirmPass)) {
            showAlert("Mismatch", "Passwords do not match.");
            return;
        }

        showAlert("Success", "Your password has been reset!");

    }

    @FXML
    private void goToLogin() {
        System.out.println("Back to login");

    }

    private String generateCode() {
        String chars = "ABCDEFGH0123456789";
        StringBuilder code = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return code.toString();
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}

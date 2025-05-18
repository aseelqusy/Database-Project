package org.example.ecommerce.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.ecommerce.utils.Session;



import java.io.IOException;

public class HomeController {

    @FXML
    private ImageView heroImage;
    @FXML
    private ImageView userAvatar;


    @FXML
    private TextField searchField;
    @FXML private Button adminDashboardBtn;

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() == null) {
            System.out.println("No user session found!");
            return;
        }

        if (Session.getCurrentUser().getRole().equalsIgnoreCase("admin")) {
            adminDashboardBtn.setVisible(true);
        } else {
            adminDashboardBtn.setVisible(false);
        }
    }

    @FXML
    private void goToProducts(ActionEvent event) {
        openProductsPage("all");
    }

    @FXML
    private void filterPhones(ActionEvent event) {
        openProductsPage("phones");
    }

    @FXML
    private void filterWatches(ActionEvent event) {
        openProductsPage("games");
    }

    @FXML
    private void filterCameras(ActionEvent event) {
        openProductsPage("cameras");
    }

    @FXML
    private void filterHeadphones(ActionEvent event) {
        openProductsPage("headphones");
    }

    @FXML
    private void filterComputers(ActionEvent event) {
        openProductsPage("computers");
    }

    @FXML
    private void filterGaming(ActionEvent event) {
        openProductsPage("gaming");
    }
    @FXML
    private void filterGames(ActionEvent event) {
        openProductsPage("games");
    }
    @FXML
    private void goToAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) adminDashboardBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openProductsPage(String category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/products.fxml"));
            Parent root = loader.load();

            ProductsController controller = loader.getController();
            controller.setSelectedCategory(category);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/products.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Products - " + category);
            stage.setScene(scene);
            stage.show();

            heroImage.getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
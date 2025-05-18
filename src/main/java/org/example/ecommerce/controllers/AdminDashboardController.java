package org.example.ecommerce.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private void openManageProducts() {
        loadPage("/views/ManageProducts.fxml", "Manage Products");
    }

    @FXML
    private void openManageUsers() {
        loadPage("/views/ManageUsers.fxml", "Manage Users");
    }

    @FXML
    private void openReports() {
        loadPage("/views/Reports.fxml", "Sales Reports");
    }

    @FXML
    private void openOrders() {
        loadPage("/views/Orders.fxml", "Orders");
    }

    @FXML
    private void handleLogout() {
        loadPage("/views/login.fxml", "Login");
    }
    @FXML
    private void openHomePage() {
        navigate("/views/home.fxml", "Home Page");
    }

    @FXML
    private void openProductsPage() {
        navigate("/views/products.fxml", "Products");
    }

    @FXML
    private void openProfilePage() {
        navigate("/views/profile.fxml", "My Profile");
    }

    private void navigate(String path, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            if (path.contains("home")) {
                scene.getStylesheets().add(getClass().getResource("/styles/home.css").toExternalForm());
            } else if (path.contains("products")) {
                scene.getStylesheets().add(getClass().getResource("/styles/products.css").toExternalForm());
            } else if (path.contains("admin_dashboard")) {
                scene.getStylesheets().add(getClass().getResource("/styles/admin.css").toExternalForm());
            }

            Stage stage = (Stage) javafx.stage.Window.getWindows().filtered(w -> w.isShowing()).get(0);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadPage(String path, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = (Stage) javafx.stage.Window.getWindows().filtered(w -> w.isShowing()).get(0);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

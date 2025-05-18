package org.example.ecommerce.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ecommerce.models.Product;
import org.example.ecommerce.services.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ProductsController implements Initializable {

    @FXML private FlowPane productsFlowPane;
    @FXML private Label categoryTitle;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> sortCombo;
    @FXML private ComboBox<String> filterTypeCombo;
    @FXML private VBox brandFilterBox;
    @FXML private TextField brandSearchField;

    private String selectedCategory;
    private List<Product> allProducts = new ArrayList<>();
    private Set<String> selectedBrands = new HashSet<>();

    public void setSelectedCategory(String category) {
        this.selectedCategory = category;
        categoryTitle.setText(category);
        loadProducts();
    }

    private void loadProducts() {
        try {
            ProductService service = new ProductService();
            allProducts = service.getProductsByCategory(selectedCategory);
            generateBrandFilters();
            displayProducts(allProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateBrandFilters() {
        brandFilterBox.getChildren().clear();
        Set<String> brands = allProducts.stream()
                .map(Product::getBrand)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (String brand : brands) {
            CheckBox cb = new CheckBox(brand);
            cb.setOnAction(e -> {
                if (cb.isSelected()) {
                    selectedBrands.add(brand);
                } else {
                    selectedBrands.remove(brand);
                }
                applyFilters();
            });
            brandFilterBox.getChildren().add(cb);
        }
    }

    private void applyFilters() {
        List<Product> filtered = allProducts;

        if (!selectedBrands.isEmpty()) {
            filtered = filtered.stream()
                    .filter(p -> selectedBrands.contains(p.getBrand()))
                    .collect(Collectors.toList());
        }

        String keyword = searchField.getText().toLowerCase();
        String filterType = filterTypeCombo.getValue();

        if (!keyword.isEmpty() && filterType != null) {
            switch (filterType) {
                case "Name" -> filtered = filtered.stream()
                        .filter(p -> p.getName().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                case "Genre" -> filtered = filtered.stream()
                        .filter(p -> p.getGenre() != null && p.getGenre().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                case "Price" -> {
                    try {
                        double price = Double.parseDouble(keyword);
                        filtered = filtered.stream()
                                .filter(p -> p.getPrice() <= price)
                                .collect(Collectors.toList());
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        displayProducts(filtered);
    }

    private void displayProducts(List<Product> products) {
        productsFlowPane.getChildren().clear();
        for (Product product : products) {
            VBox card = createProductCard(product);
            if (card != null) {
                productsFlowPane.getChildren().add(card);
            }
        }
    }

    private VBox createProductCard(Product product) {
        Image imageFile;
        try {
            URL imageUrl = getClass().getResource("/assets/" + product.getImagePath());
            if (imageUrl == null) throw new IOException("Image not found: " + product.getImagePath());
            imageFile = new Image(imageUrl.toExternalForm());
        } catch (Exception e) {
            System.err.println("[Image Error] " + e.getMessage());
            imageFile = new Image(Objects.requireNonNull(getClass().getResource("/assets/default.png")).toExternalForm());
        }

        ImageView image = new ImageView(imageFile);
        image.setFitWidth(200);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.getStyleClass().add("product-image");

        Label name = new Label(product.getName());
        name.getStyleClass().add("product-name");

        Label price = new Label("$" + product.getPrice());
        price.getStyleClass().add("product-price");

        Button buyNow = new Button("Buy Now");
        buyNow.getStyleClass().add("add-to-cart-btn");

        VBox box = new VBox(image, name, price, buyNow);
        box.getStyleClass().add("product-card");
        box.setSpacing(10);
        return box;
    }

    @FXML
    private void goBack() {
        navigateTo("/views/home.fxml", "Home");
    }

    @FXML
    private void handleLogout() {
        navigateTo("/views/login.fxml", "Login");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) productsFlowPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void filterBrandList() {
        String query = brandSearchField.getText().toLowerCase();
        brandFilterBox.getChildren().forEach(node -> {
            if (node instanceof CheckBox cb) {
                cb.setVisible(cb.getText().toLowerCase().contains(query));
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        if (filterTypeCombo != null) {
            filterTypeCombo.getItems().addAll("Name", "Genre", "Price");
            filterTypeCombo.setValue("Name");
        }
    }
}

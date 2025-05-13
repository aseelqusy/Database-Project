package org.example.ecommerce.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class ProductsController {

    @FXML
    private FlowPane productsContainer;

    @FXML
    private Label categoryLabel;

    @FXML
    private TextField searchField;

    private String currentCategory = "all";

    public void setCategory(String category) {
        this.currentCategory = category;
        categoryLabel.setText(capitalize(category) + " Products");
        loadProducts();
    }

    private void loadProducts() {
        productsContainer.getChildren().clear();
        List<Product> productList = getMockProducts();

        for (Product product : productList) {
            if (!currentCategory.equals("all") && !product.getCategory().equalsIgnoreCase(currentCategory))
                continue;

            VBox card = createProductCard(product);
            productsContainer.getChildren().add(card);
        }
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.getStyleClass().add("product-card");
        card.setPadding(new Insets(10));

        ImageView imageView = new ImageView(new Image(getClass().getResource("/assets/sample_product.jpg").toExternalForm()));
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("product-name");

        Label priceLabel = new Label("$" + product.getPrice());
        priceLabel.getStyleClass().add("product-price");

        Button addToCart = new Button("Add to Cart");
        addToCart.getStyleClass().add("add-to-cart-btn");

        card.getChildren().addAll(imageView, nameLabel, priceLabel, addToCart);
        return card;
    }

    private List<Product> getMockProducts() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("iPhone 14 Pro", "phones", 1099));
        list.add(new Product("Galaxy Watch 6", "smartwatches", 399));
        list.add(new Product("Canon EOS R50", "cameras", 899));
        list.add(new Product("Sony WH-1000XM5", "headphones", 299));
        list.add(new Product("MSI GL65 Laptop", "computers", 999));
        list.add(new Product("PlayStation 5", "gaming", 499));
        return list;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private static class Product {
        private final String name;
        private final String category;
        private final double price;

        public Product(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public String getName() { return name; }
        public String getCategory() { return category; }
        public double getPrice() { return price; }
    }
}
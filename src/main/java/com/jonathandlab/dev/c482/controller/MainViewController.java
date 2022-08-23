package com.jonathandlab.dev.c482.controller;

import com.jonathandlab.dev.c482.NavigationHelper;
import com.jonathandlab.dev.c482.model.Inventory;
import com.jonathandlab.dev.c482.model.Part;
import com.jonathandlab.dev.c482.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.jonathandlab.dev.c482.ModalHelper.displayAlert;

/**
 * @author Jonathan Dowdell
 */
public class MainViewController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private TextField partSearchBox;

    @FXML
    public Button partSearchButton;

    @FXML
    private TextField productSearchBox;

    @FXML
    public Button productSearchButton;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Part, Integer> productIDColumn;

    @FXML
    private TableColumn<Part, String> productNameColumn;

    @FXML
    private TableColumn<Part, Integer> productInventoryColumn;

    @FXML
    private TableColumn<Part, Double> productPriceColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.requestFocus();
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Navigate to PartView
     * @param event ActionEvent
     * @throws IOException error
     */
    @FXML
    private void addPart(ActionEvent event) throws IOException {
        NavigationHelper.gotoPartView(null, event);
    }

    /**
     * Navigates to PartView with Part
     * @param event ActionEvent
     * @throws IOException error
     */
    @FXML
    private void modifyPart(ActionEvent event) throws IOException {
        try {
            Part part = Optional.ofNullable(partsTable.getSelectionModel().getSelectedItem())
                    .orElseThrow();
            NavigationHelper.gotoPartView(part, event);
        } catch (Exception e) {
            displayAlert(Alert.AlertType.ERROR,
                    "Part Modification Error",
                    "There was no part selected.",
                    "Please select a part to modify.");
        }
    }

    /**
     * Delete Part
     */
    @FXML
    private void deletePart() {
        try {
            Part part = Optional.ofNullable(partsTable.getSelectionModel().getSelectedItem())
                    .orElseThrow();

            Alert alert = displayAlert(Alert.AlertType.CONFIRMATION, "Delete Part", "Are you sure you want to delete this part?", "Press OK to delete the part. \nPress Cancel to cancel the deletion.");

            if (alert.getResult() == ButtonType.OK) {
                Inventory.deletePart(part);
            } else  {
                alert.close();
            }
        } catch (Exception e) {
            displayAlert(Alert.AlertType.ERROR,
                    "Part Deletion Error",
                    "The part was NOT deleted.",
                    "Please select a part to delete.");
        }
    }

    /**
     * Search for parts in Parts Table
     */
    @FXML
    private void partSearchBoxButtonAction() {
        String partSearchText = partSearchBox.getText().trim();
        ObservableList<Part> partsFound = Inventory.lookupPart(partSearchText);
        partsTable.setItems(partsFound);
    }

    /**
     * Restores Parts Table on empty partSearchTextfield
     */
    @FXML
    private void partSearchBoxKeyPressed() {
        if (partSearchBox.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Navigate to ProductView
     * @param event ActionEvent
     * @throws IOException error
     */
    @FXML
    private void addProduct(ActionEvent event) throws IOException {
        NavigationHelper.gotoProductView(null, event);
    }

    /**
     * Navigate to ProductView using Product
     * @param event ActionEvent
     */
    @FXML
    private void modifyProduct(ActionEvent event) {
        try {
            Product product = Optional.ofNullable(productTable.getSelectionModel().getSelectedItem())
                    .orElseThrow();
            NavigationHelper.gotoProductView(product, event);
        } catch (Exception e) {
            displayAlert(Alert.AlertType.ERROR,
                    "Product Modification Error",
                    "No product to modify.",
                    "Please select a product to modify.");
        }
    }

    /**
     * Delete Product if no associated parts
     */
    @FXML
    private void deleteProduct() {
        try {
            Product product = Optional.ofNullable(productTable.getSelectionModel().getSelectedItem())
                    .orElseThrow();
            Alert alert = displayAlert(Alert.AlertType.CONFIRMATION, "Delete Product", "Are you sure you want to delete this product?", "Press OK to delete the product. \nPress Cancel to cancel the deletion.");

            if (alert.getResult() == ButtonType.OK) {
                boolean deleteResults = Inventory.deleteProduct(product);
                if (!deleteResults) {
                    displayAlert(Alert.AlertType.ERROR,
                            "Product Deletion Error",
                            "The product was NOT deleted.",
                            "Product has associated parts. Please modify and remove parts");
                }
            } else  {
                alert.close();
            }
        } catch (Exception e) {
            displayAlert(Alert.AlertType.ERROR,
                    "Product Deletion Error",
                    "The product was NOT deleted.",
                    "Please select a product to delete.");
        }
    }

    /**
     * Search for products in Products Table
     */
    @FXML
    private void productSearchBoxButtonAction() {
        String searchString = productSearchBox.getText();
        ObservableList<Product> products = Inventory.lookupProduct(searchString);
        productTable.setItems(products);
    }

    /**
     * Restores Product Table on empty partSearchTextfield
     */
    @FXML
    private void productSearchBoxKeyPressed() {
        if (productSearchBox.getText().isEmpty()) {
            productTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Close Program
     */
    @FXML
    private void exitProgram() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

}

package com.jonathandlab.dev.c482.controller;

import com.jonathandlab.dev.c482.NavigationHelper;
import com.jonathandlab.dev.c482.model.Inventory;
import com.jonathandlab.dev.c482.model.Part;
import com.jonathandlab.dev.c482.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.jonathandlab.dev.c482.ModalHelper.displayAlert;
import static com.jonathandlab.dev.c482.model.Inventory.getAllParts;

/**
 * @author Jonathan Dowdell
 */
public class ProductViewController implements Initializable {

    @FXML
    public Label mainLabel;

    @FXML
    public TextField partSearchTextfield;

    @FXML
    public Button partSearchButton;

    @FXML
    public TableView<Part> partsTable;

    @FXML
    public TableColumn<Part, String> partIDColumn;

    @FXML
    public TableColumn<Part, String> partNameColumn;

    @FXML
    public TableColumn<Part, Integer> partInventoryColumn;

    @FXML
    public TableColumn<Part, Double> partPriceColumn;

    @FXML
    public TableView<Part> associatedPartsTable;

    @FXML
    public TableColumn<Part, String> associatedPartIDColumn;

    @FXML
    public TableColumn<Part, String> associatedPartNameColumn;

    @FXML
    public TableColumn<Part, Integer> associatedPartInventoryColumn;

    @FXML
    public TableColumn<Part, Double> associatedPartPriceColumn;

    @FXML
    public TextField productIDTextfield;

    @FXML
    public TextField productNameTextfield;

    @FXML
    public TextField productInventoryTextfield;

    @FXML
    public TextField productPriceTextfield;

    @FXML
    public TextField productInventoryMaxTextfield;

    @FXML
    public TextField productInventoryMinTextfield;

    private final ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    private boolean editing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableConfiguration();
    }

    /**
     * Loads View with data from Product
     * @param product Product to be used
     */
    public void loadProductData(Product product) {
        try {
            Product targetProduct = Optional.ofNullable(product)
                    .orElseThrow();
            productIDTextfield.setText(String.valueOf(targetProduct.getProductId()));
            productNameTextfield.setText(targetProduct.getName());
            productInventoryTextfield.setText(String.valueOf(targetProduct.getInStock()));
            productPriceTextfield.setText(String.valueOf(targetProduct.getPrice()));
            productInventoryMaxTextfield.setText(String.valueOf(targetProduct.getMax()));
            productInventoryMinTextfield.setText(String.valueOf(targetProduct.getMin()));
            associatedPartsList.addAll(targetProduct.getAssociatedParts());
            editing = true;
            mainLabel.setText("Modify Product");
        } catch (Exception ignored) {}
    }

    /**
     * Configuration of Tables
     */
    private void tableConfiguration() {
        partsTable.setItems(getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        associatedPartsTable.setItems(associatedPartsList);
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Search for parts in Parts Table
     */
    @FXML
    private void searchParts() {
        String searchText = partSearchTextfield.getText();
        ObservableList<Part> searchedProducts = Inventory.lookupPart(searchText);
        partsTable.setItems(searchedProducts);
    }

    /**
     * Restores Parts Table on empty partSearchTextfield
     */
    @FXML
    private void partSearchBoxKeyPressed() {
        if (partSearchTextfield.getText().isEmpty()) {
            partsTable.setItems(getAllParts());
        }
    }

    /**
     * Adds Associated Part to associatedPartsList
     * @return Boolean
     */
    @FXML
    private boolean addAssociatedPart() {
        try {
            Part selectedPart = Optional.ofNullable(partsTable.getSelectionModel().getSelectedItem())
                    .orElseThrow();
            for (Part part : associatedPartsList) {
                if (selectedPart.getId() == part.getId()) {
                    return false;
                }
            }
            return associatedPartsList.add(selectedPart);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Removes Associated Part to associatedPartsList
     */
    @FXML
    private void removeAssociatedPart() {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        associatedPartsList.remove(selectedPart);
    }

    /**
     * Saves Product
     * @param event ActionEvent
     * @throws IOException error
     */
    @FXML
    private void saveProduct(ActionEvent event) throws IOException {
        List<String> errors = validChecks();
        if (errors.isEmpty()) {
            String idValue = Objects.equals(productIDTextfield.getText(), "Auto Generated") ? "-1" : productIDTextfield.getText();
            int productID = Integer.parseInt(idValue);
            String productName = productNameTextfield.getText();
            int productInventory = Integer.parseInt(productInventoryTextfield.getText());
            double productPrice = Double.parseDouble(productPriceTextfield.getText());
            int productInventoryMax = Integer.parseInt(productInventoryMaxTextfield.getText());
            int productInventoryMin = Integer.parseInt(productInventoryMinTextfield.getText());

            Product product = new Product(
                    productID, productName, productPrice, productInventory, productInventoryMin, productInventoryMax
            );
            product.setAssociatedParts(FXCollections.observableArrayList());
            associatedPartsList.forEach(product::addAssociatedPart);
            if (editing) {
                Inventory.updateProduct(product);
            } else {
                Inventory.addProduct(product);
            }
            NavigationHelper.gotoMainView(event);
        } else {
            String errorMessages = errors.stream().map(error -> error + "\n").collect(Collectors.joining());
            System.out.println(errorMessages);
            displayAlert(Alert.AlertType.ERROR, "Error", "Please Address Error(s)", errorMessages);
        }
    }

    /**
     * Returns to MainView
     * @param event ActionEvent
     * @throws IOException error
     */
    @FXML
    private void cancelAction(ActionEvent event) throws IOException {
        NavigationHelper.gotoMainView(event);
    }


    /**
     * Checks for errors in fields
     * @return List of Errors (strings)
     */
    private List<String> validChecks() {
        ArrayList<String> errors = new ArrayList<>();

        if (productNameTextfield.getText().isEmpty()) {
            errors.add("Name field is missing.");
        }

        if (productInventoryTextfield.getText().isEmpty()) {
            errors.add("Inventory field is missing.");
        }

        if (productPriceTextfield.getText().isEmpty()) {
            errors.add("Price field is missing.");
        }

        if (productInventoryMaxTextfield.getText().isEmpty()) {
            errors.add("Max field is missing.");
        }

        if (productInventoryMinTextfield.getText().isEmpty()) {
            errors.add("Min field is missing.");
        }

        if (productInventoryMinTextfield.getText().isEmpty() ||
                productInventoryMaxTextfield.getText().isEmpty() ||
                productInventoryTextfield.getText().isEmpty()) {
            return errors;
        }

        int min = Integer.parseInt(productInventoryMinTextfield.getText());
        int max = Integer.parseInt(productInventoryMaxTextfield.getText());

        if (min > max) {
            errors.add("Min is greater than Max");
        }

        int stock = Integer.parseInt(productInventoryTextfield.getText());

        if (stock < min) {
            errors.add("Inventory is less than Min");
        }

        if (stock > max) {
            errors.add("Inventory is more than Max");
        }

        return errors;
    }

}

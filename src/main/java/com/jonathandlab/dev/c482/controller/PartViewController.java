package com.jonathandlab.dev.c482.controller;


import com.jonathandlab.dev.c482.NavigationHelper;
import com.jonathandlab.dev.c482.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.jonathandlab.dev.c482.ModalHelper.displayAlert;

/**
 * @author Jonathan Dowdell
 */
public class PartViewController implements Initializable {

    @FXML
    public Label titleLabel;

    @FXML
    public RadioButton inHouseRadio;

    @FXML
    public RadioButton outsourcedRadio;

    @FXML
    public TextField idTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField inventoryTextField;

    @FXML
    public TextField priceTextField;

    @FXML
    public TextField maxTextField;

    @FXML
    public TextField minTextField;

    @FXML
    public Label machineIDCompanyNameLabel;

    @FXML
    public TextField machineIDCompanyNameTextField;

    @FXML
    public Button savePartButton;

    @FXML
    public Button cancelPartButton;

    @FXML
    public ToggleGroup partType;

    private Part selectedPart = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureListeners();
    }

    /**
     * Loads View with data from Part
     * @param part Part to be used
     */
    public void loadPartData(Part part) {
        if (part != null) {
            selectedPart = part;
            titleLabel.setText("Modify");
            idTextField.setText(String.valueOf(part.getId()));
            nameTextField.setText(part.getName());
            inventoryTextField.setText(String.valueOf(part.getStock()));
            priceTextField.setText(String.valueOf(part.getPrice()));
            maxTextField.setText(String.valueOf(part.getMax()));
            minTextField.setText(String.valueOf(part.getMin()));
            if (part instanceof Outsourced) {
                Outsourced outsourced = (Outsourced) part;;
                outsourcedRadio.setSelected(true);
                machineIDCompanyNameTextField.setText(outsourced.getCompanyName());
            } else {
                InHouse inHouse = (InHouse) part;
                inHouseRadio.setSelected(true);
                machineIDCompanyNameTextField.setText(String.valueOf(inHouse.getMachineId()));
            }
        }
    }

    /**
     * Saves Part
     * @param event Action Event
     * @throws IOException error
     */
    @FXML
    private void savePart(ActionEvent event) throws IOException {
        List<String> errors = validChecks();
        if (errors.isEmpty()) {
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            int inv = Integer.parseInt(inventoryTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());

            if (inHouseRadio.isSelected()) {
                // Build In House Part
                int machineId = Integer.parseInt(machineIDCompanyNameTextField.getText());
                if (selectedPart == null) {
                    InHouse inHouse = new InHouse(-1, name, price, inv, min, max, machineId);
                    Inventory.addPart(inHouse);
                } else {
                    InHouse inHouse = new InHouse(selectedPart.getId(), name, price, inv, min, max, machineId);
                    Inventory.updatePart(inHouse);
                }
            } else {
                // Build Outsourced Part
                String companyName = machineIDCompanyNameTextField.getText();
                if (selectedPart == null) {
                    Outsourced outsourced = new Outsourced(-1, name, price, inv, min, max, companyName);
                    Inventory.addPart(outsourced);
                } else {
                    Outsourced outsourced = new Outsourced(selectedPart.getId(), name, price, inv, min, max, companyName);
                    Inventory.updatePart(outsourced);
                }
            }
            NavigationHelper.gotoMainView(event);
        } else {
            // Display Errors
            String errorMessages = errors.stream().map(error -> error + "\n").collect(Collectors.joining());
            System.out.println(errorMessages);
            displayAlert(Alert.AlertType.ERROR, "Error", "Please Address Error(s)", errorMessages);
        }
    }

    /**
     * Configure Field Listeners
     */
    private void configureListeners() {
        List<TextField> numberOnlyFields = List.of(inventoryTextField, priceTextField, maxTextField, minTextField, machineIDCompanyNameTextField);
        numberOnlyFields.forEach(textField -> textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            boolean inHouseRadioSelected = inHouseRadio.isSelected();
            if (inHouseRadioSelected && !newValue.matches("\\d*\\.?")) {
                textField.setText(newValue.replaceAll("[^\\d.?]", ""));
            } else {
                textField.setText(newValue);
            }
        }));

        partType.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (outsourcedRadio.isSelected()) {
                machineIDCompanyNameLabel.setText("Company Name");
            } else {
                machineIDCompanyNameLabel.setText("Machine ID");
            }

            machineIDCompanyNameTextField.setText("");

            if (this.selectedPart != null) {
                if (selectedPart instanceof Outsourced) {
                    Outsourced outsourced = (Outsourced) this.selectedPart;
                    machineIDCompanyNameTextField.setText(outsourced.getCompanyName());
                } else {
                    InHouse inHouse = (InHouse) this.selectedPart;
                    machineIDCompanyNameTextField.setText(String.valueOf(inHouse.getMachineId()));
                }
            }
        });
    }

    /**
     * Navigates to MainView
     * @param event ActionEvent
     * @throws IOException error
     */
    @FXML
    public void cancelAction(ActionEvent event) throws IOException {
        NavigationHelper.gotoMainView(event);
    }

    /**
     * Validates fields
     * @return List of Strings
     */
    private List<String> validChecks() {
        ArrayList<String> errors = new ArrayList<>();

        if (nameTextField.getText().isEmpty()) {
            errors.add("Name field is missing.");
        }

        if (inventoryTextField.getText().isEmpty()) {
            errors.add("Inventory field is missing.");
        }

        if (priceTextField.getText().isEmpty()) {
            errors.add("Price field is missing.");
        }

        if (maxTextField.getText().isEmpty()) {
            errors.add("Max field is missing.");
        }

        if (minTextField.getText().isEmpty()) {
            errors.add("Min field is missing.");
        }

        if (machineIDCompanyNameTextField.getText().isEmpty()) {
            if (inHouseRadio.isSelected()) {
                errors.add("Machine ID field is missing.");
            } else {
                errors.add("Company Name field is missing.");
            }
        }

        if (minTextField.getText().isEmpty() ||
                maxTextField.getText().isEmpty() ||
                inventoryTextField.getText().isEmpty()) {
            return errors;
        }

        int min = Integer.parseInt(minTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());

        if (min > max) {
            errors.add("Min is greater than Max");
        }

        int stock = Integer.parseInt(inventoryTextField.getText());

        if (stock < min) {
            errors.add("Inventory is less than Min");
        }

        if (stock > max) {
            errors.add("Inventory is more than Max");
        }

        return errors;
    }
}
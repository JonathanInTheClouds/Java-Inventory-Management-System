package com.jonathandlab.dev.c482;

import com.jonathandlab.dev.c482.controller.PartViewController;
import com.jonathandlab.dev.c482.controller.ProductViewController;
import com.jonathandlab.dev.c482.model.Part;
import com.jonathandlab.dev.c482.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Jonathan Dowdell
 */
public class NavigationHelper {

    /**
     *
     * @param event
     * @throws IOException
     * Navigates to MainView
     */
    @FXML
    public static void gotoMainView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param updatableProduct Product to be updated
     * @param event Action Event
     * @throws IOException
     * Navigates to ProductView
     */
    @FXML
    public static void gotoProductView(Product updatableProduct, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("product-view.fxml"));
        Parent parent = fxmlLoader.load();
        ProductViewController productViewController = fxmlLoader.getController();
        productViewController.loadProductData(updatableProduct);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param updatablePart Part to be updated
     * @param event Action Event
     * @throws IOException
     * Navigates to Part View
     */
    @FXML
    public static void gotoPartView(Part updatablePart, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("part-view.fxml"));
        Parent parent = fxmlLoader.load();
        PartViewController partViewController = fxmlLoader.getController();
        partViewController.loadPartData(updatablePart);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

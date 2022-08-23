package com.jonathandlab.dev.c482;

import com.jonathandlab.dev.c482.controller.MainViewController;
import com.jonathandlab.dev.c482.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * @author Jonathan Dowdell
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        InHouse tvScreen = new InHouse(-1, "TV Screen", 300.00, 5, 1, 20, 101);
        InHouse tvShell = new InHouse(-1,"TV Shell", 100.00, 5, 1, 20, 101);
        InHouse powerCord = new InHouse(-1,"Power Cord", 2.99, 5, 1, 20, 101);
        Outsourced remote = new Outsourced(-1, "Remote Control",29.99, 56, 30, 100, "Panasonic");
        Product television = new Product(-1, "LCD Television", 520.00, 10, 1, 20);
        Product microwave = new Product(-1, "Microwave Oven", 320, 10, 1, 20);

        List<Part> tvParts = List.of(tvScreen, tvShell, powerCord, remote);

        tvParts.forEach(television::addAssociatedPart);
        tvParts.forEach(Inventory::addPart);

        List<Product> products = List.of(television, microwave);
        products.forEach(Inventory::addProduct);

        launch(args);
    }
}
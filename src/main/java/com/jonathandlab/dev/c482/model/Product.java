package com.jonathandlab.dev.c482.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * @author Jonathan Dowdell
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productId;
    private String name;
    private double price = 0.0;
    private int inStock = 0;
    private int min;
    private int max;

    public Product(int productId, String name, double price, int inStock, int min, int max) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void addAssociatedPart(Part part) {
        if (part != null) {
            associatedParts.add(part);
        }
    }

    public boolean deleteAssociatedPart(Part part) {
        for (int i = 0; i < associatedParts.size(); i++) {
            Part currentPart = associatedParts.get(i);
            if (currentPart == part) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }

}

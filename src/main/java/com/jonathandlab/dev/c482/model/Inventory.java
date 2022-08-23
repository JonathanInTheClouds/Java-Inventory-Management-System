package com.jonathandlab.dev.c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * @author Jonathan Dowdell
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Add Part to allParts list
     * @param part Part
     */
    public static void addPart(Part part) {
        int id = allParts.size() + 1;
        part.setId(id);
        allParts.add(part);
    }

    /**
     * Update Part from allParts list
     * @param part Part
     */
    public static void updatePart(Part part) {
        for (int i = 0; i < allParts.size(); i++) {
            Part currentPart = allParts.get(i);
            if (currentPart.getId() == part.getId()) {
                allParts.set(i, part);
                break;
            }
        }
    }

    /**
     * Update using index of allParts list
     * @param index of allParts list
     * @param selectedPart Part
     */
    public static void updatePart(int index, Part selectedPart) {
        if (selectedPart != null && index < allParts.size()) {
            allParts.set(index, selectedPart);
        }
    }

    /**
     * Delete part from allParts list
     * @param selectedPart Part
     * @return Deletion results
     */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart != null) {
            return allParts.remove(selectedPart);
        } else {
            return false;
        }
    }


    /**
     * Search for part using part's name or id
     * @param searchValue String
     * @return Results from search
     */
    public static ObservableList<Part> lookupPart(String searchValue) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchValue) || part.getName().contains(searchValue)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    /**
     * Search for part using part's id
     * @param id Part Id
     * @return Part
     */
    public static Part lookupPart(int id) {
        for (Part part : allParts) {
            if (id == part.getId()) {
                return part;
            }
        }
        return null;
    }


    /**
     * Add Product to allProducts list
     * @param product Product
     */
    public static void addProduct(Product product) {
        if (product != null) {
            int id = allProducts.size() + 1;
            product.setProductId(id);
            allProducts.add(product);
        }
    }


    /**
     * Delete Product from allProducts list
     * @param product Product
     * @return Deletion Results
     */
    public static boolean deleteProduct(Product product) {
        if (product != null && product.getAssociatedParts().isEmpty()) {
            return allProducts.remove(product);
        } else {
            return false;
        }
    }

    /**
     * Search for Product using product's name and id
     * @param searchValue String
     * @return Results from search
     */
    public static ObservableList<Product> lookupProduct(String searchValue) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (String.valueOf(product.getProductId()).contains(searchValue) || product.getName().contains(searchValue)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * Search allProducts using Product's ID
     * @param productID int
     * @return Product from allProducts list
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getProductId() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Update Product of allProduct list
     * @param product Product
     */
    public static void updateProduct(Product product) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product currentProduct = allProducts.get(i);
            if (currentProduct.getProductId() == product.getProductId()) {
                allProducts.set(i, product);
                break;
            }
        }
    }

    /**
     * Update using allProduct list
     * @param index of allProduct list
     * @param product Product
     */
    public static void updateProduct(int index, Product product) {
        if (product != null && index < allProducts.size()) {
            allProducts.set(index, product);
        }
    }
}

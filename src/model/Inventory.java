package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** Models an Inventory of Products and Parts.
 *
 * Data in this class is persistent throughout the application.
 *
 * @author Stephanie DelBono
 */
public class Inventory {

    /** ID for a part. Variable used to generate unique part ID.
     */
    private static int partId = 0;

    /** ID for a product. Variable used to generate unique product ID.
     */
    private static int productId = 0;



    /** List of all parts in inventory. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Adds part to inventory.
     *
     * @param part The part object to be added.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /** Gets the list of all parts in inventory.
     *
     * @return List of part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /** List of all products in inventory. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds product to inventory.
     *
     * @param product The product object to be added.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /** Gets the list of all products in inventory.
     *
     * @return List of product objects.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Generates a new part ID.
     *
     * @return a unique part ID.
     */
    public static int getGenPartId(){
        return ++partId;
    }

    /** Generates a new product ID.
     *
     * @return a unique product ID.
     */
    public static int getGenProductId(){
        return ++productId;
    }


    /** Deletes a product from the list of products.
     *
     * @param selectedProduct The product to be deleted.
     * @return boolean indicating product deletion status.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** Deletes a part from the list of parts.
     *
     * @param selectedPart The part to be deleted.
     * @return boolean indicating part deletion status.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Searches the list of parts by partial or full name.
     *
     * @param partialName The part name.
     * @return List of parts found.
     */
    public static ObservableList<Part> lookupPart(String partialName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts){
            if(part.getName().contains(partialName)){
                namedParts.add(part);
            }
        }

        return namedParts;
    }

    /** Searches the list of parts by ID.
     *
     * @param id The part ID.
     * @return part The part if found or null if not found.
     */
    public static Part lookupPart(int id) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(int i = 0; i < allParts.size(); i++){
            Part part = allParts.get(i);

            if(part.getId() == id){
                return part;
            }
        }
        return null;
    }

    /** Searches the list of products by partial or full name.
     *
     * @param partialName The product name.
     * @return List of products found.
     */
    public static ObservableList<Product> lookupProduct(String partialName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product product : allProducts){
            if(product.getName().contains(partialName)){
                namedProducts.add(product);
            }
        }
        return namedProducts;
    }

    /** Searches the list of products by ID.
     *
     * @param productId The product ID.
     * @return the product if found or null if not found.
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(int i = 0; i < allProducts.size(); i++){
            Product product = allProducts.get(i);

            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }


    /** Updates a part in the list of parts.
     *
     * @param index Index of the part to be replaced.
     * @param selectedPart The part used for the replacement.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    /** Updates a product in the list of products.
     *
     * @param index Index of the product to be replaced.
     * @param selectedProduct The product used for the replacement.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
}




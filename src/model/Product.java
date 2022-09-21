package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** Models a product that can contain associated part or parts.
 *
 * @author Stephanie DelBono
 */
public class Product {

    /** ID for the product */
    private int id;

    /** Name of the product */
    private String name;

    /** Price of the product */
    private double price;

    /** Inventory count of the product. */
    private int stock;

    /** Minimum inventory count for the product. */
    private int min;

    /** Maximum inventory count for the product. */
    private int max;


    /** List of associated parts for the product. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Gets list of associated parts for the product.
     *
     * @return the list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


    /** Constructor for a new instance of a product.
     * @param id the ID for the product.
     * @param name the name for the product.
     * @param stock the inventory level of the product.
     * @param price the price of the product.
     * @param min the minimum for the product.
     * @param max the maximum for the product.
     */
    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /** Getter for the ID.
     *
     * @return id of the product.
     */
    public int getId() {
        return id;
    }


    /** Setter for the ID.
     *
     * @param id the id of the product.
     */
    public void setId(int id) {
        this.id = id;
    }


    /** Getter for the name.
     *
     * @return name of the product.
     */
    public String getName() {
        return name;
    }


    /** Setter for the name.
     *
     * @param name the name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }


    /** Getter for the price.
     *
     * @return price of the product.
     */
    public double getPrice() {
        return price;
    }


    /** Setter for the price.
     *
     * @param price the price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /** Getter for the stock.
     *
     * @return stock of the product.
     */
    public int getStock() {
        return stock;
    }


    /** Setter for the stock.
     *
     * @param stock the stock of the product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }


    /** Getter for the min.
     *
     * @return minimum level of the product.
     */
    public int getMin() {
        return min;
    }


    /** Setter for the min.
     *
     * @param min the minimum level of the product.
     */
    public void setMin(int min) {
        this.min = min;
    }


    /** Getter for the max.
     *
     * @return maximum level of the product.
     */
    public int getMax() {
        return max;
    }


    /** Setter for the max.
     *
     * @param max the maximum level of the product.
     */
    public void setMax(int max) {
        this.max = max;
    }




    /** Adds part to associated parts list for the product.
     *
     * @param part The part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


    /** Deletes part from the list of associated parts for the product.
     *
     * @param selectedAssociatedPart The selected part to delete.
     * @return boolean indicating associated part deletion status.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }


}

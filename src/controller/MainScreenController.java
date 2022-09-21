package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



/** Controller class provides logic control for Modify Part screen.
 *
 * A runtime error kept occurring when clicking the Modify Part button.
 * I corrected this error by preventing the null pointer inside toPartModify Action for partModify
 * from being passed and creating an if statement to display an alert if no part was selected.
 * This stopped the runtime error from occurring.
 *
 * @author Stephanie DelBono
 */
public class MainScreenController extends Inventory implements Initializable {


    /** Main screen parts table view. */
    @FXML
    public TableView partTableView;

    /** The Part ID column for parts table. */
    @FXML
    public TableColumn partIdCol;

    /** The Part Name column for parts table. */
    @FXML
    public TableColumn partNameCol;

    /** The Part inventory level column for parts table. */
    @FXML
    public TableColumn partInventoryCol;

    /** The Part price column for parts table. */
    @FXML
    public TableColumn partPriceCol;


    /** Main screen parts table view. */
    @FXML
    public TableView productTableView;

    /** Product ID column for product table. */
    @FXML
    public TableColumn productIdCol;

    /** Product Name column for product table. */
    @FXML
    public TableColumn productNameCol;

    /** Product Inventory level column for product table. */
    @FXML
    public TableColumn productInventoryCol;

    /** Product Price column for product table. */
    @FXML
    public TableColumn productPriceCol;

    /** The Part Search bar text field for Parts table. */
    @FXML
    public TextField partQueryTF;

    /** The Product Search bar text field for Products table. */
    @FXML
    public TextField productQueryTF;

/** Selected part object from the table view on Main Screen. */
    public static Part partToModify;

    /** Gets the Selected part object from the table view on Main Screen.
     *
     * @return part object or null if no part is selected.
     */
    public static Part getPartToModify(){
        return partToModify;
    }


    /** Selected product object from the table view on Main Screen. */
    public static Product productToModify;

    /** Gets the Selected product object from the table view on Main Screen.
     *
     * @return product object or null if no product is selected.
     */
    public static Product getProductToModify(){
        return productToModify;
    }

    /** List of all parts in inventory for MainScreenController. */
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** List of all products in inventory for MainScreenController. */
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();



    /** Deletes selected Product from product table.
     * Displays confirmation dialog before deleting and Displays error dialog
     * if no product is selected.
     * @param actionEvent Delete Product Button Action.
     */
    public void productDeleteAction(ActionEvent actionEvent) {
        System.out.println("Product Delete button clicked");

        Product selectedProduct = (Product) productTableView.getSelectionModel().getSelectedItem();

        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Product NOT deleted");
            alert.setContentText("Cannot delete a product with associated parts");
            alert.showAndWait();
        }

        else if (selectedProduct == null) {
            displayAlert(4);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete the selected Product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /** Deletes selected part from part table.
     * Displays confirmation dialog before deleting and Displays error dialog
     * if no part is selected.
     * @param actionEvent Delete Part Button Action.
     */
    public void partDeleteAction(ActionEvent actionEvent) {
        System.out.println("Part Delete button clicked");

        Part selectedPart = (Part) partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete the selected Part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /** Exits the program.
     * Confirmation dialog box to confirm exit.
     * @param actionEvent Exit Button action.
     */
    public void exitProgramAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

/** Initializes MainScreenController and populates the table views for parts
 * and products.
 * @param url Location used for root object relative paths.
 * @param resourceBundle Resources for root object localization.
 */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /** Loads AddPart controller.
     *
     * @param actionEvent Add part button action.
     * @throws Exception from FXMLLoader.
     */
    @FXML
    public void toAddPart(ActionEvent actionEvent) throws Exception {
        System.out.println("Add Part Button was clicked");

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    }

    /** Loads ModifyPartController.
     * Displays an error message if no part is selected.
     * @param actionEvent modify part button action.
     * @throws Exception from FXMLLoader.
     */
    @FXML
    public void toModifyPart(ActionEvent actionEvent) throws Exception {
        System.out.println("Modify Part button was clicked");

       partToModify = (Part) partTableView.getSelectionModel().getSelectedItem();

        if (partToModify == null) {         // THIS IS WHERE THE RUNTIME ERROR OCCURRED //
            displayAlert(3);
        } else {

            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();

        }
    }

    /** Loads AddProductController.
     *
     * @param actionEvent Add product button action.
     * @throws Exception from FXMLLoader.
     */
    @FXML
    public void toAddProduct(ActionEvent actionEvent) throws Exception {
        System.out.println("Add Product button was clicked");

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Loads ModifyProductController.
     * Displays an error message if no product is selected.
     * @param actionEvent modify product button action.
     * @throws Exception from FXMLLoader.
     */
    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws Exception {
        System.out.println("Modify Product button was clicked");

        productToModify = (Product) productTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            displayAlert(4);
        }
        else {

            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }



    /** Initiates a search based on Part Name text field or Part ID text field
     * and refreshes table view and displays search results.
     *
     * Parts can be searched for by Full ID or Partial Name.
     * @param actionEvent Search button action.
     */
    @FXML
    public void partSearchButton(ActionEvent actionEvent) {
        String q = partQueryTF.getText();

        ObservableList<Part> parts = lookupPart(q);

        if(parts.size() == 0){

            try {

                int id = Integer.parseInt(q);
                Part part = lookupPart(id);

                if (part != null)
                    parts.add(part);
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }

        partTableView.setItems(parts);
        partQueryTF.setText("");

        if(parts.size() == 0){
            displayAlert(1);
        }
    }


    /** Initiates a search based on Product Name text field or Product ID text field
     * and refreshes table view and displays search results.
     *
     * Products can be searched for by Full ID or Partial Name.
     * @param actionEvent Search button action.
     */
    @FXML
    public void productSearchButton(ActionEvent actionEvent) {
        String q = productQueryTF.getText();

        ObservableList<Product> products = lookupProduct(q);

        if (products.size() == 0) {

            try {

                int id = Integer.parseInt(q);
                Product product = lookupProduct(id);

                if (product != null)
                    products.add(product);
            } catch (NumberFormatException e) {
                //ignore
            }

        }
        productTableView.setItems(products);
        productQueryTF.setText("");

        if(products.size() == 0){
            displayAlert(2);
        }
    }



    /** Displays Alert Messages.
     * @param alertType Error and Information message selector.
     */
    private void displayAlert(int alertType){
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
                break;

            case 2:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Product not found");
                alertInfo.showAndWait();
                break;

            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("No Parts Selected");
                alertError.showAndWait();
                break;

            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("No Products Selected");
                alertError.showAndWait();
                break;


        }

    }

}

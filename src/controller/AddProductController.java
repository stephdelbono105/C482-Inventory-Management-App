package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
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

import static controller.MainScreenController.*;

/** Controller class provides logic control for Add Product screen.
 *
 * @author Stephanie DelBono
 */

public class AddProductController implements Initializable {

    /** The Product ID text field. */
    @FXML
    public TextField productIdText;

    /** The Product Name field. */
    @FXML
    public TextField productNameText;

    /** The Product Inventory level text field. */
    @FXML
    public TextField productInventoryText;

    /** The Product price text field. */
    @FXML
    public TextField productPriceText;

    /** The Product maximum text field. */
    @FXML
    public TextField productMaxText;

    /** The Product minimum text field. */
    @FXML
    public TextField productMinText;

    /** The Add Product Part Table View. */
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

    /** Add Product associated parts table view. */
    @FXML
    public TableView assocPartTableView;

    /** The Part ID column for Associated Parts table. */
    @FXML
    public TableColumn assocPartIdCol;

    /** The Part Name column for Associated Parts table. */
    @FXML
    public TableColumn assocPartNameCol;

    /** The Part Inventory level column for Associated Parts table. */
    @FXML
    public TableColumn assocPartInventoryCol;

    /** The Part Price column for Associated Parts table. */
    @FXML
    public TableColumn assocPartPriceCol;

    /** List containing products with associated parts. */
    public ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /** The Part Search bar for Associated Parts table. */
    @FXML
    public TextField partQueryTF;


    /** Initializes Add Product controller and populates table views.
     *
     * @param url Location used for root object relative paths.
     * @param resourceBundle Resources for root object localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Back to main screen when button is pressed.
     * @param actionEvent Back to main screen.
     * @throws Exception from the FXMLLoader.
     */
    @FXML
    public void toMainScreen(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }


    /** Displays confirmation of cancel button and loads MainScreenController.
     * @param actionEvent Cancel button action.
     * @throws Exception from FXMLLoader.
     */
    public void onCancelButton(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            toMainScreen(actionEvent);
        }
    }

    /** Adds a new product to Inventory and loads MainScreenController.
     * All text fields are validated and prevent invalid entries with error messages.
     * @param actionEvent Save button action.
     * @throws Exception From FXMLLoader.
     */
    @FXML
    public void onSaveButton(ActionEvent actionEvent) throws Exception {

        try {
            int id = 0;
            String name = productNameText.getText();
            Double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productInventoryText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if (name.isEmpty()) {
                displayAlert(2);
            }
            else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {
                    Product newProduct = new Product(id, name, stock, price, min, max);

                    for (Part part : assocParts){
                        newProduct.addAssociatedPart(part);
                    }
                    newProduct.setId(Inventory.getGenProductId());
                    Inventory.addProduct(newProduct);
                    toMainScreen(actionEvent);
                }
            }
        }
        catch (Exception e) {
            displayAlert(4);
        }
    }

    /** Adds a part to the associated parts table for the product.
     * Displays an Error Message if not part is selected.
     * @param actionEvent Add button action.
     */
    @FXML
    public void onAddButton(ActionEvent actionEvent) {

        Part selectedPart = (Part) partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
        }
        else {
            assocParts.add(selectedPart);
            assocPartTableView.setItems(assocParts);
        }
    }

    /** Displays confirmation dialog box and removes selected part from
     * the associated part table.
     * Displays error message if no part is selected.
     *
     * @param actionEvent Remove Associated Part button action.
     */
    @FXML
    public void onRemoveButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) assocPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete the selected associated part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                assocPartTableView.setItems(assocParts);
            }
        }
    }

    /** Initiates a search based on Part Name text field or Part ID text field
     * and refreshes table view and displays search results.
     *
     * Parts can be searched for by Full ID or Partial Name.
     * @param actionEvent Search button action.
     */
    @FXML
    public void onSearchButton(ActionEvent actionEvent) {
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


    /** Displays Alert Messages.
     * @param alertType Error and Information message selector.
     */
     private void displayAlert(int alertType) {
         Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
         Alert alertError = new Alert(Alert.AlertType.ERROR);

         switch (alertType) {
             case 1:
                 alertInfo.setTitle("Information");
                 alertInfo.setHeaderText("Part not found");
                 alertInfo.showAndWait();
                 break;

             case 2:
                 alertInfo.setTitle("Error");
                 alertInfo.setHeaderText("Name Field empty");
                 alertInfo.setContentText("Name field must have a value");
                 alertInfo.showAndWait();
                 break;

             case 3:
                 alertError.setTitle("Error");
                 alertError.setHeaderText("No Parts Selected");
                 alertError.showAndWait();
                 break;

             case 4:
                 alertError.setTitle("Error");
                 alertError.setHeaderText("Product NOT added");
                 alertError.setContentText("Invalid values or blank fields");
                 alertError.showAndWait();
                 break;

             case 5:
                 alertError.setTitle("Error");
                 alertError.setHeaderText("Minimum value is Invalid");
                 alertError.setContentText("Min field must contain numerical value greater than 0 and less than Max");
                 alertError.showAndWait();
                 break;

             case 6:
                 alertError.setTitle("Error");
                 alertError.setHeaderText("Inventory value is Invalid");
                 alertError.setContentText("Inventory can only contain numbers equal to or between the minimum and maximum stock values");
                 alertError.showAndWait();
                 break;
         }
     }

    /**
     * Validates that min is greater than 0 and less than the max.
     *
     * @param min The minimum value allowed for product.
     * @param max The maximum value allowed for product.
     * @return Boolean verifies that the minimum value is valid.
     */
    private boolean minValid(int min, int max) {
        boolean isValid = true;
        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(5);
        }
        return isValid;
    }

    /**
     * Validates that the level of inventory is equal to or between
     * the minimum and maximum.
     *
     * @param min The minimum value allowed for product.
     * @param max The maximum value allowed for product.
     * @param stock The level of inventory for product.
     * @return Boolean verifies that inventory level is valid.
     */
    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;
        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(6);
        }
        return isValid;
    }



}

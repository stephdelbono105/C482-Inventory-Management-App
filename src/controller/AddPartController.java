package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class provides logic control for Add Parts screen.
 *
 * @author Stephanie DelBono
 */


public class AddPartController implements Initializable {

    /** The in-house radio button. */
    @FXML
    public RadioButton inhouseRB;

    /** The outsourced radio button. */
    @FXML
    public RadioButton outsourcedRB;

    /** The toggle group for in-house and outsourced radio buttons. */
    @FXML
    public ToggleGroup tgroup;

    /** The part name text field. */
    @FXML
    public TextField partNameText;

    /** The part inventory text field. */
    @FXML
    public TextField partInventoryText;

    /** The part price text field. */
    @FXML
    public TextField partPriceText;

    /** The part maximum text field. */
    @FXML
    public TextField partMaxText;

    /** The part minimum text field. */
    @FXML
    public TextField partMinText;

    /** Machine ID/Company Name text field for parts. */
    @FXML
    public TextField machineIdText;

    /** Machine ID/Company Name label for parts. */
    @FXML
    public Label machineIdLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /** Back to main screen when button is pressed.
     * @param actionEvent Back to main screen.
     * @throws Exception from the FXMLLoader.
     */
    @FXML
    public void toMainScreen(ActionEvent actionEvent) throws Exception {
        System.out.println("To Main Screen Button clicked");

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /** Sets the machine ID label to "Machine ID".
     * @param actionEvent In-house radio button action.
     */
    @FXML
    public void onInHouse(ActionEvent actionEvent) {
        System.out.println("In-House Button clicked");

        machineIdLabel.setText("Machine ID");
    }

    /** Sets the machine ID label to "Company Name" for Outsourced parts.
     * @param actionEvent outsourced radio button action.
     */
    @FXML
    public void onOutsourced(ActionEvent actionEvent) {
        System.out.println("Outsourced Button Clicked");

        machineIdLabel.setText("Company Name");
    }

    /** Adds a new part to Inventory and loads MainScreenController.
     * All text fields are validated and prevent invalid entries with error messages.
     * @param actionEvent Save button action.
     * @throws Exception From FXMLLoader.
     */
    @FXML
    public void onSaveButton(ActionEvent actionEvent) throws Exception {
        System.out.println("Save Button Clicked");

        try {
            int id = 0;
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean addPartSuccessful = false;

            if(name.isEmpty()) {
                displayAlert(2);
            }
            else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {
                    if (inhouseRB.isSelected()) {
                        try {
                            machineId = Integer.parseInt(machineIdText.getText());
                            InHouse newInhousePart = new InHouse(id,name,price,stock,min,max,machineId);
                            newInhousePart.setId(Inventory.getGenPartId());
                            Inventory.addPart(newInhousePart);
                            addPartSuccessful = true;
                        }
                        catch (Exception e) {
                            displayAlert(3);
                        }
                    }

                    if (outsourcedRB.isSelected()) {
                        companyName = machineIdText.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourcedPart.setId(Inventory.getGenPartId());
                        Inventory.addPart(newOutsourcedPart);
                        addPartSuccessful = true;
                    }
                    if (addPartSuccessful) {
                        toMainScreen(actionEvent);
                    }
                }

            }

        }
        catch (Exception e) {
            displayAlert(1);
        }

    }

    /** Displays confirmation of cancel button and loads MainScreenController.
     * @param actionEvent Cancel button action.
     * @throws Exception from FXMLLoader.
     */
    public void onCancelButton(ActionEvent actionEvent) throws Exception {
        System.out.println("Cancel Button Clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            toMainScreen(actionEvent);
        }
    }

    /** Displays Alert Messages.
     * @param alertType Error message selector.
     */
    private void displayAlert(int alertType){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part NOT added");
                alertError.setContentText("Invalid values or blank fields");
                alertError.showAndWait();
                break;

            case 2:
                alertError.setTitle("Error");
                alertError.setHeaderText("Name field empty");
                alertError.setContentText("Name field must have a value");
                alertError.showAndWait();
                break;

            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Machine ID value is Invalid");
                alertError.setContentText("Machine ID can only contain numbers");
                alertError.showAndWait();
                break;

            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Inventory value is Invalid");
                alertError.setContentText("Inventory can only contain numbers equal to or between the minimum and maximum stock values");
                alertError.showAndWait();
                break;

            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Minimum value is Invalid");
                alertError.setContentText("Min field must contain numerical value greater than 0 and less than Max");
                alertError.showAndWait();
                break;

        }

    }

/**
 * Validates that min is greater than 0 and less than the max.
 *
 * @param min The minimum value allowed for part.
 * @param max The maximum value allowed for part.
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
     * @param min The minimum value allowed for part.
     * @param max The maximum value allowed for part.
     * @param stock The level of inventory for part.
     * @return Boolean verifies that inventory level is valid.
     */
    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;
        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }
        return isValid;
    }

}

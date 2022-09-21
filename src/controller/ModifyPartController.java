package controller;

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
import model.Part;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;

/** Controller class provides logic control for Modify Part screen.
 *
 * @author Stephanie DelBono
 */
public class ModifyPartController implements Initializable {

    /** The toggle group for in-house and outsourced radio buttons. */
    @FXML
    public ToggleGroup tGroup;

    /** The in-house radio button. */
    @FXML
    public RadioButton inHouseRB;

    /** The outsourced radio button. */
    @FXML
    public RadioButton outsourcedRB;

    /** The part ID text field. */
    @FXML
    public TextField partIdText;

    /** The part name text field. */
    @FXML
    public TextField partNameText;

    /** The part Inventory text field. */
    @FXML
    public TextField partInventoryText;

    /** The part Price text field. */
    @FXML
    public TextField partPriceText;

    /** The part Max text field. */
    @FXML
    public TextField partMaxText;

    /** The part Min text field. */
    @FXML
    public TextField partMinText;

    /** The Part object selected in MainScreenController. */
   public Part selectedPart;

    /** Machine ID/ Company ID label and text field */
    @FXML
    public TextField machineIdText;

    /** Machine ID/Company Name label for parts. */
    @FXML
    public Label machineIdLabel;


    /** Initializes Modify Part controller and populates text fields
     * with the selected part from MainScreenController.
     *
     * @param url Location used for root object relative paths.
     * @param resourceBundle Resources for root object localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainScreenController.getPartToModify();

        if (selectedPart instanceof InHouse) {
            inHouseRB.setSelected(true);
            machineIdLabel.setText("Machine ID");
            machineIdText.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced) {
            outsourcedRB.setSelected(true);
            machineIdLabel.setText("Company Name");
            machineIdText.setText(((Outsourced) selectedPart).getCompanyName());
        }

        partIdText.setText(String.valueOf(selectedPart.getId()));
        partNameText.setText(selectedPart.getName());
        partInventoryText.setText(String.valueOf(selectedPart.getStock()));
        partPriceText.setText(String.valueOf(selectedPart.getPrice()));
        partMaxText.setText(String.valueOf(selectedPart.getMax()));
        partMinText.setText(String.valueOf(selectedPart.getMin()));

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

    /** Sets the machine ID label to "Machine ID".
     * @param actionEvent In-house radio button action.
     */
    @FXML
    public void onInHouse(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID");
    }

    /** Sets the machine ID label to "Company Name" for Outsourced parts.
     * @param actionEvent outsourced radio button action.
     */
    @FXML
    public void onOutsourced(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
    }


    /** Updates part in Inventory and loads MainScreenController.
     * All text fields are validated and prevent invalid entries with error messages.
     * @param actionEvent Save button action.
     * @throws Exception From FXMLLoader.
     */
    @FXML
    public void onSaveButton(ActionEvent actionEvent) throws Exception {

        try {
            int id = selectedPart.getId();
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean partModifySuccessful = false;

            if (minValid(min, max) && inventoryValid(min, max, stock)) {

                if (inHouseRB.isSelected()){
                    try{
                        machineId = Integer.parseInt(machineIdText.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partModifySuccessful = true;
                    }
                    catch (Exception e) {
                        displayAlert(2);
                    }
                }

                if (outsourcedRB.isSelected()) {
                    companyName = machineIdText.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partModifySuccessful = true;
                }

                if (partModifySuccessful) {

                    int index = getAllParts().indexOf(selectedPart);
                    Inventory.updatePart(index, selectedPart);

                    Inventory.getAllParts().remove(selectedPart);

                    toMainScreen(actionEvent);
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
    public void onCancelButton(ActionEvent actionEvent) throws Exception{
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
                alertError.setHeaderText("Part NOT modified");
                alertError.setContentText("Invalid values or blank fields");
                alertError.showAndWait();
                break;


            case 2:
                alertError.setTitle("Error");
                alertError.setHeaderText("Machine ID value is Invalid");
                alertError.setContentText("Machine ID can only contain numbers");
                alertError.showAndWait();
                break;


            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Inventory value is Invalid");
                alertError.setContentText("Inventory can only contain numbers equal to or between the minimum and maximum stock values");
                alertError.showAndWait();
                break;

            case 4:
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
            displayAlert(4);
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
            displayAlert(3);
        }
        return isValid;
    }
}







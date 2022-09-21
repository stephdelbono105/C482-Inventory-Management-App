package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * Inventory Management Program is an application for managing an inventory database
 * of parts and products with associated parts.
 *
 * A suitable feature for a future version of the program would be the implementation of
 * generated reports based on specific inventory criteria.
 *
 * @author Stephanie DelBono
 *
 */


public class Main extends Application {

    /**
     * The start method creates the FXML stage and loads main screen scene.
     * @param primaryStage The primary stage
     * @throws Exception Thrown exception
     */


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    /** The main method is the entry point of the program.
     * The main method creates the sample data that is loaded when the application launches.
     *
     * Javadocs located at \IdeaProjects\NEW_C482_PA_Project\javadoc. 
     *
     * @param args The default string arguments
     */

    public static void main(String[] args) {

                                    // TEST DATA //

        InHouse testData1 = new InHouse(1,"Brakes", 15.99, 10,1,10,51230);
        InHouse testData2 = new InHouse(2, "Wheel", 11.99,15,1,15, 34560);
        Inventory.addPart(testData1);
        Inventory.addPart(testData2);

        Outsourced testData5 = new Outsourced(3, "Seat",12.99, 10, 1,10,"");
        Inventory.addPart(testData5);

        Product testData3 = new Product(1000, "Giant Bike", 5, 299.99,1,5);
        Product testData4 = new Product(1001, "Tricycle", 3, 199.99, 1, 3);
        Product testData6 = new Product(1002,"Unicycle", 5, 99.99, 1,5);
        Inventory.addProduct(testData3);
        Inventory.addProduct(testData4);
        Inventory.addProduct(testData6);


        launch(args);
    }
}

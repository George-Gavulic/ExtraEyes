import java.io.IOException;
import java.util.List;

import application.ExtraEyes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextField tfIngredient;
    @FXML
    private TextField tfBarcode;
    @FXML
    private Button btHomeToIngredient;
    @FXML
    private Button btHomeToBarcode;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String ingredient;
    String barcode; // TODO set this to an int

//This section controls switching scene between settings, the scanner, the ingerdient page, and the barcode page. 
    public void switchToSettings(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SettingsScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setStage(root);
    }
    public void switchToScanner(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ScannerScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setStage(root);
    }
    public void switchToBarcode(ActionEvent event) throws IOException {
        //TODO// Check if the inputed barcode is an integer if so, 
        //if so call the barcode to image function.
        //if so call the barcode get ingredients functions.

        // barcode = tfBarcode.getText();
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("BarcodeScene.fxml"));
        // root = loader.load();

        // IngredientController ingredientController = loader.getController();
        // ingredientController.setIngredient(barcode);

        Parent root = FXMLLoader.load(getClass().getResource("IngredientScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setStage(root);
    }
    public void switchToIngredient(ActionEvent event) throws IOException {
        ingredient = tfIngredient.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IngredientScene.fxml"));
        root = loader.load();

        IngredientController ingredientController = loader.getController();
        ingredientController.setIngredient(ingredient);

        //Parent root = FXMLLoader.load(getClass().getResource("IngredientScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setStage(root);
    }
    public void setStage(Parent root2){
        String css = this.getClass().getResource("/application.css").toExternalForm();
        scene = new Scene(root2);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}
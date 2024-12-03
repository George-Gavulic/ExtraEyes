import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IngredientController {

    @FXML
    Label lbDisplayedIngredient;

    @FXML
    private TextField ingredientField;

    @FXML
    private TextField doiField;

    @FXML
    private RadioButton redButton, orangeButton, yellowButton, greenButton, blueButton, purpleButton;;


    private Stage stage;
    private Scene scene;
    private Parent root;

    DataBase Database = new DataBase();

    public void addNewIngredient(ActionEvent event) {

        String ingredient = ingredientField.getText();
        String doi = doiField.getText();
        String color = getSelectedColor();

        if (ingredient.isEmpty() || doi.isEmpty() || color == null) {
            showAlert("Error", "All fields must be filled, and a color must be selected!");
            return;
        }

        Database.addIngredient(ingredient, color, doi);
        showAlert("Success", "Ingredient added successfully!");
    }

    private String getSelectedColor() {
        if (redButton.isSelected()) return "red";
        if (orangeButton.isSelected()) return "orange";
        if (yellowButton.isSelected()) return "yellow";
        if (greenButton.isSelected()) return "green";
        if (blueButton.isSelected()) return "blue";
        if (purpleButton.isSelected()) return "purple";
        return null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setIngredient(String ingredient){
        lbDisplayedIngredient.setText(ingredient);
    }

    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
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

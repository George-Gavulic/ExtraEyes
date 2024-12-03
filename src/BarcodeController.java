import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Application;

public class BarcodeController extends Application {

    @FXML
    Label lbBarcode;
    @FXML
    Label lbBarcode1, lbBarcode2, lbBarcode3, lbBarcode4, lbBarcode5, lbBarcode6, lbBarcode7, lbBarcode8, lbBarcode9, lbBarcode10, lbBarcode11, lbBarcode12, lbBarcode13, lbBarcode14, lbBarcode15, lbBarcode16, lbBarcode17, lbBarcode18, lbBarcode19, lbBarcode20;

    @FXML
    Label source4, source5, source6, source7, source8, source9, source10, source11, source12, source13, source14, source15, source16, source17, source18, source19, source20;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DataBase database = new DataBase();

    public void setBarcode(String barcodeText) {
        lbBarcode.setText("Barcode: " + barcodeText);
        setIngredient(barcodeText);
    }

    public void setIngredient(String barcodeText) {
        GetIngredients getIngredients = new GetIngredients();
        getIngredients.findIngredients(barcodeText);

        List<String> ingredients = getIngredients.getIngredients();
        List<String> allergens = getIngredients.getAllergens();

        populateLabels(allergens, ingredients);
    }

    public void populateLabels(List<String> allergens, List<String> ingredients) {
        List<String> combinedList = new ArrayList<>();
        combinedList.add("Allergens:");
        combinedList.addAll(allergens);
        combinedList.add("Ingredients:");
        combinedList.addAll(ingredients);

        Label[] labels = {lbBarcode1, lbBarcode2, lbBarcode3, lbBarcode4, lbBarcode5, lbBarcode6, lbBarcode7,
                lbBarcode8, lbBarcode9, lbBarcode10, lbBarcode11, lbBarcode12, lbBarcode13, lbBarcode14,
                lbBarcode15, lbBarcode16, lbBarcode17, lbBarcode18, lbBarcode19, lbBarcode20};

        Label[] doiLabels = {source4, source5, source6, source7, source8, source9, source10, source11,
                source12, source13, source14, source15, source16, source17, source18, source19, source20};

        for (Label label : labels) {
            label.setVisible(false);
        }
        for (Label doiLabel : doiLabels) {
            doiLabel.setVisible(false);
        }

        int maxSize = Math.min(combinedList.size(), labels.length);  // Ensure we don't exceed the bounds

        for (int i = 0; i < maxSize; i++)  {
            String ingredient = combinedList.get(i);
            Label currentLabel = labels[i];

            currentLabel.setText(ingredient);
            currentLabel.setVisible(true);

            if (combinedList.get(i).equals("Allergens:")) {
                currentLabel.setStyle("-fx-font-size: 20;");
                currentLabel.setStyle("-fx-background-color: #f26b6b;");
            } else if (combinedList.get(i).equals("Ingredients:")) {
                currentLabel.setStyle("-fx-font-size: 20;");
                currentLabel.setStyle("-fx-background-color: #f26b6b;");
            }

            Label currentDoiLabel = doiLabels[i];
            if (database.isIngredientInDatabase(ingredient)) {
                String color = database.getColorForIngredient(ingredient);
                String doi = database.getDoiForIngredient(ingredient);

                currentLabel.setStyle("-fx-text-fill: " + color + ";");
                
                //currentDoiLabel.setStyle("-fx-text-fill: #6eb1f4;");
                
                currentDoiLabel.setText(doi);
                currentDoiLabel.setVisible(true);
                
            } else {
                // If the ingredient is not in the database, reset the label style
                //currentLabel.setStyle("-fx-background-color: transparent;");
            }
            if (database.isIngredientInDatabase(ingredient)) {
                String doi = database.getDoiForIngredient(ingredient);
                if (doi != null && !doi.isEmpty()) 
                {
                    currentDoiLabel.setStyle("-fx-text-fill: #6eb1f4; -fx-font-size: 10px; -fx-alignment: center-left;");
                    
                } else {
                    currentDoiLabel.setVisible(false); // Hide label if no DOI
                }
            } else {
                currentDoiLabel.setVisible(false); // Hide label for ingredients not in the database
            }

            // Hide remaining unused labels if there are fewer than 20 items
            if (i >= combinedList.size()) {
                currentLabel.setVisible(false);
                currentDoiLabel.setVisible(false);
            }
        }
    }

    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setStage(root);
    }

    public void setStage(Parent root2) {
        String css = this.getClass().getResource("/application.css").toExternalForm();
        scene = new Scene(root2);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage arg0) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}

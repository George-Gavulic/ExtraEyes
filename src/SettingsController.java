import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SettingsController {

    @FXML
    private Button btHomeFromSettings;

    private Stage stage;
    private Scene scene;
    private Parent root;


    // Switch to the main scene method
    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Ensure the stage is set correctly
        setStage(root);
    }

    public void setStage(Parent root2) {
        String css = this.getClass().getResource("/application.css").toExternalForm();
        scene = new Scene(root2);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}

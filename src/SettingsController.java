import java.io.IOException;

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

public class SettingsController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

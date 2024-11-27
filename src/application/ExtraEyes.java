package application;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExtraEyes extends Application {

    public String css;

    @Override
    public void start(Stage stage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
            Scene scene = new Scene(root);

            css = this.getClass().getResource("/application.css").toExternalForm();
            scene.getStylesheets().add(css);

            //System.out.println("\n\n\n\n\n\n"+ css);




            Image icon = new Image("/icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Extra Eyes");
            stage.setScene(scene);
            stage.show();     
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) {    launch(args);   }
}
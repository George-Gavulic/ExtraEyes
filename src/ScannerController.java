import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScannerController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView ivWebCam;

    private Webcam webcam;
    private volatile boolean isRunning;
    private volatile boolean gotBarcode = false;

    public void startWebCam(ActionEvent event) {
        if (isRunning) {
            return;
        }
        isRunning = true;
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();
    
        Thread videoThread = new Thread(() -> {

            while (isRunning) {
                
                try {
                    BufferedImage bufferedImage = webcam.getImage();
                    if (bufferedImage != null) {
                        // Convert BufferedImage to an Image for JavaFX
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(toByteArray(bufferedImage));
                        Image fxImage = new Image(inputStream);
    
                        // Update ImageView on the JavaFX Application Thread
                        Platform.runLater(() -> ivWebCam.setImage(fxImage));
    
                        // Decode the barcode from the frame
                        decodeBarcode(bufferedImage);
                        if (gotBarcode){
                            isRunning = false;
                        }
                    }
    
                    // Adjust sleep to control frame rate (e.g., 30 FPS = 33ms)
                    Thread.sleep(66); // Approx 30 FPS
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            webcam.close();
            try {
                switchToBarcode();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        videoThread.setDaemon(true); // Ensures thread stops when application closes
        videoThread.start();
    }    

    public void stopWebCam(ActionEvent event) {
        isRunning = false;
        System.out.println("WebCam stopped");
    }

    private byte[] toByteArray(BufferedImage bufferedImage) throws IOException {
        try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "PNG", baos);
            return baos.toByteArray();
        }
    }

    String barcodeText;
    
    private void decodeBarcode(BufferedImage bufferedImage) {
    try {
        // Initialize ZXing reader
        com.google.zxing.Reader reader = new com.google.zxing.MultiFormatReader();
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        // Decode the image and get the result
        Result result = reader.decode(bitmap);

        if (result != null) {
            barcodeText = result.getText();
            System.out.println("Barcode Detected: " + barcodeText);
            gotBarcode = true;


            // You can trigger actions or update the UI based on the barcode content here
        }
    } catch (Exception e) {
        // Handle exceptions like no barcode detected
        // Optional: log or show a message if no barcode is found
    }
}


    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setStage(root);
    }
    public void switchToBarcode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BarcodeScene.fxml"));
        root = loader.load();

        BarcodeController barcodeController = loader.getController();
        barcodeController.setBarcode(barcodeText);
        
        Platform.runLater(() -> {
            try {
                stage = (Stage) ivWebCam.getScene().getWindow(); // Get the current stage
                setStage(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    

    public void setStage(Parent root2){
        String css = this.getClass().getResource("/application.css").toExternalForm();
        scene = new Scene(root2);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    // public void takePicture(ActionEvent event) throws IOException {
    //     Webcam webcam = Webcam.getDefault();

        
    //     webcam.open();

    //     //webcam.addWebcamListener(new WebcamListener());

    //     //System.out.println(webcam.getViewSize().toString() + "    ello");

    //     ImageIO.write(webcam.getImage(), "PNG", new File("src/png/BusyBarcode.png"));
    //     webcam.close(); 

    //     GenerateBarcode generateBarcode = new GenerateBarcode();
    //     generateBarcode.GenerateBarcode("src/png/BusyBarcode.png");
    // }
}
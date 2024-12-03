import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

//import com.google.zxing.Reader;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GenerateBarcode {
    //String filePath;

    public void GenerateBarcode(String filePath) {
        //this.filePath = filePath;
        System.out.println("GenerateBarcode");
        System.out.println(filePath);

        getGreyScale(filePath);
    }

    public void getGreyScale(String filePath){

        // Load the image
        Image image = new Image("file:"+ "src/png/BusyBarcode.png");
        System.out.println("hi");


        // Get image dimensions
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Create a writable image
        WritableImage grayscaleImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = grayscaleImage.getPixelWriter();

        // Convert each pixel to grayscale
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                Color grayscaleColor = new Color(gray, gray, gray, color.getOpacity());
                pixelWriter.setColor(x, y, grayscaleColor);
            }
        }

        // Save the grayscale image as a file
        try {
            // Convert WritableImage to BufferedImage
            BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(grayscaleImage, null);

            // Save the BufferedImage to a file
            File outputFile = new File("src/png/grayscale_image.png");
            ImageIO.write(bufferedImage, "png", outputFile);
            System.out.println("Grayscale image saved to: " + outputFile.getAbsolutePath());
            BarcodeReader();
        } catch (IOException e) {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }
    public void BarcodeReader() {
        try {
            // Load your grayscale image
            File file = new File("src/png/grayscale_image.png");
            BufferedImage bufferedImage = ImageIO.read(file);

            // Decode the image
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            MultiFormatReader reader = new com.google.zxing.MultiFormatReader();
            Result result = reader.decode(bitmap);

            System.out.println("Barcode text: " + result.getText());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

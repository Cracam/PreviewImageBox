/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PreviewImageBoxTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        PreviewImageBox previewImageBox = new PreviewImageBox();

    

        // Ajouter des images à partir de fichiers locaux
        addImageFromFile(previewImageBox, "C:\\BACKUP\\ENSE3\\Foyer\\Cartes Vertes\\design\\Classique\\Batch4\\RECTO1.png");
        addImageFromFile(previewImageBox, "C:\\BACKUP\\ENSE3\\Foyer\\Cartes Vertes\\design\\Classique\\Batch4\\verso_0.png");
        addImageFromFile(previewImageBox, "C:\\BACKUP\\ENSE3\\Foyer\\Cartes Vertes\\design\\Classique\\Batch4\\RECTO1.png");
        addImageFromFile(previewImageBox, "C:\\BACKUP\\ENSE3\\Foyer\\received_951688789163420.jpeg");
        // Créer la scène et la montrer
        Scene scene = new Scene(previewImageBox, 510, 510);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Preview Image Box Test");
        primaryStage.show();
    }



    // Méthode pour ajouter une image à partir d'un fichier
    private void addImageFromFile(PreviewImageBox previewImageBox, String filePath) {
        Image image = new Image("file:" + filePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200); // Taille initiale
        imageView.setFitHeight(200); // Taille initiale
        previewImageBox.addImageView(imageView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

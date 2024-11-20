
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PreviewImageBox extends Pane {

        private final List<ImageView> imageViews;
        private final double hgap = 10; // Espace horizontal entre les images
        private final double vgap = 10; // Espace vertical entre les images

        public PreviewImageBox() {
                imageViews = new ArrayList<>();

                // Configurer le Pane
                this.setWidth(510);
                this.setHeight(510);
                this.setMaxHeight(510);
                this.setMaxWidth(510);

                // Ajouter une bordure pour visualiser les limites du Pane
                this.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

                // Écouter l'ajout à la scène
                this.sceneProperty().addListener((obs, oldScene, newScene) -> {
                        if (newScene != null) {
                                // Quand la scène est prête, écouter les changements de taille
                                newScene.windowProperty().addListener((obsWindow, oldWindow, newWindow) -> {
                                        if (newWindow != null) {
                                                // Lorsque la fenêtre est prête, initialiser
                                                this.widthProperty().addListener((observable, oldWidth, newWidth) -> updatePane());
                                                this.heightProperty().addListener((observable, oldHeight, newHeight) -> updatePane());

                                                // Appeler updatePane pour s'assurer que tout est initialisé
                                                updatePane();
                                        }
                                });
                        }
                });
        }

        public void addImageView(ImageView imageView) {
                // Garder le ratio de l'image
                imageView.setPreserveRatio(true);

                imageViews.add(imageView);
                updatePane();
        }

        // Méthode pour mettre à jour le Pane avec les ImageView
        private void updatePane() {
                this.getChildren().clear();

                // Obtenir la taille disponible du Pane
                double width = this.getWidth();
                double height = this.getHeight();

                // Calculer l'espace disponible pour chaque image
                int imageCount = imageViews.size();
                if (imageCount == 0) {
                        return;
                }

                // Calculer le nombre de colonnes et de lignes
                int numCols = (int) Math.ceil(Math.sqrt(imageCount));
                int numRows = (int) Math.ceil((double) imageCount / numCols);
               System.out.println("Number row and col : "+numRows+"   "+numCols);

                int[][] heightArray=new int[numRows][numCols];
                int[][] widthArray=new int[numRows][numCols];
                
                int k=0;
                for(int i=0;(i<numRows | k<imageCount);i++){
                        for(int j=0;(j<numCols| k<imageCount);j++){
                                heightArray[i][j]=(int) imageViews.get(k).getImage().getHeight();
                                widthArray[i][j]=(int) imageViews.get(k).getImage().getWidth();
                                k++;
                        }
                }
                
                
                
//                System.out.println(imageViews.get(0).getImage().getHeight() + "   " + imageViews.get(0).getImage().getWidth());
//                for (int i = 0; i < imageViews.size(); i++) {
//                        ImageView imageView = imageViews.get(i);
//                        // Ajuster les tailles tout en préservant le ratio
//                        imageView.setFitWidth(minTileWidth);
//                        imageView.setFitHeight(minTileHeight);
//                        imageView.setPreserveRatio(true);
//                        imageView.setSmooth(true);
//
//                        // Positionner les images
//                        int col = i % numCols;
//                        int row = i / numCols;
//                        imageView.setLayoutX(col * (minTileWidth + hgap));
//                        imageView.setLayoutY(row * (minTileHeight + vgap));
//
//                        this.getChildren().add(imageView);
//                }
        }

}

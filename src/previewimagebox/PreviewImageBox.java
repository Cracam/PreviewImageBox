package previewimagebox;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import javafx.scene.layout.StackPane;

public class PreviewImageBox extends Pane {

        private final HashMap<String, ImageView> imageViews;
        private final double hgap = 10; // Espace horizontal entre les images
        private final double vgap = 10; // Espace vertical entre les images

        private int[] heightRatios;
        private int[] widthRatios;

        private int numCols;
        private int numRows;

        private boolean locked = false;


        public PreviewImageBox() {
                imageViews = new HashMap<>();
                //   this.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-style: solid;");
                // Add listeners to the width and height properties of the component
                this.widthProperty().addListener((observable, oldWidth, newWidth) -> {
                        //  System.out.println("Width changed: " + newWidth);

                                updatePane();

                });

                this.heightProperty().addListener((observable, oldHeight, newHeight) -> {
                        //   System.out.println("Height changed: " + newHeight);

                                updatePane();

                });

        }

        /**
         * This code add an imageView to the Pane
         *
         * @param key
         * @param imageView
         */
        public void setImageView(String key, ImageView imageView) {

                // Garder le ratio de l'image
                imageView.setPreserveRatio(true);
                imageViews.put(key, imageView);
                actuLinesColumnsMatrix();
                updatePane();

        }

        /**
         * Clear all the Image view currenly in the preview boxe
         */
        public void clearAllImagesViews() {
                imageViews.clear();
                actuLinesColumnsMatrix();
                updatePane();
        }

        /**
         * This code recalculate the min and maximum values that our program
         * will take
         */
        private void actuLinesColumnsMatrix() {

                // Calculer l'espace disponible pour chaque image
                int imageCount = imageViews.size();
                if (imageCount == 0) {
                        return;
                }

                // Calculer le nombre de colonnes et de lignes
                numCols = (int) Math.ceil(Math.sqrt(imageCount));
                numRows = (int) Math.ceil((double) imageCount / numCols);
                //  System.out.println("Number row and col : " + numRows + "   " + numCols);

                int[][] heightArray = new int[numRows][numCols];
                int[][] widthArray = new int[numCols][numRows];
                int col;
                int row;

                int i = 0;
                for (String key : imageViews.keySet()) {

                        col = i % numCols;
                        row = i / numCols;

                        heightArray[row][col] = (int) imageViews.get(key).getImage().getHeight();
                        widthArray[col][row] = (int) imageViews.get(key).getImage().getWidth();
                        i++;
                }

                heightRatios = new int[numRows];
                widthRatios = new int[numCols];

                for (int j = 0; j < heightRatios.length; j++) {
                        heightRatios[j] = findMaxValue(heightArray[j]);
                }

                for (int j = 0; j < widthRatios.length; j++) {
                        widthRatios[j] = findMaxValue(widthArray[j]);
                }

        }

        /**
         * Returns the maximum value in the given array of integers.
         *
         * @param array the array of integers
         * @return the maximum value in the array
         * @throws IllegalArgumentException if the array is null or empty
         */
        public static int findMaxValue(int[] array) {
                if (array == null || array.length == 0) {
                        throw new IllegalArgumentException("Array must not be null or empty");
                }

                int maxValue = array[0]; // Initialize maxValue with the first element

                for (int i = 1; i < array.length; i++) {
                        if (array[i] > maxValue) {
                                maxValue = array[i]; // Update maxValue if the current element is greater
                        }
                }

                return maxValue;
        }

        /**
         * return the sum of all the element of the array
         *
         * @param arr
         * @return
         */
        private int sum(int[] arr) {
                int ret = 0;
                for (int i = 0; i < arr.length; i++) {
                        ret = ret + arr[i];
                }
                return ret;
        }

       /**
     * This method will update the pane when it is resized
     */
    private void updatePane() {
        if (imageViews.isEmpty()) {
            return;
        }

        this.getChildren().clear();

        // Obtenir la taille disponible du Pane
        double width = this.getWidth();
        double height = this.getHeight();

        // Calculer la taille moyenne d'une "tuile"
        double maxWitdthFactor = (width - (numCols - 1) * hgap) / sum(widthRatios);
        double maxHeightFactor = (height - (numRows - 1) * vgap) / sum(heightRatios);
        double usedRatio;

        if (((numCols - 1) * hgap + maxHeightFactor * sum(widthRatios)) > width) {
            usedRatio = maxWitdthFactor;
        } else {
            usedRatio = maxHeightFactor;
        }

        int i = 0;
        for (String key : imageViews.keySet()) {
            ImageView imageView = imageViews.get(key);

            int col = i % numCols;
            int row = i / numCols;

            // Ajuster les tailles tout en préservant le ratio
            imageView.setFitWidth(usedRatio * widthRatios[col]);
            imageView.setFitHeight(usedRatio * heightRatios[row]);

            // Encapsuler l'ImageView dans un StackPane avec une bordure
            StackPane borderedImageView = createBorderedImageView(imageView);

            // Positionner les images
            double imageWidth = usedRatio * widthRatios[col];
            double imageHeight = usedRatio * heightRatios[row];

            borderedImageView.setLayoutX(col * (imageWidth + hgap)); // Aligner à gauche
            borderedImageView.setLayoutY(row * (imageHeight + vgap));

            this.getChildren().add(borderedImageView);
            i++;
        }
    }
        private StackPane createBorderedImageView(ImageView imageView) {
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(imageView);
                stackPane.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-style: solid;");
                return stackPane;
        }

     

}

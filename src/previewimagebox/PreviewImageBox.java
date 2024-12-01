package previewimagebox;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class PreviewImageBox extends Pane {

        private final List<ImageView> imageViews;
        private final double hgap = 10; // Espace horizontal entre les images
        private final double vgap = 10; // Espace vertical entre les images

        private int[] heightRatios;
        private int[] widthRatios;

        private int numCols;
        private int numRows;

        public PreviewImageBox() {
                imageViews = new ArrayList<>();

                this.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-style: solid;");
                // Add listeners to the width and height properties of the component
                this.widthProperty().addListener((observable, oldWidth, newWidth) -> {
                        System.out.println("Width changed: " + newWidth);
                        updatePane();
                });

                this.heightProperty().addListener((observable, oldHeight, newHeight) -> {
                        System.out.println("Height changed: " + newHeight);
                        updatePane();
                });
        }

        /**
         * This code add an imageView to the Pane
         *
         * @param imageView
         */
        public void addImageView(ImageView imageView) {
                // Garder le ratio de l'image
                imageView.setPreserveRatio(true);
                 imageView.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-style: solid;");
                imageViews.add(imageView);
                actuLinesColumnsMatrix();
                updatePane();
        }
        
        
        /**
         * Clear all the Image view currenly in the preview boxe
         */
        public void clearAllImagesViews(){
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
                System.out.println("Number row and col : " + numRows + "   " + numCols);

                int[][] heightArray = new int[numRows][numCols];
                int[][] widthArray = new int[numCols][numRows];
                int col;
                int row;
                for (int i = 0; i < imageViews.size(); i++) {
                        col = i % numCols;
                        row = i / numCols;

                        heightArray[row][col] = (int) imageViews.get(i).getImage().getHeight();
                        widthArray[col][row] = (int) imageViews.get(i).getImage().getWidth();

                }

                heightRatios = new int[numRows];
                widthRatios = new int[numCols];

                for (int i = 0; i < heightRatios.length; i++) {
                        heightRatios[i] = findMaxValue(heightArray[i]);
                }

                for (int i = 0; i < widthRatios.length; i++) {
                        widthRatios[i] = findMaxValue(widthArray[i]);
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
         * This method will Update the pane when this one is resised
         */
        private void updatePane() {
                              
                if (imageViews.size() == 0) {
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

//                System.out.println("___" + maxWitdthFactor);
//                System.out.println("X   " + width + "   X F  " + ((numCols - 1) * hgap + maxHeightFactor * sum(widthRatios)));
//                System.out.println("Y   " + height + "   Y F  " + ((numCols - 1) * vgap + maxWitdthFactor * sum(heightRatios)));

                if (((numCols - 1) * hgap + maxHeightFactor * sum(widthRatios)) > width) {
                        usedRatio = maxWitdthFactor;
                } else {
                        usedRatio = maxHeightFactor;

                }

                //System.out.println(imageViews.get(0).getImage().getHeight() + "   " + imageViews.get(0).getImage().getWidth());
                for (int i = 0; i < imageViews.size(); i++) {
                        ImageView imageView = imageViews.get(i);

                        int col = i % numCols;
                        int row = i / numCols;

                        // Ajuster les tailles tout en préservant le ratio
                        imageView.setFitWidth(usedRatio * widthRatios[col]);
                        imageView.setFitHeight(usedRatio * heightRatios[row]);
                        //   imageView.setPreserveRatio(true);
                        // imageView.setSmooth(true);

                        // Positionner les images
                        if (col == 0) {
                                imageView.setLayoutX(0);
                        } else {
                                imageView.setLayoutX(col * (usedRatio * widthRatios[col - 1] + hgap));
                                //  System.out.println("x "+col * (witdthFactor * widthRatios[col - 1] + hgap));
                        }
                        if (row == 0) {
                                imageView.setLayoutY(0);
                        } else {
                                imageView.setLayoutY(row * (usedRatio * heightRatios[row - 1] + vgap));
                                //System.out.println("y "+row * (heightFactor * heightRatios[row - 1] + vgap));

                        }
                        imageView.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-style: solid;");
                        this.getChildren().add(imageView);
                }
        }

}

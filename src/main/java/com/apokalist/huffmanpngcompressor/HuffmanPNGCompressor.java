package com.apokalist.huffmanpngcompressor;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class HuffmanPNGCompressor extends Application {
    private ImageView originalImage;
    private TextArea resultTextArea;
    private TextArea huffmanTreeArea;
    private Label compressionLabel;
    private Label pixelCountLabel;
    private Compressor compressor;


    /**
     * HuffmanNode class represents a node in the Huffman tree
     * Contains the color, frequency of that color in the image,
     *
     */
    static class HuffmanNode implements  Comparable<HuffmanNode> {
        String color;
        int frequency;
        HuffmanNode left, right;

        /*
         * Constructor for HuffmanNode
         *
         */
        HuffmanNode(String color, int frequency) {
            this.color = color;
            this.frequency = frequency;
        }

        /**
         * Constructor for HuffmanNode
         * This constructor used to create a node that has two children (left and right)
         *
         * @param frequency
         * @param left
         * @param right
         */
        HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        /**
         * Used to compare the frequency of two nodes
         *
         * @param other
         * @return
         */
        @Override
        public int compareTo (HuffmanNode other) {
            return Integer.compare(this.frequency, other.frequency);
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }


    /**
     * Main scene controller
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage primaryStage)  {
        primaryStage.setTitle("Huffman png compressor for Witek)");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Panels
        HBox topPanel = createTopPanel();
        HBox centrePanel = createCenterPanel();
        HBox bottomPanel = createBottomPanel();

        // Setting panels
        root.setTop(topPanel);
        root.setCenter(centrePanel);
        root.setBottom(bottomPanel);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private HBox createCenterPanel() {
        HBox centerPanel = new HBox(20);
        centerPanel.setAlignment(Pos.TOP_CENTER);

        VBox imagePanel = new VBox(8);
        imagePanel.setAlignment(Pos.CENTER);
        imagePanel.getStyleClass().add("image-panel");

        Label imageLabel = new Label("Original Image");
        imageLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        imageLabel.getStyleClass().add("label");

        originalImage = new ImageView();
        originalImage.setFitWidth(250);
        originalImage.setFitHeight(250);
        originalImage.setPreserveRatio(true);

        imagePanel.getChildren().addAll(imageLabel, originalImage);

        VBox resultsPanel = new VBox(10);
        resultsPanel.setPrefWidth(450);
        resultsPanel.getStyleClass().add("results-panel");

        Label resultsLabel = new Label("Compression Results");
        resultsLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        resultsLabel.getStyleClass().add("label");

        resultTextArea = new TextArea();
        resultTextArea.setPrefHeight(160);
        resultTextArea.setEditable(false);
        resultTextArea.getStyleClass().add("text-area");

        Label treeLabel = new Label("Huffman Tree");
        treeLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        treeLabel.getStyleClass().add("label");

        huffmanTreeArea = new TextArea();
        huffmanTreeArea.setPrefHeight(220);
        huffmanTreeArea.setEditable(false);
        huffmanTreeArea.getStyleClass().addAll("text-area", "huffman-tree");

        resultsPanel.getChildren().addAll(resultsLabel, resultTextArea, treeLabel, huffmanTreeArea);

        centerPanel.getChildren().addAll(imagePanel, resultsPanel);
        return centerPanel;
    }


    /**
     * Creates top panel with functional buttons for loading, compressing, and clearing the image.
     *
     * @return HBox containing the original image and the result text area
     */
    private HBox createTopPanel() {
        HBox topPanel = new HBox (12);
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setPadding(new Insets(0,0,15,0));

        Button loadButton = createNiceButton("Load PNG", "button-accent");
        loadButton.setOnAction(e-> loadImage());

        Button compressButton = createNiceButton("Compress", "button-info");
        compressButton.setOnAction(e-> compressImage());

        Button clearButton = createNiceButton("Clear", "button-clear");
        clearButton.setOnAction(e -> clearAll());

        topPanel.getChildren().addAll(loadButton, compressButton, clearButton);
        return topPanel;
    }

    /**
     * Creates bottom panel with labels for compression ratio and pixel count.
     *
     * @return HBox containing the compression ratio and pixel count labels
     */
    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox(20);
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setPadding(new Insets(25, 0, 0, 0));
        bottomPanel.getStyleClass().add("bottom-panel");

        pixelCountLabel = new Label("Pixels: 0");
        pixelCountLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        pixelCountLabel.getStyleClass().add("label");

        compressionLabel = new Label("Compression: 0%");
        compressionLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        compressionLabel.getStyleClass().add("label");

        bottomPanel.getChildren().addAll(pixelCountLabel, compressionLabel);
        return bottomPanel;
    }

    /**
     * Creates a styled button with the given text and style class
     *
     * @param text the text to display on the button
     * @param styleClass the CSS style class to apply to the button
     * @return a Button instance with the specified text and style class
     */
    private Button createNiceButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().addAll("button", styleClass);
        return button;
    }


    /**
     * Loads a PNG image from the file system using a FileChooser dialog.
     * If the image is successfully loaded, it is displayed in the ImageView.
     * If there is an error, an alert is shown to the user.
     */
    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PNG Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                originalImage.setImage(image);
                clearResults();
            } catch (Exception e) {
                Utils.showAlert("Error", "I cant load it))) : " + e.getMessage());
            }
        }
    }

    /**
     * Compresses the loaded image using Huffman coding.
     * If no image is loaded, an alert is shown to the user.
     * If compression is successful, the results are displayed in the text areas.
     */
    private void compressImage() {
        Image image = originalImage.getImage();
        if (image == null) {
            Utils.showAlert("Error", "Load an image, witek!");
            return;
        }
        try {
            compressor = new Compressor(image);
            displayResults();
            displayHuffmanTree();
        } catch (Exception e) {
            Utils.showAlert("Error", "Issue in compression: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * Displays the results of the compression, including color frequency, Huffman codes, and compression stats
     */
    private void displayResults() {
        Map<String, Integer> colorFrequency = compressor.getColorFrequency();
        Map<String, String> huffmanCodes = compressor.getHuffmanCodes();
        int totalPixels = compressor.getTotalPixels();
        int originalSize = compressor.getOriginalSize();
        int compressedSize = compressor.getCompressedSize();
        double compressionRatio = compressor.getCompressionRatio();

        StringBuilder sb = new StringBuilder();
        sb.append("=== COLOR ANALYSIS ===\n");
        sb.append(String.format("Unique colors: %d\n", colorFrequency.size()));
        sb.append(String.format("Total pixels: %d\n\n", totalPixels));

        sb.append("=== TOP COLORS |===\n");
        List<Map.Entry<String, Integer>> sortedColors = new ArrayList<>(colorFrequency.entrySet());
        sortedColors.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        int count = 0;

        for (Map.Entry<String, Integer> entry : sortedColors) {
            if (count++ >= 8) break;

            String color = entry.getKey();
            int freq = entry.getValue();
            double percentage = (double) freq / totalPixels * 100;
            sb.append(String.format("%s: %d times (%.1f%%)\n", color, freq, percentage));
        }

        sb.append("\n=== HUFFMAN CODES ===\n");
        count = 0;
        for (Map.Entry<String, Integer> entry : sortedColors) {
            if (count++ >= 8) break;
            String color = entry.getKey();
            String code = huffmanCodes.get(color);
            sb.append(String.format("%s → %s\n", color, code));
        }

        sb.append(String.format("\n=== COMPRESSION STATS ===\n"));
        sb.append(String.format("Original size: %d bits\n", originalSize));
        sb.append(String.format("Compressed size: %d bits\n", compressedSize));
        sb.append(String.format("Saved: %d bits\n", originalSize - compressedSize));

        resultTextArea.setText(sb.toString());

        pixelCountLabel.setText(String.format("Pixels: %d | Colors: %d", totalPixels, colorFrequency.size()));
        compressionLabel.setText(String.format("Compression: %.1f%%", compressionRatio));
    }

    private void displayHuffmanTree() {
        HuffmanNode root = compressor.getHuffmanRoot();
        StringBuilder sb = new StringBuilder();
        buildTreeVisualization(root, "", true, sb);
        huffmanTreeArea.setText(sb.toString());
    }


    /**
     * Recursively builds a string representation of the Huffman tree for visualization
     * StackOverflow logic source
     *
     * @param node the current node in the Huffman tree
     * @param prefix the prefix string to use for indentation
     * @param isLast whether this node is the last child of its parent
     * @param sb the StringBuilder to append the visualization to
     */
    private void buildTreeVisualization(HuffmanNode node, String prefix, boolean isLast, StringBuilder sb) {
        if (node != null) {
            sb.append(prefix);
            sb.append(isLast ? "└── " : "├── ");
            if (node.isLeaf()) {
                sb.append(String.format("%s (%d)", node.color, node.frequency));
            } else {
                sb.append(String.format("* (%d)", node.frequency));
            }
            sb.append("\n");
            if (!node.isLeaf()) {
                String newPrefix = prefix + (isLast ? "    " : "│   ");
                buildTreeVisualization(node.left, newPrefix, false, sb);
                buildTreeVisualization(node.right, newPrefix, true, sb);
            }
        }
    }

    private void clearAll() {
        originalImage.setImage(null);
        clearResults();
    }

    private void clearResults() {
        resultTextArea.clear();
        huffmanTreeArea.clear();
        pixelCountLabel.setText("Pixels: 0");
        compressionLabel.setText("Compression: 0%");
    }

    /**
     * Main method to launch the application
     *
     * @param args command line arguments
     */
    public static void main (String [] args) {
        launch(args);
    }


}
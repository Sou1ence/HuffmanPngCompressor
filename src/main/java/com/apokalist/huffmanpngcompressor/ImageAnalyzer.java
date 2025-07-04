package com.apokalist.huffmanpngcompressor;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.HashMap;

/*
 * This class analyzes an image to count the frequency of each color
 */
public class ImageAnalyzer {

    /**
     * Analyzes the colors in the given image and returns a map of color hex codes to their frequency.
     *
     * @param image The image to analyze.
     * @return A map where keys are color hex codes and values are their frequency in the image.
     */
    public Map <String, Integer> analyzeColors(Image image ) {

        Map<String, Integer> colorFrequency = new HashMap<>();
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                String hexCode = colorToHex(color);
                // ColorFrequency.put(hexCode, colorFrequency.getOrDefault(hexCode, 0) + 1);
                colorFrequency.merge(hexCode, 1, Integer::sum);
            }
        }
        return colorFrequency;
    }

    /**
     * Converts a Color object to its hexadecimal representation.
     *
     * @param color The Color object to convert.
     * @return A string representing the color in hexadecimal format.
     */
    private String colorToHex(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        /// Ensuring that rgb in range 0-255
        return String.format("#%02X%02X%02X", r, g, b);
    }
}
package com.apokalist.huffmanpngcompressor;

import javafx.scene.image.Image;

import java.util.Map;

/**
 * Core class to represent the compressor functionality
 */
public class Compressor {
    private HuffmanTree huffmanTree;
    private Map<String, Integer> colorFrequency;

    private int totalPixels;
    private double compressionRatio;

    private int originalSize;
    private int compressedSize;



    public Compressor(Image image) {
        ImageAnalyzer iAM = new ImageAnalyzer();
        colorFrequency = iAM.analyzeColors(image);
        huffmanTree = new HuffmanTree(colorFrequency);
        calculateStatistics();
    }

    private void calculateStatistics () {
        /// Original Size of the image in bits
        totalPixels = colorFrequency.values().stream().mapToInt(Integer::intValue).sum();

        // 24 bits (3 bytes per pixel for RGB)
        originalSize = totalPixels * 24;

        compressedSize = calculateCompressedSize();
        compressionRatio = (1.0 - (double) compressedSize / originalSize);

        //  ___ Note for Witold: The alpha channel is intentionally excluded
        //  as it is deemed unnecessary for the compression process ___
    }



    private int  calculateCompressedSize() {
        int totalBits = 0;

        Map<String, String> huffmanCodes = huffmanTree.getCodes();

        for (Map.Entry<String, Integer> entry : colorFrequency.entrySet()) {
            String color = entry.getKey();
            int frequency = entry.getValue();

            String code = huffmanCodes.get(color);
            totalBits += code.length() * frequency;
        }
        return totalBits;

    }


    // GETTERS
    public Map <String, Integer> getColorFrequency() {
        return colorFrequency;
    }

    public Map <String, String> getHuffmanCodes() {
        return huffmanTree.getCodes();
    }


    public int getTotalPixels() {
        return totalPixels;
    }

    public double getCompressionRatio() {
        return compressionRatio;
    }

    public int getOriginalSize() {
        return originalSize;
    }

    public int getCompressedSize() {
        return compressedSize;
    }

    public HuffmanPNGCompressor.HuffmanNode getHuffmanRoot() {
        return huffmanTree.getRoot();
    }


}



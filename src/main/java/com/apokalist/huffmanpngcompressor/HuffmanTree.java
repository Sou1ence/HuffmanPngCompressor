package com.apokalist.huffmanpngcompressor;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private HuffmanPNGCompressor.HuffmanNode root;
    private Map <String, String> huffmanCodes;

    public HuffmanTree(Map <String, Integer> colorFrequency) {
        buildTree(colorFrequency);
        huffmanCodes = new HashMap<>();
        generateCodes(root, "");
    }

    public void buildTree(Map<String, Integer> colorFrequency) {
        PriorityQueue <HuffmanPNGCompressor.HuffmanNode> pq= new PriorityQueue<>();

        // Create a priority queue to hold the nodes based on their frequency
        for (Map.Entry<String, Integer> entry : colorFrequency.entrySet())
            pq.offer(new HuffmanPNGCompressor.HuffmanNode(entry.getKey(), entry.getValue()));

        while (pq.size() > 1) {
            HuffmanPNGCompressor.HuffmanNode left = pq.poll();
            HuffmanPNGCompressor.HuffmanNode right = pq.poll();
            HuffmanPNGCompressor.HuffmanNode parent = new HuffmanPNGCompressor.
                    HuffmanNode(left.frequency + right.frequency, left, right);
            pq.offer(parent);
        }
        root = pq.poll();
    }

    private void generateCodes (HuffmanPNGCompressor.HuffmanNode node, String code) {
        if (node.isLeaf()) {
            huffmanCodes.put(node.color, code.isEmpty() ? "0" : code);
            return;
        }

        if (node.left != null)
            generateCodes(node.left, code + "0");

        if (node.right != null)
            generateCodes(node.right, code + "1");
    }

    public Map <String, String > getCodes() {
        return huffmanCodes;
    }

    public HuffmanPNGCompressor.HuffmanNode getRoot() {
        return root;
    }
}

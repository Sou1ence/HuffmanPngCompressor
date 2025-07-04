# Huffman PNG Compressor

A JavaFX desktop application for PNG image compression using Huffman coding algorithm with visual tree representation and compression statistics.

---
## Preview
![изображение](https://github.com/user-attachments/assets/f6ea3faf-42b6-479c-8c42-ee95e550007c)

---

## Features

- **Image Loading**: Load PNG images through intuitive file dialog
- **Color Analysis**: Analyze color distribution with frequency statistics
- **Huffman Compression**: Generate optimal Huffman codes for image colors
- **Compression Statistics**: Display original vs compressed sizes and ratios
- **Tree Visualization**: View the constructed Huffman tree structure
- **Modern UI**: Dark theme with responsive design

## System Requirements

- **Java**: JDK 17 or higher
- **JavaFX**: JavaFX SDK 17 or higher
- **OS**: Windows, macOS, or Linux
- **Memory**: 512MB RAM minimum

## Installation

1. **Prerequisites**
   ```bash
   # Verify Java installation
   java -version
   
   # Should show Java 17 or higher
   ```

2. **Clone Repository**
   ```bash
   git clone https://github.com/Sou1ence/HuffmanPngCompressor.git
   cd HuffmanPngCompressor
   ```

3. **Setup JavaFX**
   - Download JavaFX SDK 17+ from [OpenJFX](https://openjfx.io/)
   - Extract to your preferred location
   - Configure your IDE to include JavaFX libraries

4. **Run Application**
   ```bash
   # Using module path (adjust path to your JavaFX installation)
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.apokalist.huffmanpngcompressor.HuffmanPNGCompressor
   ```

## Usage

### Basic Workflow

1. **Launch Application**
   - Run the main class `HuffmanPNGCompressor`
   - The application window opens with control buttons and display areas

2. **Load Image**
   - Click "Load PNG" button
   - Select a PNG file from the file dialog
   - Image appears in the preview area

3. **Compress Image**
   - Click "Compress" button
   - View results including:
     - Color frequency analysis
     - Huffman codes for top colors
     - Compression statistics
     - Tree visualization

4. **Clear Results**
   - Click "Clear" button to reset all data

### Understanding Results

**Color Analysis**
- Shows unique colors count and total pixels
- Displays top 8 most frequent colors with percentages

**Compression Statistics**
- Original size (in bits)
- Compressed size (theoretical)
- Compression ratio
- Bits saved

**Huffman Tree**
- Textual representation of the compression tree
- Shows the hierarchical structure used for encoding

## Architecture

### Core Classes

```
com.apokalist.huffmanpngcompressor/
├── HuffmanPNGCompressor.java    # Main application class
├── Compressor.java              # Compression logic coordinator
├── HuffmanTree.java             # Tree construction and code generation
├── ImageAnalyzer.java           # Color frequency analysis
├── Utils.java                   # UI utilities
└── HelloController.java         # Future extensions
```

### Key Components

**HuffmanPNGCompressor**
- Main application entry point
- UI management and event handling
- Contains HuffmanNode inner class for tree nodes

**Compressor**
- Integrates analysis and tree construction
- Calculates compression statistics
- Provides unified interface for compression operations

**HuffmanTree**
- Builds optimal Huffman tree from color frequencies
- Generates binary codes for each color
- Uses priority queue for efficient tree construction

**ImageAnalyzer**
- Extracts color information from PNG images
- Converts colors to hexadecimal representation
- Calculates frequency distributions

## Technical Details

### Compression Algorithm

1. **Color Extraction**: Analyze image pixels to identify unique colors
2. **Frequency Analysis**: Count occurrences of each color
3. **Tree Construction**: Build Huffman tree using priority queue
4. **Code Generation**: Assign binary codes based on tree structure
5. **Size Calculation**: Compute theoretical compressed size

### Color Processing

- Uses 24-bit RGB color model (excludes alpha channel)
- Converts colors to hexadecimal format for processing
- Handles color frequency mapping efficiently

### UI Styling

The application uses CSS styling (`style.css`) with:
- Dark theme for modern appearance
- Styled buttons with hover effects
- Formatted text areas for results display
- Responsive layout design

### Primary Colors
| Color | Badge | Hex Code | Usage | Description |
|-------|-------|----------|-------|-------------|
| ⚫ Dark Charcoal | ![Dark Charcoal](https://img.shields.io/badge/Dark_Charcoal-2c2c2a?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#2c2c2a` | Main background | Primary application background |
| ⬛ Medium Gray | ![Medium Gray](https://img.shields.io/badge/Medium_Gray-3f3f3c?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#3f3f3c` | Panel backgrounds | Image and results panel containers |
| ◼️ Dark Gray | ![Dark Gray](https://img.shields.io/badge/Dark_Gray-272726?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#272726` | Secondary backgrounds | Text areas and bottom panel |
| ⬜ Light Gray | ![Light Gray](https://img.shields.io/badge/Light_Gray-dfdfdc?style=flat&logo=materialdesignicons&logoColor=2c2c2a) | `#dfdfdc` | Primary text | Main text and labels |
| 🔘 Muted Gray | ![Muted Gray](https://img.shields.io/badge/Muted_Gray-a19e96?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#a19e96` | Secondary text | Text area content |
| 🔲 Border Gray | ![Border Gray](https://img.shields.io/badge/Border_Gray-444444?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#444444` | Borders | Text area borders and separators |

### Accent Colors
| Color | Badge | Hex Code | Usage | Description |
|-------|-------|----------|-------|-------------|
| 🔶 Warm Orange | ![Warm Orange](https://img.shields.io/badge/Warm_Orange-ca7b5d?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#ca7b5d` | Load button | Primary action button |
| 🟣 Purple | ![Purple](https://img.shields.io/badge/Purple-7f72c3?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#7f72c3` | Compress button | Secondary action button |
| 🔴 Red | ![Red](https://img.shields.io/badge/Red-d32f2f?style=flat&logo=materialdesignicons&logoColor=dfdfdc) | `#d32f2f` | Clear button | Destructive action button |

## Limitations

- **No File Output**: Calculates theoretical compression without generating compressed files
- **Alpha Channel**: Excludes transparency information from compression
- **Display Limit**: Shows only top 8 colors in results
- **Single Image**: Processes one image at a time

## Future Enhancements

- File output generation for compressed images
- Alpha channel support for transparency
- Graphical Huffman tree visualization
- Batch processing capabilities
- Export options for compression data
- Performance optimizations for large images

## Troubleshooting

**Common Issues**

- **JavaFX not found**: Ensure JavaFX is properly configured in module path
- **Image loading fails**: Verify file is valid PNG format
- **UI not displaying**: Check CSS file is in resources directory
- **Memory issues**: Increase JVM heap size for large images

**Performance Tips**

- Use images under 10MB for optimal performance
- Close and reopen application if memory usage is high
- Clear results between different image analyses
 


## Repository

**GitHub**: [https://github.com/Sou1ence/HuffmanPngCompressor.git](https://github.com/Sou1ence/HuffmanPngCompressor.git)

For issues, feature requests, or contributions, please use the GitHub repository.

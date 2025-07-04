module com.apokalist.huffmanpngcompressor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.apokalist.huffmanpngcompressor to javafx.fxml;
    exports com.apokalist.huffmanpngcompressor;
}
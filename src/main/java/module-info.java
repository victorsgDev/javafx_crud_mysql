module com.example.cuestion3_hito3t {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    requires org.kordamp.bootstrapfx.core;
    requires java.security.jgss;

    opens com.example.cuestion3_hito3t to javafx.fxml;
    exports com.example.cuestion3_hito3t;
}
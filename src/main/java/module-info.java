module com.example.proyecto_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.proyecto_1 to javafx.fxml;
    exports com.example.proyecto_1;
    exports com.example.proyecto_1.controladores;
    opens com.example.proyecto_1.controladores to javafx.fxml;
    exports com.example.proyecto_1.modelos;
    opens com.example.proyecto_1.modelos to javafx.fxml;
}
package com.example.proyecto_1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Sistema de Asignaciones");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
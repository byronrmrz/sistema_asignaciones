package com.example.proyecto_1.controladores;

import com.example.proyecto_1.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    @FXML
    private Button asignmentButton;

    @FXML
    private Button verifyButton;


    @FXML
    void gotoAssign(ActionEvent event) {
        System.out.println("Navegando a la ventana de Asignaciones...");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("AsignacionesView.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Asignaciones");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gotoVerify(ActionEvent event) {
        System.out.println("Navegando a la ventana de Verificación de Cursos...");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("VerificacionView.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Asignaciones");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

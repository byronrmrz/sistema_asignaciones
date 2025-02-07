package com.example.proyecto_1.controladores;

import com.example.proyecto_1.modelos.Conexion;
import com.example.proyecto_1.modelos.Estudiante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificacionView {

    @FXML
    private ComboBox<String> cursoComboBox;

    @FXML
    private TableView<Estudiante> estudiantesTable;

    @FXML
    private TableColumn<Estudiante, String> carnetColumn;

    @FXML
    private TableColumn<Estudiante, String> nombreColumn;

    @FXML
    private TableColumn<Estudiante, String> emailColumn;

    @FXML
    private TableColumn<Estudiante, String> fechaColumn;

    @FXML
    private TableColumn<Estudiante, Boolean> solventeColumn;

    @FXML
    private PieChart estudiantesPieChart;

    private ObservableList<Estudiante> estudiantesFiltrados;

    @FXML
    public void initialize() {
        carnetColumn.setCellValueFactory(new PropertyValueFactory<>("carnet"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        solventeColumn.setCellValueFactory(new PropertyValueFactory<>("solvente"));

        estudiantesFiltrados = FXCollections.observableArrayList();

        cargarCursos();

        estudiantesPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Solventes", 0),
                new PieChart.Data("No Solventes", 0)
        ));

        cursoComboBox.setOnAction(event -> aplicarFiltros());
    }

    private void cargarCursos() {
        Conexion conexion = new Conexion();
        try {
            String sql = "SELECT nombre_curso FROM cursos";
            PreparedStatement stmt = conexion.getConexion().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ObservableList<String> cursos = FXCollections.observableArrayList();
            while (rs.next()) {
                cursos.add(rs.getString("nombre_curso"));
            }
            cursoComboBox.setItems(cursos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void aplicarFiltros() {
        String cursoSeleccionado = cursoComboBox.getValue();
        Conexion conexion = new Conexion();
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT e.carnet, e.nombre, e.correo, a.fecha_asignacion, a.pagado " +
                            "FROM estudiante e " +
                            "JOIN asignaciones a ON e.carnet = a.carnet " +
                            "JOIN cursos c ON a.id_curso = c.id_curso " +
                            "WHERE 1=1 "
            );

            if (cursoSeleccionado != null) {
                sql.append("AND c.nombre_curso = ? ");
            }

            PreparedStatement stmt = conexion.getConexion().prepareStatement(sql.toString());

            int paramIndex = 1;
            if (cursoSeleccionado != null) {
                stmt.setString(paramIndex++, cursoSeleccionado);
            }

            ResultSet rs = stmt.executeQuery();
            estudiantesFiltrados.clear();

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        rs.getString("carnet"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getDate("fecha_asignacion").toLocalDate(),
                        rs.getBoolean("pagado")
                );
                estudiantesFiltrados.add(estudiante);
            }

            estudiantesTable.setItems(estudiantesFiltrados);

            actualizarGrafico();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarGrafico() {
        long solventes = estudiantesFiltrados.stream().filter(Estudiante::isSolvente).count();
        long noSolventes = estudiantesFiltrados.size() - solventes;

        estudiantesPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Solventes", solventes),
                new PieChart.Data("No Solventes", noSolventes)
        ));
    }
}
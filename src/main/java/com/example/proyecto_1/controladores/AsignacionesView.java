package com.example.proyecto_1.controladores;

import com.example.proyecto_1.modelos.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.sql.*;
import java.time.LocalDate;

public class AsignacionesView {

    @FXML
    private ListView<String> cursosDisponiblesList;

    @FXML
    private ListView<String> cursosAsignadosList;

    @FXML
    private Button asignarButton;

    @FXML
    private CheckBox pagarAhoraCheckbox;

    @FXML
    private TextField tarjetaField, cSeguridadField, expiracionField, nombreCardField, nitField, nombreField, apellidoField, carnetField, telefonoField, emailField, docField;

    @FXML
    private Label totalLabel;

    private ObservableList<String> cursosAsignados = FXCollections.observableArrayList();
    private double totalMonto = 0;

    private double getPrecioCurso(String nombre_curso) {
        Conexion conexion = new Conexion();
        String sql = "SELECT precio FROM cursos WHERE nombre_curso = ?";
        try {
            PreparedStatement stm = conexion.getConexion().prepareStatement(sql);
            stm.setString(1, nombre_curso);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("precio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public void obtener() {
        ObservableList<String> cursosDisponibles = FXCollections.observableArrayList();
        Conexion conexion = new Conexion();
        String sql = "SELECT * FROM cursos";
        try {
            Statement stm = conexion.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String curso = rs.getString("nombre_curso");
                cursosDisponibles.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cursosDisponiblesList.setItems(cursosDisponibles);
    }

    private void actualizarTotalLabel() {
        totalLabel.setText("Total: Q" + totalMonto);
    }


    @FXML
    public void initialize() {
        obtener();
        cursosAsignadosList.setItems(cursosAsignados);

        cursosDisponiblesList.setOnMouseClicked(event -> {
            String selectedCurso = cursosDisponiblesList.getSelectionModel().getSelectedItem();
            if (selectedCurso != null && !cursosAsignados.contains(selectedCurso)) {
                cursosAsignados.add(selectedCurso);
                totalMonto += getPrecioCurso(selectedCurso);
                actualizarTotalLabel();
                validateFields();
            }
        });

        cursosAsignadosList.setOnMouseClicked(event -> {
            String selectedCurso = cursosAsignadosList.getSelectionModel().getSelectedItem();
            if (selectedCurso != null) {
                cursosAsignados.remove(selectedCurso);
                totalMonto -= getPrecioCurso(selectedCurso);
                actualizarTotalLabel();
                validateFields();
            }
        });

        pagarAhoraCheckbox.selectedProperty().addListener((obs, oldValue, newValue) -> validateFields());

        addTextFieldListeners(nombreField, carnetField, telefonoField, emailField, tarjetaField, cSeguridadField, expiracionField, nombreCardField, docField, nitField);

        validateFields();
        actualizarTotalLabel();

        Conexion conexion = new Conexion();
        try (Statement stmt = conexion.getConexion().createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTextFieldListeners(TextField... fields) {
        for (TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        }
    }


    @FXML
    void validateFields() {
        boolean pagarAhora = pagarAhoraCheckbox.isSelected();
        tarjetaField.setDisable(!pagarAhora);
        cSeguridadField.setDisable(!pagarAhora);
        expiracionField.setDisable(!pagarAhora);
        nombreCardField.setDisable(!pagarAhora);
        nitField.setDisable(!pagarAhora);
        asignarButton.setDisable(!todosCamposValidos());
    }

    private boolean NombreValido(String texto) {
        return texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    private boolean nombreCardValido(String texto) {
        return texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }


    private boolean numeroValido(String texto) {
        return texto.matches("^[0-9]+$");
    }

    private boolean numeroTarjetaValido(String texto) {
        return texto.matches("^[0-9]{16,}$");
    }
    private boolean cSeguridadValid(String texto) {
        return texto.matches("^[0-9]{3,}$");
    }
    private boolean fechaVencValido(String texto) {
        return texto.matches("^(0[1-9]|1[0-2])/\\d{2}$");
    }

    private boolean emailValido(String texto) {
        return texto.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    @FXML
    private void limpiarCampos() {
        tarjetaField.clear();
        cSeguridadField.clear();
        expiracionField.clear();
        nombreCardField.clear();
        nitField.clear();
        nombreField.clear();
        apellidoField.clear();
        carnetField.clear();
        telefonoField.clear();
        emailField.clear();
        docField.clear();
        cursosAsignadosList.getSelectionModel().clearSelection();
        cursosAsignados.clear();
        pagarAhoraCheckbox.setSelected(false);
        totalMonto = 0;
        actualizarTotalLabel();
    }

    @FXML
    public void asignarCurso(ActionEvent actionEvent) {
        if (!NombreValido(nombreField.getText()) || !NombreValido(apellidoField.getText())) {
            mostrarAlerta("Error", "El nombre y apellido no pueden contener números, o estar vacíos.");
            return;
        }

        if (!numeroValido(telefonoField.getText()) || !numeroValido(carnetField.getText()) || !numeroValido(docField.getText())) {
            mostrarAlerta("Error", "Revisa que el Teléfono, carnét o Documento de identidad solo contengan números");
            return;
        }

        if (!emailValido(emailField.getText())) {
            mostrarAlerta("Error", "El formato de email está incorrecto");
            return;  // Detener la ejecución si los campos no son válidos
        }

        if (cursosAsignados.isEmpty()) {
            mostrarAlerta("Error", "Debe asignar al menos un curso.");
            return;
        }

        if (pagarAhoraCheckbox.isSelected()) {
            if (!numeroTarjetaValido(tarjetaField.getText())) {
                mostrarAlerta("Error", "El Número de la tarjeta debe contener 16 números.");
                return;
            }
            if (!fechaVencValido(expiracionField.getText())) {
                mostrarAlerta("Error", "La fecha de vencimiento debe tener un formato como este MM/AA.");
                return;
            }
            if (!cSeguridadValid(cSeguridadField.getText())) {
                mostrarAlerta("Error", "El código de seguridad debe contener solamente 3 números.");
                return;
            }
        }

        String carnet = carnetField.getText().trim();
        boolean pagoRealizado = pagarAhoraCheckbox.isSelected();

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        int reintentos = 0;
        while (reintentos < 5) {
            try {
                conn.setAutoCommit(false);
                String nombreCompleto = nombreField.getText().trim() + " " + apellidoField.getText().trim();

                String verificarEstudianteSQL = "SELECT * FROM estudiante WHERE carnet = ?";
                try (PreparedStatement psVerificarEstudiante = conn.prepareStatement(verificarEstudianteSQL)) {
                    psVerificarEstudiante.setString(1, carnet);
                    ResultSet rsEstudiante = psVerificarEstudiante.executeQuery();

                    if (rsEstudiante.next()) {
                        String updateEstudianteSQL = "UPDATE estudiante SET nombre = ?, telefono = ?, correo = ? WHERE carnet = ?";
                        try (PreparedStatement psUpdateEstudiante = conn.prepareStatement(updateEstudianteSQL)) {
                            psUpdateEstudiante.setString(1, nombreCompleto);
                            psUpdateEstudiante.setString(2, telefonoField.getText().trim());
                            psUpdateEstudiante.setString(3, emailField.getText().trim());
                            psUpdateEstudiante.setString(4, carnet);
                            psUpdateEstudiante.executeUpdate();
                        }
                    } else {
                        String insertEstudianteSQL = "INSERT INTO estudiante (carnet, nombre, telefono, correo) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement psInsertEstudiante = conn.prepareStatement(insertEstudianteSQL)) {
                            psInsertEstudiante.setString(1, carnet);
                            psInsertEstudiante.setString(2, nombreCompleto);
                            psInsertEstudiante.setString(3, telefonoField.getText().trim());
                            psInsertEstudiante.setString(4, emailField.getText().trim());
                            psInsertEstudiante.executeUpdate();
                        }
                    }
                }

                for (String curso : cursosAsignados) {
                    String getIdCursoSQL = "SELECT id_curso FROM cursos WHERE nombre_curso = ?";
                    try (var psCurso = conn.prepareStatement(getIdCursoSQL)) {
                        psCurso.setString(1, curso);
                        ResultSet rsCurso = psCurso.executeQuery();
                        if (rsCurso.next()) {
                            int idCurso = rsCurso.getInt("id_curso");

                            String insertAsignacionSQL = "INSERT INTO asignaciones (carnet, id_curso, fecha_asignacion, pagado) VALUES (?, ?, CURRENT_DATE, ?)";
                            try (var psAsignacion = conn.prepareStatement(insertAsignacionSQL)) {
                                psAsignacion.setInt(1, Integer.parseInt(carnet));
                                psAsignacion.setInt(2, idCurso);
                                psAsignacion.setBoolean(3, pagoRealizado);
                                psAsignacion.executeUpdate();
                            }
                        }
                    }
                }

                if (pagoRealizado) {
                    String insertPagoSQL = "INSERT INTO HistorialPagos (carnet, monto, fecha_pago) VALUES (?, ?, CURRENT_DATE)";
                    try (var psPago = conn.prepareStatement(insertPagoSQL)) {
                        psPago.setInt(1, Integer.parseInt(carnet));
                        psPago.setDouble(2, calcularMontoTotal());
                        psPago.executeUpdate();
                    }
                }

                conn.commit();
                mostrarAlerta("Éxito", "Asignación y pago realizados correctamente.");
                break;

            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (e.getMessage().contains("SQLITE_BUSY")) {
                    reintentos++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } else {
                    mostrarAlerta("Error", "Ocurrió un error al realizar la asignación: " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            } finally {
                try {
                    conn.setAutoCommit(true);
                    limpiarCampos();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private double calcularMontoTotal() {
        return totalMonto;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean todosCamposValidos() {
        return !nombreField.getText().isEmpty() && !carnetField.getText().isEmpty() && !telefonoField.getText().isEmpty() && !emailField.getText().isEmpty()
                && (pagarAhoraCheckbox.isSelected() ? !tarjetaField.getText().isEmpty() && !cSeguridadField.getText().isEmpty() && !expiracionField.getText().isEmpty() && !nombreCardField.getText().isEmpty() && !nitField.getText().isEmpty() : true);
    }
}
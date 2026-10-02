package ni.edu.uam.empleadossistema.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;

public class EmpleadoController {
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCargo;
    @FXML
    private ComboBox<String> cmbDepartamento;
    @FXML
    private TextField txtSalario;
    @FXML
    private DatePicker dpFechaContratacion;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private TableView<Empleado> tblEmpleados;
    @FXML
    private TableColumn<Empleado, Integer> colId;
    @FXML
    private TableColumn<Empleado, String> colNombres;
    @FXML
    private TableColumn<Empleado, String> colApellidos;
    @FXML
    private TableColumn<Empleado, String> colCedula;
    @FXML
    private TableColumn<Empleado, String> colCorreo;
    @FXML
    private TableColumn<Empleado, String> colTelefono;
    @FXML
    private TableColumn<Empleado, String> colCargo;
    @FXML
    private TableColumn<Empleado, String> colDepartamento;
    @FXML
    private TableColumn<Empleado, Double> colSalario;
    @FXML
    private TableColumn<Empleado, LocalDate> colFechaContratacion;
    @FXML
    private TableColumn<Empleado, String> colEstado;

    private final ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurarTabla();
        configurarComboBox();
        cargarEmpleados();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    //Método encargado de cargar datos por defecto del combobox
    private void configurarComboBox(){
        cmbDepartamento.getItems().clear();
        cmbDepartamento.getItems().addAll("Recursos Humanos", "Contabilidad", "Ventas", "Tecnología", "Otros");
        cmbEstado.getItems().clear();
        cmbEstado.getItems().addAll("Activo", "Inactivo");
    }

    @FXML
    private void cargarEmpleados() {
        listaEmpleados.clear();
        String sql = "SELECT * FROM empleado";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombres(resultSet.getString("nombres"));
                empleado.setApellidos(resultSet.getString("apellidos"));
                empleado.setCedula(resultSet.getString("cedula"));
                empleado.setCorreo(resultSet.getString("correo"));
                empleado.setTelefono(resultSet.getString("telefono"));
                empleado.setCargo(resultSet.getString("cargo"));
                empleado.setDepartamento(resultSet.getString("departamento"));
                empleado.setSalario(resultSet.getDouble("salario"));
                empleado.setFechaContratacion(resultSet.getDate("fecha_contratacion").toLocalDate());
                empleado.setEstado(resultSet.getString("estado"));
                listaEmpleados.add(empleado);
            }
            tblEmpleados.setItems(listaEmpleados);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    @FXML
    private void guardarEmpleado() {
        if (!validarCampos()) {
            return;
        }
        String sql = "INSERT INTO empleado(nombres, apellidos, cedula, correo, telefono, cargo, departamento, salario, fecha_contratacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, txtNombres.getText().trim());
            statement.setString(2, txtApellidos.getText().trim());
            statement.setString(3, txtCedula.getText().trim());
            statement.setString(4, txtCorreo.getText().trim());
            statement.setString(5, txtTelefono.getText().trim());
            statement.setString(6, txtCargo.getText().trim());
            statement.setString(7, cmbDepartamento.getValue());
            statement.setDouble(8, Double.parseDouble(txtSalario.getText().trim()));
            statement.setDate(9, Date.valueOf(dpFechaContratacion.getValue()));
            statement.setString(10, cmbEstado.getValue());
            statement.executeUpdate();
            mostrarAlerta(
                    Alert.AlertType.INFORMATION, "Registro almacenado", "Empleado registrado", "El empleado se ha almacenado exitosamente");
            limpiarCampos();
            cargarEmpleados();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean validarCampos() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String cedula = txtCedula.getText().trim();
        String salario = txtSalario.getText().trim();

        if (nombres.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || cmbDepartamento.getValue() == null || salario.isEmpty() || dpFechaContratacion.getValue() == null || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campos incompletos", "Por favor, complete los campos requeridos.");
            return false;
        }
        try {
            Double.parseDouble(salario);
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Salario inválido", "El salario debe ser un valor numérico.");
            return false;
        }
        return true;
    }

    @FXML
    private void limpiarCampos() {
        txtNombres.clear();
        txtApellidos.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCargo.clear();
        txtSalario.clear();
        cmbDepartamento.getSelectionModel().clearSelection();
        dpFechaContratacion.setValue(null);
        cmbEstado.getSelectionModel().clearSelection();
    }



}

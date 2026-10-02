package ni.edu.uam.empleadossistema.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.empleadossistema.database.DatabaseConnection;
import ni.edu.uam.empleadossistema.model.Empleado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    @FXML
    private ComboBox<String> cmbConsulta;

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
        cmbDepartamento.getItems().addAll("Recursos Humanos", "Contabilidad", "Ventas", "Tecnología", "Marketing", "Otros");
        cmbEstado.getItems().clear();
        cmbEstado.getItems().addAll("Activo", "Inactivo");
        cmbConsulta.getItems().clear();
        cmbConsulta.getItems().addAll("Mostrar todos", "Empleados activos", "Ordenar por salario", "Ordenar por apellido");
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
            statement.setString(4, textoONulo(txtCorreo.getText()));
            statement.setString(5, textoONulo(txtTelefono.getText()));
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
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el empleado", ex.getMessage());
        }
    }

    private String textoONulo(String valor) {
        String texto = valor == null ? "" : valor.trim();
        return texto.isEmpty() ? null : texto;
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
        String cargo = txtCargo.getText().trim();
        String salario = txtSalario.getText().trim();

        if (nombres.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || cargo.isEmpty() || cmbDepartamento.getValue() == null || salario.isEmpty() || dpFechaContratacion.getValue() == null || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campos incompletos", "Por favor, complete los campos requeridos.");
            return false;
        }
        double salarioNumerico;
        try {
            salarioNumerico = Double.parseDouble(salario);
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Salario inválido", "El salario debe ser un valor numérico.");
            return false;
        }
        if (salarioNumerico < 0) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Salario inválido", "El salario no puede ser negativo.");
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

    @FXML
    private void realizarConsulta() {

        String consultaSeleccionada = cmbConsulta.getValue();
        if (consultaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Consulta no seleccionada", "Seleccione una consulta.");
            return;
        }
        String sql = "";
        switch (consultaSeleccionada) {
            case "Mostrar todos":
                sql = "SELECT * FROM empleado";
                break;
            case "Empleados activos":
                sql = "SELECT * FROM empleado WHERE estado = 'Activo'";
                break;
            case "Ordenar por salario":
                sql = "SELECT * FROM empleado ORDER BY salario DESC";
                break;
            case "Ordenar por apellido":
                sql = "SELECT * FROM empleado ORDER BY apellidos ASC";
                break;
        }
        listaEmpleados.clear();

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
    private void salirAplicacion() {
        System.exit(0);
    }
}

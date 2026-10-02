package ni.edu.uam.empleadossistema.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Comprobación temporal de conectividad entre Java y PostgreSQL.
 */
public class TestConexion {

    public static void main(String[] args) {
        String sql = "SELECT * FROM empleado";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Conexión exitosa con PostgreSQL.");
            System.out.println("Consulta ejecutada: " + sql);

            int filas = 0;
            while (resultSet.next()) {
                filas++;
                System.out.printf(
                        "%d | %s %s | %s | %s | %s%n",
                        resultSet.getInt("id"),
                        resultSet.getString("nombres"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("cargo"),
                        resultSet.getString("departamento"),
                        resultSet.getString("estado")
                );
            }

            System.out.println("Registros devueltos: " + filas);
        } catch (SQLException e) {
            System.err.println("Error al conectar con PostgreSQL.");
            e.printStackTrace();
        }
    }
}

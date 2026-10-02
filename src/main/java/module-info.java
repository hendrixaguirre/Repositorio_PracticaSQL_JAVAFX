module ni.edu.uam.empleadossistema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens ni.edu.uam.empleadossistema to javafx.fxml;
    exports ni.edu.uam.empleadossistema;
    exports ni.edu.uam.empleadossistema.database;
}
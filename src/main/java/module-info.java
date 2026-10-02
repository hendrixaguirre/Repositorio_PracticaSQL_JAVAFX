module ni.edu.uam.empleadossistema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens ni.edu.uam.empleadossistema to javafx.fxml;
    opens ni.edu.uam.empleadossistema.controller to javafx.fxml;
    opens ni.edu.uam.empleadossistema.model to javafx.base;

    exports ni.edu.uam.empleadossistema;
    exports ni.edu.uam.empleadossistema.database;
}

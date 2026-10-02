module ni.edu.uam.empleadossistema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ni.edu.uam.empleadossistema to javafx.fxml;
    exports ni.edu.uam.empleadossistema;
}
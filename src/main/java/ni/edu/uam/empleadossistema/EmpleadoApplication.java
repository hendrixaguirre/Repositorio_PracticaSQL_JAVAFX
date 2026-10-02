package ni.edu.uam.empleadossistema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpleadoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmpleadoApplication.class.getResource("empleado-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registro de Empleado");
        stage.setScene(scene);
        stage.show();
    }
}

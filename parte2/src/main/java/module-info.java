module david.practica_7_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens david.controlador to javafx.fxml;
    exports david.controlador;
}
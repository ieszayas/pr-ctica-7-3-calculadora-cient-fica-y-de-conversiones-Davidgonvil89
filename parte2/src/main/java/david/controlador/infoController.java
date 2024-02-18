package david.controlador;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class infoController {
    private Image imagen1;
    private Image imagen2;
    @javafx.fxml.FXML
    private Button bnSalir;

    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        Stage stage = (Stage) bnSalir.getScene().getWindow();
        stage.close();

    }

}

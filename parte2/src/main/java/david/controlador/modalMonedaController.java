package david.controlador;

import Modelo.Operaciones;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class modalMonedaController {
    @javafx.fxml.FXML
    private TextArea valor_mi_moneda;
    @javafx.fxml.FXML
    private Button btn_salir;
    @javafx.fxml.FXML
    private Button btn_aceptar;
    Operaciones modelo;

    public void setModelo (Operaciones modelo) {
        this.modelo = modelo;
    }
    public String obtenerNumeroDesdeTextArea() {
        return valor_mi_moneda.getText();
    }
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_salir.getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void aceptar(ActionEvent actionEvent) {
    }
}

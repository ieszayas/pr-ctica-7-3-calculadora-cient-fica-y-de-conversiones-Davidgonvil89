package david.controlador;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class modalMonedaController implements Initializable {

    private static ObservableList<String> lista;
    @FXML
    private CheckBox ch_dol;
    @FXML
    private Button bt_ok;
    @FXML
    private CheckBox ch_lib;
    @FXML
    private CheckBox ch_eur;
    @FXML
    private CheckBox ch_yen;

    public static ObservableList<String> getDatos() {
        return lista;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void ok(ActionEvent actionEvent) {
        lista = FXCollections.observableArrayList();

        if (ch_eur.isSelected()) {
            lista.add("EUR");
        }
        if (ch_dol.isSelected()) {
            lista.add("USD");
        }
        if (ch_lib.isSelected()) {
            lista.add("GBP");
        }
        if (ch_yen.isSelected()) {
            lista.add("YJP");
        }
        // Obtener el Stage de la ventana actual
        Stage stage = (Stage) bt_ok.getScene().getWindow();
        stage.close();
    }


}

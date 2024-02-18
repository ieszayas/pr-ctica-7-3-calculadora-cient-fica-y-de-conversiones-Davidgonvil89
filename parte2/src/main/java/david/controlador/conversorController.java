package david.controlador;

import Modelo.Operaciones;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class conversorController implements Initializable {

    private final ObservableList<String> SisConversion = FXCollections.observableArrayList("Moneda", "Longitud", "Tiempo");
    private final ObservableList<String> Monedas = FXCollections.observableArrayList("EUR", "USD", "GBP", "YPJ");
    private final ObservableList<String> Longitud = FXCollections.observableArrayList("mm", "dm", "cm", "m", "km");
    private final ObservableList<String> Tiempo = FXCollections.observableArrayList("milisegundos", "segundos", "minutos", "horas", "dias", "semanas", "años");
    String memoria_aux = "0";
    @javafx.fxml.FXML
    private Label L_resultado;
    @javafx.fxml.FXML
    private ComboBox cmOrigen;
    @javafx.fxml.FXML
    private ComboBox cmSeleccion;
    @javafx.fxml.FXML
    private ComboBox cmDestino;
    private Operaciones operaciones;
    private boolean flagResultado = false;
    private boolean flagDecimal = false;
    private String seleccion = "";
    @javafx.fxml.FXML
    private ProgressIndicator progreso;
    private modalMonedaController mc;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmSeleccion.setValue("Moneda");
        cmSeleccion.setItems(SisConversion);
        cmOrigen.setItems(Monedas);
        cmOrigen.setValue("EUR");
        cmDestino.setItems(Monedas);
        cmDestino.setValue("USD");
        operaciones = new Operaciones();

    }


    @javafx.fxml.FXML
    public void limpiarTodo(ActionEvent actionEvent) {
        borrarPantalla();
        memoria_aux = "0";

        flagDecimal = false;
    }

    @javafx.fxml.FXML
    public void escribir(KeyEvent event) {
        if (flagResultado) {
            borrarPantalla();
        }
        switch (event.getCode()) {
            case DELETE:
                borrarPantalla();
                break;
            case DECIMAL:
                ponerDecimal(null);
                break;
            case ENTER:
                igual(new ActionEvent());
                break;

            default:
                String entrada = event.getText();
                if (esNumero(entrada)) {
                    mostrarPorPantalla(entrada, false);
                }
        }
    }

    private boolean esNumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @javafx.fxml.FXML
    public void pulsarBoton(ActionEvent event) {
        String inicio = L_resultado.getText();
        if (inicio.equals("o")) {
            borrarPantalla();
        }
        if (flagResultado) {
            borrarPantalla();
        }
        String valor = ((Button) event.getSource()).getText();
        mostrarPorPantalla(valor, false);
    }

    private void borrarPantalla() {
        L_resultado.setText("");
    }

    @javafx.fxml.FXML
    public void igual(ActionEvent actionEvent) {
        progreso.setVisible(true);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String aux = L_resultado.getText();
            seleccion = cmSeleccion.getValue().toString();
            if (!aux.isEmpty()) {
                String origen = cmOrigen.getValue().toString();
                String destino = cmDestino.getValue().toString();

                String res = operaciones.conversion(seleccion, aux, origen, destino);
                Platform.runLater(() -> {
                    L_resultado.setText(res);
                    progreso.setVisible(false);
                });
            }
            progreso.setVisible(false);
        });
        executor.shutdown();
    }

    @javafx.fxml.FXML
    public void seleccionConversor(ActionEvent actionEvent) {
        this.seleccion = cmSeleccion.getValue().toString();
        if (!seleccion.isEmpty()) {
            switch (seleccion) {
                case "Moneda":
                    cmOrigen.setItems(Monedas);
                    cmOrigen.setValue("EUR");
                    cmDestino.setItems(Monedas);
                    cmDestino.setValue("USD");
                    break;
                case "Longitud":
                    cmOrigen.setItems(Longitud);
                    cmOrigen.setValue("m");
                    cmDestino.setItems(Longitud);
                    cmDestino.setValue("km");
                    break;
                case "Tiempo":
                    cmOrigen.setItems(Tiempo);
                    cmOrigen.setValue("segundos");
                    cmDestino.setItems(Tiempo);
                    cmDestino.setValue("horas");
                    break;
            }
        }
    }

    @javafx.fxml.FXML
    public void ponerDecimal(ActionEvent actionEvent) {
        if (!flagDecimal) {
            if (L_resultado.getText().isEmpty()) {
                mostrarPorPantalla("0.", false);
            } else {
                mostrarPorPantalla(".", false);
            }
            flagDecimal = true;
        }
    }

    @javafx.fxml.FXML
    public void limpiar(ActionEvent actionEvent) {
        String datos = L_resultado.getText();
        String nDatos = datos.substring(0, datos.length() - 1);

        if (!nDatos.contains(".")) {
            flagDecimal = false;
        }
        L_resultado.setText(nDatos);
    }

    private void mostrarPorPantalla(String valor, boolean flag) {
        L_resultado.setText(L_resultado.getText() + valor);
        flagResultado = flag;
    }

    @javafx.fxml.FXML
    public void pantallaCientifica(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/david/Vista/cientifica.fxml"));

            Parent root = loader.load();

            cientificaController controller = loader.getController();

            //Scene scene = new Scene(root);
            Scene scene = new Scene(root, 330, 370);
            Stage cientificaStage = new Stage();

            cientificaStage.setScene(scene);
            cientificaStage.show();
            cientificaStage.setResizable(false);
            String path = "/images/icon_cal_4.png";
            Image icon = new Image(getClass().getResourceAsStream(path));
            cientificaStage.getIcons().add(icon);
            cientificaStage.setTitle("Calculadora, DGV!");

            // Cierra la ventana actual
            Stage myStage = (Stage) L_resultado.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            System.out.println("Error al cargar la pantalla científica: " + ex.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void pantallaBasica(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/david/Vista/basica.fxml"));
            Parent root = loader.load();
            basicaController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage basicaStage = new Stage();

            basicaStage.setScene(scene);
            basicaStage.show();
            basicaStage.setResizable(false);
            String path = "/images/icon_cal_4.png";
            Image icon = new Image(getClass().getResourceAsStream(path));
            basicaStage.getIcons().add(icon);
            basicaStage.setTitle("Calculadora Cientifica, DGV!");

            // Cierra la ventana actual
            Stage myStage = (Stage) L_resultado.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            System.out.println("Error al cargar la pantalla basica: " + ex.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void pantallaInfo(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/david/Vista/info.fxml"));

            Parent root = loader.load();

            infoController controller = loader.getController();

            Scene scene = new Scene(root);
            Stage infoStage = new Stage();

            infoStage.setScene(scene);
            infoStage.show();
            infoStage.setResizable(false);
            String path = "/images/icon_cal_4.png";
            Image icon = new Image(getClass().getResourceAsStream(path));
            infoStage.getIcons().add(icon);
            infoStage.setTitle("Información, DGV!");

        } catch (IOException ex) {
            System.out.println("Error al cargar la pantalla de Informacion: " + ex.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void elegirMoneda(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/david/Vista/modalMoneda.fxml"));
            Pane root = loader.load();
            this.mc = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Ventana Modal");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            Monedas.clear();
            Monedas.addAll(modalMonedaController.getDatos());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}

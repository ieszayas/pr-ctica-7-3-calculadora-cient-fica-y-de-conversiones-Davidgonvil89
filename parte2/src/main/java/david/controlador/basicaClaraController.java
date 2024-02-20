package david.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class basicaClaraController implements Initializable {

    @FXML
    private Label L_resultado;
    @FXML
    private Label L_operacion;

    private String memoria_aux = "0";
    private String memoria_prin = "0";
    private String num1 = "";
    private String operacion;

    private boolean flagResultado = false;
    private boolean flagDecimal = false;
    private boolean flagOpInicial = true;
    private boolean flagOperacion = false;
    @FXML
    private MenuItem itemCientifica;
    private final boolean flagTema = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
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

    private void mostrarPorPantalla(String valor, boolean flag_e) {
        L_resultado.setText(L_resultado.getText() + valor);
        this.flagResultado = flag_e;
    }

    @FXML
    public void igual(ActionEvent actionEvent) {
        num1 = L_resultado.getText();
        if (num1.equals("")) {
            num1 = "0";
        }
        borrarPantalla();
        mostrarPorPantalla(calculo(num1, operacion), true);
        mostrarOperacion(num1, false);
    }

    @FXML
    public void operacion(ActionEvent event) {
        operar(((Button) event.getSource()).getText());
    }

    private void operar(String operacion_aux) {
        flagDecimal = false;
        if (flagResultado) {
            borrarPantalla();
        }

        num1 = L_resultado.getText();

        if (flagOpInicial) {
            memoria_aux = num1;
            borrarPantalla();
            flagOpInicial = false;
        } else {
            num1 = L_resultado.getText();
            borrarPantalla();
            mostrarPorPantalla(calculo(num1, operacion), true);
        }

        operacion = operacion_aux;
        mostrarOperacion(num1 + operacion_aux, false);
    }

    private void mostrarOperacion(String valor, boolean flag) {
        L_operacion.setText(L_operacion.getText() + valor);
        this.flagOperacion = flag;
    }

    public String calculo(String numero1, String operacion) {
        Double num1;
        Double numMemoria;
        String res;
        try {
            num1 = Double.valueOf(numero1);
            numMemoria = Double.valueOf(memoria_aux);
            Double resultado;

            switch (operacion) {
                case "+":
                    resultado = numMemoria + num1;
                    break;
                case "-":
                    resultado = numMemoria - num1;
                    break;
                case "x":
                    resultado = numMemoria * num1;
                    break;
                case "/":
                    resultado = numMemoria / num1;
                    break;
                default:
                    resultado = 0.0;
                    break;
            }
            res = String.valueOf(resultado);
            memoria_aux = res;
        } catch (NumberFormatException ex) {

        }
        return memoria_aux;
    }

    @FXML
    public void ponerDecimal() {
        if (!flagDecimal) {
            if (L_resultado.getText().isEmpty()) {
                mostrarPorPantalla("0.", false);
            } else {
                mostrarPorPantalla(".", false);
            }
            flagDecimal = true;
        }
    }

    @FXML
    public void cambiar_signo(ActionEvent actionEvent) {
        String aux = L_resultado.getText();
        if (flagResultado) {
            borrarPantalla();
        }

        if (aux.contains("-")) {
            L_resultado.setText(aux.substring(1));
        } else {
            L_resultado.setText("-" + L_resultado.getText());
        }
    }

    @FXML
    public void limpiarTodo(ActionEvent actionEvent) {
        borrarPantalla();
        memoria_aux = "0";
        L_operacion.setText("");
        memoria_prin = "0";
        flagDecimal = false;
        flagOpInicial = true;
    }

    private void borrarPantalla() {
        L_resultado.setText("");
        flagResultado = false;
        flagOperacion = false;
    }

    @FXML
    public void memoria(ActionEvent event) {
        String boton = ((Button) event.getSource()).getText();
        String numero = L_resultado.getText();

        switch (boton) {
            case "M+":
                memoria_prin = calculo(numero, "+");
                break;
            case "M-":
                memoria_prin = calculo(numero, "-");
                break;
        }
        mostrarPorPantalla(memoria_prin, true);
    }

    @FXML
    public void pantallaCientifica() {
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

    @FXML
    public void escribir(KeyEvent event) {
        if (flagResultado) {
            borrarPantalla();
        }
        switch (event.getCode()) {
            case BACK_SPACE:
                borrarPantalla();
                break;
            case DECIMAL:
                ponerDecimal();
                break;
            case ENTER:
                igual(new ActionEvent());
                break;
            case ADD:
                operar("+");
                break;
            case SUBTRACT:
                operar("-");
                break;
            case MULTIPLY:
                operar("*");
                break;
            case DIVIDE:
                operar("/");
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

    @FXML
    public void pantallaConversion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/david/Vista/conversor.fxml"));

            Parent root = loader.load();

            conversorController controller = loader.getController();

            Scene scene = new Scene(root);
            Stage conversorStage = new Stage();

            conversorStage.setScene(scene);
            conversorStage.show();
            conversorStage.setResizable(false);
            String path = "/images/icon_cal_4.png";
            Image icon = new Image(getClass().getResourceAsStream(path));
            conversorStage.getIcons().add(icon);
            conversorStage.setTitle("Conversor, DGV!");

            // Cierra la ventana actual
            Stage myStage = (Stage) L_resultado.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            System.out.println("Error al cargar la pantalla de Conversion: " + ex.getMessage());
        }

    }

    @FXML
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


    @FXML
    public void cambiarModo(ActionEvent event) {
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
            basicaStage.setTitle("Calculadora Basica, DGV!");

            // Cierra la ventana actual
            Stage myStage = (Stage) L_resultado.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            System.out.println("Error al cargar la pantalla basica: " + ex.getMessage());
        }
    }
}


package Modelo;

import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Operaciones {

    HashMap<String, Double> unidadesTiempo;
    private final HashMap<String, Double> unidadesLongitud;
    private Stage stagePrincipal;


    public Operaciones() {
        unidadesLongitud = new HashMap<>();
        unidadesLongitud.put("mm", 0.001);
        unidadesLongitud.put("cm", 0.01);
        unidadesLongitud.put("dm", 0.1);
        unidadesLongitud.put("dm", 0.1);
        unidadesLongitud.put("m", 1.0);
        unidadesLongitud.put("km", 1000.0);

        unidadesTiempo = new HashMap<>();
        unidadesTiempo.put("milisegundos", 1.0);
        unidadesTiempo.put("segundos", 1000.0);
        unidadesTiempo.put("minutos", 60000.0);
        unidadesTiempo.put("horas", 3600000.0);
        unidadesTiempo.put("dias", 86400000.0);
        unidadesTiempo.put("semanas", 604800000.0);
        unidadesTiempo.put("años", 31557600000.0);

    }

    public void setStagePrincipal(Stage stage) {
        this.stagePrincipal = stage;
    }

    public String conversion(String modo, String valor, String origen, String destino) {
        String resultado = "";

        switch (modo) {
            case "Moneda":
                resultado = calcularDivisa(valor, origen, destino);
                break;
            case "Longitud":
                resultado = conversion(valor, origen, destino, unidadesLongitud);
                break;
            case "Tiempo":
                resultado = conversion(valor, origen, destino, unidadesTiempo);
                break;
        }

        return resultado;
    }

    private String calcularDivisa(String valor, String origen, String destino) {

        String json = requestAPI(origen);
        Double valorDestino = divisa(json, destino);
        Double valorACalcular = Double.valueOf(valor);

        return String.valueOf(valorACalcular * valorDestino);
    }

    public Double divisa(String json, String destino) {
        JSONObject obj = new JSONObject(json);
        String result = obj.getJSONObject("conversion_rates").optString(destino);
        double solucion = Double.parseDouble(result);

        return solucion;
    }

    private String requestAPI(String origen) {
        String json = "";
        Scanner sc = null;
        String enlace = "https://v6.exchangerate-api.com/v6/893432d92dd618a412aff045/latest/";

        try {
            URL url = new URL(enlace + origen);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int code = conn.getResponseCode();

            //Si devuelve otro codigo es que ha dado error
            if (code != 200) {
                System.out.println("Request no exitosa");
            } else {
                sc = new Scanner(url.openStream());
                //Metemos en un String la request (en forma de Json)
                while (sc.hasNext()) {
                    json += sc.nextLine();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
        return json;
    }

    private String conversion(String valor, String origen, String destino, HashMap<String, Double> unidades) {
        Double valorEntrada = Double.valueOf(valor);
        Double factorOrigen = unidades.get(origen);
        Double factorDestino = unidades.get(destino);

        // Convertir la cantidad a milisegundos y luego a la unidad de destino
        return String.valueOf((valorEntrada * factorOrigen) / factorDestino);
    }
}

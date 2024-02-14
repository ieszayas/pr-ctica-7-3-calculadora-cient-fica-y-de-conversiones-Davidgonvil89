package david.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/david/Vista/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 265, 340);
        String path = "/images/icon_cal_4.png";
        Image icon = new Image(getClass().getResourceAsStream(path));
        stage.getIcons().add(icon);
        stage.setTitle("Calculadora, DGV!");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}
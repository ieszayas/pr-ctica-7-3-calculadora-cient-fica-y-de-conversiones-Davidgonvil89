package david.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;
    private static Image icon;

    @Override
    public void start(Stage stage_e) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/david/Vista/basica.fxml"));
        scene = new Scene(fxmlLoader.load(), 265, 370);
        String path = "/images/icon_cal_4.png";
        icon = new Image(getClass().getResourceAsStream(path));
        stage_e.getIcons().add(icon);
        stage_e.setTitle("Calculadora, DGV!");
        stage_e.setScene(scene);
        stage_e.show();
        stage_e.setResizable(false);
    }

}
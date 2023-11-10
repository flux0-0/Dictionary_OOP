package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class APP extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(APP.class.getResource("ConteinerControl.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        Image icon = new Image("img/icon.png");
        stage.setTitle("ENG-VIE Dictionary");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

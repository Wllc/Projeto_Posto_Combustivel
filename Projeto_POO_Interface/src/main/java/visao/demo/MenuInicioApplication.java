package visao.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuInicioApplication extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stagePrincipal) throws IOException {
        stage = stagePrincipal;
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("menuInicio.fxml"));
        Scene scene = new Scene(fxmlLoader, 400, 600);
        stage.setTitle("Posto de combustivel");
        stage.setScene(scene);
        stage.show();
    }
    public static Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
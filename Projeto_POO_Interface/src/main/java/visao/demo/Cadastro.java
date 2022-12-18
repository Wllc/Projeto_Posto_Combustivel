package visao.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Cadastro {
    private static Stage stage;
    public void start(Stage stagePrincipal) throws IOException {
        stage = stagePrincipal;
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("cadastro.fxml"));
        Scene scene = new Scene(fxmlLoader, 400, 600);
        stage.setTitle("Posto de combustivel");
        stage.setScene(scene);
        stage.show();
    }
    public static Stage getStage(){
        return stage;
    }
}

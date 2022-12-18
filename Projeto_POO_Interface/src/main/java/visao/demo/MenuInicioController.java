package visao.demo;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuInicioController {
    @FXML
    protected void navCadastro() throws IOException {
        Cadastro cadastro = new Cadastro();
        cadastro.start(new Stage());
        MenuInicioApplication.getStage().close();
    }
    @FXML
    protected void navAcesso() throws IOException {
        MenuAcesso menuAcesso = new MenuAcesso();
        menuAcesso.start(new Stage());
        MenuInicioApplication.getStage().close();
    }

}
package visao.demo;

import dominio.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import persistencia.ClienteDAO;

import java.io.IOException;

public class CadastroController {
    @FXML
    private TextField txtCpf, txtNome, txtIdade;
    @FXML
    Label lblCadastro;
    Cliente cliente = null;
    @FXML
    protected void voltarMenu() throws IOException {
        MenuInicioApplication menu = new MenuInicioApplication();
        menu.start(new Stage());
        Cadastro.getStage().close();
    }
    @FXML
    protected void cadastrarCliente() {
        cliente = new Cliente(Long.parseLong(txtCpf.getText()), txtNome.getText(), Integer.parseInt(txtIdade.getText()), 0);
        if(ClienteDAO.verificarCliente(cliente.getCpf()) == null || ClienteDAO.verificarCliente(cliente.getCpf()).getCpf() != cliente.getCpf()){
            ClienteDAO.inserirCliente(cliente);
            lblCadastro.setText("Cadastrado com sucesso");
            lblCadastro.setTextFill(Paint.valueOf("GREEN"));
        }
        else{
            lblCadastro.setText("CPF ja cadastrado");
            lblCadastro.setTextFill(Paint.valueOf("RED"));
        }
    }

}

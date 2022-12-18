package visao.demo;

import dominio.Cliente;
import dominio.Combustivel;
import dominio.FormaPagamento;
import dominio.Pedido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistencia.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class MenuAcessoController implements Initializable {
    @FXML
    private AnchorPane apnLogin, apnOpcoes, apnVerPrecos, apnVerPedidos, apnFazerPedido, apnFinalizarPedidos;
    @FXML
    private Label lblAcessoResultado, lblCpf, lblMensagemPedido, lblPromocoes, lblNovoValor, lblLitros, lblMensagemRemover, lblNumeroCartao, lblCvv, lblMensagemFinal;
    @FXML
    private TextField txtCpfAcesso, txtIdPedido, txtValorPedido, txtNumeroCartao, txtCvv;
    @FXML
    private TextArea txtAreaFinal;
    @FXML
    private ChoiceBox<String> cbFormaPagamento, cbFormaCartao;

    //combustivel
    @FXML
    private TableView<Combustivel> tbCombustivel;
    @FXML
    private TableColumn<Combustivel, Integer> tbIdCombustivel;
    @FXML
    private TableColumn<Combustivel, String> tbNomeCombustivel;
    @FXML
    private TableColumn<Combustivel, Float> tbPrecoCombustivel;
    @FXML
    private TableView<Combustivel> tbEscolhaC;
    @FXML
    private TableColumn<Combustivel, Integer> tbIdEscolhaC;
    @FXML
    private TableColumn<Combustivel, String> tbNomeEscolhaC;
    @FXML
    private TableColumn<Combustivel, Float> tbPrecoEscolhaC;
    private List<Combustivel> listCombustivel = new ArrayList();
    private ObservableList<Combustivel> obsCombustivel;

    //pedido
    @FXML TableView<Pedido> tbPedidos;
    @FXML
    private TableColumn<Pedido, Integer> tbIdPedido;
    @FXML
    private TableColumn<Pedido, Long> tbCpfPedido;
    @FXML
    private TableColumn<Pedido, Float> tbValorPedido;
    @FXML
    private TableColumn<Pedido, Float> tbLitrosPedido;
    @FXML
    private TableColumn<Pedido, String> tbPromoPedido;
    private List<Pedido> listPedidos = new ArrayList();
    private ObservableList<Pedido> obsPedido;
    int op;
    private Cliente cliente = null;
    private Combustivel combustivel = null;
    private Pedido pedido = null;
    private FormaPagamento formaPagamento = null;
    private Date dataHoraAtual = null;
    private String data, hora;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apnLogin.setVisible(true);
        apnOpcoes.setVisible(false);
        apnVerPrecos.setVisible(false);
        apnVerPedidos.setVisible(false);
        apnFazerPedido.setVisible(false);
        apnFinalizarPedidos.setVisible(false);
        cbFormaPagamento.getItems().add("Dinheiro");
        cbFormaPagamento.getItems().add("Pix");
        cbFormaPagamento.getItems().add("Cartão");
        cbFormaCartao.getItems().add("Debito");
        cbFormaCartao.getItems().add("Credito");
    }
    @FXML
    protected void voltarMenuInicio() throws IOException {
        MenuInicioApplication menu = new MenuInicioApplication();
        menu.start(new Stage());
        MenuAcesso.getStage().close();
    }
    @FXML
    protected void acessarSistema() {
        try{
            cliente = ClienteDAO.verificarCliente(Long.parseLong(txtCpfAcesso.getText()));
            if(cliente != null && cliente.getCpf() == ClienteDAO.verificarCliente(cliente.getCpf()).getCpf()){
                apnLogin.setVisible(false);
                apnOpcoes.setVisible(true);
                lblAcessoResultado.setText("");
            }
            else{
                txtCpfAcesso.setText("");
                lblAcessoResultado.setText("Cliente não encontrado");
            }
        }catch (Exception e){
            lblAcessoResultado.setText("Campo vazio");
        }

    }
    @FXML
    protected  void atualizarTabelas(){
        listCombustivel.clear();
        tbIdCombustivel.setCellValueFactory(new PropertyValueFactory<>("idCombustivel"));
        tbNomeCombustivel.setCellValueFactory(new PropertyValueFactory<>("nomeCombustivel"));
        tbPrecoCombustivel.setCellValueFactory(new PropertyValueFactory<>("valorCombustivel"));
        tbIdEscolhaC.setCellValueFactory(new PropertyValueFactory<>("idCombustivel"));
        tbNomeEscolhaC.setCellValueFactory(new PropertyValueFactory<>("nomeCombustivel"));
        tbPrecoEscolhaC.setCellValueFactory(new PropertyValueFactory<>("valorCombustivel"));

        Combustivel c;
        for (int i = 1; i <= 4; i++) {
            c = CombustivelDAO.buscarCombustivel(i);
            listCombustivel.add(c);
        }
        obsCombustivel = FXCollections.observableArrayList(listCombustivel);
        tbCombustivel.setItems(obsCombustivel);
        tbEscolhaC.setItems(obsCombustivel);
    }
    @FXML
    protected void verPrecos(){
        apnOpcoes.setVisible(false);
        apnVerPrecos.setVisible(true);
        atualizarTabelas();
    }
    @FXML
    protected void fazerPedido(){
        apnOpcoes.setVisible(false);
        apnFazerPedido.setVisible(true);
        atualizarTabelas();
    }
    @FXML
    protected void confirmarPedido(){
        try {
            if(PedidoDAO.verificarIdPedido(Integer.parseInt(txtIdPedido.getText())) != Integer.parseInt(txtIdPedido.getText()) && Integer.parseInt(txtIdPedido.getText()) >= 0) {
                if (!tbEscolhaC.getSelectionModel().isEmpty() && txtIdPedido.getText().length() >= 0 && txtValorPedido.getText().length() > 0) {
                    combustivel = tbEscolhaC.getSelectionModel().getSelectedItem();
                    pedido = new Pedido(combustivel, Integer.parseInt(txtIdPedido.getText()), cliente.getCpf(), Float.parseFloat(txtValorPedido.getText()), false);
                    pedido.calcularLitros();
                    if (pedido.getValorPedido() > 200) {
                        pedido.adicionarPromocao(PromocaoDAO.selecionarPromocao(1));
                    }
                    if (cliente.getIdade() > 60) {
                        pedido.adicionarPromocao(PromocaoDAO.selecionarPromocao(2));
                    }
                    if (cliente.getPedidosRealizados() > 10) {
                        pedido.adicionarPromocao(PromocaoDAO.selecionarPromocao(3));
                    }
                    if (cliente.getPedidosRealizados() == 0) {
                        pedido.adicionarPromocao(PromocaoDAO.selecionarPromocao(4));
                    }
                    pedido.calcularValorPedidoDescontado();
                    cliente.incrementaPedidosRealizados();
                    PedidoDAO.inserirPedido(pedido, combustivel, cliente, Integer.parseInt(txtIdPedido.getText()));
                    txtIdPedido.setText("");
                    txtValorPedido.setText("");
                    lblPromocoes.setText("PROMOÇÕES ATIVAS: " + pedido.getPromocoes());
                    lblNovoValor.setText("NOVO VALOR: " + pedido.getValorPedido());
                    lblLitros.setText("LITROS: " + pedido.getLitros());
                    lblMensagemPedido.setStyle("-fx-text-fill: lime;");
                    lblMensagemPedido.setText("Pedido realizado, finalize sua compra");
                } else {
                    lblMensagemPedido.setStyle("-fx-text-fill: red;");
                    lblMensagemPedido.setText("Algum campo não preenchido");
                }
            }else{
                txtIdPedido.setText("");
                lblMensagemPedido.setStyle("-fx-text-fill: red;");
                lblMensagemPedido.setText("ID ja utilizado ou digite ID maior que zero");
            }
        }catch (Exception e){
            txtIdPedido.setText("");
            txtValorPedido.setText("");
            lblPromocoes.setText("PROMOÇÕES ATIVAS: ");
            lblNovoValor.setText("NOVO VALOR: ");
            lblLitros.setText("LITROS: ");
            lblMensagemPedido.setStyle("-fx-text-fill: red;");
            lblMensagemPedido.setText("Preencha os campos corretamente");
        }
    }
    protected  void selecionarCombustivel(Combustivel combustivel){
        this.combustivel = combustivel;
    }
    @FXML
    protected void verPedidos(){
        listPedidos.clear();
        apnOpcoes.setVisible(false);
        apnVerPedidos.setVisible(true);
        tbIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        tbCpfPedido.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tbValorPedido.setCellValueFactory(new PropertyValueFactory<>("valorPedido"));
        tbLitrosPedido.setCellValueFactory(new PropertyValueFactory<>("litros"));
        tbPromoPedido.setCellValueFactory(new PropertyValueFactory<>("strPromocoes"));
        Pedido p;

        for (int i = 0; i < PedidoDAO.verificarPedidos(cliente, combustivel).size(); i++) {
            listPedidos.add(PedidoDAO.verificarPedidos(cliente, combustivel).get(i));
        }
        obsPedido = FXCollections.observableArrayList(listPedidos);
        tbPedidos.setItems(obsPedido);
    }
    @FXML
    protected void removerPedido(){
        if(!tbPedidos.getSelectionModel().isEmpty()){
            Pedido pAux = tbPedidos.getSelectionModel().getSelectedItem();
            PedidoDAO.removerPedido(pAux.getIdPedido());
            tbPedidos.getItems().remove(pAux);
            cliente.decrementaPedidosRealizados();
            lblMensagemRemover.setStyle("-fx-text-fill: red;");
            lblMensagemRemover.setText("Pedido removido");
        }
        else{
            lblMensagemRemover.setStyle("-fx-text-fill: red;");
            lblMensagemRemover.setText("Nenhum pedido selecinado");
        }
    }
    @FXML
    protected void irFinalizarPedidos(){
        apnOpcoes.setVisible(false);
        apnFinalizarPedidos.setVisible(true);
        txtNumeroCartao.setVisible(true);
        txtCvv.setVisible(true);
        lblNumeroCartao.setVisible(true);
        lblCvv.setVisible(true);
        cbFormaCartao.setVisible(true);
    }

    @FXML
    protected void finalizarPedido(){
        if(PedidoDAO.verificarPedidos(cliente, combustivel).size() > 0){
            System.out.println(String.valueOf(cbFormaPagamento.getItems()));
            try {
                if (String.valueOf(cbFormaPagamento.getSelectionModel().getSelectedItem()).equals("Dinheiro")) {
                    formaPagamento = new FormaPagamento(1);
                } else if (String.valueOf(cbFormaPagamento.getSelectionModel().getSelectedItem()).equals("Pix")) {
                    formaPagamento = new FormaPagamento(2);
                } else if (!cbFormaCartao.getSelectionModel().isEmpty()){
                    formaPagamento = new FormaPagamento(Long.parseLong(txtNumeroCartao.getText()), Integer.parseInt(txtCvv.getText()), String.valueOf(cbFormaCartao.getSelectionModel().getSelectedItem()));
                }
                System.out.println("\nEmitindo nota fiscal...");
                dataHoraAtual = new Date();
                data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
                hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
                NotaFiscalDAO.inserirNota(cliente, formaPagamento, PedidoDAO.valorPedidosNaoPagos(cliente), PedidoDAO.quantidadePedidos(cliente), data, hora);
                txtAreaFinal.setText(NotaFiscalDAO.emitirNota(cliente));
                PedidoDAO.atualizarPedidosNaoPagos(cliente);
                PedidoDAO.removerPedidosPagos();
                ClienteDAO.atualizarPedidosRealizados(cliente);
                lblMensagemFinal.setStyle("-fx-text-fill: lime;");
                lblMensagemFinal.setText("Nota fiscal emitida");
                txtNumeroCartao.setText("");
                txtCvv.setText("");
            }
            catch (Exception e){
                txtNumeroCartao.setText("");
                txtCvv.setText("");
                lblMensagemFinal.setStyle("-fx-text-fill: red;");
                lblMensagemFinal.setText("Preencha os campos corretamente");
            }
        }
        else{
            txtNumeroCartao.setText("");
            txtCvv.setText("");
            lblMensagemFinal.setStyle("-fx-text-fill: red;");
            lblMensagemFinal.setText("Nenhum pedido realizado");
        }
    }
    @FXML
    protected  void voltarOpcoes(){
        apnOpcoes.setVisible(true);
        apnVerPedidos.setVisible(false);
        apnVerPrecos.setVisible(false);
        apnFazerPedido.setVisible(false);
        apnFinalizarPedidos.setVisible(false);
        txtIdPedido.setText("");
        txtValorPedido.setText("");
        lblPromocoes.setText("PROMOÇÕES ATIVAS: ");
        lblNovoValor.setText("NOVO VALOR: ");
        lblLitros.setText("LITROS: ");
        lblMensagemPedido.setText("");
        lblMensagemRemover.setText("");
        txtNumeroCartao.setText("");
        txtCvv.setText("");
        lblMensagemFinal.setText("");
    }
    @FXML
    protected void voltarMenuAcesso() {
        apnLogin.setVisible(true);
        apnOpcoes.setVisible(false);
    }
    @FXML
    protected void voltarLogin() {
        apnLogin.setVisible(true);
        apnOpcoes.setVisible(false);
    }

}

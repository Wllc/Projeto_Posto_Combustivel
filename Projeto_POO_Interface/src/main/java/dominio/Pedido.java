package dominio;

import java.util.ArrayList;

public class Pedido{
    private int idPedido;
    private long cpf;

    private float valorPedido;
    private float litros;
    private String strPromocoes;
    private boolean pago;
    private ArrayList<Promocao> promocoes = new ArrayList<>();
    private Combustivel c;

    public Pedido(Combustivel c, int idPedido, long cpf, float valorPedido, float litros, String strPromocoes, boolean pago){
        this.idPedido = idPedido;
        this.cpf = cpf;
        this.valorPedido = valorPedido;
        this.litros = litros;
        this.c = c;
        this.strPromocoes = strPromocoes;
        this.pago = pago;
    }
    public Pedido(Combustivel c, int idPedido, long cpf, float valorPedido, boolean pago){
        this.c = c;
        this.idPedido = idPedido;
        this.cpf = cpf;
        this.valorPedido = valorPedido;
        this.pago = pago;
    }

    public void adicionarPromocao(Promocao promocao) {
        promocoes.add(promocao);
    }

    public String getPromocoes() {
        String tiposPromocoes = "[";
        if(promocoes.size() > 0){
            tiposPromocoes += promocoes.get(0).getTipo_promocao() + " - " + promocoes.get(0).getPerc_promocao() + "%";
            for (int i = 1; i < promocoes.size(); i++) {
                tiposPromocoes += "; " + promocoes.get(i).getTipo_promocao() + " - " + promocoes.get(i).getPerc_promocao() + "%";
            }
        }
        else{
            tiposPromocoes += "Nenhuma promocao";
        }
        return tiposPromocoes + "]";
    }

    public void calcularValorPedidoDescontado(){
        float percentualDescontado = 0;
        if(promocoes.size() > 0) {
            for (int i = 0; i < promocoes.size(); i++) {
                percentualDescontado += promocoes.get(i).getPerc_promocao();
            }
            percentualDescontado = percentualDescontado/100;
            valorPedido = valorPedido - (valorPedido * percentualDescontado);
            if(valorPedido < 0){
                valorPedido = 0;
            }
        }
    }
    public void calcularLitros(){
        litros = valorPedido /c.getValorCombustivel();
    }

    public float getLitros() {
        return litros;
    }

    public void setValorPedido(float valorPedido) {
        this.valorPedido = valorPedido;
    }

    public float getValorPedido() {
        return valorPedido;
    }

    public int getId() {
        return idPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public long getCpf() {
        return cpf;
    }

    public String getStrPromocoes() {
        return strPromocoes;
    }

    public boolean isPago() {
        return pago;
    }
}


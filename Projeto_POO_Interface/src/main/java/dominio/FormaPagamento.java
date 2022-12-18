package dominio;

public class FormaPagamento {
    private String formaPagamento;
    private long numeroCartao;
    private int cvv;
    private String formaPagamentoCartao;


    public FormaPagamento(int op){
        if(op == 1){
            formaPagamento = "Dinheiro";
        }else{
            formaPagamento = "Pix";
        }
    }
    public FormaPagamento(long numeroCartao, int cvv, String formaPagamentoCartao){
        formaPagamento = "Cartao";
        this.numeroCartao = numeroCartao;
        this.cvv = cvv;
        this.formaPagamentoCartao = formaPagamentoCartao;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public String getFormaPagamentoCartao() {
        return formaPagamentoCartao;
    }

    public void setFormaPagamentoCartao(int op) {
        if(op == 1){
            formaPagamento = "Debito";
        } else if (op == 2) {
            formaPagamento = "Credito";
        }
    }

}

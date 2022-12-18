package dominio;

public class Cliente {
    private String nome;
    private long cpf;
    private int idade;
    private int pedidosRealizados;

    public Cliente(long cpf, String nome, int idade, int pedidos_realizados){
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
        this.pedidosRealizados = pedidos_realizados;
    }

    public int getPedidosRealizados() {
        return pedidosRealizados;
    }

    public void incrementaPedidosRealizados() {
        pedidosRealizados++;
    }
    public void decrementaPedidosRealizados() {
        pedidosRealizados--;
    }

    public long getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
}

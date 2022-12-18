package dominio;

public class Promocao {
    private int id_promocao;
    private String tipo_promocao;
    private int perc_promocao;

    public Promocao(int id_promocao, String tipo_promocao, int perc_promocao){
        this.id_promocao = id_promocao;
        this.tipo_promocao = tipo_promocao;
        this.perc_promocao = perc_promocao;
    }

    public int getPerc_promocao() {
        return perc_promocao;
    }

    public String getTipo_promocao() {
        return tipo_promocao;
    }

    @Override
    public String toString() {
        return "Promocao Aplicada{" +
                "ID=" + id_promocao +
                ", Promocao='" + tipo_promocao + '\'' +
                ", Percentual=" + perc_promocao +
                '}';
    }
}

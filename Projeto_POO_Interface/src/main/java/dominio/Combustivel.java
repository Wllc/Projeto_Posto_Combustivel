package dominio;

public class Combustivel {
    private int idCombustivel;
    private String nomeCombustivel;
    private float valorCombustivel;

    public Combustivel(int id_Combustivel, String nomeCombustivel, float valor_Combustivel){
        this.idCombustivel = id_Combustivel;
        this.nomeCombustivel = nomeCombustivel;
        this.valorCombustivel = valor_Combustivel;
    }

    public String getNomeCombustivel() {
        return nomeCombustivel;
    }

    public float getValorCombustivel() {
        return valorCombustivel;
    }

    public int getIdCombustivel() {
        return idCombustivel;
    }

    public void setNomeCombustivel(String nomeCombustivel) {
        this.nomeCombustivel = nomeCombustivel;
    }
}

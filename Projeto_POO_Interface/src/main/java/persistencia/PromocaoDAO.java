package persistencia;

import dominio.Promocao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PromocaoDAO {

    public static Promocao selecionarPromocao(int id){
        Connection con = null;
        ResultSet resultSet = null;
        Promocao promocao = null;
        try{
            con = Conexao.getConexao();
            resultSet = con.createStatement().executeQuery("select * from promocoes");
            while(resultSet.next()){
                if(resultSet.getInt("id_promocao") == id){
                    promocao = new Promocao(resultSet.getInt("id_promocao"), resultSet.getString("tipo_promocao"), resultSet.getInt("perc_promocao"));
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
        return promocao;
    }
}

package persistencia;

import dominio.Combustivel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CombustivelDAO {
    //insert into combustivel (id_combustivel, nome_combustivel, valor_combustivel) values(?,?,?)
    public static Combustivel buscarCombustivel(int id){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Combustivel c = null;
        try{
            con = Conexao.getConexao();
            preparedStatement = con.prepareStatement("select * from combustivel where id_combustivel = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if(id == resultSet.getInt("id_combustivel")){
                    c = new Combustivel(resultSet.getInt("id_combustivel"),resultSet.getString("nome_combustivel"), resultSet.getFloat("valor_combustivel"));
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro" + e);
        }
        return c;
    }



}

package persistencia;

import dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    public static void inserirCliente(Cliente cliente) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = Conexao.getConexao();
            preparedStatement = con.prepareStatement("insert into clientes (cliente_cpf, cliente_nome, cliente_idade, pedidos_realizados) values(?,?,?,?)");
            preparedStatement.setLong(1, cliente.getCpf());
            preparedStatement.setString(2, cliente.getNome());
            preparedStatement.setInt(3, cliente.getIdade());
            preparedStatement.setInt(4, cliente.getPedidosRealizados());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("\nCadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("\nErro: " + e);
        }
    }
    //busca o cpf do cliente no bd, cria e retorna o objeto cliente com os dados do banco de dados
    public static Cliente verificarCliente(long cpf){
        Connection con = null;
        ResultSet resultSet = null;
        Cliente cliente = null;
        try{
            con = Conexao.getConexao();
            resultSet = con.createStatement().executeQuery("select * from clientes where cliente_cpf = " + cpf);
            while(resultSet.next()){
                cliente = new Cliente(resultSet.getLong("cliente_cpf"), resultSet.getString("cliente_nome"), resultSet.getInt("cliente_idade"), resultSet.getInt("pedidos_realizados"));
            }
        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
        return cliente;
    }

    public static void atualizarPedidosRealizados(Cliente cliente){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try{
            con = Conexao.getConexao();
            preparedStatement= con.prepareStatement("update clientes set pedidos_realizados = ? where cliente_cpf = ?");
            preparedStatement.setLong(1,cliente.getPedidosRealizados());
            preparedStatement.setLong(2,cliente.getCpf());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
    }

}

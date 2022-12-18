package persistencia;

import dominio.Cliente;
import dominio.Combustivel;
import dominio.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PedidoDAO {
    public static void inserirPedido(Pedido pedido, Combustivel combustivel, Cliente cliente, int id){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try{
            con = Conexao.getConexao();
            preparedStatement = con.prepareStatement("insert into pedido ( id_pedido, cpf_cliente, valor_pedido, litros, nome_combustivel, promocoes, pago) values(?,?,?,?,?,?,?)");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, cliente.getCpf());
            preparedStatement.setFloat(3, pedido.getValorPedido());
            preparedStatement.setFloat(4, pedido.getLitros());
            preparedStatement.setString(5, combustivel.getNomeCombustivel());
            preparedStatement.setString(6, pedido.getPromocoes());
            preparedStatement.setBoolean(7, false);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("\nInserido com sucesso!");
        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
    }
    public static int verificarIdPedido(int id){
        Connection con = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            con = Conexao.getConexao();
            preparedStatement = con.prepareStatement("select * from pedido where id_pedido = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if(resultSet.getInt("id_pedido") == id){
                    return resultSet.getInt("id_pedido");
                }
            }
            con.close();
        }catch(SQLException e){
            System.out.println("\nErro: " + e);
        }
        return -1;
    }
    public static void removerPedido(int id){
        Connection con = null;
        PreparedStatement preparedStatement =  null;
        try {
            con = Conexao.getConexao();
            preparedStatement = con.prepareStatement("delete from pedido where id_pedido = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("\nRemovido com sucesso!");
        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
    }
    public static ArrayList<Pedido> verificarPedidos(Cliente cliente, Combustivel combustivel){
        Connection con = null;
        ResultSet resultSet = null;
        Pedido pedido = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        try{
            con = Conexao.getConexao();
            resultSet = con.createStatement().executeQuery("select * from pedido where cpf_cliente = " + cliente.getCpf());
            while (resultSet.next()){
                pedidos.add(new Pedido(combustivel, resultSet.getInt("id_pedido"), resultSet.getLong("cpf_cliente"), resultSet.getFloat("valor_pedido"), resultSet.getFloat("litros"), resultSet.getString("promocoes"), resultSet.getBoolean("pago")));
            }
            con.close();
        }catch(SQLException e){
            System.out.println("\nErro: " + e);
        }
        return pedidos;
    }

    public static float valorPedidosNaoPagos(Cliente cliente){
        Connection con = null;
        ResultSet resultSet = null;
        float valorTotal = 0;
        try{
            con = Conexao.getConexao();
            resultSet = con.createStatement().executeQuery("select * from pedido where pago = false and cpf_cliente = " + cliente.getCpf());
            while (resultSet.next()){
                valorTotal += resultSet.getFloat("valor_pedido");
            }
            resultSet.close();
        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
        return valorTotal;
    }
    public static void atualizarPedidosNaoPagos(Cliente cliente){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try{
            con = Conexao.getConexao();
            preparedStatement= con.prepareStatement("update pedido set pago = true where cpf_cliente = ?");
            preparedStatement.setLong(1,cliente.getCpf());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("\nAtualizado com sucesso!");
        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
    }
    public static void removerPedidosPagos(){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try{
            con = Conexao.getConexao();
            preparedStatement = con.prepareStatement("delete from pedido where pago = true");
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (SQLException e){
            System.out.println("\nErro: " + e);
        }
    }
    public static int quantidadePedidos(Cliente cliente){
        Connection con = null;
        ResultSet resultSet = null;
        int qtd = 0;
        try{
            con = Conexao.getConexao();
            resultSet = con.createStatement().executeQuery("select * from pedido where cpf_cliente = " + cliente.getCpf());
            while (resultSet.next()){
                qtd++;
            }
            con.close();
        }catch(SQLException e){
            System.out.println("\nErro: " + e);
        }
        return qtd;
    }
}

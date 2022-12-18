package persistencia;

import dominio.Cliente;
import dominio.FormaPagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaFiscalDAO {
    public static void inserirNota(Cliente cliente, FormaPagamento formaPagamento, float valorNaoPago, int qtd, String data, String hora){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try{
            con = Conexao.getConexao();
            preparedStatement = con.prepareStatement("insert into nota_fiscal (id_nota, cpf_cliente, nome_cliente, forma_pagamento, valor_total, quantidade, data, hora) values (default,?,?,?,?,?,?,?)");
            preparedStatement.setLong(1, cliente.getCpf());
            preparedStatement.setString(2, cliente.getNome());
            preparedStatement.setString(3,formaPagamento.getFormaPagamento() + " (" +formaPagamento.getFormaPagamentoCartao()+")");
            preparedStatement.setFloat(4, valorNaoPago);
            preparedStatement.setInt(5, qtd);
            preparedStatement.setString(6, data);
            preparedStatement.setString(7, hora);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Erro: " + e);
        }
    }

    public static String emitirNota(Cliente cliente){
        Connection con = null;
        ResultSet resultSet = null;
        String nota = "";
        try{
            con = Conexao.getConexao();
            resultSet = con.createStatement().executeQuery("select * from nota_fiscal where cpf_cliente = " + cliente.getCpf());
            while (resultSet.next()){
                nota = "ID: " + resultSet.getInt( "id_nota") + " - "
                        + "CPF: " + resultSet.getLong("cpf_cliente") + " - "
                        + "Nome: " + resultSet.getString("nome_cliente")+ " - "
                        + "Forma de pagamento: " + resultSet.getString("forma_pagamento")+ " - "
                        + "Valor total: " + resultSet.getFloat("valor_total")+ " - "
                        + "Qtd pedidos: " + resultSet.getFloat("quantidade")+ " - "
                        + "Data: " + resultSet.getString("data") + " - "
                        + "Hora: " + resultSet.getString("hora");
            }
           resultSet.close();
        }catch(SQLException e){
            System.out.println("\nErro: " + e);
        }
        return nota;
    }
}

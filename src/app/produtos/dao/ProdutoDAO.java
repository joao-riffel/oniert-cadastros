package app.produtos.dao;

import app.produtos.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/db_produtos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";


    //metodo para conexão
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    // Criar Produto
    public void inserirProduto(Produto produto) throws SQLException {

        String sql = "INSERT INTO produtos (nome, preco, quantidade ) VALUES (?,?,?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());

            stmt.executeUpdate();

        }

    }


    public List<Produto> listarProdutos() throws SQLException {

        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, preco, quantidade FROM produtos ORDER BY id";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){

            while (rs.next()){

                int id = rs.getInt ("id");
                String nome = rs.getString ("nome");
                double preco = rs.getDouble ("preco");
                int quantidade = rs.getInt ("quantidade");
                produtos.add(new Produto (id, nome, preco, quantidade));

            }
        }
        return produtos;
    }


    public void atualizarProduto(Produto produto) throws SQLException {

        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString (1, produto.getNome());
            stmt.setDouble (2, produto.getPreco());
            stmt.setInt (3, produto.getQuantidade());
            stmt.setInt (4, produto.getId());

            stmt.executeUpdate();
            System.out.println ("Produto ID: " + produto.getId() + " atualizado com sucesso!");;

        }

    }

    public void deletarProduto(int id) throws SQLException {

        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt (1, id);
            stmt.executeUpdate();
            System.out.println ("Produto ID " + id + " deletado com sucesso!");

        }

    }

    public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getInt("quantidade")
                    );
                }
            }
        }
        return null;
    }



}

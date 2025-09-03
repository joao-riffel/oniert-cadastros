package app.produtos.dao;

import app.produtos.model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FuncionarioDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/db_produtos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Funcionario validarLogin(String usuario, String senha) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // se encontrou o usuário, retorna objeto Funcionario
                    String nome = rs.getString("nome");
                    return new Funcionario(nome, usuario, senha);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // se não encontrou ou deu erro
    }

    // (Opcional) Inserir novo funcionário
    public void inserirFuncionario(Funcionario f) {
        String sql = "INSERT INTO usuarios (nome, usuario, senha) VALUES (?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getUsuario());
            stmt.setString(3, f.getSenha());

            stmt.executeUpdate();
            System.out.println("Funcionário cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Funcionario buscarPorUsuario(String usuario, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getString("nome"),
                            rs.getString("usuario"),
                            rs.getString("senha")
                    );
                }
            }
        }
        return null; // não encontrado
    }




}
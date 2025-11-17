package service;

import model.*;
import util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    // CREATE
    public void salvarNovoUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios(nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            pstmt.setString(1, u.getNome());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getSenha());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    u.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar usuario: " + e.getMessage());
        }
    }

    //READ
    public Usuario informacoesUsuario(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                return u;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }

        return null;
    }

    public List<Usuario> listarUsuarios() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                usuarios.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }

        return usuarios;
    }


    public Usuario login(Usuario u) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, u.getEmail());
            pstmt.setString(2, u.getSenha());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario u2 = new Usuario();
                u2.setId(rs.getInt("id"));
                u2.setNome(rs.getString("nome"));
                u2.setEmail(rs.getString("email"));

                return u2;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // UPDATE
    public void atualizarInformacoesUsuario(Usuario u) {
        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, u.getNome());
            pstmt.setString(2, u.getEmail());
            pstmt.setInt(3, u.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    // DELETE
    public void deletarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}

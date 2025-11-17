package service;

import model.Categoria;
import model.Orcamento;
import model.Transacao;
import util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoService {

    public void salvarNovoOrcamento(Orcamento o) {
        String sql = "INSERT INTO orcamentos(categoria, valorLimite, idUsuario) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, o.getCategoria().name());

            pstmt.setDouble(2, o.getValorLimite());

            pstmt.setInt(3, o.getIdUsuario());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    o.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar orçamento: " + e.getMessage());
        }
    }


    public List<Orcamento> listarOrcamentos(int idUsuario) {
        List<Orcamento> lista = new ArrayList<>();

        String sql = "SELECT * FROM orcamentos WHERE idUsuario = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Categoria categoria = Categoria.valueOf(rs.getString("categoria"));
                double limite = rs.getDouble("valorLimite");

                lista.add(new Orcamento(id, categoria, limite));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar orçamentos: " + e.getMessage());
        }

        return lista;
    }
}

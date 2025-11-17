package service;

import model.Categoria;
import model.Despesa;
import model.Receita;
import model.Transacao;
import util.ConexaoBanco;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoService {

    // CREATE
    public void salvarNovaTransacao(Transacao t) {
        String sql = "INSERT INTO transacoes(tipo, valor, descricao, data, categoria, idUsuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String tipo = (t instanceof Despesa) ? "DESPESA" : "RECEITA";
            Categoria categoria = (t instanceof Despesa)
                    ? ((Despesa) t).getCategoria()
                    : ((Receita) t).getCategoria();

            LocalDate data = (t.getData() == null) ? LocalDate.now() : t.getData();

            pstmt.setString(1, tipo);
            pstmt.setDouble(2, t.getValor());
            pstmt.setString(3, t.getDescricao());
            pstmt.setDate(4, Date.valueOf(data));
            pstmt.setString(5, categoria.name());
            pstmt.setInt(6, t.getIdUsuario());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    t.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar transação: " + e.getMessage());
        }
    }

    // READ 
    public List<Transacao> listarTodasTransacoesUsuario(int idUsuario) {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT id, tipo, valor, descricao, data, categoria FROM transacoes WHERE idUsuario = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    double valor = rs.getDouble("valor");
                    String descricao = rs.getString("descricao");

                    LocalDate data = rs.getDate("data").toLocalDate();
                    Categoria categoria = Categoria.valueOf(rs.getString("categoria"));

                    Transacao t;
                    if (tipo.equalsIgnoreCase("RECEITA")) {
                        t = new Receita(valor, descricao, data, categoria);
                    } else {
                        t = new Despesa(valor, descricao, data, categoria);
                    }

                    transacoes.add(t);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar transações: " + e.getMessage());
        }

        return transacoes;
    }

    // DELETE 
    public void deletarTransacao(int id) {
        String sql = "DELETE FROM transacoes WHERE id = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar transação: " + e.getMessage());
        }
    }

    // RELATÓRIO 
    public double calcularSaldoAtual(int idUsuario) {
        double saldo = 0;

        for (Transacao t : listarTodasTransacoesUsuario(idUsuario)) {
            if (t instanceof Receita) saldo += t.getValor();
            else saldo -= t.getValor();
        }

        return saldo;
    }
}

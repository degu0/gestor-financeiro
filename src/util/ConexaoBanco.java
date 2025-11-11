package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL = "jdbc:sqlite:financas.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do SQLite não encontrado.", e);
        }
        return DriverManager.getConnection(URL);
    }

    public static void criarTabelas() {
        String sqlTransacoes = "CREATE TABLE IF NOT EXISTS transacoes (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tipo TEXT NOT NULL," +
                " valor REAL NOT NULL," +
                " descricao TEXT," +
                " data TEXT NOT NULL," +
                " categoria TEXT NOT NULL," +
                " idUsuario INTEGER NOT NULL," +
                " FOREIGN KEY(idUsuario) REFERENCES usuarios(id)" +
                ");";

        String sqlOrcamentos = "CREATE TABLE IF NOT EXISTS orcamentos (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " categoria TEXT NOT NULL UNIQUE," +
                " valorLimite REAL NOT NULL," +
                " idUsuario INTEGER NOT NULL," +
                " FOREIGN KEY(idUsuario) REFERENCES usuarios(id)" +
                ");";

        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT NOT NULL," +
                " email TEXT NOT NULL," +
                " senha TEXT NOT NULL" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlTransacoes);
            stmt.execute(sqlOrcamentos);
            stmt.execute(sqlUsuarios);
            System.out.println("Tabelas verificadas/criadas com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao criar as tabelas: " + e.getMessage());
        }
    }
}
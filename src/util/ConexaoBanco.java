package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL = "jdbc:mysql://localhost:3306/GestorFinanceiro?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "rootMysql";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao MySQL: " + e.getMessage());
        }
    }

    public static void criarTabelas() {
        String sqlUsuarios = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                senha VARCHAR(100) NOT NULL
            ) ENGINE=InnoDB;
        """;

        String sqlTransacoes = """
            CREATE TABLE IF NOT EXISTS transacoes (
                id INT AUTO_INCREMENT PRIMARY KEY,
                tipo VARCHAR(20) NOT NULL,
                valor DOUBLE NOT NULL,
                descricao VARCHAR(255),
                data DATE NOT NULL,
                categoria VARCHAR(50) NOT NULL,
                idUsuario INT NOT NULL,
                FOREIGN KEY (idUsuario) REFERENCES usuarios(id)
            ) ENGINE=InnoDB;
        """;

        String sqlOrcamentos = """
            CREATE TABLE IF NOT EXISTS orcamentos (
                id INT AUTO_INCREMENT PRIMARY KEY,
                categoria VARCHAR(50) NOT NULL UNIQUE,
                valorLimite DOUBLE NOT NULL,
                idUsuario INT NOT NULL,
                FOREIGN KEY (idUsuario) REFERENCES usuarios(id)
            ) ENGINE=InnoDB;
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsuarios);
            stmt.execute(sqlTransacoes);
            stmt.execute(sqlOrcamentos);

            System.out.println("Tabelas verificadas/criadas com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar as tabelas: " + e.getMessage());
        }
    }
}

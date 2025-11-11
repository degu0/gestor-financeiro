import util.ConexaoBanco;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConexaoBanco.criarTabelas();
        try (Connection conn = ConexaoBanco.getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
import util.ConexaoBanco;
import view.MenuLogin;

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

        MenuLogin menu = new MenuLogin();

        while (true) {
            int opcao = menu.exibirMenu();
            menu.executarOpcao(opcao);
            if (opcao == 0) break;
        }
    }
}
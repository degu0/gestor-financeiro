import util.ConexaoBanco;
import view.MenuLogin;
import view.MenuTransacao;
import model.Usuario;

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

        MenuLogin menuLogin = new MenuLogin();
        MenuTransacao menuTransacao = new MenuTransacao();

        while (true) {
            int opcao = menuLogin.exibirMenu();

            Usuario logado = menuLogin.executarOpcao(opcao);

            if (opcao == 0) {
                System.out.println("Obrigado por usar o sistema!");
                break;
            }

            if (logado != null) {
                System.out.println("\n=== Bem-vindo ao Menu Financeiro ===");

                menuTransacao.executarMenu(logado.getId());

                System.out.println("\nVocê foi deslogado!");
            }
        }
    }
}

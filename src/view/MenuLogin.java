package view;

import model.Usuario;
import service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class MenuLogin {

    private Scanner input = new Scanner(System.in);
    private UsuarioService usuarioService = new UsuarioService();

    public int exibirMenu() {
        System.out.println("\n=== Login ===");
        System.out.println("1 - Cadastro");
        System.out.println("2 - Log In");
        System.out.println("3 - Listar");
        System.out.println("0 - Sair");
        System.out.print("Escolha a opção: ");

        while (!input.hasNextInt()) {
            System.out.println("Digite um número válido!");
            input.next();
        }

        int opcao = input.nextInt();
        input.nextLine();
        return opcao;
    }

    public void executarOpcao(int opcaoUsuario) {
        switch (opcaoUsuario) {
            case 1:
                cadastrarUsuario();
                break;
            case 2:
                fazerLogin();
                break;
            case 3:
                listarUsuario();
                break;
            case 0:
                System.out.println("Obrigado e volte sempre!");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void cadastrarUsuario() {
        Usuario u = new Usuario();

        System.out.print("Digite o nome do usuário: ");
        u.setNome(input.nextLine());

        System.out.print("Digite o email do usuário: ");
        u.setEmail(input.nextLine());

        System.out.print("Digite a senha do usuário: ");
        u.setSenha(input.nextLine());

        try {
            usuarioService.salvarNovoUsuario(u);
            System.out.println("Cadastro realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void fazerLogin() {
        Usuario u = new Usuario();

        System.out.print("Digite o email do usuário: ");
        u.setEmail(input.nextLine());

        System.out.print("Digite a senha do usuário: ");
        u.setSenha(input.nextLine());

        try {
            Usuario logado = usuarioService.login(u);
            if (logado != null) {
                System.out.println("Login feito com sucesso! Bem-vindo " + logado.getNome());
            } else {
                System.out.println("Email ou senha inválidos!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar fazer login: " + e.getMessage());
        }
    }

    private void listarUsuario() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();

            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário cadastrado!");
                return;
            }

            for (Usuario u : usuarios) {
                System.out.println(u);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }
}

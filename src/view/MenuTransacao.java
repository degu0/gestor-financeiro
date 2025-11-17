package view;

import model.*;
import service.OrcamentoService;
import service.TransacaoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuTransacao {

    private Scanner input = new Scanner(System.in);
    private TransacaoService transacaoService = new TransacaoService();
    private OrcamentoService orcamentoService = new OrcamentoService();

    public int exibirMenu() {
        System.out.println("\n=== Transações ===");
        System.out.println("1 - Cadastrar nova transação");
        System.out.println("2 - Cadastrar novo orçamento");
        System.out.println("3 - Listar transações");
        System.out.println("4 - Relatório (saldo)");
        System.out.println("5 - Deletar transação");
        System.out.println("0 - Voltar");
        System.out.print("Escolha a opção: ");

        while (!input.hasNextInt()) {
            System.out.println("Digite um número válido!");
            input.next();
        }

        int opcao = input.nextInt();
        input.nextLine();
        return opcao;
    }

    public void executarMenu(int idUsuarioLogado) {

        while (true) {
            int opcao = exibirMenu();

            switch (opcao) {
                case 1:
                    cadastrarTransacao(idUsuarioLogado);
                    break;

                case 2:
                    cadastrarOrcamento(idUsuarioLogado);
                    break;

                case 3:
                    listarTransacoes(idUsuarioLogado);
                    break;

                case 4:
                    gerarRelatorio(idUsuarioLogado);
                    break;

                case 5:
                    deletarTransacao(idUsuarioLogado);
                    break;

                case 0:
                    System.out.println("Voltando ao menu...");
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarTransacao(int idUsuario) {

        System.out.println("\n=== Cadastro de Transação ===");
        System.out.print("Tipo (despesa / receita): ");
        String tipo = input.nextLine().trim().toLowerCase();

        if (!tipo.equals("despesa") && !tipo.equals("receita")) {
            System.out.println("Tipo inválido!");
            return;
        }

        System.out.print("Valor: ");
        while (!input.hasNextDouble()) {
            System.out.println("Digite um número válido!");
            input.next();
        }
        double valor = input.nextDouble();
        input.nextLine();

        System.out.print("Descrição: ");
        String descricao = input.nextLine();

        System.out.println("Categorias disponíveis:");
        for (Categoria c : Categoria.values()) {
            System.out.println("- " + c);
        }

        System.out.print("Escolha a categoria: ");
        String catStr = input.nextLine().toUpperCase();

        Categoria categoria;
        try {
            categoria = Categoria.valueOf(catStr);
        } catch (Exception e) {
            System.out.println("Categoria inválida!");
            return;
        }

        Transacao t;
        LocalDate hoje = LocalDate.now();

        if (tipo.equals("despesa")) {
            t = new Despesa( valor, descricao, hoje, categoria);
        } else {
            t = new Receita( valor, descricao, hoje, categoria);
        }

        t.setIdUsuario(idUsuario);

        transacaoService.salvarNovaTransacao(t);
        System.out.println("Transação cadastrada com sucesso!");
    }

    private void cadastrarOrcamento(int idUsuario) {

        System.out.println("\n=== Cadastro de Orçamento ===");

        System.out.println("Categorias disponíveis:");
        for (Categoria c : Categoria.values()) {
            System.out.println("- " + c);
        }

        System.out.print("Escolha a categoria: ");
        String catStr = input.nextLine().toUpperCase();

        Categoria categoria;
        try {
            categoria = Categoria.valueOf(catStr);
        } catch (Exception e) {
            System.out.println("Categoria inválida!");
            return;
        }

        System.out.print("Valor limite do orçamento: ");
        while (!input.hasNextDouble()) {
            System.out.println("Digite um número válido!");
            input.next();
        }
        double valorLimite = input.nextDouble();
        input.nextLine();

        try {
            Orcamento o = new Orcamento(categoria, valorLimite);
            o.setIdUsuario(idUsuario);

            orcamentoService.salvarNovoOrcamento(o);

            System.out.println("Orçamento cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar orçamento: " + e.getMessage());
        }
    }


    private void listarTransacoes(int idUsuario) {
        List<Transacao> lista = transacaoService.listarTodasTransacoesUsuario(idUsuario);

        if (lista.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
            return;
        }

        System.out.println("\n=== Lista de Transações ===");
        for (Transacao t : lista) {
            System.out.println(t);
        }
    }

    private void gerarRelatorio(int idUsuario) {
        OrcamentoService orcamentoService = new OrcamentoService();
        List<Orcamento> orcamentos = orcamentoService.listarOrcamentos(idUsuario);
        List<Transacao> transacoes = transacaoService.listarTodasTransacoesUsuario(idUsuario);
        double saldo = transacaoService.calcularSaldoAtual(idUsuario);

        System.out.println("\n=== Relatório ===");
        System.out.println("Saldo atual: R$ " + saldo);

        if (orcamentos.isEmpty()) {
            System.out.println("Nenhum orçamento cadastrado.");
            return;
        }

        for (Orcamento o : orcamentos) {

            double somaDespesas = 0;

            for (Transacao t : transacoes) {
                if (t instanceof Despesa && ((Despesa) t).getCategoria() == o.getCategoria()) {
                    somaDespesas += t.getValor();
                }
            }

            String acao;
            if (somaDespesas < o.getValorLimite() * 0.7) {
                acao = "Dentro do limite";
            } else if (somaDespesas < o.getValorLimite()) {
                acao = "Atenção: perto do limite!";
            } else {
                acao = "⚠️  Estourou o limite!";
            }

            System.out.println("\nCategoria: " + o.getCategoria());
            System.out.println("Total gasto: R$ " + somaDespesas);
            System.out.println("Orçamento: R$ " + o.getValorLimite());
            System.out.println("Ação: " + acao);
        }
    }

    private void deletarTransacao(int idUsuario) {
        System.out.print("Digite o ID da transação a deletar: ");
        List<Transacao> lista = transacaoService.listarTodasTransacoesUsuario(idUsuario);

        if (lista.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
            return;
        }
        System.out.println("\n=== Lista de Transações ===");
        for (Transacao t : lista) {
            System.out.println(t);
        }

        while (!input.hasNextInt()) {
            System.out.println("Digite um número válido!");
            input.next();
        }

        int id = input.nextInt();
        input.nextLine();

        transacaoService.deletarTransacao(id);
        System.out.println("Transação deletada com sucesso!");
    }
}

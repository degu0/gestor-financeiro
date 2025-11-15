package model;

import java.time.LocalDate;

public class Receita extends Transacao {

    private Categoria categoria;

    // Construtor para CRIAR nova receita (sem ID e data)
    public Receita(double valor, String descricao, Categoria categoria) {
        super(valor, descricao); // chama o construtor da classe Transacao
        this.categoria = categoria;
    }

    // Construtor para LER receita do banco (com ID e data)
    public Receita(double valor, String descricao, LocalDate data, Categoria categoria) {
        super(valor, descricao, data); // chama o construtor completo de Transacao
        this.categoria = categoria;
    }

    @Override
    public String getTipo() {
        return "RECEITA"; // mantém o padrão em caixa alta usado no banco
    }

    // Getters e Setters
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Receita {" +
                " valor=" + getValor() +
                ", descricao='" + getDescricao() + '\'' +
                ", data=" + getData() +
                ", categoria=" + categoria +
                '}';
    }
}

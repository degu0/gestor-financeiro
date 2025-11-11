package model;

public class Orcamento {

    private int id;
    private Categoria categoria;
    private double valorLimite;

    // --- Construtor para CRIAR novo orçamento ---
    public Orcamento(Categoria categoria, double valorLimite) {
        validarValor(valorLimite);
        this.categoria = categoria;
        this.valorLimite = valorLimite;
    }

    // --- Construtor para LER orçamento do banco ---
    public Orcamento(int id, Categoria categoria, double valorLimite) {
        validarValor(valorLimite);
        this.id = id;
        this.categoria = categoria;
        this.valorLimite = valorLimite;
    }

    // --- Getters e Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(double valorLimite) {
        validarValor(valorLimite);
        this.valorLimite = valorLimite;
    }

    // --- Validação interna ---
    private void validarValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor limite deve ser positivo.");
        }
    }

    @Override
    public String toString() {
        return "Orcamento {" +
                "id=" + id +
                ", categoria=" + categoria +
                ", valorLimite=" + valorLimite +
                '}';
    }
}

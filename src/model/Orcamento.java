package model;

public class Orcamento {

    private int id;
    private Categoria categoria;
    private double valorLimite;

    public Orcamento(Categoria categoria, double valorLimite) {
        validarValor(valorLimite);
        this.categoria = categoria;
        this.valorLimite = valorLimite;
    }
    public Orcamento(int id, Categoria categoria, double valorLimite) {
        validarValor(valorLimite);
        this.id = id;
        this.categoria = categoria;
        this.valorLimite = valorLimite;
    }

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

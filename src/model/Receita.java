package model;

import java.time.LocalDate;

public class Receita extends Transacao {

    private Categoria categoria;

    public Receita(double valor, String descricao, Categoria categoria) {
        super(valor, descricao);
        this.categoria = categoria;
    }

    public Receita(double valor, String descricao, LocalDate data, Categoria categoria) {
        super(valor, descricao, data);
        this.categoria = categoria;
    }

    @Override
    public String getTipo() {
        return "RECEITA";
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Receita {" +
                "id=" + getId() +
                " valor=" + getValor() +
                ", descricao='" + getDescricao() + '\'' +
                ", data=" + getData() +
                ", categoria=" + categoria +
                '}';
    }
}

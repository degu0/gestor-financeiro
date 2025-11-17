package model;

import java.time.LocalDate;

public class Despesa extends Transacao {

    private Categoria categoria;

    public Despesa(double valor, String descricao, Categoria categoria) {
        super(valor, descricao);
        this.categoria = categoria;
    }

    public Despesa(double valor, String descricao, LocalDate data, Categoria categoria) {
        super(valor, descricao, data);
        this.categoria = categoria;
    }

    @Override
    public String getTipo() {
        return "DESPESA";
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Despesa {" +
                "id=" + getId() +
                " valor=" + getValor() +
                ", descricao='" + getDescricao() + '\'' +
                ", data=" + getData() +
                ", categoria=" + categoria +
                '}';
    }
}

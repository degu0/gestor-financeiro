package model;

import java.time.LocalDate;

public abstract class Transacao {

    private int id;
    private double valor;
    private String descricao;
    private LocalDate data;
    private int idUsuario;

    public Transacao(double valor, String descricao) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        this.valor = valor;
        this.descricao = descricao;
        this.data = LocalDate.now();

    }
    public Transacao(int id, double valor, String descricao, LocalDate data, int idUsuario) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.idUsuario = idUsuario;
    }

    public Transacao(double valor, String descricao, LocalDate data) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
    }


    public abstract String getTipo();

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getValor(){
        return valor;
    }

    public void setValor(double valor){
        if (valor < 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        this.valor = valor;

    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}

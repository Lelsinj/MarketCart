package com.marketcart.app.models;

import java.util.UUID;

public class Item {

    private String id;
    private String nome;
    private double quantidade;
    private String unidade;
    private double preco;
    private boolean comprado;
    private String categoria;

    public Item() {
        this.id = UUID.randomUUID().toString();
        this.comprado = false;
    }

    public Item(String nome, double quantidade, String unidade, double preco, String categoria) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.preco = preco;
        this.categoria = categoria;
        this.comprado = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }

    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public boolean isComprado() { return comprado; }
    public void setComprado(boolean comprado) { this.comprado = comprado; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    /** Subtotal deste item (preco x quantidade) */
    public double getSubtotal() {
        return preco * quantidade;
    }
}

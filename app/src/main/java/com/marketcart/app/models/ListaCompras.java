package com.marketcart.app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListaCompras {

    private String id;
    private String nome;
    private double orcamento;
    private List<Item> itens;

    public ListaCompras() {
        this.id = UUID.randomUUID().toString();
        this.itens = new ArrayList<>();
    }

    public ListaCompras(String nome, double orcamento) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.orcamento = orcamento;
        this.itens = new ArrayList<>();
    }

    /** Total apenas dos itens marcados como comprado */
    public double calcularTotal() {
        double total = 0;
        for (Item item : itens) {
            if (item.isComprado()) {
                total += item.getSubtotal();
            }
        }
        return total;
    }

    /** Total geral (todos os itens, independente de comprado) */
    public double calcularTotalGeral() {
        double total = 0;
        for (Item item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    /** Quantidade de itens já comprados */
    public int quantidadeComprados() {
        int count = 0;
        for (Item item : itens) {
            if (item.isComprado()) count++;
        }
        return count;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getOrcamento() { return orcamento; }
    public void setOrcamento(double orcamento) { this.orcamento = orcamento; }

    public List<Item> getItens() {
        if (itens == null) itens = new ArrayList<>();
        return itens;
    }
    public void setItens(List<Item> itens) { this.itens = itens; }
}

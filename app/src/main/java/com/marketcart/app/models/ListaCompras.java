package com.marketcart.app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa uma lista de compras criada pelo usuário.
 *
 * <p>Cada lista possui um nome, um orçamento opcional e uma coleção de
 * {@link Item}. Os métodos de cálculo permitem acompanhar o progresso
 * e o total gasto em tempo real.</p>
 */
public class ListaCompras {

    /** Identificador único da lista, gerado via UUID. */
    private String id;

    /** Nome da lista. Ex: "Compras da semana", "Churrasco". */
    private String nome;

    /** Orçamento máximo definido pelo usuário. Zero indica sem limite. */
    private double orcamento;

    /** Coleção de itens pertencentes a esta lista. */
    private List<Item> itens;

    /**
     * Construtor padrão necessário para desserialização via Gson.
     * Inicializa a lista de itens como vazia.
     */
    public ListaCompras() {
        this.id = UUID.randomUUID().toString();
        this.itens = new ArrayList<>();
    }

    /**
     * Construtor para criação de uma nova lista.
     *
     * @param nome      Nome da lista de compras
     * @param orcamento Orçamento máximo em reais (0 para sem limite)
     */
    public ListaCompras(String nome, double orcamento) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.orcamento = orcamento;
        this.itens = new ArrayList<>();
    }

    /**
     * Calcula o total dos itens já marcados como comprado.
     *
     * @return Soma dos subtotais dos itens comprados
     */
    public double calcularTotal() {
        double total = 0;
        for (Item item : getItens()) {
            if (item.isComprado()) total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Calcula o total geral de todos os itens da lista,
     * independente de terem sido comprados ou não.
     *
     * @return Soma dos subtotais de todos os itens
     */
    public double calcularTotalGeral() {
        double total = 0;
        for (Item item : getItens()) total += item.getSubtotal();
        return total;
    }

    /**
     * Retorna a quantidade de itens já marcados como comprado.
     *
     * @return Número de itens comprados
     */
    public int quantidadeComprados() {
        int count = 0;
        for (Item item : getItens()) {
            if (item.isComprado()) count++;
        }
        return count;
    }

    // ── Getters e Setters ──────────────────────────────────────────────────

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

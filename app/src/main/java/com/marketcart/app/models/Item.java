package com.marketcart.app.models;

import java.util.UUID;

/**
 * Representa um produto dentro de uma {@link ListaCompras}.
 *
 * <p>Cada item possui nome, quantidade, unidade de medida, preço unitário,
 * categoria e um flag indicando se já foi comprado.</p>
 *
 * <p>O identificador único ({@code id}) é gerado automaticamente via UUID
 * no momento da criação, garantindo unicidade mesmo sem banco de dados.</p>
 */
public class Item {

    /** Identificador único do item, gerado via UUID. */
    private String id;

    /** Nome do produto. Ex: "Arroz", "Feijão". */
    private String nome;

    /** Quantidade do produto. Ex: 2.5 (para 2,5 kg). */
    private double quantidade;

    /** Unidade de medida. Ex: "kg", "un", "L". */
    private String unidade;

    /** Preço unitário do produto em reais. */
    private double preco;

    /** Indica se o item já foi colocado no carrinho/comprado. */
    private boolean comprado;

    /** Categoria do produto. Ex: "Hortifruti", "Limpeza". */
    private String categoria;

    /**
     * Construtor padrão necessário para desserialização via Gson.
     * Gera um novo UUID automaticamente.
     */
    public Item() {
        this.id = UUID.randomUUID().toString();
        this.comprado = false;
    }

    /**
     * Construtor completo para criação de um novo item.
     *
     * @param nome      Nome do produto
     * @param quantidade Quantidade desejada
     * @param unidade   Unidade de medida (kg, un, L, etc.)
     * @param preco     Preço unitário em reais
     * @param categoria Categoria do produto
     */
    public Item(String nome, double quantidade, String unidade, double preco, String categoria) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.preco = preco;
        this.categoria = categoria;
        this.comprado = false;
    }

    /**
     * Calcula o subtotal deste item (preço × quantidade).
     *
     * @return Valor total do item em reais
     */
    public double getSubtotal() {
        return preco * quantidade;
    }

    // ── Getters e Setters ──────────────────────────────────────────────────

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
}

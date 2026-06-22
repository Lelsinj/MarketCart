package com.marketcart.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marketcart.app.R;
import com.marketcart.app.models.ListaCompras;

import java.util.List;

/**
 * Adapter do RecyclerView para exibição da lista de compras na tela principal.
 *
 * <p>Cada item exibe o nome da lista, orçamento definido e progresso
 * de itens comprados. Suporta clique simples (abrir lista) e clique
 * longo (menu de editar/excluir).</p>
 */
public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {

    private List<ListaCompras> listas;
    private final OnListaClickListener listener;

    /**
     * Interface de callbacks para interações do usuário com os cards de lista.
     */
    public interface OnListaClickListener {
        /** Chamado quando o usuário toca em uma lista. */
        void onListaClick(ListaCompras lista);
        /** Chamado quando o usuário segura o toque em uma lista. */
        void onListaLongClick(ListaCompras lista);
    }

    /**
     * @param listas   Coleção inicial de listas a exibir
     * @param listener Callbacks de interação do usuário
     */
    public ListaAdapter(List<ListaCompras> listas, OnListaClickListener listener) {
        this.listas   = listas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        ListaCompras lista = listas.get(position);

        holder.txtNomeLista.setText(lista.getNome());

        holder.txtOrcamento.setText(lista.getOrcamento() > 0
                ? String.format("Orçamento: R$ %.2f", lista.getOrcamento())
                : "Sem orçamento definido");

        int total     = lista.getItens().size();
        int comprados = lista.quantidadeComprados();
        holder.txtQuantidadeItens.setText(comprados + "/" + total + " itens comprados");

        holder.itemView.setOnClickListener(v -> listener.onListaClick(lista));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onListaLongClick(lista);
            return true;
        });
    }

    @Override
    public int getItemCount() { return listas.size(); }

    /**
     * Atualiza o dataset do adapter e notifica o RecyclerView.
     *
     * @param novasListas Nova coleção de listas a exibir
     */
    public void atualizarListas(List<ListaCompras> novasListas) {
        this.listas = novasListas;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder que mantém referências aos elementos visuais de cada card de lista.
     */
    static class ListaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeLista;
        TextView txtOrcamento;
        TextView txtQuantidadeItens;

        ListaViewHolder(View itemView) {
            super(itemView);
            txtNomeLista       = itemView.findViewById(R.id.txtNomeLista);
            txtOrcamento       = itemView.findViewById(R.id.txtOrcamentoLista);
            txtQuantidadeItens = itemView.findViewById(R.id.txtQuantidadeItens);
        }
    }
}

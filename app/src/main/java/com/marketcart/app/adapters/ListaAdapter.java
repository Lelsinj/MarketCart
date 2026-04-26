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

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {

    private List<ListaCompras> listas;
    private OnListaClickListener listener;

    // Interface para tratar cliques
    public interface OnListaClickListener {
        void onListaClick(ListaCompras lista);
        void onListaLongClick(ListaCompras lista); // clique longo para excluir
    }

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
        holder.txtOrcamento.setText(String.format("Orçamento: R$ %.2f", lista.getOrcamento()));
        holder.txtQuantidadeItens.setText(lista.getItens().size() + " itens");

        holder.itemView.setOnClickListener(v -> listener.onListaClick(lista));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onListaLongClick(lista);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    // Atualiza a lista exibida
    public void atualizarListas(List<ListaCompras> novasListas) {
        this.listas = novasListas;
        notifyDataSetChanged();
    }

    // ViewHolder: referencia os elementos do layout item_lista.xml
    static class ListaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeLista;
        TextView txtOrcamento;
        TextView txtQuantidadeItens;

        ListaViewHolder(View itemView) {
            super(itemView);
            txtNomeLista      = itemView.findViewById(R.id.txtNomeLista);
            txtOrcamento      = itemView.findViewById(R.id.txtOrcamentoLista);
            txtQuantidadeItens = itemView.findViewById(R.id.txtQuantidadeItens);
        }
    }
}

package com.marketcart.app.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marketcart.app.R;
import com.marketcart.app.models.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itens;
    private final OnItemInteractionListener listener;

    public interface OnItemInteractionListener {
        void onCheckChanged(Item item, boolean comprado);
        void onItemLongClick(Item item);
    }

    public ItemAdapter(List<Item> itens, OnItemInteractionListener listener) {
        this.itens    = itens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itens.get(position);

        holder.txtNome.setText(item.getNome());
        holder.txtQuantidade.setText(formatar(item.getQuantidade()) + " " + item.getUnidade());
        holder.txtCategoria.setText(item.getCategoria());
        holder.txtPreco.setText(String.format("R$ %.2f", item.getPreco()));

        // Atualiza checkbox sem disparar o listener
        holder.checkComprado.setOnCheckedChangeListener(null);
        holder.checkComprado.setChecked(item.isComprado());
        aplicarEstilo(holder, item.isComprado());

        holder.checkComprado.setOnCheckedChangeListener((btn, isChecked) -> {
            item.setComprado(isChecked);
            aplicarEstilo(holder, isChecked);
            listener.onCheckChanged(item, isChecked);
        });

        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(item);
            return true;
        });
    }

    /** Aplica risco e opacidade quando o item está comprado */
    private void aplicarEstilo(ItemViewHolder holder, boolean comprado) {
        if (comprado) {
            holder.txtNome.setPaintFlags(
                    holder.txtNome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtNome.setAlpha(0.45f);
            holder.itemView.setAlpha(0.7f);
        } else {
            holder.txtNome.setPaintFlags(
                    holder.txtNome.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtNome.setAlpha(1f);
            holder.itemView.setAlpha(1f);
        }
    }

    private String formatar(double valor) {
        if (valor == (long) valor) return String.valueOf((long) valor);
        return String.valueOf(valor);
    }

    @Override
    public int getItemCount() { return itens.size(); }

    public void atualizarItens(List<Item> novosItens) {
        this.itens = novosItens;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkComprado;
        TextView txtNome, txtQuantidade, txtCategoria, txtPreco;

        ItemViewHolder(View itemView) {
            super(itemView);
            checkComprado = itemView.findViewById(R.id.checkComprado);
            txtNome       = itemView.findViewById(R.id.txtNomeItem);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidadeItem);
            txtCategoria  = itemView.findViewById(R.id.txtCategoriaItem);
            txtPreco      = itemView.findViewById(R.id.txtPrecoItem);
        }
    }
}

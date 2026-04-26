package com.marketcart.app.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marketcart.app.R;
import com.marketcart.app.adapters.ItemAdapter;
import com.marketcart.app.models.Item;
import com.marketcart.app.models.ListaCompras;
import com.marketcart.app.storage.StorageManager;

public class ItensActivity extends AppCompatActivity {

    public static final String EXTRA_LISTA_ID = "lista_id";

    private RecyclerView recyclerItens;
    private View layoutVazio;
    private TextView txtOrcamento;
    private TextView txtTotal;
    private ItemAdapter adapter;
    private StorageManager storage;
    private ListaCompras lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);

        storage = new StorageManager(this);

        // Recupera o ID da lista passado pela MainActivity
        String listaId = getIntent().getStringExtra(EXTRA_LISTA_ID);
        lista = storage.buscarListaPorId(listaId);

        if (lista == null) {
            finish();
            return;
        }

        // Toolbar com nome da lista
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(lista.getNome());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Views
        recyclerItens = findViewById(R.id.recyclerItens);
        layoutVazio   = findViewById(R.id.layoutVazio);
        txtOrcamento  = findViewById(R.id.txtOrcamento);
        txtTotal      = findViewById(R.id.txtTotal);

        recyclerItens.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter(lista.getItens(), new ItemAdapter.OnItemInteractionListener() {

            @Override
            public void onCheckChanged(Item item, boolean comprado) {
                // Atualiza o item na lista em memória e salva no storage
                item.setComprado(comprado);
                storage.atualizarLista(lista);
                atualizarTotal();
            }

            @Override
            public void onItemLongClick(Item item) {
                new AlertDialog.Builder(ItensActivity.this)
                        .setTitle("Excluir item")
                        .setMessage("Deseja excluir \"" + item.getNome() + "\"?")
                        .setPositiveButton("Excluir", (dialog, which) -> {
                            lista.getItens().remove(item);
                            storage.atualizarLista(lista);
                            adapter.atualizarItens(lista.getItens());
                            atualizarEstadoVazio();
                            atualizarTotal();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        recyclerItens.setAdapter(adapter);
        atualizarEstadoVazio();
        atualizarTotal();

        // FAB: novo item
        FloatingActionButton fab = findViewById(R.id.fabNovoItem);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, NovoItemActivity.class);
            intent.putExtra(EXTRA_LISTA_ID, lista.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega a lista ao voltar do formulário de novo item
        lista = storage.buscarListaPorId(lista.getId());
        if (lista != null) {
            adapter.atualizarItens(lista.getItens());
            atualizarEstadoVazio();
            atualizarTotal();
        }
    }

    private void atualizarTotal() {
        txtOrcamento.setText(String.format("R$ %.2f", lista.getOrcamento()));
        txtTotal.setText(String.format("R$ %.2f", lista.calcularTotal()));
    }

    private void atualizarEstadoVazio() {
        if (lista.getItens().isEmpty()) {
            layoutVazio.setVisibility(View.VISIBLE);
            recyclerItens.setVisibility(View.GONE);
        } else {
            layoutVazio.setVisibility(View.GONE);
            recyclerItens.setVisibility(View.VISIBLE);
        }
    }
}

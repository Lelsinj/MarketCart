package com.marketcart.app.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
    private TextView txtProgresso;
    private ItemAdapter adapter;
    private StorageManager storage;
    private ListaCompras lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);

        storage = new StorageManager(this);

        String listaId = getIntent().getStringExtra(EXTRA_LISTA_ID);
        lista = storage.buscarListaPorId(listaId);
        if (lista == null) { finish(); return; }

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(lista.getNome());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Views
        recyclerItens  = findViewById(R.id.recyclerItens);
        layoutVazio    = findViewById(R.id.layoutVazio);
        txtOrcamento   = findViewById(R.id.txtOrcamento);
        txtTotal       = findViewById(R.id.txtTotal);
        txtProgresso   = findViewById(R.id.txtProgresso);

        recyclerItens.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter(lista.getItens(), new ItemAdapter.OnItemInteractionListener() {

            @Override
            public void onCheckChanged(Item item, boolean comprado) {
                // Atualiza o item na lista em memória e persiste
                item.setComprado(comprado);
                storage.atualizarLista(lista);
                atualizarResumo();
            }

            @Override
            public void onItemLongClick(Item item) {
                new AlertDialog.Builder(ItensActivity.this)
                        .setTitle("Excluir item")
                        .setMessage("Deseja excluir \"" + item.getNome() + "\"?")
                        .setPositiveButton("Excluir", (d, w) -> {
                            lista.getItens().remove(item);
                            storage.atualizarLista(lista);
                            adapter.atualizarItens(lista.getItens());
                            atualizarEstadoVazio();
                            atualizarResumo();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        recyclerItens.setAdapter(adapter);
        atualizarEstadoVazio();
        atualizarResumo();

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
        // Recarrega do storage para refletir itens recém-adicionados
        lista = storage.buscarListaPorId(lista.getId());
        if (lista == null) { finish(); return; }
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(lista.getNome());
        adapter.atualizarItens(lista.getItens());
        atualizarEstadoVazio();
        atualizarResumo();
    }

    /** Atualiza os valores de orçamento, total e progresso no card superior */
    private void atualizarResumo() {
        double orcamento = lista.getOrcamento();
        double total     = lista.calcularTotal();
        int comprados    = lista.quantidadeComprados();
        int totalItens   = lista.getItens().size();

        txtOrcamento.setText(String.format("R$ %.2f", orcamento));
        txtTotal.setText(String.format("R$ %.2f", total));

        if (txtProgresso != null) {
            txtProgresso.setText(comprados + " de " + totalItens + " itens comprados");
        }

        // Aviso visual se ultrapassar orçamento
        if (orcamento > 0 && total > orcamento) {
            txtTotal.setTextColor(Color.parseColor("#FF5252")); // vermelho
        } else {
            txtTotal.setTextColor(Color.WHITE);
        }
    }

    private void atualizarEstadoVazio() {
        boolean vazio = lista.getItens().isEmpty();
        layoutVazio.setVisibility(vazio ? View.VISIBLE : View.GONE);
        recyclerItens.setVisibility(vazio ? View.GONE : View.VISIBLE);
    }
}

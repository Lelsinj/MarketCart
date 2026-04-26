package com.marketcart.app.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marketcart.app.R;
import com.marketcart.app.adapters.ListaAdapter;
import com.marketcart.app.models.ListaCompras;
import com.marketcart.app.storage.StorageManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Código de retorno ao voltar da NovaListaActivity
    public static final int REQUEST_NOVA_LISTA = 1;

    private RecyclerView recyclerListas;
    private View layoutVazio;
    private ListaAdapter adapter;
    private StorageManager storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Storage
        storage = new StorageManager(this);

        // Views
        recyclerListas = findViewById(R.id.recyclerListas);
        layoutVazio    = findViewById(R.id.layoutVazio);

        // Configura o RecyclerView
        recyclerListas.setLayoutManager(new LinearLayoutManager(this));

        List<ListaCompras> listas = storage.carregarListas();

        adapter = new ListaAdapter(listas, new ListaAdapter.OnListaClickListener() {

            @Override
            public void onListaClick(ListaCompras lista) {
                // Navega para a tela de itens passando o ID da lista
                Intent intent = new Intent(MainActivity.this, ItensActivity.class);
                intent.putExtra(ItensActivity.EXTRA_LISTA_ID, lista.getId());
                startActivityForResult(intent, REQUEST_NOVA_LISTA);
            }

            @Override
            public void onListaLongClick(ListaCompras lista) {
                // Confirma exclusão
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Excluir lista")
                        .setMessage("Deseja excluir a lista \"" + lista.getNome() + "\"?")
                        .setPositiveButton("Excluir", (dialog, which) -> {
                            storage.removerLista(lista.getId());
                            recarregarListas();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        recyclerListas.setAdapter(adapter);
        atualizarEstadoVazio(listas);

        // FAB: abrir formulário de nova lista
        FloatingActionButton fab = findViewById(R.id.fabNovaLista);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, NovaListaActivity.class);
            startActivityForResult(intent, REQUEST_NOVA_LISTA);
        });
    }

    // Recarrega ao voltar de outra tela
    @Override
    protected void onResume() {
        super.onResume();
        recarregarListas();
    }

    private void recarregarListas() {
        List<ListaCompras> listas = storage.carregarListas();
        adapter.atualizarListas(listas);
        atualizarEstadoVazio(listas);
    }

    private void atualizarEstadoVazio(List<ListaCompras> listas) {
        if (listas.isEmpty()) {
            layoutVazio.setVisibility(View.VISIBLE);
            recyclerListas.setVisibility(View.GONE);
        } else {
            layoutVazio.setVisibility(View.GONE);
            recyclerListas.setVisibility(View.VISIBLE);
        }
    }
}

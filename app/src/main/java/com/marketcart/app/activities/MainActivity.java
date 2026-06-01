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

    private RecyclerView recyclerListas;
    private View layoutVazio;
    private ListaAdapter adapter;
    private StorageManager storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storage        = new StorageManager(this);
        recyclerListas = findViewById(R.id.recyclerListas);
        layoutVazio    = findViewById(R.id.layoutVazio);

        recyclerListas.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListaAdapter(storage.carregarListas(), new ListaAdapter.OnListaClickListener() {

            @Override
            public void onListaClick(ListaCompras lista) {
                Intent intent = new Intent(MainActivity.this, ItensActivity.class);
                intent.putExtra(ItensActivity.EXTRA_LISTA_ID, lista.getId());
                startActivity(intent);
            }

            @Override
            public void onListaLongClick(ListaCompras lista) {
                // Menu com opções: Editar ou Excluir
                String[] opcoes = {"✏️  Editar lista", "🗑️  Excluir lista"};
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(lista.getNome())
                        .setItems(opcoes, (dialog, which) -> {
                            if (which == 0) {
                                // Editar
                                Intent intent = new Intent(MainActivity.this, EditarListaActivity.class);
                                intent.putExtra(EditarListaActivity.EXTRA_LISTA_ID, lista.getId());
                                startActivity(intent);
                            } else {
                                // Excluir com confirmação
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Excluir lista")
                                        .setMessage("Deseja excluir \"" + lista.getNome() + "\"?\nEsta ação não pode ser desfeita.")
                                        .setPositiveButton("Excluir", (d, w) -> {
                                            storage.removerLista(lista.getId());
                                            recarregarListas();
                                        })
                                        .setNegativeButton("Cancelar", null)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        recyclerListas.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabNovaLista);
        fab.setOnClickListener(v ->
                startActivity(new Intent(this, NovaListaActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        recarregarListas();
    }

    private void recarregarListas() {
        List<ListaCompras> listas = storage.carregarListas();
        adapter.atualizarListas(listas);
        layoutVazio.setVisibility(listas.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerListas.setVisibility(listas.isEmpty() ? View.GONE : View.VISIBLE);
    }
}

package com.marketcart.app.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.marketcart.app.R;
import com.marketcart.app.models.Item;
import com.marketcart.app.models.ListaCompras;
import com.marketcart.app.storage.StorageManager;

public class NovoItemActivity extends AppCompatActivity {

    private TextInputLayout layoutNome;
    private TextInputEditText edtNome;
    private TextInputEditText edtQuantidade;
    private TextInputEditText edtPreco;
    private AutoCompleteTextView spinnerUnidade;
    private AutoCompleteTextView spinnerCategoria;
    private StorageManager storage;
    private ListaCompras lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        storage = new StorageManager(this);

        String listaId = getIntent().getStringExtra(ItensActivity.EXTRA_LISTA_ID);
        lista = storage.buscarListaPorId(listaId);

        if (lista == null) {
            finish();
            return;
        }

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Views
        layoutNome      = findViewById(R.id.layoutNomeProduto);
        edtNome         = findViewById(R.id.edtNomeProduto);
        edtQuantidade   = findViewById(R.id.edtQuantidade);
        edtPreco        = findViewById(R.id.edtPreco);
        spinnerUnidade  = findViewById(R.id.spinnerUnidade);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);

        // Popula o spinner de unidades
        String[] unidades = getResources().getStringArray(R.array.unidades);
        ArrayAdapter<String> adapterUnidade = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, unidades);
        spinnerUnidade.setAdapter(adapterUnidade);
        spinnerUnidade.setText(unidades[0], false);

        // Popula o spinner de categorias (ignora o primeiro item "Selecione...")
        String[] todasCategorias = getResources().getStringArray(R.array.categorias);
        String[] categorias = new String[todasCategorias.length - 1];
        System.arraycopy(todasCategorias, 1, categorias, 0, categorias.length);
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, categorias);
        spinnerCategoria.setAdapter(adapterCategoria);
        spinnerCategoria.setText(categorias[categorias.length - 1], false); // "Outros"

        // Botão salvar
        MaterialButton btnAdicionar = findViewById(R.id.btnAdicionarItem);
        btnAdicionar.setOnClickListener(v -> salvarItem());
    }

    private void salvarItem() {
        String nome = edtNome.getText() != null
                ? edtNome.getText().toString().trim() : "";

        if (TextUtils.isEmpty(nome)) {
            layoutNome.setError(getString(R.string.campo_obrigatorio));
            return;
        }
        layoutNome.setError(null);

        double quantidade = 1;
        String qtdStr = edtQuantidade.getText() != null
                ? edtQuantidade.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(qtdStr)) {
            try { quantidade = Double.parseDouble(qtdStr.replace(",", ".")); }
            catch (NumberFormatException e) { quantidade = 1; }
        }

        double preco = 0;
        String precoStr = edtPreco.getText() != null
                ? edtPreco.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(precoStr)) {
            try { preco = Double.parseDouble(precoStr.replace(",", ".")); }
            catch (NumberFormatException e) { preco = 0; }
        }

        String unidade  = spinnerUnidade.getText().toString();
        String categoria = spinnerCategoria.getText().toString();

        // Cria o item e adiciona à lista
        Item novoItem = new Item(nome, quantidade, unidade, preco, categoria);
        lista.getItens().add(novoItem);
        storage.atualizarLista(lista);

        Toast.makeText(this, "Item adicionado!", Toast.LENGTH_SHORT).show();
        finish();
    }
}

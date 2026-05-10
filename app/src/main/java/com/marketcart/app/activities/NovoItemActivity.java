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
    private TextInputEditText edtNome, edtQuantidade, edtPreco;
    private AutoCompleteTextView spinnerUnidade, spinnerCategoria;
    private StorageManager storage;
    private ListaCompras lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        storage = new StorageManager(this);

        String listaId = getIntent().getStringExtra(ItensActivity.EXTRA_LISTA_ID);
        lista = storage.buscarListaPorId(listaId);
        if (lista == null) { finish(); return; }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        layoutNome       = findViewById(R.id.layoutNomeProduto);
        edtNome          = findViewById(R.id.edtNomeProduto);
        edtQuantidade    = findViewById(R.id.edtQuantidade);
        edtPreco         = findViewById(R.id.edtPreco);
        spinnerUnidade   = findViewById(R.id.spinnerUnidade);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);

        // Unidades
        String[] unidades = {"un", "kg", "g", "L", "ml", "pct", "cx"};
        spinnerUnidade.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, unidades));
        spinnerUnidade.setText(unidades[0], false);

        // Categorias
        String[] categorias = {"Hortifruti", "Carnes", "Laticínios", "Bebidas",
                               "Limpeza", "Higiene", "Padaria", "Congelados", "Outros"};
        spinnerCategoria.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, categorias));
        spinnerCategoria.setText(categorias[categorias.length - 1], false);

        MaterialButton btnAdicionar = findViewById(R.id.btnAdicionarItem);
        btnAdicionar.setOnClickListener(v -> salvarItem());
    }

    private void salvarItem() {
        String nome = edtNome.getText() != null
                ? edtNome.getText().toString().trim() : "";

        if (TextUtils.isEmpty(nome)) {
            layoutNome.setError("Nome obrigatório");
            return;
        }
        layoutNome.setError(null);

        double quantidade = 1;
        try {
            String s = edtQuantidade.getText() != null
                    ? edtQuantidade.getText().toString().trim() : "";
            if (!s.isEmpty()) quantidade = Double.parseDouble(s.replace(",", "."));
        } catch (NumberFormatException ignored) {}

        double preco = 0;
        try {
            String s = edtPreco.getText() != null
                    ? edtPreco.getText().toString().trim() : "";
            if (!s.isEmpty()) preco = Double.parseDouble(s.replace(",", "."));
        } catch (NumberFormatException ignored) {}

        String unidade   = spinnerUnidade.getText().toString();
        String categoria = spinnerCategoria.getText().toString();

        // Adiciona o item à lista e persiste
        lista.getItens().add(new Item(nome, quantidade, unidade, preco, categoria));
        storage.atualizarLista(lista);

        Toast.makeText(this, nome + " adicionado!", Toast.LENGTH_SHORT).show();
        finish(); // Volta para ItensActivity que recarrega em onResume
    }
}

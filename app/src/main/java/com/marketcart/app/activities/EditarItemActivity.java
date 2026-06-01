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

public class EditarItemActivity extends AppCompatActivity {

    public static final String EXTRA_LISTA_ID = "lista_id";
    public static final String EXTRA_ITEM_ID  = "item_id";

    private TextInputLayout layoutNome, layoutQuantidade, layoutPreco;
    private TextInputEditText edtNome, edtQuantidade, edtPreco;
    private AutoCompleteTextView spinnerUnidade, spinnerCategoria;
    private StorageManager storage;
    private ListaCompras lista;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item);

        storage = new StorageManager(this);

        String listaId = getIntent().getStringExtra(EXTRA_LISTA_ID);
        String itemId  = getIntent().getStringExtra(EXTRA_ITEM_ID);

        lista = storage.buscarListaPorId(listaId);
        if (lista == null) { finish(); return; }

        item = buscarItemPorId(itemId);
        if (item == null) { finish(); return; }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Editar Item");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        layoutNome      = findViewById(R.id.layoutNomeProduto);
        layoutQuantidade = findViewById(R.id.layoutQuantidade);
        layoutPreco     = findViewById(R.id.layoutPreco);
        edtNome         = findViewById(R.id.edtNomeProduto);
        edtQuantidade   = findViewById(R.id.edtQuantidade);
        edtPreco        = findViewById(R.id.edtPreco);
        spinnerUnidade  = findViewById(R.id.spinnerUnidade);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);

        // Spinners
        String[] unidades = {"un", "kg", "g", "L", "ml", "pct", "cx"};
        spinnerUnidade.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, unidades));

        String[] categorias = {"Hortifruti", "Carnes", "Laticínios", "Bebidas",
                               "Limpeza", "Higiene", "Padaria", "Congelados", "Outros"};
        spinnerCategoria.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, categorias));

        // Preenche com os valores atuais do item
        edtNome.setText(item.getNome());
        edtQuantidade.setText(formatarNumero(item.getQuantidade()));
        edtPreco.setText(String.format("%.2f", item.getPreco()));
        spinnerUnidade.setText(item.getUnidade(), false);
        spinnerCategoria.setText(item.getCategoria(), false);

        MaterialButton btnSalvar = findViewById(R.id.btnAdicionarItem);
        btnSalvar.setText("Salvar alterações");
        btnSalvar.setOnClickListener(v -> salvarEdicao());
    }

    private void salvarEdicao() {
        String nome = edtNome.getText() != null
                ? edtNome.getText().toString().trim() : "";

        // Validação: nome
        if (TextUtils.isEmpty(nome)) {
            layoutNome.setError("Nome obrigatório");
            return;
        }
        if (nome.length() < 2) {
            layoutNome.setError("Nome deve ter pelo menos 2 caracteres");
            return;
        }
        layoutNome.setError(null);

        // Validação: quantidade
        double quantidade = 1;
        String qtdStr = edtQuantidade.getText() != null
                ? edtQuantidade.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(qtdStr)) {
            try {
                quantidade = Double.parseDouble(qtdStr.replace(",", "."));
                if (quantidade <= 0) {
                    layoutQuantidade.setError("Quantidade deve ser maior que zero");
                    return;
                }
            } catch (NumberFormatException e) {
                layoutQuantidade.setError("Valor inválido");
                return;
            }
        }
        layoutQuantidade.setError(null);

        // Validação: preço
        double preco = 0;
        String precoStr = edtPreco.getText() != null
                ? edtPreco.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(precoStr)) {
            try {
                preco = Double.parseDouble(precoStr.replace(",", "."));
                if (preco < 0) {
                    layoutPreco.setError("Preço não pode ser negativo");
                    return;
                }
            } catch (NumberFormatException e) {
                layoutPreco.setError("Valor inválido");
                return;
            }
        }
        layoutPreco.setError(null);

        // Atualiza o item
        item.setNome(nome);
        item.setQuantidade(quantidade);
        item.setPreco(preco);
        item.setUnidade(spinnerUnidade.getText().toString());
        item.setCategoria(spinnerCategoria.getText().toString());

        storage.atualizarLista(lista);
        Toast.makeText(this, "Item atualizado!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private Item buscarItemPorId(String itemId) {
        for (Item i : lista.getItens()) {
            if (i.getId().equals(itemId)) return i;
        }
        return null;
    }

    private String formatarNumero(double valor) {
        if (valor == (long) valor) return String.valueOf((long) valor);
        return String.valueOf(valor);
    }
}

package com.marketcart.app.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.marketcart.app.R;
import com.marketcart.app.models.ListaCompras;
import com.marketcart.app.storage.StorageManager;

public class EditarListaActivity extends AppCompatActivity {

    public static final String EXTRA_LISTA_ID = "lista_id";

    private TextInputLayout layoutNome;
    private TextInputEditText edtNome, edtOrcamento;
    private StorageManager storage;
    private ListaCompras lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista);

        storage = new StorageManager(this);

        String listaId = getIntent().getStringExtra(EXTRA_LISTA_ID);
        lista = storage.buscarListaPorId(listaId);
        if (lista == null) { finish(); return; }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Editar Lista");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        layoutNome   = findViewById(R.id.layoutNomeLista);
        edtNome      = findViewById(R.id.edtNomeLista);
        edtOrcamento = findViewById(R.id.edtOrcamento);

        // Preenche os campos com os valores atuais
        edtNome.setText(lista.getNome());
        if (lista.getOrcamento() > 0) {
            edtOrcamento.setText(String.format("%.2f", lista.getOrcamento()));
        }

        MaterialButton btnSalvar = findViewById(R.id.btnSalvarLista);
        btnSalvar.setOnClickListener(v -> salvarEdicao());
    }

    private void salvarEdicao() {
        String nome = edtNome.getText() != null
                ? edtNome.getText().toString().trim() : "";

        // Validação: nome obrigatório
        if (TextUtils.isEmpty(nome)) {
            layoutNome.setError("Nome obrigatório");
            return;
        }
        if (nome.length() < 2) {
            layoutNome.setError("Nome deve ter pelo menos 2 caracteres");
            return;
        }
        layoutNome.setError(null);

        // Validação: orçamento
        double orcamento = 0;
        String orcStr = edtOrcamento.getText() != null
                ? edtOrcamento.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(orcStr)) {
            try {
                orcamento = Double.parseDouble(orcStr.replace(",", "."));
                if (orcamento < 0) {
                    edtOrcamento.setError("Orçamento não pode ser negativo");
                    return;
                }
            } catch (NumberFormatException e) {
                edtOrcamento.setError("Valor inválido");
                return;
            }
        }

        lista.setNome(nome);
        lista.setOrcamento(orcamento);
        storage.atualizarLista(lista);

        Toast.makeText(this, "Lista atualizada!", Toast.LENGTH_SHORT).show();
        finish();
    }
}

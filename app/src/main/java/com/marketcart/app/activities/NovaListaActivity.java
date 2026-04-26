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

public class NovaListaActivity extends AppCompatActivity {

    private TextInputLayout layoutNome;
    private TextInputEditText edtNome;
    private TextInputEditText edtOrcamento;
    private StorageManager storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista);

        // Toolbar com botão voltar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        storage      = new StorageManager(this);
        layoutNome   = findViewById(R.id.layoutNomeLista);
        edtNome      = findViewById(R.id.edtNomeLista);
        edtOrcamento = findViewById(R.id.edtOrcamento);

        MaterialButton btnSalvar = findViewById(R.id.btnSalvarLista);
        btnSalvar.setOnClickListener(v -> salvarLista());
    }

    private void salvarLista() {
        String nome = edtNome.getText() != null
                ? edtNome.getText().toString().trim() : "";

        // Validação
        if (TextUtils.isEmpty(nome)) {
            layoutNome.setError(getString(R.string.campo_obrigatorio));
            return;
        }
        layoutNome.setError(null);

        double orcamento = 0;
        String orcamentoStr = edtOrcamento.getText() != null
                ? edtOrcamento.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(orcamentoStr)) {
            try {
                orcamento = Double.parseDouble(orcamentoStr.replace(",", "."));
            } catch (NumberFormatException e) {
                orcamento = 0;
            }
        }

        // Cria e salva a nova lista
        ListaCompras novaLista = new ListaCompras(nome, orcamento);
        storage.adicionarLista(novaLista);

        Toast.makeText(this, "Lista criada com sucesso!", Toast.LENGTH_SHORT).show();
        finish(); // Volta para a MainActivity
    }
}

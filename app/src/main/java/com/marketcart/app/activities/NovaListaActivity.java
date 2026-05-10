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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        if (TextUtils.isEmpty(nome)) {
            layoutNome.setError("Nome obrigatório");
            return;
        }
        layoutNome.setError(null);

        double orcamento = 0;
        try {
            String s = edtOrcamento.getText() != null
                    ? edtOrcamento.getText().toString().trim() : "";
            if (!s.isEmpty()) orcamento = Double.parseDouble(s.replace(",", "."));
        } catch (NumberFormatException ignored) {}

        storage.adicionarLista(new ListaCompras(nome, orcamento));
        Toast.makeText(this, "Lista criada!", Toast.LENGTH_SHORT).show();
        finish();
    }
}

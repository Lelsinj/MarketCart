package com.marketcart.app.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marketcart.app.models.ListaCompras;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {

    private static final String PREF_NAME  = "marketcart_storage";
    private static final String KEY_LISTAS = "listas_compras";

    private final SharedPreferences prefs;
    private final Gson gson;

    public StorageManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson  = new Gson();
    }

    /** Retorna todas as listas salvas. Nunca retorna null. */
    public List<ListaCompras> carregarListas() {
        String json = prefs.getString(KEY_LISTAS, null);
        if (json == null) return new ArrayList<>();
        Type tipo = new TypeToken<ArrayList<ListaCompras>>() {}.getType();
        List<ListaCompras> listas = gson.fromJson(json, tipo);
        return listas != null ? listas : new ArrayList<>();
    }

    /** Substitui toda a lista salva pela nova. */
    public void salvarListas(List<ListaCompras> listas) {
        prefs.edit().putString(KEY_LISTAS, gson.toJson(listas)).apply();
    }

    /** Adiciona uma nova lista. */
    public void adicionarLista(ListaCompras lista) {
        List<ListaCompras> listas = carregarListas();
        listas.add(lista);
        salvarListas(listas);
    }

    /** Atualiza uma lista existente pelo id. */
    public void atualizarLista(ListaCompras listaAtualizada) {
        List<ListaCompras> listas = carregarListas();
        for (int i = 0; i < listas.size(); i++) {
            if (listas.get(i).getId().equals(listaAtualizada.getId())) {
                listas.set(i, listaAtualizada);
                break;
            }
        }
        salvarListas(listas);
    }

    /** Remove uma lista pelo id. */
    public void removerLista(String listaId) {
        List<ListaCompras> listas = carregarListas();
        listas.removeIf(l -> l.getId().equals(listaId));
        salvarListas(listas);
    }

    /** Busca uma lista específica pelo id. */
    public ListaCompras buscarListaPorId(String listaId) {
        for (ListaCompras l : carregarListas()) {
            if (l.getId().equals(listaId)) return l;
        }
        return null;
    }
}

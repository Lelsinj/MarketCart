package com.marketcart.app.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marketcart.app.models.ListaCompras;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador de persistência local do MarketCart.
 *
 * <p>Utiliza {@link SharedPreferences} como mecanismo de armazenamento (LocalStorage),
 * serializando e desserializando os objetos em formato JSON via biblioteca Gson.</p>
 *
 * <p>Todas as listas de compras e seus itens são armazenados em uma única chave
 * no SharedPreferences, garantindo simplicidade e compatibilidade.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     StorageManager storage = new StorageManager(context);
 *     storage.adicionarLista(new ListaCompras("Semana", 150.0));
 *     List&lt;ListaCompras&gt; listas = storage.carregarListas();
 * </pre>
 */
public class StorageManager {

    /** Nome do arquivo de SharedPreferences. */
    private static final String PREF_NAME  = "marketcart_storage";

    /** Chave onde a lista de compras é armazenada em JSON. */
    private static final String KEY_LISTAS = "listas_compras";

    private final SharedPreferences prefs;
    private final Gson gson;

    /**
     * Cria uma nova instância do StorageManager.
     *
     * @param context Contexto Android necessário para acessar o SharedPreferences
     */
    public StorageManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson  = new Gson();
    }

    /**
     * Carrega todas as listas de compras salvas no armazenamento local.
     *
     * @return Lista de {@link ListaCompras} salvas, ou lista vazia se não houver nenhuma
     */
    public List<ListaCompras> carregarListas() {
        String json = prefs.getString(KEY_LISTAS, null);
        if (json == null) return new ArrayList<>();
        Type tipo = new TypeToken<ArrayList<ListaCompras>>() {}.getType();
        List<ListaCompras> listas = gson.fromJson(json, tipo);
        return listas != null ? listas : new ArrayList<>();
    }

    /**
     * Substitui toda a coleção de listas no armazenamento.
     *
     * @param listas Nova lista completa a ser salva
     */
    public void salvarListas(List<ListaCompras> listas) {
        prefs.edit().putString(KEY_LISTAS, gson.toJson(listas)).apply();
    }

    /**
     * Adiciona uma nova lista ao armazenamento.
     *
     * @param lista Lista de compras a ser adicionada
     */
    public void adicionarLista(ListaCompras lista) {
        List<ListaCompras> listas = carregarListas();
        listas.add(lista);
        salvarListas(listas);
    }

    /**
     * Atualiza uma lista existente identificada pelo seu {@code id}.
     * Caso a lista não seja encontrada, nenhuma alteração é feita.
     *
     * @param listaAtualizada Lista com os dados atualizados
     */
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

    /**
     * Remove uma lista do armazenamento pelo seu identificador.
     *
     * @param listaId ID da lista a ser removida
     */
    public void removerLista(String listaId) {
        List<ListaCompras> listas = carregarListas();
        listas.removeIf(l -> l.getId().equals(listaId));
        salvarListas(listas);
    }

    /**
     * Busca e retorna uma lista específica pelo seu identificador.
     *
     * @param listaId ID da lista desejada
     * @return A {@link ListaCompras} encontrada, ou {@code null} se não existir
     */
    public ListaCompras buscarListaPorId(String listaId) {
        for (ListaCompras l : carregarListas()) {
            if (l.getId().equals(listaId)) return l;
        }
        return null;
    }
}

# 🛒 MarketCart — Gestão de Compras no Mercado

Aplicativo Android nativo para organizar e otimizar compras em supermercados e mercados locais. Permite criar listas de compras, adicionar produtos, controlar orçamento e acompanhar o progresso das compras em tempo real.

---

## 📱 Telas do Aplicativo

| Tela | Descrição |
|---|---|
| **Splash** | Tela de abertura com logo do app (2 segundos) |
| **Minhas Listas** | Exibe todas as listas com progresso e orçamento |
| **Criar Lista** | Formulário com nome e orçamento |
| **Editar Lista** | Formulário pré-preenchido para edição |
| **Itens da Lista** | Produtos com checkbox, total e progresso em tempo real |
| **Adicionar Item** | Formulário com nome, quantidade, preço e categoria |
| **Editar Item** | Formulário pré-preenchido para edição do item |

---

## ✅ Funcionalidades

- Criar, editar e excluir listas de compras
- Adicionar, editar e excluir itens de uma lista
- Marcar itens como comprados com atualização do total em tempo real
- Controle de orçamento com aviso visual quando ultrapassado (total em vermelho)
- Contador de progresso "X de Y itens comprados"
- Menu de contexto (toque longo) com opções de editar ou excluir
- Validações de formulário: campo obrigatório, mínimo 2 caracteres, quantidade maior que zero, preço não negativo
- Persistência local de dados: dados salvos mesmo após fechar o app
- Estado vazio com mensagem orientativa quando não há listas ou itens

---

## 🏗️ Estrutura do Projeto

```
app/src/main/
├── java/com/marketcart/app/
│   ├── activities/
│   │   ├── SplashActivity.java         # Tela de abertura
│   │   ├── MainActivity.java           # Lista de compras
│   │   ├── NovaListaActivity.java      # Formulário nova lista
│   │   ├── EditarListaActivity.java    # Formulário editar lista
│   │   ├── ItensActivity.java          # Itens de uma lista
│   │   ├── NovoItemActivity.java       # Formulário novo item
│   │   └── EditarItemActivity.java     # Formulário editar item
│   ├── adapters/
│   │   ├── ListaAdapter.java           # RecyclerView de listas
│   │   └── ItemAdapter.java            # RecyclerView de itens
│   ├── models/
│   │   ├── ListaCompras.java           # Entidade lista de compras
│   │   └── Item.java                   # Entidade item/produto
│   └── storage/
│       └── StorageManager.java         # Persistência via SharedPreferences
└── res/
    ├── layout/                          # XMLs das telas e cards
    ├── values/                          # Cores, strings e temas
    └── drawable/                        # Ícones e recursos visuais
```

---

## 🗂️ Entidades

### ListaCompras
| Atributo | Tipo | Descrição |
|---|---|---|
| id | String | Identificador único (UUID) |
| nome | String | Nome da lista |
| orcamento | double | Orçamento máximo em R$ |
| itens | List\<Item\> | Itens pertencentes à lista |

### Item
| Atributo | Tipo | Descrição |
|---|---|---|
| id | String | Identificador único (UUID) |
| nome | String | Nome do produto |
| quantidade | double | Quantidade desejada |
| unidade | String | Unidade (kg, un, L, etc.) |
| preco | double | Preço unitário em R$ |
| comprado | boolean | Se o item já foi comprado |
| categoria | String | Categoria do produto |

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 1.8 | Linguagem principal |
| Android SDK | API 24+ | Plataforma (Android 7.0+) |
| Android Studio | Última estável | IDE de desenvolvimento |
| SharedPreferences | - | Armazenamento local (LocalStorage) |
| Gson | 2.10.1 | Serialização de objetos para JSON |
| Material Design | 1.9.0 | Componentes visuais |
| RecyclerView | 1.3.0 | Listas dinâmicas e eficientes |

---

## 💾 Armazenamento de Dados

O app utiliza **SharedPreferences** como mecanismo de LocalStorage. Os objetos Java são serializados em JSON via **Gson** e armazenados em uma única chave no SharedPreferences.

```java
// Salvar
String json = gson.toJson(listas);
prefs.edit().putString("listas_compras", json).apply();

// Carregar
String json = prefs.getString("listas_compras", null);
List<ListaCompras> listas = gson.fromJson(json, tipo);
```

Os dados persistem entre sessões e são mantidos até que o usuário desinstale o app.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- [Android Studio](https://developer.android.com/studio) instalado
- JDK 17
- Android SDK API 24+

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/Lelsinj/MarketCart.git

# 2. Abra o Android Studio
# File > Open > selecione a pasta MarketCart

# 3. Aguarde o Gradle sincronizar automaticamente

# 4. Rode no emulador ou dispositivo físico
# Clique no botão ▶ Run
```

### Dispositivo físico
Ative o **Modo desenvolvedor** e a **Depuração USB** no celular, conecte via cabo e selecione o dispositivo na lista do Android Studio.

---

## 📦 Entregas

| Entrega | Descrição | Status |
|---|---|---|
| Proposta | Tema, objetivo, funcionalidades e entidades | ✅ Concluída |
| Parcial 2 | Estrutura do projeto, telas iniciais e navegação básica | ✅ Concluída |
| Parcial 3 | Funcionalidades do sistema e integração entre telas | ✅ Concluída |
| Parcial 4 | CRUD completo, validações e melhoria de interface | ✅ Concluída |
| **Final** | **Aplicativo funcional, código completo e documentação** | ✅ **Concluída** |

---

## 👨‍💻 Autor

Desenvolvido por **Leonardo** — Disciplina de Desenvolvimento Android · UTFPR

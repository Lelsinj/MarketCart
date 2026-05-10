# 🛒 MarketCart — Gestão de Compras no Mercado

Aplicativo Android nativo para organizar e otimizar compras em supermercados e mercados locais, ajudando o usuário a controlar gastos, evitar esquecimentos e gerenciar listas de compras.

---

## 📋 Sobre o Projeto

O MarketCart permite criar listas de compras, adicionar produtos com preço e quantidade, marcar itens como comprados e acompanhar o total gasto em relação ao orçamento definido. Todos os dados são armazenados localmente no dispositivo via **LocalStorage (SharedPreferences)**.

---

## 🎯 Funcionalidades

- ✅ Criar e gerenciar listas de compras
- ✅ Adicionar produtos com nome, quantidade, unidade, preço e categoria
- ✅ Marcar itens como comprados com atualização do total em tempo real
- ✅ Controle de orçamento por lista
- ✅ Histórico de listas criadas
- ✅ Organização por categorias (Hortifruti, Carnes, Bebidas, Limpeza, etc.)
- ✅ Exclusão de listas e itens com confirmação

---

## 🏗️ Estrutura do Projeto

```
app/src/main/
├── java/com/marketcart/app/
│   ├── activities/
│   │   ├── SplashActivity.java       # Tela de abertura
│   │   ├── MainActivity.java         # Lista de compras
│   │   ├── NovaListaActivity.java    # Formulário nova lista
│   │   ├── ItensActivity.java        # Itens de uma lista
│   │   └── NovoItemActivity.java     # Formulário novo item
│   ├── adapters/
│   │   ├── ListaAdapter.java         # RecyclerView de listas
│   │   └── ItemAdapter.java          # RecyclerView de itens
│   ├── models/
│   │   ├── ListaCompras.java         # Entidade lista
│   │   └── Item.java                 # Entidade item
│   └── storage/
│       └── StorageManager.java       # Acesso ao LocalStorage
└── res/
    ├── layout/                        # XMLs das telas
    ├── values/                        # Cores, strings, temas
    └── drawable/                      # Ícones e recursos visuais
```

---

## 🗂️ Entidades

| Entidade | Atributos |
|---|---|
| **ListaCompras** | id, nome, orçamento, itens |
| **Item** | id, nome, quantidade, unidade, preço, comprado, categoria |

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| Android nativo | Plataforma de desenvolvimento |
| Java | Linguagem de programação |
| Android Studio | IDE |
| SharedPreferences | Armazenamento local (LocalStorage) |
| Gson | Serialização de objetos para JSON |
| Material Design 3 | Componentes visuais |
| RecyclerView | Listas dinâmicas |

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- [Android Studio](https://developer.android.com/studio) instalado
- JDK 17
- Android SDK API 24+

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/MarketCart.git

# 2. Abra o Android Studio
# File > Open > selecione a pasta MarketCart

# 3. Aguarde o Gradle sincronizar

# 4. Rode no emulador ou dispositivo físico
# clique no botão ▶ Run
```

---

## 📱 Telas

| Tela | Descrição |
|---|---|
| **Splash** | Tela de abertura com logo do app |
| **Minhas Listas** | Lista todas as compras criadas |
| **Criar Lista** | Formulário com nome e orçamento |
| **Itens da Lista** | Produtos com checkbox e total |
| **Adicionar Item** | Formulário com nome, qtd, preço e categoria |

---

## 📁 Entrega

| Entrega                                                                                           | Status |
|---------------------------------------------------------------------------------------------------|---|
| Entrega Parcial 2 — Estrutura, telas iniciais e navegação                                         | ✅ Concluída |
| Entrega Parcial 3 - CRUD, Integração entre telas                                                  | 🔄 Em desenvolvimento |
| Entrega Parcial 4 - Banco de dados, Funcionalidades completas, Melhoria de interface e Validações | 🔄 Em desenvolvimento |
| Entrega Final                                                                                     | 🔄 Em desenvolvimento |

---

## 👨‍💻 Autor

Desenvolvido por **Leonardo** como projeto da disciplina de Desenvolvimento Android.
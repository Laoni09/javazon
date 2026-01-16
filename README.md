# Javazon

API REST simples de e-commerce, construída com Spring Boot.

## Executar

### Via Maven Wrapper (Linux/macOS)

```bash
./mvnw spring-boot:run
```

### Via Maven Wrapper (Windows)

```bash
mvnw.cmd spring-boot:run
```

Por padrão, a API ficará disponível em:

- `http://localhost:8080`

---

## Estrutura principal

- Aplicação: `com.example.javazon.JavazonApplication`
- Controle de produtos: `com.example.javazon.controller.ProdutoController`
- Controle do carrinho: `com.example.javazon.controller.CarrinhoController`
- Serviços:
  - `com.example.javazon.service.ProdutoService`
  - `com.example.javazon.service.CarrinhoService`
  - `com.example.javazon.service.PedidoService`
- Repositório em memória: `com.example.javazon.repository.EstoqueRepository`
- Tratamento global de erros: `com.example.javazon.exception.GlobalExceptionHandler`

---

## Endpoints

### 1. Produtos (`/produtos`)

#### 1.1 Listar todos os produtos

- **METHOD:** `GET`
- **URL:** `/produtos`
- **Body:** _nenhum_

**Exemplo (curl):**

```bash
curl -X GET http://localhost:8080/produtos
```

**Resposta (200 – exemplo):**

```json
[
  {
    "id": 1,
    "nome": "Notebook Gamer",
    "preco": 4500.0,
    "quantidadeEmEstoque": 10
  }
]
```

---

#### 1.2 Buscar produto por ID

- **METHOD:** `GET`
- **URL:** `/produtos/{id}`

**Exemplo:**

```bash
curl -X GET http://localhost:8080/produtos/1
```

**Resposta (200 – exemplo):**

```json
{
  "id": 1,
  "nome": "Notebook Gamer",
  "preco": 4500.0,
  "quantidadeEmEstoque": 10
}
```

---

#### 1.3 Cadastrar produto físico

- **METHOD:** `POST`
- **URL:** `/produtos/fisico`
- **Content-Type:** `application/json`

**Formato do JSON:**

```json
{
  "nome": "Notebook Gamer",
  "preco": 4500.0,
  "quantidadeEmEstoque": 10,
  "peso": 2.5,
  "volume": 0.01
}
```

**Exemplo (curl):**

```bash
curl -X POST http://localhost:8080/produtos/fisico \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Notebook Gamer",
    "preco": 4500.0,
    "quantidadeEmEstoque": 10,
    "peso": 2.5,
    "volume": 0.01
  }'
```

**Resposta (200 – texto):**

```text
Produto Físico Notebook Gamer adicionado!
```

---

#### 1.4 Cadastrar produto digital

- **METHOD:** `POST`
- **URL:** `/produtos/digital`
- **Content-Type:** `application/json`

**Formato do JSON:**

```json
{
  "nome": "Curso Java",
  "preco": 199.9,
  "quantidadeEmEstoque": 100,
  "url": "https://meus-cursos.com/curso-java",
  "tamanhoDoArquivo": 1024.0
}
```

**Exemplo (curl):**

```bash
curl -X POST http://localhost:8080/produtos/digital \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Curso Java",
    "preco": 199.9,
    "quantidadeEmEstoque": 100,
    "url": "https://meus-cursos.com/curso-java",
    "tamanhoDoArquivo": 1024.0
  }'
```

**Resposta (200 – texto):**

```text
Produto Digital Curso Java adicionado!
```

---

#### 1.5 Remover produto

- **METHOD:** `DELETE`
- **URL:** `/produtos/{id}`

**Exemplo (curl):**

```bash
curl -X DELETE http://localhost:8080/produtos/1
```

**Resposta (200 – texto):**

```text
Produto removido com sucesso!
```

---

### 2. Carrinho de Compras (`/carrinho`)

#### 2.1 Listar itens do carrinho

- **METHOD:** `GET`
- **URL:** `/carrinho`

**Exemplo:**

```bash
curl -X GET http://localhost:8080/carrinho
```

**Resposta (200 – exemplo):**

```json
[
  {
    "produto": {
      "id": 1,
      "nome": "Notebook Gamer",
      "preco": 4500.0,
      "quantidadeEmEstoque": 10
    },
    "quantidade": 2
  }
]
```

---

#### 2.2 Adicionar item ao carrinho

- **METHOD:** `POST`
- **URL:** `/carrinho/adicionar/{id}`
- **Query param (opcional):**
  - `quantidade` (inteiro, padrão = `1`)
- **Body:** _nenhum_ (parâmetros apenas na URL)

**Exemplos:**

Adicionar 1 unidade (padrão):

```bash
curl -X POST "http://localhost:8080/carrinho/adicionar/1"
```

Adicionar 3 unidades:

```bash
curl -X POST "http://localhost:8080/carrinho/adicionar/1?quantidade=3"
```

**Resposta (200 – texto):**

```text
Item adicionado ao carrinho!
```

---

#### 2.3 Remover item do carrinho

- **METHOD:** `DELETE`
- **URL:** `/carrinho/{id}`

**Exemplo:**

```bash
curl -X DELETE http://localhost:8080/carrinho/1
```

**Resposta (200 – texto):**

```text
Item removido do carrinho!
```

---

#### 2.4 Finalizar compra

- **METHOD:** `POST`
- **URL:** `/carrinho/finalizar`
- **Body:** _texto simples_ com o método de pagamento (`PIX` ou `CARTAO`)
- **Content-Type:** `text/plain` ou `application/json` com string simples

> Observação: o controller recebe um `String` cru como corpo da requisição (`@RequestBody String metodoPagamento`).

**Exemplos de corpo da requisição:**

- Pagamento via PIX:

```text
PIX
```

- Pagamento via cartão:

```text
CARTAO
```

**Exemplos (curl):**

```bash
# PIX
curl -X POST http://localhost:8080/carrinho/finalizar \
  -H "Content-Type: text/plain" \
  -d "PIX"

# CARTAO
curl -X POST http://localhost:8080/carrinho/finalizar \
  -H "Content-Type: text/plain" \
  -d "CARTAO"
```

**Resposta (200 – texto – exemplo):**

```text
Compra finalizada com sucesso usando PIX
```

Se o método for inválido:

```text
Método de pagamento inválido. Use PIX ou CARTAO.
```

---

## Tratamento de Erros

Os erros são padronizados por `com.example.javazon.exception.GlobalExceptionHandler` e retornam JSON no formato:

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "Produto não encontrado no carrinho",
  "path": "/carrinho/1"
}
```

### Erros principais

- `404 NOT_FOUND` – Recurso não encontrado  
  - Ex.: produto inexistente no carrinho ou no estoque.
- `422 UNPROCESSABLE_ENTITY` – Regra de negócio violada  
  - Ex.: `EstoqueInsuficienteException` (sem estoque suficiente).
- `500 INTERNAL_SERVER_ERROR` – Erro genérico não tratado.

---

# Desafio Itaú - API de Transações

API REST para gerenciamento de transações e cálculo de estatísticas.

## Requisitos

- Java 17 ou superior
- Gradle 8.x ou superior

## Como Executar

1. Clone o repositório
2. Na pasta do projeto, execute:
   ```bash
   ./gradlew bootRun
   ```
3. A API estará disponível em `http://localhost:8080`

## Endpoints

### POST /api/transacao
Cria uma nova transação.

**Request Body:**
```json
{
    "valor": 123.45,
    "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

**Respostas:**
- 201 Created: Transação criada com sucesso
- 422 Unprocessable Entity: Transação inválida
- 400 Bad Request: JSON inválido

### DELETE /api/transacao
Remove todas as transações.

**Respostas:**
- 200 OK: Transações removidas com sucesso

### GET /api/estatistica
Retorna estatísticas das transações dos últimos 60 segundos.

**Resposta:**
```json
{
    "count": 10,
    "sum": 1234.56,
    "avg": 123.456,
    "min": 12.34,
    "max": 123.56
}
```

## Documentação da API

A documentação completa da API está disponível em:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Testes

Para executar os testes:
```bash
./gradlew test
``` 
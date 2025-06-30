# **Documentação da API de Contas**

Esta documentação detalha os endpoints para autenticação e gerenciamento de usuários.

## **Recursos Principais**
* **Autenticação**: Gerencia o login de usuários.
* **Usuário**: Operações de CRUD para os usuários.

---

## **Autenticação**

Endpoints para lidar com a autenticação de usuários.

### `POST /auth/login`

Autentica um usuário e retorna um token JWT para acesso às rotas protegidas.

**Corpo da Requisição (`AuthRequestDTO`)**:

```json
{
  "email": "user@example.com",
  "password": "yourpassword"
}
```

**Resposta de Sucesso (200 OK) (`AuthResponseDTO`)**:

```json
{
  "token": "Bearer eyJhbGciOiJIUzI1NiJ9..."
}
```

**Resposta de Erro (401 Unauthorized)**:

* Retorna uma mensagem de erro em texto plano se as credenciais forem inválidas.

---

## **Usuário**

Endpoints para criar, ler, atualizar e deletar usuários.

### `GET /user/all`

Recupera uma lista com todos os usuários cadastrados.

**Resposta de Sucesso (200 OK)**:

* Retorna um array de objetos `User`.

```json
[
  {
    "id": "c6a9b4d0-9a8c-4a1e-8f2b-3c7d1e9f0a1b",
    "username": "usuario1",
    "email": "usuario1@example.com",
    "avatarUrl": "[http://example.com/avatar1.png](http://example.com/avatar1.png)",
    "bio": "Bio do usuário 1",
    "createdAt": "2025-06-30T22:28:37.123456Z",
    "updatedAt": "2025-06-30T22:28:37.123456Z"
  },
  {
    "id": "d8b0c5e1-0b9d-5b2f-9g3c-4d8e2f0g1b2c",
    "username": "usuario2",
    "email": "usuario2@example.com",
    "avatarUrl": null,
    "bio": null,
    "createdAt": "2025-06-30T22:29:00.654321Z",
    "updatedAt": "2025-06-30T22:29:00.654321Z"
  }
]
```

### `GET /user/{id}`

Busca um usuário específico pelo seu `ID`.

**Parâmetro de URL**:

* `id` (UUID): O ID único do usuário. Ex: `c6a9b4d0-9a8c-4a1e-8f2b-3c7d1e9f0a1b`

**Resposta de Sucesso (200 OK) (`UserResponseDTO`)**:

```json
{
  "id": "c6a9b4d0-9a8c-4a1e-8f2b-3c7d1e9f0a1b",
  "username": "usuario1",
  "email": "usuario1@example.com",
  "avatarUrl": "[http://example.com/avatar1.png](http://example.com/avatar1.png)",
  "bio": "Bio do usuário 1"
}
```

### `GET /user/getEmail/{email}`

Busca um usuário específico pelo seu `email`.

**Parâmetro de URL**:

* `email` (string): O endereço de e-mail do usuário. Ex: `usuario1@example.com`

**Resposta de Sucesso (200 OK) (`UserResponseDTO`)**:

```json
{
  "id": "c6a9b4d0-9a8c-4a1e-8f2b-3c7d1e9f0a1b",
  "username": "usuario1",
  "email": "usuario1@example.com",
  "avatarUrl": "[http://example.com/avatar1.png](http://example.com/avatar1.png)",
  "bio": "Bio do usuário 1"
}
```

### `POST /user`

Cria um novo usuário no sistema.

**Corpo da Requisição (`UserRequestDTO`)**:

```json
{
  "username": "novousuario",
  "email": "novo@example.com",
  "password": "securepassword123",
  "avatarUrl": "[http://example.com/new_avatar.png](http://example.com/new_avatar.png)",
  "bio": "Olá! Sou novo aqui."
}
```

**Resposta de Sucesso (200 OK)**:

* Nenhum conteúdo no corpo da resposta.

### `PUT /user/{id}`

Atualiza as informações de um usuário existente.

**Parâmetro de URL**:

* `id` (UUID): O ID do usuário a ser atualizado.

**Corpo da Requisição (`UserUpdateReqDTO`)**:

```json
{
  "username": "usuario_atualizado",
  "email": "atualizado@example.com",
  "passwordHash": "$2a$10$...",
  "avatarUrl": "[http://example.com/updated_avatar.png](http://example.com/updated_avatar.png)",
  "bio": "Minha bio atualizada.",
  "createdAt": "2025-06-30T22:28:37.123456Z",
  "updatedAt": "2025-07-01T10:00:00.000000Z"
}
```

**Resposta de Sucesso (200 OK)**:

* Retorna o objeto `User` completo e atualizado.

### `DELETE /user/{id}`

Exclui um usuário do sistema.

**Parâmetro de URL**:

* `id` (UUID): O ID do usuário a ser excluído.

**Resposta de Sucesso (204 No Content)**:

* Nenhum conteúdo no corpo da resposta, indicando que o usuário foi removido com sucesso.

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

---

# **Documentação da API de Jogos**

Esta documentação detalha os endpoints para obtenção de dados de jogos do Rawg API e gerenciamento dos jogos favoritos do usuário.

## **Recursos Principais**
* **Catálogo**: Gerencia e obtem dados de jogos da api externa Rawg.
* **Jogos Favoritos**: Operações de CRUD para os jogos favoritos do usuário.

---

## **Catálogo**

Endpoints para lidar com o gerenciamento e obtenção de dados dos jogos da api externa Rawg.

---

### `GET /catalogo/rawg/gameById/{gameId}`

Busca um jogo específico pelo seu `ID` e retorna suas principais informações do Rawg API.

**Authorization no Header**:

* Recebe um token JWT para garantir que o usuário está logado.

**Parâmetro de URL**:

* `gameId` (Long): O ID único do jogo. Ex: `3498`

**Resposta de Sucesso (200 OK) (`GameExtraInfoAdapted`)**:

```json
{
    "id": 3498,
    "name": "Grand Theft Auto V",
    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>\n<p>Español<br />\nRockstar Games se hizo más grande desde su entrega anterior de la serie. Obtienes la construcción del mundo complicada y realista de Liberty City de GTA4 en el escenario de Los Santos, un viejo favorito de los fans, GTA San Andreas. 561 vehículos diferentes (incluidos todos los transportes que puede operar) y la cantidad aumenta con cada actualización.<br />\nNarración simultánea desde tres perspectivas únicas:<br />\nSigue a Michael, ex-criminal que vive su vida de ocio lejos del pasado, Franklin, un niño que busca un futuro mejor, y Trevor, el pasado exacto del que Michael está tratando de huir.<br />\nGTA Online proporcionará muchos desafíos adicionales incluso para los jugadores experimentados, recién llegados del modo historia. Ahora tendrás otros jugadores cerca que pueden ayudarte con la misma probabilidad que arruinar tu misión. Los jugadores pueden experimentar todas las mecánicas de GTA actualizadas a través del personaje personalizable único, y el contenido de la comunidad combinado con el sistema de nivelación tiende a mantener a todos ocupados y comprometidos.</p>",
    "released": "2013-09-17",
    "rating": 4.47,
    "ratings_count": 7139,
    "achievementsCount": 539,
    "website": "http://www.rockstargames.com/V/",
    "background_image": "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
    "platforms": [
        {
            "id": 4,
            "name": "PC"
        },
        {
            "id": 187,
            "name": "PlayStation 5"
        },
        {
            "id": 186,
            "name": "Xbox Series S/X"
        },
        {
            "id": 18,
            "name": "PlayStation 4"
        },
        {
            "id": 16,
            "name": "PlayStation 3"
        },
        {
            "id": 14,
            "name": "Xbox 360"
        },
        {
            "id": 1,
            "name": "Xbox One"
        }
    ],
    "genres": [
        {
            "id": 4,
            "name": "Action"
        }
    ]
}
```

---

### `GET /catalogo/rawg/searchGames`

Recupera um lista de jogos de acordo com as informações da pesquisa que forem passadas no body.

**Corpo da Requisição (`GameSearchRequestDTO`)**:

* Nenhum atributo do body é obrigatório, a pesquisa pode ser montada de acordo com a necessidade, podendo até mesmo passar um body vazio que retorna como padrão uma lista com 50 jogos da página 1.

```json
{
    "page": 1,
    "pageSize": 3,
    "search": "little nightmares",
    "genre": "action",
    "platform": 1,
    "ordering": "realesed",
    "stores": 1,
    "searchExact": true,
    "searchPrecise": true
}
```

**Resposta de Sucesso (200 OK) (`BaseResponseDTO<GameAdapted>`)**:

```json
{
    "count": 6.0,
    "next": "https://api.rawg.io/api/games?genres=action&key=40fafc5be329409a87eccd2be4e515df&ordering=-realesed&page=2&page_size=3&platforms=1&search=little+nightmares&search_exact=true&search_precise=true&stores=1",
    "previous": null,
    "results": [
        {
            "id": 41,
            "name": "Little Nightmares",
            "released": "2017-04-27",
            "rating": 4.04,
            "ratings_count": 1596,
            "background_image": "https://media.rawg.io/media/games/8a0/8a02f84a5916ede2f923b88d5f8217ba.jpg",
            "platforms": [
                {
                    "id": 4,
                    "name": "PC"
                },
                {
                    "id": 1,
                    "name": "Xbox One"
                },
                {
                    "id": 18,
                    "name": "PlayStation 4"
                },
                {
                    "id": 7,
                    "name": "Nintendo Switch"
                },
                {
                    "id": 3,
                    "name": "iOS"
                }
            ],
            "genres": [
                {
                    "id": 83,
                    "name": "Platformer"
                },
                {
                    "id": 3,
                    "name": "Adventure"
                },
                {
                    "id": 4,
                    "name": "Action"
                }
            ],
            "stores": [
                {
                    "id": 1,
                    "name": "Steam"
                },
                {
                    "id": 3,
                    "name": "PlayStation Store"
                },
                {
                    "id": 2,
                    "name": "Xbox Store"
                },
                {
                    "id": 4,
                    "name": "App Store"
                },
                {
                    "id": 5,
                    "name": "GOG"
                }
            ]
        },
        {
            "id": 395157,
            "name": "Little Nightmares: The Residence",
            "released": "2018-02-23",
            "rating": 4.18,
            "ratings_count": 54,
            "background_image": "https://media.rawg.io/media/screenshots/7d3/7d347a9056c97d3ecb189ff316685d5f.jpg",
            "platforms": [
                {
                    "id": 4,
                    "name": "PC"
                },
                {
                    "id": 1,
                    "name": "Xbox One"
                },
                {
                    "id": 18,
                    "name": "PlayStation 4"
                }
            ],
            "genres": [
                {
                    "id": 83,
                    "name": "Platformer"
                },
                {
                    "id": 3,
                    "name": "Adventure"
                },
                {
                    "id": 4,
                    "name": "Action"
                }
            ],
            "stores": [
                {
                    "id": 1,
                    "name": "Steam"
                },
                {
                    "id": 3,
                    "name": "PlayStation Store"
                },
                {
                    "id": 2,
                    "name": "Xbox Store"
                },
                {
                    "id": 5,
                    "name": "GOG"
                }
            ]
        },
        {
            "id": 366881,
            "name": "Little Nightmares II",
            "released": "2021-02-10",
            "rating": 4.37,
            "ratings_count": 527,
            "background_image": "https://media.rawg.io/media/games/c2a/c2a7dc4540eb79aaff7099ae691105d3.jpg",
            "platforms": [
                {
                    "id": 4,
                    "name": "PC"
                },
                {
                    "id": 187,
                    "name": "PlayStation 5"
                },
                {
                    "id": 1,
                    "name": "Xbox One"
                },
                {
                    "id": 18,
                    "name": "PlayStation 4"
                },
                {
                    "id": 186,
                    "name": "Xbox Series S/X"
                },
                {
                    "id": 7,
                    "name": "Nintendo Switch"
                }
            ],
            "genres": [
                {
                    "id": 83,
                    "name": "Platformer"
                },
                {
                    "id": 3,
                    "name": "Adventure"
                },
                {
                    "id": 4,
                    "name": "Action"
                }
            ],
            "stores": [
                {
                    "id": 1,
                    "name": "Steam"
                },
                {
                    "id": 3,
                    "name": "PlayStation Store"
                },
                {
                    "id": 2,
                    "name": "Xbox Store"
                },
                {
                    "id": 5,
                    "name": "GOG"
                },
                {
                    "id": 6,
                    "name": "Nintendo Store"
                }
            ]
        }
    ]
}
```

---

### `GET /catalogo/rawg/searchPlatforms`

Recupera um lista de plataformas de acordo com as informações da pesquisa que forem passadas no body.

**Corpo da Requisição (`BaseSearchRequestDTO`)**:

* Nenhum atributo do body é obrigatório, a pesquisa pode ser montada de acordo com a necessidade, podendo até mesmo passar um body vazio que retorna como padrão uma lista com 50 plataformas da página 1.

```json
{
    "page": 1,
    "pageSize": 5,
    "ordering": "name"
}
```

**Resposta de Sucesso (200 OK) (`BaseResponseDTO<PlatformAdapted>`)**:

```json
{
    "count": 51.0,
    "next": "https://api.rawg.io/api/platforms?key=40fafc5be329409a87eccd2be4e515df&ordering=-name&page=2&page_size=5",
    "previous": null,
    "results": [
        {
            "id": 186,
            "name": "Xbox Series S/X"
        },
        {
            "id": 1,
            "name": "Xbox One"
        },
        {
            "id": 14,
            "name": "Xbox 360"
        },
        {
            "id": 80,
            "name": "Xbox"
        },
        {
            "id": 10,
            "name": "Wii U"
        }
    ]
}
```

--- 

### `GET /catalogo/rawg/searchGenres`

Recupera um lista de generos de acordo com as informações da pesquisa que forem passadas no body.

**Corpo da Requisição (`BaseSearchRequestDTO`)**:

* Nenhum atributo do body é obrigatório, a pesquisa pode ser montada de acordo com a necessidade, podendo até mesmo passar um body vazio que retorna como padrão uma lista com os 19 generos totais da página 1.
  
```json
{
    "page": 1,
    "pageSize": 5,
    "ordering": "name"
}
```

**Resposta de Sucesso (200 OK) (`BaseResponseDTO<GenreAdapted>`)**:

```json
{
    "count": 19.0,
    "next": "https://api.rawg.io/api/genres?key=40fafc5be329409a87eccd2be4e515df&ordering=-name&page=2&page_size=5",
    "previous": null,
    "results": [
        {
            "id": 10,
            "name": "Strategy"
        },
        {
            "id": 15,
            "name": "Sports"
        },
        {
            "id": 14,
            "name": "Simulation"
        },
        {
            "id": 2,
            "name": "Shooter"
        },
        {
            "id": 5,
            "name": "RPG"
        }
    ]
}
```

--- 

### `GET /catalogo/rawg/searchStores`

Recupera um lista de lojas de acordo com as informações da pesquisa que forem passadas no body.

**Corpo da Requisição (`BaseSearchRequestDTO`)**:

* Nenhum atributo do body é obrigatório, a pesquisa pode ser montada de acordo com a necessidade, podendo até mesmo passar um body vazio que retorna como padrão uma lista com os 19 generos totais da página 1.
  
```json
{
    "page": 1,
    "pageSize": 5,
    "ordering": "name"
}

```

**Resposta de Sucesso (200 OK) (`BaseResponseDTO<StoreAdapted>`)**:

```json
{
    "count": 10.0,
    "next": "https://api.rawg.io/api/stores?key=40fafc5be329409a87eccd2be4e515df&ordering=-name&page=2&page_size=5",
    "previous": null,
    "results": [
        {
            "id": 2,
            "name": "Xbox Store"
        },
        {
            "id": 7,
            "name": "Xbox 360 Store"
        },
        {
            "id": 1,
            "name": "Steam"
        },
        {
            "id": 3,
            "name": "PlayStation Store"
        },
        {
            "id": 6,
            "name": "Nintendo Store"
        }
    ]
}
```

---

## **Jogos Favoritos**

Endpoints para criar, ler, atualizar e deletar jogos favoritos..

---

### `GET /favoriteGame/getAll`

Recupera uma lista com todos os jogos favoritos do usuário cadastrados.

**Resposta de Sucesso (200 OK)**:

* Retorna um array de objetos `FavoriteGame`.

```json
[
    {
        "id": "99b275c9-90c5-47f0-8a8a-1aed430708ff",
        "gameId": 58175,
        "userId": "4ab9c97b-7114-4ba2-ad67-0d922027c70f"
    },
    {
        "id": "bef8a1c8-0bbf-4999-a62b-24d69292d736",
        "gameId": 3498,
        "userId": "4ab9c97b-7114-4ba2-ad67-0d922027c70f"
    },
    {
        "id": "e2beeac8-d2e3-4c84-b15a-d9c941466227",
        "gameId": 41,
        "userId": "4ab9c97b-7114-4ba2-ad67-0d922027c70f"
    }
]
```

### `GET /favoriteGame/getById/{gameId}`

Busca um jogo favorito específico do usuário pelo seu `gameId`.

**Parâmetro de URL**:

* `gameId` (Long): O ID único do jogo. Ex: `3498`

**Resposta de Sucesso (200 OK) (`FavoriteGame`)**:

```json
{
    "id": "bef8a1c8-0bbf-4999-a62b-24d69292d736",
    "gameId": 3498,
    "userId": "4ab9c97b-7114-4ba2-ad67-0d922027c70f"
}
```

### `POST /favoriteGame/{gameId}`

Adiciona um novo jogo favorito do usuário no sistema.

**Parâmetro de URL**:

* `gameId` (Long): O ID único do jogo. Ex: `3498`

**Resposta de Sucesso (200 OK)**:

* Nenhum conteúdo no corpo da resposta.

### `DELETE /favoriteGame/{gameId}`

Exclui um jogo da lista de favoritos do usuário do sistema.

**Parâmetro de URL**:

* `gameId` (Long): O ID do jogo favorito a ser excluído.

**Resposta de Sucesso (204 No Content)**:

* Nenhum conteúdo no corpo da resposta, indicando que o jogo favorito foi removido com sucesso.

---

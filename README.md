# Sistema Videolocadora

Cadastros de atores, diretores, classes, titulos e itens, com backend Spring Boot (Java 17) e frontend Angular. Cliente fica fora desta entrega.

## Arquitetura

O backend segue o fluxo `controller → service → repository → banco de dados`:

- `controller`: rotas HTTP, validacao de entrada e respostas.
- `service`: operacoes de cadastro, consulta de registros existentes e transacoes.
- `repository`: persistencia com Spring Data JPA.
- `domain`: entidades e restricoes de validacao.
- `exceptions`: respostas padronizadas para validacao (400) e recurso inexistente (404).

Cada cadastro possui listagem, consulta por ID, inclusao, alteracao e exclusao.

| Recurso | Endpoint | Campos |
| --- | --- | --- |
| Ator | `/api/atores` | `id`, `nome` |
| Diretor | `/api/diretores` | `id`, `nome` |
| Classe | `/api/classes` | `id`, `nome`, `valor`, `prazoDevolucao` |

O nome e obrigatorio. Classe exige valor de pelo menos R$ 0,01 e prazo de devolucao inteiro positivo, em dias. O ID de inclusao e gerado pelo banco.

## Executar

Backend, com Java 17 e Maven:

```bash
cd backend
mvn spring-boot:run
```

A API utiliza a porta 8081 e H2 persistido em arquivo. Swagger: `http://localhost:8081/swagger-ui.html`.

Frontend, com Node compativel com Angular 20:

```bash
cd frontend
npm ci
npm start
```

Abra `http://localhost:4200`.

## Verificar

```bash
cd backend
mvn test
```

Os testes de integracao usam H2 em memoria e verificam CRUD, validacao e respostas 404 nos tres recursos.

```bash
cd frontend
npm run build
```

A retirada do cadastro de cliente nao apaga tabelas ou dados de bancos H2 preexistentes.


## Sprint 2: Controle de acervo

A imagem do planejamento exige CRUD de Diretor, Titulo e Item. Diretor foi mantido da Sprint 1.
Como a imagem nao fornece um diagrama de classes ou especificacoes de campos, foram adotados:

- Titulo: nome, ano, sinopse, categoria, um diretor, uma classe e um ou mais atores.
- Item: numero de serie unico, data de aquisicao (sem data futura), tipo de midia e um titulo.
- Midias: FITA, DVD e BLURAY.
- Cada titulo pode possuir varios exemplares (itens).
- O valor e o prazo de locacao sao definidos pela classe do titulo.

Cadastre nesta ordem: atores/diretores/classes, depois titulos e depois itens. As telas oferecem seletores para os relacionamentos.

API: `/api/titulos` e `/api/itens`, com GET/POST na colecao e GET/PUT/DELETE em `/{id}`.
Inclusao retorna 201 com Location; exclusao retorna 204. Dados invalidos retornam 400; recurso ou relacionamento inexistente, 404; numero de serie duplicado ou exclusao com vinculos, 409.
As requisicoes usam IDs (`diretorId`, `classeId`, `atoresIds`, `tituloId`); as respostas incluem os objetos relacionados.
Nao se exclui titulo com itens, nem ator, diretor ou classe usados por titulos. Remova os vinculos antes.

A documentacao em `http://localhost:8081/swagger-ui.html` inclui os recursos e os erros do acervo. Foi mantida a dependencia springdoc compativel com Spring Boot 3 do projeto; o tutorial fornecido usa uma versao antiga.
Locacao/devolucao (S#3) e login/consulta publica (S#4) permanecem para as respectivas sprints.

### Atualizar uma copia existente

Extraia o projeto completo em uma pasta nova, evitando manter fontes de Cliente de uma versao antiga. Para preservar um banco existente, copie a pasta `backend/data` com o backend parado.
No Windows, na pasta backend, use `./mvnw.cmd clean test` e depois `./mvnw.cmd spring-boot:run` (ou o caminho completo do Maven instalado).
No frontend, execute `npm ci` e `npm start` em outro terminal.

Os testes AcervoIntegrationTest verificam CRUD, relacionamentos, exclusoes protegidas, numero de serie duplicado, dados invalidos e a documentacao OpenAPI.

Validacao desta entrega: build de producao Angular e compilacao Java/testes aprovados. A execucao dos testes JUnit neste ambiente ficou bloqueada pela resolucao de dependencias do plugin Maven Surefire. Execute `./mvnw.cmd clean test` localmente para executar toda a suite.

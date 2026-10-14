# Sistema Videolocadora

Backend em Spring Boot (porta 8081) e frontend em Angular (porta 4200).

## Pre-requisitos

- Java 17 ou mais novo (`java -version`)
- Node.js 20.19+ ou 22.12+ (`node -v`)

Maven nao precisa estar instalado: o projeto usa o Maven Wrapper (`mvnw`).

## Como rodar

Abra **dois terminais**. O backend precisa estar no ar antes de usar o frontend.

### 1. Backend

```bash
cd backend
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows (cmd ou PowerShell)
```

Espere aparecer `Started LocadoraApiApplication`. Teste em http://localhost:8081/api/atores
(deve mostrar `[]`).

### 2. Frontend

```bash
cd frontend
npm install
npm start
```

Abra http://localhost:4200 no navegador.

## Problemas comuns

- **"Erro ao carregar atores. O backend esta no ar?"**: o backend nao esta rodando,
  ou ainda esta subindo. Confira o terminal do backend.
- **`./mvnw: Permission denied`** (Linux/macOS): rode `chmod +x mvnw`.
- **Porta 8081 ou 4200 ja em uso**: feche o outro programa que esta usando a porta.
- **`ng` nao encontrado**: rode `npm install` dentro da pasta `frontend` antes do `npm start`.

O console do banco H2 fica em http://localhost:8081/h2-console
(JDBC URL `jdbc:h2:file:./data/locadora`, usuario `sa`, sem senha).

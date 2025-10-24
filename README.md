# Facilite Dev Jr — README

## Resumo da aplicação

Monólito JHipster com CRUD de Funcionários, Departamentos e Endereços. O desafio é implementar a consulta de CEP via backend que integra o ViaCEP e preenche o formulário de endereço no front.

---

## Requisitos

* Java 17
* Node 18+ e npm 9+
* Docker e Docker Compose (opcional para banco)
* PostgreSQL 14+

---

## Setup rápido

### 1) Variáveis de ambiente
A configuração do acesso ao banco de dados é feita no arquivo application-dev.yml e está, por padrão, como abaixo:

```yaml
# Banco local
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:postgresql://localhost:5432/facilitedevjr
    username: postgres
    password: root
```
Você pode criar um banco com o nome "facilitedevjr" e rodar a aplicação. Altere usuário e senha no arquivo application-dev.yml de acordo com a sua necessidade.

#### Se preferir, configure as variáveis de ambiente:
```bash
# Banco local
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/hrlite
export SPRING_DATASOURCE_USERNAME=hrlite
export SPRING_DATASOURCE_PASSWORD=hrlite
```

### 2) Banco via Docker (opcional)
```bash
docker run -d --name pg-facilitedevjr -e POSTGRES_DB=facilitedevjr \
  -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root \
  -p 5432:5432 postgres:14
```

### 3) Backend
> Executar com profile 'dev'
```bash
./gradlew
./gradlew clean bootRun
```

* API: `http://localhost:8080`
* Swagger UI: `/swagger-ui/index.html`

### 4) Frontend

```bash
npm install
npm start
```

* UI: `http://localhost:9000`

---

## Referências JHipster

* Docs: [https://www.jhipster.tech](https://www.jhipster.tech)
* Stack padrão: Spring Boot, Spring Security, MapStruct, JPA, Angular
* Convenções: DTO + Mapper + ServiceImpl + Resource, paginação e filtros via JHipster

---

## Arquitetura do projeto

### Camadas

* **web**: `*Resource` (REST Controllers). Tradução de HTTP para casos de uso.
* **service**: `*Service` e `*ServiceImpl`. Orquestração, regras, integração ViaCEP.
* **service.dto**: objetos de fronteira entre web e domínio persistido.
* **service.mapper**: MapStruct entre entidades e DTOs.
* **domain**: entidades JPA.
* **repository**: Spring Data JPA.
* **client** (Angular): módulos, componentes, serviços HTTP, forms reativos.

### Entidades principais

* `Department(name, costCenter)`
* `Address(cep, street, number, complement, district, city, uf)`
* `Employee(firstName, lastName, email, phone, hireDate, salary, active, department, address)`

### Endpoints relevantes

* `GET /api/employees` — paginação padrão JHipster
* `POST /api/employees` — criação
* `GET /api/cep/{cep}` — lookup de CEP no ViaCEP via backend **(a implementar)**

---

## O que já vem pronto

* CRUD básico gerado pelo JHipster para Employee, Department e Address.
* Formulário de Employee com campos de endereço e botão “Buscar CEP” visível.
* Stubs:

  * `CepLookupResource.get(String cep)` — vazio
  * `CepLookupService.normalizeCep(String raw)` — vazio
  * `CepLookupService.lookup(String cep)` — vazio
  * `cep-lookup.service.ts.fetch(cep: string)` — vazio
  * `employee-update.component.ts.onBuscarCep()` — vazio
* Testes de esqueleto para `CepLookupServiceTest`.

---

## O que precisa ser implementado

### Backend

1. **Normalização e validação de CEP**

* `normalizeCep(String raw)`: remover não dígitos, validar 8 dígitos.
* CEP inválido → HTTP 400.

2. **Integração ViaCEP**

* `lookup(String cep)`: `https://viacep.com.br/ws/{cep}/json/`.
* `erro=true` → HTTP 404.
* Mapear:

  * `logradouro` → `street`
  * `bairro` → `district`
  * `localidade` → `city`
  * `uf` → `uf`
  * `complemento` → `complement`
* Falhas de rede ou 5xx → HTTP 502.

3. **Exposição REST**

* `GET /api/cep/{cep}` retorna `AddressDTO`.

4. **Testes**

* Happy path 200.
* 400 para CEP inválido.
* 404 para CEP inexistente.
* 502 para indisponibilidade do ViaCEP.

### Frontend

1. **Serviço de CEP**

* `cep-lookup.service.ts.fetch(cep)`: GET `/api/cep/{cep}`.

2. **Componente**

* `employee-update.component.ts.onBuscarCep()`:

  * Normalizar entrada.
  * Loading + desabilitar botão durante request.
  * Preencher `street, district, city, uf, complement`.
  * Preservar `number` já digitado.
  * Tratar erros:

    * 400: “CEP inválido”
    * 404: “CEP não encontrado”
    * 502: “Serviço de CEP indisponível”

3. **UX mínima**

* Feedback visual de carregamento.
* Toast ou mensagem inline no form.

---

## Critérios de aceite

### Funcional

* `GET /api/cep/{cep}` retorna `200` com `AddressDTO` válido para CEP existente.
* Form de Employee preenche endereço ao acionar “Buscar CEP”.
* Mensagens de erro corretas por cenário: 400, 404, 502.
* Campo `number` preservado após preenchimento automático.

### Qualidade técnica

* Validação e tratamento de exceções consistentes.
* Mapeamento ViaCEP → `AddressDTO` completo e tipado.
* Código alinhado a padrões JHipster: DTO, Mapper, ServiceImpl, Resource.

### UX

* Botão com estado de loading.
* Erros exibidos com texto claro e não intrusivo.
* Form não perde dados manuais já preenchidos.

### Diferenciais (não obrigatórios)

* Debounce no campo CEP com auto-trigger.

---

## Troubleshooting

* `Connection refused` no banco: valide `SPRING_DATASOURCE_URL` e container Postgres.
* CORS no dev: usar proxy do JHipster ou `application-dev.yml` com CORS habilitado.
* Erros de build Angular: remover `node_modules`, `npm ci`, `npm start`.

---

## Scripts úteis

```bash
# Lint front
npm run lint

# Limpeza backend
./gradlew clean

# Executar com profile dev
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

---

## Estrutura de pastas (resumo)

```
src/main/java/.../web/rest         # Resources REST
src/main/java/.../service          # Serviços e casos de uso
src/main/java/.../service/dto      # DTOs
src/main/java/.../service/mapper   # MapStruct
src/main/java/.../domain           # Entidades JPA
src/main/java/.../repository       # Repositórios
src/main/webapp/app/               # Angular app
```

Entregue a implementação e os testes. Inclua breve nota técnica no PR descrevendo decisões e trade-offs.

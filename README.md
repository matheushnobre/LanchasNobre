# 🛥️ Lanchas Nobre - API RESTful

## 📖 Visão Geral

**Lanchas Nobre** é uma API RESTful desenvolvida em Java com Spring Boot, com o objetivo de facilitar o processo de **compra e venda de passagens de lanchas** utilizadas no transporte fluvial no estado do Amazonas.

Este projeto foi desenvolvido durante as aulas da disciplina de **Programação Orientada a Objetos** do curso de **Engenharia de Software** no **IFAM - Campus Parintins**, sob orientação do professor **Ronem Lavareda**.

---

## 🧩 Tecnologias Utilizadas

- Java
- Spring Boot  
- Spring Data JPA  
- MySQL  
- Maven  
- Lombok  
- Bean Validation (Jakarta)

---

## 🏗️ Arquitetura da Aplicação

O projeto segue a arquitetura em camadas:

- `entity`
- `controller`
- `service`
- `repository`

Essa separação facilita a manutenção, escalabilidade e organização do código, aderindo fortemente aos princípios da **Programação Orientada a Objetos (POO)**.

### Princípios POO Aplicados

- **Abstração**: Interfaces e uso de `JpaRepository`
- **Encapsulamento**: Atributos privados com acesso via Getters/Setters (Lombok)
- **Herança**: Repositórios estendem `JpaRepository`
- **Polimorfismo**: Uso de interfaces para desacoplar implementações

---

## 🗄️ Banco de Dados

Utiliza-se o banco relacional **MySQL**, com estrutura baseada em **modelo entidade-relacionamento (MER)**. Cada entidade Java representa uma tabela no banco.

O diagrama a seguir demonstra a representação dos dados neste sistema.
![Diagrama Entidade-Relacionamento](https://imgur.com/a/fLQSeEx.png)

---

## 📦 Pacotes Auxiliares

- `dto`: Representações para transferência de dados  
- `exception`: Lançamento de exceções customizadas  
- `validator`: Regras de validação aplicadas antes das operações no banco

---

## 🧩 Diagrama de Classes
![Diagrama Entidade-Relacionamento](https://imgur.com/a/GAdUagN.png)

## 🔧 Funcionalidades da API

### 🗺️ Mapas Internos

Permite cadastrar, listar, visualizar e excluir mapas de assentos usados nas lanchas.

| Método | Endpoint       | Descrição                    |
|--------|----------------|------------------------------|
| POST   | `/mapas`       | Cadastrar novo mapa          |
| GET    | `/mapas`       | Listar todos os mapas        |
| GET    | `/mapas/{id}`  | Obter mapa por ID            |
| DELETE | `/mapas/{id}`  | Remover mapa (se não em uso) |

**Regras de negócio**:
- Descrição obrigatória  
- Capacidade deve ser maior que 0  
- Não permitir remoção se o mapa estiver em uso  

---

### 🛥️ Lanchas

Permite cadastrar, editar, visualizar e excluir embarcações.

| Método | Endpoint        | Descrição                     |
|--------|------------------|-------------------------------|
| POST   | `/lanchas`       | Cadastrar nova lancha         |
| GET    | `/lanchas`       | Listar todas as lanchas       |
| GET    | `/lanchas/{id}`  | Detalhar uma lancha           |
| PUT    | `/lanchas/{id}`  | Atualizar lancha              |
| DELETE | `/lanchas/{id}`  | Excluir lancha (se permitido) |

**Regras de negócio**:
- Nome da lancha deve ser único  
- Mapa interno é obrigatório  
- Não pode excluir lancha com viagens vinculadas  

---

### 🏙️ Cidades

Cadastro de cidades envolvidas nas rotas.

| Método | Endpoint         | Descrição                     |
|--------|------------------|-------------------------------|
| POST   | `/cidades`       | Cadastrar nova cidade         |
| GET    | `/cidades`       | Listar todas as cidades       |
| PUT    | `/cidades/{id}`  | Atualizar cidade              |
| DELETE | `/cidades/{id}`  | Excluir cidade (se permitido) |

**Regras de negócio**:
- Nome + estado devem ser únicos  
- Não excluir cidade vinculada a viagens  

---

### 📆 Viagens

Cadastro de viagens fluviais.

| Método | Endpoint                                                    | Descrição                     |
|--------|-------------------------------------------------------------|-------------------------------|
| POST   | `/viagens`                                                  | Cadastrar nova viagem         |
| GET    | `/viagens`                                                  | Listar todas as viagens       |
| GET    | `/viagens/{id}`                                             | Detalhar viagem por ID        |
| GET    | `/viagens/{idOrigem}/{idDestino}/{dataPartida}`             | Buscar por parâmetros         |
| GET    | `/viagens/passageiros/{id}`                                 | Listar passageiros da viagem  |
| PUT    | `/viagens/{id}`                                             | Atualizar viagem              |

**Regras de negócio**:
- Origem e destino devem ser diferentes  
- Partida deve ser futura, e chegada após a partida  
- Não excluir viagens com passagens vendidas  

---

### 👤 Usuários

Cadastro e atualização de usuários (empresas e passageiros).

| Método | Endpoint                         | Descrição                    |
|--------|----------------------------------|------------------------------|
| POST   | `/usuarios`                      | Cadastrar novo usuário       |
| GET    | `/usuarios`                      | Listar todos os usuários     |
| GET    | `/usuarios/{id}`                 | Detalhar usuário             |
| GET    | `/usuarios/listarPassagens/{id}` | Listar passagens do usuário  |
| PUT    | `/usuarios/{id}`                 | Atualizar dados do usuário   |

**Regras de negócio**:
- Nome, email, CPF e data de nascimento obrigatórios  
- CPF deve ser válido  
- Email deve ser único e imutável  
- Usuário não pode ser excluído  

---

### 🎟️ Passagens

Cadastro e consulta de passagens.

| Método | Endpoint          | Descrição                      |
|--------|-------------------|--------------------------------|
| POST   | `/passagens`      | Cadastrar nova passagem        |
| GET    | `/passagens/{id}` | Selecionar passagem por ID     |

**Regras de negócio**:
- Não vender passagens para assentos já ocupados  
- Não vender passagens para viagens passadas ou em andamento  
- Status de pagamento iniciado com `PROCESSAMENTO`  

---

## 🧪 Testes e Validações

- Validação de CPF com algoritmo oficial  
- Verificação de campos obrigatórios com mensagens amigáveis  
- Respostas com códigos HTTP apropriados (`200`, `201`, `404`, `409`, `422`)  

---

## 📬 Contato

Projeto acadêmico — **IFAM - Campus Parintins**  
Desenvolvido por **Matheus Nobre**  
Orientação: Prof. **Ronem Lavareda**

---

## 📌 Funcionalidades Futuras

1. Adicionar suporte para pagamento via PIX  
2. Melhorar atualizações de viagens, permitindo alterar a lancha de viagens com passagens vinculadas (com realocação de assentos)  
3. Adicionar autenticação à API, oferecendo maior segurança  
4. Melhorar campo de busca por viagens, permitindo conexões usando algoritmos de grafos  
5. Desenvolver o front-end da aplicação  

---

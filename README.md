# 🛥️ Lanchas Nobre - API RESTful

## 📖 Visão Geral

**Lanchas Nobre** é uma API RESTful desenvolvida em Java com Spring Boot, com o objetivo de facilitar o processo de **compra e venda de passagens de lanchas** utilizadas no transporte fluvial no estado do Amazonas.

Este projeto foi desenvolvido durante as aulas da disciplina de **Programação Orientada a Objetos** do curso de **Engenharia de Software** no **IFAM - Campus Parintins**, sob orientação do professor **Ronem Lavareda**.

---

## 🧩 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Lombok
- Bean Validation (Jakarta)

---

## 🏗️ Arquitetura da Aplicação

O projeto segue a arquitetura em camadas:
controller
service
repository
entity
dto
exception
validator


Essa separação facilita a manutenção, escalabilidade e organização do código, aderindo fortemente aos princípios da **Programação Orientada a Objetos (POO)**.

### Princípios POO Aplicados

- **Abstração**: Interfaces e uso de `JpaRepository`.
- **Encapsulamento**: Atributos privados com acesso via Getters/Setters (Lombok).
- **Herança**: Repositórios estendem `JpaRepository`.
- **Polimorfismo**: Uso de interfaces para desacoplar implementações.

---

## 🗄️ Banco de Dados

Utiliza-se o banco relacional **MySQL**, com estrutura baseada em **modelo entidade-relacionamento (MER)**. Cada entidade Java representa uma tabela no banco.

---

## 📦 Pacotes Auxiliares

- **dto**: Representações para transferência de dados.
- **exception**: Lançamento de exceções customizadas.
- **validator**: Regras de validação aplicadas antes das operações no banco.

---

## 🔧 Funcionalidades da API

Abaixo, as principais entidades disponíveis e seus contratos REST:

### 📌 Mapas Internos

Permite cadastrar, listar, visualizar e excluir mapas de assentos usados nas lanchas.

| Método | Endpoint           | Descrição                       |
|--------|--------------------|---------------------------------|
| POST   | `/mapas`           | Cadastrar novo mapa             |
| GET    | `/mapas`           | Listar todos os mapas           |
| GET    | `/mapas/{id}`      | Obter mapa por ID               |
| DELETE | `/mapas/{id}`      | Remover mapa (se não em uso)    |

Regras de negócio:
- Descrição obrigatória.
- Capacidade > 0.
- Não permitir duplicação nem remoção se estiver em uso.

---

### 🛥️ Lanchas

Permite cadastrar, editar, visualizar e excluir embarcações.

| Método | Endpoint           | Descrição                       |
|--------|--------------------|---------------------------------|
| POST   | `/lanchas`         | Cadastrar nova lancha           |
| GET    | `/lanchas`         | Listar todas as lanchas         |
| GET    | `/lanchas/{id}`    | Detalhar uma lancha             |
| PUT    | `/lanchas/{id}`    | Atualizar lancha                |
| DELETE | `/lanchas/{id}`    | Excluir lancha (se permitido)   |

Regras de negócio:
- Nome único.
- Mapa interno obrigatório.
- Não excluir se estiver em uso por uma viagem.

---

### 🏙️ Cidades

Cadastro de cidades envolvidas nas rotas.

| Método | Endpoint           | Descrição                       |
|--------|--------------------|---------------------------------|
| POST   | `/cidades`         | Cadastrar nova cidade           |
| GET    | `/cidades`         | Listar todas as cidades         |
| PUT    | `/cidades/{id}`    | Atualizar cidade                |
| DELETE | `/cidades/{id}`    | Excluir cidade (se permitido)   |

Regras de negócio:
- Nome + estado devem ser únicos.
- Cidades não podem ser removidas se vinculadas a viagens.

---

### 📆 Viagens

Cadastro de viagens fluviais.

| Método | Endpoint                                                        | Descrição                           |
|--------|------------------------------------------------------------------|-------------------------------------|
| POST   | `/viagens`                                                      | Cadastrar nova viagem               |
| GET    | `/viagens`                                                      | Listar todas as viagens             |
| GET    | `/viagens/{id}`                                                 | Detalhar viagem por ID              |
| GET    | `/viagens/{idOrigem}/{idDestino}/{dataPartida}`                 | Buscar por parâmetros               |
| GET    | `/viagens/passageiros/{id}`                                     | Listar passageiros da viagem        |
| PUT    | `/viagens/{id}`                                                 | Atualizar viagem (se permitido)     |

Regras de negócio:
- Origem e destino não podem ser iguais.
- Datas devem ser válidas (partida futura, chegada após partida).
- Viagens com passagens vendidas não podem ser removidas.

---
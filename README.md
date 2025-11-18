# 🚖 Sistema de Transporte – Trabalho Final (Laboratório de Banco de Dados)

Este repositório contém o sistema completo desenvolvido para o Trabalho Final da disciplina **Laboratório de Banco de Dados**, envolvendo:

- Banco de Dados Relacional (MySQL)
- Banco de Dados NoSQL (MongoDB)
- Backend
- Frontend
- Documento técnico em formato de artigo acadêmico
- DER (MySQL Workbench)

---

## 📁 1. Estrutura do Repositório

```md
📦 transporte-sistema
 ┣ 📁 transporte-backend/        → API em Java/Spring Boot
 ┣ 📁 front-end de transporte/   → Interface web (HTML/JS/CSS)
 ┣ 📄 Documento Técnico.pdf       → Artigo científico final
 ┣ 📄 DER-Transportes.mwb         → Modelo EER do MySQL Workbench
 ┣ 📄 transporte.sql              → Script SQL completo (MySQL)
 ┣ 📄 transporte_nosql.js         → Script MongoDB (NoSQL)
 ┗ 📄 README.md
```

---

## 🔧 2. Tecnologias Utilizadas

### **Backend**
- Java 17  
- Spring Boot  
- Maven  
- REST API  

### **Frontend**
- HTML  
- CSS  
- JavaScript 

### **Banco Relacional**
- MySQL 8  
- MySQL Workbench (modelagem e DER)

### **Banco NoSQL**
- MongoDB

---

## 🗄️ 3. Banco de Dados

### **MySQL**
Inclui:
- Tabelas com relacionamentos  
- Índices  
- Triggers  
- Views  
- Function para geração de IDs  
- Procedures  
- Controle de acesso por usuários  
- Proibição de uso do root  

Script completo:
transporte.sql

### **MongoDB**
Utilizado para:
- Avaliações de motoristas

Script:
transporte_nosql.js


---

## ▶️ 4. Como Executar o Projeto

### **1. Configurar o MySQL**
- Crie o banco `transporte`  
- Execute o script:


transporte.sql


### **2. Configurar o MongoDB**
Execute:


mongo < transporte_nosql.js


### **3. Executar o Backend**
No diretório `transporte-backend`:


mvn spring-boot:run

API disponível em:


http://localhost:8080


### **4. Executar o Frontend**
Abra:


front-end de transporte/index.html

Ou use Live Server.

---

## 🔐 5. Login e Controle de Acesso

Níveis disponíveis:
- **Administrador (ADM)** – acesso total  
- **Operador/Usuário** – acessos limitados  

Credenciais de exemplo estão no documento técnico e nos scripts.

---

## 📄 6. Documento Técnico

Arquivo:


Documento Técnico.pdf


Inclui:
- Modelagem  
- DER  
- Tecnologias  
- SQL e NoSQL  
- Metodologia  
- Justificativas  
- Controle de acesso  

---

## 🧪 7. Testes Recomendados

- Login  
- Cadastro de veículo  
- Cadastro de motorista  
- Cadastro de rota  
- Criação de viagem  
- Associação de passageiros  
- Finalização de viagem  
- Avaliação de motorista (MongoDB)

---

## 🧑‍🏫 8. Autores

- Ana Beatriz  
- David Cordeiro  
- Eduarda Alves  
- Guilherme Andrade  
- Ícaro de Oliveira  

---

## 📬 9. Professor

**Jefferson Salomão Rodrigues**

---

## 🎯 10. Licença

Projeto acadêmico — uso exclusivo para fins de avaliação.

# Tech Challenge - Fase 3

## 1. Introdução 

### Descrição do Problema
Em um ambiente hospitalar, é essencial contar com sistemas que garantam o agendamento eficaz de consultas, o gerenciamento do
histórico de pacientes e o envio de lembretes automáticos para garantir a presença dos pacientes nas consultas. Este sistema deve
ser acessível a diferentes tipos de usuários (médicos, enfermeiros e pacientes), com acesso controlado e funcionalidades específicas
para cada perfil.

### Objetivo

O objetivo é desenvolver um backend simplificado e modular, com foco em segurança e comunicação assíncrona, garantindo que o
sistema seja escalável, seguro e que utilize boas práticas de autenticação, autorização e comunicação entre serviços.

### Clean Architecture

Autenticação com Spring Security: implementar autenticação básica para garantir que cada tipo de usuário tenha acesso controlado às funcionalidades. ​

Níveis de Acesso:​
- Médicos: podem visualizar e editar o histórico de consultas.  ​
- Enfermeiros: podem registrar consultas e acessar o histórico.​
- Pacientes: podem visualizar apenas as suas consultas..

## Consultas e Histórico do Paciente

Implementação de GraphQL: permitir consultas flexíveis sobre o histórico médico, como listar todos os atendimentos de um paciente ou apenas as futuras.​

Serviço de Agendamento: médicos e enfermeiros poderão registrar novas consultas e modificar consultas existente.


## Separação dos Serviços

Serviço de Agendamento: responsável pela criação e edição das consultas. ​
Serviço de notificações: envia lembretes automáticos aos pacientes sobre consultas futuras. ​
Serviço de histórico (opcional): armazena o histórico de consultas e disponibiliza dados via GraphQL. ​


A seguir, apresento a implementação de um backend simplificado para o sistema hospitalar. Dividi o sistema em dois serviços principais:​
​

Serviço de Agendamento: Responsável por autenticação, agendamento de consultas, gerenciamento de histórico via GraphQL, e envio de mensagens assíncronas para o serviço de notificações.​

Serviço de Notificações: Recebe mensagens assíncronas via RabbitMQ e simula o envio de lembretes (por exemplo, via log ou e-mail fictício).



## 3. Descrição dos Endpoints

Esta API fornece um conjunto de endpoints RESTful para gerenciar usuários, restaurantes, itens de menu e papéis (roles) dentro do sistema.<br>
Todos os endpoints estão hospedados em http://localhost:8080/api/v1/ e assumem um corpo de requisição/resposta em JSON, quando aplicável.

## Configuração do Ambiente

Para a configuração do ambiente, foi utilizado o container Docker do RabbitMQ para gerenciamento das trocas de mensagens entre os serviços. Segue abaixo o comando para criação do container:

```bash
docker run -d --name myRabbit -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=123456 -p 5672:5672 -p 8080:15672 rabbitmq:3-manageme
```

## Documentação da API

Para a documentação da API, foi utilizado o Swagger, que permite a visualização e teste dos endpoints de forma interativa. A documentação pode ser acessada através da URL: ​
http://localhost:8081/swagger-ui/index.html

### Descrição do Endpoint

Endpoint de Agendamento.

- Criar Nova Consulta
  - Método: POST
  - URL: /appointments
  - Descrição: Cria uma novo agendamento de consulta.
  - Authorization: Tipo> Basic Auth; Username: medico, enfermeiro; Password: password
  
```json
{
    "patientName": "paciente",
    "doctorName": "medico",
    "dateTime": "2025-10-07T10:00:00",
    "description": "Consulta de rotina",
    "status": "SCHEDULED"
}
```

- Atualizar Consulta
  - Método: POST
  - URL: /appointments/{id}
  - Descrição: Atualiza consulta agendada.
  - Authorization: Tipo> Basic Auth; Username: medico; Password: password
```json
{
    "patientName": "paciente",
    "doctorName": "medico",
    "dateTime": "2025-10-07T11:00:00",
    "description": "Consulta de rotina - Atualizada",
    "status": "SCHEDULED"
}
```

- Listar Consultas
  - Método: GET
  - URL: /appointments/patient/{name}?futureOnly=true
  - Descrição: Cria uma nova conta de usuário.
  - Authorization: Tipo> Basic Auth; Username: medico, enfermeiro, paciente; Password: password

## 8. Collections do Postman

A coleção de testes no postman está disponível na pasta ./postman
[postman_collection](.postman/TechChallenge3.postman_collection.json)

## 9. Repositório do Código
O código-fonte do sistema está disponível no repositório do GitHub: [ams-app](https://github.com/EduardoAguiarDeAraujo/eaa-postech-ams-app)

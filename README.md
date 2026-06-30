# ⚽ GolUp
 
**GolUp** é um aplicativo de e-commerce com a temática da Copa do Mundo, construído sobre uma arquitetura de **microsserviços** em Java/Spring Boot, orquestrados via Docker.
 
## 🏗️ Arquitetura
 
O projeto é dividido em múltiplos serviços independentes, que se comunicam entre si através de um API Gateway e um serviço de descoberta (Service Discovery):
 
| Serviço | Descrição |
|---|---|
| `gateway-service` | Porta de entrada única da aplicação (API Gateway), roteando as requisições para os demais microsserviços |
| `discovery-service` | Service Registry (Eureka) responsável pelo registro e descoberta dos microsserviços |
| `auth-service` | Responsável pela autenticação e autorização de usuários |
| `product-service` | Gerenciamento do catálogo de produtos |
| `order-service` | Gerenciamento de pedidos |
| `currency-service` | Conversão/cotação de moedas para produtos |
| `greeting-service` | Serviço de exemplo/boas-vindas |
| `config-service` | Servidor de configuração centralizada (Spring Cloud Config) |
| `configs` | Arquivos de configuração compartilhados entre os serviços |
 
## 🛠️ Tecnologias
 
- **Java** com **Spring Boot**
- **Spring Cloud Gateway** — roteamento das requisições
- **Spring Cloud Netflix Eureka** — descoberta de serviços
- **Spring Cloud Config** — configuração centralizada
- **Docker / Docker Compose** — orquestração dos containers
- **WebSocket** — suporte a comunicação em tempo real (rotas `/ws/**`)
## 🚀 Como executar
 
### Pré-requisitos
- Docker e Docker Compose instalados
- Java 17+ (caso queira rodar algum serviço fora do container)
### Subindo o projeto
 
```bash
docker-compose up --build
```
 
Isso irá subir todos os microsserviços, incluindo o `discovery-service` e o `gateway-service`, que centraliza todo o tráfego da aplicação.
 
## 🌐 Rotas do Gateway
 
Todas as requisições passam pelo `gateway-service`, que redireciona para o microsserviço correspondente:
 
| Rota | Destino |
|---|---|
| `/get` | `httpbin.org` (rota de teste) |
| `/products/**` | `product-service` |
| `/ws/products/**` | `product-service` (WebSocket) |
| `/currency/**` | `currency-service` |
| `/ws/currency/**` | `currency-service` (WebSocket) |
| `/auth/**` | `auth-service` |
| `/ws/orders/**` | `order-service` (WebSocket) |
 
## 📂 Estrutura do projeto
 
```
GolUp/
├── auth-service/
├── config-service/
├── configs/
├── currency-service/
├── discovery-service/
├── gateway-service/
├── greeting-service/
├── order-service/
├── product-service/
├── docker-compose.yml
└── README.md
```


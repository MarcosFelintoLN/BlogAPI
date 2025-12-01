# Blog API

API simples de Blog desenvolvida com **Spring Boot**, documentada com **Swagger**, utilizando **PostgreSQL** e completamente **dockerizada**.

---

## 1. Configuração do ambiente local
### 1.1 Instalação das dependências
Para começar a configurar o ambiente, siga os passos abaixo:
```bash
git clone https://github.com/MarcosFelintoLN/BlogAPI.git
```
```
cd BlogAPI
```

## 2. Iniciando os Containers
### 2.1 Execute o comando abaixo para criar os containers:
```
docker-compose --build 
```
### 2.2 Execute o comando abaixo para rodar:
```
docker-compose up -d
```
### 2.3 Execute o comando abaixo para parar os containers:
```
docker-compose down
```

## 3.Aplicação disponível em:
```
http:localhost:8080
```

## 4. Swagger
para acessar o swagger, rode o projeto como indica o item 2.2 e digite a url abaixo:
```
http://localhost:8080/swagger-ui/index.html
```




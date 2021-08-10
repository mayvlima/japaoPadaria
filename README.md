# Trabalho de Conclusão de Curso Tech Bank

  API REST que possibilita o gerenciamento da lista de produtos favoritos dos clientes. 
    
## Sobre o projeto
  
API REST que auxilie na administração de uma padaria. Essa api controla o estoque e as vendas.
Na padaria temos produtos fabricados diariamente e produtos para revenda.
Para os produtos que são fabricados, tiramos a quantidade relativa a receita do produto,
no momento em que for fabricado. Em relação aos produtos revendidos, devemos tirar do estoque no momento da venda.
                   
A arquitetura do projeto é formada por EndPoints que podem ser usados por uma aplicação FrontEnd.
                    
### Tecnologias utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias:
* Java 8
* Spring Framework
* Banco de dados relacional Postgres
* Documentação no Postman: https://documenter.getpostman.com/view/11575875/Tzz4QKJh

## Começando

Siga as instruções para executar a API.

### Instalando

* Esse é um projeto Maven, assegure-se que tem o Maven instalado no seu sistema

1. Clone o repositório
   ```sh
   git clone https://github.com/mayvlima/japaoPadaria.git
   ```  
2. Excute o projeto por qualquer IDE
   ```sh
   run
   ```   
3. Criei o application.properties e utilize a configuração do banco de dados que desejar

4. Instale as dependências 
    ```sh
   mvn clean install
   ```   
5. Rode o projeto
   ```sh
   mvn spring-boot:run
   ```
7. Acesse os endpoints 
   ```sh
   localhost:8080/
   ```

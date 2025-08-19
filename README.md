# ForumHub

🚀 Tecnologias utilizadas

Java 17+
Spring Boot
Maven
Spring Web
Spring Data JPA
Spring Security
Flyway Migration
MySQL Driver
Validation
Lombok
JWT (JSON Web Token)

📂 Estrutura do projeto

src/main/java/com/forumhub
│── controller       → Controllers REST
│── dto              → Objetos de transferência de dados (DTOs)
│── model            → Entidades do banco
│── repository       → Interfaces JPA Repository
│── service          → Regras de negócio e serviços
│── security         → Configurações e filtros de segurança

⚙️ Configuração do Banco de Dados

No arquivo application.properties, configure o acesso ao seu banco MySQL:
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# JWT
jwt.secret=MINHA_CHAVE_SECRETA
jwt.expiration=86400000

📌 Endpoints da API
🔑 Autenticação

POST /login → Gera o token JWT.
Body exemplo:
{
  "login": "usuario",
  "senha": "123456"
}

📝 Tópicos

POST /topicos → Criar um novo tópico
{
  "titulo": "Erro no Spring",
  "mensagem": "Estou com erro ao configurar o Spring Security",
  "autor": "João Silva",
  "curso": "Spring Boot Avançado"
}

GET /topicos → Listar todos os tópicos
GET /topicos/{id} → Buscar tópico por ID
PUT /topicos/{id} → Atualizar um tópico existente
DELETE /topicos/{id} → Deletar um tópico

🛡️ Regras de Negócio

Todos os campos são obrigatórios.
Não é permitido cadastrar tópicos duplicados (mesmo título e mensagem).
Apenas usuários autenticados podem acessar os endpoints.
Respostas seguem o padrão JSON.

🧪 Testando a API

Você pode testar a API com ferramentas como Postman ou Insomnia:
Realizar login em /login para obter o token JWT.
Enviar o token no header Authorization das próximas requisições: Authorization: Bearer SEU_TOKEN_AQUI

▶️ Executando o projeto

Clonar o repositório:
git clone https://github.com/seu-usuario/forumhub.git


Entrar no diretório do projeto:
cd forumhub


Executar a aplicação:
mvn spring-boot:run

Acessar a API em:
http://localhost:8080

📖 Opcionais Implementados (se aplicável)

Paginação e ordenação na listagem de tópicos.
Busca por curso e ano de Exibir apenas os 10 primeiros resultados em ordem de criação.

👩‍💻 Autor

Projeto desenvolvido para prática de Spring Boot, APIs REST, JPA, autenticação JWT e boas práticas de desenvolvimento backend.

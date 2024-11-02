## Projeto para estudo de Microsserviços e mensageria

### Sistema:
Se trata de um sistema de empréstimos, onde um usuário pode fazer propostas de empréstimo através de uma interface front-end (consumida via container no Docker).

Os dados da proposta são enviados a um microsserviço de notificação, a partir do qual são enviadas mensagens de texto atualizando o usuário através do AWS SNS. 

A proposta também é enviada ao microsserviço de análise de crédito, onde os dados inseridos serão analisados para que seja concedido ou não o empréstimo.

### Principais tecnologias utilizadas: 
- Spring Boot (Java 17)
- Docker
- Mapstruct
- RabbitMQ
- AWS SNS
- PostgreSQL
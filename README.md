# Challenge 2: Register of Compass Scholarship Program classes

The goal in this technical challenge is to develop a RESTful API, which is capable of handling the basic operations of the four HTTP verbs: GET, POST, PUT and DELETE. For the development of the API, the **Java** programming language will be used in conjunction with the **Spring Boot** framework and the **MySQL** database. Additionally, other relevant dependencies will be incorporated for specific purposes, such as testing and documentation, ensuring a comprehensive solution that adheres to RESTful standards.
## Implemented features

* CRUD (Create, Read, Update, Delete, Patch)  
* OpenAPI (using Swagger)  
* ProblemDetails [(RFC 7807)](https://datatracker.ietf.org/doc/html/rfc7807)  
* HATEOAS  
* Unit Tests (JUnit 5, Mockito)  
* Integration Tests (MockMvc)  

## Modeling
### Class diagram
![Class diagram](https://raw.githubusercontent.com/kia735/Challenge_2/main/docs/class_diagram.png)

### Database diagram
![Database diagram](https://raw.githubusercontent.com/kia735/Challenge_2/main/docs/database_diagram.png)

## API documentation
To access the API documentation, it is necessary to start the application and then access the specified endpoint: **/swagger-ui/index.html.** This route will provide an interactive interface that details all relevant information about the API, including available endpoints, accepted parameters, and response formats.

## Updating Features
### Flyway
With Flyway framework, this application guarantees an evolution of database schemas in an automated and controlled way and promote application versioning during development.

### CRUD Implementation with Records
Due to the ease and safety of using records within Java applications, it is possible to use records as DTOs to transfer data between layers. 

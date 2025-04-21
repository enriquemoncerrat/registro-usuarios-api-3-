# API Registro de Usuarios

## Tecnologías
- Java 11
- Spring Boot
- JPA + H2
- JWT
- Maven
- Swagger UI

## Cómo probar

```bash
mvn clean install
mvn spring-boot:run
```

Abrí en el navegador:

```
http://localhost:8080/swagger-ui.html
```

## Endpoint principal

POST `/api/users`

```json
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}
```

## Respuesta de éxito

```json
{
  "id": "UUID",
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "token": "JWT...",
  "isActive": true,
  "created": "...",
  "modified": "...",
  "lastLogin": "...",
  "phones": [...]
}
```

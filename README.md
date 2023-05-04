# The Ethereum Fetcher - REST Server

# Description

This is a REST API server that returns information for certain Ethereum transactions identified by their transaction
hashes.

# Design overview

The application uses a classic 3-layer structure - controller, service and repository.
Using this approach we utilize separation of concerns.

* We have controller layer that handles HTTP requests
* We have service layer that performs business logic
* We have a repository layer that interacts with the database

# How to run the server

Maven is used as a build tool for the application, so the way to run is by executing the following terminal command:

`mvnw spring-boot:run` (maven-wrapper binary is included in the project for convenience)

Or, the other way, via docker-compose:

`docker-compose up --build`

The database used is PostgreSQL, all required schemas and tables will be created upon running the app,
so nothing more than installed and running postgre server is required (on port 5432).

If docker-compose is used to start the server and the database, postgre is not required locally

# Security
The server is secured via JWT and an access token must be present in the headers
of every http request.
Users are inserted upon starting automatically for convenience.
One can authenticate themselves when sending a POST request to the folowing endpoint:
### Endpoint: `/lime/authenticate`
Using a JSON request body with basic credentials:
```jsx
{
  "username": "",
  "password": ""
}
```
All the following pairs will work:

`admin-admin`

`alice-alice`

`bob-bob`

`carol-carol`

`dave-dave`

After request returns the generated access token, it can be passed onwards
on each request as an `Authorization Bearer` header

# Swagger-UI
OpenAPI is used to build an api documentation with possibility to test each endpoint. When server is started that is available at:

`http://localhost:9000/swagger-ui/index.html`
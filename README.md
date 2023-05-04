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

`mvnw spring-boot:run`

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

# Endpoints and examples

### Endpoint: `/lime/eth/:rlphex`

Request

```jsx
curl -X GET http://127.0.0.1:9000/lime/eth/f90110b842307839623266366133633265316165643263636366393262613636366332326430353361643064386135646137616131666435343737646364363537376234353234b842307835613537653330353163623932653264343832353135623037653762336431383531373232613734363534363537626436346131346333396361336639636632b842307837316239653262343464343034393863303861363239383866616337373664306561633062356239363133633337663966366639613462383838613862303537b842307863356639366266316235346433333134343235643233373962643737643765643465363434663763366538343961373438333230323862333238643464373938
```

Response

```jsx
{
  "transactions": [
    {
      "transactionHash": "0x5a57e3051cb92e2d482515b07e7b3d1851722a74654657bd64a14c39ca3f9cf2",
      "transactionStatus": "0x1",
      "blockHash": "0x92557f7e29c39cae6be013ffc817620fcd5233b68405cdfc6e0b5528261e81e5",
      "blockNumber": "0x79b5b5",
      "from": "0xf29a6c0f8ee500dc87d0d4eb8b26a6fac7a76767",
      "to": "0xb0428bf0d49eb5c2239a815b43e59e124b84e303",
      "contractAddress": null,
      "logsCount": 55,
      "input": "0x",
      "value": "0xb1a2bc2ec50000"
    },
    ...
  ]
}
```

### Endpoint: `/lime/all`

Request

```jsx
curl -X GET http://127.0.0.1:{PORT}/lime/all
```

Response

```jsx
{
  "transactions": [
    {
      "transactionHash": "0x5a57e3051cb92e2d482515b07e7b3d1851722a74654657bd64a14c39ca3f9cf2",
      "transactionStatus": "0x1",
      "blockHash": "0x92557f7e29c39cae6be013ffc817620fcd5233b68405cdfc6e0b5528261e81e5",
      "blockNumber": "0x79b5b5",
      "from": "0xf29a6c0f8ee500dc87d0d4eb8b26a6fac7a76767",
      "to": "0xb0428bf0d49eb5c2239a815b43e59e124b84e303",
      "contractAddress": null,
      "logsCount": 55,
      "input": "0x",
      "value": "0xb1a2bc2ec50000"
    },
    ...
  ]
}
```
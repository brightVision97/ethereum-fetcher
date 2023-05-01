# The Ethereum Fetcher - REST Server

# Description

This is a REST API server that returns information for certain Ethereum transactions identified by their transaction hashes.

# Design overview

The application uses a classic 3-layer structure - controller, service and repository.
Using this approach we utilize separation of concerns.
* We have controller layer that handles HTTP requests
* We have service layer that performs business logic
* We have a repository layer that interacts with the database

# How to run the server

Maven is used as a build tool for the application, so the way to run is by executing the following terminal command:

`mvnw spring-boot:run`

The database used is PostgreSQL, all required schemas and tables will be created upon running the app, 
so nothing more than installed and running postgre server is required (on port 5432). 

# Endpoints and examples
### Endpoint: `/lime/eth/:rlphex`

Request
```jsx
curl -X GET http://127.0.0.1:{PORT}/lime/eth/f90110b842307839623266366133633265316165643263636366393262613636366332326430353361643064386135646137616131666435343737646364363537376234353234b842307835613537653330353163623932653264343832353135623037653762336431383531373232613734363534363537626436346131346333396361336639636632b842307837316239653262343464343034393863303861363239383866616337373664306561633062356239363133633337663966366639613462383838613862303537b842307863356639366266316235346433333134343235643233373962643737643765643465363434663763366538343961373438333230323862333238643464373938
```

Response
```jsx
{
  "transactions": [
    {
      "blockHash": "0x92557f7e29c39cae6be013ffc817620fcd5233b68405cdfc6e0b5528261e81e5",
      "blockNumber": "0x79b5b5",
      "chainId": "0x5",
      "from": "0xf29a6c0f8ee500dc87d0d4eb8b26a6fac7a76767",
      "to": "0xb0428bf0d49eb5c2239a815b43e59e124b84e303",
      "gas": "0x5208",
      "gasPrice": "0xaf0b0a9d4",
      "hash": "0x5a57e3051cb92e2d482515b07e7b3d1851722a74654657bd64a14c39ca3f9cf2",
      "input": "0x",
      "maxFeePerGas": "0xc2152614e",
      "maxPriorityFeePerGas": "0x558d55f2",
      "nonce": "0x289a5",
      "r": "0xaa92362727b8bdf167cd4df731cefea0b057e1fe9254652648966b468462205c",
      "s": "0x25fe2e6e0fac194678f79d6abf574c1260e5eba11caac4e6b731568c0e7d9b55",
      "transactionIndex": "0x19",
      "type": "0x2",
      "v": "0x0",
      "value": "0xb1a2bc2ec50000"
    },
    {
      "blockHash": "0x32edca7a39d0b1fc3d19fd1487c3c69beadad7cdcd5e5f1c9e815e7d1c460a0d",
      "blockNumber": "0x796b79",
      "chainId": "0x5",
      "from": "0x22ba753ca065d65d4d0b9f4fac7a669746175199",
      "to": "0x14cb06e8de2222912138f9a062e5a4d9f4821409",
      "gas": "0x4f533",
      "gasPrice": "0xcf3d698b1",
      "hash": "0x71b9e2b44d40498c08a62988fac776d0eac0b5b9613c37f9f6f9a4b888a8b057",
      "input": "0x95297e24a8d61b73377cdc07fcf0cdd5473a1c81d541d3bcbbac29dd02d9f680af901d705591dba920dde33e5f1043c1b84106b0f223e7b954b17bde9ffe62206b583b2d00000000000000000000000000000000000000000000000000000000000021d300eea48906338871c59f0d12348b85c66461cd8c9e80faa4d3e63b134279595a159d3bfa16088686ea5b2406f82109b60a5792b77bc173106c01f0fbfed6598905d63c4ca36d0740e427d53ea8d1cc707b15a846c854a14b2e3b2e30ce129b8721a54e650d0e077cf260d8c3c84a431b287bd35ffe4c03c27a19d9a0d3320ae905a76cdd5a8bfeffa1c837279d67654e053a8e80cf2e581968a93bf827c3cf702d8c881054165ebd6d1ebea052f9af3a10338c9314ed99609735b8b76fe274c411d32840d8a1f85b51ee84bd2b0d70fe5725362406ac200a1186ea82ae39731a05d84408b5eca5130fa799aa898bbb2132054dcd8890ff004ac855f57c813fc6",
      "maxFeePerGas": "0x1712e2801c",
      "maxPriorityFeePerGas": "0x3b9aca00",
      "nonce": "0x2c33",
      "r": "0x58db65d35d7ecea640e055826e36909ba3abbd4e6be3b6716d878eb9e7e72987",
      "s": "0x2b1058bc1a7831c060691276d4420142187e94789f0bdf90819c9878d84b130d",
      "transactionIndex": "0x4f",
      "type": "0x2",
      "v": "0x0",
      "value": "0x0"
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
      "blockHash": "0x92557f7e29c39cae6be013ffc817620fcd5233b68405cdfc6e0b5528261e81e5",
      "blockNumber": "0x79b5b5",
      "chainId": "0x5",
      "from": "0xf29a6c0f8ee500dc87d0d4eb8b26a6fac7a76767",
      "to": "0xb0428bf0d49eb5c2239a815b43e59e124b84e303",
      "gas": "0x5208",
      "gasPrice": "0xaf0b0a9d4",
      "hash": "0x5a57e3051cb92e2d482515b07e7b3d1851722a74654657bd64a14c39ca3f9cf2",
      "input": "0x",
      "maxFeePerGas": "0xc2152614e",
      "maxPriorityFeePerGas": "0x558d55f2",
      "nonce": "0x289a5",
      "r": "0xaa92362727b8bdf167cd4df731cefea0b057e1fe9254652648966b468462205c",
      "s": "0x25fe2e6e0fac194678f79d6abf574c1260e5eba11caac4e6b731568c0e7d9b55",
      "transactionIndex": "0x19",
      "type": "0x2",
      "v": "0x0",
      "value": "0xb1a2bc2ec50000"
    },
    ...
  ]
}
```
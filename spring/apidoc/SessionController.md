# SessionController

Back to [README.md](../README.md)

- [[GET] /api/sessions](#1)
- [[GET] /api/sessions/\{id\}](#2)
- [[POST] /api/sessions](#3)
- [[PUT] /api/sessions/\{id\}](#4)
- [[DELETE] /api/sessions/\{id\}](#5)

___

### [GET] /api/sessions {#1}

Get all sessions

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Session[ ]

___

## [GET] /api/sessions/\{id\} {#2}

Get a session

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Session
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/sessions {#3}

Create a session

### Parameters

- *json* : object Session

### Response (HTTP)

- *201 Created :* Session

___

## [PUT] /api/sessions/\{id\} {#4}

Update a session

### Parameters

- *\{id\} :* integer
- *json :* object Session

### Response (HTTP)

- *200 Ok :* Session
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/sessions/\{id\} {#5}

Delete a session

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

Back to [README.md](../README.md)
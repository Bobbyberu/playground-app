# RoleController

Back to [README.md](../README.md)

- [[GET] /api/roles](#1)
- [[GET] /api/roles/\{id\}](#2)
- [[POST] /api/roles](#3)
- [[PUT] /api/roles/\{id\}](#4)
- [[DELETE] /api/roles/\{id\}](#5)
- [[GET] /api/users/\{userId\}/roles](#6) (Not Done)

___

### [GET] /api/roles {#1}

Get all roles

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Role[ ]

___

## [GET] /api/roles/\{id\} {#2}

Get a role

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Role
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/roles {#3}

Create a role

### Parameters

- *json* : object Role

### Response (HTTP)

- *201 Created :* Role

___

## [PUT] /api/roles/\{id\} {#4}

Update a role

### Parameters

- *\{id\} :* integer
- *json :* object Role

### Response (HTTP)

- *200 Ok :* Role
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/roles/\{id\} {#5}

Delete a role

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

## [GET] /api/users/\{userId\}/roles {#6}

Get all roles for an user

### Parameters

- *\{userId\} :* integer

### Response (HTTP)

- *200 Ok :* Role
- *404 Not found :* ResourceNotFoundException

___

Back to [README.md](../README.md)
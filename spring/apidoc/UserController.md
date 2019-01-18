# UserController

Back to [README.md](../README.md)

- [[GET] /api/users](#1)
- [[GET] /api/users/\{id\}/favouritePlaygrounds](#2)
- [[GET] /api/users/\{userId\}/favouritePlaygrounds/\{playgroundId\}](#3)
- [[GET] /api/users/\{id\}](#4)
- [[GET] /api/users/\{username\}](#5)
- [[POST] /api/users/signup](#6)
- [[PUT] /api/users/\{userId\}/favouritePlaygrounds/\{playgroundId\}](#7)
- [[PUT] /api/users/\{id\}](#8)
- [[DELETE] /api/users/\{id\}](#9)

## [GET] /api/users {#1}

Get all users

### Parameters

- *No Parameter*

### Response (HTTP)

- *200 OK* User[ ]

___

## [GET] /api/users/\{id\}/favouritePlaygrounds {#2}

Get all user's favourite playgrounds

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Playground[ ]
- *404 Not found :* ResourceNotFoundException

___

## [GET] /api/users/\{userId\}/favouritePlaygrounds/\{playgroundId\} {#3}

Return true if it's a user's favourite playground 

### Parameters

- *\{userId\} :* integer
- *\{playgroundId\} :* integer

### Response (HTTP)

- *200 Ok :* boolean
- *404 Not found :* ResourceNotFoundException

___

## [GET] /api/users/\{id\} {#4}

Get an user by his id 

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* User
- *404 Not found :* ResourceNotFoundException

___

## [GET] /api/users/\{username\} {#5}

Get an user by his username

### Parameters

- *\{username\} :* String

### Response (HTTP)

- *200 Ok :* User
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/users/signup {#6}

Create an user

### Parameters

- *json* : User object

### Response (HTTP)

- *201 Created :* User

___

## [PUT] /api/users/\{userId\}/favouritePlaygrounds/\{playgroundId\} {#7}

Add/Remove a user's favourite playground 

### Parameters

- *\{userId\} :* integer
- *\{playgroundId\} :* integer

### Response (HTTP)

- *200 Ok :* boolean
- *404 Not found :* ResourceNotFoundException

___

## [PUT] /api/users/\{id\} {#8}

Update an user

### Parameters

- *\{id\} :* integer
- *json :* User object

### Response (HTTP)

- *200 Ok :* User
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/users/\{id\} {#9}

Delete an user

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException

___

Back to [README.md](../README.md)
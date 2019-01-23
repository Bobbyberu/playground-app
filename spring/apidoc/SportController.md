# SportController

Back to [README.md](../README.md)

- [[GET] /api/sports](#1)
- [[GET] /api/sports/\{id\}](#2)
- [[POST] /api/sports](#3)
- [[PUT] /api/sports/\{id\}](#4)
- [[DELETE] /api/sports/\{id\}](#5)
- [[GET] /api/playgrounds/\{playgroundId\}/sports](#6) (Not Done)

___

### [GET] /api/sports {#1}

Get all sports

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Sport[ ]

___

## [GET] /api/sports/\{id\} {#2}

Get a sport

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Sport
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/sports {#3}

Create a sport

### Parameters

- *json* : object Sport

### Response (HTTP)

- *201 Created :* Sport

___

## [PUT] /api/sports/\{id\} {#4}

Update a sport

### Parameters

- *\{id\} :* integer
- *json :* object Sport

### Response (HTTP)

- *200 Ok :* Sport
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/sports/\{id\} {#5}

Delete a sport

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

## [GET] /api/playgrounds/\{playgroundId\}/sports {#6}

Get all sports for a playground

### Parameters

- *\{playgroundId\} :* integer

### Response (HTTP)

- *200 Ok :* Sport
- *404 Not found :* ResourceNotFoundException

___

Back to [README.md](../README.md)
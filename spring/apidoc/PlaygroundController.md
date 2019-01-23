# PlaygroundController

Back to [README.md](../README.md)

- [[GET] /api/playgrounds](#1)
- [[GET] /api/playgrounds/\{id\}](#2)
- [[POST] /api/playgrounds](#3)
- [[PUT] /api/playgrounds/\{id\}](#4) (ADMIN)
- [[DELETE] /api/playgrounds/\{id\}](#5) (ADMIN)
- [[GET] /api/playgrounds/search/\{keyword\}](#6)
- [[GET] /api/playgrounds/\{id\}/image](#7)
- [[POST] /api/playgrounds/\{id\}/image](#8)
- [PATCH] /api/playgrounds/{id}/playing
- [GET] /api/signalement/playgrounds

## [GET] /api/playgrounds {#1}

Get all playgrounds

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Playground[ ]

___

## [GET] /api/playgrounds/\{id\} {#2}

Get a playground

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Playground
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/playgrounds {#3}

Create a playground

### Parameters

- *json* : Playground object

### Response (HTTP)

- *201 Created :* Playground

___

## [PUT] /api/playgrounds/\{id\} {#4}

Update a playground

### Parameters

- *\{id\} :* integer
- *json :* Playground object

### Response (HTTP)

- *200 Ok :* Playground
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/playgrounds/\{id\} {#5}

Delete a playground

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

## [GET] /api/playgrounds/search/\{keyword\} {#6}

Get all playgrounds which are a linked with the keyword (sport, name, location ...)

### Parameters

- *\{keyword\} :* String

### Response (HTTP)

- *200 Ok :* Playground[]

___

## [GET] /api/playgrounds/\{id\}/image {#7}

Get corresponding playground image

### Parameters

- *\{id\} :* Int

### Response (HTTP)

- *200 Ok* 

___

## [POST] /api/playgrounds/\{id\}/image {#8}

Upload image for given playground

### Parameters

- *\{id\} :* Int
- *\{file\} :* MultipartFile (file to be sent)

### Response (HTTP)

- *200 Ok* 

___

Back to [README.md](../README.md)
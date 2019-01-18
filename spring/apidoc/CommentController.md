# CommentController

Back to [README.md](../README.md)

- [[GET] /api/comments](#1)
- [[GET] /api/comments/\{id\}](#2)
- [[POST] /api/comments](#3)
- [[PUT] /api/comments/\{id\}](#4)
- [[DELETE] /api/comments/\{id\}](#5)
- [[GET] /api/playgrounds/\{playgroundId\}/comments](#6)

___

### [GET] /api/comments {#1}

Get all comments

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Comment[ ]

___

## [GET] /api/comments/\{id\} {#2}

Get a comment

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Comment
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/comments {#3}

Create a comment

### Parameters

- *json* : object Comment

### Response (HTTP)

- *201 Created :* Comment

___

## [PUT] /api/comments/\{id\} {#4}

Update a comment

### Parameters

- *\{id\} :* integer
- *json :* object Comment

### Response (HTTP)

- *200 Ok :* Comment
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/comments/\{id\} {#5}

Delete a comment

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

## [GET] /api/playgrounds/\{playgroundId\}/comments {#6}

Get all comments of a playground

### Parameters

- *\{playgroundId\} :* integer

### Response (HTTP)

- *200 Ok :* Comment[ ]
- *404 Not found :* ResourceNotFoundException

___

Back to [README.md](../README.md)
# CommentController

Back to [README.md](../README.md)

- [[GET] /api/comments](#1)
- [[GET] /api/playgrounds/\{playgroundId\}/comments/\{commentId\}](#2)
- [[POST] /api/playgrounds/\{playgroundId\}/comments](#3)
- [[PUT] /api/playgrounds/\{playgroundId\}/comments/\{commentId\}](#4)
- [[DELETE] /api/playgrounds/\{playgroundId\}/comments/\{commentId\}](#5)
- [[PUT] /api/playgrounds/\{playgroundId\}/comments/archived/\{commentId\}](#6)
- [[GET] /api/playgrounds/\{playgroundId\}/comments](#7)

___

### [GET] /api/comments {#1}

Get all comments

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Comment[ ]

___

## [GET] /api/playgrounds/\{playgroundId\}/comments/\{commentId\} {#2}

Get a comment

### Parameters

- *\{playgroundId\} :* integer
- *\{commentId\} :* integer

### Response (HTTP)

- *200 Ok :* Comment
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/playgrounds/\{playgroundId\}/comments {#3}

Create a comment

### Parameters

- *\{playgroundId\} :* integer
- *json* : object Comment

### Response (HTTP)

- *201 Created :* Comment
- *404 Not found :* ResourceNotFoundException

___

## [PUT] /api/playgrounds/\{playgroundId\}/comments/\{commentId\} {#4}

Update a comment

### Parameters

- *\{playgroundId\} :* integer
- *\{commentId\} :* integer
- *json :* object Comment

### Response (HTTP)

- *200 Ok :* Comment
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/playgrounds/\{playgroundId\}/comments/\{commentId\} {#5}

Delete a comment

### Parameters

- *\{playgroundId\} :* integer
- *\{commentId\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

## [PUT] /api/playgrounds/\{playgroundId\}/comments/archived/\{\} {#6}

Archived a comment

### Parameters

- *\{playgroundId\} :* integer
- *\{commentId\} :* integer

### Response (HTTP)

- *200 Ok :* Comment
- *404 Not found :* ResourceNotFoundException

___

## [GET] /api/playgrounds/\{playgroundId\}/comments {#7}

Get all comments of a playground

### Parameters

- *\{playgroundId\} :* integer

### Response (HTTP)

- *200 Ok :* Comment[ ]
- *404 Not found :* ResourceNotFoundException

___

Back to [README.md](../README.md)
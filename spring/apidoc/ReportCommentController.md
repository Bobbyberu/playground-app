# ReportCommentController

Back to [README.md](../README.md)

- [[GET] /api/reportComments](#1)
- [[GET] /api/comments/\{commentId\}/reportComments/\{reportCommentId\}](#2)
- [[POST] /api/comments/\{commentId\}/reportComments](#3)
- [[PUT] /api/comments/\{commentId\}/reportComments/\{reportCommentId\}](#4)
- [[DELETE] /api/comments/\{commentId\}/reportComments/\{reportCommentId\}](#5)

___

### [GET] /api/reportComments {#1}

Get all reportComments

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* ReportComment[ ]

___

## [GET] /api/comments/\{commentId\}/reportComments/\{reportCommentId\} {#2}

Get a reportComment

### Parameters

- *\{commentId\} :* integer
- *\{reportCommentId\} :* integer

### Response (HTTP)

- *200 Ok :* ReportComment
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/comments/\{commentId\}/reportComments {#3}

Create a reportComment

### Parameters

- *\{commentId\} :* integer
- *json* : object ReportComment

### Response (HTTP)

- *201 Created :* ReportComment
- *404 Not found :* ResourceNotFoundException

___

## [PUT] /api/comments/\{commentId\}/reportComments/\{reportCommentId\} {#4}

Update a reportComment

### Parameters

- *\{commentId\} :* integer
- *\{reportCommentId\} :* integer
- *json :* object ReportComment

### Response (HTTP)

- *200 Ok :* ReportComment
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/comments/\{commentId\}/reportComments/\{reportCommentId\} {#5}

Delete a reportComment

### Parameters

- *\{commentId\} :* integer
- *\{reportCommentId\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

Back to [README.md](../README.md)
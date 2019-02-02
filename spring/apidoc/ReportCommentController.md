# ReportCommentController

Back to [README.md](../README.md)

- [[GET] /api/reportComments](#1)
- [[GET] /api/reportComments/\{reportCommentId\}](#2)
- [[POST] /api/comments/\{commentId\}/reportComments](#3)
- [[PUT] /api/reportComments/\{reportCommentId\}](#4)
- [[DELETE] /api/reportComments/\{reportCommentId\}](#5)

___

### [GET] /api/reportComments {#1}

Get all reportComments

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* ReportComment[ ]

___

## [GET] /api/reportComments/\{reportCommentId\} {#2}

Get a reportComment

### Parameters

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

## [PUT] /api/reportComments/\{reportCommentId\} {#4}

Update a reportComment

### Parameters

- *\{reportCommentId\} :* integer
- *json :* object ReportComment

### Response (HTTP)

- *200 Ok :* ReportComment
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/reportComments/\{reportCommentId\} {#5}

Delete a reportComment

### Parameters

- *\{reportCommentId\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

Back to [README.md](../README.md)
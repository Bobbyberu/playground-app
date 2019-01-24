# ReportPlaygroundController

Back to [README.md](../README.md)

- [[GET] /api/reportPlaygrounds](#1)
- [[GET] /api/playgrounds/\{playgroundId\}/reportPlaygrounds/\{reportPlaygroundId\}](#2)
- [[POST] /api/playgrounds/\{playgroundId\}/reportPlaygrounds](#3)
- [[PUT] /api/playgrounds/\{playgroundId\}/reportPlaygrounds/\{reportPlaygroundId\}](#4)
- [[DELETE] /api/reportPlaygrounds/\{reportPlaygroundId\}](#5)

___

### [GET] /api/reportPlaygrounds {#1}

Get all reportPlaygrounds

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* ReportPlayground[ ]

___

## [GET] /api/playgrounds/\{playgroundId\}/reportPlaygrounds/\{reportPlaygroundId\} {#2}

Get a reportPlayground

### Parameters

- *\{playgroundId\} :* integer
- *\{reportPlaygroundId\} :* integer

### Response (HTTP)

- *200 Ok :* ReportPlayground
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/playgrounds/\{playgroundId\}/reportPlaygrounds {#3}

Create a reportPlayground

### Parameters

- *\{playgroundId\} :* integer
- *json* : object ReportPlayground

### Response (HTTP)

- *201 Created :* ReportPlayground
- *404 Not found :* ResourceNotFoundException

___

## [PUT] /api/playgrounds/\{playgroundId\}/reportPlaygrounds/\{reportPlaygroundId\} {#4}

Update a reportPlayground

### Parameters

- *\{playgroundId\} :* integer
- *\{reportPlaygroundId\} :* integer
- *json :* object ReportPlayground

### Response (HTTP)

- *200 Ok :* ReportPlayground
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/reportPlaygrounds/\{reportPlaygroundId\} {#5}

Delete a reportPlayground

### Parameters

- *\{reportPlaygroundId\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

Back to [README.md](../README.md)
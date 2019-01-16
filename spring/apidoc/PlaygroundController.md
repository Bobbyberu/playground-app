# PlaygroundController

Back to [README.md](../README.md)

## [GET] /api/playgrounds

Get all playgrounds

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Playground[ ]

___

## [GET] /api/playgrounds/\{id\}

Get a playground

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Playground
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/playgrounds

Create a playground

### Parameters

- *body* : Json

### Response (HTTP)

- *201 Created :* Playground

___

## [PUT] /api/playgrounds/\{id\}

Update a playground

### Parameters

- *\{id\} :* integer
- *body :* Json

### Response (HTTP)

- *200 Ok :* Playground
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/playgrounds/\{id\}

Delete a playground

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

## [GET] /api/playgrounds/search/\{keyword\}

Get all playgrounds which are a linked with the keyword (sport, name, location ...)

### Parameters

- *\{keyword\} :* String

### Response (HTTP)

- *200 Ok :* Playground[]

___

Back to [README.md](../README.md)
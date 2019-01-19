# ScheduleController

Back to [README.md](../README.md)

- [[GET] /api/schedules](#1)
- [[GET] /api/schedules/\{id\}](#2)
- [[POST] /api/schedules](#3)
- [[PUT] /api/schedules/\{id\}](#4)
- [[DELETE] /api/schedules/\{id\}](#5)

___

### [GET] /api/schedules {#1}

Get all schedules

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Schedule[ ]

___

## [GET] /api/schedules/\{id\} {#2}

Get a schedule

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *200 Ok :* Schedule
- *404 Not found :* ResourceNotFoundException

___

## [POST] /api/schedules {#3}

Create a schedule

### Parameters

- *json* : object Schedule

### Response (HTTP)

- *201 Created :* Schedule

___

## [PUT] /api/schedules/\{id\} {#4}

Update a schedule

### Parameters

- *\{id\} :* integer
- *json :* object Schedule

### Response (HTTP)

- *200 Ok :* Schedule
- *404 Not found :* ResourceNotFoundException

___

## [DELETE] /api/schedules/\{id\} {#5}

Delete a schedule

### Parameters

- *\{id\} :* integer

### Response (HTTP)

- *204 No Content :* void
- *404 Not found :* ResourceNotFoundException
  
___

Back to [README.md](../README.md)
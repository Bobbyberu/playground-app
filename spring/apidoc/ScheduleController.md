# ScheduleController

Back to [README.md](../README.md)

- [[POST] /api/schedules](#1)
- [[PUT] /api/schedules/\{id\}](#2)
- [[DELETE] /api/schedules/\{id\}](#3)

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
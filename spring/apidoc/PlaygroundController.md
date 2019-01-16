# PlaygroundController

## **[GET]** **/api/playground**

### Parameters

- *No parameter*

### Response (HTTP)

- *200 OK* Playground[ ]

___

## **[GET]** **/api/playground/{id}** (*{id} :* int)

### Parameters

- *{id} :* integer

### Response (HTTP)

- *200 OK :* Playground
- *404 Not found :* ResourceNotFoundException

___

**[POST]** **/api/playground** (*body :* Json)

___

**[PUT]**  **/api/playground/{id}** (*{id} :* int, *body :* Json)

___

**[DELETE]** **/api/playground/{id}** (*{id} :* int)

___

**[GET]** **/api/playground/search/{keyword}** (*{keyword} :* String ) 

___
# Microservice: statistics-service

### Method: GET
* *Endpoint:* http://localhost:8080/statistics-service/statistics
* *Response Example:* 
```json5
{
    "sum": 0.00,
    "min": 0.00,
    "max": 0.00,
    "avg": 0.00,
    "count": 0
}
```

### Method: POST
* *Endpoint:* http://localhost:8080/statistics-service/transaction
* *Response Example:* 
```json5
{
    "timestamp": 0,
    "amount": 0.00
}
```

## Docker
* Create image with Docker	
		
`				
docker build -f Dockerfile -t target/transaction-statistics-service .
`

* Run image on port 8080
									
`
docker run -p 8080:8080 -t target/transaction-statistics-service
`

## Swagger
`
http://localhost:8080/swagger-ui.html
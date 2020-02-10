# money-transfer

A Java RESTful API for money transfers between accounts

### Technologies
- Java 8
- Jersey
- Jetty Container
- Maven
- Junit 5

### How to run
mvn exec:java

### How to run tests
mvn '-Dtest=com.revolut.moneytransfer.**.*Test' test

### Services
- POST [http://localhost:8080/api/bank/1.0/transfer](http://localhost:8080/api/bank/1.0/transfer)

### Http Responses Status
- 200 OK
- 400 Bad request

#### OK body example
```json
{
	"debit_account_id": 123,
	"credit_account_id": 123,
	"amount": 2
}
```

#### Bad Request body example

```json
{
    "message": "Amount cannot be null"
}
```


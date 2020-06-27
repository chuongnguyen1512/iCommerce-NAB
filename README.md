# iCommerce

A small start-up named "iCommerce" wants to build a very simple online shopping application to sell their products. I implemented some operations for 3​ backend web services to demonstrate inter-service
communication between these services.
+ Order Service for receive, process order requests and deliver to Deliver Service by RESTful API.
+ Deliver Service for receive requests from Order Service and handling with 3rd party APIs to deliver items. 
+ Product Service for importing products with audit prices.

Prefer below diagrams:
- [Class Diagrams](https://drive.google.com/file/d/1Zo4V09V7YgHosw_xmq3pEJZ_yzBfzUIr/view?usp=sharing)
- [Solution Diagrams](https://drive.google.com/file/d/1sUEAotFYD7ygYY1eN1HgNvOT3_aZoyvi/view?usp=sharing)

## Technical
- Language: Java 8
- Framework: Spring Boot, Spring Data JPA + Hibernate + Hibernate Envers, JUnit, Mockito.
- Database: H2 Database(Can config to MySQL Database if already set up)
- Develop based on web technology with RESTful to communicate between services to demonstrate microservices communication.
- For audit demostration, applied Hibernate Envers for audit data changes. Ex: Product has price field marked @Audited for audit changed values.
- Also for enhancement, applied template method pattern to re-use codes and prevent duplication. 
- Applied retry mechanism for calling Restful APIs, handling failure data records.

## Structures:
#### iCommerce-Order-Service
Demo to receive order request from FrontEnd and process order request to Database and deliver them to Deliver Service if payment is cash. Otherwise, deliver to payment-service(not implemented yet).
#### iCommerce-Deliver-Service
Demo to receive order requests from iCommerce-Order-Service and communicate with 3rd party APIs to ship items to customers. This service also keeps track order's status when delivering.
#### iCommerce-Product-Service
Demo for saving product info with audit price.
#### iCommerce-Components
- iCommerce-Entity: Contained models for Hibernate mapping
- iCommerce-DTO: Contained models for processing
- iCommerce-Common: Contained some common class or util class
- iCommerce-Repostiory: Contained DAO class for persit/query data

## Installation

1/ Download [Gradle 6.5](https://gradle.org/releases/)

2/ Clone source code

3/ Using Gradle build command to build below modules in the following order:
```bash
$ gradle clean build -xtest
```
  - iCommerce-Entity
  - iCommerce-DTO
  - iCommerce-Common
  - iCommerce-Repository
  - iCommerce-Order-Service
  - iCommerce-Product-Service
  - iCommerce-Deliver-Service

4/ Go to build/libs folders for each below services and start by running command:
```bash
$ java -jar <<dir>>/<<jar_name>>.jar
```
Example:
```bash
$ java -jar iCommerce-Order-Service-1.0.0.jar
```
- iCommerce-Order-Service (need to start first because service contained H2 remoted database)
- iCommerce-Deliver-Service
- iCommerce-Product-Service

## Verfication:
Can use Postman collection to verify: [iCommerce Collection](https://www.getpostman.com/collections/b60e120068577df72998)
or can use below cURL commands to verify:

- Saving Product URL
```bash
$ curl --location --request POST 'localhost:8082/savingProducts' \
--header 'transaction_id: 7aeabe6f-1df8-4d56-848b-433a8c141d5b' \
--header 'Authorization: Basic Y2h1b25nOjEyMw==' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "prodName": "Product 1",
        "supplierName": "Supplier 1",
        "prodDescript": "This is a description for Product 1",
        "price": 10,
        "status": "New"
    },
     {
        "prodName": "Product 2",
        "supplierName": "Supplier 2",
        "prodDescript": "This is a description for Product 2",
        "price": 20,
        "status": "New"
    }
]'
```

- Process Orders:
```bash
curl --location --request POST 'localhost:8081/processOrders' \
--header 'transaction_id: 456-7812-20054' \
--header 'Authorization: Basic Y2h1b25nOjEyMw==' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "userName": "chuong_123",
        "orderDate": "2018-10-12",
        "contactPhone": "0722654599",
        "paymentType": "Cash",
        "shippingAddress": "123 Fake Street",
        "totalPrice": 10,
        "items": [
            {
                "quantity": 1,
                "productNumber": "01ba974a-8a48-495c-ba7a-a44eb3d930b3",
                "price": 10
            }
        ]
    }
]'
```

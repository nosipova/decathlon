# DECATHLON DEV  Skills  Exercise solution

## Notes

This is the first version of solution which will be proposed  and will provide an fully reactive event oriented architecture that will cover lock and down time issues related with high volume of request to exposed API services. 

## Introduction

This API presents a show case where you can consult the stock of shoes. The API allows you to:
- Consult a shoe model
- Consult the stock with all shoes of the shop
- Update the stock and shoes(The shoes could be created, deleted or updated)
- Add a new shoe to global stock

## Modifications to provided project architecture

The main changes maded are:
- Creation of two new modules: data (the dao part containing entities and repositories) and core-stock (the business part containing necessary service to manage all operations related to stocks and shoes). This modules have tests created with JUnit component for testing implemented  functionality and they will be launched automatically on every application compilation to be ensure if the implemented functionality doesn't work correctly the application won't be generated. If we want to disable tests launching on compilations we must to add "-Dmaven.test.skip=true" on maven install command.

- Addition of mapstruct component on core-stock module which helps convert automatically entities to dto's and vice versa. 

- Embbebed H2 database which will charge testing data and allow add new data. Cons: it will delete any modifications maded on application run and, once the application will be restarted will charge testing data again.

- Updating the project from 14 to 16 Java J2EE version and Spring Boot version from 2.3.0 to 2.5.0. Also was added javax.validation dependencies on dto's pom project to activate data validation on incoming requests.

- Addition of an component called Swagger UI which makes easier to test API services from any web navigator.

- Containerization of the application using Docker which provide it own isolated workload environment, make it independently deployable and scalable on any cloud provider or on premise environment.   

## Launching API application

There are two ways to launch the application: 

 - The recommended way is using Docker, we will need have installed Docker. By first, from command console, we will go inside project parent folder which contain Dockerfile necessary to generate docker image and launch the application. Inside this folder we will execute the following command: 'docker build -t demo .' which will generate docker image from Dockerfile with the application inside it and all necessary to his correct functionality. Once the image called 'demo' it's generated we can launch the docker image with following command: 'docker run -dp 8080:8080 demo'. If we can access to URL http://localhost:8080/swagger-ui/index.html it works.
 
 - Using Maven and Java to generate new jar file and launch it via Java. For this option we need to have installed jdk16 and maven 3.8.1. By first, from command console, we will execute following maven comman inside project parent folder: 'mvn clean install'. If all is gone well, we will execute another command from same folder which will launch the application: 'java -jar controller/target/stock-api-controller.jar'. If we can access to URL http://localhost:8080/swagger-ui/index.html it works.
 
## Testing API services
 
 After we had launched our API application and accessed on http://localhost:8080/swagger-ui/index.html we could see all available services of our API called 'StockController' with following routes:
-  _GET_​/shoes​/stock : will return the stock with state of stock, total quantity and all shoes of the shop. 

   <summary><b>Example of request with curl: </b></summary>
    
    
   ```shell script  
    curl --location --request GET 'http://localhost:8080/shoes/stock'    
   ```

- _PATCH_​/shoes​/stock : update the stock and shoes(The shoes could be created, deleted or updated)

   <summary><b>Example of request with curl: </b></summary>
   
   ```shell script   
curl --location --request PATCH 'http://localhost:8080/shoes/stock' \
--header 'Content-Type: application/json' \
--data-raw '{
  "state": "SOME",
  "shoes": {
    "shoes": [
      {
        "name": "mocasines",
        "size": 42,
        "color": "BLACK",
        "quantity": 1
      },
      {
        "name": "deportivas",
        "size": 41,
        "color": "BLACK",
        "quantity": 1
      },
      {
        "name": "botines",
        "size": 43,
        "color": "BLACK",
        "quantity": 1
      },
      {
        "name": "salon",
        "size": 40,
        "color": "BLACK",
        "quantity": 2
      },
      {
        "name": "salon",
        "size": 39,
        "color": "BLACK",
        "quantity": 2
      },
      {
        "name": "deportivas",
        "size": 42,
        "color": "BLUE",
        "quantity": 2
      },
      {
        "name": "botines",
        "size": 43,
        "color": "BLUE",
        "quantity": 3
      },
      {
        "name": "mocasines",
        "size": 40,
        "color": "BLUE",
        "quantity": 2
      },
      {
        "name": "salon",
        "size": 38,
        "color": "BLUE",
        "quantity": 1
      },
      {
        "name": "botas",
        "size": 41,
        "color": "BLUE",
        "quantity": 1
      },
      {
        "name": "salon",
        "size": 39,
        "color": "BLUE",
        "quantity": 1
      },
      {
        "name": "salon",
        "size": 37,
        "color": "BLUE",
        "quantity": 1
      },
      {
        "name": "sandalias",
        "size": 36,
        "color": "BLUE",
        "quantity": 1
      },
      {
        "name": "sandalias",
        "size": 38,
        "color": "BLUE",
        "quantity": 11
      }      
    ]
  },
  "totalQuantity": 30
}'
```

- _PUT_​/shoes​/stock/shoe : add a new shoe to global stock

   <summary><b>Example of request with curl: </b></summary>
   
   ```shell script 
   curl --location --request PUT 'http://localhost:8080/shoes/stock/shoe?name="salon"&size=39&color="BLACK"&quantity=1'
```

Or if we use Postman application, we can import file ` 'DECATHLON.postman_collection.json' `  inside project folder to our Postman application and launch prepared tests.

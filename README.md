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
- Creation of two new modules: data (the dao part containing entities and repositories) and core-stock (the business part containing necessary service to manage all operations related to stocks and shoes). This modules have tests created with JUnit component for testing implemented  functionality and they are launched on application compilation to be ensure if the implemented functionality doesn't work correctly the application won't be generated.
- Embbebed H2 database which will charge testing data and allow add new data. Cons: it will delete any modifications maded on application run and, once the application will be restarted will charge testing data again.
- Addition of an component called Swagger UI which makes easier to test API services from any web navigator.
- Containerization of the application using Docker which provide it own isolated workload environment, make it independently deployable and scalable on any cloud provider or on premise environment.   
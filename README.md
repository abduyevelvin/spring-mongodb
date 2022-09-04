# v1: fleet-management
Maven is used as a build automation tool. 
There are 2 ways to run the application:
    Using local MongoDD and run application locally, 
    then below commands could be used to clean/install and 
    run the application using the command line respectively:
 - "Path\mongod.exe" --dbpath="db-path" (need provide mongo db exe file location and db path location)
 - mvn clean install
 - mvn spring-boot:run

    Or using docker-compose command (before need uncomment following line in application.properties file "#spring.data.mongodb.host=fleetdb"):
 - docker pull mongo:latest
 - docker-compose up

MongoDB is used for storing data, verify data from MongoDB, "mongosh" (Mongo Shell) could be used:
- mongosh command in cmd.exe
- show dbs
- use Fleet
- show collections
- db.Fleet.find().pretty() (we can see created Fleet data)
 
Implemented Swagger UI, could be reached from the below link:
http://localhost:8080/swagger-ui.html#

For mapping models to DTOs mapstruct is used.
SLF4J Logger is used for logging purposes.
Mock is used for testing purposes.

All configurations could be found in the resources: application.properties
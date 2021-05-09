# RESTful API: Events, Venues and Artists

## Abstract

This project is a RESTful API for retrieving events, venues and artists data.

## Requirements

- Java 8 (JDK 8)
- Maven

## How to build and run

1. To compile run ```mvn clean package```
2. To run the application execute the following command ```java -jar target/reactive-rest-service*.jar```

or alternatively run with Maven:

1. ```mvn clean install```
2. ```mvn spring-boot:run``` 


Application should start at  ```http://localhost:8080``` for either method.

## Exploring the API

The application contains the following REST endpoints:
```
GET /artist/{artist-id} - Get artist information for a specific artistId. eg. /artist/23

GET /venue/{venue-id} - Get venue information for a specific venueId. eg /venue/43
```

```Swagger``` can be used to test/explore the endpoints at this URL ```http://localhost:8080/swagger-ui/``` once the application is running.

## External dependencies

Data regarding events, venues and artists is retrieved from the following endpoints:

```
https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/events.json -> contains data for events. It links to artists and venues via ids
https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json -> contains data for artists.
https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/venues.json -> contains data for venues
```

## Possible Improvements

- Authentication on endpoints to enhance security
- Containerization
- Caching on queries to events, venues and artists external endpoints for faster response

## Other info
```Actuator``` has been added to the project to expose operational information about the running application — health, metrics, info, etc.



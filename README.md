# BookNook

## Description
BookNook is a console-based Java application for managing book entries including books as seen on The New York Times Best Sellers lists.
Book data is stored in the form of JSON documents in a MongoDB NoSQL database, hosted on an AWS EC2 instance. 
This application uses the Mongo Java driver to connect to the EC2 instance and allow the user to perform database management CRUD operations.

## Requirements
In order to run the BookNook application, you will need the following:
  
1. `mongo-java-driver-3.8.1.jar`
2. Java IDE such as Eclipse or IntelliJ

## Installation
Download the BookNook application and open the project in your IDE. Add the Mongo Java driver to your target folder before running the application.

## BookNook Features
BookNook lets you:

* Create a book entry
* Delete a book entry
* Update a book entry
* Search for books by category, keyword, or price
* Browse books on The New York Times Best Sellers

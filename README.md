# BookNook: The New York Times Book Manager

## Description
BookNook is a console-based Java application for managing book entries including books as seen on The New York Times Best Sellers lists.
Book data is stored in the form of JSON documents in a MongoDB NoSQL database, hosted on an AWS EC2 instance. 
This application uses the Mongo Java driver to connect to the EC2 instance and allow the user to perform database management CRUD operations.

## Requirements
In order to run the BookNook application, you will need the following:
  
1. `mongo-java-driver-3.8.1.jar`
2. Java IDE such as Eclipse or IntelliJ

## Installation
1. Download the BookNook application 
2. Open the project in your IDE
3. Add the Mongo Java driver to your target folder

## BookNook Features
BookNook lets you:

* Create a book entry
* Delete a book entry
* Update a book entry
* Search for books by category, keyword, or price
* Browse books on The New York Times Best Sellers lists

Book entries include details such as:
| Field         | Description                                                                       |
| ------------- | --------------------------------------------------------------------------------- |
| Age group     | The recommended reader age group for the book                                     |
| Author        | The author of the book                                                            |
| Description   | A brief description of the book                                                   |
| Price         | The price of the book in USD                                                      |
| ISBN          | A unique ISBN identifier for the book                                             |
| Publisher     | The publisher of the book                                                         |

## Credits
A special thanks to Team Strozzi 

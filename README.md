# BookNook 

## Description
BookNook is a Java console application for managing book entries, creating personal book lists, and browsing books from The New York Times Best Sellers.
Book data is stored in the form of JSON documents in a MongoDB NoSQL database, hosted on an AWS EC2 instance. 
This application uses the Mongo Java driver to connect to the EC2 instance and allows the user to perform database management CRUD operations.

## Requirements
To run the BookNook application, you will need the `mongo-java-driver-3.8.1.jar` file and a Java IDE such as Eclipse or IntelliJ.

## Installation
1. Clone the repository
2. Open the project in your IDE
3. Add the Mongo Java driver to the target folder

## BookNook Features
BookNook lets you:
- Create a book entry
- Delete a book entry
- Update a book entry
- Search for books by category
- Search for books by keyword
- Search for books by ascending or descending price
- Browse books on The New York Times Best Sellers lists
- Save books to personal book lists


Book entries include details such as:
| Field         | Description                                                                       |
| ------------- | --------------------------------------------------------------------------------- |
| Age group     | The recommended reader age group for the book                                     |
| Author        | The author of the book                                                            |
| Description   | A brief description of the book                                                   |
| Price         | The price of the book in USD                                                      |
| ISBN          | A unique ISBN identifier for the book                                             |
| Publisher     | The publisher of the book                                                         |
| Rank          | The sales rank of the book according to The New York Times Best Sellers           |
| Title         | The title of the book                                                             |
| Weeks on list | The number of weeks on The New York Times Best Seller list                        |
| Purchase links| The names and URL links of retail websites to purchase the book                   |

## Credits
*A special thanks to Team Strozzi*

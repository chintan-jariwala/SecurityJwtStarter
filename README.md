# Spring security boilerplate code

## Dependencies
- [x] Java
- [x] Maven
- [x] Mysql
- [x] Lombok
- [x] Jwts
- [x] Flyway

## Description
This application will provide an authentication scheme base don username and password.

If the user logs in for the fist time, he goes to the endpoint `/api/auth/signup` and registers himself. Then when he logs in using `/api/auth/signin` then Spring will use a custom `userDetailsService` to authenticate that user and if the user checks out then give a jwt token to him.

All the implementation is done to support the application up to the login point. There is also a basic flyway implementation done here to create an empty table for `user` class.

## Pre-requisites for running

1. Before you start the project, you need to create a database called `testdb` in your local mysql server. You can always change the name of the database to your liking in `application.properties`.

Once you have finished that step, then you can run the application.


# Bookmark and short url
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8371e285f3ff48ba9df64ade01279eed)](https://app.codacy.com/manual/Avinash1604/bookmarks?utm_source=github.com&utm_medium=referral&utm_content=Avinash1604/bookmarks&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.com/Avinash1604/bookmarks.svg?branch=master)](https://travis-ci.com/Avinash1604/bookmarks) [![Coverage Status](https://coveralls.io/repos/github/Avinash1604/bookmarks/badge.svg?branch=master&kill_cache=1)](https://coveralls.io/github/Avinash1604/bookmarks?branch=master)
## About
A URL shortener is a service that is used to create short links from very long URL this will solve long url issue and Bookmark is to store the all the urls related to application and can be categorize as different group and share the group related urls


## CI/CD 
 
Code deployment process is automated , used Travis CI to build and deploy when code is pushed to master , 

Angular build :
Below are the task taken care by Travis :
* Clone project 
* Check for lint issue 
* Test case 
* Coverage 
 coveralls plugin used for the automate coverage for UI , coveralls dashboard will show details of overall coverage
* Auto deployment to git hub pages 

Spring boot Kotlin : 
* Travis CI will run the test cases and make sure that get the coverage 
* Integrated with heroku and will be deployed automatically when code is merged to master.

## Demo 
Swagger
* `https://bookmarks-tiny.herokuapp.com/swagger-ui.html` [url](https://bookmarks-tiny.herokuapp.com/swagger-ui.html)

Application 
* `https://avinash1604.github.io/bookmarks/`[demo](https://avinash1604.github.io/bookmarks/)

### How to set up application on local
#### Backend - requirement - kotlin
 - [OPENJDK 11](https://jdk.java.net/11/)
 - [Maven 3](https://maven.apache.org)
 - Intellij or STS IDE 
 
 There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.bookmark.BookmarkApplication` class from your IDE.
 
 Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
 
 ```shell
 mvn spring-boot:run
 ```
 Once after ran successfully then validate all apis on swagger 
 ```swagger
http://localhost:8080/swagger-ui/index.html or http://localhost:8080/swagger-ui.html
```
#### Front end - angular
 - [README](https://github.com/Avinash1604/bookmarks/blob/master/bookmark-ui/README.md)
### Technology and versions 
```
* Backend

	 * Language: kotlin (OpenJDK 11.2)
	 * Framework: Spring, spring-boot, spring-jpa
         * documentation: Swagger 3.0.2
         * database : postgres 
         * code quality: codacy, maven enforcer 
         * code coverage: Jacoco, coveralls
         * Test Framework: Junit 5 

*Front end 

         * Language: Angular (version 9)
         * code quality: codacy, Lint  
         * code coverage: coveralls
         * Test Framework: jasmine
  
```
## Overall Architecture
The application is build upon a very strong hexagonal architecture where every piece of code is tested through BDD and TDD.

![](docs/arch-design.png)


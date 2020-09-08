# Bookmark and short url
[![Build Status](https://travis-ci.com/Avinash1604/bookmarks.svg?branch=master)](https://travis-ci.com/Avinash1604/bookmarks) [![Coverage Status](https://coveralls.io/repos/github/Avinash1604/bookmarks/badge.svg?branch=master)](https://coveralls.io/github/Avinash1604/bookmarks?branch=master)
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
Integrated with heroku and will be deployed automatically when code is pushed.

## Demo 
Swagger
* `https://bookmarks-tiny.herokuapp.com/swagger-ui.html` [url](https://bookmarks-tiny.herokuapp.com/swagger-ui.html)

Application 
* `https://avinash1604.github.io/bookmarks/`[demo](https://avinash1604.github.io/bookmarks/)

### How to use an application 

### Technology and versions 
* Spring boot - 2.3.2 
* Kotlin 
* Swagger : open API 3 
* OpenJdk 11.2 
* Angular - 9 version 


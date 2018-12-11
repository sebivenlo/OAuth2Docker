# OAuth2Docker

This repository contains files needed for the OAuth2 workshop of the module ESD at Fontys University of Applied Sciences. The workshop is given by the authors Daljeet Sandu and Sebastian Wilczek.

## Presentation

The presentation slides of the workshop can be found [here](https://slides.com/sebastianwilczek/oauth2/).

## Setting up the resource server

To follow this workshop, you will make use of a REST server. To run the server, clone or download this repository (we will look at the source code, however there is no reason to change it). In the folder "RESTWebServer", run the following commands to build and run the server as a docker image:
```
docker build --tag=resourceserver .
docker run --network="host" -t -i resourceserver
```

## Assignment 1: Starting simple

For the first assignment, you will simply try to access a resource provided by the server. Send a curl request to the following URL:
```
http://localhost:8080/resourceserver/unsecurehelloworld
```

## Assignment 2: Protect yourself

Resources should be protected. Try to send another curl request to the following URL, with the parameters "user" and "password":
```
http://localhost:8080/resourceserver/passwordhelloworld
```

## Assignment 3: Don't do it yourself

Try looking at the source code first to understand how the "helloworld" endpoint of the server works. Try to send a curl request to the following URL:
```
http://localhost:8080/resourceserver/helloworld
```

Observe what happens. Afterwards, visit the following URL in your web browser and try to follow what has happened. If everything worked fine, make sury to store the token you receive in a place you can find it, you'll need it for the next assignment.
```
https://slack.com/oauth/authorize?client_id=308511548535.486102684279&scope=identity.basic
```

## Setting up the database

Before you can continue with the assignments, you'll need a database with test data. In order to set up the database run following commands after navigating to the project directory:

Start a couchDB instance:
```
docker run -d --name my-couchdb couchdb
```

Expose the port to the outside world:
```
docker run -p 5984:5984 -d couchdb
```

Create a DB:
```
curl -X PUT http://127.0.0.1:5984/workshop_db
```

Insert test data:

Before we insert test data it is important that you have a python module called "requests" installed. You can find further instructions [here](https://stackoverflow.com/questions/17309288/importerror-no-module-named-requests) on how to install it (have a look at the top answer).

```
cd db
python script.py
```

## Assignment 4: Make use of OAuth2

The previous assignment should have given you a token as a result. Use this token to send requests to the following URLs, providing the "read" endpoint with a parameter "entry", for example 10, and the "write" endpoint with parameters for "name", "gender" and "email". Make sure you don't forget the "token" parameter! Also, try using a token that you know can't be right.
```
http://localhost:8080/resourceserver/database/read
http://localhost:8080/resourceserver/database/write
```

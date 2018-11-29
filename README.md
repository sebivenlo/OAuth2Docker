# OAuth2Docker

This repository contains files needed for the OAuth2 workshop of the module ESD at Fontys University of Applied Sciences. The workshop is given by the authors Daljeet Sandu and Sebastian Wilczek.

## Presentation

The presentation slides of the workshop can be found [here](https://slides.com/sebastianwilczek/oauth2/).

## Google Docs

During the development, a Google Doc was used to keep track of notes. This document can be found [here](https://docs.google.com/document/d/1wqRp_c1P0pdBKGCJCySyA2t6uORjo3C_Df39R8IapGo/edit).

## Setting up the database

In order to set up the database run following commands after navigating to the project directory:

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
```
cd db
```

```
python db/script.py
```

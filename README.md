# Apache Camel with MongoDB and ActiveMQ-Artemis

## Introduction
Apache Camel is an open source framework for message-oriented middleware with a rule-based routing and mediation engine that provides a Java object-based implementation of the Enterprise Integration Patterns using an application programming interface to configure routing and mediation rules.

## Prerequisites
- MongoDB
- ActiveMq-Artemis
- MongoDB-Express (Client to connect with MongoDB server)


## Steps to run the application

_Step 1:_ Create collection in mongodb and insert some data in the collection 

_Step 2:_ Replace the name of collection in the MessagesRouter.java file
```
     .to("mongodb:mongoClient?database=rtp&authSource=admin&collection=rtp_outbox_t&operation=findAll")
```

_Step 3:_ Start Activmq-artemis either on Docker or local system

_Step 4:_ Start the application and you will see that data from mongodb will be published to queue in Activemq-artemis
Spring boot:

wrapper on spring framework

Auto-configurations and standalone applications

No need of any tomcat server spring boot directly generates jars that we can deploy it consits of embeded Tomcat server

@SpringBootApplication:
this annoation should be on only main class 
annotaion alone brings a lot of preconfigured features, including automatics component scanning and embeded server
configuration, which would have required more steps in traditional spring setup
@SpringBootApplication does three functions:

@Configuration -> if this is written on any class we can create a Bean
@EnableAutoConfiguration -> Connection to DB will be autoconfigured with details mentioned in application.properties files
@ComponentScan -> Scans for @Component in the base package and keeps objects(Beans) in IOC

Bean ?

Bean is a object , which will be used in the entire spring application no need to reintialize again these beans will be given by application context

IOC container: consists of all objects

No need to creat the object in spring we can ask spring to provide object , spring creates obj by Inversion of control(IOC)

@Component: 

It is annoation which will be added for a class so that it will create the object it self for the class and keep it in IOC container

Ex:

@Component
public class Car{
  // class is automatically registered as a spring bean(nothing but a object)
}

Dependency Injection: Using the method of a class in other class 

Dependency Injection can be done by annotation @Autowired

@Autowired:
This annoation can be used for getting object(bean) which was created by @Component annotion on a class

Ex:

@Autowired

private Dog dog;

below is the class

package com.example.MyFirstProject;

import org.springframework.stereotype.Component;

@Component
public class Dog {
    
    String fun(){
        return "Something";
    }
}

-------------------------------------------------------
Spring boot Project
 REST API
 
REST - Representational State Transfer 
API - Application programming Interfaces

GET  - just to see
POST - create 
PUT -  modify
DELETE - delete

For a REST API to be written we need to have below
Entity Class:
Entity class is nothing but the POJO class (plain old java object) which is nothing but the defining attributes of class, suppose journal is a class which consists of attributes id, Title, content and for this we need to have getters and setters to set the values and get the values 
EX:

package net.engineeringdigest.journalApp.entity;

public class JournalEntry {

    private long id;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {//POJO class plain old java object
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

Control class:

1.this class consists of all controls like GET, POST, PUT, DELETE

2.For a controller class @RestController Annotations need to be added to acts REST API 

3. FOr this class a request path should be provided with @RequestMapping("/journal")

4. under this class you can write GET , POST , PUT , DELETE under the same path, these functions can be done with @GetMapping, @PostMapping

while @PostMapping we need the pass the body given the curl so get that body we use @RequestBody 

Ex: 
package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal") // same journal path is used for get and post calls
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){ // fecthes all journals in list of arrays
        return  new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean creatEntry(@RequestBody JournalEntry journalEntry){ // puts data in maps
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry  getJournalEntryById(@PathVariable Long myId){
       return  journalEntries.get(myId);
    }
    @DeleteMapping("/id/{myId}")
    public boolean deleteJournalEntrybyID(@PathVariable Long myId){
        journalEntries.remove(myId);
        return true;
    }

    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        return journalEntries.put(myId,myEntry);
    }
}

------------------------------------------------------------------------
MongoDB

In this DB Tables => collections , Rows => documents

some commands

test> show dbs
admin   40.00 KiB
config  12.00 KiB
local   40.00 KiB
test> use school
switched to db school
school> show collections

school> db.students.insertOne({"name":"Ram","age":20})
{
  acknowledged: true,
  insertedId: ObjectId('67164a99515478bc7186b01d')
}
school> db.students.insertOne({"name":"Shyam","age":21})
{
  acknowledged: true,
  insertedId: ObjectId('67164aad515478bc7186b01e')
}
school> show collections
students
school> db.students.find
[Function: find] AsyncFunction {
  returnsPromise: true,
  apiVersions: [ 1, Infinity ],
  returnType: 'Cursor',
  serverVersions: [ '0.0.0', '999.999.999' ],
  topologies: [ 'ReplSet', 'Sharded', 'LoadBalanced', 'Standalone' ],
  deprecated: false,
  platforms: [ 'Compass', 'Browser', 'CLI' ],
  isDirectShellCommand: false,
  acceptsRawInput: false,
  shellCommandCompleter: undefined,
  help: [Function (anonymous)] Help
}
school> db.students.find()
[
  { _id: ObjectId('67164a99515478bc7186b01d'), name: 'Ram', age: 20 },
  { _id: ObjectId('67164aad515478bc7186b01e'), name: 'Shyam', age: 21 }
]
school> db.students.find().pretty
[Function: pretty] {
  returnType: 'this',
  serverVersions: [ '0.0.0', '999.999.999' ],
  apiVersions: [ 0, Infinity ],
  topologies: [ 'ReplSet', 'Sharded', 'LoadBalanced', 'Standalone' ],
  returnsPromise: false,
  deprecated: false,
  platforms: [ 'Compass', 'Browser', 'CLI' ],
  isDirectShellCommand: false,
  acceptsRawInput: false,
  shellCommandCompleter: undefined,
  help: [Function (anonymous)] Help
}
school> db.students.find().pretty()
[
  { _id: ObjectId('67164a99515478bc7186b01d'), name: 'Ram', age: 20 },
  { _id: ObjectId('67164aad515478bc7186b01e'), name: 'Shyam', age: 21 }
]
school> db.students.find({name:"Ram"})
[ { _id: ObjectId('67164a99515478bc7186b01d'), name: 'Ram', age: 20 } ]
school>

----------------------------------------------------------------
Understanding ORM, JPA, and Spring Data JPA

ORM -> Object Relational Mapping
ORM is a technique used to map java objects to database tools
It allows to work with databases using OOPs concepts, making it easier to interact with relational Databases

Ex: Consider a java calss User and a database tables users. ORM frameworks like Hibernate can map the fields in the User class to columns in the user table, making it easier to insert, update, retreive and delete records

JPA -> Java Persistence API
A way to achive ORM, includes interfaces and annoations  that you use in your java classes, requires a persistence(ORM tools) for implementation

ORM Tools:

To use JPA, you need a persistence provider. A persistence provider is a specific implementation of JPA specification.Examples of JPA persistence providers include Hibernate, EclipseLink and OpenJPA. These providers implement JPA interfaces and and provide the underlying functionality to interact with databases

Spring Data JPA:

Spring Data JPA is built on top JPA specification, but its not a JPA implementation itself.Instead, it simplifies working JPA by providing higher-level abstractions and utilities.However,to use Spring Data JPA effectively, you still need JPA implementation, such as Hibernate, EclipseLink, or another JPA compliant provider, to handle the actual database interactions

JPA is primarily designed for working with relational Databases, where data is stored in tables with a predefined schema.

Why JPA is not used with MongoDB ?

MongoDB on the other hand is a NoSQL Database that uses different data model typically based on collections of documents, which are schema less or have flexible schemas.This fundamental difference in in data models and storage structures is why JPA is not used with MongoDB

In Case of MongoDB, you dont have a traditional JPA persistence provider. MongoDB is NoSQL Database and Spring Data MongoDB servers as the "persistence provider" for MongoDB.It provides the neccessary abstractions and implementations to work with MongoDB in spring application

We need to add Spring Data MongoDB Dependency to pom.xml

There are two methods to interact with databases when using Spring Data JPA for relational Databases and Spring Data MongoDB for MongoDB Database

1. Query Method DSL -> It is a simple and convienient way to create queries based on method naming conventions 
2. Criteria API -> offers more dynamice and programmatic approach for building complex and custom queries

------------------------------------------------------------------------

Best practices:

create the packages
1.entities -> POJO act as API input class and maps with DB using ORM
2.controller -> we will configure path which calls service
3.service -> business logic
4.repository -> it is interface extends DB repostory , all DB operation done by this which will be called by service

------------------------------------------------------------
ResponseEntity in Spring Boot in Hindi | Handling HttpStatus while creating REST API:

HttpStatus code: An HttpStatus code is three digit numeric code returned by web server as part of the response to an HTTP request made by client

These status codes are used to convey information about the result or status of the requested about the result or status of the requested operation

HTTP status codes are grouped into five categories based on there first digit 

1XX (informational)
2XX (sucessful) 200 everthing sucessful , 201 created (like created an entry) , 204 No content(like deleted the data in DB) 
3XX (redirectorna) 
These status code indicate that further action is needed to complete the request. They are used when client needs to take additional steps to access the requested resource

301 Moved permanantly : The requested resource had been permanantly moved to diff URL
like API path is changed

4XX(client Error)
These status codes indicate that there was an error on the client's part, such as malformed request or authentication issues

400 Bad request

401 unauthorized

403 forbidden

5XX (server Error)

These status code indicate that there was an error on the servers part while trying to fulfill the request

500 Internal server error 

502 Bad gatway

503 Service unavilable

ResponseEntity:
The ResponseEntity class is part of the springframework and is commonly used in SpringBoot application to customize the HTTP response

It provides methods for setting the response status , header and body . You can use it to return different types of Data in your controller methods, such as JSOn, XML or HTML

------------------------------------------------

Mastering Project Lombok in Java: Simplify Your Code Like a Pro

Lombok is populay library in java ecosystem, often used in Spring boot application

It aims to reduce the boilerplate code that developers have to write, such as getters and setters, constructors, and more

Lombok generates bytecode for methods like getter, setters, constructors, equals(), hashcode() and toString(), as specificied annoations used in your code.This generated code is added to compiled class files(.class files)

Java compiler compiles your classes, including the generated code. This mean that the generated methods become part of your compiles class files

When you run you application, the generated methods are available for use, just like another methods in your classes

--------------------------------------------------------
Mastering MongoDB Relationships in Spring Boot: @DBRef Annotation for Seamless Collection Linking!




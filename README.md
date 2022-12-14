# ZooApp

### Does the application have any tests ?
Yes, the application has the necessary unit tests.<br>
According to intelinj app has **96%** line coverage by tests.

### How to run this app?
To begin, download the repository and unzip or clone it.<br>
To start the application find main class called `ZooAppApplication` and run it with local spring profile.<br>
That's it.

### How to create new animal ?
First you must create animalZone for animals and get their id.<br> 
Next you can add new animals to new created zone.<br>

```
**IMPORTANT** AnimalZoneName must be unique.
**IMPORTANT** New Animal must be created with correct animalZoneID. 
```

### What is inside Postman folder?
Inside the folder you can find a postman collection.
It can be imported and rest api can be tested.

### How I can run Swagger UI?
App run Locally:
```
Use http://localhost:9020/swagger-ui/index.html in your browser.
```

### What database does the project use ?
App was configured for using H2 in file memory in path `./db/zoo_database`.

### What port app use on http ?
App was configured for using port `9020`.

### How to open this project?
Use the pom file in project folder.
# Spring Boot Project Scheduler

A minimal Spring Boot application that calculates calendar schedules for project plans where:
* a project plan consists of activities;
* an activity has a duration;
* an activity can depend on zero or more other activities; and
* an activity can start only if all of its predecessors are completed.


## User Stories
* As a User, I want to be able to upload a project by uploading a JSON file that contains all pieces of information about it so that I can request to calculate its calendar schedules.
* As a User, I want to be able to view the uploaded project and its activites with their respective durations after I upload its JSON file.
* As a Scheduler, I want to be able to receive a JSON file that contains all pieces of information about a project so that I can upload it to the database.
* As a User, I want to be able to post a request to calculate the schedule of the uploaded project.
* As a Scheduler, I want to be able to calculate the calendar schedules of the uploaded project upon request.
* As a User, I want to be able to view the calculated schedule of the uploaded project that lists the activities in sequence by date.

## Technology Stack
- Java 8
- Spring MVC
- Spring Data JPA
- H2
- Thymeleaf

## Scope and Limitations
This project is created only as a proof of concept of the use of the technologies involved and as a showcase of the algorithm used to calculate the schedules.

### Scope
- Input project by uploading a JSON file
- Dynamic schedule calculation based on supported time units: day, week, month, year


### Limitations
- Happy path only! No cyclic dependencies support. Minimal error handling.
- For simplicity, the user interfacing is minimal where it only includes views for upload, activities, and calendar.
- Start date and time unit are hardwired to project due to limitation in defined views but the backend side has capability to dynamically create a calendar on-the-fly given of these parameters. By design, the calendar is loosely coupled from project.


## Getting Started

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `SpringbootProjectSchedulerApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### Preparing the JSON File

Prepare a JSON file with a .json extension. An example content is as follows:

```
{
  "name": "Martian",
  "startDate": "20190925",
  "timeUnit" : "month",
  "activities" : [
    { "code" :  "A", "duration":  5, "next" : ["C", "D"]},
    { "code" :  "B", "duration":  6},
    { "code" :  "C", "duration":  3, "next" : ["D", "E"]},
    { "code" :  "D", "duration":  2, "next" : ["E", "G"]},
    { "code" :  "E", "duration":  3}
  ]
}
```

The content conveys the following pieces of information:
* The project name is Martian.
* The project starts on September 25, 2019. It is in 'yyyyMMdd' format. When not provided, the system will use the current date.
* The project is timed by month. So, the schedule will be calculated by month. Other options are "day", "week", and "year". When not provided, the system will use "day" by default.
* There are five activities: A, B, C, D, E. Note, short codes are only used for brevity.
* Activity details:
```
A
	- duration = 5
	- no dependencies
	- followed by C and D
	
B
	- duration = 6
	- no dependencies
	- no successors

C
	- duration = 3
	- depends on A
	- followed by D and E

D
	- duration = 2
	- depends on A and C
	- followed by E and G	

E
	- duration = 3
	- depends on C and D
	- no successors
```

### Using the Application

After running the application, navigate to 127.0.0.1:8080. This opens the home page. In case of conflict on port 8080 change the server.port value in [application.properties](https://github.com/jodyguillen/springboot-project-scheduler/blob/springboot-project-scheduler-v2/src/main/resources/application.properties) to a different value.

On the home page, upload the target json file. You may use [martian.json](https://github.com/jodyguillen/springboot-project-scheduler/blob/springboot-project-scheduler-v2/src/main/resources/json/martian.json) - the sample json file bundled in the project. 

After a successful upload, the activities page opens where it shows the name of the project along with its respective activities and their duration. Click the "Schedule" button to calculate the schedule. 

Upon a successful schedule calculation, the calendar page opens where it shows the project name and the activities per date. The date increments change depending on the time unit used.

Retest by going back to the home page by clicking the "Home" button or manually navigating to 127.0.0.1:<port>.


## Author
Jody Guillen | [LinkedIn](https://www.linkedin.com/in/jodelyn-guillen-mscs-89b46b110/)
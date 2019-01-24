# Playground application

Spring application which manage the backend of Playground

___

# Installation

- Install Java 8 (Set JAVA_HOME : https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/)

## Project Properties

Before runing the spring application, you have to set configure the project. All the setting are in the `application.properties` file

```
spring.datasource.url=[database url]
spring.datasource.username=[database username]
spring.datasource.password=[database password]
playground.img.playground=[path to save playground image]
playground.img.user=[path to save user avatar]
```
Try to use the actual database. The databse is running on a Rasp. But if it doesn't work tou can use a local database
If you use a local database, import `playground.sql` (/playground-app/tree/develop/spring/src/main/resources)

# Run Project

- On first run, you have to type : `.mvnw install` to install dependencies
- Open a terminal and type command : `./mvnw spring-boot:run` to run spring boot application

___

# API Documentation

API which allow access to playground app datas

- [CommentController](./apidoc/CommentController.md)
- [PlaygroundController](./apidoc/PlaygroundController.md)
- [ReportCommentController](./apidoc/ReportCommentController.md)
- [ReportPlaygroundController](./apidoc/ReportPlaygroundController.md)
- [RoleController](./apidoc/RoleController.md)
- [ScheduleController](./apidoc/ScheduleController.md)
- [SessionController](./apidoc/SessionController.md)
- [SportController](./apidoc/SportController.md)
- [UserController](./apidoc/UserController.md)

___

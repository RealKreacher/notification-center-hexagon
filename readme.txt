# Start
---
## Requirements
------
- Java
    - =< 17
## Profiles
------
### default
---------
Without any specific profile the application.yaml is taken as property file. Only thing that is specified is path to the dataset.
With default profile you can specify path to custom data set by creating variable NOTIFICATION_DATA. If not specified there is default path to dataset stored in project resources folder.

### local
---------
By specifying local profile the application-local.yaml properties are used.
The only difference is that it just take the dataset from resources folder.

## IDE
------
If you are using IDEA or other IDE you should be able to create Spring boot configuration and run it without any other configuration.

## Terminal
------
To start the application from terminal:
- unzip the project
- navigate to the folder
- run command: `mvn spring-boot:run -Dspring-boot.run.profiles=local`
    - The `-Dspring-boot.run.profiles=local` can be omitted and then the *default* profile will be taken.


# API
---
There is swagger-ui at {host}:{port}/v1/swagger-ui/index.html#/ that provides documentation for the apis including responses.

Here I provides basic description of the api and how the call would look like:
## Get
------
### /v1/user/notifications/
---------
Provide list of notifications for given user with flag if notification is read or not.

curl --request GET \
  --url 'http://localhost:8080/user/notifications?userId={userId}'

### /v1/user/notifications/{notificationId}/
---------
Provide detail for notification with given ID

curl --request GET \
  --url 'http://localhost:8080/v1/user/notifications/{notificationId}?userId={userId}'

## Patch
------
## /v1/user/notifications/{notificationId}/
---------
Mark specified notification as read

curl --request PATCH \
  --url 'http://localhost:8080/v1/user/notifications/{notificationId}?userId={userId}'

## Delete
------
### /v1/user/notifications/{notificationId}/
---------
Delete specified notification

curl --request DELETE \
  --url 'http://localhost:8080/v1/user/notifications/{notificationId}?userId={userId}'
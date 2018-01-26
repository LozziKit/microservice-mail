# LozziKit
## Micro Service Mail
Micro service for mails, permits the user to send mail to an SMTP server. Implements a delay in order not to spam the SMTP server too much. It also allows the user to cancel a job.

This micro service is binded with a template service.

## Micro Service Template
Micro Service Template allows the user to create tempates for emails with variables.

## Testing
In order to test the service
```
$ mvn clean package
$ ./integration-test.sh
```

This will build the project, create the docker images and then run docker compose with customs arguments.
Arguments are there to run the test faster (Job timeout is 100ms instead of 20s) and to set the SMTP server to cucumber.

The Gerkhins scenarios can be found in ` swagger/cucumber/src/test/resources/scenarios ` they specify the behaviours of both mail and template requirements

## Production
In order to lauch the service
```
$ mvn clean package
$ docker-compose up
```

This will lauch the Spring Sever with the Swagger specification.

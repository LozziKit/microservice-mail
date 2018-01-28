# LozziKit
## Micro Service Mail
Micro service for mails, permits the user to send mail to an SMTP server. Implements a delay in order not to spam the SMTP server too much. It also allows the user to cancel a job.

This micro service is binded with a template service. You can find a Web interface [here](https://github.com/LozziKit/microservice-mail-interface).

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

This will lauch the full Spring Sever with the Swagger specification. From that point you might want to lauch the Web interface

## REST API
This service exposes a REST API. Its specification can be found in ` swagger/api-spec.yaml ` . Here is a brief introduction to the three kinds of endpoints. You can also use this [link](https://editor.swagger.io//?_ga=2.20399164.544215540.1517157778-2137575029.1517157778#/) to see a better representation of the API

### Mail
You can GET a history of every mail created and their content, GET a mail from its id and POST a new mail.

The fields of a new mail are : name of template to use, from, to, cc, cci, and a map of variable names from the template and their values.

### Job
When a mail is created, the service creates and sends back a job. A job contains its location (url), the location of the mail, and its status (DONE, FAILED, ONGOING, or INVALD). You can GET one or all jobs, as well as DELETE a job (cancels the expedition of a mail if it's not too late).

### Template
You can GET all templates, POST a new template, as well as GET, PUT, and DELETE a template by name.

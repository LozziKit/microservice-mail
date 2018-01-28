# LozziKit
## Micro Service Mail
Micro service for mails, permits the user to send mail to an SMTP server. Implements a delay in order not to spam the SMTP server too much. It also allows the user to cancel a job.

This micro service is binded with a template service.

## Micro Service Template
Micro Service Template allows the user to create tempates for emails with variables. Templates make sending batch mails easier. You can CRUD templates with variables and then use it to specify some words in the mails.

```
Hello ${NAME},

Welcome to ${WEBSITE} !
Hope you will enjoy the content, don't forget to like and subscribe !
```
Called with `Bykow` and  `Youtube` would produce the following mail:

```
Hello Bykow, 
Welcome to Youtube !
Hope you will enjoy the content, don't forget to like and subscribe !
```



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
This service exposes a REST API. Its specification can be found in ` swagger/api-spec.yaml ` . Here is a brief introduction to the three kinds of endpoints.

### mail
You can GET a history of every mail created and their content, GET a mail from its id and POST a new mail.

The fields of a new mail are : name of template to use, from, to, cc, cci, and a map of variable names from the template and their values.

### job
When a mail is created, the service creates and sends back a job. A job contains its location (url), the location of the mail, and its status (DONE, FAILED, ONGOING, or INVALD). You can GET one or all jobs, as well as DELETE a job (cancels the expedition of a mail if it's not too late).

### template
You can GET all templates, POST a new template, as well as GET, PUT, and DELETE a template by name.

## Web Interface
This app allows the administration of the Lozzikit - Mail service. 
It was develloped during the TWEB course at the University of Applied Sciences of Yverdon.

First thing you need to do in order to lauch the Web interface is 
```
$ cd interface
```

```
$ npm install
```
Install the dependencies of the app.


```
$ npm start
```
Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

```
$ npm run build
```

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

### Usage

For a correct usage of this app, the Lozzikit - Mail microservice must be running (for example with the docker-compose configuration file) and the app must be configured with the proper URL to reach the microservice.

If the microservice can't be reached at the same adress and port as the host of the app then the host must enable Cross-Origin Resource Sharing.

# Thanks

This project was bootstrapped with [Create React App](https://github.com/facebookincubator/create-react-app).

This project uses the following project:

* [Material-UI](https://material-ui-next.com/) for the user interface.
* [React Router DOM](https://reacttraining.com/react-router/) for the routing of the single-page.
* [Superagent](https://github.com/visionmedia/superagent) for the api request.


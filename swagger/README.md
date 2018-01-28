# Introduction

This micro service allows the administration of the Lozzikit - Mail service to deploy.
It was developed during the AMT course at the University of Applied Sciences of Yverdon.

## Micro service Mail

This micro service provides a mail and template intergration.

Mail is designed to send mails by transfering them to a SMTP server. It implements a small delay between each job to avoid server overloads. This delay can also be used to cancel a job.<br>
Furthermore, it stores all jobs in a database, thus allowing to keep track of what mails are sent. 

Template provides a canvas to send a form. It allows the user to create a predefined mail with variables. It can be used with specifications of these variables to send mail batches.


## Installation

```
$ mvn clean package
$ docker-compose up
```
Installs the dependencies of the micro service and runs it in docker virutalization setup. 

## Testing with scenarios

We added a test configuration to ensure our micro-service was working as intented. We used Cucumber testing.<br>
To run the test:

```
$ mvn clean package
$ ./integration-test.sh
```

This test will run a Wiser mock SMTP server to ensure that mails are properly sent. The server allows to reduce the inital delay of 20 seconds *neerly* to 0.

# Usage

For this micro-service to function properly we recommand using the web interface we provide in the `interface` folder. 

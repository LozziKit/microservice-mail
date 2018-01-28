# Introduction

This micro service allows the administration of the Lozzikit - Mail service to deploy.
It was developed during the AMT course at the University of Applied Sciences of Yverdon.

## Micro service Mail

This micro service provides a mail and template intergration.

Mail provides is able to send mails, by passing them to a SMTP server. It implements a small delay between each job to avoid an overloading of the server. This delay can also be used to cancel a job.<br>
It also stores all jobs in a database in order to keep track of what mails are sent. 

Template provides a canevas to send a form. It allows the user to create a predefined mail with variable. It can then be used with specifications of those variable to send mail batches.


## Installation

```
$ mvn clean package
$ docker-compose up
```
Installs the dependencies of the micro service and run it in docker virutalization setup. 

## Testing with scenarios

We added a test configuration to ensure our micro-service was working as intented. We used Cucumber testing.<br>
In order to run the test:

```
$ mvn clean package
$ ./integration-test.sh
```

Those test will run a Wiser mock SMTP server to ensure the mails are properly sent. The delay is *almost* reduced to 0, to make the tests run much faster. Otherwise we should wait 20 seconds between each mail...

# Usage

For a correct usage of this micro service, we recommand using the web interface we provide in the `interface` folder. 

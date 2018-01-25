Feature: Manipulation of mails

  Background:
    Given A mail endpoint
    And A job endpoint
    And A SMTP server

  Scenario: Fetch all archived mails when database has none
    When I GET on the /mails endpoint
    Then I receive a 204 status code

  Scenario: Fetch all jobs when database has none
    When I GET on the /jobs endpoint
    Then I receive a 204 status code

  Scenario: Create a template
    Given A template payload
    When I POST the payload to the /templates endpoint
    Then I receive a 201 status code

  Scenario: Send some mail
    Given A mail payload
    When I POST the payload to the /mails endpoint
    Then I receive a 201 status code
    And I receive a list of job payload
    And I wait 600 milliseconds
    And The SMTP server has received the corresponding mail

  Scenario: Send and cancel a mail
    Given A unique mail payload
    When I POST the payload to the /mails endpoint
    And I wait 5 milliseconds
    And I DELETE the job on the /jobs/id endpoint
    Then I receive a 204 status code
    And I wait 300 milliseconds
    And The SMTP server has not received the unique mail

  Scenario: Send an invalid mail payload
    Given An invalid mail payload
    When I POST the payload to the /mails endpoint
    Then I receive a job with invalid status

  Scenario: Send an mail with an invalid template
    Given A mail with an invalid template
    When I POST the payload to the /mails endpoint
    Then I receive a job with invalid status

  Scenario: Fetch all archived mails
    When I GET on the /mails endpoint
    Then I receive a 200 status code
    And I receive a list of mails payload

  Scenario: Fetch an archived mail
    Given A mail id
    When I GET on the /mails/id endpoint
    Then I receive a 200 status code
    And I receive an archived mail payload

  Scenario: Fetch an non-existing archived mail
    Given A non-existing mail id
    When I GET on the /mails/id endpoint
    Then I receive a 404 status code

  Scenario: Fetch all jobs
    When I GET on the /jobs endpoint
    Then I receive a 200 status code
    And I receive a list of job payload

  Scenario: Fetch a job
    Given A job id
    When I GET on the /jobs/id endpoint
    Then I receive a 200 status code
    And I receive a job payload

  Scenario: Fetch an non-existing job
    Given A non-existing job id
    When I GET on the /jobs/id endpoint
    Then I receive a 404 status code

  Scenario: Delete a non-existing job
    Given A non-existing job id
    When I DELETE on the /jobs/id endpoint
    Then I receive a 404 status code

  Scenario: Delete an already processed job
    Given A processed job id
    When I DELETE on the /jobs/id endpoint
    Then I receive a 410 status code
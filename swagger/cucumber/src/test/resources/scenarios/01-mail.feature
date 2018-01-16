Feature: Manipulation of mails

  Background:
    Given A mail endpoint
    And A job endpoint
    And A filled template database
    And A SMTP server

  Scenario: Fetch all archived mails when database has none
    Given An empty job and mail database
    When I GET on the /mails endpoint
    Then I receive a 204 status code

  Scenario: Fetch all archived mails
    Given A filled job and mail database
    When I GET on the /mails endpoint
    Then I receive a 200 status code
    And I receive a list of mails payload

  Scenario: Send some mail
    Given A mail payload
    When I POST the payload to the /mails endpoint
    Then I receive a 201 status code
    And I receive a list of jobs payload
    And I wait 300 milliseconds
    And The SMTP server has received the corresponding mail

  Scenario: Send and cancel a mail
    Given A unique mail payload
    When I POST the payload to the /mails endpoint
    And I wait 5 milliseconds
    And I DELETE the job on the /jobs/id endpoint
    Then I receive a 204 status code
    And I wait 300 milliseconds
    And The SMTP server has not received the unique mail

  Scenario: Fetch an archived mail
    Given A filled job and mail database
    And A mail id
    When I GET on the /mails/id endpoint
    Then I receive a 200 status code
    And I receive a archived mail payload

  Scenario: Fetch an non-existing archived mail
    Given A non-existing mail id
    When I GET on the /mails/id endpoint
    Then I receive a 404 status code

  Scenario: Fetch all jobs when database has none
    Given An empty job and mail database
    When I GET on the /jobs endpoint
    Then I receive a 204 status code

  Scenario: Fetch all jobs
    Given A filled job and mail database
    When I GET on the /jobs endpoint
    Then I receive a 200 status code
    And I receive a list of jobs payload

  Scenario: Fetch a job
    Given A filled job and mail database
    And A job id
    When I GET on the /jobs/id endpoint
    Then I receive a 200 status code
    And I receive a job payload

  Scenario: Fetch an non-existing job
    Given A non-existing job id
    When I GET on the /jobs/id endpoint
    Then I receive a 404 status code

  Scenario: Delete a job in progress
    Given A filled job and mail database
    And An in-progress job id
    When I DELETE on the /jobs/id endpoint
    Then I receive a 204 status code

  Scenario: Delete a non-existing job
    Given A non-existing job id
    When I DELETE on the /jobs/id endpoint
    Then I receive a 404 status code

  Scenario: Delete an already processed job
    Given A filled job and mail database
    And A processed job id
    When I DELETE on the /jobs/id endpoint
    Then I receive a 410 status code
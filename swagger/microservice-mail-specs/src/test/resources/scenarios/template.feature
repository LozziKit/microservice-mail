Feature: Creation of templates

  Background:
    Given there is a templates endpoint

  Scenario: create a template
    Given I have a template payload
    When I POST the payload to the /templates endpoint
    Then I receive a 201 status code

  Scenario: Bad creation of template
    Given I have a bad template payload
    When I POST the payload to the /templates endpoint
    Then I receive a 400 status code

  Scenario: fetch all templates
    Given I want to fetch all template without an id
    When I GET without an id to the /templates endpoint
    Then I receive a 200 status code
    And I receive multiple template payloads

  Scenario: fetch all templates
    Given I want to fetch all templates but DB is empty
    But There is not template in DB
    When I GET to the /templates endpoint
    Then I receive a 204 status code

  Scenario: fetch a template
    Given I have a template id
    When I GET the id to the /templates endpoint
    Then I receive a 200 status code
    And I receive a template payload

  Scenario: fetch an unexisting template
    Given I have an unexisting template id
    When I GET the id to the /templates endpoint
    Then I receive a 404 status code

  Scenario: Bad Request
    Given I have a request
    When I GET to the /templates endpoint
    Then I receive a 400 status code



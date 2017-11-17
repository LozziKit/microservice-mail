Feature: Creation of templates

  Background:
    Given A template endpoint

  Scenario: Fetch all templates when database has none
    Given An empty database
    When I GET on the /templates endpoint
    Then I receive a 204 status code

  Scenario: Fetch all templates
    Given A filled database
    When I GET on the /templates endpoint
    Then I receive a 200 status code
    And I receive multiple template payloads

  Scenario: Fetch a template
    Given A template id
    When I GET on the /templates/id endpoint
    Then I receive a 200 status code
    And I receive a template payload

  Scenario: Create a template
    Given A template payload
    When I POST the payload to the /templates endpoint
    Then I receive a 201 status code

  Scenario: Bad creation of template
    Given I have a bad template payload
    When I POST the payload to the /templates endpoint
    Then I receive a 406 status code

  Scenario: Fetch an non-existing template
    Given A non-existing template id
    When I GET on the /templates/id endpoint
    Then I receive a 404 status code

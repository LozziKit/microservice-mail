Feature: Creation of templates

  Background:
    Given A template endpoint

  Scenario: Fetch all templates when database has none
    When I GET on the /templates endpoint
    Then I receive a 204 status code

  Scenario: Create a template
    Given A template payload
    When I POST the payload to the /templates endpoint
    Then I receive a 201 status code

  Scenario: Bad creation of template
    Given A bad template payload
    When I POST the payload to the /templates endpoint
    Then I receive a 406 status code

  Scenario: Fetch all templates
    When I GET on the /templates endpoint
    Then I receive a 200 status code
    And I receive multiple template payloads

  Scenario: Fetch a template
    Given An existing template name
    When I GET on the /templates/name endpoint
    Then I receive a 200 status code
    And I receive a template payload

  Scenario: Fetch an non-existing template
    Given A non-existing template name
    When I GET on the /templates/name endpoint
    Then I receive a 404 status code

  Scenario: Update a template
    Given An existing template name
    And A template payload
    When I PUT on the /templates/name endpoint
    Then I receive a 204 status code
    When I GET on the /templates/name endpoint
    Then I receive a 200 status code

  Scenario: Update a non existing template
    Given A non-existing template name
    And A template payload
    When I PUT on the /templates/name endpoint
    Then I receive a 404 status code

  Scenario: Update with an invalid template
    Given An existing template name
    And A bad template payload
    When I PUT on the /templates/name endpoint
    Then I receive a 406 status code

  Scenario: Delete an existing template
    Given An existing template name
    When I DELETE on the /templates/name endpoint
    Then I receive a 204 status code
    When I GET on the /templates/name endpoint
    Then I receive a 404 status code

  Scenario: Delete a non existing template
    Given A non-existing template name
    When I DELETE on the /templates/name endpoint
    Then I receive a 404 status code

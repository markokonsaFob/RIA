@add
Feature: Adding new information system descriptions

  Background: User opens RIHA website
    Given user opens RIHA website
    Then user should be on the RIHA landing page

  @clear_table
  Scenario: Adding information system description without name field
    When user clicks on a add new button
    Then user should be on form view
    When user enters "Cify" into SHORTNAME field
    And user enters "https://github.com/fobsolutions/cify-framework" into DOCUMENTATION field
    And user clicks save button
    Then user should be on form view
    And NAME field should be INCORRECT
    And SHORTNAME field should be CORRECT
    And DOCUMENTATION field should be CORRECT

  @clear_table
  Scenario: Adding new information system description with valid information
    When user clicks on a add new button
    Then user should be on form view
    When user enters "Cify Test Automation Framework" into NAME field
    And user enters "Cify" into SHORTNAME field
    And user enters "https://github.com/fobsolutions/cify-framework" into DOCUMENTATION field
    And user clicks save button
    Then user should be on the RIHA landing page
    And newly added information system details should be visible
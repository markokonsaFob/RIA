@delete
Feature: Deleting information system details

  Background: User adds one information system description
    Given user opens RIHA website
    Then user should be on the RIHA landing page
    When user clicks on a add new button
    Then user should be on form view
    When user enters unique ID into NAME field
    And user enters "Cify Runner" into SHORTNAME field
    And user enters "https://github.com/fobsolutions/cify-runner" into DOCUMENTATION field
    And user clicks save button
    Then user should be on the RIHA landing page
    And newly added information system details should be visible

  @clear_table
  Scenario: Deleting previously entered information system description
    When user clicks on delete button on newly added details
    Then user should be on the RIHA landing page
    And information system description should be deleted

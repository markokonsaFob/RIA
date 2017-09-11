@edit
Feature: Changing information system description

  Background: User adds one information system description
    Given user opens RIHA website
    Then user should be on the RIHA landing page
    When user clicks on a add new button
    Then user should be on form view
    When user enters "Cify Test Automation Runner" into NAME field
    And user enters "Cify Runner" into SHORTNAME field
    And user enters "https://github.com/fobsolutions/cify-runner" into DOCUMENTATION field
    And user clicks save button
    Then user should be on the RIHA landing page
    And newly added information system details should be visible

  @clear_table
  Scenario: Changing system description with valid information
    When user clicks on change button on newly added details
    Then user should be on form view
    When user enters "FOB's GitHub" into NAME field
    And user enters "GitHub" into SHORTNAME field
    And user enters "https://github.com/fobsolutions" into DOCUMENTATION field
    And user clicks save button
    Then user should be on the RIHA landing page
    And newly added information system details should be visible

  @clear_table
  Scenario: Changing system description without correct name field
    When user clicks on change button on newly added details
    Then user should be on form view
    When user enters "" into NAME field
    And user clicks save button
    Then user should be on form view
    And NAME field should be INCORRECT
    And SHORTNAME field should be CORRECT
    And DOCUMENTATION field should be CORRECT
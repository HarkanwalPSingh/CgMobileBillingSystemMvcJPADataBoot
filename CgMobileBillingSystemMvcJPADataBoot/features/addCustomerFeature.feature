Feature: Add new Customer to Database Feature
  Using this feature the user can add new feature to database
  
  Scenario: User enters all given fields correctly and new customer is added in the database
    Given User is on Add Customer Page
    When Enters correct input values
    And Click submit button
    Then New Customer will be added to database

 Scenario: User enters all given fields incorrectly and error message is shown for incorrect values entered
   Given User is on Add Customer Page
   When Enters incorrect input values
   And Click submit button
   Then Error message is shown corresponding to incorrect values entered


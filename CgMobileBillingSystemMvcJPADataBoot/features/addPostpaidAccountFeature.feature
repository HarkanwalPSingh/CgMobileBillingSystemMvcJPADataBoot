Feature: Add new Postpaid Account Feature
  Using this user can add new postpaid account for given Customer ID
  Scenario: User wants to add new postpaid account for his/her Customer account
    Given User is on Add Postpaid Account Page
    When Enters valid 'Customer ID' 
    And Selects plan 
    Then New Postpaid Account is generated
    
    Scenario: User wants to add new postpaid account for his/her Customer account using invalid Customer ID
    Given User is on Add Postpaid Account Page
    When Enters invalid 'Customer ID' 
    And Selects plan 
    Then Error message displayed showing invalid 'Customer ID'
Feature: Get Customer Details Feature
  Using this feature the use can retrieve Customer Details from database
  Scenario: User wants to retrieve Customer Details using valid Customer ID
    Given User is on Get Customer Details Page
    When User enters valid Customer ID
    And Clicks Submit button
    Then Customer Details Displayed on Customer Details Page 
    
  Scenario: User wants to retrieve Customer Details using invalid Customer ID
    Given User is on Get Customer Details Page
    When User enters invalid Customer ID
    And Clicks Submit button
    Then Error message is displayed showing invalid Customer ID
    
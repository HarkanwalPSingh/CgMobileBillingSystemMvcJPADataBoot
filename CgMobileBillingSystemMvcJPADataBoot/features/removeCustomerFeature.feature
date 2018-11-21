Feature: Remove Customer From Database Feature
  Using this feature the user can remove customer from database
		Scenario: User wants to remove customer details for a particular customer id
    Given User is on Remove Customer Page
    When Enters valid Customer ID
    And Clicks Submit Button
    Then Customer gets removed from database and customer removed success page is displayed
    
    Scenario: User wants to remove customer details for a invalid customer id
    Given User is on Remove Customer Page
    When Enters invalid Customer ID
    And Clicks Submit Button
    Then Invalid Customer ID message displayed on Remove Customer Page
    

Feature: Test
Scenario: Navigate
When I navigate to "http://localhost:8080"
Then I expect visible element having linkText "Hello Sample Test"
And I expect visible element having partialLinkText Sample
And I expect visible element having id button3
And I expect visible element having id "button3"
And I expect visible element having partialText Sample
And I expect visible element having text "Hello Sample Test"
When I click element having id button1
Then I expect visible alert having partialText "Do you confirm"
When I accept alert
Then I expect hidden alert


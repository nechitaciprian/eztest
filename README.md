# EZTest - WEB UI testing framework

## What is it?
Is it a set of tools and sample projects to create automation scripts for web based applications.
The EZTest scripting language is an abstraction to be used to create test cases targeting web applications.
It follows a BDD approach, meaning the testing scripts are very similar to the plain language used to describe the test cases. 


## How is build?
The source code is in Java using Cucumber and Selenium.

## Capabilities
- Use Case Scripting are easy to create and maintain by Developers or Testers with the following skills: HTML, CSS, XPATH.
- Test scripts are cross browser. The test scripts can run on different browsers using Selenium WebDriver or Selenium Grid.
- All the scripts steps include implicit waits so that page loads or partial page reloads through AJAX does not need to be an explicit step.
- Multiple runners: Standalone(future), JUnit, TestNG(future), Eclipse Plugin(future)
- Script Editor: Eclipse Plugin(future)

## Sample

`Feature: Test`  
`Scenario: Navigate`  
`When I navigate to "http://localhost:8080"`  
`Then I expect visible element having linkText "Hello Sample Test"`  
`And I expect visible element having partialLinkText Sample`  
`And I expect visible element having id button3`  
`And I expect visible element having id "button3"`  
`And I expect visible element having partialText Sample`  
`And I expect visible element having text "Hello Sample Test"`  
`When I click element having id button1`  
`Then I expect visible alert having partialText "Do you confirm"`  
`When I accept alert`  
`Then I expect hidden alert`  

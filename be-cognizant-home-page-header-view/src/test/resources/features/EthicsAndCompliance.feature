#Author: akanksha.sharma03@cognizant.com
Feature: Ethics & Compliance

  Scenario: User can navigate to Ethics & Compliance
    Given user on becognizant home page
    When user navigates to ethics and compliance
    Then user is redirected to ethics and compliance

  Scenario: All Resource Links should work
    Given user on ethics and compliance page
    When user tries openening all resources and links
    Then all links and resources should be working

  Scenario Outline: key focus areas listed
    Given user on ethics and compliance page
    When user looks at focus area
    Then user finds <focusarea>

    Examples: 
      | focusarea                                         |
      | "Global Anti-Corruption"                          |
      | "Global Trade Compliance"                         |
      | "Internal Investigations"                         |
      | "Ethics & Compliance Training and Communications" |

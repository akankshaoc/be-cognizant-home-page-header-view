#Author: akanksha.sharma03@cognizant.com
Feature: Navigation

  Background: 
    Given user on becognizant home page

  Scenario: User Information Display
    When user clicks on user icon
    Then user info displayed

  Scenario: User Navigates Through Nav Bar
    Given user records all links available on nav bar
    When user visits each of them
    Then all links should work

  Scenario: User Navigates Through Nav Bar
    Given user records all links available on nav bar
    Then all links should be listes as writen in navigation json
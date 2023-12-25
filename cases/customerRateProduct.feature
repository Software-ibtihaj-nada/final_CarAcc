
Feature: customer 
  Scenario: customer rate product 
    Given customerr was login
    When customer want to rate product with id "95" and set new evaluation for product "3"
    Then customer rate product succsessfully

  Scenario: customer make installation
    Given customerr was login
    When customer with email "nour@gmail.com" choose  installer with id "7"
    And day is "Monday" 
    And installation request is "change weels" 
    And carmodel is "BMW"
    Then customer  make installation succsessfully

   
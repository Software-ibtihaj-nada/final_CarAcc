
Feature: installer

  Scenario: installer view his installation request
    Given  installer was log in
    When installer with email "omar@gmail.com" want to view his installation request
    Then installer can view his installation request 
    
    Scenario: admin view installer 
    Given  admin was log in
    When admin want to view installer
    Then all installer will print
    
     Scenario: installer make installation request done
    Given  installer was log in
    When installer want to make installation request with id "11" done
    Then installation request done
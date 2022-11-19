Feature: Login user
  As a user
  I want to login in to application

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/accounts" is available


  Scenario: User login
    When A User login request is sent with values "yoimerdr", "12345678"
    And Account exist
    Then A Account with status 200 is received


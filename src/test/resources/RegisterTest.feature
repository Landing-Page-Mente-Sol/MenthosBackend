Feature: Login user
  As a user
  I want to register in to application

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/accounts" is available


  Scenario: User register
    When A User register request is sent with values "yoimerdr", "12345678", "1"
    And username with value "yoimerdr" doesn't exist
    Then A Account with status 201 is received


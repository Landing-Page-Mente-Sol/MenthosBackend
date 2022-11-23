Feature: Rate Answers
As user
I want to rate answers

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/"users is available

  @rate-answers
  Scenario: Rate answer
    When A Rate Answer Request is sent with values 1, 100
    Then A User with status 200 is received
Feature: Make Questions
  As User
  I want to make a question int the app

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/"questions is available

  @user-make-question
  Scenario: User make question
    When A Question request is sent with values "This is a question title test", "This is a question description test", "11/23/2022", 1, 1
    Then A Question with status 201 is received

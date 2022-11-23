Feature: Make Answers
  As User
  I want to make a answers into the questions

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/"answers is available

  @user-make-answer
  Scenario: User make answer
    When A Answer request is sent with values "This is a answer description", "11/23/2022", 1, 1
    Then A Answer with status 201 is received

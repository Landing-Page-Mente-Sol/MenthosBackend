Feature: Login User
As user i want to login into app
  Background:
    Given The Endpoint "http://localhost:%d/api/v1/"accounts is available

  @user-login
  Scenario: User login
    When A Login Request is sent with values "username", "password"
    Then A Login status with code 200 is sent
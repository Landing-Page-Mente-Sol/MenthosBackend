Feature: Register user
  As user
  I want to register into the application

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/"users is available

  @register-student
  Scenario: Register student
    When A User Request is sent with values "Diego", "De la Cruz", "student", "u20201b973@upc.edu.com", "U20201B973", "Ingeniería de Software", 5, 0, "username", "password"
    Then A User with status 201 is received

  @register-tutor
  Scenario: Register tutor
    When A User Request is sent with values "Diego", "De la Cruz", "tutor", "u20201b973@upc.edu.com", "U20201B973", "Ingeniería de Software", 5, 0, "username", "password"
    Then A User with status 201 is received
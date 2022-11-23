Feature: User Adding
  As a Developer
  I want to add User through API
  So that it can be available to application

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/"users is available

  @add-user
  Scenario: Add User
    When A User Request is sent with values "Diego", "De la Cruz", "student", "u20201b973@upc.edu.com", "U20201B973", "Ingeniería de Software", 5, 0
    Then A User with status 201 is received

  @delete-user
  Scenario: Delete User
    When A User Delete is sent with id value "2"
    Then A User with status 200 is received

  @get-user-by-id
  Scenario: Get User by id
    When A User Selected is sent with value "1"
    Then A User with status 200 is received

  @get-all-users
  Scenario: Get All Users
    When All Users who are registered en DB
    Then List of Users with status 200 is received

  @update-user-by-id
  Scenario: Update User by Id
    When A User Updated is sent with values "3", "Yoimer", "Davila", "tutor", "u20201b973@gmail.com", "U20201B973", "Ingeniería de Software", 5, 0
    Then A User with status 200 is received
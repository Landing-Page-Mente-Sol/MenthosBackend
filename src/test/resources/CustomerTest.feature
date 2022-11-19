Feature: Customer Adding
  As a Developer
  I want to add Customer through API
  So that it can be available to application

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/users" is available

  @add-user
  Scenario: Add Customer
    When A Customer Request is sent with values "Diego", "De la Cruz", "student", "u20201b973@upc.edu.com", "U20201B973", "Ingeniería de Software", 5, 0
    Then A Customer with status 201 is received

  @delete-user
  Scenario: Delete Customer
    When A Customer Delete is sent with id value "2"
    Then A Customer with status 200 is received

  @get-user-by-id
  Scenario: Get Customer by id
    When A Customer Selected is sent with value "1"
    Then A Customer with status 200 is received

  @get-all-users
  Scenario: Get All Customers
    When All Customers who are registered en DB
    Then List of Customers with status 200 is received

  @update-user-by-id
  Scenario: Update Customer by Id
    When A Customer Updated is sent with values "3", "Yoimer", "Davila", "tutor", "u20201b973@gmail.com", "U20201B973", "Ingeniería de Software", 5, 0
    Then A Customer with status 200 is received
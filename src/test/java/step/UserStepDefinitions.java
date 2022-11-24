package step;

import com.api.mentosbackend.entities.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.api.mentosbackend.entities.User;
import org.springframework.http.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStepDefinitions extends BasicCucumberDefinitions<User> {


    @Given("The Endpoint {string}users is available")
    public void theEndpointUsersIsAvailable(String endpointPath) {
       this.createEndpoint(endpointPath + "users");
    }

    @When("A User Request is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {int}, {int}")
    public void aUserRequestIsSentWithValues(String firstname, String lastname, String userType, String email, String upcCode, String career, int cycle, int points) {
        User user = new User(0L, firstname, lastname, userType, email, upcCode, career, cycle, (long) points);

        this.postForEntity(user, this.endpointPath, User.class);
    }

    @Then("A User with status {int} is received")
    public void aUserWithStatusIsReceived(int expectedStatus) {
        this.validateStatusCode(expectedStatus);
    }

    @When("A User Delete is sent with id value {string}")
    public void aUserDeleteIsSentWithIdValue(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
        testRestTemplate.delete(endpointPath + "/{id}", params);
        this.responseEntity = new ResponseEntity<>(HttpStatus.OK);
    }

    @When("A User Selected is sent with value {string}")
    public void aUserSelectedIsSentWithValue(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
        User user = testRestTemplate.getForObject(endpointPath + "/{id}", User.class,params);
        this.responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
    }

    @When("All Users who are registered en DB")
    public void allUsersWhoAreRegisteredEnDB() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        responseEntity = testRestTemplate.exchange(endpointPath, HttpMethod.GET, entity, User.class);
        System.out.println(responseEntity);
    }

    @Then("List of Users with status {int} is received")
    public void listOfUsersWithStatusIsReceived(int expectedStatus) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatus).isEqualTo(actualStatusCode);
    }

    @When("A User Updated is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {string}, {int}, {int}")
    public void aUserUpdatedIsSentWithValues(String userId, String firstname, String lastname, String userType, String email, String upcCode, String career, int cycle, int points) {
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
        User customerUpdated = new User(0L, firstname, lastname, userType, email, upcCode, career, cycle, (long) points);
        testRestTemplate.put(this.endpointPath + "/{id}", customerUpdated, params);
        responseEntity = new ResponseEntity<>(customerUpdated, HttpStatus.OK);
    }

    @When("A Rate Answer Request is sent with values {int}, {int}")
    public void aRateAnswerRequestIsSentWithValues(int userId, int points) {
        Map<String, String> params = new HashMap<>();
        String id = String.valueOf(userId);
        params.put("id", id);
        User user = testRestTemplate.getForObject(endpointPath + "/{id}", User.class,params);
        user.setPoints(user.getPoints() + points);
        testRestTemplate.put(this.endpointPath + "/{id}", user, params);
        responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
    }

    @When("A User Request is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {int}, {int}, {string}, {string}")
    public void aUserRequestIsSentWithValues(String firstname, String lastname, String userType, String email, String upcCode, String career, int cycle, int points, String username, String password) {
        this.aUserRequestIsSentWithValues(firstname, lastname, userType, email, upcCode, career, cycle, points);
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(responseEntity.getBody(), User.class);

        String accountEndpoint = this.endpointPath.replace("users", "accounts");
        Account account = new Account(0L, username, password, user);

        BasicCucumberDefinitions<Account> definitions = new  BasicCucumberDefinitions<>();

        definitions.postForEntity(this.testRestTemplate, account, accountEndpoint + "/" + user.getId(), Account.class);

        if(definitions.responseEntity.getStatusCodeValue() == 201)
            this.responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);

    }
}

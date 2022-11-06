package step;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.api.mentosbackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStepDefinitions {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;
    private String endpointPath;
    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available")
    public void theEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
    }

    @When("A User Request is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {int}, {int}")
    public void aUserRequestIsSentWithValues(String firstname, String lastname, String userType, String email, String upcCode, String career, int cycle, int points) {
        User user = new User(0L, firstname, lastname, userType, email, upcCode, career, cycle, (long) points);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        responseEntity = testRestTemplate.postForEntity(this.endpointPath, request, String.class);
    }

    @Then("A User with status {int} is received")
    public void aUserWithStatusIsReceived(int expectedStatus) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        System.out.println(actualStatusCode);
        assertThat(expectedStatus).isEqualTo(actualStatusCode);
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
        this.responseEntity = new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }

    @When("All Users who are registered en DB")
    public void allUsersWhoAreRegisteredEnDB() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        responseEntity = testRestTemplate.exchange(endpointPath, HttpMethod.GET, entity, String.class);
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

        responseEntity = new ResponseEntity<>(customerUpdated.toString(), HttpStatus.OK);
    }
}

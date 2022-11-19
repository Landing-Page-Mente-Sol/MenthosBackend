package step;

import com.api.mentosbackend.entities.Account;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginStepDefinitions {

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

    @When("A User login request is sent with values {string}, {string}")
    public void aUserLoginRequestIsSentWithValues(String username, String password) {
        Map<String, String> params = new HashMap<>();

        params.put("username", username);
        params.put("password", password);

        Account account = testRestTemplate.getForObject(this.endpointPath + "/search/username/{username}/password/{password}", Account.class, params);

        this.responseEntity = new ResponseEntity<>(account.toString(), HttpStatus.OK);
    }

    @And("Account exist")
    public void accountExist() {
    }

    @Then("A Account with status {int} is received")
    public void aAccountWithStatusIsReceived(int expectedStatus) {
        int actualStatusCode = responseEntity.getStatusCodeValue();

        assertThat(expectedStatus).isEqualTo(actualStatusCode);
    }
}

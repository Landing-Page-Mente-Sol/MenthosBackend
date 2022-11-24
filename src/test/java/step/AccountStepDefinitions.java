package step;

import com.api.mentosbackend.entities.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class AccountStepDefinitions extends BasicCucumberDefinitions<Account> {
    @Given("The Endpoint {string}accounts is available")
    public void theEndpointAccountsIsAvailable(String endpoint) {
        this.createEndpoint(endpoint + "accounts");
    }

    @When("A Login Request is sent with values {string}, {string}")
    public void aLoginRequestIsSentWithValues(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        Account account = this.testRestTemplate.getForObject(this.endpointPath + "/search/username/{username}/password/{password}", Account.class, params);
        this.responseEntity = new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Then("A Login status with code {int} is sent")
    public void aLoginStatusWithCodeIsSent(int expectedStatusCode) {
        this.validateStatusCode(expectedStatusCode);
    }
}

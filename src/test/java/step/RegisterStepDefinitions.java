package step;

import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.Customer;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterStepDefinitions {

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

    @When("A User register request is sent with values {string}, {string}, {string}")
    public void aUserRegisterRequestIsSentWithValues(String username, String password, String userId) {
        Account account = new Account(0L, username, password, new Customer());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> params = new HashMap<>();
        params.put("userid", userId);

        HttpEntity<Account> request = new HttpEntity<>(account, headers);
        responseEntity = testRestTemplate.postForEntity(this.endpointPath + "/{userid}", request, String.class, params);
    }

    @And("username with value {string} doesn't exist")
    public void usernameWithValueDoesnTExist(String username) {

    }

    @Then("A Account with status {int} is received")
    public void aAccountWithStatusIsReceived(int expectedStatus) {
        int actualStatusCode = responseEntity.getStatusCodeValue();

        assertThat(expectedStatus).isEqualTo(actualStatusCode);
    }
}

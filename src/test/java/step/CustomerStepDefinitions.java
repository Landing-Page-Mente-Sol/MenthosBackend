package step;

import com.api.mentosbackend.entities.Customer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerStepDefinitions {

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

    @When("A Customer Request is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {int}, {int}")
    public void aCustomerRequestIsSentWithValues(String firstname, String lastname, String userType, String email, String upcCode, String career, int cycle, int points) {
        Customer customer = new Customer(0L, firstname, lastname, userType, email, upcCode, career, cycle, (long) points);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> request = new HttpEntity<>(customer, headers);
        responseEntity = testRestTemplate.postForEntity(this.endpointPath, request, String.class);
    }

    @Then("A Customer with status {int} is received")
    public void aCustomerWithStatusIsReceived(int expectedStatus) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        System.out.println(actualStatusCode);
        assertThat(expectedStatus).isEqualTo(actualStatusCode);
    }

    @When("A Customer Delete is sent with id value {string}")
    public void aCustomerDeleteIsSentWithIdValue(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
        testRestTemplate.delete(endpointPath + "/{id}", params);
        this.responseEntity = new ResponseEntity<>(HttpStatus.OK);
    }

    @When("A Customer Selected is sent with value {string}")
    public void aCustomerSelectedIsSentWithValue(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
        Customer customer = testRestTemplate.getForObject(endpointPath + "/{id}", Customer.class,params);
        this.responseEntity = new ResponseEntity<>(customer.toString(), HttpStatus.OK);
    }

    @When("All Customers who are registered en DB")
    public void allCustomersWhoAreRegisteredEnDB() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        responseEntity = testRestTemplate.exchange(endpointPath, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity);
    }

    @Then("List of Customers with status {int} is received")
    public void listOfCustomersWithStatusIsReceived(int expectedStatus) {
        int actualStatusCode = responseEntity.getStatusCodeValue();

        assertThat(expectedStatus).isEqualTo(actualStatusCode);
    }

    @When("A Customer Updated is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {string}, {int}, {int}")
    public void aCustomerUpdatedIsSentWithValues(String userId, String firstname, String lastname, String userType, String email, String upcCode, String career, int cycle, int points) {
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
        Customer customerUpdated = new Customer(0L, firstname, lastname, userType, email, upcCode, career, cycle, (long) points);

        testRestTemplate.put(this.endpointPath + "/{id}", customerUpdated, params);

        responseEntity = new ResponseEntity<>(customerUpdated.toString(), HttpStatus.OK);
    }
}

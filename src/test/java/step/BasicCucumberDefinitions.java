package step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;


import static org.assertj.core.api.Assertions.assertThat;

public class BasicCucumberDefinitions<Type> {
    @Autowired
    protected TestRestTemplate testRestTemplate;

    @LocalServerPort
    protected int randomServerPort;
    protected String endpointPath;
    protected ResponseEntity<Type> responseEntity;

    protected void createEndpoint(String endpoint) {
        this.endpointPath = String.format(endpoint, randomServerPort);
    }

    protected void validateStatusCode(int expectedStatus) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        System.out.println(actualStatusCode);
        assertThat(expectedStatus).isEqualTo(actualStatusCode);
    }

    protected void postForEntity(Type entity, String endpointPath, Class<Type> responseType) {
        this.postForEntity(this.testRestTemplate,entity, endpointPath, responseType);
    }
    protected void postForEntity(TestRestTemplate testRestTemplate, Type entity, String endpointPath, Class<Type> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Type> request = new HttpEntity<>(entity, headers);
        Class<Type> type;
        this.responseEntity = testRestTemplate.postForEntity(endpointPath, request, responseType);
    }

}

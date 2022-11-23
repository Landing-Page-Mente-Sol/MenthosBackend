package step;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.util.AnswerGenerator;
import com.api.mentosbackend.util.UserGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpEntity;

import java.util.Date;

public class AnswerStepDefinitions extends BasicCucumberDefinitions<Answer> {
    @Given("The Endpoint {string}answers is available")
    public void theEndpointAnswersIsAvailable(String endpoint) {
        this.createEndpoint(endpoint + "answers");
    }

    @When("A Answer request is sent with values {string}, {string}, {int}, {int}")
    public void aAnswerRequestIsSentWithValues(String description, String date, int questionId, int userId) {
        Date madeAt = new Date(date);
        Answer answer = AnswerGenerator.answer();
        answer.setMadeAt(madeAt);

        this.postForEntity(answer, this.endpointPath + "/" + questionId + "/" + userId, Answer.class);
    }

    @Then("A Answer with status {int} is received")
    public void aAnswerWithStatusIsReceived(int expectedStatus) {
        this.validateStatusCode(expectedStatus);
    }
}

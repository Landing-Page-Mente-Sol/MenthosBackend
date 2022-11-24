package step;

import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.util.CourseGenerator;
import com.api.mentosbackend.util.UserGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.util.Date;


public class QuestionsStepsDefinitions extends BasicCucumberDefinitions<Question> {


    @Given("The Endpoint {string}questions is available")
    public void theEndpointQuestionsIsAvailable(String endpoint) {
        this.createEndpoint(endpoint + "questions");
    }

    @When("A Question request is sent with values {string}, {string}, {string}, {int}, {int}")
    public void aQuestionRequestIsSentWithValues(String title, String description, String date, long courseId, long userId) {
        Date madeAt = new Date(date);
        Question question = new Question(0L, description, title, madeAt, UserGenerator.user(userId), CourseGenerator.course(courseId));

        this.postForEntity(question, this.endpointPath + "/" + courseId + "/" + userId, Question.class);
    }

    @Then("A Question with status {int} is received")
    public void aQuestionWithStatusIsReceived(int expectedStatus) {
        this.validateStatusCode(expectedStatus);
    }
}

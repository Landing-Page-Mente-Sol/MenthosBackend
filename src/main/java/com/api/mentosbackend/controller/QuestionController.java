package com.api.mentosbackend.controller;

import com.api.mentosbackend.controller.common.CrudController;
import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.service.ICourseService;
import com.api.mentosbackend.service.IQuestionService;
import com.api.mentosbackend.service.ICustomerService;
import com.api.mentosbackend.util.SetId;
import com.api.mentosbackend.util.TextDocumentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/v1/questions")
@Api(tags = "Questions", value = "Web Service RESTful of questions")
public class QuestionController extends CrudController<Question, Long> {
    private final IQuestionService questionService;
    private final ICourseService courseService;
    private final ICustomerService customerService;
    private final SetId<Question> setQuestionId;

    public QuestionController(IQuestionService questionService, ICourseService courseService, ICustomerService customerService) {
        super(questionService);
        this.questionService = questionService;
        this.courseService = courseService;
        this.customerService = customerService;
        this.setQuestionId = new SetId<>();
    }

    @GetMapping(value = {"", "/search/all"},produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Questions List.", notes = "Method for list all questions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Questions" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 204, message = "Questions" + TextDocumentation.HAVE_NOT_CONTENT)
    })
    public ResponseEntity<List<Question>> findAllQuestions(){ return this.getAll(); }

    @GetMapping(value = {"/{id}", "/search/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Question.", notes = "Method for search a question by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Question" + TextDocumentation.FOUND),
            @ApiResponse(code = 404, message = "Question" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Question> findQuestionById(@PathVariable("id")Long id){ return this.getById(id); }

    @PostMapping(value = "/{courseId}/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert Question.", notes = "Method for insert a question.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Answer" + TextDocumentation.CREATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 424, message = TextDocumentation.FAILED_DEPENDENCY)
    })
    public ResponseEntity<Question> insertQuestion(@PathVariable("courseId")Long courseId, @PathVariable("customerId")Long customerId, @Valid @RequestBody Question question){
        try {
            Optional<Course> course = this.courseService.getById(courseId);
            Optional<Customer> customer = this.customerService.getById(customerId);

            if(course.isPresent() && customer.isPresent()){
                question.setCourse(course.get());
                question.setCustomer(customer.get());
                this.questionService.save(question);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Question.", notes = "Method for update a question by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Question" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Question" + TextDocumentation.UPDATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Question> updateQuestion(@PathVariable("id")Long id, @Valid @RequestBody Question question){ return this.update(id, question, this.setQuestionId); }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Question.", notes = "Method for delete a question by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Question" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Question" + TextDocumentation.DELETED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Question> deleteQuestion(@PathVariable("id") Long id){ return this.delete(id); }

    @GetMapping(value = {"/search/course/{id}", "/search/course/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Questions.", notes = "Method for search questions by course (id).")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Questions" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = "Course" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 204, message = "Questions" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Question>> findQuestionsByCourse(@PathVariable("id")Long courseId) {
        try {
            Optional<Course> course = this.courseService.getById(courseId);
            if(course.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<Question> questions = this.questionService.findQuestionsByCourse(course.get());
            if (questions.size() > 0)
                return new ResponseEntity<>(questions, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/title/contains/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Questions.", notes = "Method for search questions by title keyword")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Questions" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Questions" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Question>> findQuestionsByTitleContaining(@PathVariable("keyword") String keyword){
        try {
            List<Question> questions = this.questionService.findQuestionsByTitleContaining(keyword);
            if(questions.size() > 0)
                return new ResponseEntity<>(questions, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/search/customer/{customerId}", "/search/customer/id/{customerId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Questions.", notes = "Method for search questions by customer (id)")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Customer" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Questions" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Questions" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Question>> findQuestionsByCustomer(@PathVariable("customerId")Long customerId){
        try {
            Optional<Customer> customer = this.customerService.getById(customerId);
            if(customer.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<Question> questions = this.questionService.findQuestionsByCustomer(customer.get());
            if(questions.size() > 0)
                return new ResponseEntity<>(questions, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/course/{courseId}/title/contains/{keyword}")
    @ApiOperation(value = "Search Questions", notes = "Method for search questions by course (id) and title keyword.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Questions" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Questions" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 404, message = "Course" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Question>> findQuestionsByCourseAndTitleContaining(@PathVariable("courseId")Long courseId, @PathVariable("keyword")String keyword){
        try {
            Optional<Course> course = this.courseService.getById(courseId);
            if(course.isPresent()){
                List<Question> questions = this.questionService.findQuestionsByCourseAndTitleContaining(course.get(), keyword);
                if(questions.size() > 0)
                    return new ResponseEntity<>(questions, HttpStatus.OK);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

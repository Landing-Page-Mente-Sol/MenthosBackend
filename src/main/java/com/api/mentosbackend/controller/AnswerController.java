package com.api.mentosbackend.controller;

import com.api.mentosbackend.controller.common.CrudController;
import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.service.IAnswerService;
import com.api.mentosbackend.service.IQuestionService;
import com.api.mentosbackend.service.IUserService;
import com.api.mentosbackend.util.TextDocumentation;
import com.api.mentosbackend.util.SetId;
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
@RequestMapping("/api/v1/answers")
@Api(tags = "Answers", value = "Web Service RESTful of answers")
public class AnswerController extends CrudController<Answer, Long> {
    private final IAnswerService answerService;
    private final IUserService userService;
    private final IQuestionService questionService;

    private final SetId<Answer> setAnswerId;

    public AnswerController(IAnswerService answerService, IQuestionService questionService, IUserService userService) {
        super(answerService);
        this.answerService = answerService;
        this.userService = userService;
        this.questionService = questionService;
        this.setAnswerId = new SetId<>();
    }

    @GetMapping(value = {"", "/search/all"},produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Answers List.", notes = "Method for list all answers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Answers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 204, message = "Answers" + TextDocumentation.HAVE_NOT_CONTENT)
    })
    public ResponseEntity<List<Answer>> findAllAnswers(){ return this.getAll(); }

    @GetMapping(value = {"/{id}", "/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Answer.", notes = "Method for search a answer by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Answer" + TextDocumentation.FOUND),
            @ApiResponse(code = 404, message = "Answer" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Answer> findAnswerById(@PathVariable("id") Long id){ return this.getById(id); }

    @PostMapping(value = "/{questionId}/{userId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert Answer.", notes = "Method for insert a answer")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Answer" + TextDocumentation.CREATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 424, message = TextDocumentation.FAILED_DEPENDENCY)
    })
    public ResponseEntity<Answer> insertAnswer(@PathVariable("questionId") Long questionId, @PathVariable("userId") Long userId, @Valid @RequestBody Answer answer) {
        try {
            Optional<User> user = this.userService.getById(userId);
            Optional<Question> question = this.questionService.getById(questionId);
            if(user.isPresent() && question.isPresent()){
                answer.setQuestion(question.get());
                answer.setUser(user.get());
                Answer newAnswer = this.answerService.save(answer);
                return ResponseEntity.status(HttpStatus.CREATED).body(newAnswer);
            }
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Answer.", notes = "Method for update a answer by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Answer" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Answer" + TextDocumentation.UPDATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Answer> updateAnswer(@PathVariable("id") Long idAnswer, @Valid @RequestBody Answer answer){ return this.update(idAnswer, answer, this.setAnswerId); }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Answer.", notes = "Method for delete a answer by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Answer" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Answer" + TextDocumentation.DELETED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Answer> deleteAnswer(@PathVariable("id") Long id) { return this.delete(id); }

    @GetMapping(value = {"/search/user/{id}", "/search/user/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Answers.", notes = "Method for search answers by user (id).")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Answers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = "User" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 204, message = "Answers" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Answer>> findAnswersByUser(@PathVariable("id") Long userId) {
        try {
            Optional<User> user = this.userService.getById(userId);
            if(!user.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<Answer> answers = this.answerService.findAnswersByUser(user.get());
            if(answers.size() > 0)
                return new ResponseEntity<>(answers, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/search/question/{id}", "/search/question/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Answers.", notes = "Method for search answers by question (id)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Answers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = "Question" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 204, message = "Answers" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Answer>> findAnswersByQuestion(@PathVariable("id")Long questionId){
        try {
            Optional<Question> question = this.questionService.getById(questionId);
            if (!question.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<Answer> answers = this.answerService.findAnswersByQuestion(question.get());
            if(answers.size() > 0)
                return new ResponseEntity<>(answers, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



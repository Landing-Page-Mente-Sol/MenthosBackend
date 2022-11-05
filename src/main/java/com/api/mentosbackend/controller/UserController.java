package com.api.mentosbackend.controller;

import com.api.mentosbackend.controller.common.CrudController;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.service.IUserService;
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
@RequestMapping("/api/v1/users")
@Api(tags = "Users", value = "Web Service RESTful of users")
public class UserController extends CrudController<User, Long> {
    private final IUserService userService;
    private final SetId<User> setUserId;

    public UserController(IUserService userService) {
        super(userService);
        this.userService = userService;
        this.setUserId = new SetId<>();
    }

    @GetMapping(value = {"", "/search/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Users List.", notes = "Method for list all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 204, message = "Users" + TextDocumentation.HAVE_NOT_CONTENT)
    })
    public ResponseEntity<List<User>> findAllUsers() { return this.getAll(); }

    @GetMapping(value = {"/{id}", "/search/user/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search User.", notes = "Method for search a user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User" + TextDocumentation.FOUND),
            @ApiResponse(code = 404, message = "User" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<User> findUserById(@PathVariable("id")Long id) { return this.getById(id); }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert User.", notes = "Method for insert a user.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User" + TextDocumentation.CREATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<User> insertUser(@Valid @RequestBody User user){ return this.insert(user); }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update User.", notes = "Method for update a user by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "User" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "User" + TextDocumentation.UPDATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<User> updateUser(@PathVariable("id")Long id,@Valid @RequestBody User user) { return this.update(id, user, this.setUserId); }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete User.", notes = "Method for delete a user by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "User" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "User" + TextDocumentation.DELETED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<User> deleteUser(@PathVariable("id")Long id){ return this.delete(id); }

    @GetMapping(value = "/search/users/cycle/{cycle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Users.", notes = "Method for search users by cycle.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Users" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<User>> findUsersByCycle(@PathVariable("cycle")int cycle) {
        try {
            List<User> users = this.userService.findUsersByCycle(cycle);
            if(users.size() > 0)
                return new ResponseEntity<>(users, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/user/points/gte/{points}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Users.", notes = "Method for search users by points greater than equal to a quantity.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Users" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<User>> findUsersByPointsGreaterThanEqual(@PathVariable("points")Long points){
        try {
            List<User> users = this.userService.findUsersByPointsGreaterThanEqual(points);
            if(users.size() > 0)
                return new ResponseEntity<>(users, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/user/points/top/100/desc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List Users.", notes = "List top 100 users according to their points.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Users" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<User>> findTop100ByOrderByPointsDesc(){
        try {
            List<User> users = this.userService.findTop100ByOrderByPointsDesc();
            if(users.size() > 0)
                return new ResponseEntity<>(users, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/user/type/{userType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Users.", notes = "Method for search users by user type.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Users" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<User>> findUsersByUserType(@PathVariable("userType")String userType){
        try{
            List<User> users = this.userService.findUsersByUserType(userType);
            if(users.size() > 0)
                return new ResponseEntity<>(users, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/user/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Users.", notes = "Method for search users by email.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "User" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<User> findUserByEmail(@PathVariable("email") String email){
        try {
            Optional<User> user = this.userService.findUserByEmail(email);
            if(user.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

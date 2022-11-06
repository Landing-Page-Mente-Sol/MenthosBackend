package com.api.mentosbackend.controller;

import com.api.mentosbackend.controller.common.RelatedCrudController;
import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.service.IAccountService;
import com.api.mentosbackend.service.IUserService;
import com.api.mentosbackend.util.AssignAccount;
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
@RequestMapping("/api/v1/accounts")
@Api(tags = "Accounts", value = "Web Service RESTful of accounts")
public class AccountController extends RelatedCrudController<Account, Long, User, Long> {
    private final IAccountService accountService;
    private final SetId<Account> setAccountId;
    private final AssignAccount assignAccount;

    public AccountController(IAccountService accountService, IUserService userService) {
        super(accountService, userService);
        this.accountService = accountService;
        this.assignAccount = new AssignAccount();
        this.setAccountId = new SetId<>();
    }

    @GetMapping(value = {"", "/search/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Account List.", notes = "Method for list all accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Accounts" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 204, message = "Accounts" + TextDocumentation.HAVE_NOT_CONTENT)
    })
    public ResponseEntity<List<Account>> findAllAccounts() { return this.getAll(); }

    @GetMapping(value = {"/{id}", "/search/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Account.", notes = "Method for search a account by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account" + TextDocumentation.FOUND),
            @ApiResponse(code = 404, message = "Account" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Account> findAccountById(@PathVariable("id")Long id) { return this.getById(id); }

    @PostMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert Account.", notes = "Method for insert a account")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Account" + TextDocumentation.CREATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 424, message = TextDocumentation.FAILED_DEPENDENCY)
    })
    public ResponseEntity<Account> insertAccount(@PathVariable("userId")Long userId,@Valid @RequestBody Account account){ return this.insert(userId, account, this.assignAccount); }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Account.", notes = "Method for update a account by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Account" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Account" + TextDocumentation.UPDATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Account> updateAccount(@PathVariable("id")Long id, @Valid @RequestBody Account account) { return this.update(id, account, this.setAccountId); }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Account.", notes = "Method for delete a account by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Account" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Account" + TextDocumentation.DELETED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Account> deleteAccount(@PathVariable("id")Long id) { return this.delete(id); }

    @GetMapping(value = "/search/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search account by username", notes = "Method for search a account by username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account" + TextDocumentation.FOUND),
            @ApiResponse(code = 204, message = "Account" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Account> findAccountByUsername(@PathVariable("username")String username){
        try {
            Account account = this.accountService.findAccountByUsername(username);
            if(account != null)
                return new ResponseEntity<>(account, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/username/{username}/password/{password}")
    @ApiOperation(value = "Search a account by username and password", notes = "Method for search a account by username and password.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account" + TextDocumentation.FOUND),
            @ApiResponse(code = 204, message = "Account" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Account> findAccountByUsernameAndPassword(@PathVariable("username")String username, @PathVariable("password")String password){
        try {
            Account account = this.accountService.findAccountByUsernameAndPassword(username, password);
            if(account != null)
                return new ResponseEntity<>(account, HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/search/user/{userId}", "/search/user/id/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search a account by user id", notes = "Method for search a account by user.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User or Account" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 200, message = "Account" + TextDocumentation.FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Account> findAccountByUser(@PathVariable("userId")Long userId){
        try {
            Optional<User> user = this.relatedCrudService.getById(userId);
            if(user.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Account account = this.accountService.findAccountByUser(user.get());
            if(account != null)
                return new ResponseEntity<>(account, HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

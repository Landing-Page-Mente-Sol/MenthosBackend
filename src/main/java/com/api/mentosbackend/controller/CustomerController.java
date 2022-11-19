package com.api.mentosbackend.controller;

import com.api.mentosbackend.controller.common.CrudController;
import com.api.mentosbackend.entities.Customer;
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
@RequestMapping("/api/v1/customers")
@Api(tags = "Customers", value = "Web Service RESTful of customers")
public class CustomerController extends CrudController<Customer, Long> {
    private final ICustomerService customerService;
    private final SetId<Customer> setCustomerId;

    public CustomerController(ICustomerService customerService) {
        super(customerService);
        this.customerService = customerService;
        this.setCustomerId = new SetId<>();
    }

    @GetMapping(value = {"", "/search/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Customers List.", notes = "Method for list all customers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 204, message = "Customers" + TextDocumentation.HAVE_NOT_CONTENT)
    })
    public ResponseEntity<List<Customer>> findAllCustomers() { return this.getAll(); }

    @GetMapping(value = {"/{id}", "/search/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Customer.", notes = "Method for search a customer by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customer" + TextDocumentation.FOUND),
            @ApiResponse(code = 404, message = "Customer" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Customer> findCustomerById(@PathVariable("id")Long id) { return this.getById(id); }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert Customer.", notes = "Method for insert a customer.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Customer" + TextDocumentation.CREATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Customer> insertCustomer(@Valid @RequestBody Customer customer){ return this.insert(customer); }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Customer.", notes = "Method for update a customer by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Customer" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Customer" + TextDocumentation.UPDATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id")Long id, @Valid @RequestBody Customer customer) { return this.update(id, customer, this.setCustomerId); }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Customer.", notes = "Method for delete a customer by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Customer" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Customer" + TextDocumentation.DELETED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id")Long id){ return this.delete(id); }

    @GetMapping(value = "/search/cycle/{cycle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Customers.", notes = "Method for search customers by cycle.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Customers" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Customer>> findCustomersByCycle(@PathVariable("cycle")int cycle) {
        try {
            List<Customer> customers = this.customerService.findCustomersByCycle(cycle);
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/points/gte/{points}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Customers.", notes = "Method for search customers by points greater than equal to a quantity.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Customers" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Customer>> findCustomersByPointsGreaterThanEqual(@PathVariable("points")Long points){
        try {
            List<Customer> customers = this.customerService.findCustomersByPointsGreaterThanEqual(points);
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/points/top/100/desc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List Customers.", notes = "List top 100 customers according to their points.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Customers" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Customer>> findTop100ByOrderByPointsDesc(){
        try {
            List<Customer> customers = this.customerService.findTop100ByOrderByPointsDesc();
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/type/{customerType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Customers.", notes = "Method for search customers by customer type.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Customers" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Customer>> findCustomersByCustomerType(@PathVariable("customerType")String customerType){
        try{
            List<Customer> customers = this.customerService.findCustomersByCustomerType(customerType);
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Customers.", notes = "Method for search customers by email.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customers" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 204, message = "Customers" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Customer> findCustomerByEmail(@PathVariable("email") String email){
        try {
            Optional<Customer> customer = this.customerService.findCustomerByEmail(email);
            if(customer.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

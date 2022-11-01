package com.api.mentosbackend.controller;

import com.api.mentosbackend.controller.common.CrudController;
import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.service.ICourseService;
import com.api.mentosbackend.util.SetId;
import com.api.mentosbackend.util.TextDocumentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@Api(tags = "Courses", value = "Web Service RESTful of courses")
public class CourseController extends CrudController<Course, Long> {

    private final SetId<Course> setCourseId;

    public CourseController(ICourseService courseService) { super(courseService); this.setCourseId = new SetId<>(); }

    @GetMapping(value = {"", "/search/all"},produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Course List.", notes = "Method for list all courses.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Course" + TextDocumentation.FOUNDS),
            @ApiResponse(code = 404, message = TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 204, message = "Course" + TextDocumentation.HAVE_NOT_CONTENT),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<List<Course>> findAllCourses(){ return this.getAll(); }

    @GetMapping(value = {"/{id}", "/search/id/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search Course.", notes = "Method for search a course by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Course" + TextDocumentation.FOUND),
            @ApiResponse(code = 404, message = "Course" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Course> findCourseById(@PathVariable("id")Long id) { return this.getById(id); }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert Course.", notes = "Method for insert a course.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Course" + TextDocumentation.CREATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Course> insertCourse(@Valid @RequestBody Course course) { return this.insert(course); }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Course.", notes = "Method for update a course by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Course" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Course" + TextDocumentation.UPDATED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Course> updateCourse(@PathVariable("id")Long id, @Valid @RequestBody Course course){ return this.update(id, course, this.setCourseId); }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Course.", notes = "Method for delete a course by id.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Course" + TextDocumentation.NOT_FOUND),
            @ApiResponse(code = 200, message = "Course" + TextDocumentation.DELETED),
            @ApiResponse(code = 501, message = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Course> deleteCourse(@PathVariable("id")Long id){ return this.delete(id); }
}

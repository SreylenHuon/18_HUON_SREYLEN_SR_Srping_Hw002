package com.sreylen.springboot_hw002.controller;

import com.sreylen.springboot_hw002.model.Course;
import com.sreylen.springboot_hw002.model.request.CourseCreateRequest;
import com.sreylen.springboot_hw002.model.request.CourseUpdateRequest;
import com.sreylen.springboot_hw002.model.response.ApiResponse;
import com.sreylen.springboot_hw002.model.response.ErrorResponse;
import com.sreylen.springboot_hw002.service.servicelmpl.CourseService;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    // findAllCourses
    @GetMapping
    public ResponseEntity<ApiResponse<AssertFalse.List<Course>>> findAllCourses(@RequestParam(defaultValue = "1") Integer page,
                                                                                @RequestParam(defaultValue = "10") Integer size){

        ApiResponse<AssertFalse.List<Course>> response = ApiResponse.<List<Course>>builder()
                .message("Find all courses is successfully")
                .payload(courseService.findAllCourses(page, size))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    // findCourseById
    @GetMapping("/{id}")
    public ResponseEntity<?> findCourseById(@PathVariable Integer id){
        Course course = courseService.findCourseById(id);

        if(course != null){
            ApiResponse<Course> response = ApiResponse.<Course>builder()
                    .message("Find course by id is successfully")
                    .payload(course)
                    .status(HttpStatus.OK)
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        // custom error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                "about:blank",
                "Not Found",
                HttpStatus.NOT_FOUND.value(),
                "Courses with ID " + id + " not found.",
                "/api/v1/courses/" + id,
                LocalDateTime.now()
        ));
    }
    //    createCourse
    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody CourseCreateRequest courseCreateRequest){
        Course newCourse = courseService.createCourse(courseCreateRequest);

        ApiResponse<Course> response = ApiResponse.<Course>builder()
                .message("Create new course is successfully")
                .payload(newCourse)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // deleteCourseById
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Integer id){
        Course deletedCourse = courseService.findCourseById(id);
        if (deletedCourse == null){
            // custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                    "about:blank",
                    "Not Found",
                    HttpStatus.NOT_FOUND.value(),
                    "Courses with ID " + id + " not found.",
                    "/api/v1/courses/" + id,
                    LocalDateTime.now()
            ));
        }
        courseService.deleteCourseById(id);

        return ResponseEntity.ok(ApiResponse.<Course>builder()
                .message("Delete course by id is successfully")
                .status(HttpStatus.NO_CONTENT)
                .timestamp(LocalDateTime.now())
                .build());
    }
    // updateCourseById
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourseById(@PathVariable Integer id, @RequestBody CourseUpdateRequest courseUpdateRequest){
        Course exitingCourse = courseService.findCourseById(id);

        if (exitingCourse == null){
            // custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                    "about:blank",
                    "Not Found",
                    HttpStatus.NOT_FOUND.value(),
                    "Courses with ID " + id + " not found.",
                    "/api/v1/courses/" + id,
                    LocalDateTime.now()
            ));
        }
        Course updatedCourse = courseService.updateCourseById(id, courseUpdateRequest);
        ApiResponse<Course> response = ApiResponse.<Course>builder()
                .message("Course updated successfully")
                .payload(updatedCourse)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

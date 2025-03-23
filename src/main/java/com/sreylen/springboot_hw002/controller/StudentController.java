package com.sreylen.springboot_hw002.controller;

import com.sreylen.springboot_hw002.model.Student;
import com.sreylen.springboot_hw002.model.request.StudentCreateRequest;
import com.sreylen.springboot_hw002.model.request.StudentUpdateRequest;
import com.sreylen.springboot_hw002.model.response.ApiResponse;
import com.sreylen.springboot_hw002.model.response.ErrorResponse;
import com.sreylen.springboot_hw002.service.servicelmpl.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    // Find all students with pagination
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> findAllStudents(@RequestParam(defaultValue = "1") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size){
        ApiResponse<List<Student>> response = ApiResponse.<List<Student>>builder()
                .message("Find all students is successfully")
                .payload(studentService.findAllStudents(page, size))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // findStudentById
    @GetMapping("/{id}")
    public ResponseEntity<?> findStudentById(@PathVariable Integer id){
        Student student = studentService.findStudentById(id);

        if(student != null){
            ApiResponse<Student> response = ApiResponse.<Student>builder()
                    .message("Find student by id is successfully")
                    .payload(student)
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
                "Students with ID " + id + " not found.",
                "/api/v1/students/" + id,
                LocalDateTime.now()
        ));
    }
    // createCourse
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody StudentCreateRequest studentCreateRequest){
        Student newStudent = studentService.createStudent(studentCreateRequest);
        System.out.println(newStudent);
        ApiResponse<Student> response = ApiResponse.<Student>builder()
                .message("Student is successfully created")
                .payload(newStudent)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // deleteStudentById
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Integer id){
        Student existingStudent = studentService.findStudentById(id);

        if(existingStudent == null){
            // custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                    "about:blank",
                    "Not Found",
                    HttpStatus.NOT_FOUND.value(),
                    "Students with ID " + id + " not found.",
                    "/api/v1/students/" + id,
                    LocalDateTime.now()
            ));
        }
        studentService.deleteStudentById(id);


        return ResponseEntity.ok(ApiResponse.<Student>builder()
                .message("Delete student is successfully deleted")
                .payload(existingStudent)
                .status(HttpStatus.NO_CONTENT)
                .timestamp(LocalDateTime.now())
                .build());
    }
    // updateStudentById
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable Integer id,@RequestBody StudentUpdateRequest studentUpdateRequest){
        Student exitingStudent = studentService.findStudentById(id);
        if (exitingStudent == null){
            // custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                    "about:blank",
                    "Not Found",
                    HttpStatus.NOT_FOUND.value(),
                    "Students with ID " + id + " not found.",
                    "/api/v1/students/" + id,
                    LocalDateTime.now()
            ));
        }
        Student updatedStudent = studentService.updateStudentById(id, studentUpdateRequest);

        return ResponseEntity.ok(ApiResponse.<Student>builder()
                .message("Update student is successfully")
                .payload(updatedStudent)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build());
    }
}

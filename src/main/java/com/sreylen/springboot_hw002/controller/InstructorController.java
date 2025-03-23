package com.sreylen.springboot_hw002.controller;

import com.sreylen.springboot_hw002.model.Instructor;
import com.sreylen.springboot_hw002.model.request.InstructorCreateRequest;
import com.sreylen.springboot_hw002.model.request.InstructorUpdateRequest;
import com.sreylen.springboot_hw002.model.response.ApiResponse;
import com.sreylen.springboot_hw002.model.response.ErrorResponse;
import com.sreylen.springboot_hw002.service.servicelmpl.InstructorService;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // findAllInstructors
    @GetMapping
    public ResponseEntity<ApiResponse<AssertFalse.List<Instructor>>> findAllInstructors(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        ApiResponse<List<Instructor>> response = ApiResponse.<List<Instructor>>builder()
                .message("Find all instructions is successfully")
                .payload(instructorService.findAllInstructors(page, size))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createInstructor(@RequestBody InstructorCreateRequest instructorCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                .message("Create instructor is successfully")
                .payload(instructorService.createInstructor(instructorCreateRequest))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build());
    }
    // findInstructorById
    @GetMapping("/{id}")
    public ResponseEntity<?> findInstructorById(@PathVariable Integer id) {
        Instructor instructor = instructorService.findInstructorById(id);

        if (instructor != null) {
            return ResponseEntity.ok(ApiResponse.<Instructor>builder()
                    .message("Find instructor by id is successful")
                    .payload(instructor)
                    .status(HttpStatus.OK)
                    .timestamp(LocalDateTime.now())
                    .build());
        }

        // custom error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                "about:blank",
                "Not Found",
                HttpStatus.NOT_FOUND.value(),
                "Instructor with ID " + id + " not found.",
                "/api/v1/instructors/" + id,
                LocalDateTime.now()
        ));
    }
    // deleteInstructorById
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstructorById(@PathVariable Integer id){
        Instructor instructor = instructorService.findInstructorById(id);

        if (instructor == null){
            // custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                    "about:blank",
                    "Not Found",
                    HttpStatus.NOT_FOUND.value(),
                    "Instructor with ID " + id + " not found.",
                    "/api/v1/instructors/" + id,
                    LocalDateTime.now()
            ));
        }

        instructorService.deleteInstructorById(id);

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Delete instructor by id is successfully")
                .status(HttpStatus.NO_CONTENT)
                .timestamp(LocalDateTime.now())
                .build());
    }
    // updateInstructorById
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInstructorById(@PathVariable Integer id, @RequestBody InstructorUpdateRequest instructorUpdateRequest) {

        Instructor existingInstructor = instructorService.findInstructorById(id);

        if (existingInstructor == null) {
            // custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                    "about:blank",
                    "Not Found",
                    HttpStatus.NOT_FOUND.value(),
                    "Instructor with ID " + id + " not found.",
                    "/api/v1/instructors/" + id,
                    LocalDateTime.now()
            ));
        }

        Instructor updatedInstructor = instructorService.updateInstructorById(id, instructorUpdateRequest);

        ApiResponse<Instructor> response = ApiResponse.<Instructor>builder()
                .message("The instructor has been successfully updated.")
                .payload(updatedInstructor)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

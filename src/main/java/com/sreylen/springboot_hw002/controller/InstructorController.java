package com.sreylen.springboot_hw002.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/instructors")
public class InstructorController {

    @GetMapping
    public String getAllInstructors() {
        return "All Instructors";
    }
}

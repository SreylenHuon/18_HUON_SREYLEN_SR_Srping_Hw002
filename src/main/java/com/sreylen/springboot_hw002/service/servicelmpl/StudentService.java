package com.sreylen.springboot_hw002.service.servicelmpl;


import com.sreylen.springboot_hw002.model.Student;
import com.sreylen.springboot_hw002.model.request.StudentCreateRequest;
import com.sreylen.springboot_hw002.model.request.StudentUpdateRequest;

import java.util.List;

public interface StudentService {
    // Find all students with pagination
    List<Student> findAllStudents(Integer page, Integer size);

    // findStudentById
    Student findStudentById(Integer id);

    Student createStudent(StudentCreateRequest studentCreateRequest);

    // deleteStudentById
    void deleteStudentById(Integer id);

    // updateStudentById
    Student updateStudentById(Integer id, StudentUpdateRequest studentUpdateRequest);
}


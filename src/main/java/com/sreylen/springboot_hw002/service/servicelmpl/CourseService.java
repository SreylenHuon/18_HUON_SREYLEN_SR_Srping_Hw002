package com.sreylen.springboot_hw002.service.servicelmpl;


import com.sreylen.springboot_hw002.model.Course;
import com.sreylen.springboot_hw002.model.request.CourseCreateRequest;
import com.sreylen.springboot_hw002.model.request.CourseUpdateRequest;

import java.util.List;

public interface CourseService {
    // findAllCourses
    List<Course> findAllCourses(Integer page, Integer size);
    // findCourseById
    Course findCourseById(Integer id);
    Course createCourse(CourseCreateRequest courseCreateRequest);
    void deleteCourseById(Integer id);
    Course updateCourseById(Integer id, CourseUpdateRequest courseUpdateRequest);
}


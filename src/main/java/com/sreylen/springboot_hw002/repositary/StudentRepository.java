package com.sreylen.springboot_hw002.repositary;

import com.sreylen.springboot_hw002.model.Course;
import com.sreylen.springboot_hw002.model.Student;
import com.sreylen.springboot_hw002.model.request.StudentCreateRequest;
import com.sreylen.springboot_hw002.model.request.StudentUpdateRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentRepository {
    // Find all students with pagination
    @Select("""
        SELECT * FROM students 
        LIMIT #{size} OFFSET ((#{page} - 1) * #{size})
    """)
    @Results(id = "studentMapper", value = {
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "studentName", column = "student_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "courses", column = "student_id",
                    many = @Many(select = "org.ksga._07_mouk_makara_spring_homework002.repository.StudentRepository.findCoursesByStudentId")
            )
    })
    List<Student> findAllStudents(@Param("page") Integer page, @Param("size") Integer size);

    // Find courses by student ID
    @Select("""
        SELECT c.* FROM courses c
        INNER JOIN student_course sc ON c.course_id = sc.course_id
        WHERE sc.student_id = #{studentId}
    """)
    @Results(id = "courseMapper", value = {
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "instructor", column = "instructor_id",
                    one = @One(select = "org.ksga._07_mouk_makara_spring_homework002.repository.InstructorRepository.findInstructorById")
            )
    })
    List<Course> findCoursesByStudentId(@Param("studentId") Integer studentId);

    // find student by id
    @Select("""
        SELECT * FROM students
        WHERE student_id = #{studentId}
    """)
    @ResultMap("studentMapper")
    Student findStudentById(Integer studentId);

    // Create student
    @Select("""
        INSERT INTO students (student_name, email, phone_number)
        VALUES (#{student.studentName}, #{student.email}, #{student.phoneNumber})
        RETURNING student_id
    """)
    Integer createStudent(@Param("student") StudentCreateRequest studentCreateRequest);

    // create student in table student_course
    @Insert("""
    INSERT INTO student_course(student_id, course_id)
    VALUES (#{studentId}, #{courseId})
    """)
    void createStudentCourse(Integer studentId, Integer courseId);

    // delete student
    @Delete("DELETE FROM students WHERE student_id = #{studentId}")
    void deleteStudentById(Integer studentId);

    // update student by id
    @Update("""
    UPDATE students
    SET student_name = #{student.studentName}, email = #{student.email}, phone_number = #{student.phoneNumber}
    WHERE student_id = #{studentId} RETURNING student_id
    """)
    Integer updateStudentById(@Param("studentId") Integer id, @Param("student") StudentUpdateRequest studentUpdateRequest);

    // update student_course
    @Insert("""
    insert into student_course(student_id, course_id)
    VALUES (#{studentId}, #{courseId})
""")
    void updateStudentCourse(Integer studentId, Integer courseId);

    // delete student_course
    @Select("""
    DELETE FROM student_course WHERE student_id = #{studentId}
""")
    void deleteStudentCourse(Integer studentId);
}

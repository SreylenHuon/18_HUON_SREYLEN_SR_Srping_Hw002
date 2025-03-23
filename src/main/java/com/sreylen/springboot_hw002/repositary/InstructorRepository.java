package com.sreylen.springboot_hw002.repositary;


import com.sreylen.springboot_hw002.model.Instructor;
import com.sreylen.springboot_hw002.model.request.InstructorCreateRequest;
import com.sreylen.springboot_hw002.model.request.InstructorUpdateRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InstructorRepository {
    // find all instructors
//    @Select("SELECT * FROM instructors")
    @Select("""
        SELECT * FROM instructors
        offset #{size} * (#{page} - 1)
        limit #{size}
    """)
    @Results(id = "instructorMapper", value = {
            @Result(property = "instructorId", column = "instructor_id"),
            @Result(property = "instructorName", column = "instructor_name")
    })
    List<Instructor> findAllInstructors(Integer page, Integer size);

    // add new instructor
    @Select(("""
        INSERT INTO instructors(instructor_name, email) 
        VALUES (#{instructor.instructorName},#{instructor.email}) 
        RETURNING *;
"""))
    @ResultMap("instructorMapper")
    Instructor createInstructor(@Param("instructor") InstructorCreateRequest instructorCreateRequest);

    // find instructor by id
    @Select("SELECT * FROM instructors WHERE instructor_id = #{instructorId}")
    @ResultMap("instructorMapper")
    Instructor findInstructorById(Integer id);

    // delete instructor by id
    @Select("DELETE FROM instructors WHERE instructor_id = #{instructorId}")
    void deleteInstructorById(Integer id);

    // update instructor by id
    @Select("UPDATE instructors SET instructor_name = #{instructor.instructorName}, email = #{instructor.email} WHERE instructor_id = #{id} RETURNING *")
    @ResultMap("instructorMapper")
    Instructor updateInstructorById(@Param("id") Integer id,@Param("instructor") InstructorUpdateRequest instructorUpdateRequest);


}

package com.nye.university.nyeuni.repository;

import com.nye.university.nyeuni.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository for Course persistence operations. */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}

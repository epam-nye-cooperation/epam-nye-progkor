package com.nye.university.nyeuni.repository;

import com.nye.university.nyeuni.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository for Student persistence operations. */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}

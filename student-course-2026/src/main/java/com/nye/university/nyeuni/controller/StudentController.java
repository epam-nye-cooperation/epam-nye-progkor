package com.nye.university.nyeuni.controller;

import com.nye.university.nyeuni.dto.student.StudentRequestDto;
import com.nye.university.nyeuni.dto.student.StudentResponseDto;
import com.nye.university.nyeuni.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** REST endpoints for student CRUD operations. */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StudentResponseDto createStudent(@Valid @RequestBody StudentRequestDto requestDto) {
    return studentService.createStudent(requestDto);
  }

  @GetMapping
  public List<StudentResponseDto> getAllStudents() {
    return studentService.getAllStudents();
  }

  @GetMapping("/{id}")
  public StudentResponseDto getStudentById(@PathVariable Long id) {
    return studentService.getStudentById(id);
  }

  @PutMapping("/{id}")
  public StudentResponseDto updateStudent(@PathVariable Long id,
      @Valid @RequestBody StudentRequestDto requestDto) {
    return studentService.updateStudent(id, requestDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
  }
}
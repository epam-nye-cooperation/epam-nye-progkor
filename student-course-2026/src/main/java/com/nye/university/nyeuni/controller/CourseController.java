package com.nye.university.nyeuni.controller;

import com.nye.university.nyeuni.dto.course.CourseRequestDto;
import com.nye.university.nyeuni.dto.course.CourseResponseDto;
import com.nye.university.nyeuni.service.CourseService;
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

/** REST endpoints for course CRUD operations. */
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CourseResponseDto createCourse(@Valid @RequestBody CourseRequestDto requestDto) {
    return courseService.createCourse(requestDto);
  }

  @GetMapping
  public List<CourseResponseDto> getAllCourses() {
    return courseService.getAllCourses();
  }

  @GetMapping("/{id}")
  public CourseResponseDto getCourseById(@PathVariable Long id) {
    return courseService.getCourseById(id);
  }

  @PutMapping("/{id}")
  public CourseResponseDto updateCourse(@PathVariable Long id,
      @Valid @RequestBody CourseRequestDto courseRequestDto) {
    return courseService.updateCourse(id, courseRequestDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCourse(@PathVariable Long id) {
    courseService.deleteCourse(id);
  }

}

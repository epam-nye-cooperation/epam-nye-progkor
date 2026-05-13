package com.nye.university.nyeuni.service;

import com.nye.university.nyeuni.dto.course.CourseRequestDto;
import com.nye.university.nyeuni.dto.course.CourseResponseDto;
import com.nye.university.nyeuni.entity.Course;
import com.nye.university.nyeuni.exception.ResourceNotFoundException;
import com.nye.university.nyeuni.repository.CourseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Business logic for course management operations. */
@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  /**
   * Creates a new course.
   *
   * @param courseRequestDto requested course data
   * @return saved course data
   */
  public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
    Course course = Course.builder()
        .name(courseRequestDto.getName())
        .teacherName(courseRequestDto.getTeacherName())
        .build();
    Course savedCourse = courseRepository.save(course);
    return mapToDto(savedCourse);
  }

  /**
   * Returns every course.
   *
   * @return list of courses
   */
  public List<CourseResponseDto> getAllCourses() {
    return courseRepository.findAll()
        .stream()
        .map(this::mapToDto)
        .toList();
  }

  /**
   * Returns a course by identifier.
   *
   * @param id course identifier
   * @return course data
   */
  public CourseResponseDto getCourseById(Long id) {
    Optional<Course> courseOptional = courseRepository.findById(id);
    Course course = courseOptional
        .orElseThrow(
            () -> new ResourceNotFoundException("Course not found by id: " + id)
        );
    return mapToDto(course);
  }

  /**
   * Updates an existing course.
   *
   * @param id identifier of course to update
   * @param requestDto updated course values
   * @return updated course data
   */
  public CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto) {
    Course course = courseRepository.findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Course not found by id: " + id)
        );

    course.setName(requestDto.getName());
    course.setTeacherName(requestDto.getTeacherName());

    Course updatedCourse = courseRepository.save(course);
    return mapToDto(updatedCourse);
  }

  public void deleteCourse(Long id) {
    courseRepository.deleteById(id);
  }

  private CourseResponseDto mapToDto(Course course) {
    return CourseResponseDto.builder()
        .id(course.getId())
        .name(course.getName())
        .teacherName(course.getTeacherName())
        .build();
  }
}

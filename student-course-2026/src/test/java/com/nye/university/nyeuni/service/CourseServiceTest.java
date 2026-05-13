package com.nye.university.nyeuni.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nye.university.nyeuni.dto.course.CourseRequestDto;
import com.nye.university.nyeuni.dto.course.CourseResponseDto;
import com.nye.university.nyeuni.entity.Course;
import com.nye.university.nyeuni.exception.ResourceNotFoundException;
import com.nye.university.nyeuni.repository.CourseRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

  @Mock
  private CourseRepository courseRepositoryMock;

  @InjectMocks
  private CourseService underTest;

  @Test
  void createCourseWhenCourseIsSaved() {
    //GIVEN
    CourseRequestDto requestDto = CourseRequestDto.builder()
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    Course savedCourse = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    CourseResponseDto expected = CourseResponseDto.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    when(courseRepositoryMock.save(any(Course.class))).thenReturn(savedCourse);

    //WHEN
    CourseResponseDto result = underTest.createCourse(requestDto);

    //THEN
    assertEquals(expected, result);
  }

  @Test
  void getAllCoursesWhenCoursesExistThenReturnMappedCourses() {
    //GIVEN
    Course course1 = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    Course course2 = Course.builder()
        .id(2L)
        .name("Physics")
        .teacherName("Dr. Brown")
        .build();

    when(courseRepositoryMock.findAll()).thenReturn(List.of(course1, course2));

    //WHEN
    List<CourseResponseDto> result = underTest.getAllCourses();

    //THEN
    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals("Mathematics", result.get(0).getName());
    assertEquals("Dr. Smith", result.get(0).getTeacherName());
    assertEquals(2L, result.get(1).getId());
    assertEquals("Physics", result.get(1).getName());
    assertEquals("Dr. Brown", result.get(1).getTeacherName());
  }

  @Test
  void getAllCoursesWhenNoCoursesExistThenReturnEmptyList() {
    //GIVEN
    when(courseRepositoryMock.findAll()).thenReturn(List.of());

    //WHEN
    List<CourseResponseDto> result = underTest.getAllCourses();

    //THEN
    assertEquals(0, result.size());
  }

  @Test
  void getCourseByIdWhenCourseExistsThenReturnCourse() {
    //GIVEN
    Course course = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();
    when(courseRepositoryMock.findById(1L)).thenReturn(Optional.of(course));

    //WHEN
    CourseResponseDto result = underTest.getCourseById(1L);

    //THEN
    assertEquals(1L, result.getId());
    assertEquals("Mathematics", result.getName());
    assertEquals("Dr. Smith", result.getTeacherName());
  }

  @Test
  void getCourseByIdWhenCourseDoesNotExistThenThrowException() {
    //GIVEN
    when(courseRepositoryMock.findById(99L)).thenReturn(Optional.empty());

    //WHEN
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> underTest.getCourseById(99L)
    );

    //THEN
    assertEquals("Course not found by id: 99", exception.getMessage());
  }

  @Test
  void updateCourseWhenCourseExistsThenUpdateCourse() {
    //GIVEN
    CourseRequestDto requestDto = CourseRequestDto.builder()
        .name("Advanced Mathematics")
        .teacherName("Dr. Green")
        .build();

    Course existingCourse = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    Course updatedCourse = Course.builder()
        .id(1L)
        .name("Advanced Mathematics")
        .teacherName("Dr. Green")
        .build();

    when(courseRepositoryMock.findById(1L)).thenReturn(Optional.of(existingCourse));
    when(courseRepositoryMock.save(existingCourse)).thenReturn(updatedCourse);

    //WHEN
    CourseResponseDto result = underTest.updateCourse(1L, requestDto);

    //THEN
    assertEquals(1L, result.getId());
    assertEquals("Advanced Mathematics", result.getName());
    assertEquals("Dr. Green", result.getTeacherName());
  }

  @Test
  void updateCourseWhenCourseDoesNotExistThenThrowException() {
    //GIVEN
    CourseRequestDto requestDto = CourseRequestDto.builder()
        .name("Advanced Mathematics")
        .teacherName("Dr. Green")
        .build();
    when(courseRepositoryMock.findById(99L)).thenReturn(Optional.empty());

    //WHEN
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> underTest.updateCourse(99L, requestDto)
    );

    //THEN
    assertEquals("Course not found by id: 99", exception.getMessage());
  }

  @Test
  void deleteCourseWhenCalledThenDeleteCourseById() {
    //GIVEN
    Long courseId = 1L;

    //WHEN
    underTest.deleteCourse(courseId);

    //THEN
    verify(courseRepositoryMock).deleteById(courseId);
  }
}


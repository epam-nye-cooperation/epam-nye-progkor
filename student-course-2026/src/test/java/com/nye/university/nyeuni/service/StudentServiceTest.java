package com.nye.university.nyeuni.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nye.university.nyeuni.dto.student.StudentRequestDto;
import com.nye.university.nyeuni.dto.student.StudentResponseDto;
import com.nye.university.nyeuni.entity.Course;
import com.nye.university.nyeuni.entity.Student;
import com.nye.university.nyeuni.exception.ResourceNotFoundException;
import com.nye.university.nyeuni.repository.CourseRepository;
import com.nye.university.nyeuni.repository.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository studentRepositoryMock;
  @Mock
  private CourseRepository courseRepositoryMock;

  @InjectMocks
  private StudentService underTest;

  @Test
  void createStudentWhenStudentIsSaved() {
    //GIVEN
    StudentRequestDto studentRequestDto = StudentRequestDto.builder()
        .name("John Doe")
        .age(20)
        .courseId(1L)
        .build();
    Course course = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();
    Student student = Student.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .course(course)
        .build();

    StudentResponseDto expectedStudentResponseDto = StudentResponseDto.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .courseId(1L)
        .courseName("Mathematics")
        .build();

    when(courseRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(course));
    when(studentRepositoryMock.save(any(Student.class))).thenReturn(student);

    //WHEN
    StudentResponseDto result = underTest.createStudent(studentRequestDto);
    //THEN
    assertEquals(expectedStudentResponseDto, result);
  }

  @Test
  void createStudentWhenCourseDoesNotExistThenThrowException() {
    //GIVEN
    StudentRequestDto studentRequestDto = StudentRequestDto.builder()
        .name("John Doe")
        .age(20)
        .courseId(99L)
        .build();
    when(courseRepositoryMock.findById(99L)).thenReturn(Optional.empty());

    //WHEN
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> underTest.createStudent(studentRequestDto)
    );

    //THEN
    assertEquals("Course not found by id: 99", exception.getMessage());
    verify(studentRepositoryMock, never()).save(any(Student.class));
  }

  @Test
  void getAllStudentsWhenStudentsExistThenReturnMappedStudents() {
    //GIVEN
    Course course = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    Student studentWithCourse = Student.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .course(course)
        .build();

    Student studentWithoutCourse = Student.builder()
        .id(11L)
        .name("Jane Doe")
        .age(21)
        .course(null)
        .build();

    when(studentRepositoryMock.findAll())
        .thenReturn(List.of(studentWithCourse, studentWithoutCourse));

    //WHEN
    List<StudentResponseDto> result = underTest.getAllStudents();

    //THEN
    assertEquals(2, result.size());
    assertEquals(10L, result.get(0).getId());
    assertEquals("Mathematics", result.get(0).getCourseName());
    assertEquals(11L, result.get(1).getId());
    assertNull(result.get(1).getCourseId());
    assertNull(result.get(1).getCourseName());
  }

  @Test
  void getAllStudentsWhenNoStudentsExistThenReturnEmptyList() {
    //GIVEN
    when(studentRepositoryMock.findAll()).thenReturn(List.of());

    //WHEN
    List<StudentResponseDto> result = underTest.getAllStudents();

    //THEN
    assertEquals(0, result.size());
  }

  @Test
  void getStudentByIdWhenStudentExistsThenReturnStudent() {
    //GIVEN
    Course course = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();
    Student student = Student.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .course(course)
        .build();
    when(studentRepositoryMock.findById(10L)).thenReturn(Optional.of(student));

    //WHEN
    StudentResponseDto result = underTest.getStudentById(10L);

    //THEN
    assertEquals(10L, result.getId());
    assertEquals("John Doe", result.getName());
    assertEquals(20, result.getAge());
    assertEquals(1L, result.getCourseId());
    assertEquals("Mathematics", result.getCourseName());
  }

  @Test
  void getStudentByIdWhenStudentDoesNotExistThenThrowException() {
    //GIVEN
    when(studentRepositoryMock.findById(99L)).thenReturn(Optional.empty());

    //WHEN
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> underTest.getStudentById(99L)
    );

    //THEN
    assertEquals("Student not found by id: 99", exception.getMessage());
  }

  @Test
  void updateStudentWhenStudentAndCourseExistThenUpdateStudent() {
    //GIVEN
    StudentRequestDto studentRequestDto = StudentRequestDto.builder()
        .name("Updated Name")
        .age(22)
        .courseId(2L)
        .build();

    Course existingCourse = Course.builder()
        .id(1L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    Course newCourse = Course.builder()
        .id(2L)
        .name("Physics")
        .teacherName("Dr. Brown")
        .build();

    Student existingStudent = Student.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .course(existingCourse)
        .build();

    Student updatedStudent = Student.builder()
        .id(10L)
        .name("Updated Name")
        .age(22)
        .course(newCourse)
        .build();

    when(studentRepositoryMock.findById(10L)).thenReturn(Optional.of(existingStudent));
    when(courseRepositoryMock.findById(2L)).thenReturn(Optional.of(newCourse));
    when(studentRepositoryMock.save(existingStudent)).thenReturn(updatedStudent);

    //WHEN
    StudentResponseDto result = underTest.updateStudent(10L, studentRequestDto);

    //THEN
    assertEquals(10L, result.getId());
    assertEquals("Updated Name", result.getName());
    assertEquals(22, result.getAge());
    assertEquals(2L, result.getCourseId());
    assertEquals("Physics", result.getCourseName());
  }

  @Test
  void updateStudentWhenStudentDoesNotExistThenThrowException() {
    //GIVEN
    StudentRequestDto studentRequestDto = StudentRequestDto.builder()
        .name("Updated Name")
        .age(22)
        .courseId(2L)
        .build();
    when(studentRepositoryMock.findById(99L)).thenReturn(Optional.empty());

    //WHEN
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> underTest.updateStudent(99L, studentRequestDto)
    );

    //THEN
    assertEquals("Student not found by id: 99", exception.getMessage());
    verify(courseRepositoryMock, never()).findById(any(Long.class));
    verify(studentRepositoryMock, never()).save(any(Student.class));
  }

  @Test
  void updateStudentWhenCourseDoesNotExistThenThrowException() {
    //GIVEN
    StudentRequestDto studentRequestDto = StudentRequestDto.builder()
        .name("Updated Name")
        .age(22)
        .courseId(99L)
        .build();
    Student existingStudent = Student.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .build();

    when(studentRepositoryMock.findById(10L)).thenReturn(Optional.of(existingStudent));
    when(courseRepositoryMock.findById(99L)).thenReturn(Optional.empty());

    //WHEN
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> underTest.updateStudent(10L, studentRequestDto)
    );

    //THEN
    assertEquals("Course not found by id: 99", exception.getMessage());
    verify(studentRepositoryMock, never()).save(any(Student.class));
  }

  @Test
  void deleteStudentWhenStudentExistsThenDeleteStudent() {
    //GIVEN
    when(studentRepositoryMock.existsById(10L)).thenReturn(true);

    //WHEN
    underTest.deleteStudent(10L);

    //THEN
    verify(studentRepositoryMock).deleteById(10L);
  }

  @Test
  void deleteStudentWhenStudentDoesNotExistThenThrowException() {
    //GIVEN
    when(studentRepositoryMock.existsById(99L)).thenReturn(false);

    //WHEN
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> underTest.deleteStudent(99L)
    );

    //THEN
    assertEquals("Student not found by id: 99", exception.getMessage());
    verify(studentRepositoryMock, never()).deleteById(any(Long.class));
  }

}

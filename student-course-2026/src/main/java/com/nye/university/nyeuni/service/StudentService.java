package com.nye.university.nyeuni.service;

import com.nye.university.nyeuni.dto.student.StudentRequestDto;
import com.nye.university.nyeuni.dto.student.StudentResponseDto;
import com.nye.university.nyeuni.entity.Course;
import com.nye.university.nyeuni.entity.Student;
import com.nye.university.nyeuni.exception.ResourceNotFoundException;
import com.nye.university.nyeuni.repository.CourseRepository;
import com.nye.university.nyeuni.repository.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Business logic for student management operations. */
@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;
  private final CourseRepository courseRepository;

  /**
   * Creates a new student.
   *
   * @param requestDto requested student data
   * @return saved student data
   */
  public StudentResponseDto createStudent(StudentRequestDto requestDto) {

    Course course = findCourseById(requestDto.getCourseId());

    Student student = Student.builder()
        .name(requestDto.getName())
        .age(requestDto.getAge())
        .course(course)
        .build();

    Student savedStudent = studentRepository.save(student);
    return mapToDto(savedStudent);
  }

  /**
   * Returns every student.
   *
   * @return list of students
   */
  public List<StudentResponseDto> getAllStudents() {
    return studentRepository.findAll()
        .stream()
        .map(this::mapToDto)
        .toList();
  }

  /**
   * Returns a student by identifier.
   *
   * @param id student identifier
   * @return student data
   */
  public StudentResponseDto getStudentById(Long id) {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found by id: " + id));
    return mapToDto(student);
  }

  /**
   * Updates an existing student.
   *
   * @param id identifier of student to update
   * @param requestDto updated student values
   * @return updated student data
   */
  public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found by id: " + id));

    Course course = findCourseById(requestDto.getCourseId());

    student.setName(requestDto.getName());
    student.setAge(requestDto.getAge());
    student.setCourse(course);

    Student updatedStudent = studentRepository.save(student);
    return mapToDto(updatedStudent);
  }

  /**
   * Deletes a student by identifier.
   *
   * @param id student identifier
   */
  public void deleteStudent(Long id) {
    if (!studentRepository.existsById(id)) {
      throw new ResourceNotFoundException("Student not found by id: " + id);
    }
    studentRepository.deleteById(id);
  }

  private Course findCourseById(Long id) {
    return courseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Course not found by id: " + id));
  }

  private StudentResponseDto mapToDto(Student student) {
    Course course = student.getCourse();
    return StudentResponseDto.builder()
        .id(student.getId())
        .name(student.getName())
        .age(student.getAge())
        .courseId(course != null ? course.getId() : null)
        .courseName(course != null ? course.getName() : null)
        .build();
  }
}
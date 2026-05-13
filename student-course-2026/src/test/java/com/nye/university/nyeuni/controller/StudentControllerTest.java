package com.nye.university.nyeuni.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nye.university.nyeuni.dto.student.StudentResponseDto;
import com.nye.university.nyeuni.service.StudentService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService studentService;

  @Test
  void createStudentWhenRequestIsValidThenReturnCreatedStudent() throws Exception {
    //GIVEN
    StudentResponseDto responseDto = StudentResponseDto.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .courseId(1L)
        .courseName("Mathematics")
        .build();

    when(studentService.createStudent(org.mockito.ArgumentMatchers.any())).thenReturn(responseDto);

    //WHEN + THEN
    mockMvc.perform(post("/api/students")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "name": "John Doe",
                  "age": 20,
                  "courseId": 1
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.name").value("John Doe"))
        .andExpect(jsonPath("$.age").value(20))
        .andExpect(jsonPath("$.courseId").value(1))
        .andExpect(jsonPath("$.courseName").value("Mathematics"));

    verify(studentService).createStudent(org.mockito.ArgumentMatchers.any());
    verifyNoMoreInteractions(studentService);
  }

  @Test
  void createStudentWhenNameIsBlankThenReturnBadRequest() throws Exception {
    //WHEN + THEN
    mockMvc.perform(post("/api/students")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "name": " ",
                  "age": 20,
                  "courseId": 1
                }
                """))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(studentService);
  }

  @Test
  void createStudentWhenCourseIdIsMissingThenReturnBadRequest() throws Exception {
    //WHEN + THEN
    mockMvc.perform(post("/api/students")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "name": "John Doe",
                  "age": 20
                }
                """))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(studentService);
  }

  @Test
  void getAllStudentsWhenServiceReturnsStudentsThenReturnOk() throws Exception {
    //GIVEN
    StudentResponseDto student1 = StudentResponseDto.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .courseId(1L)
        .courseName("Mathematics")
        .build();

    StudentResponseDto student2 = StudentResponseDto.builder()
        .id(11L)
        .name("Jane Doe")
        .age(21)
        .courseId(2L)
        .courseName("Physics")
        .build();

    when(studentService.getAllStudents()).thenReturn(List.of(student1, student2));

    //WHEN + THEN
    mockMvc.perform(get("/api/students"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(10))
        .andExpect(jsonPath("$[0].name").value("John Doe"))
        .andExpect(jsonPath("$[1].id").value(11))
        .andExpect(jsonPath("$[1].name").value("Jane Doe"));

    verify(studentService).getAllStudents();
    verifyNoMoreInteractions(studentService);
  }

  @Test
  void getStudentByIdWhenStudentExistsThenReturnOk() throws Exception {
    //GIVEN
    StudentResponseDto responseDto = StudentResponseDto.builder()
        .id(10L)
        .name("John Doe")
        .age(20)
        .courseId(1L)
        .courseName("Mathematics")
        .build();

    when(studentService.getStudentById(10L)).thenReturn(responseDto);

    //WHEN + THEN
    mockMvc.perform(get("/api/students/{id}", 10L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.name").value("John Doe"));

    verify(studentService).getStudentById(10L);
    verifyNoMoreInteractions(studentService);
  }

  @Test
  void updateStudentWhenRequestIsValidThenReturnUpdatedStudent() throws Exception {
    //GIVEN
    StudentResponseDto responseDto = StudentResponseDto.builder()
        .id(10L)
        .name("Updated Name")
        .age(22)
        .courseId(2L)
        .courseName("Physics")
        .build();

    when(studentService.updateStudent(org.mockito.ArgumentMatchers.eq(10L),
        org.mockito.ArgumentMatchers.any())).thenReturn(responseDto);

    //WHEN + THEN
    mockMvc.perform(put("/api/students/{id}", 10L)
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "name": "Updated Name",
                  "age": 22,
                  "courseId": 2
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.name").value("Updated Name"))
        .andExpect(jsonPath("$.courseId").value(2));

    verify(studentService).updateStudent(org.mockito.ArgumentMatchers.eq(10L),
        org.mockito.ArgumentMatchers.any());
    verifyNoMoreInteractions(studentService);
  }

  @Test
  void deleteStudentWhenStudentExistsThenReturnNoContent() throws Exception {
    //WHEN + THEN
    mockMvc.perform(delete("/api/students/{id}", 10L))
        .andExpect(status().isNoContent());

    verify(studentService).deleteStudent(10L);
    verifyNoMoreInteractions(studentService);
  }
}

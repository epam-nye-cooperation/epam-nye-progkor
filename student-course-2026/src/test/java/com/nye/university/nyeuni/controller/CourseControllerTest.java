package com.nye.university.nyeuni.controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.nye.university.nyeuni.dto.course.CourseResponseDto;
import com.nye.university.nyeuni.service.CourseService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CourseService courseService;

  @Test
  void createCourseWhenRequestIsValidThenReturnCreatedCourse() throws Exception {
    //GIVEN
    CourseResponseDto responseDto = CourseResponseDto.builder()
        .id(10L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    when(courseService.createCourse(any())).thenReturn(responseDto);

    //WHEN + THEN
    mockMvc.perform(post("/api/courses")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "name": "Mathematics",
                  "teacherName": "Dr. Smith"
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.name").value("Mathematics"))
        .andExpect(jsonPath("$.teacherName").value("Dr. Smith"));

    verify(courseService).createCourse(any());
    verifyNoMoreInteractions(courseService);
  }

  @Test
  void createCourseWhenNameIsBlankThenReturnBadRequest() throws Exception {
    //WHEN + THEN
    mockMvc.perform(post("/api/courses")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "name": " ",
                  "teacherName": "Dr. Smith"
                }
                """))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(courseService);
  }

  @Test
  void createCourseWhenNameIsMissingThenReturnBadRequest() throws Exception {
    //WHEN + THEN
    mockMvc.perform(post("/api/courses")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "teacherName": "Dr. Smith"
                }
                """))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(courseService);
  }

  @Test
  void getAllCoursesWhenServiceReturnsCoursesThenReturnOk() throws Exception {
    //GIVEN
    CourseResponseDto course1 = CourseResponseDto.builder()
        .id(10L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    CourseResponseDto course2 = CourseResponseDto.builder()
        .id(11L)
        .name("Physics")
        .teacherName("Dr. Brown")
        .build();

    when(courseService.getAllCourses()).thenReturn(List.of(course1, course2));

    //WHEN + THEN
    mockMvc.perform(get("/api/courses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(10))
        .andExpect(jsonPath("$[0].name").value("Mathematics"))
        .andExpect(jsonPath("$[1].id").value(11))
        .andExpect(jsonPath("$[1].name").value("Physics"));

    verify(courseService).getAllCourses();
    verifyNoMoreInteractions(courseService);
  }

  @Test
  void getCourseByIdWhenCourseExistsThenReturnOk() throws Exception {
    //GIVEN
    CourseResponseDto responseDto = CourseResponseDto.builder()
        .id(10L)
        .name("Mathematics")
        .teacherName("Dr. Smith")
        .build();

    when(courseService.getCourseById(10L)).thenReturn(responseDto);

    //WHEN + THEN
    mockMvc.perform(get("/api/courses/{id}", 10L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.name").value("Mathematics"));

    verify(courseService).getCourseById(10L);
    verifyNoMoreInteractions(courseService);
  }

  @Test
  void updateCourseWhenRequestIsValidThenReturnUpdatedCourse() throws Exception {
    //GIVEN
    CourseResponseDto responseDto = CourseResponseDto.builder()
        .id(10L)
        .name("Advanced Mathematics")
        .teacherName("Dr. Taylor")
        .build();

    when(courseService.updateCourse(org.mockito.ArgumentMatchers.eq(10L),
        any())).thenReturn(responseDto);

    //WHEN + THEN
    mockMvc.perform(put("/api/courses/{id}", 10L)
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "name": "Advanced Mathematics",
                  "teacherName": "Dr. Taylor"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.name").value("Advanced Mathematics"))
        .andExpect(jsonPath("$.teacherName").value("Dr. Taylor"));

    verify(courseService).updateCourse(org.mockito.ArgumentMatchers.eq(10L),
        any());
    verifyNoMoreInteractions(courseService);
  }

  @Test
  void deleteCourseWhenCourseExistsThenReturnNoContent() throws Exception {
    //WHEN + THEN
    mockMvc.perform(delete("/api/courses/{id}", 10L))
        .andExpect(status().isNoContent());

    verify(courseService).deleteCourse(10L);
    verifyNoMoreInteractions(courseService);
  }
}


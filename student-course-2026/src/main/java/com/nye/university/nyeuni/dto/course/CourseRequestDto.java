package com.nye.university.nyeuni.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Request payload for creating or updating a course. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

  @NotBlank(message = "Course name is required!")
  private String name;
  private String teacherName;
}

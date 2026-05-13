package com.nye.university.nyeuni.dto.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response payload that represents a course. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

  private Long id;
  private String name;
  private String teacherName;

}

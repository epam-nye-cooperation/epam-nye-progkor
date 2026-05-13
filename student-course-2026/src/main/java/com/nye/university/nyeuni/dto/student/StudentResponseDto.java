package com.nye.university.nyeuni.dto.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response payload that represents a student. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

  private Long id;
  private String name;
  private Integer age;
  private Long courseId;
  private String courseName;
}

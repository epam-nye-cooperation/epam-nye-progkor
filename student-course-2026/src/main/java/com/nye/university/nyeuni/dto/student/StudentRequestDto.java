package com.nye.university.nyeuni.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Request payload for creating or updating a student. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {
  @NotBlank(message = "Student name is required!")
  private String name;

  private Integer age;

  @NotNull(message = "Course id is required!")
  private Long courseId; // renamed from curseId
}

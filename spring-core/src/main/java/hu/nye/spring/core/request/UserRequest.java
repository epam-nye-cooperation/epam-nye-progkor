package hu.nye.spring.core.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {

    @NotEmpty
    private String name;

    @PositiveOrZero
    private int age;

    @Email
    private String email;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate registrationDate;
}

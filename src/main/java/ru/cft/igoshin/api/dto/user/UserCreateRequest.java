package ru.cft.igoshin.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserCreateRequest(
        @NotNull
        @Length(min = 2, max = 50)
        @Pattern(regexp = "^[А-ЯЁ][а-яА-ЯёЁ-]+$")
        String lastName,

        @NotNull
        @Length(min = 2, max = 50)
        @Pattern(regexp = "^[А-ЯЁ][а-яА-ЯёЁ-]+$")
        String firstName,

        @Length(max = 50)
        @Pattern(regexp = "^(|[А-ЯЁ][а-яА-ЯёЁ-]*)$")
        String middleName,

        @NotNull
        @Pattern(regexp = "^7\\d{10}$")
        String phone,

        @NotNull
        @Email
        String email,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate,

        @NotNull
        @Length(min = 8, max = 64)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?])[a-zA-Z0-9!?]+$")
        String password
) {
}

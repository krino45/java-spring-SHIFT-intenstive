package ru.cft.igoshin.api.dto.user;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserPatchRequest(
        @Length(min = 1, max = 50)
        @Pattern(regexp = "^[А-ЯЁ][а-яА-ЯёЁ-]+$")
        String lastName,

        @Length(min = 1, max = 50)
        @Pattern(regexp = "^[А-ЯЁ][а-яА-ЯёЁ-]+$")
        String firstName,

        @Length(max = 50)
        @Pattern(regexp = "^(|[А-ЯЁ][а-яА-ЯёЁ-]*)$")
        String middleName,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate
) {
}

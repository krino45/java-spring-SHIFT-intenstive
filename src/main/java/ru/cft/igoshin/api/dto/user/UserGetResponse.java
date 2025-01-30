package ru.cft.igoshin.api.dto.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserGetResponse(
        @NotNull
        String lastName,

        @NotNull
        String firstName,

        String middleName,

        @NotNull
        String phone,

        @NotNull
        String email,

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate
) {
}

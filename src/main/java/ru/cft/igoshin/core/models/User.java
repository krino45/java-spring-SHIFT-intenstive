package ru.cft.igoshin.core.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

public record User(
        UUID id,
        String surname,
        String name,
        String middleName,
        int phoneNumber,
        String email,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate birthdate,
        String hashedPassword,
        Timestamp created,
        Timestamp updated
) {
}

package ru.cft.igoshin.api.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizedUserGetResponse extends UserGetResponse {
        String middleName;
        String email;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate;
}

package ru.cft.igoshin.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGetResponse {
        String lastName;
        String firstName;
        String phone;
}

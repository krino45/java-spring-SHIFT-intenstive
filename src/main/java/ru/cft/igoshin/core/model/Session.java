package ru.cft.igoshin.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions")
public class Session {
    @Id
    private UUID sessionToken;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationDate;
    private boolean isAlive;
    private LocalDateTime TTL;
}

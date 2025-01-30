package ru.cft.igoshin.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("session")
public class SessionProperties {
    private long ttl;
}
package ru.cft.igoshin.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.cft.igoshin.core.model.converter.dto.*;

@Configuration
public class ModelConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTransferTypeConverter());
        registry.addConverter(new StringToStatusConverter());
    }
}

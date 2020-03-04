package com.innoviaai.music.musiclibrary.config;

import com.innovia.ai.music.datasource.db.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@EnableMongoRepositories(basePackages = "com.innovia.ai.music.datasource.db.repository")
public class ApplicationConfig {

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}

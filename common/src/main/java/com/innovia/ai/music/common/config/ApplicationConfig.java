package com.innovia.ai.music.common.config;

import com.innovia.ai.music.common.datasource.db.model.SongDbModel;
import com.innovia.ai.music.common.dto.Song;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper getMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<Song, SongDbModel>() {
            @Override
            protected void configure() {
                skip(destination.getDateCreated());
            }
        });
        return mapper;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}

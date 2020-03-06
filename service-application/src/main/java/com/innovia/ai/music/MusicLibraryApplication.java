package com.innovia.ai.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.innovia.ai.music.common.*", "com.innovia.ai.music.*"})
@EntityScan(basePackages = "com.innovia.ai.music.common.*")
@EnableJpaRepositories(basePackages = "com.innovia.ai.music.common.*")
@EnableAutoConfiguration
@EnableSwagger2
public class MusicLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicLibraryApplication.class, args);
    }
}

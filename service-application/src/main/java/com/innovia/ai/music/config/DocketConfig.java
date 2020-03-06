package com.innovia.ai.music.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@Configuration
public class DocketConfig {

    @Bean
    public Docket serviceProviderApi(Environment env) {
        Contact contact = new Contact("Peter Edike",
                "https://github.com/eyekaypeter19/music-library",
                "peter.edike@outlook.com");
        ApiInfo apiInfo = new ApiInfo("Music Library", "",
                "0.0.1-SNAPSHOT", "", contact, "",
                "", new ArrayList<>());

        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(true)
                .pathMapping("/").select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
                .apis(Predicates
                        .not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo)
                .genericModelSubstitutes(Optional.class)
                .directModelSubstitute(Timestamp.class, String.class);
    }
}

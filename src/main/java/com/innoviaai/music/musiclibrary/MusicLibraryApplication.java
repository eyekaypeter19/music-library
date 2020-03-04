package com.innoviaai.music.musiclibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.innovia.ai.music", "com.innoviaai"})
public class MusicLibraryApplication {


	public static void main(String[] args) {
		SpringApplication.run(MusicLibraryApplication.class, args);
	}

}

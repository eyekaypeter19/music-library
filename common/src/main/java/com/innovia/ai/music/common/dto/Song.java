package com.innovia.ai.music.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Song {

    @NotNull
    @JsonProperty("title")
    @NotNull(message = "Song title field is required")
    @NotBlank(message = "Song title field is required")
    @Size(max = 250, min = 3, message = "Song title must be between 3 and 250 characters long")
    private String songTitle;

    @JsonProperty("author")
    @NotNull(message = "Author field is required")
    @NotBlank(message = "Author field is required")
    @Size(max = 250, min = 3, message = "Song Author must be between 3 and 250 characters long")
    private String songAuthor;


    @JsonProperty("id")
    private Long id;
}

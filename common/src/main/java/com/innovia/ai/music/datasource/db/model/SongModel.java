package com.innovia.ai.music.datasource.db.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "songs")
@Getter
@Setter
public class SongModel {

    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    private String author;

    private Date dateCreated;

}

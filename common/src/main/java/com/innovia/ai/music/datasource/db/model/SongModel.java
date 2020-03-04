package com.innovia.ai.music.datasource.db.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;


@Getter
@Setter
@Entity(name = "Song")
public class SongModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "song_title", nullable = false)
    private String title;

    @Column(name = "song_author", nullable = false)
    private String author;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

}

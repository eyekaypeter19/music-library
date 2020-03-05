package com.innovia.ai.music.common.datasource.db.model;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Song")
@Immutable
public class SongDbModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "song_title", nullable = false)
    private String title;

    @Column(name = "song_author", nullable = false)
    private String author;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}

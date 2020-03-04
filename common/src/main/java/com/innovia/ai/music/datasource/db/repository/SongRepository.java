package com.innovia.ai.music.datasource.db.repository;


import com.innovia.ai.music.datasource.db.model.SongModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface SongRepository extends MongoRepository<SongModel, String> {

    public SongModel findByTitle(String title);
    public Page<SongModel> findByAuthor(String author, Pageable pageable);
}

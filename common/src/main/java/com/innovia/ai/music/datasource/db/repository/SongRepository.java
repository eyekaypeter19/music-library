package com.innovia.ai.music.datasource.db.repository;


import com.innovia.ai.music.datasource.db.model.SongModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SongRepository extends JpaRepository<SongModel, Long> {

    public Page<SongModel> findBySongTitleContaining(String title, Pageable pageable);

    public Page<SongModel> findBySongAuthorContaining(String author, Pageable pageable);
}

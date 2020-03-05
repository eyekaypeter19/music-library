package com.innovia.ai.music.common.datasource.db.repository;


import com.innovia.ai.music.common.datasource.db.model.SongDbModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;;
import org.springframework.stereotype.Repository;


@Repository
public interface SongRepository extends JpaRepository<SongDbModel, Long> {

    public Page<SongDbModel> findByTitleContaining(String title, Pageable pageable);

    public Page<SongDbModel> findByAuthorContaining(String author, Pageable pageable);
}

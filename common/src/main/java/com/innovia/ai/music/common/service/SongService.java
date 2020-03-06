package com.innovia.ai.music.common.service;

import com.innovia.ai.music.common.datasource.db.model.SongDbModel;
import com.innovia.ai.music.common.datasource.db.repository.SongRepository;
import com.innovia.ai.music.common.dto.Song;
import com.innovia.ai.music.common.exceptions.NotFoundException;
import com.innovia.ai.music.common.mapper.SongMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
@Slf4j
public class SongService {

    @Autowired
    SongRepository songRepository;

    @Setter
    SongMapper songMapper;

    @Autowired
    public SongService(SongMapper mapper) {
        this.songMapper = mapper;
    }

    public void create(@Validated Song song) {
        SongDbModel model = songMapper.map(song);
        model.setDateCreated(new Date());
        songRepository.save(model);
    }

    public Song update(@NotNull @Valid Song song) {
        Optional<SongDbModel> optionalModel = songRepository.findById(song.getId());
        if (optionalModel.isEmpty()) {
            throw new NotFoundException("Song with the specified Id does not exist");
        }
        SongDbModel model = optionalModel.get();
        model = songMapper.map(song);
        model = songRepository.save(model);
        return songMapper.map(model);
    }

    public void delete(@NotNull Long id) {
        try {
            this.songRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.getMessage());
        }

    }

    public Song get(@NotNull  Long id) {
        Optional<SongDbModel> model = this.songRepository.findById(id);
        if (model.isPresent()) {
            Song song = songMapper.map(model.get());
            return song;
        }
       throw new NotFoundException(String.format("Song with the id %s could not be found", id));
    }

    public Page<Song> getAll(String title, Pageable pageRequest) {
        Page<SongDbModel> byAuthor;
        if(Objects.isNull(title) || title.isBlank()) {
            byAuthor = this.songRepository.findAll(
                    pageRequest);
        }
        else {
            byAuthor = this.songRepository.findByTitleContaining(title,
                    pageRequest);
        }
        List<Song> songs = songMapper.map(byAuthor.getContent());
        return new PageImpl<>(songs, pageRequest, byAuthor.getTotalElements());

    }
}

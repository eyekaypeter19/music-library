package com.innovia.ai.music.service;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.innovia.ai.music.datasource.db.model.SongModel;
import com.innovia.ai.music.datasource.db.repository.SongRepository;
import com.innovia.ai.music.dto.Song;
import com.innovia.ai.music.exceptions.NotFoundException;
import com.innovia.ai.music.mapper.SongMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Validated
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
        SongModel model = songMapper.map(song);
        songRepository.save(model);
    }

    public Song update(@NotNull @Valid Song song) {
        Optional<SongModel> optionalModel = songRepository.findById(song.getId());
        if (optionalModel.isEmpty()) {
            throw new NotFoundException("Song with the specified Id does not exist");
        }
        SongModel model = optionalModel.get();
        model = songMapper.map(song);
        model = songRepository.save(model);
        return songMapper.map(model);
    }

    public void Delete(@NotNull Long id) {
        SongModel model = new SongModel();
        model.setId(id);
        this.songRepository.delete(model);
    }

    public Song get(@NotNull  Long id) {
        Optional<SongModel> model = this.songRepository.findById(id);
        if (model.isPresent()) {
            Song song = songMapper.map(model.get());
            return song;
        }
       throw new NotFoundException(String.format("Song with the id %s could not be found", id));
    }

    public Page<Song> getAll(@NotNull Song song, PageRequest pageRequest) {
        Page<SongModel> byAuthor = this.songRepository.findBySongAuthorContaining(song.getSongAuthor(), pageRequest);
        List<Song> songs = songMapper.map(byAuthor.getContent());
        return new PageImpl<>(songs, pageRequest, byAuthor.getTotalElements());
    }
}
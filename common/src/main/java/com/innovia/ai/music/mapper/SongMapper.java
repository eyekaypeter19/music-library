package com.innovia.ai.music.mapper;


import com.innovia.ai.music.datasource.db.model.SongModel;
import com.innovia.ai.music.dto.Song;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class SongMapper {

    private ModelMapper modelMapper;

    public SongMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SongModel map(Song song) {
        return this.modelMapper.map(song, SongModel.class);
    }

    public Song map(SongModel model) {
        return this.modelMapper.map(model, Song.class);
    }

    public List<Song> map(List<SongModel> content) {
        Type listType = new TypeToken<List<Song>>(){}.getType();
        List<Song> songList = modelMapper.map(content, listType);
        return songList;
    }
}

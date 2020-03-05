package com.innovia.ai.music.common.mapper;


import com.innovia.ai.music.common.datasource.db.model.SongDbModel;
import com.innovia.ai.music.common.dto.Song;
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

    public SongDbModel map(Song song) {
        return this.modelMapper.map(song, SongDbModel.class);
    }

    public Song map(SongDbModel model) {
        return this.modelMapper.map(model, Song.class);
    }

    public List<Song> map(List<SongDbModel> content) {
        Type listType = new TypeToken<List<Song>>(){}.getType();
        List<Song> songList = modelMapper.map(content, listType);
        return songList;
    }
}

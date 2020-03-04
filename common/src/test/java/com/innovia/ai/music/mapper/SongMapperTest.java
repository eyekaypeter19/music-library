package com.innovia.ai.music.mapper;

import com.github.javafaker.Faker;
import com.innovia.ai.music.datasource.db.model.SongModel;
import com.innovia.ai.music.dto.Song;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
public class SongMapperTest {

    SongMapper songMapper;
    Faker faker;

    @Before
    public void init() {
        ModelMapper mapper = new ModelMapper();
        songMapper = new SongMapper(mapper);
        Locale locale;
        faker = new Faker(Locale.ENGLISH);
    }

    @Test
    public void testMappingFromModelIsCorrect() {
        SongModel model = new SongModel();
        model.setAuthor(faker.name().nameWithMiddle());
        model.setDateCreated(new Date());
        model.setTitle(faker.funnyName().name());
        Song song = songMapper.map(model);
        assertEquals(model.getAuthor(), song.getSongAuthor());
        assertEquals(model.getTitle(), song.getSongTitle());
        assertEquals(model.getId(), song.getId());

    }

    @Test
    public void testMappingFromDTOIsCorrect() {
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.hipster().word());
        song.setId(faker.number().randomNumber());
        SongModel model = songMapper.map(song);
        assertEquals(song.getSongAuthor(), model.getAuthor());
        assertEquals(song.getSongTitle(), model.getTitle());
        assertEquals(song.getId(), model.getId());

    }

    @Test
    public void testMappingFromListOfModelsIsCorrect() {
        SongModel song = new SongModel();
        song.setAuthor(faker.artist().name());
        song.setTitle(faker.hipster().word());
        song.setId(faker.number().randomNumber());
        List<SongModel> songs = new ArrayList<>();
        songs.add(song);
        List<Song> mappedSongs = this.songMapper.map(songs);
        assertEquals(1, mappedSongs.size());
        assertTrue(mappedSongs.stream().anyMatch(t -> t.getSongTitle().equals(song.getTitle())));
        assertTrue(mappedSongs.stream().anyMatch(t -> t.getSongAuthor().equals(song.getAuthor())));

    }

}
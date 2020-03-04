package com.innovia.ai.music.service;

import com.github.javafaker.Faker;
import com.innovia.ai.music.config.ApplicationConfig;
import com.innovia.ai.music.datasource.db.model.SongModel;
import com.innovia.ai.music.datasource.db.repository.SongRepository;
import com.innovia.ai.music.dto.Song;
import com.innovia.ai.music.mapper.SongMapper;
import com.innovia.ai.music.service.SongService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class SongServiceTest {

    @Mock
    SongRepository songRepository;

    SongMapper mapper;

    @InjectMocks
    SongService songService;

    Faker faker;

    @Before
    public void setUp() {
        faker = new Faker(Locale.ENGLISH);
        mapper = new SongMapper(new ModelMapper());
        doReturn(getSongModel()).when(songRepository).insert(any(SongModel.class));
        doReturn(Optional.of(getSongModel())).when(songRepository).findById(anyString());
        MockitoAnnotations.initMocks(this);
        songService.setSongMapper(mapper);
    }

    @Test
    public void create() {
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.shakespeare().asYouLikeItQuote());
        songService.create(song);
        verify(songRepository, times(1)).insert(any(SongModel.class));
    }

    @Test
    public void update() {
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.shakespeare().asYouLikeItQuote());
        song.setId(ObjectId.get().toString());
        doReturn(getSongModel(song)).when(songRepository).save(any(SongModel.class));
        doReturn(Optional.of(getSongModel())).when(songRepository).findById(anyString());
        var newSong = songService.update(song);
        assertEquals(newSong.getId(), song.getId());
        assertEquals(song.getSongTitle(), newSong.getSongTitle());
        verify(songRepository, times(1)).findById(anyString());
        verify(songRepository, times(1)).save(any(SongModel.class));

    }

    @Test
    public void delete() {
        String identifier = ObjectId.get().toString();
        doNothing().when(songRepository).delete(any(SongModel.class));
        songService.Delete(identifier);
        verify(songRepository, times(1)).delete(any(SongModel.class));
    }

    @Test
    public void get() {
        String identifier = ObjectId.get().toString();
        SongModel returnedModel = getSongModel();
        doReturn(Optional.of(returnedModel)).when(songRepository).findById(eq(identifier));
        Song s = songService.get(identifier);
        assertEquals(s.getSongTitle(), returnedModel.getTitle());
        assertEquals(s.getSongAuthor(), returnedModel.getAuthor());
        verify(songRepository, atLeastOnce()).findById(eq(identifier));
    }

    @Test
    public void getAll() {
        Song song = new Song();
        song.setSongAuthor(faker.lordOfTheRings().character());
        doReturn(new PageImpl<>(List.of(getSongModel()), PageRequest.of(0,1), 1)).when(
                songRepository).findByAuthor(anyString(), any(Pageable.class));
        var res = songService.getAll(song, PageRequest.of(0,10));
        assertTrue(res.hasContent());
        verify(songRepository, times(1)).findByAuthor(eq(song.getSongAuthor()), eq(PageRequest.of(
                0, 10
        )));
    }


    private SongModel getSongModel() {
        SongModel model = new SongModel();
        model.setTitle(faker.ancient().god());
        model.setAuthor(faker.name().fullName());
        model.setDateCreated(new Date());
        model.setId(ObjectId.get().toString());
        return model;
    }

    private SongModel getSongModel(Song song) {
        return new SongMapper(new ModelMapper()).map(song);
    }
}
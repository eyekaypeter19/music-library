package com.innovia.ai.music.service;

import com.github.javafaker.Faker;
import com.innovia.ai.music.config.ApplicationConfig;
import com.innovia.ai.music.datasource.db.model.SongModel;
import com.innovia.ai.music.datasource.db.repository.SongRepository;
import com.innovia.ai.music.dto.Song;
import com.innovia.ai.music.mapper.SongMapper;

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

import java.util.*;

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
        doReturn(getSongModel()).when(songRepository).save(any(SongModel.class));
        doReturn(Optional.of(getSongModel())).when(songRepository).findById(anyLong());
        MockitoAnnotations.initMocks(this);
        songService.setSongMapper(mapper);
    }

    @Test
    public void create() {
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.shakespeare().asYouLikeItQuote());
        songService.create(song);
        verify(songRepository, times(1)).save(any(SongModel.class));
    }

    @Test
    public void update() {
        var id = new Random().nextLong();
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.shakespeare().asYouLikeItQuote());
        song.setId(id);
        doReturn(getSongModel(song)).when(songRepository).save(any(SongModel.class));
        doReturn(Optional.of(getSongModel())).when(songRepository).findById(anyLong());
        var newSong = songService.update(song);
        assertEquals(newSong.getId(), song.getId());
        assertEquals(song.getSongTitle(), newSong.getSongTitle());
        verify(songRepository, times(1)).findById(anyLong());
        verify(songRepository, times(1)).save(any(SongModel.class));

    }

    @Test
    public void delete() {
        var id = new Random().nextLong();
        doNothing().when(songRepository).delete(any(SongModel.class));
        songService.Delete(id);
        verify(songRepository, times(1)).delete(any(SongModel.class));
    }

    @Test
    public void get() {
        var id = new Random().nextLong();
        SongModel returnedModel = getSongModel();
        doReturn(Optional.of(returnedModel)).when(songRepository).findById(eq(id));
        Song s = songService.get(id);
        assertEquals(s.getSongTitle(), returnedModel.getTitle());
        assertEquals(s.getSongAuthor(), returnedModel.getAuthor());
        verify(songRepository, atLeastOnce()).findById(eq(id));
    }

    @Test
    public void getAll() {
        Song song = new Song();
        song.setSongAuthor(faker.lordOfTheRings().character());
        doReturn(new PageImpl<>(List.of(getSongModel()), PageRequest.of(0,1), 1)).when(
                songRepository).findBySongAuthorContaining(anyString(), any(Pageable.class));
        var res = songService.getAll(song, PageRequest.of(0,10));
        assertTrue(res.hasContent());
        verify(songRepository, times(1)).findBySongAuthorContaining(eq(song.getSongAuthor()), eq(PageRequest.of(
                0, 10
        )));
    }


    private SongModel getSongModel() {
        var id = new Random().nextLong();
        SongModel model = new SongModel();
        model.setTitle(faker.ancient().god());
        model.setAuthor(faker.name().fullName());
        model.setDateCreated(new Date());
        model.setId(id);
        return model;
    }

    private SongModel getSongModel(Song song) {
        return new SongMapper(new ModelMapper()).map(song);
    }
}
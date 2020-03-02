package com.innovia.ai.music;

import com.github.javafaker.Faker;
import com.innovia.ai.music.config.ApplicationTestConfig;
import com.innovia.ai.music.datasource.db.model.SongModel;
import com.innovia.ai.music.datasource.db.repository.SongRepository;
import com.innovia.ai.music.dto.Song;
import com.innovia.ai.music.mapper.SongMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {ApplicationTestConfig.class})
public class SongServiceTest {

    @Mock
    SongRepository songRepository;

    @Mock
    SongMapper mapper;

    @InjectMocks
    SongService songService;

    Faker faker;

    @Before
    public void setUp() {
        faker = new Faker(Locale.ENGLISH);
        MockitoAnnotations.initMocks(this);
        doReturn(getSongModel()).when(songRepository).insert(any(SongModel.class));
        doReturn(getSongModel()).when(mapper).map(any(Song.class));

    }

    @Test
    public void create() {
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.shakespeare().asYouLikeItQuote());
        songService.create(song);
        verify(songRepository, times(1)).insert(any(SongModel.class));
        verify(mapper, times(1)).map(any(Song.class));
    }

    @Test(expected = ConstraintViolationException.class)
    public void createSongShouldFailValidation() {
        Song song = new Song();
        songService.create(song);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }


    private SongModel getSongModel() {
        return new SongModel();
    }
}
package service;

import com.github.javafaker.Faker;
import com.innovia.ai.music.common.config.ApplicationConfig;
import com.innovia.ai.music.common.datasource.db.model.SongDbModel;
import com.innovia.ai.music.common.datasource.db.repository.SongRepository;
import com.innovia.ai.music.common.dto.Song;
import com.innovia.ai.music.common.exceptions.NotFoundException;
import com.innovia.ai.music.common.mapper.SongMapper;
import com.innovia.ai.music.common.service.SongService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Mockito.doReturn(getSongModel()).when(songRepository).save(any(SongDbModel.class));
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
        verify(songRepository, times(1)).save(any(SongDbModel.class));
    }


    @Test
    public void update() {
        var id = new Random().nextLong();
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.shakespeare().asYouLikeItQuote());
        song.setId(id);
        doReturn(getSongModel(song)).when(songRepository).save(any(SongDbModel.class));
        doReturn(Optional.of(getSongModel())).when(songRepository).findById(anyLong());
        var newSong = songService.update(song);
        assertEquals(newSong.getId(), song.getId());
        assertEquals(song.getSongTitle(), newSong.getSongTitle());
        verify(songRepository, times(1)).findById(anyLong());
        verify(songRepository, times(1)).save(any(SongDbModel.class));

    }

    @Test(expected = NotFoundException.class)
    public void testUpdateFailsWithNotFoundIfRecordDoesNotExist() {
        var id = new Random().nextLong();
        Song song = new Song();
        song.setSongAuthor(faker.artist().name());
        song.setSongTitle(faker.shakespeare().asYouLikeItQuote());
        song.setId(id);
        doReturn(getSongModel(song)).when(songRepository).save(any(SongDbModel.class));
        doReturn(Optional.empty()).when(songRepository).findById(anyLong());
        var newSong = songService.update(song);
        verify(songRepository, times(1)).findById(anyLong());
        verify(songRepository, never()).save(any(SongDbModel.class));
    }

    @Test
    public void delete() {
        var id = new Random().nextLong();
        doNothing().when(songRepository).deleteById(anyLong());
        songService.delete(id);
        verify(songRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void get() {
        var id = new Random().nextLong();
        SongDbModel returnedModel = getSongModel();
        doReturn(Optional.of(returnedModel)).when(songRepository).findById(eq(id));
        Song s = songService.get(id);
        assertEquals(s.getSongTitle(), returnedModel.getTitle());
        assertEquals(s.getSongAuthor(), returnedModel.getAuthor());
        verify(songRepository, atLeastOnce()).findById(eq(id));
    }

    @Test(expected = NotFoundException.class)
    public void getByIdThrowsNotFoundException() {
        doReturn(Optional.empty()).when(songRepository).findById(anyLong());
        Song song = songService.get(34L);
    }

    private SongDbModel getSongModel() {
        var id = new Random().nextLong();
        SongDbModel model = new SongDbModel();
        model.setTitle(faker.ancient().god());
        model.setAuthor(faker.name().fullName());
        model.setDateCreated(new Date());
        model.setId(id);
        return model;
    }

    private SongDbModel getSongModel(Song song) {
        return new SongMapper(new ModelMapper()).map(song);
    }
}
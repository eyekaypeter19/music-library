package com.innovia.ai.music.controllers;

import com.innovia.ai.music.common.dto.Song;
import com.innovia.ai.music.common.service.SongService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SongControllerTest {

    private static final String SONG_RESOURCE = "/song";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    SongService songService;

    @Before
    public void init() {
        WebApplicationContext context;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getSong() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void create() {
    }

    @Test
    public void delete() {
    }


    private MvcResult createSongTest(String jsonRepForSong) throws Exception {
        doNothing().when(songService).create(any(Song.class));
        ResultActions resultActions = mockMvc.perform(post(SONG_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON).content(loadJsonFromResource(jsonRepForSong)));
        resultActions.andExpect(status().is2xxSuccessful());
        return resultActions.andReturn();

    }

    String loadJsonFromResource(String path) {
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            String json = new String(Files.readAllBytes(file.toPath()));
            json = json.replace("\r","").replace("\n","");
            return json;
        } catch (IOException ignore) {
            return "";
        }
    }
}
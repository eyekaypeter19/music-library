package com.innovia.ai.music.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SongControllerTest {

    private static String SONG_RESOURCE = "/song";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;


    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void createSongSucceeds() throws Exception {
        String content = getCreateJson();
        MvcResult mvcResult = createSongTest(content);
        assertEquals(mvcResult.getResponse().getStatus(), HTTP_CREATED);
    }

    @Test
    public void createSongFailsWithClientError() throws Exception {
        String content = "";
        ResultActions resultActions = mockMvc.perform(post(SONG_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON).content(content));
        resultActions.andExpect(status().isBadRequest());
        assertEquals(resultActions.andReturn().getResponse().getStatus(), HTTP_BAD_REQUEST);
    }

    @Test
    public void getAll() throws Exception {
        ResultActions resultAction = mockMvc.perform(get(SONG_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        resultAction.andExpect(status().isOk());
        var mvcResult = resultAction.andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONArray jsonArray = jsonObject.getJSONArray("content");
        assertTrue(jsonArray.length() > 0);
        assertEquals(mvcResult.getResponse().getStatus(), HTTP_OK);
    }

    @Test
    public void testGetSingleSucceeds() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(SONG_RESOURCE + "/1")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        var mvcResult = resultActions.andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertNotNull(jsonObject.get("title"));
        assertNotNull(jsonObject.getString("author"));
    }

    @Test
    public void updateSucceeds() throws Exception {
        String content = getUpdateJSON();
        ResultActions resultActions = mockMvc.perform(put(SONG_RESOURCE + "/1")
                .contentType(MediaType.APPLICATION_JSON).content(content));
        resultActions.andExpect(status().is2xxSuccessful());
        var result = resultActions.andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        assertEquals(jsonObject.getString("title"), "What Gwan");
        assertEquals(jsonObject.getString("author"), "Mr PETER");
    }


    @Test
    public void deleteSucceeds() throws Exception {
        String id = "1";
        ResultActions resultAction = mockMvc.perform(delete(SONG_RESOURCE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        resultAction.andExpect(status().isNoContent());
        var mvcResult = resultAction.andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HTTP_NO_CONTENT);
    }


    private MvcResult createSongTest(String conent) throws Exception {
        //   doNothing().when(songService).create(any(Song.class));
        ResultActions resultActions = mockMvc.perform(post(SONG_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON).content(conent));
        resultActions.andExpect(status().is2xxSuccessful());
        return resultActions.andReturn();

    }

    String getCreateJson() {
        return "{\n" +
                "  \"author\": \"Mr PEE\",\n" +
                "  \"title\": \"What Gwan in Coming\"\n" +
                "}";
    }

    String getUpdateJSON() {
        return "{\n" +
                "  \"author\": \"Mr PETER\",\n" +
                "  \"title\": \"What Gwan\"\n" +
                "}";
    }
}
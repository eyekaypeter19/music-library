package com.innovia.ai.music.controllers;

import com.innovia.ai.music.common.dto.Song;
import com.innovia.ai.music.common.exceptions.ApiError;
import com.innovia.ai.music.common.service.SongService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Objects;

import static java.net.HttpURLConnection.*;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get A Song By The Id")
    @ApiResponses(value = {
            @ApiResponse(response = Song.class, code = HTTP_OK, message = "Ok"),
            @ApiResponse(response = ApiError.class, code = HTTP_NOT_FOUND, message = "Not Found"),
            @ApiResponse(response = ApiError.class, code = HTTP_INTERNAL_ERROR, message = "Server Error")
    })
    public ResponseEntity<Song> getSong(@ApiParam(name = "id", required = true, example = "123")@PathVariable(name = "id") Long id) {
        var song = songService.get(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get All Songs")
    @ApiResponses(value = {
            @ApiResponse(response = Page.class, code = HTTP_OK, message = "Ok"),
            @ApiResponse(response = ApiError.class, code = HTTP_INTERNAL_ERROR, message = "Server Error")

    })
    public ResponseEntity<Page<Song>> getAll(@RequestParam(value = "title", required = false)String title,
            @RequestParam(value = "page", required = false, defaultValue = "0")int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "15")int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(songService.getAll(title, pageable));
    }

    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an existing song")
    @ApiResponses( {
            @ApiResponse(response = Song.class, code = HTTP_OK, message = "Ok"),
            @ApiResponse(response = ApiError.class, code = HTTP_NOT_FOUND, message = "Not Found"),
            @ApiResponse(response = ApiError.class, code = HTTP_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(response = ApiError.class, code = HTTP_INTERNAL_ERROR, message = "Server Error")
    })
    public ResponseEntity<Song> update(@RequestBody @Valid Song song, @ApiParam(value = "id", required = true, example = "1") @PathVariable(name = "id") Long id) {
        song.setId(id);
        var updatedSong = songService.update(song);
        return ResponseEntity.ok(updatedSong);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new song")
    @ApiResponses( {
            @ApiResponse( code = HTTP_CREATED, message = "Ok"),
            @ApiResponse(response = ApiError.class, code = HTTP_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(response = ApiError.class, code = HTTP_INTERNAL_ERROR, message = "Server Error")
    })
    public void create(@RequestBody @Valid Song song) {
        songService.create(song);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses( {
            @ApiResponse(code = HTTP_NO_CONTENT, message = "Ok"),
            @ApiResponse(response = ApiError.class, code = HTTP_NOT_FOUND, message = "Not Found"),
            @ApiResponse(response = ApiError.class, code = HTTP_INTERNAL_ERROR, message = "Server Error")
    })
    public void delete(@PathVariable("id") Long id) {
        songService.delete(id);
    }
}

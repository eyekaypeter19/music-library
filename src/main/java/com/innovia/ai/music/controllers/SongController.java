package com.innovia.ai.music.controllers;

import com.innovia.ai.music.common.dto.Song;
import com.innovia.ai.music.common.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Song> getSong(@PathVariable(name = "id") Long id) {
        var song = songService.get(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Song>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0")int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "15")int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(songService.getAll(pageable));

    }

    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Song> update(@RequestBody @Valid Song song) {
        var updatedSong = songService.update(song);
        return ResponseEntity.ok(updatedSong);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Song song) {
        songService.create(song);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        songService.delete(id);
    }
}

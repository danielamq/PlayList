package com.playlist.api.controller;

import com.playlist.api.business.PlayListBusiness;
import com.playlist.api.entity.PlayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlayListController {



    @Autowired
    private PlayListBusiness business;

    @PostMapping
    public ResponseEntity<PlayList> addPlayList(@RequestBody PlayList playList) {
        return business.addPlayList(playList);
    }

    @GetMapping
    public ResponseEntity<List<PlayList>> getAllPlayLists() {
       return business.getAllPlayLists();
    }

    @GetMapping("/{listName}")
    public ResponseEntity<PlayList> getPlayListByName(@PathVariable String listName) {
        return business.getPlayListByName(listName);
    }


    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deletePlayList(@PathVariable String listName) {
        return business.deletePlayList(listName);
    }


}

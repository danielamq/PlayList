package com.playlist.api.business;

import com.playlist.api.entity.PlayList;
import com.playlist.api.entity.Song;
import com.playlist.api.repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PlayListBusiness {
    @Autowired
    private PlayListRepository playListRepository;

    public ResponseEntity<PlayList> addPlayList(@RequestBody PlayList playList) {
        if (playList.getName() == null || playList.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (playListRepository.findByName(playList.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        for (Song song : playList.getSongs()) {
            song.setPlaylist(playList);
        }

        PlayList savedPlayList = playListRepository.save(playList);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPlayList.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedPlayList);
    }


    public ResponseEntity<List<PlayList>> getAllPlayLists() {
        List<PlayList> playLists = playListRepository.findAll();
        return ResponseEntity.ok(playLists);
    }


    public ResponseEntity<PlayList> getPlayListByName(@PathVariable String listName) {
        Optional<PlayList> playListOptional = playListRepository.findByName(listName);

        if (playListOptional.isPresent()) {
            return ResponseEntity.ok(playListOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Void> deletePlayList(@PathVariable String listName) {
        Optional<PlayList> playListOptional = playListRepository.findByName(listName);

        if (playListOptional.isPresent()) {
            playListRepository.delete(playListOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

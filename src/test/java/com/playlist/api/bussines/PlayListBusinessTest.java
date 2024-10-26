package com.playlist.api.business;

import com.playlist.api.entity.PlayList;
import com.playlist.api.entity.Song;
import com.playlist.api.repository.PlayListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayListBusinessTest {

    @InjectMocks
    private PlayListBusiness playListBusiness;

    @Mock
    private PlayListRepository playListRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAddPlayList_BadRequest_EmptyName() {
        PlayList playList = new PlayList();
        playList.setName(""); // Nombre vacío

        ResponseEntity<PlayList> response = playListBusiness.addPlayList(playList);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAddPlayList_BadRequest_NameExists() {
        PlayList playList = new PlayList();
        playList.setName("Mi Lista de Reproducción 1");

        when(playListRepository.findByName(playList.getName())).thenReturn(Optional.of(new PlayList()));

        ResponseEntity<PlayList> response = playListBusiness.addPlayList(playList);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetAllPlayLists() {
        PlayList playList1 = new PlayList();
        playList1.setName("Lista 1");
        PlayList playList2 = new PlayList();
        playList2.setName("Lista 2");
        List<PlayList> playLists = Arrays.asList(playList1, playList2);

        when(playListRepository.findAll()).thenReturn(playLists);

        ResponseEntity<List<PlayList>> response = playListBusiness.getAllPlayLists();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetPlayListByName_Found() {
        String listName = "Mi Lista de Reproducción 1";
        PlayList playList = new PlayList();
        playList.setName(listName);

        when(playListRepository.findByName(listName)).thenReturn(Optional.of(playList));

        ResponseEntity<PlayList> response = playListBusiness.getPlayListByName(listName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listName, response.getBody().getName());
    }

    @Test
    void testGetPlayListByName_NotFound() {
        String listName = "No Existe";

        when(playListRepository.findByName(listName)).thenReturn(Optional.empty());

        ResponseEntity<PlayList> response = playListBusiness.getPlayListByName(listName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeletePlayList_Found() {
        String listName = "Mi Lista de Reproducción 1";
        PlayList playList = new PlayList();
        playList.setName(listName);

        when(playListRepository.findByName(listName)).thenReturn(Optional.of(playList));

        ResponseEntity<Void> response = playListBusiness.deletePlayList(listName);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(playListRepository, times(1)).delete(playList);
    }

    @Test
    void testDeletePlayList_NotFound() {
        String listName = "No Existe";

        when(playListRepository.findByName(listName)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = playListBusiness.deletePlayList(listName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(playListRepository, never()).delete(any());
    }
}

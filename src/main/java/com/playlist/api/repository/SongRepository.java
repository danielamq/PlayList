package com.playlist.api.repository;

import com.playlist.api.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long>  {

}

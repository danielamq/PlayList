package com.playlist.api.repository;

import com.playlist.api.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayListRepository extends JpaRepository <PlayList, Long>  {
    Optional<PlayList> findByName(String name);
}

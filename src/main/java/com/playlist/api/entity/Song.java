package com.playlist.api.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String artist;
    private String album;

    @Column(name = "\"year\"")
    private String year;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    @JsonIgnore
    private PlayList playlist;
}


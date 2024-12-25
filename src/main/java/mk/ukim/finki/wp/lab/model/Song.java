package mk.ukim.finki.wp.lab.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String trackId;
    String title;
    String genre;
    int releaseYear;
    @ManyToOne
    Album album;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Artist> artists;

    public Song(Long id, String trackId, String title, String genre, int releaseYear, Album a, List<Artist> artists) {
        this.id = id;
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.album = a;
        this.artists = artists;
    }

    public Song(String trackId, String title, String genre, int releaseYear, Album album, List<Artist> artists) {
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.album = album;
        this.artists = artists;
    }
}

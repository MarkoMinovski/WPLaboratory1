package mk.ukim.finki.wp.lab.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Song {

    Long id;
    String trackId;
    String title;
    String genre;
    int releaseYear;
    Album album;
    List<Artist> artists;

    public Song(Long id, String trackId, String title, String genre, int releaseYear, Album a) {
        this.id = id;
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artists = new ArrayList<>();
        this.album = a;
    }

}

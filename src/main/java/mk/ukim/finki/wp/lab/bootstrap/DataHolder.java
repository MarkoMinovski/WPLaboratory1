package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Artist> artists;
    public static List<Song> songs;

    @PostConstruct
    public void init() {
        artists = new ArrayList<>();
        songs = new ArrayList<>();

        songs.add(new Song("LCR", "Like Crazy", "K-Pop", 2023));
        songs.add(new Song("TYN", "Thank U, Next", "Pop", 2019));
        songs.add(new Song("DYN", "Dynamite", "K-Pop", 2020));
        songs.add(new Song("CCB", "Chk Chk Boom", "Hip-Hop", 2024));
        songs.add(new Song("DUA", "Duality", "Nu-Metal", 2004));

        artists.add(new Artist(1L, "Megan", "Thee Stallion", "Some bio 1"));
        artists.add(new Artist(2L, "Taylor", "Swift", "Some bio 2"));
        artists.add(new Artist(3L, "Ariana", "Grande", "Some bio 3"));
        artists.add(new Artist(4L, "Noah", "Sebastian", "Some bio 4"));
        artists.add(new Artist(5L, "Park", "Ji-min", "Some bio 5"));

    }

}

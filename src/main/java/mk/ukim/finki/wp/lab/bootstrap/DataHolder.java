package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataHolder {
    public static List<Artist> artists;
    public static List<Song> songs;
    public static List<Album> albums;

    @PostConstruct
    public void init() {
        Random rand = new Random();
        artists = new ArrayList<>();
        songs = new ArrayList<>();
        albums = new ArrayList<>();

        albums.add(new Album(rand.nextLong(10000000),"Face", "K-Pop", "2023"));
        albums.add(new Album(rand.nextLong(10000000),"Thank U, Next", "Pop", "2019"));
        albums.add(new Album(rand.nextLong(10000000),"BE", "K-Pop", "2020"));
        albums.add(new Album(rand.nextLong(10000000),"GIANT", "K-Pop", "2024"));
        albums.add(new Album(rand.nextLong(10000000),"Vol. 3: (The Subliminal Verses)", "Nu-Metal", "2004"));

        songs.add(new Song(rand.nextLong(10000000),"LCR", "Like Crazy", "K-Pop", 2023, albums.get(0)));
        songs.add(new Song(rand.nextLong(10000000),"TYN", "Thank U, Next", "Pop", 2019, albums.get(1)));
        songs.add(new Song(rand.nextLong(10000000),"DYN", "Dynamite", "K-Pop", 2020, albums.get(2)));
        songs.add(new Song(rand.nextLong(10000000),"CCB", "Chk Chk Boom", "Hip-Hop", 2024, albums.get(3)));
        songs.add(new Song(rand.nextLong(10000000),"DUA", "Duality", "Nu-Metal", 2004, albums.get(4)));

        artists.add(new Artist(1L, "Megan", "Thee Stallion", "Some bio 1"));
        artists.add(new Artist(2L, "Taylor", "Swift", "Some bio 2"));
        artists.add(new Artist(3L, "Ariana", "Grande", "Some bio 3"));
        artists.add(new Artist(4L, "Noah", "Sebastian", "Some bio 4"));
        artists.add(new Artist(5L, "Park", "Ji-min", "Some bio 5"));


    }

}

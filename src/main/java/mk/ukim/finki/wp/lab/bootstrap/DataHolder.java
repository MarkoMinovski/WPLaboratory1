package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Artist> artists;
    public static List<Song> songs;
    public static List<Album> albums;

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public DataHolder(AlbumRepository albumRepository, ArtistRepository artistRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        artists = new ArrayList<>();
        songs = new ArrayList<>();
        albums = new ArrayList<>();


        if (this.albumRepository.count() == 0) {
            albums.add(new Album("Face", "K-Pop", "2023", new ArrayList<>()));
            albums.add(new Album("Thank U, Next", "Pop", "2019", new ArrayList<>()));
            albums.add(new Album("BE", "K-Pop", "2020", new ArrayList<>()));
            albums.add(new Album("GIANT", "K-Pop", "2024", new ArrayList<>()));
            albums.add(new Album("Vol. 3: (The Subliminal Verses)", "Nu-Metal", "2004",
                    new ArrayList<>()));
            albumRepository.saveAll(albums);
        }

        if (this.artistRepository.count() == 0) {
            artists.add(new Artist("Megan", "Thee Stallion", "Some bio 1"));
            artists.add(new Artist("Taylor", "Swift", "Some bio 2"));
            artists.add(new Artist("Ariana", "Grande", "Some bio 3"));
            artists.add(new Artist("Noah", "Sebastian", "Some bio 4"));
            artists.add(new Artist("Park", "Ji-min", "Some bio 5"));
            artistRepository.saveAll(artists);
        }

        if (this.songRepository.count() == 0) {
            songs.add(new Song("LCR", "Like Crazy", "K-Pop", 2023, albums.get(0), new ArrayList<>()));
            songs.add(new Song("TYN", "Thank U, Next", "Pop", 2019, albums.get(1), new ArrayList<>()));
            songs.add(new Song("DYN", "Dynamite", "K-Pop", 2020, albums.get(2), new ArrayList<>()));
            songs.add(new Song("CCB", "Chk Chk Boom", "Hip-Hop", 2024, albums.get(3), new ArrayList<>()));
            songs.add(new Song("DUA", "Duality", "Nu-Metal", 2004, albums.get(4), new ArrayList<>()));
            songRepository.saveAll(songs);

            for (Song song : songs) {
                Album a = albumRepository.findById(song.getAlbum().getId()).get();
                a.getSongs().add(song);
                albumRepository.save(a);
            }

        }

    }

}

package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongRepository {
    public List<Song> findAll() {
        return DataHolder.songs;
    }

    public Song findByTrackId(String trackId) {
        return DataHolder.songs.stream().filter(song -> song.getTrackId().equals(trackId)).findFirst().orElse(null);
    }

    public Song findByLongId(Long id) {
        return DataHolder.songs.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public Artist addArtistToSong(Artist artist, Song song) {
        if (DataHolder.songs.stream().anyMatch(s -> s.equals(song))) {
            Song tmp_song = DataHolder.songs.stream().filter(s -> s.equals(song)).findFirst().orElse(null);
            // "orElse clause unreachable"
            assert tmp_song != null;

            // To avoid duplicates!

            if (tmp_song.getArtists().stream().anyMatch(songArtist -> songArtist.equals(artist))) {
                return null;
            } else {
                tmp_song.getArtists().add(artist);
            }

            return artist;
        }

        return null;
    }
}

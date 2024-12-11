package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository sp, ArtistRepository ap) {
        this.songRepository = sp;
        this.artistRepository = ap;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        List<Artist> AllCurrentArtists = artistRepository.findAll();

        if (AllCurrentArtists.stream().noneMatch(a -> a.equals(artist))) {
            artistRepository.save(artist);
        }

        Optional<Song> s = songRepository.findById(song.getId());

        s.ifPresent(value -> value.getArtists().add(artist));
        s.ifPresent(songRepository::save);


        return artist;
    }

    public void saveOrUpdate(Song s) {
        songRepository.save(s);
    }

    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }

    @Override
    public Song findByLongId(Long id) {
        return songRepository.findSongById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        songRepository.deleteById(id);
    }

}

package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArtistService {
    List<Artist> listArtists();
    Artist ArtistfindById(Long id);
    public Page<Artist> findAll(String firstName, String lastName);
}

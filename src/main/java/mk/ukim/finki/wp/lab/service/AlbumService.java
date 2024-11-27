package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    public List<Album> findAll();
    public Optional<Album> findById(Long id);
}

package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.service.specifications.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import mk.ukim.finki.wp.lab.service.ArtistService;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository ap) {
        this.artistRepository = ap;
    }

    @Override
    public List<Artist> listArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist ArtistfindById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Artist> findAll(String firstName, String lastName) {
        Specification<Artist> query = Specification
                .where(SpecificationBuilder.filterContainsText(Artist.class, "firstName", firstName))
                .or(SpecificationBuilder.filterContainsText(Artist.class, "lastName", lastName));

        return this.artistRepository.findAll(query,
                PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "lastName")));
    }


    public void save(Artist a) {
        artistRepository.save(a);
    }
}

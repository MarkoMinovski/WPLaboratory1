package mk.ukim.finki.wp.lab.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.impl.ArtistServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.SongServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistServiceImpl artistService;
    private final SongServiceImpl songService;

    public ArtistController(ArtistServiceImpl artistService, SongServiceImpl songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @PostMapping("/choose")
    public String chooseArtistForm(HttpServletRequest req, Model model) {
        String longID = req.getParameter("id");
        String trackID = songService.findByLongId(Long.parseLong(longID)).getTrackId();

        model.addAttribute("trackId", trackID);
        model.addAttribute("artistList", artistService.listArtists());

        return "artistsList";
    }

    @GetMapping("/all")
    public String seeArtists(Model model) {
        String trackID = "N/A";

        model.addAttribute("trackId", trackID);
        model.addAttribute("artistList", artistService.listArtists());

        return "artistsList";
    }

    @PostMapping("/songDetails")
    public String openSongDetailsForm(HttpServletRequest req, Model model) {
        String trackID = req.getParameter("trackId");
        String artistId = req.getParameter("artistId");

        Song s = songService.findByTrackId(trackID);
        Artist a = artistService.ArtistfindById(Long.parseLong(artistId));

        songService.addArtistToSong(a, s);

        model.addAttribute("song", s);

        return "songDetails";
    }

    @PostMapping("/add")
    public String acceptAddingNewArtistPostRequest(HttpServletRequest req, Model model) {
        String userInputFirstName = req.getParameter("firstName");
        String userInputLastName = req.getParameter("lastName");
        String userInputBio = req.getParameter("bio");
        String hiddenInputTrackID = req.getParameter("trackId");

        Artist a = new Artist(userInputFirstName, userInputLastName, userInputBio);

        artistService.save(a);

        model.addAttribute("trackId", hiddenInputTrackID);
        model.addAttribute("artistList", artistService.listArtists());

        return "artistsList";
    }

    @PostMapping("/search")
    public String filterArtistsPostRequest(HttpServletRequest req, Model model) {
        String userInputFirstName = req.getParameter("firstName-search");
        String userInputLastName = req.getParameter("lastName-search");

        String hiddenInputTrackID = req.getParameter("trackId");

        Page<Artist> result = artistService.findAll(userInputFirstName, userInputLastName);
        List<Artist> resultAsList = result.stream().toList();

        model.addAttribute("trackId", hiddenInputTrackID);
        model.addAttribute("artistList", resultAsList);

        return "artistsList";
    }
}

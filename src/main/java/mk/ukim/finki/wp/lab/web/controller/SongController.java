package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.impl.AlbumServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.SongServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongServiceImpl songService;
    private final AlbumServiceImpl albumService;

    public SongController(SongServiceImpl songService, AlbumServiceImpl albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }


    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("songList", songService.listSongs());
        model.addAttribute("albumsList", albumService.findAll());
        return "listSongs";
    }


    @PostMapping("/delete/{songId}")
    public String deleteSong(@PathVariable Long songId, Model model) {
        Song target = songService.findByLongId(songId);
        songService.delete(songId);

        model.addAttribute("songList", songService.listSongs());
        model.addAttribute("albumsList", albumService.findAll());

        return "listSongs";
    }

    @PostMapping("/add")
    public String openEditFormToAddNewSong(HttpServletRequest req, Model model) {
        // Дека не може null
        model.addAttribute("song", new Song());
        model.addAttribute("albumsList", albumService.findAll());

        return "add-song";
    }


    // Return add-song.html form
    @PostMapping("/edit/{*}")
    public String openEditFormWithExistingSong(HttpServletRequest req, Model model) {
        String targetSongParameter = req.getParameter("id");
        Song targetSong = songService.findByLongId(Long.parseLong(targetSongParameter));

        model.addAttribute("song", targetSong);
        model.addAttribute("albumsList", albumService.findAll());
        model.addAttribute("artistList", targetSong.getArtists());

        return "add-song";
    }

    @PostMapping("/saveAlbum")
    public String acceptNewAlbumPostRequest(HttpServletRequest req, Model model) {
        String userInputName = req.getParameter("name");
        String userInputGenre = req.getParameter("genre");
        String userInputReleaseYear = req.getParameter("releaseYear");

        albumService.save(new Album(userInputName, userInputGenre, userInputReleaseYear, new ArrayList<>()));

        model.addAttribute("songList", songService.listSongs());
        model.addAttribute("albumsList", albumService.findAll());

        return "listSongs";
    }

    // Save changes from the add-song.html form
    @PostMapping("/save")
    public String saveChangesFromEditForm(HttpServletRequest req, Model model) {
        String longIDParameter = req.getParameter("id");
        String userInputAlbum = req.getParameter("new-song-album");

        long longID;
        if (!longIDParameter.isEmpty()) {
            longID = Long.parseLong(req.getParameter("id"));
        } else {
            longID = 0L;
        }

        // if longId = 0L, then this is a new song

        Optional<Album> selected = albumService.findById(Long.parseLong(userInputAlbum));
        String userInputTitle = req.getParameter("title");
        String userInputTrackID = req.getParameter("trackId");
        String userInputGenre = req.getParameter("genre");
        String userInputReleaseYear = req.getParameter("releaseYear");

        assert selected.isPresent();

        Song s;

        if (longID == 0L) {
            s = new Song(userInputTrackID, userInputTitle,
                    userInputGenre,Integer.parseInt(userInputReleaseYear),
                    selected.get(), new ArrayList<>());
        } else {
            s = new Song(longID, userInputTrackID, userInputTitle, userInputGenre, Integer.parseInt(userInputReleaseYear),
                    selected.get(), songService.findByLongId(longID).getArtists());
        }

       songService.saveOrUpdate(s);

        model.addAttribute("songList", songService.listSongs());
        model.addAttribute("albumsList", albumService.findAll());

        return "listSongs";
    }


}

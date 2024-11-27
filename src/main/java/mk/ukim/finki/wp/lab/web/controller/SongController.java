package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.impl.AlbumServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.SongServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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
        songService.listSongs().remove(target);

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

    // Save changes from the add-song.html form
    @PostMapping("/save")
    public String saveChangesFromEditForm(HttpServletRequest req, Model model) {
        String longIDParameter = req.getParameter("id");

        long longID;
        if (!longIDParameter.isEmpty()) {
            longID = Long.parseLong(req.getParameter("id"));
        } else {
            longID = 0L;
        }

        String userInputTitle = req.getParameter("title");
        String userInputTrackID = req.getParameter("trackId");
        String userInputGenre = req.getParameter("genre");
        String userInputReleaseYear = req.getParameter("releaseYear");
        String userInputAlbum = req.getParameter("new-song-album");

        Optional<Album> selected = albumService.findById(Long.parseLong(userInputAlbum));

        if (selected.isPresent()) {
            Random rand = new Random();
            Song newSong = new Song(rand.nextLong(10000000), userInputTrackID, userInputTitle,
                    userInputGenre, Integer.parseInt(userInputReleaseYear), selected.get());

            Song existingElement = songService.findByLongId(longID);

            if (existingElement != null) {
                songService.listSongs().remove(existingElement);
            }

            songService.listSongs().add(newSong);
        }

        model.addAttribute("songList", songService.listSongs());
        model.addAttribute("albumsList", albumService.findAll());

        return "listSongs";
    }


}
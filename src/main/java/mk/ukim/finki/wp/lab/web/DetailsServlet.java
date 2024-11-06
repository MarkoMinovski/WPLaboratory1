package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.impl.ArtistServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.SongServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name="SongDetailsServlet", urlPatterns = "/songs/songDetails")
public class DetailsServlet extends HttpServlet {
    private final ArtistServiceImpl artistService;
    private final SpringTemplateEngine springTemplateEngine;
    private final SongServiceImpl songService;

    public DetailsServlet(SpringTemplateEngine springTemplateEngine, SongServiceImpl songService, ArtistServiceImpl artistService) {
        this.springTemplateEngine = springTemplateEngine;
        this.songService = songService;
        this.artistService = artistService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(this.getServletContext())
                .buildExchange(req, resp);

        Song defaultSong = songService.listSongs().getFirst();

        WebContext webContext = new WebContext(webExchange);

        webContext.setVariable("song", defaultSong);

        springTemplateEngine.process("songDetails.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Artist requestedArtist = artistService.ArtistfindById(Long.parseLong(req.getParameter("artistId")));
        Song requestedTrack = songService.findByTrackId(req.getParameter("trackId"));

        if (requestedArtist != null) {
            if (requestedTrack != null) {
                requestedTrack.getArtists().add(requestedArtist);
            } else {
                requestedTrack = songService.listSongs().getFirst();
                requestedTrack.getArtists().add(requestedArtist);
            }
        } else {
            requestedArtist = artistService.listArtists().getFirst();
            if (requestedTrack == null) {
                requestedTrack = songService.listSongs().getFirst();
                requestedTrack.getArtists().add(requestedArtist);
            } else {
                requestedTrack.getArtists().add(requestedArtist);
            }
        }

        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(this.getServletContext())
                .buildExchange(req, resp);

        WebContext webContext = new WebContext(webExchange);
        webContext.setVariable("song", requestedTrack);

        springTemplateEngine.process("songDetails.html", webContext, resp.getWriter());
    }
}

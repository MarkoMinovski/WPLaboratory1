package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.impl.ArtistServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "ArtistServlet", urlPatterns = "/servlet/artist/artists")
public class ArtistServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private ArtistServiceImpl artistService;

    public ArtistServlet(SpringTemplateEngine springTemplateEngine, ArtistServiceImpl artistService) {
        this.springTemplateEngine = springTemplateEngine;
        this.artistService = artistService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(this.getServletContext())
                .buildExchange(req, resp);

        WebContext webContext = new WebContext(webExchange);

        webContext.setVariable("artistList", artistService.listArtists());

        springTemplateEngine.process("artistsList.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext webContext = new WebContext(webExchange);


        String trackId = req.getParameter("id");

        if (trackId == null) {
            trackId = "N/A";
        }

        webContext.setVariable("trackId", trackId);
        webContext.setVariable("artistList", artistService.listArtists());
        springTemplateEngine.process("artistsList.html", webContext, resp.getWriter());
    }
}

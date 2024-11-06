package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.impl.SongServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "SongListServlet", urlPatterns = "/songs/songsList")
public class SongListServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final SongServiceImpl songServiceImpl;

    public SongListServlet(SpringTemplateEngine springTemplateEngine, SongServiceImpl songServiceImpl) {
        this.springTemplateEngine = springTemplateEngine;
        this.songServiceImpl = songServiceImpl;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext webContext = new WebContext(webExchange);

        webContext.setVariable("songList", songServiceImpl.listSongs());

        springTemplateEngine.process("listSongs.html", webContext, response.getWriter());
    }


}

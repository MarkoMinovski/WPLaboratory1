package mk.ukim.finki.wp.lab.web.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    @GetMapping("/")
    public String redirectDefaultToSongsPage() {
        return "redirect:/songs";
    }

}

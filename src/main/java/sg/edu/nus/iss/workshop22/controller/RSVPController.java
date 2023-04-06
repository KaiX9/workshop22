package sg.edu.nus.iss.workshop22.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.workshop22.model.RSVP;

@Controller
@RequestMapping(path={"/", "/api/rsvp"})
public class RSVPController {
    
    @GetMapping
    public String insertUpdateRSVP(Model m, @ModelAttribute RSVP rsvp) {
        
        m.addAttribute("rsvp", rsvp);
        return "index";
    }
}

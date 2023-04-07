package sg.edu.nus.iss.workshop22.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.workshop22.model.RSVP;

@Controller
@RequestMapping
public class RSVPController {
    
    @GetMapping(path={"/", "/api/rsvp"})
    public String insertUpdateRSVP(Model m, @ModelAttribute RSVP rsvp) {
        
        m.addAttribute("rsvp", rsvp);
        return "index";
    }

    @GetMapping(path="/api/rsvp/put")
    public String updateRSVPByEmail(Model m, @ModelAttribute RSVP rsvp) {
        
        m.addAttribute("rsvp", rsvp);
        return "put";
    }
}

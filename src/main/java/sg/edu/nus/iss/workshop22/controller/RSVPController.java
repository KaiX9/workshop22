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
    
    // Insert/update RSVP via html form
    @GetMapping(path={"/", "/api/rsvp"})
    public String insertUpdateRSVP(Model m, @ModelAttribute RSVP rsvp) {
        
        m.addAttribute("rsvp", rsvp);
        return "index";
    }

    // Update RSVP by email via html form
    @GetMapping(path="/api/rsvp/put")
    public String updateRSVPByEmail(Model m, @ModelAttribute RSVP rsvp) {
        
        m.addAttribute("rsvp", rsvp);
        return "put";
    }

    // Get RSVP by name via html form
    @GetMapping(path="/api/rsvp/name")
    public String getRSVPByName(Model m, @ModelAttribute String name) {

        m.addAttribute("rsvp", new RSVP());
        return "getbyname";
    }
}

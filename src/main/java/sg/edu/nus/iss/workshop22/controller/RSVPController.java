package sg.edu.nus.iss.workshop22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.workshop22.model.RSVP;
import sg.edu.nus.iss.workshop22.repository.RSVPRepository;

@Controller
@RequestMapping
public class RSVPController {
    
    @Autowired
    private RSVPRepository rsvpRepo;

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
    public String getRSVPByName(Model m, @ModelAttribute RSVP rsvp) {

        m.addAttribute("rsvp", rsvp);
        return "getbyname";
    }

    // Get details by name via html form, displaying on same page
    @GetMapping(path="/name")
    public String getRSVPByNameResult(Model m, @ModelAttribute RSVP rsvp
        , @RequestParam String name) {
        
        List<RSVP> rsvpList = rsvpRepo.getRSVPByName(name);
        System.out.println(rsvpList);

        // check if list is null, then display the error
        if (rsvpList == null) {
            m.addAttribute("display", false);
            m.addAttribute("error", true);
        } else {
        m.addAttribute("display", true);
        m.addAttribute("error", false);
        m.addAttribute("rsvpList", rsvpList);
        }

        return "getbyname";
    }
}

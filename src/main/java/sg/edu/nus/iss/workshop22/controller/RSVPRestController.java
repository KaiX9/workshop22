package sg.edu.nus.iss.workshop22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.workshop22.model.RSVP;
import sg.edu.nus.iss.workshop22.repository.RSVPRepository;

@RestController
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class RSVPRestController {

    @Autowired
    private RSVPRepository RSVPRepo;

    @GetMapping(path="/rsvps")
    public ResponseEntity<String> getAllRSVP() {

        List<RSVP> rsvps = RSVPRepo.getAllRSVP();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (RSVP r : rsvps) {
            arrBuilder.add(r.toJSON());
        }

        JsonArray result = arrBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
    
    @GetMapping(path="/rsvp")
    public ResponseEntity<String> getRSVPByName(@RequestParam String name) {

        List<RSVP> rsvp = RSVPRepo.getRSVPByName(name);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (RSVP r : rsvp) {
            arrBuilder.add(r.toJSON());
        }

        JsonArray result = arrBuilder.build();

        if (rsvp.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'error message' : " + HttpStatus.NOT_FOUND + "}");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());

    }

    @PostMapping(path="/rsvp")
    public ResponseEntity<String> insertUpdateRSVP(@RequestBody String json) throws Exception {

        RSVP rsvp = new RSVP();
        rsvp = RSVP.create(json); // converting json to java object
        RSVP result = RSVPRepo.createRSVP(rsvp);

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("rsvpID", result.getId())
            .build();

        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObj.toString());
    }

    @PostMapping(path="/rsvp/form")
    public ResponseEntity<String> insertUpdateRSVP(@ModelAttribute RSVP rsvp
        , @RequestParam String confirmation_date) {

        rsvp.setConfirmationDate(RSVP.getDateTimeFromForm(confirmation_date));
        RSVP result = RSVPRepo.createRSVP(rsvp);

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("rsvpID", result.getId())
            .build();

        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObj.toString());
    }

    @PutMapping(path="/rsvp/{email}")
    public ResponseEntity<String> updateExistingRSVP(@PathVariable String email
        , @RequestBody String json) throws Exception {

        RSVP rsvp = RSVP.create(json);
        // RSVP existingRSVP = RSVPRepo.getRSVPByEmail(email);
        boolean result = RSVPRepo.updateRSVPforPut(rsvp, email);
        
        if (!result) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{'error message' : " + HttpStatus.NOT_FOUND + "}");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{'success' : updated for " + email + "}");
    }

    @PutMapping(path="/rsvp/form/{emailInput}")
    public ResponseEntity<String> updateExistingRSVPForm(@ModelAttribute RSVP rsvp
        ,@PathVariable String emailInput, @RequestParam String confirmation_date) {

        rsvp.setConfirmationDate(RSVP.getDateTimeFromForm(confirmation_date));
        boolean result = RSVPRepo.updateRSVPforPut(rsvp, rsvp.getEmailInput());

        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'error message' : " + rsvp.getEmailInput() + " not found}");
            }
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'success' : updated for " + rsvp.getEmailInput() + "}");
    }

    @GetMapping(path="/rsvps/count")
    public ResponseEntity<String> getTotalRSVPCounts() {
        
        JsonObject resp;
        Long totalRSVPs = RSVPRepo.getTotalRSVPCount();

        resp = Json.createObjectBuilder()
                .add("total_count", totalRSVPs)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(resp.toString());
    }

}

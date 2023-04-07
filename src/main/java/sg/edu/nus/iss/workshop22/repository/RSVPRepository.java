package sg.edu.nus.iss.workshop22.repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop22.model.RSVP;
import static sg.edu.nus.iss.workshop22.repository.DBQueries.*;

@Repository
public class RSVPRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<RSVP> getAllRSVP() {

        List<RSVP> rsvp = new ArrayList<RSVP>();

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ALL_RSVP);

        while (rs.next()) {
            rsvp.add(RSVP.create(rs));
        }

        return rsvp;
    }

    public List<RSVP> getRSVPByName(String name) {

        String correctName = "%" + name + "%";
        List<RSVP> rsvp = new ArrayList<RSVP>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_RSVP_BY_NAME, correctName);

        while (rs.next()) {
            rsvp.add(RSVP.create(rs));
        }
        return rsvp;
    }

    public RSVP getRSVPByEmail(String email) {
        
        List<RSVP> rsvpList = new ArrayList<RSVP>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_RSVP_BY_EMAIL, email);

        while (rs.next()) {
            rsvpList.add(RSVP.create(rs));
        }

        if (rsvpList.isEmpty()) {
            return null;
        }
        return rsvpList.get(0);
    }

    public RSVP createRSVP(RSVP rsvp) {
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        RSVP existingRSVP = getRSVPByEmail(rsvp.getEmail());

        if (Objects.isNull(existingRSVP)) {
            // insert record

            jdbcTemplate.update(conn -> {
                PreparedStatement statement = conn.prepareStatement
                (INSERT_NEW_RSVP, Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, rsvp.getName());
                statement.setString(2, rsvp.getEmail());
                statement.setString(3, rsvp.getPhone());
                statement.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate()
                    .toDateTime().getMillis()));
                statement.setString(5, rsvp.getComments());
                return statement;
            }, keyHolder);

            BigInteger primaryKey = (BigInteger) keyHolder.getKey();
            rsvp.setId(primaryKey.intValue());

        } else {
            // update existing record
            existingRSVP.setName(rsvp.getName());
            existingRSVP.setPhone(rsvp.getPhone());
            existingRSVP.setConfirmationDate(rsvp.getConfirmationDate());
            existingRSVP.setComments(rsvp.getComments());

            boolean isUpdated = updateRSVP(existingRSVP);

            if (isUpdated) {
                rsvp.setId(existingRSVP.getId());
            }
        }
    
    return rsvp;
    }

    public boolean updateRSVP(RSVP existingRSVP) {
        
        return jdbcTemplate.update(UPDATE_RSVP_BY_EMAIL_POST, 
                existingRSVP.getName(),     
                existingRSVP.getPhone(), 
                new Timestamp(existingRSVP.getConfirmationDate().toDateTime().getMillis()), 
                existingRSVP.getComments(),
                existingRSVP.getEmail()) > 0;
    }

    public boolean updateRSVPforPut(RSVP rsvp, String emailInput) {

        return jdbcTemplate.update(UPDATE_RSVP_BY_EMAIL_PUT, 
                rsvp.getName(),     
                rsvp.getPhone(), 
                new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()), 
                rsvp.getComments(),
                rsvp.getEmail(),
                emailInput) > 0;
    }

    public Long getTotalRSVPCount() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_RSVPS_COUNT);

        return (Long) rows.get(0).get("total_count");
    }
}

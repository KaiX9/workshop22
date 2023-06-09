package sg.edu.nus.iss.workshop22.repository;

public class DBQueries {
    
    public static final String SELECT_ALL_RSVP
         = "select id, name, email, phone, confirmation_date, comments from rsvp";

    public static final String SELECT_RSVP_BY_NAME 
        = "select id, name, email, phone, confirmation_date, comments from rsvp where name like ?";

    public static final String SELECT_RSVP_BY_EMAIL
        = "select * from rsvp where email = ?";
    
    public static final String INSERT_NEW_RSVP
        = "INSERT INTO rsvp (name, email, phone, confirmation_date, comments) VALUES(?, ?, ?, ?, ?)";

    public static final String UPDATE_RSVP_BY_EMAIL_POST
        = "update rsvp set name = ?, phone = ?, confirmation_date = ?, comments = ? where email = ?";

    public static final String UPDATE_RSVP_BY_EMAIL_PUT
        = "update rsvp set name = ?, phone = ?, confirmation_date = ?, comments = ?, email = ? where email = ?";

    public static final String SELECT_RSVPS_COUNT
        = "select count(*) as total_count from rsvp";
}

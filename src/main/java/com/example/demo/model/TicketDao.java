package com.example.demo.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TicketDao {

    private final JdbcTemplate jdbcTemplate;

    public TicketDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all Tickets
    public List<TicketDTO> getAllTickets() {
        String sql = "SELECT * FROM ticket";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TicketDTO(
                rs.getInt("ticketId"),
                rs.getInt("preis"),
                rs.getString("sitzplatz"),
                rs.getInt("vId"),
                rs.getString("email")
        ));
    }

    // Filter Tickets by email
    public List<TicketDTO> getTicketsByEmail(String email) {
        String sql = "SELECT * FROM ticket WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, (rs, rowNum) -> new TicketDTO(
                rs.getInt("ticketId"),
                rs.getInt("preis"),
                rs.getString("sitzplatz"),
                rs.getInt("vId"),
                rs.getString("email")
        ));
    }
}


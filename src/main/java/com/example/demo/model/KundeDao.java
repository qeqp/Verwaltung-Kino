package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KundeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveKunde(KundeDTO kunde) {
        String sql = "INSERT INTO kunde (email, passwort, vorname, nachname, geburtsdatum, strasse, hausnummer, stadt, plz) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, kunde.email(), kunde.passwort(), kunde.vorname(), kunde.nachname(), kunde.geburtsdatum(), kunde.strasse(), kunde.hausnummer(), kunde.stadt(), kunde.plz());
    }

    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM kunde WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public KundeDTO getKundeByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM kunde WHERE email = ?",
                new Object[]{email},
                (rs, rowNum) -> new KundeDTO(
                        rs.getString("email"),
                        rs.getString("passwort"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getDate("geburtsdatum").toLocalDate(),
                        rs.getString("strasse"),
                        rs.getString("hausnummer"),
                        rs.getString("stadt"),
                        rs.getInt("plz")
                )
        );
    }

    public List<KundeDTO> getAllKunden() {
        // TODO: Aufgabe 4b) gib alle Kunden zurueck
        return null;
    }

    public void updatePassword(String email, String newPasswort) {
        // TODO: Aufgabe 4g)
    }

}

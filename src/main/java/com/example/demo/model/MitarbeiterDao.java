package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MitarbeiterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveMitarbeiter(MitarbeiterDTO mitarbeiter) {
        String sql = "INSERT INTO mitarbeiter (mitarbeiterkennung, vorname, nachname, gehalt, einstellungsdatum, passwort) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                mitarbeiter.mitarbeiterkennung(),
                mitarbeiter.vorname(),
                mitarbeiter.nachname(),
                mitarbeiter.gehalt(),
                mitarbeiter.einstellungdatum(),
                mitarbeiter.passwort()
        );
    }

    public boolean checkMitarbeiterkennungExists(String mitarbeiterkennung) {
        String sql = "SELECT COUNT(*) FROM mitarbeiter WHERE mitarbeiterkennung = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, mitarbeiterkennung);
        return count != null && count > 0;
    }

    public MitarbeiterDTO getMitarbeiterByKennung(String mitarbeiterkennung) {
        String sql = "SELECT * FROM mitarbeiter WHERE mitarbeiterkennung = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{mitarbeiterkennung},
                (rs, rowNum) -> new MitarbeiterDTO(
                        rs.getString("mitarbeiterkennung"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getInt("gehalt"),
                        rs.getDate("einstellungsdatum").toLocalDate(),
                        rs.getString("passwort")
                )
        );
    }

    public List<MitarbeiterDTO> getAllMitarbeiter() {
        String sql = "SELECT * FROM mitarbeiter";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new MitarbeiterDTO(
                        rs.getString("mitarbeiterkennung"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getInt("gehalt"),
                        rs.getDate("einstellungsdatum").toLocalDate(),
                        rs.getString("passwort")
                )
        );
    }
}


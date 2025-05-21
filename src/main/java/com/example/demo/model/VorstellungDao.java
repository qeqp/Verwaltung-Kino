package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public class VorstellungDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<VorstellungDTO> getAllVorstellungen() {
        // TODO: Aufgabe 4c) 
        return null;
    }

    
    public List<VorstellungDTO> getVorstellungenByDatum(Date datum) {
        // TODO: Aufgabe 4c) 
        return null;
    }

     
    public List<VorstellungDTO> getVorstellungenByUhrzeit(Time uhrzeit) {
        // TODO: Aufgabe 4c)
        return null;
    }
    
    public List<VorstellungDTO> getVorstellungenByDatumAndUhrzeit(Date datum, Time uhrzeit) {
        // TODO: Aufgabe 4c) 
        return null;
    }
    
    public void saveVorstellung(VorstellungDTO vorstellung) {
        // TODO: Aufgabe 4e) speichere eine neue Vorstellung
        return;
    }

}


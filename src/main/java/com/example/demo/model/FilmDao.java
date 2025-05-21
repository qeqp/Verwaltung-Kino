package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<FilmDTO> getAllFilme() {
        String sql = "SELECT * FROM film"; // Basis-SQL-Abfrage, die alle Filme aus der Tabelle "Film" auswaehlt.

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new FilmDTO(
                        rs.getInt("filmId"),
                        rs.getString("titel"),
                        rs.getString("genre"),
                        rs.getInt("fsk"),
                        rs.getInt("dauer")
                )
        );
    }

    public List<FilmDTO> getFilmeByGenreAndTitel(String genre, String titel) {
        String sql = "SELECT * FROM film WHERE genre = ? AND LOWER(titel) LIKE ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{genre, "%" + titel.toLowerCase() + "%"},
                (rs, rowNum) -> new FilmDTO(
                        rs.getInt("filmId"),
                        rs.getString("titel"),
                        rs.getString("genre"),
                        rs.getInt("fsk"),
                        rs.getInt("dauer")
                )
        );
    }

    public List<FilmDTO> getFilmeByGenre(String genre) {
        String sql = "SELECT * FROM film WHERE genre = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{genre},
                (rs, rowNum) -> new FilmDTO(
                        rs.getInt("filmId"),
                        rs.getString("titel"),
                        rs.getString("genre"),
                        rs.getInt("fsk"),
                        rs.getInt("dauer")
                )
        );
    }

    public List<FilmDTO> getFilmeByTitel(String titel) {
        // TODO: Aufgabe 4a) filtere Filme nach dem Titel
        return null;
    }

    public void saveFilm(FilmDTO film) {
        String sql = "INSERT INTO film (filmId, titel, genre, fsk, dauer) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.filmId(), film.titel(), film.genre(), film.fsk(), film.dauer());
    }
}


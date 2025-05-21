package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record KundeDTO(@JsonProperty("email") String email,
                       @JsonProperty("passwort") String passwort,
                       @JsonProperty("vorname") String vorname,
                       @JsonProperty("nachname") String nachname,
                       @JsonProperty("geburtsdatum") LocalDate geburtsdatum,
                       @JsonProperty("strasse") String strasse,
                       @JsonProperty("hausnummer") String hausnummer,
                       @JsonProperty("stadt") String stadt,
                       @JsonProperty("plz") Integer plz) {}
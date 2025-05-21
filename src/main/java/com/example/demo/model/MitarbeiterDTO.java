package com.example.demo.model;

import java.time.LocalDate;

public record MitarbeiterDTO(String mitarbeiterkennung, String vorname, String nachname, int gehalt, LocalDate einstellungdatum, String passwort) {}
package com.sakila.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Film de la base de datos Sakila.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Film {
    private int filmId;
    private String title;
    private String description;
    private int releaseYear;
    private int languageId;
    private int rentalDuration;
    private double rentalRate;
    private double replacementCost;
    private final List<Actor> actors = new ArrayList<>();

    /** Constructor vacío por defecto. */
    public Film() {}

    /**
     * Constructor completo con los campos principales de Film.
     * @param filmId Identificador único de la película.
     * @param title Título de la película.
     * @param description Sinopsis o descripción.
     * @param releaseYear Año de estreno.
     * @param languageId Identificador del idioma.
     * @param rentalDuration Duración estándar de renta en días.
     * @param rentalRate Tarifa de renta.
     * @param replacementCost Costo de reemplazo.
     */
    public Film(int filmId, String title, String description, int releaseYear, int languageId,
                int rentalDuration, double rentalRate, double replacementCost) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.languageId = languageId;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
    }

    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public int getLanguageId() { return languageId; }
    public void setLanguageId(int languageId) { this.languageId = languageId; }
    public int getRentalDuration() { return rentalDuration; }
    public void setRentalDuration(int rentalDuration) { this.rentalDuration = rentalDuration; }
    public double getRentalRate() { return rentalRate; }
    public void setRentalRate(double rentalRate) { this.rentalRate = rentalRate; }
    public double getReplacementCost() { return replacementCost; }
    public void setReplacementCost(double replacementCost) { this.replacementCost = replacementCost; }
    public List<Actor> getActors() { return actors; }

    @Override
    public String toString() {
        return "Film{id=" + filmId + ", title='" + title + "', year=" + releaseYear + ", rate=" + rentalRate + "}";
    }
}
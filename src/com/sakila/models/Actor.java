package com.sakila.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Actor y las películas en las que participa
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Actor {
    private int actorId;
    private String firstName;
    private String lastName;
    private final List<Film> films = new ArrayList<>();

    /** Constructor vacío por defecto. */
    public Actor() {}

    /**
     * Constructor con datos del actor.
     * @param actorId Identificador del actor.
     * @param firstName Nombre.
     * @param lastName Apellido.
     */
    public Actor(int actorId, String firstName, String lastName) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getActorId() { return actorId; }
    public void setActorId(int actorId) { this.actorId = actorId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public List<Film> getFilms() { return films; }

    @Override
    public String toString() {
        return "Actor{id=" + actorId + ", nombre='" + firstName + " " + lastName + "'}";
    }
}
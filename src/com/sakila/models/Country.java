package com.sakila.models;

/**
 * Representa la entidad Country de la base de datos Sakila.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Country {
    private int countryId;
    private String country;

    /** Constructor vacío por defecto. */
    public Country() {}

    /**
     * Constructor con datos del país.
     * @param countryId Identificador del país.
     * @param country Nombre del país.
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public int getCountryId() { return countryId; }
    public void setCountryId(int countryId) { this.countryId = countryId; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "Country{id=" + countryId + ", name='" + country + "'}";
    }
}
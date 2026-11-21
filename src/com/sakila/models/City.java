package com.sakila.models;

/**
 * Representa la entidad City con composición hacia Country.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class City {
    private int cityId;
    private String city;
    private int countryId;
    private Country countryObj;

    /** Constructor vacío por defecto. */
    public City() {}

    /**
     * Constructor con campos de City.
     * @param cityId Identificador de la ciudad.
     * @param city Nombre de la ciudad.
     * @param countryId Identificador del país asociado.
     */
    public City(int cityId, String city, int countryId) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
    }

    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public int getCountryId() { return countryId; }
    public void setCountryId(int countryId) { this.countryId = countryId; }
    public Country getCountryObj() { return countryObj; }
    public void setCountryObj(Country countryObj) { this.countryObj = countryObj; }

    @Override
    public String toString() {
        return "City{id=" + cityId + ", city='" + city + "', countryId=" + countryId + "}";
    }
}
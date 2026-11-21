package com.sakila.models;

/**
 * Representa una copia física en inventario (composición con Film y Store)
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Inventory {
    private int inventoryId;
    private int filmId;
    private int storeId;
    private Film film;
    private Store store;

    /** Constructor vacío por defecto. */
    public Inventory() {}

    /**
     * Constructor con claves de inventario.
     * @param inventoryId Identificador de inventario.
     * @param filmId Identificador de película.
     * @param storeId Identificador de tienda.
     */
    public Inventory(int inventoryId, int filmId, int storeId) {
        this.inventoryId = inventoryId;
        this.filmId = filmId;
        this.storeId = storeId;
    }

    public int getInventoryId() { return inventoryId; }
    public void setInventoryId(int inventoryId) { this.inventoryId = inventoryId; }
    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }
    public int getStoreId() { return storeId; }
    public void setStoreId(int storeId) { this.storeId = storeId; }
    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }

    @Override
    public String toString() {
        return "Inventory{inventoryId=" + inventoryId + ", filmId=" + filmId + ", storeId=" + storeId + "}";
    }
}
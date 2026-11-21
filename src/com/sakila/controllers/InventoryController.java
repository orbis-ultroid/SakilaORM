package com.sakila.controllers;

import com.sakila.data.FilmDAO;
import com.sakila.data.InventoryDAO;
import com.sakila.models.Inventory;
import java.util.List;

/**
 * Controlador MVC para la gestión de inventario (Inventory) y enlace con Film.
 * @author Estudiante UASD
 * @version 1.0
 */
public class InventoryController {
    private final InventoryDAO inventoryDAO;
    private final FilmDAO filmDAO;
    private List<Inventory> inventoryCache;

    /** Inicializa el controlador y enlaza los objetos Film correspondientes. */
    public InventoryController() {
        this.inventoryDAO = new InventoryDAO();
        this.filmDAO = new FilmDAO();
        refresh();
    }

    /** Recarga el inventario y asocia cada copia con su entidad Film. */
    public void refresh() {
        this.inventoryCache = inventoryDAO.get();
        for (Inventory inv : inventoryCache) {
            inv.setFilm(filmDAO.get(inv.getFilmId()));
        }
    }

    /**
     * Obtiene la lista completa de copias en inventario.
     * @return Lista de Inventory.
     */
    public List<Inventory> getAllInventory() {
        return inventoryCache;
    }

    /**
     * Agrega una copia física de una película a una tienda.
     * @param filmId ID de la película.
     * @param storeId ID de la tienda.
     * @return true si se registró correctamente.
     */
    public boolean addCopyToStore(int filmId, int storeId) {
        Inventory inv = new Inventory(0, filmId, storeId);
        boolean ok = inventoryDAO.post(inv);
        if (ok) refresh();
        return ok;
    }

    /**
     * Elimina una copia del inventario por su ID.
     * @param inventoryId Identificador de inventario.
     * @return true si se eliminó.
     */
    public boolean removeCopy(int inventoryId) {
        boolean ok = inventoryDAO.delete(inventoryId);
        if (ok) refresh();
        return ok;
    }
}
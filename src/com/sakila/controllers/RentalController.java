package com.sakila.controllers;

import com.sakila.data.*;
import com.sakila.models.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador MVC coherente al negocio de renta y devolución de películas.
 * @author Estudiante UASD
 * @version 1.0
 */
public class RentalController {
    private final RentalDAO rentalDAO;
    private final InventoryDAO inventoryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final PaymentDAO paymentDAO;
    private List<Rental> rentalCache;

    /** Inicializa el controlador y sus DAOs relacionados. */
    public RentalController() {
        this.rentalDAO = new RentalDAO();
        this.inventoryDAO = new InventoryDAO();
        this.customerDAO = new CustomerDAO();
        this.filmDAO = new FilmDAO();
        this.paymentDAO = new PaymentDAO();
        refresh();
    }

    /** Sincroniza la lista de rentas en memoria. */
    public void refresh() {
        this.rentalCache = rentalDAO.get();
    }

    /**
     * Devuelve todas las rentas cargadas.
     * @return Lista de Rental.
     */
    public List<Rental> getAllRentals() {
        return rentalCache;
    }

    /**
     * Verifica si una copia en inventario está disponible (sin renta abierta).
     * @param inventoryId ID de inventario.
     * @return true si está disponible para rentar.
     */
    public boolean isInventoryAvailable(int inventoryId) {
        for (Rental r : rentalDAO.get()) {
            if (r.getInventoryId() == inventoryId && r.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Registra la renta de una película y genera su pago inicial en payment.
     * @param inventoryId ID de copia en inventario.
     * @param customerId ID del cliente.
     * @param staffId ID del empleado.
     * @return true si la renta se completó.
     */
    public boolean rentMovie(int inventoryId, int customerId, int staffId) {
        Inventory inv = inventoryDAO.get(inventoryId);
        Customer cust = customerDAO.get(customerId);
        if (inv == null || cust == null || !cust.isActive()) {
            System.err.println("[Negocio] Inventario no encontrado o cliente inactivo.");
            return false;
        }
        if (!isInventoryAvailable(inventoryId)) {
            System.err.println("[Negocio] La copia de inventario ID " + inventoryId + " ya está rentada.");
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        Rental rental = new Rental(0, now, inventoryId, customerId, null, staffId);
        if (rentalDAO.post(rental)) {
            Film film = filmDAO.get(inv.getFilmId());
            double rate = (film != null) ? film.getRentalRate() : 4.99;
            Payment payment = new Payment(0, customerId, staffId, rental.getRentalId(), rate, now);
            paymentDAO.post(payment);
            refresh();
            return true;
        }
        return false;
    }

    /**
     * Registra la devolución de una película rentada.
     * @param rentalId ID de la renta.
     * @return true si se registró la devolución.
     */
    public boolean returnMovie(int rentalId) {
        Rental rental = rentalDAO.get(rentalId);
        if (rental == null || rental.getReturnDate() != null) {
            System.err.println("[Negocio] Renta inexistente o ya devuelta.");
            return false;
        }
        rental.setReturnDate(LocalDateTime.now());
        boolean ok = rentalDAO.put(rental);
        if (ok) refresh();
        return ok;
    }
}